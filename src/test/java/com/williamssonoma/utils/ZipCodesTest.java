package com.williamssonoma.utils;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ZipCodesTest {


    @Test
    public void parseList() {
        String [] validInput = {"94226,94399", "94133,94200", "94134,94299"};
        Integer[][] validOutput = {{94226,94399}, {94133,94200},{94134,94299}};

        Integer[][] output = ZipCodes.parseList(validInput);
        Assert.assertArrayEquals(validOutput[0], output[0]);
        Assert.assertArrayEquals(validOutput[1], output[1]);
        Assert.assertArrayEquals(validOutput[2], output[2]);
    }

    @Test
    public void mergeRangesNoOverlapAnyOrder() throws Exception {
        Integer[][] ranges = {{94200,94299}, {94600,94699},{94133,94133}};
        Integer[][] expectedRanges = {{94133,94133}, {94200,94299}, {94600,94699}};

        List<Integer[]> source = ZipCodes.mergeRanges(ranges);
        List<Integer[]> expected = new ArrayList<>(Arrays.asList(expectedRanges));

        Assert.assertArrayEquals("Returns correct answer.",expected.get(0), source.get(0) );
        Assert.assertArrayEquals("Returns correct answer.",expected.get(1), source.get(1) );
        Assert.assertArrayEquals("Returns correct answer.",expected.get(2), source.get(2) );
        Assert.assertNotEquals("Sorted correctly",expected.get(0), source.get(0));
    }

    @Test
    public void mergeRangeSingleOverlap(){
        Integer[][] ranges = {{94133,94133}, {94200,94299}, {94226,94399}};
        Integer[][] expectedRanges = {{94133,94133}, {94200,94399}};

        List<Integer[]> source = ZipCodes.mergeRanges(ranges);
        List<Integer[]> expected = new ArrayList<>(Arrays.asList(expectedRanges));
        Integer[] mergedRange = new Integer[] {94200,94399};

        Assert.assertArrayEquals("Returns correct ranges",expected.toArray(),source.toArray());
        Assert.assertThat("Sorted ranges correctly",expected.get(0), IsNot.not(IsEqual.equalTo(mergedRange)));
        Assert.assertArrayEquals("Merges ranges correctly",expected.get(1),mergedRange);
    }

    @Test
    public void mergeRangeMultipleOverlap(){
        Integer[][] ranges = {{94133,94200}, {94134,94299}, {94226,94399}};
        Integer[][] expectedRanges = {{94133,94399}};

        List<Integer[]> source = ZipCodes.mergeRanges(ranges);
        List<Integer[]> expected = new ArrayList<>(Arrays.asList(expectedRanges));
        Assert.assertEquals("Returns correct length", expectedRanges.length, source.size());
        Assert.assertArrayEquals("Returns correct ranges",expected.get(0), source.get(0));
    }



}