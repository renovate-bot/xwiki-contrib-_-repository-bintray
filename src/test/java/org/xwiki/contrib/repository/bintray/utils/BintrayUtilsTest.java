package org.xwiki.contrib.repository.bintray.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Krzysztof on 17.07.2017.
 */
public class BintrayUtilsTest
{
    @Test
    public void shouldParseIdCorrectly() throws Exception
    {
        String id = "org.xwiki.contrib:repository-bintray";
        MavenId mavenId = BintrayUtils.parseMavenId(id);
        assertEquals("org.xwiki.contrib", mavenId.getGroupId());
        assertEquals("repository-bintray", mavenId.getArtifactId());
    }
}