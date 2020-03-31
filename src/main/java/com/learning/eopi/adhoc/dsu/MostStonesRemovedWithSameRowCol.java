package com.learning.eopi.adhoc.dsu;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * On a 2D plane, we place stones at some integer coordinate points.  Each coordinate point may have at most one stone.
 * <p>
 * Now, a move consists of removing a stone that shares a column or row with another stone on the grid.
 * <p>
 * What is the largest possible number of moves we can make?
 * <p>
 * <a href="https://leetcode.com/problems/most-stogsnes-removed-with-same-row-or-column/>
 */
public class MostStonesRemovedWithSameRowCol {

    private final Set<Stone> visitedStones = new HashSet<>();
    private final Map<Stone, Integer> stoneGroupMemberCount = new HashMap<>();

    private static final int NUM_ROW = 3;
    private static final int NUM_COL = 3;

    private final int[][] stoneMatrix = new int[NUM_ROW][NUM_COL];

    public int removeStones(int[][] stones) {
        clear();
        if (stones.length == 0) {
            return 0;
        }

        for (int[] stone : stones) {
            stoneMatrix[stone[0]][stone[1]] = 1;
        }
        for (int i = 0; i < NUM_ROW; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                //processStone stones
                if (stoneMatrix[i][j] == 1) {
                    Stone currentStone = new Stone(i, j);
                    processStone(currentStone, null);
                }
            }
        }
        int maxGroupSize = stoneGroupMemberCount.values().stream().mapToInt(i -> i).max().orElse(1);
        return maxGroupSize - 1;
    }

    private void clear() {
        visitedStones.clear();
        stoneGroupMemberCount.clear();
        //initialise our array with stones
        for (int i = 0; i < NUM_ROW; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                stoneMatrix[i][j] = 0;
            }
        }
    }

    private void processStone(Stone current, Stone parent) {
        if (visitedStones.contains(current)) {
            return;
        }
        visitedStones.add(current);
        if (parent == null) {
            //you are going to form a new group
            stoneGroupMemberCount.put(current, 1);
        } else {
            //you are coming from parent, update its count
            stoneGroupMemberCount.put(parent, stoneGroupMemberCount.get(parent) + 1);
        }
        //process each column that you can impact in the current row, except for current stone
        for (int j = 0; j < NUM_COL; j++) {
            if (j != current.col && stoneMatrix[current.row][j] == 1) {
                //this stone needs to be processed with the parent set
                processStone(new Stone(current.row, j), parent == null ? current : parent);
            }
        }
        //process each row that you can impact in the current col, except for current stone
        for (int i = 0; i < NUM_ROW; i++) {
            if (i != current.row && stoneMatrix[i][current.col] == 1) {
                //this stone needs to be processed with the parent set
                processStone(new Stone(current.col, i), parent == null ? current : parent);
            }
        }
    }

    private static class Stone {

        private final int row;
        private final int col;

        Stone(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Stone pair = (Stone) o;
            return row == pair.row &&
                    col == pair.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    public static void main(String[] args) {
        int[][] stonesA = {{0, 0}, {0, 1}, {1, 0}, {1, 2}, {2, 1}, {2, 2}};
        int[][] stonesB = {{0, 0}, {0, 2}, {1, 1}, {2, 0}, {2, 2}};
        int[][] stonesC = {{0, 0}};
        MostStonesRemovedWithSameRowCol obj = new MostStonesRemovedWithSameRowCol();

        System.out.println(obj.removeStones(stonesA));
        System.out.println(obj.removeStones(stonesB));
        System.out.println(obj.removeStones(stonesC));
    }
}
