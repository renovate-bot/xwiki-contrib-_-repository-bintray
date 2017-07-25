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
package org.xwiki.contrib.repository.bintray.internal.utils;

import org.xwiki.extension.ExtensionId;
import org.xwiki.extension.ResolveException;

/**
 * Created by Krzysztof on 17.07.2017.
 */
public class BintrayUtils
{
    public static MavenId parseMavenId(ExtensionId extensionId) throws ResolveException
    {
        String id = extensionId.getId();
        return parseMavenId(id);
    }

    public static MavenId parseMavenId(String id) throws ResolveException
    {
        String[] parts = id.split(":");
        if (parts.length < 2) {
            throw new ResolveException("Bad id " + id + ", expected format is <groupId>:<artifactId>[:<classifier>]");
        }
        return new MavenId(parts[0], parts[1]);
    }
}
