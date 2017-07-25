package org.xwiki.contrib.repository.bintray.internal.dto;

import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Krzysztof on 22.06.2017.
 */
public class BintrayPackagesTest
{
    private BintrayPackageDTO[] createBintrayPackageArray(int size)
    {
        ArrayList<BintrayPackageDTO> packages = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            packages.add(new BintrayPackageDTO());
        }
        return packages.toArray(new BintrayPackageDTO[size]);
    }

    private void runTest(int initialCapacity, int limitValue)
    {
        runTest(initialCapacity, limitValue, limitValue);
    }

    private void runTest(int initialCapacity, int limitValue, int expectedCapacity)
    {
        //given
        BintrayPackages bintrayPackages = new BintrayPackages(createBintrayPackageArray(initialCapacity));
        //when
        bintrayPackages.limitContent(limitValue);
        //then
        assertEquals(expectedCapacity, bintrayPackages.getBintrayPackageDTOs().size());
    }

    @Test
    public void limitContentWhenToBig() throws Exception
    {
        runTest(22, 20);
    }

    @Test
    public void limitContentWhenEqual() throws Exception
    {
        runTest(20, 20);
    }

    @Test
    public void limitContentWhenBelow() throws Exception
    {
        runTest(18, 20, 18);
    }

    @Test
    public void limitContentWhenArgumentBiggerThen50() throws Exception
    {
        runTest(22, 55, 22);
    }

    @Test
    public void limitContentWhenArgumentBelow0() throws Exception
    {
        runTest(22, -1, 22);
    }
}