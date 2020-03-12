package com.williamssonoma;

import com.williamssonoma.entities.Range;
import com.williamssonoma.utils.ZipCodes;

import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args){

        // Parse args
        List<Range> zipcodes = ZipCodes.parseCliArgs(args);
        // Merge Ranges
        List<Range> ranges = ZipCodes.mergeRanges(zipcodes);
        // Print out ranges
        ranges.forEach(range-> System.out.println(Arrays.toString(range.getBoundsAsArray())));
    }
}
