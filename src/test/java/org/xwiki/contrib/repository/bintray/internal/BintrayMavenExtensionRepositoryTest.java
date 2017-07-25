package org.xwiki.contrib.repository.bintray.internal;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.xwiki.extension.repository.DefaultExtensionRepositoryDescriptor;
import org.xwiki.extension.repository.ExtensionRepositoryDescriptor;

import static org.junit.Assert.assertEquals;

/**
 * Created by Krzysztof on 13.06.2017.
 */
public class BintrayMavenExtensionRepositoryTest
{
    @Test
    public void populationOfSubjectRepoTest() throws Exception
    {

        List<String> uris =
                Arrays.asList("http://dl.bintray.com/bintray/jcenter", "http://dl.bintray.com/bintray/jcenter/");

        for (String uri : uris) {
            //given
            ExtensionRepositoryDescriptor extensionRepositoryDescriptor =
                    new DefaultExtensionRepositoryDescriptor(
                            "jcenter",
                            "bintray-maven",
                            new URI(uri));

            //when
            BintrayMavenExtensionRepository bintrayMavenExtensionRepository =
                    new BintrayMavenExtensionRepository(extensionRepositoryDescriptor, null, null, null, null, null);

            //then
            assertEquals("bintray", bintrayMavenExtensionRepository.getSubject());
            assertEquals("jcenter", bintrayMavenExtensionRepository.getRepo());
        }
    }
}