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
package org.xwiki.contrib.repository.bintray.internal;

import java.net.URISyntaxException;
import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.phase.Initializable;
import org.xwiki.contrib.repository.bintray.internal.model.BintrayRepositoryDescriptor;
import org.xwiki.extension.repository.ExtensionRepository;
import org.xwiki.extension.repository.ExtensionRepositoryException;
import org.xwiki.extension.repository.ExtensionRepositoryFactory;
import org.xwiki.extension.repository.ExtensionRepositoryManager;
import org.xwiki.observation.AbstractEventListener;
import org.xwiki.observation.event.Event;

/**
 * This listener is only for execution configuration logic on installment.
 * It's a bit of workaround. Every listener is initialized by Observation Manager after registering it.
 *
 * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
 * @since 1.0
 */
@Component
@Named("BintrayRepositoryExtensionPostInstallmentConfigListener")
@Singleton
public class ExtensionPostInstallmentConfigListener extends AbstractEventListener implements Initializable
{
    @Inject
    private Logger logger;

    @Inject
    private BintrayRepositoryConfiguration bintrayRepositoryConfiguration;

    @Inject
    private ExtensionRepositoryManager extensionRepositoryManager;

    @Inject
    @Named("bintray-maven")
    private ExtensionRepositoryFactory bintrayMavenExtensionRepositoryFactory;

    /**
     *
     */
    public ExtensionPostInstallmentConfigListener()
    {
        super("BintrayRepositoryExtensionPostInstallmentConfigListener", Collections.emptyList());
    }

    @Override
    public void initialize()
    {
        logger.info(getName() + " registered");
        addBintrayRepositories();
    }

    private void addBintrayRepositories()
    {
        bintrayRepositoryConfiguration.getBintrayRepositoriesDescriptors().stream().forEach(
                bintrayRepositoryDescriptor -> {
                    try {
                        ExtensionRepository extensionRepository =
                                createExtensionRepository(bintrayRepositoryDescriptor);
                        extensionRepositoryManager.addRepository(extensionRepository);
                        this.logger.info("Repository of id: '" + bintrayRepositoryDescriptor.getId()
                                + "' registered successfully");
                    } catch (ExtensionRepositoryException | URISyntaxException e) {
                        this.logger.error("Failed to add bintray repository [" + bintrayRepositoryDescriptor + "]", e);
                    }
                }
        );
    }

    private ExtensionRepository createExtensionRepository(BintrayRepositoryDescriptor bintrayRepositoryDescriptor)
            throws URISyntaxException, ExtensionRepositoryException
    {
        switch (bintrayRepositoryDescriptor.getRepositoryType()) {
            case MAVEN:
                return bintrayMavenExtensionRepositoryFactory.createRepository(
                        bintrayRepositoryDescriptor.toExtensionRepositoryDescriptor());
            default:
                throw new RuntimeException(
                        "Bintray repository of type: " + bintrayRepositoryDescriptor.getRepositoryType()
                                + " not yet fully implemented.");
        }
    }

    @Override
    public void onEvent(Event event, Object o, Object o1)
    {
    }
}
