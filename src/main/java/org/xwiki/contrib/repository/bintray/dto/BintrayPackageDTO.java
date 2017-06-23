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

import java.util.List;

/**
 * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
 * @since 1.0
 */
public class BintrayPackageDTO
{
    private String name;

    private String repo;

    private String owner;

    private String desc;

    private List<String> labels;

    private List<String> attribute_names;

    private List<String> licenses;

    private List<String> custom_licenses;

    private int followers_count;

    private String created;

    private String website_url;

    private String issue_tracker_url;

    private List<String> linked_to_repos;

    private List<String> permissions;

    private List<String> versions;

    private String latest_version;

    private String updated;

    private String rating_count;

    private List<String> system_ids;

    private String vcs_url;

    private String maturity;

    private String linked_to_repo;

    private String rating;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRepo()
    {
        return repo;
    }

    public void setRepo(String repo)
    {
        this.repo = repo;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public List<String> getLabels()
    {
        return labels;
    }

    public void setLabels(List<String> labels)
    {
        this.labels = labels;
    }

    public List<String> getAttribute_names()
    {
        return attribute_names;
    }

    public void setAttribute_names(List<String> attribute_names)
    {
        this.attribute_names = attribute_names;
    }

    public List<String> getLicenses()
    {
        return licenses;
    }

    public void setLicenses(List<String> licenses)
    {
        this.licenses = licenses;
    }

    public List<String> getCustom_licenses()
    {
        return custom_licenses;
    }

    public void setCustom_licenses(List<String> custom_licenses)
    {
        this.custom_licenses = custom_licenses;
    }

    public int getFollowers_count()
    {
        return followers_count;
    }

    public void setFollowers_count(int followers_count)
    {
        this.followers_count = followers_count;
    }

    public String getCreated()
    {
        return created;
    }

    public void setCreated(String created)
    {
        this.created = created;
    }

    public String getWebsite_url()
    {
        return website_url;
    }

    public void setWebsite_url(String website_url)
    {
        this.website_url = website_url;
    }

    public String getIssue_tracker_url()
    {
        return issue_tracker_url;
    }

    public void setIssue_tracker_url(String issue_tracker_url)
    {
        this.issue_tracker_url = issue_tracker_url;
    }

    public List<String> getLinked_to_repos()
    {
        return linked_to_repos;
    }

    public void setLinked_to_repos(List<String> linked_to_repos)
    {
        this.linked_to_repos = linked_to_repos;
    }

    public List<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(List<String> permissions)
    {
        this.permissions = permissions;
    }

    public List<String> getVersions()
    {
        return versions;
    }

    public void setVersions(List<String> versions)
    {
        this.versions = versions;
    }

    public String getLatest_version()
    {
        return latest_version;
    }

    public void setLatest_version(String latest_version)
    {
        this.latest_version = latest_version;
    }

    public String getUpdated()
    {
        return updated;
    }

    public void setUpdated(String updated)
    {
        this.updated = updated;
    }

    public String getRating_count()
    {
        return rating_count;
    }

    public void setRating_count(String rating_count)
    {
        this.rating_count = rating_count;
    }

    public List<String> getSystem_ids()
    {
        return system_ids;
    }

    public void setSystem_ids(List<String> system_ids)
    {
        this.system_ids = system_ids;
    }

    public String getVcs_url()
    {
        return vcs_url;
    }

    public void setVcs_url(String vcs_url)
    {
        this.vcs_url = vcs_url;
    }

    public String getMaturity()
    {
        return maturity;
    }

    public void setMaturity(String maturity)
    {
        this.maturity = maturity;
    }

    public String getLinked_to_repo()
    {
        return linked_to_repo;
    }

    public void setLinked_to_repo(String linked_to_repo)
    {
        this.linked_to_repo = linked_to_repo;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }
}

