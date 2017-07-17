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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.phase.Initializable;
import org.xwiki.extension.ExtensionLicenseManager;
import org.xwiki.extension.internal.ExtensionFactory;
import org.xwiki.extension.repository.AbstractExtensionRepositoryFactory;
import org.xwiki.extension.repository.DefaultExtensionRepositoryDescriptor;
import org.xwiki.extension.repository.ExtensionRepository;
import org.xwiki.extension.repository.ExtensionRepositoryDescriptor;
import org.xwiki.extension.repository.ExtensionRepositoryException;
import org.xwiki.extension.repository.ExtensionRepositoryFactory;
import org.xwiki.extension.repository.http.internal.HttpClientFactory;

/**
 * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
 * @since 0.2
 */
@Component
@Singleton
@Named("bintray-maven")
public class BintrayMavenExtensionRepositoryFactory extends AbstractExtensionRepositoryFactory implements Initializable
{
    @Inject
    @Named("maven")
    private ExtensionRepositoryFactory mavenExtensionRepositoryFactory;

    @Inject
    private Logger logger;

    @Inject
    private ExtensionLicenseManager licenseManager;

    @Inject
    private HttpClientFactory httpClientFactory;

    @Inject
    private ExtensionFactory extensionFactory;

    @Override public void initialize()
    {
        this.logger.info("Bintray Maven Extension Repository Factory initialized.");
    }

    @Override
    public ExtensionRepository createRepository(ExtensionRepositoryDescriptor extensionRepositoryDescriptor)
            throws ExtensionRepositoryException
    {
        try {
            ExtensionRepositoryDescriptor mavenRepositoryDescriptor =
                    obtainMavenRepositoryDescriptor(extensionRepositoryDescriptor);
            ExtensionRepository aetherExtensionRepository =
                    mavenExtensionRepositoryFactory.createRepository(mavenRepositoryDescriptor);
            return new BintrayMavenExtensionRepository(extensionRepositoryDescriptor,
                    aetherExtensionRepository, licenseManager, extensionFactory,
                    httpClientFactory, logger);
        } catch (Exception e) {
            throw new ExtensionRepositoryException(
                    "Failed to create repository [" + extensionRepositoryDescriptor + "]", e);
        }
    }

    private ExtensionRepositoryDescriptor obtainMavenRepositoryDescriptor(
            ExtensionRepositoryDescriptor extensionRepositoryDescriptor)
    {
        return new DefaultExtensionRepositoryDescriptor(extensionRepositoryDescriptor.getId() + "-maven", "maven",
                extensionRepositoryDescriptor.getURI());
    }
}
