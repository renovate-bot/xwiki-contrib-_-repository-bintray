/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.repository.bintray;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.xwiki.contrib.repository.bintray.dto.BintrayPackageDTO;
import org.xwiki.contrib.repository.bintray.dto.BintrayPackages;
import org.xwiki.contrib.repository.bintray.model.BintrayExtension;
import org.xwiki.extension.Extension;
import org.xwiki.extension.ExtensionDependency;
import org.xwiki.extension.ExtensionId;
import org.xwiki.extension.ExtensionLicenseManager;
import org.xwiki.extension.ResolveException;
import org.xwiki.extension.internal.ExtensionFactory;
import org.xwiki.extension.repository.AbstractExtensionRepository;
import org.xwiki.extension.repository.ExtensionRepositoryDescriptor;
import org.xwiki.extension.repository.aether.internal.AetherExtensionRepository;
import org.xwiki.extension.repository.http.internal.HttpClientFactory;
import org.xwiki.extension.repository.result.CollectionIterableResult;
import org.xwiki.extension.repository.result.IterableResult;
import org.xwiki.extension.repository.search.SearchException;
import org.xwiki.extension.repository.search.Searchable;
import org.xwiki.extension.version.Version;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
 * @since 0.2
 */
public class BintrayMavenExtensionRepository extends AbstractExtensionRepository implements Searchable
{
    private static ObjectMapper objectMapper = new ObjectMapper();

    private final AetherExtensionRepository aetherExtensionRepository;


    private final ExtensionLicenseManager licenseManager;

    private final ExtensionFactory extensionFactory;

    private final HttpClientFactory httpClientFactory;

    private final Logger logger;

    private final HttpClientContext localContext;

    private String subject;

    private String repo;

    /**
     * @param extensionRepositoryDescriptor -
     * @param aetherExtensionRepository - previously created aetherExtensionRepository
     * @param licenseManager -
     * @param extensionFactory -
     * @param httpClientFactory -
     * @param logger -
     */
    public BintrayMavenExtensionRepository(ExtensionRepositoryDescriptor extensionRepositoryDescriptor,
            AetherExtensionRepository aetherExtensionRepository,
            ExtensionLicenseManager licenseManager, ExtensionFactory extensionFactory,
            HttpClientFactory httpClientFactory, Logger logger)
    {
        super(extensionRepositoryDescriptor);
        this.aetherExtensionRepository = aetherExtensionRepository;
        this.licenseManager = licenseManager;
        this.extensionFactory = extensionFactory;
        this.httpClientFactory = httpClientFactory;
        this.logger = logger;
        this.localContext = HttpClientContext.create();

        populateSubjectRepoFields(extensionRepositoryDescriptor.getURI());
    }

    private void populateSubjectRepoFields(URI uri)
    {
        String[] pathElements = uri.getPath().split("/");
        subject = pathElements[1];
        repo = pathElements[2];
    }

    @Override
    public Extension resolve(ExtensionId extensionId) throws ResolveException
    {
        return aetherExtensionRepository.resolve(extensionId);
    }

    @Override
    public Extension resolve(ExtensionDependency extensionDependency) throws ResolveException
    {
        return aetherExtensionRepository.resolve(extensionDependency);
    }

    @Override
    public IterableResult<Version> resolveVersions(String s, int i, int i1) throws ResolveException
    {
        return aetherExtensionRepository.resolveVersions(s, i, i1);
    }

    @Override public IterableResult<Extension> search(String pattern, int offset, int limit) throws SearchException
    {
        String url = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(BintrayParameters.BINTRAY_PACKAGE_SEARCH_URL);
            uriBuilder.addParameter(BintrayParameters.BINTRAY_API_SUBJECT_PARAM, subject);
            uriBuilder.addParameter(BintrayParameters.BINTRAY_API_REPO_PARAM, repo);
            uriBuilder.addParameter(BintrayParameters.BINTRAY_API_PACKAGE_SEARCH_NAME_PARAM, pattern);
            uriBuilder.addParameter(BintrayParameters.BINTRAY_API_PAGINATION_START_POS_PARAM, "" + offset);

            url = uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            throw new SearchException("Failed to build REST URL", e);
        }

        HttpGet getMethod = new HttpGet(url);
        CloseableHttpClient httpClient = httpClientFactory.createClient(null, null);
        CloseableHttpResponse response;
        try {
            if (this.localContext != null) {
                response = httpClient.execute(getMethod, this.localContext);
            } else {
                response = httpClient.execute(getMethod);
            }
        } catch (Exception e) {
            throw new SearchException(String.format("Failed to request [%s]", getMethod.getURI()), e);
        }

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new SearchException(String.format("Invalid answer [%s] from the server when requesting [%s]",
                    response.getStatusLine().getStatusCode(), getMethod.getURI()));
        }

        BintrayPackages bintrayPackages = null;
        int totalHits = 0;
        try {
            String totalHitsString = response.getLastHeader("X-RangeLimit-Total").getValue();
            totalHits = Integer.parseInt(totalHitsString);
            BintrayPackageDTO[] bintrayPackageDTOs =
                    objectMapper.readValue(response.getEntity().getContent(), BintrayPackageDTO[].class);
            bintrayPackages = new BintrayPackages(bintrayPackageDTOs);
        } catch (IOException e) {
            throw new SearchException(
                    String.format("Invalid response body from the bintray server when requesting: [%s]", pattern), e);
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(httpClient);
        }

        bintrayPackages.limitContent(limit);

        return getItarableResultFrom(bintrayPackages, totalHits, offset, logger);
    }

    private CollectionIterableResult<Extension> getItarableResultFrom(BintrayPackages bintrayPackages, int totalHits,
            int offset, Logger logger) throws SearchException
    {
        ArrayList<Extension> extensions = new ArrayList<>(bintrayPackages.getBintrayPackageDTOs().size());
        for (BintrayPackageDTO bintrayPackageDTO : bintrayPackages.getBintrayPackageDTOs()) {
            try {
                extensions.add(new BintrayExtension(bintrayPackageDTO, this, aetherExtensionRepository, licenseManager,
                        extensionFactory));
            } catch (Exception e) {
                logger.warn("Problem with resolving extension: [{}] - [{}]", bintrayPackageDTO.getName(),
                        e.getMessage());
            }
        }
        return new CollectionIterableResult<Extension>(totalHits, offset, extensions);
    }

    protected String getSubject()
    {
        return subject;
    }

    protected String getRepo()
    {
        return repo;
    }
}

