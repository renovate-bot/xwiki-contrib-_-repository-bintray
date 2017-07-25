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

import java.net.URI;
import java.net.URISyntaxException;

import org.xwiki.contrib.repository.bintray.internal.BintrayParameters;
import org.xwiki.extension.repository.DefaultExtensionRepositoryDescriptor;
import org.xwiki.extension.repository.ExtensionRepositoryDescriptor;

/**
 * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
 * @since 1.0
 */
public class BintrayRepositoryDescriptor
{
    private String id;

    private BintrayRepositoryType repositoryType;

    private String subject;

    private String repo;

    /**
     * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
     * @since 1.0
     * @param id -
     * @param repositoryType -
     * @param subject -
     * @param repo -
     */
    public BintrayRepositoryDescriptor(String id, BintrayRepositoryType repositoryType, String subject, String repo)
    {
        this.id = id;
        this.repositoryType = repositoryType;
        this.subject = subject;
        this.repo = repo;
    }

    /**
     * @return
     * @throws URISyntaxException when bad subject or repo data
     */
    public ExtensionRepositoryDescriptor toExtensionRepositoryDescriptor() throws URISyntaxException
    {
        return new DefaultExtensionRepositoryDescriptor(id, repositoryType.getName(), getNativeRepoURI());
    }

    /**
     * @return
     * @throws URISyntaxException when bad subject or repo data
     */
    public URI getNativeRepoURI() throws URISyntaxException
    {
        return new URI(BintrayParameters.NATIVE_REPO_URL + "/" + subject + "/" + repo);
    }

    /**
     *
     * @return
     */
    public BintrayRepositoryType getRepositoryType()
    {
        return repositoryType;
    }

    /**
     *
     * @return
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     *
     * @return
     */
    public String getRepo()
    {
        return repo;
    }

    /**
     *
     * @return
     */
    public String getId()
    {
        return id;
    }

    @Override public String toString()
    {
        return "BintrayRepositoryDescriptor{" +
                ", id='" + id + '\'' +
                ", repositoryType=" + repositoryType +
                ", subject='" + subject + '\'' +
                ", repo='" + repo + '\'' +
                '}';
    }
}
