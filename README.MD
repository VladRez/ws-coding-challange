# Williams-Sonoma Coding Challenge

## BACKGROUND

Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored as a series of ranges of 5 digit codes. For example if the ranges are:
`[94133,94133] [94200,94299] [94600,94699]`

Then the item can be shipped to zip code 94199, 94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299.
Any item might be restricted based on multiple sets of these ranges obtained from multiple sources.

## PROBLEM

Given a collection of 5-digit ZIP code ranges (each range includes both their upper and lower bounds), provide an algorithm that produces the minimum number of ranges required to represent the same restrictions as the input.


## SOLUTION

### Assumptions

+ Each range will be in the format of `lowerBound,upperBound` 
+ Multiple ranges will not be in the same order.
+ User will use this solution as through cli with arguments format: `lower,upper lower,upper lower,upper`
    + i.e `java -jar buid/libs/app.jar 94200,94299 94600,94699`

### General Approach

1. Parse arguments as `String` array to `Integer` 2D array.
2. Ensure `lowerBound:upperBound` format, then Sort the 2D array by its nested sub-array's lower bound.
3. Create two `List<Integer[]>`:
    + `source`(Original array as list)
    + `target`(Empty list for storing answer)
4. While `source` has more than one item.
    + Remove the next two items from `source`.
        + If items (two `Integer[]`) overlap, merge them, and insert merged item back into `source`. Restart `while` block.
        + If items don't overlap, insert first item into `target`, and second item into `source`. Restart `while` block.
    + When `source` has one item, insert that item into `target`
5. Return `List<Integer[]>` and print output.

#### Runtime Details

Sorting + Adding and Removing from lists: 
`O(n*log(n))` + `O(n)` = `O(n*log(n))`

**References: `Arrays.sort` according to the [docs] 

> This algorithm offers n*log(n) performance on many data sets that cause other quicksorts to degrade to quadratic performance.

### Usage

1. `git clone https://gitlab.mynisum.com/vreznikov/ws-coding-challange.git`
2. `./gradlew build`
3. `java -jar build/libs/ws-coding-challange-1.0-SNAPSHOT.jar 94226,94399 94133,94200 94134,94299`

Output:
```shell script
[94133, 94399]
```
#### Errors

##### For Invalid Zipcodes

`java -jar build/libs/ws-coding-challange-1.0-SNAPSHOT.jar 94226,94399 94133,94200 94134,9429`

```shell script
Invalid input:       Invalid Zip code
```

##### For Invalid Ranges

`java -jar build/libs/ws-coding-challange-1.0-SNAPSHOT.jar 9422694399`

```shell script
Invalid input:       Delimiter "," not found in range
```

<!-- Links -->

[docs]: https://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html#sort%28byte%5B%5D%29