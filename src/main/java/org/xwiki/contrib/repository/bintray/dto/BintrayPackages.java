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
package org.xwiki.contrib.repository.bintray.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
 * @since 1.0
 */
public class BintrayPackages
{
    private List<BintrayPackageDTO> bintrayPackageDTOs = Collections.EMPTY_LIST;

    /**
     *
     */
    public BintrayPackages()
    {
    }

    /**
     * @param bintrayPackageDTOs -
     */
    public BintrayPackages(BintrayPackageDTO[] bintrayPackageDTOs)
    {
        this.bintrayPackageDTOs = new ArrayList<BintrayPackageDTO>(Arrays.asList(bintrayPackageDTOs));
    }

    /**
     *
     * @return
     */
    public List<BintrayPackageDTO> getBintrayPackageDTOs()
    {
        return bintrayPackageDTOs;
    }

    /**
     * @param bintrayPackageDTOs -
     */
    public void setBintrayPackageDTOs(List<BintrayPackageDTO> bintrayPackageDTOs)
    {
        this.bintrayPackageDTOs = bintrayPackageDTOs;
    }

    /**
     * If needed removes elements from inner collection to assure number of elements does not exceed <code>number</code>
     *
     * @param number - maximum number of elements to leave in inner collection
     */
    public BintrayPackages limitContent(int number)
    {
        if (number < 0) {
            return this;
        } else if (number > 50) {
            number = 50;
        }
        if (number > bintrayPackageDTOs.size()) {
            return this;
        }
        bintrayPackageDTOs.retainAll(bintrayPackageDTOs.subList(0, number));
        return this;
    }
}
