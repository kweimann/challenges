# Challenge

One of our monitoring tools records how long various operations within a request take. A problem that we have is that sometimes parts are untimed. We'd like the parts which are not covered by the intervals so that we can fill them in.

### Task

Write a program that receives a list of possibly-overlapping intervals and outputs a list of the intervals not covered by the input intervals. You may also assume that the input is well-formed (e.g. the second is always larger than the first). For example, given input

```
2   6 
9   12 
8   9 
18  21 
4   7 
10  11
```

your program should output

```
7   8 
12  18
```

# Solution

### Description

The algorithm uses a priority queue (heap) to iterate over the input ordered by interval's end. This way it is possible to discard overlapping intervals simply by comparing the end `e` of interval `n-1` with the start `s` of interval `n`. If `e < s` there is a gap between these two intervals i.e. an untimed interval has been found. Finally, the algorithm has to make sure it always operates on the smallest start of an interval seen so far in case there exist two consecutive intervals `n-1`, `n` such that start of `n` is smaller than the start of `n-1`.

### Analysis

##### Complexity

1. building the heap: `O(n)` (source: https://en.wikipedia.org/wiki/Binary_heap#Building_a_heap)
2. iterate over heap: `O(n)`

Complexity: `O(n)`

##### Memory

1. heap: `O(n)`

Memory: `O(n)`

### Discussion

It is possible to reduce the memory footprint by using a sorting algorithm instead of a heap. However, this in return would increase the complexity.