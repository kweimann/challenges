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

### Analysis

### Discussion