# Data Structures and Algorithms Practice Sheet

This `Array` branch contains my solutions for the DSA practice sheet covering
pseudocode, flowcharts, time complexity, and space complexity.

## Folder Structure

```text
Array/
├── Section 1 - Pseudocode Problems/
│   ├── PrimeCheck.java
│   ├── NumberDigitReversal.java
│   └── PseudocodeAnswers.txt
│
├── Section 2 - Flowchart Problems/
│   ├── PrimeNumberFlowchart.md
│   └── NumberDigitReversalFlowchart.md
│
├── Section 3 - Time Complexity Problems/
│   ├── LinearSearchTimeComplexity.java
│   ├── IterativeBinarySearchComplexity.java
│   └── MergeSortTimeComplexity.java
│
└── Section 4 - Space Complexity Problems/
    ├── LinearSearchSpaceComplexity.java
    ├── BinarySearchSpaceComplexity.java
    └── MergeSortSpaceComplexity.java
```

## Section Details

### Section 1: Pseudocode Problems

- Prime number checking optimized to test divisors only up to the square root.
- Mathematical digit reversal using remainder and integer division.
- The pseudocode is available in the Java comment blocks and in
  `PseudocodeAnswers.txt` for submission.

### Section 2: Flowchart Problems

- Prime number checker flowchart.
- Number digit reversal flowchart.
- Both flowcharts use Mermaid syntax and can be rendered directly on GitHub.

### Section 3: Time Complexity Problems

- Linear Search best, average, and worst-case analysis.
- Iterative Binary Search range-narrowing steps and `O(log N)` derivation.
- Merge Sort split tree, recurrence, and `O(N log N)` analysis.

### Section 4: Space Complexity Problems

- Iterative Linear Search auxiliary space: `O(1)`.
- Iterative Binary Search auxiliary space: `O(1)`.
- Merge Sort auxiliary space: `O(N)`, including a comparison with in-place
  sorting algorithms.

## Running the Java Programs

Compile and run a file from its corresponding section folder. For example:

```bash
cd "Array/Section 1 - Pseudocode Problems"
javac PrimeCheck.java
java PrimeCheck 61
```
