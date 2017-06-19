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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.configuration.ConfigurationSource;
import org.xwiki.contrib.repository.bintray.model.BintrayRepositoryDescriptor;
import org.xwiki.contrib.repository.bintray.model.BintrayRepositoryType;

import com.google.common.collect.ImmutableList;

/**
 * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
 * @since 1.0
 */
@Component
@Singleton
public class DefaultBintrayRepositoryConfiguration implements BintrayRepositoryConfiguration
{
    /**
     * The prefix of all the bintray repository extension related properties.
     */
    private static final String BINTRAY_PREFIX = "extension.repositories.bintray";

    /**
     * The prefix of all the bintray repository extension related properties.
     */
    private static final String BINTRAY_MAVEN_PROP = BINTRAY_PREFIX + ".maven";

    @Inject
    private Logger logger;

    @Inject
    private Provider<ConfigurationSource> configuration;

    @Override
    public List<BintrayRepositoryDescriptor> getBintrayRepositoriesDescriptors()
    {
        return new ImmutableList.Builder<BintrayRepositoryDescriptor>()
                .addAll(getBintrayMavenRepositoriesDescriptors())
                //space for adding more types of repositories
                .build();
    }

    private List<BintrayRepositoryDescriptor> getBintrayMavenRepositoriesDescriptors()
    {
        List<String> bintrayMavenPropStrings =
                this.configuration.get().getProperty(BINTRAY_MAVEN_PROP, Collections.<String>emptyList());

        return bintrayMavenPropStrings.stream().map(prop -> {
            String[] parts = prop.split(":");
            if (parts.length != 3) {
                logger.warn("Found invalid bintray maven configuration [{}]. ", prop);
                return null;
            } else {
                return new BintrayRepositoryDescriptor(parts[0], BintrayRepositoryType.MAVEN, parts[1], parts[2]);
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
