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

/**
 * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
 * @since 1.0
 */
public interface BintrayParameters
{
    String NATIVE_REPO_URL = "http://dl.bintray.com";
    String BINTRAY_API_URL = "http://api.bintray.com";
    String BINTRAY_PACKAGE_SEARCH_URL = BINTRAY_API_URL + "/search/packages";
    String BINTRAY_API_SUBJECT_PARAM = "subject";
    String BINTRAY_API_REPO_PARAM = "repo";
    String BINTRAY_API_PAGINATION_START_POS_PARAM = "start_pos";
    String BINTRAY_API_PACKAGE_SEARCH_NAME_PARAM = "name";
    // properties to set in BintrayExtension
    String MAVEN_ARTIFACTID_PROP = "maven.artifactid";
    String MAVEN_GROUPID_PROP = "maven.groupid";
}
