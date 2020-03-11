package com.williamssonoma.utils;

import com.williamssonoma.errors.ZipCodeErrors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ZipCodes {

    private static String zipcodePattern = "^[0-9]{5}$";

    private static Boolean validateInput(String value, String pattern, String message) throws ZipCodeErrors{
       boolean valid = Pattern.compile(pattern).matcher(value).matches();
        if (!valid) {
            throw new ZipCodeErrors("Invalid input:\t" + message);
        }
        return true;
    }

    public static List<Integer[]> mergeRanges(Integer[][] ranges){
        ensureRangeOrder(ranges);
        sortRanges(ranges);
        List<Integer[]> source = new ArrayList<>(Arrays.asList(ranges));
        List<Integer[]> target = new ArrayList<>();
        resolve(source, target);
        return target;
    }

    private static void ensureRangeOrder(Integer[][] arr){
        for (int i = 0; i < arr.length; i++) {
            Integer [] subarr = arr[i];
            int lower = subarr[0];
            int upper = subarr[1];
            if (lower > upper){
               subarr[1] = lower;
               subarr[0] = upper;
            }
        }
    }
    private static Integer[] joinRange(Integer[] arr1, Integer[] arr2){
        Integer[] arr = new Integer[] {arr1[0], arr2[1]};
        return arr;
    }

    private static void sortRanges(Integer[][] ranges){
        Arrays.sort(ranges, (o1, o2) -> Integer.compare(o2[0], o1[0]));
    }

    private static void resolve(List<Integer[]> original, List<Integer[]> solution){

        while (original.size() >1){
            Integer[] item1 = original.remove(original.size() - 1);
            Integer[] item2 = original.remove(original.size() - 1);

            boolean overlapping = isOverlapping(item1, item2);

            if (overlapping){
                Integer[] joinedRange = joinRange(item1, item2);
                original.add(joinedRange);
                continue;
            }

            solution.add(item1);
            original.add(item2);
        }

        if(original.size() > 0) solution.add(original.get(0));
    }

    private static boolean isOverlapping(Integer[] arr1, Integer[] arr2){
        Integer x1 = arr1[0];
        Integer x2 = arr1[1];
        Integer y1 = arr2[0];
        Integer y2 = arr2[1];

        Integer upperBound =  Collections.max(Arrays.asList(x2,y2));
        Integer lowerBound = Collections.min(Arrays.asList(x1,y1));
        return (upperBound - lowerBound) <= (x2 - x1) + (y2 - y1);
    }

    public static Integer[][] parseCliArgs(String[] strArr)  {

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
        return arraysAsString.stream().map(array -> Arrays.stream(array)
                .mapToInt(Integer::parseInt)
                .boxed().toArray(Integer[]::new)
        ).toArray(Integer[][]::new);
    }
}
