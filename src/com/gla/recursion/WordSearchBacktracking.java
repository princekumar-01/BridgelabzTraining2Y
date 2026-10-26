package com.gla.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem 2 - Backtracking Template Application.
 *
 * Word search on a grid. Movement is allowed to the 8 neighbouring cells and a
 * cell may be used only once per word.
 *
 * State        : (row, col, index in word, used[][])
 * Choices      : the 8 neighbours of the current cell
 * Constraints  : inside the grid, not used yet, character matches word[index]
 * Goal         : index == word.length() - 1
 */
public class WordSearchBacktracking {

    // Neighbour order: NW, N, NE, W, E, SW, S, SE.
    // Keeping the order fixed makes the trace reproducible.
    private static final int[] DR = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] DC = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static final String[] DIRECTION = {"NW", "N", "NE", "W", "E", "SW", "S", "SE"};

    private final char[][] grid;
    private final int rows;
    private final int cols;

    private long calls;

    public WordSearchBacktracking(char[][] grid) {
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.grid = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            this.grid[r] = grid[r].clone();
        }
    }

    // ------------------------------------------------------------------
    // (a) Does the word exist? (stops at the first successful path)
    // ------------------------------------------------------------------

    public boolean exists(String word) {
        return exists(word, false);
    }

    public boolean exists(String word, boolean trace) {
        checkWord(word);
        calls = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != word.charAt(0)) {
                    continue;
                }

                boolean[][] used = new boolean[rows][cols];
                List<int[]> path = new ArrayList<int[]>();

                if (search(r, c, 0, word, used, path, null, true, trace)) {
                    if (trace) {
                        System.out.println("FOUND: " + formatPath(path));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // (e) Find every possible path for the word
    // ------------------------------------------------------------------

    public List<String> findAll(String word) {
        checkWord(word);
        calls = 0;

        List<List<int[]>> results = new ArrayList<List<int[]>>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != word.charAt(0)) {
                    continue;
                }

                boolean[][] used = new boolean[rows][cols];
                List<int[]> path = new ArrayList<int[]>();
                search(r, c, 0, word, used, path, results, false, false);
            }
        }

        List<String> formatted = new ArrayList<String>();
        for (List<int[]> path : results) {
            formatted.add(formatPath(path));
        }
        return formatted;
    }

    public long getCalls() {
        return calls;
    }

    // ------------------------------------------------------------------
    // Core backtracking routine
    // ------------------------------------------------------------------

    /**
     * @param stopAtFirst true  -> return as soon as the goal is reached
     *                    false -> record the path and keep searching
     * @return true when stopAtFirst is true and the goal was reached
     */
    private boolean search(int r, int c, int index, String word,
                           boolean[][] used, List<int[]> path,
                           List<List<int[]>> results,
                           boolean stopAtFirst, boolean trace) {

        calls++;
        used[r][c] = true;
        path.add(new int[]{r, c});

        if (trace) {
            System.out.println(spaces(index) + "enter " + grid[r][c] + "(" + r + "," + c
                    + ")  index=" + index + "  path=" + formatPath(path));
        }

        // Goal condition.
        if (index == word.length() - 1) {
            if (trace) {
                System.out.println(spaces(index) + "  GOAL: found \"" + word + "\"");
            }

            if (stopAtFirst) {
                return true; // caller stops; no need to undo anything
            }

            results.add(copy(path));
            // fall through, undo this cell and continue looking for other paths
        } else {
            boolean hadCandidate = false;

            for (int d = 0; d < 8; d++) {
                int nr = r + DR[d];
                int nc = c + DC[d];

                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                    continue;
                }
                if (used[nr][nc]) {
                    continue;
                }
                if (grid[nr][nc] != word.charAt(index + 1)) {
                    continue;
                }

                hadCandidate = true;
                if (trace) {
                    System.out.println(spaces(index) + "  choice " + DIRECTION[d]
                            + " -> " + grid[nr][nc] + "(" + nr + "," + nc + ")");
                }

                if (search(nr, nc, index + 1, word, used, path,
                        results, stopAtFirst, trace)) {
                    return true;
                }
            }

            if (!hadCandidate && trace) {
                System.out.println(spaces(index) + "  dead end: no unused neighbour is '"
                        + word.charAt(index + 1) + "' -> backtrack");
            }
        }

        // Undo the choice: restore the state exactly as it was.
        used[r][c] = false;
        path.remove(path.size() - 1);

        if (trace) {
            System.out.println(spaces(index) + "backtrack: unmark " + grid[r][c]
                    + "(" + r + "," + c + ")");
        }

        return false;
    }

    private static List<int[]> copy(List<int[]> path) {
        List<int[]> snapshot = new ArrayList<int[]>();
        for (int[] cell : path) {
            snapshot.add(new int[]{cell[0], cell[1]});
        }
        return snapshot;
    }

    private String formatPath(List<int[]> path) {
        StringBuilder sb = new StringBuilder();
        for (int[] cell : path) {
            if (sb.length() > 0) {
                sb.append(" -> ");
            }
            sb.append(grid[cell[0]][cell[1]])
              .append('(').append(cell[0]).append(',').append(cell[1]).append(')');
        }
        return sb.toString();
    }

    private static String spaces(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }

    private static void checkWord(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("word must not be empty");
        }
    }

    // ------------------------------------------------------------------

    public static void main(String[] args) {
        char[][] board = {
                {'C', 'A', 'T', 'S'},
                {'O', 'R', 'E', 'A'},
                {'D', 'E', 'A', 'M'},
                {'E', 'L', 'L', 'S'}
        };

        WordSearchBacktracking game = new WordSearchBacktracking(board);

        System.out.println("=== (b) + (c) Search for DREAM with a decision trace ===");
        boolean found = game.exists("DREAM", true);
        System.out.println("DREAM exists? " + found + "  (DFS calls: " + game.getCalls() + ")");

        System.out.println();
        System.out.println("=== (e) All ways to form DREAM ===");
        List<String> paths = game.findAll("DREAM");
        for (int i = 0; i < paths.size(); i++) {
            System.out.println((i + 1) + ". " + paths.get(i));
        }
        System.out.println("Total paths = " + paths.size()
                + "  (DFS calls: " + game.getCalls() + ")");

        System.out.println();
        System.out.println("Sanity checks:");
        System.out.println("CATS exists?   " + game.exists("CATS", false));
        System.out.println("MOUSE exists?  " + game.exists("MOUSE", false));
    }
}
