package com.williamssonoma.utils;

import com.williamssonoma.entities.Range;
import com.williamssonoma.errors.ZipCodeErrors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ZipCodes {

    private static String zipcodePattern = "^[0-9]{5}$";

    // Main
    public static List<Range> mergeRanges(List<Range> ranges){
        ensureRangeOrder(ranges);
        sortRanges(ranges);
        List<Range> target = new ArrayList<Range>();
        resolve(ranges, target);
        return target;
    }

    // swap bounds in the case of upperBound,lowerBound
    private static void ensureRangeOrder(List<Range> ranges){
        for (int i = 0; i < ranges.size(); i++) {
            Range range = ranges.get(i);
            int lower = range.getLowerBound();
            int upper = range.getUpperBound();
            if (lower > upper){
                range.setUpperBound(lower);
                range.setLowerBound(upper);
            }
        }
    }

    // Logic to join two ranges
    private static Range joinRange(Range range1, Range range2){
        // check if arr2 is inside of arr1
        if (range1.getLowerBound() < range2.getLowerBound() && range1.getUpperBound() > range2.getUpperBound())
            return range1;

        Integer[] bounds = new Integer[] {range1.getLowerBound(), range2.getUpperBound()};
        return new Range (bounds);

    }

    // Sorts Ranges
    private static void sortRanges(List<Range> ranges){
        Collections.sort(ranges, new RangeComparator());
    }

    // Logic to merge ranges
    private static void resolve(List<Range> original, List<Range> solution){

        while (original.size() >1){
            Range item1 = original.remove(original.size() - 1);
            Range item2 = original.remove(original.size() - 1);

            boolean overlapping = isOverlapping(item1, item2);

            if (overlapping){
                Range joinedRange = joinRange(item1, item2);
                original.add(joinedRange);
                continue;
            }

            solution.add(item1);
            original.add(item2);
        }

        if(original.size() > 0) solution.add(original.get(0));
    }

    //Check if ranges overlap
    private static boolean isOverlapping(Range range1, Range range2){
        Integer x1 = range1.getLowerBound();
        Integer x2 = range1.getUpperBound();
        Integer y1 = range2.getLowerBound();
        Integer y2 = range2.getUpperBound();

        Integer upperBound =  Collections.max(Arrays.asList(x2,y2));
        Integer lowerBound = Collections.min(Arrays.asList(x1,y1));
        return (upperBound - lowerBound) <= (x2 - x1) + (y2 - y1);
    }

    // Parses the commandline arguments from string array to Range collection
    public static List<Range> parseCliArgs(String[] strArr)  {

        List<String> args = Arrays.asList(strArr);
        // Validate Inputs
        List<String[]> arraysAsString = args.stream().map(arg->{
            // Validate Input ranges
            try{
                validateInput(arg, "\\d.*,\\d*", "delimiter \",\" not found in range");
            } catch (Exception e){
                System.out.println(e.getMessage());
                System.exit(1);
            }
            String[] valuesAsString = arg.split(",");
            Arrays.stream(valuesAsString).forEach(arr->{
                // Validate zip code number
                try{
                    validateInput(arr, zipcodePattern, "Invalid Zip code");
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
            });
            return valuesAsString;
        }).collect(Collectors.toList());

        // Parse string to Integer
       List<Integer[]> arraysAsInt = arraysAsString.stream().map(array ->Arrays.stream(array)
               .mapToInt(Integer::parseInt)
               .boxed().toArray(Integer[]::new)
        ).collect(Collectors.toList());

       // Return List of Range items
        return arraysAsInt.stream().map(Range::new).collect(Collectors.toList());

    }

    // Validate cli arguments using regex
    private static Boolean validateInput(String value, String pattern, String message) throws ZipCodeErrors{
        boolean valid = Pattern.compile(pattern).matcher(value).matches();
        if (!valid) {
            throw new ZipCodeErrors("Invalid input:\t" + message);
        }
        return true;
    }
}
