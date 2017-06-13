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

import java.net.URI;

import org.xwiki.extension.Extension;
import org.xwiki.extension.ExtensionDependency;
import org.xwiki.extension.ExtensionId;
import org.xwiki.extension.ResolveException;
import org.xwiki.extension.repository.AbstractExtensionRepository;
import org.xwiki.extension.repository.ExtensionRepository;
import org.xwiki.extension.repository.ExtensionRepositoryDescriptor;
import org.xwiki.extension.repository.result.IterableResult;
import org.xwiki.extension.version.Version;

/**
 * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
 * @since 0.2
 */
public class BintrayMavenExtensionRepository extends AbstractExtensionRepository
{

    final private String BINTRAY_API_URL = "http://api.bintray.com/";

    private final ExtensionRepository aetherExtensionRepository;

    private String subject;
    private String repo;


    /**
     * @param extensionRepositoryDescriptor -
     * @param aetherExtensionRepository - previously created aetherExtensionRepository
     */
    public BintrayMavenExtensionRepository(ExtensionRepositoryDescriptor extensionRepositoryDescriptor,
            ExtensionRepository aetherExtensionRepository)
    {
        this.aetherExtensionRepository = aetherExtensionRepository;
        populateSubjectRepoFields(extensionRepositoryDescriptor.getURI());

    }

    private void populateSubjectRepoFields(URI uri)
    {
        String [] pathElements = uri.getPath().split("/");
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

    protected String getSubject()
    {
        return subject;
    }

    protected String getRepo()
    {
        return repo;
    }
}

