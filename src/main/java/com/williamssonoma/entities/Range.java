package com.williamssonoma.entities;

public class Range {
    private Integer lowerBound;
    private Integer upperBound;

    public Range(Integer[] bounds){
        this.lowerBound = bounds[0];
        this.upperBound = bounds[1];
    }

    public Integer getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Integer lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Integer getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Integer upperBound) {
        this.upperBound = upperBound;
    }

    public Integer[] getBoundsAsArray(){
        return new Integer[]{this.lowerBound, this.upperBound};
    }

    @Override
    public String toString() {
        return "Range{" +
                "lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                '}';
    }
}
