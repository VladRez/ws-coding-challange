package com.williamssonoma.utils;

import com.williamssonoma.entities.Range;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ZipCodesTest {


    @Test
    public void parseValidList() {
        String [] validInput = {"94226,94399", "94133,94200", "94134,94299"};
        Integer[][] validOutput = {{94226,94399}, {94133,94200},{94134,94299}};
        List<Range> expectedRanges = Arrays.stream(validOutput).map(Range::new).collect(Collectors.toList());
        List<Range> actualOutput = ZipCodes.parseCliArgs(validInput);
        Assert.assertArrayEquals("Parses Range Correctly",
                expectedRanges.get(0).getBoundsAsArray(),
                actualOutput.get(0).getBoundsAsArray());
        Assert.assertArrayEquals("Parses Range Correctly",
                expectedRanges.get(1).getBoundsAsArray(),
                actualOutput.get(1).getBoundsAsArray());
        Assert.assertArrayEquals("Parses Range Correctly",
                expectedRanges.get(2).getBoundsAsArray(),
                actualOutput.get(2).getBoundsAsArray());

    }

    @Test
    public void mergeRangesNoOverlapAnyOrder() throws Exception {
        Integer[][] ranges = {{94200,94299}, {94600,94699},{94133,94133}};
        List<Range> listRanges = Arrays.stream(ranges).map(Range::new).collect(Collectors.toList());
        Integer[][] expectedList = {{94133,94133}, {94200,94299}, {94600,94699}};
        List<Range>  expected = Arrays.stream(expectedList).map(Range::new).collect(Collectors.toList());
        List<Range> actual = ZipCodes.mergeRanges(listRanges);

        Assert.assertArrayEquals("Returns correct answer @ index 0",
                expected.get(0).getBoundsAsArray(),
                actual.get(0).getBoundsAsArray()
        );
        Assert.assertArrayEquals("Returns correct answer @ index 1",
                expected.get(1).getBoundsAsArray(),
                actual.get(1).getBoundsAsArray()
        );
        Assert.assertArrayEquals("Returns correct answer @ index 2",
                expected.get(2).getBoundsAsArray(),
                actual.get(2).getBoundsAsArray()
        );

        Assert.assertNotEquals("Sorted correctly",expected.get(0).getBoundsAsArray(), actual.get(2).getBoundsAsArray());
    }

    @Test
    public void mergeRangeSingleOverlap(){
        Integer[][] ranges = {{94133,94133}, {94200,94299}, {94226,94399}};
        List<Range> listRanges = Arrays.stream(ranges).map(Range::new).collect(Collectors.toList());
        Integer[][] expectedList = {{94133,94133}, {94200,94399}};
        List<Range>  expected = Arrays.stream(expectedList).map(Range::new).collect(Collectors.toList());
        List<Range> actual = ZipCodes.mergeRanges(listRanges);

        Integer[] merge = new Integer[] {94200,94399};
        Range mergedRange = new Range(merge);

        Assert.assertArrayEquals("Returns correct ranges",
                expected.get(1).getBoundsAsArray(),
                mergedRange.getBoundsAsArray()
        );
        Assert.assertThat("Sorted ranges correctly",expected.get(0).getBoundsAsArray(),
                IsNot.not(IsEqual.equalTo(mergedRange.getBoundsAsArray())));
        Assert.assertArrayEquals("Merges ranges correctly",
                expected.get(1).getBoundsAsArray(),
                mergedRange.getBoundsAsArray());
    }

    @Test
    public void mergeRangeMultipleOverlap(){
        Integer[][] ranges = {{94133,94200}, {94134,94299}, {94226,94399}};
        List<Range> listRanges = Arrays.stream(ranges).map(Range::new).collect(Collectors.toList());
        List<Range> actual = ZipCodes.mergeRanges(listRanges);
        Integer[] merge = {94133,94399};
        Range mergedRange = new Range(merge);

        Assert.assertEquals("Returns correct length",1, actual.size());
        Assert.assertArrayEquals("Returns correct ranges",
                mergedRange.getBoundsAsArray(),
                actual.get(0).getBoundsAsArray()
        );
    }



}