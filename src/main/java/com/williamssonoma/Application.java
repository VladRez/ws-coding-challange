package com.williamssonoma;

import com.williamssonoma.utils.ZipCodes;

import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args){

        Integer[][] zipcodes = ZipCodes.parseList(args);
        List<Integer[]> ranges = ZipCodes.mergeRanges(zipcodes);
        ranges.forEach(l->{
            System.out.println(Arrays.toString(l));
        });
    }
}
