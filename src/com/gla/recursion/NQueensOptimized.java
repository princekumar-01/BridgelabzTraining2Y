package com.gla.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Problem 3 - N-Queens optimisation and variants.
 *
 * Representation:
 *   queenCol[row] = column of the queen in that row (-1 if empty)
 *   cols[col]                         - a queen already uses this column
 *   diagSum[row + col]                - "/" diagonal
 *   diagDiff[row - col + (n - 1)]     - "\" diagonal
 *
 * Because exactly one queen is placed per row, row conflicts can never occur.
 * All three attack checks are O(1), so a candidate takes constant time to test.
 */
public class NQueensOptimized {

    private final int n;
    private final int[] queenCol;
    private final boolean[] cols;
    private final boolean[] diagSum;
    private final boolean[] diagDiff;
    private final boolean[][] forbidden;

    private List<int[]> allSolutions;
    private int[] firstSolution;
    private boolean stopAtFirst;
    private boolean trace;
    private long nodesExplored;

    public NQueensOptimized(int n) {
        this(n, new boolean[n][n]);
    }

    public NQueensOptimized(int n, boolean[][] forbidden) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        this.n = n;
        this.forbidden = forbidden;
        this.queenCol = new int[n];
        Arrays.fill(queenCol, -1);
        this.cols = new boolean[n];
        this.diagSum = new boolean[2 * n - 1];
        this.diagDiff = new boolean[2 * n - 1];
    }

    /** Finds every solution (standard N-Queens). */
    public List<int[]> findAll() {
        reset();
        stopAtFirst = false;
        allSolutions = new ArrayList<int[]>();
        place(0);
        return allSolutions;
    }

    /** Finds only the first solution and stops immediately. */
    public int[] findFirst() {
        reset();
        stopAtFirst = true;
        place(0);
        return firstSolution;
    }

    /** Same as findFirst(), but prints every choice and backtrack. */
    public int[] findFirstWithTrace() {
        reset();
        stopAtFirst = true;
        trace = true;

        System.out.println("Tracing first solution for n=" + n
                + " (rows 0.." + (n - 1) + ", columns tried left to right)");
        place(0);

        trace = false;
        return firstSolution;
    }

    public long getNodesExplored() {
        return nodesExplored;
    }

    // ------------------------------------------------------------------

    private void reset() {
        Arrays.fill(queenCol, -1);
        Arrays.fill(cols, false);
        Arrays.fill(diagSum, false);
        Arrays.fill(diagDiff, false);
        allSolutions = null;
        firstSolution = null;
        nodesExplored = 0;
    }

    /**
     * @return true only when stopAtFirst is set and a solution has been found
     */
    private boolean place(int row) {
        nodesExplored++;

        if (row == n) {
            int[] solution = queenCol.clone();

            if (allSolutions != null) {
                allSolutions.add(solution);
            }
            if (firstSolution == null) {
                firstSolution = solution;
            }

            if (trace) {
                System.out.println("SOLUTION FOUND: " + Arrays.toString(solution));
                printBoard(solution);
            }

            // With early exit this true value bubbles all the way up.
            return stopAtFirst;
        }

        for (int col = 0; col < n; col++) {
            int sum = row + col;                 // "/" diagonal id
            int diff = row - col + n - 1;        // "\" diagonal id

            // (e) Forbidden squares are skipped before any other work.
            if (forbidden[row][col]) {
                if (trace) {
                    System.out.println("row " + row + ", col " + col
                            + ": skip (forbidden square)");
                }
                continue;
            }
            if (cols[col]) {
                if (trace) {
                    System.out.println("row " + row + ", col " + col
                            + ": skip (column used)");
                }
                continue;
            }
            if (diagSum[sum]) {
                if (trace) {
                    System.out.println("row " + row + ", col " + col
                            + ": skip (/ diagonal used, r+c=" + sum + ")");
                }
                continue;
            }
            if (diagDiff[diff]) {
                if (trace) {
                    System.out.println("row " + row + ", col " + col
                            + ": skip (\\ diagonal used, r-c+" + (n - 1) + "=" + diff + ")");
                }
                continue;
            }

            // Choose.
            queenCol[row] = col;
            cols[col] = true;
            diagSum[sum] = true;
            diagDiff[diff] = true;

            if (trace) {
                System.out.println("row " + row + ", col " + col + ": PLACE queen");
                printState();
            }

            if (place(row + 1)) {
                return true; // early exit: do not explore the remaining columns
            }

            // Un-choose (backtrack), restoring the arrays exactly.
            cols[col] = false;
            diagSum[sum] = false;
            diagDiff[diff] = false;
            queenCol[row] = -1;

            if (trace) {
                System.out.println("row " + row + ", col " + col + ": BACKTRACK (remove queen)");
                printState();
            }
        }

        return false; // no column worked in this row
    }

    private void printState() {
        System.out.println("    queenCol = " + Arrays.toString(queenCol));
        System.out.println("    cols     = " + Arrays.toString(cols));
        System.out.println("    diagSum  = " + Arrays.toString(diagSum) + "   (r+c)");
        System.out.println("    diagDiff = " + Arrays.toString(diagDiff)
                + "   (r-c+" + (n - 1) + ")");
    }

    private void printBoard(int[] solution) {
        for (int r = 0; r < n; r++) {
            StringBuilder line = new StringBuilder();
            for (int c = 0; c < n; c++) {
                line.append(solution[r] == c ? "Q " : ". ");
            }
            System.out.println("    " + line);
        }
    }

    // ------------------------------------------------------------------

    public static void main(String[] args) {
        System.out.println("=== 8-Queens: optimised solver (all solutions) ===");
        NQueensOptimized eight = new NQueensOptimized(8);

        long start = System.nanoTime();
        List<int[]> all = eight.findAll();
        long millis = (System.nanoTime() - start) / 1000000L;

        System.out.println("Solutions = " + all.size() + " (expected 92)");
        System.out.println("Search-tree nodes including root = " + eight.getNodesExplored()
                + " (expected 2057)");
        System.out.println("First solution = " + Arrays.toString(all.get(0)));
        System.out.println("Time = " + millis + " ms");

        System.out.println();
        System.out.println("=== Find only ONE solution (early exit) ===");
        NQueensOptimized one = new NQueensOptimized(8);
        int[] first = one.findFirst();
        System.out.println("Solution = " + Arrays.toString(first));
        System.out.println("Nodes explored = " + one.getNodesExplored());
        one.printBoard(first);

        System.out.println();
        System.out.println("=== (c) Trace of the first solution for N=4 ===");
        NQueensOptimized four = new NQueensOptimized(4);
        int[] fourFirst = four.findFirstWithTrace();
        System.out.println("First N=4 solution = " + Arrays.toString(fourFirst));

        System.out.println();
        System.out.println("=== (e) N=4 with forbidden squares ===");
        NQueensOptimized plain = new NQueensOptimized(4);
        System.out.println("Without forbidden squares: " + plain.findAll().size()
                + " solutions");

        boolean[][] blocked = new boolean[4][4];
        blocked[0][0] = true;
        blocked[0][1] = true;

        NQueensOptimized restricted = new NQueensOptimized(4, blocked);
        List<int[]> restrictedSolutions = restricted.findAll();
        System.out.println("Forbid (0,0) and (0,1): " + restrictedSolutions.size()
                + " solution(s)");
        for (int[] solution : restrictedSolutions) {
            System.out.println("  " + Arrays.toString(solution));
        }

        boolean[][] impossible = new boolean[4][4];
        for (int c = 0; c < 4; c++) {
            impossible[2][c] = true; // whole row 2 is forbidden
        }
        NQueensOptimized none = new NQueensOptimized(4, impossible);
        System.out.println("Forbid all of row 2: " + none.findAll().size() + " solutions");
    }
}
