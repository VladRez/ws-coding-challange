package com.williamssonoma.utils;

import com.williamssonoma.entities.Range;

import java.util.Comparator;

public class RangeComparator implements Comparator<Range> {
    @Override
    public int compare(Range o1, Range o2) {
        return o2.getLowerBound().compareTo(o1.getLowerBound());
    }
}
