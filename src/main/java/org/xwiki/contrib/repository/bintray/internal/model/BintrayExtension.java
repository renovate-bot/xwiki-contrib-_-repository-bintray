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
package org.xwiki.contrib.repository.bintray.internal.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.xwiki.contrib.repository.bintray.internal.BintrayMavenExtensionRepository;
import org.xwiki.contrib.repository.bintray.internal.BintrayParameters;
import org.xwiki.contrib.repository.bintray.internal.dto.BintrayPackageDTO;
import org.xwiki.contrib.repository.bintray.internal.utils.BintrayUtils;
import org.xwiki.contrib.repository.bintray.internal.utils.MavenId;
import org.xwiki.extension.AbstractRemoteExtension;
import org.xwiki.extension.Extension;
import org.xwiki.extension.ExtensionFile;
import org.xwiki.extension.ExtensionId;
import org.xwiki.extension.ExtensionLicense;
import org.xwiki.extension.ExtensionLicenseManager;
import org.xwiki.extension.ResolveException;
import org.xwiki.extension.internal.ExtensionFactory;
import org.xwiki.extension.repository.ExtensionRepository;

import com.google.common.collect.ImmutableMap;

/**
 * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
 * @since 1.0
 */
public class BintrayExtension extends AbstractRemoteExtension
{
    private final ExtensionRepository extensionRepository;

    private final Logger logger;

    /**
     * @param bintrayPackageDTO -
     * @param repository -
     * @param extensionRepository -
     * @param licenseManager -
     * @param extensionFactory -
     * @param logger -
     * @throws ResolveException -
     */
    public BintrayExtension(BintrayPackageDTO bintrayPackageDTO,
            BintrayMavenExtensionRepository repository, ExtensionRepository extensionRepository,
            ExtensionLicenseManager licenseManager, ExtensionFactory extensionFactory, Logger logger)
            throws ResolveException
    {
        super(repository,
                new ExtensionId(bintrayPackageDTO.getSystem_ids().get(0), bintrayPackageDTO.getLatest_version()),
                "jar");
        this.logger = logger;
        this.extensionRepository = extensionRepository;
        setName(bintrayPackageDTO.getName());
        addLicenses(bintrayPackageDTO.getLicenses(), licenseManager);
        setDescription(bintrayPackageDTO.getDesc());
        setSummary(StringUtils.substring(bintrayPackageDTO.getDesc(), 0, 200));
        setWebsite(bintrayPackageDTO.getWebsite_url());
        addRepository(repository.getDescriptor());
        setIssueManagement(
                extensionFactory.getExtensionIssueManagement(null, bintrayPackageDTO.getIssue_tracker_url()));
        setRecommended(false);
        setProperties(getId());
    }

    private void setProperties(ExtensionId id)
            throws ResolveException
    {
        MavenId mavenId = BintrayUtils.parseMavenId(getId());
        setProperties(ImmutableMap.of(BintrayParameters.MAVEN_ARTIFACTID_PROP, mavenId.getArtifactId(),
                BintrayParameters.MAVEN_GROUPID_PROP, mavenId.getGroupId()));
    }

    @Override
    public ExtensionFile getFile()
    {
        if (super.getFile() == null) {
            try {
                Extension extension = extensionRepository.resolve(getId());
                setFile(extension.getFile());
            } catch (ResolveException e) {
                this.logger.error("Failed to resolve extension of id [" + getId().getId() + "]", e);
            }
        }
        return super.getFile();
    }

    private void addLicenses(List<String> licenses, ExtensionLicenseManager licenseManager)
    {
        licenses.stream()
                .forEach(licenseName -> {
                    if (licenseName != null) {
                        ExtensionLicense extensionLicense = licenseManager.getLicense(licenseName);
                        if (extensionLicense != null) {
                            addLicense(extensionLicense);
                        } else {
                            List<String> content = null;
                            addLicense(new ExtensionLicense(licenseName, content));
                        }
                    }
                });
    }
}
