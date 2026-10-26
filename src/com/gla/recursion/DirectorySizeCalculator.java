package com.gla.recursion;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

/**
 * Problem 1 - Understanding Recursion Mechanics.
 *
 * Models the directory tree from the assignment in memory so that the call
 * trace and the totals are deterministic (380 KB).
 *
 * Recursive idea:
 *   - base case  : a file is a leaf, return its size
 *   - base case  : a directory already on the current path is a symlink cycle
 *   - recursive  : a directory is the sum of totalSize(child)
 *
 * The iterative version uses an explicit stack and a visited set.
 */
public class DirectorySizeCalculator {

    // ------------------------------------------------------------------
    // A very small in-memory file system
    // ------------------------------------------------------------------

    private static abstract class Node {
        final String name;

        Node(String name) {
            this.name = name;
        }
    }

    private static final class FileNode extends Node {
        final long sizeKB;

        FileNode(String name, long sizeKB) {
            super(name);
            this.sizeKB = sizeKB;
        }
    }

    private static final class DirNode extends Node {
        final List<Node> children = new ArrayList<Node>();

        DirNode(String name) {
            super(name);
        }

        DirNode add(Node child) {
            children.add(child);
            return this;
        }
    }

    /** A symbolic link. A link to an ancestor creates a cycle. */
    private static final class SymlinkNode extends Node {
        final Node target;

        SymlinkNode(String name, Node target) {
            super(name);
            this.target = target;
        }
    }

    // ------------------------------------------------------------------
    // (a) Recursive solution with a call trace
    // ------------------------------------------------------------------

    /**
     * @param path the directories on the current call-stack path (cycle guard)
     */
    static long totalSize(Node node, Set<Node> path, int depth, boolean trace) {
        String pad = spaces(depth);
        if (trace) {
            System.out.println(pad + "-> enter " + label(node));
        }

        // BASE CASE 1: a file is a leaf, so the recursion stops here.
        if (node instanceof FileNode) {
            long size = ((FileNode) node).sizeKB;
            if (trace) {
                System.out.println(pad + "   base case (file): return " + size + " KB");
            }
            return size;
        }

        // BASE CASE 2: we re-entered a directory that is already on this path.
        // Without this, a symbolic-link cycle would recurse forever.
        if (!path.add(node)) {
            if (trace) {
                System.out.println(pad + "   base case (cycle): " + node.name
                        + " already on path -> return 0");
            }
            return 0;
        }

        // RECURSIVE CASE: a directory (or a symlink to one) is the sum of its children.
        List<Node> children;
        if (node instanceof SymlinkNode) {
            children = Collections.singletonList(((SymlinkNode) node).target);
        } else {
            children = ((DirNode) node).children;
        }

        long total = 0;
        for (Node child : children) {
            total += totalSize(child, path, depth + 1, trace);
        }

        path.remove(node); // undo the choice while the stack unwinds

        if (trace) {
            System.out.println(pad + "<- return " + total + " KB from " + node.name);
        }
        return total;
    }

    // ------------------------------------------------------------------
    // (d) Iterative solution with an explicit stack
    // ------------------------------------------------------------------

    static long totalSizeIterative(Node root) {
        long total = 0;
        Deque<Node> stack = new ArrayDeque<Node>();
        Set<Node> visited = Collections.newSetFromMap(
                new IdentityHashMap<Node, Boolean>());

        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            if (node instanceof FileNode) {
                total += ((FileNode) node).sizeKB;
                continue;
            }

            // A directory is expanded only once, which also breaks cycles.
            if (!visited.add(node)) {
                continue;
            }

            if (node instanceof SymlinkNode) {
                stack.push(((SymlinkNode) node).target);
            } else {
                for (Node child : ((DirNode) node).children) {
                    stack.push(child);
                }
            }
        }

        return total;
    }

    // ------------------------------------------------------------------
    // Demo trees
    // ------------------------------------------------------------------

    private static DirNode buildProject() {
        DirNode project = new DirNode("project");

        DirNode src = new DirNode("src");
        src.add(new FileNode("main.java", 100));
        src.add(new FileNode("utils.java", 50));

        DirNode docs = new DirNode("docs");
        docs.add(new FileNode("readme.txt", 10));

        DirNode guides = new DirNode("guides");
        guides.add(new FileNode("setup.pdf", 200));
        docs.add(guides);

        project.add(src);
        project.add(docs);
        project.add(new FileNode("config.xml", 20));

        return project;
    }

    private static DirNode buildProjectWithSymlink() {
        DirNode project = buildProject();
        DirNode docs = (DirNode) project.children.get(1);
        docs.add(new SymlinkNode("loop", project)); // docs/loop points back to project
        return project;
    }

    private static String label(Node node) {
        if (node instanceof FileNode) {
            return node.name + " (file, " + ((FileNode) node).sizeKB + " KB)";
        }
        if (node instanceof SymlinkNode) {
            return node.name + " (symlink -> " + ((SymlinkNode) node).target.name + ")";
        }
        return node.name + " (directory)";
    }

    private static String spaces(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("    ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        DirNode project = buildProject();

        System.out.println("=== (a) + (b) Recursive total with the call trace ===");
        Set<Node> path = Collections.newSetFromMap(
                new IdentityHashMap<Node, Boolean>());
        long recursiveTotal = totalSize(project, path, 0, true);
        System.out.println();
        System.out.println("Recursive total = " + recursiveTotal + " KB (expected 380 KB)");

        System.out.println();
        System.out.println("=== (d) Iterative total with an explicit stack ===");
        long iterativeTotal = totalSizeIterative(project);
        System.out.println("Iterative total = " + iterativeTotal + " KB");
        System.out.println("Same as recursion? " + (iterativeTotal == recursiveTotal));

        System.out.println();
        System.out.println("=== (e) Symbolic link that points back to an ancestor ===");
        DirNode looped = buildProjectWithSymlink();
        System.out.println("Walking project/docs/loop -> project (a cycle)...");
        long guarded = totalSize(looped,
                Collections.newSetFromMap(new IdentityHashMap<Node, Boolean>()),
                0, true);
        System.out.println("Cycle-guarded recursive total = " + guarded + " KB");
        System.out.println("Iterative total with visited set = " + totalSizeIterative(looped) + " KB");
        System.out.println("The cycle is skipped, so the total is still 380 KB.");
    }
}
