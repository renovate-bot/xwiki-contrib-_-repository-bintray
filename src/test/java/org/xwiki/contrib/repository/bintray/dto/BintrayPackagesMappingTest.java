package org.xwiki.contrib.repository.bintray.dto;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertEquals;

/**
 * Created by Krzysztof on 23.06.2017.
 */
public class BintrayPackagesMappingTest
{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void unmapJsonToObjectTestAndLimitSize() throws Exception
    {
        String json = getBintrayPackagesJson();
        BintrayPackageDTO[] bintrayPackageDTOs = objectMapper.readValue(json, BintrayPackageDTO[].class);
        //no exception expected - all fields are mapped

        BintrayPackages bintrayPackages = new BintrayPackages(bintrayPackageDTOs);
        bintrayPackages.limitContent(10);
        assertEquals(10, bintrayPackages.getBintrayPackageDTOs().size());
    }

    public String getBintrayPackagesJson() throws IOException
    {
        InputStream resourceAsStream = getClass().getResourceAsStream("BintrayPackagesJSON.json");
        try {
            return IOUtils.toString(resourceAsStream);
        } finally {
            IOUtils.closeQuietly(resourceAsStream);
        }
    }
}