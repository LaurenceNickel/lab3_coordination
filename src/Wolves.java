import javax.swing.*;
import java.util.*;

public class Wolves {

    private int numWolves;
    private int numPreys;
    private int visibility;
    private int minCaptured;
    private int min_surround;
    public int[][] grid;
    private int rows, cols;
    private int[] wolfRow = new int[numWolves];
    private int[] wolfCol = new int[numWolves];
    private int[] preyRow = new int[numPreys];
    private int[] preyCol = new int[numPreys];
    private Wolf[] wolves = new Wolf[numWolves];
    private List<Integer> capturedList = new ArrayList<>();
    private Random r = new Random();
    private WolvesUI visuals;
    private long tickcounter = 0;

    public Wolves(int rows, int cols, int numWolves, int numPreys, int visibility, int minCaptured, int min_surround) {
        this.rows = rows;
        this.cols = cols;
        this.numWolves = numWolves;
        this.numPreys = numPreys;
        this.visibility = visibility;
        this.minCaptured = minCaptured;
        this.min_surround = min_surround;
        grid = new int[rows][cols];

        wolfRow = new int[numWolves];
        wolfCol = new int[numWolves];
        preyRow = new int[numPreys];
        preyCol = new int[numPreys];
        wolves = new Wolf[numWolves];

        boolean grid_predefined = true;

        // If the grid has been predefined, we call the appropriate method and add the wolves and prey to the grid.
        if (grid_predefined) {
            List<int[]> wolvesPreys = null;
            if (numWolves == 5) {
                if (numPreys == 15) {
                    wolvesPreys = createGrid5W15P();
                } else if (numPreys == 10) {
                    wolvesPreys = createGrid5W10P();
                } else if (numPreys == 5) {
                    wolvesPreys = createGrid5W5P();
                }
            }
            else if (numWolves == 4) {
                if (numPreys == 15) {
                    wolvesPreys = createGrid4W15P();
                } else if (numPreys == 10) {
                    wolvesPreys = createGrid4W10P();
                } else if (numPreys == 5) {
                    wolvesPreys = createGrid4W5P();
                }
            }
            else if (numWolves == 3) {
                if (numPreys == 15) {
                    wolvesPreys = createGrid3W15P();
                } else if (numPreys == 10) {
                    wolvesPreys = createGrid3W10P();
                } else if (numPreys == 5) {
                    wolvesPreys = createGrid3W5P();
                }
            }
            else {
                System.out.println("Please chose the correct number of wolves and preys!");
                System.exit(0);
            }
            wolfRow = wolvesPreys.get(0);
            wolfCol = wolvesPreys.get(1);
            preyRow = wolvesPreys.get(2);
            preyCol = wolvesPreys.get(3);

            for (int i = 0; i < numWolves; i++) {
                grid[wolfRow[i]][wolfCol[i]] = i * 2 + 1;
            }
            for (int i = 0; i < numPreys; i++) {
                grid[preyRow[i]][preyCol[i]] = i * 2 + 2;
            }
        } else {
            for (int i = 0; i < numWolves; i++) {
                do {
                    wolfRow[i] = r.nextInt(rows);
                    wolfCol[i] = r.nextInt(cols);
                } while (!empty(wolfRow[i], wolfCol[i]));
                grid[wolfRow[i]][wolfCol[i]] = i * 2 + 1;
            }
            for (int i = 0; i < numPreys; i++) {

                int preyR;
                int preyC;
                do {
                    preyR = r.nextInt(rows);
                    preyC = r.nextInt(cols);
                } while (!empty(preyR, preyC)
                        || captured(preyR, preyC));
                preyRow[i] = preyR;
                preyCol[i] = preyC;
                grid[preyR][preyC] = i * 2 + 2;
            }
        }
        initWolves();
    }

    private boolean empty(int row, int col) {
        return (grid[row][col] == 0);
    }

    private void initWolves() {
        // You should put your own wolves in the array here!!
        Wolf[] wolvesPool = new Wolf[5];
        wolvesPool[0] = new PackofWolves();
        wolvesPool[1] = new PackofWolves();
        wolvesPool[2] = new PackofWolves();
        wolvesPool[3] = new PackofWolves();
        wolvesPool[4] = new PackofWolves();

        // Below code will select three random wolves from the pool.
        // Make the pool as large as you want, but not < numWolves
        Set<Integer> generated = new LinkedHashSet<Integer>();
        while (generated.size() < numWolves) {
            Integer next = r.nextInt(wolvesPool.length);
            generated.add(next);
        }

        int i = 0;
        for (Integer index : generated) {
            wolves[i++] = wolvesPool[index];
        }
    }

    public void tick() {
        int[][] moves = new int[numWolves][2];

        int cntr = 0;
        for (int i = 0; i < numPreys; i++) {
            int rowMove, colMove;
            do {
                rowMove = r.nextInt(3) - 1;
                colMove = r.nextInt(3) - 1;
                cntr++;
            } while (!empty(rowWrap(preyRow[i], rowMove), colWrap(preyCol[i], colMove))
                    || (cntr < 100 && captured(rowWrap(preyRow[i], rowMove), colWrap(preyCol[i], colMove))));
            grid[preyRow[i]][preyCol[i]] = 0;
            preyRow[i] = rowWrap(preyRow[i], rowMove);
            preyCol[i] = colWrap(preyCol[i], colMove);
            grid[preyRow[i]][preyCol[i]] = i * 2 + 2;

        }

        // here we ask for the wolves moves; to change the movement style, change the limitMovement variable
        boolean limitMovement = false;

        int[][] safetyGrid;
        if (!limitMovement) {
            // Wolves can move diagonally
            for (int i = 0; i < numWolves; i++) {
                safetyGrid = new int[grid.length][grid[0].length];
                for (int r = 0; r < grid.length; r++)
                    for (int s = 0; s < grid[0].length; s++)
                        safetyGrid[r][s] = grid[r][s];
                moves[i] = wolves[i].moveAll(getWolfViewW(i), getWolfViewP(i));
            }
        } else {
            // Wolves can not move diagonally
            for (int i = 0; i < numWolves; i++) {
                safetyGrid = new int[grid.length][grid[0].length];
                for (int r = 0; r < grid.length; r++)
                    for (int s = 0; s < grid[0].length; s++)
                        safetyGrid[r][s] = grid[r][s];

                int dir = wolves[i].moveLim(getWolfViewW(i), getWolfViewP(i));

                switch (dir) {
                    case 0:
                        moves[i][0] = 0;
                        moves[i][1] = 0;
                        break;
                    case 1:
                        // left 
                        moves[i][0] = -1;
                        moves[i][1] = 0;
                        break;
                    case 2:
                        // down
                        moves[i][0] = 0;
                        moves[i][1] = 1;
                        break;
                    case 3:
                        // right
                        moves[i][0] = 1;
                        moves[i][1] = 0;
                        break;
                    case 4:
                        // up
                        moves[i][0] = 0;
                        moves[i][1] = -1;
                        break;
                }
            }
        }

        // and here we move everybody
        for (int i = 0; i < numWolves; i++) {
            if (empty(rowWrap(wolfRow[i], moves[i][0]), colWrap(wolfCol[i], moves[i][1]))) {
                grid[wolfRow[i]][wolfCol[i]] = 0;
                wolfRow[i] = rowWrap(wolfRow[i], moves[i][0]);
                wolfCol[i] = colWrap(wolfCol[i], moves[i][1]);
                grid[wolfRow[i]][wolfCol[i]] = i * 2 + 1;
            }
        }

        visuals.update();
        tickcounter++;

        for (int i = 0; i < numPreys; i++) { //add new captured to list
            if (capturedList.contains(i))
                continue;
            if (captured(preyRow[i], preyCol[i]))
                capturedList.add(i);
        }

        //check whether enough preys have been captured
        if (capturedList.size() >= minCaptured) {
            JOptionPane.showMessageDialog(null, "Wolves won in " + tickcounter + " steps!!");
            System.out.println("Winners");
            System.exit(0);
        }
    }

    public boolean captured(int r, int c) {
        int count = 0;
        if (grid[rowWrap(r, -1)][colWrap(c, -1)] % 2 == 1) count++;
        if (grid[rowWrap(r, -1)][colWrap(c, 0)] % 2 == 1) count++;
        if (grid[rowWrap(r, -1)][colWrap(c, 1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 0)][colWrap(c, -1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 0)][colWrap(c, 1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 1)][colWrap(c, -1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 1)][colWrap(c, 0)] % 2 == 1) count++;
        if (grid[rowWrap(r, 1)][colWrap(c, 1)] % 2 == 1) count++;
        return (count >= min_surround);
    }

    public int rowWrap(int x, int inc) {
        int tmp = x + inc;
        if (tmp < 0) tmp += rows;
        if (tmp >= rows) tmp -= rows;
        return tmp;
    }

    public int colWrap(int x, int inc) {
        int tmp = x + inc;
        if (tmp < 0) tmp += cols;
        if (tmp >= cols) tmp -= cols;
        return tmp;
    }

    public int getNumbCols() {
        return cols;
    }

    public int getNumbRows() {
        return rows;
    }

    public boolean isWolf(int i, int j) { //Odd numbers are wolves
        return grid[i][j] % 2 == 1;
    }

    public boolean isPrey(int i, int j) { //Even numbers are sheeps
        return grid[i][j] != 0 & grid[i][j] % 2 == 0;
    }

    public void attach(WolvesUI wolvesUI) {
        visuals = wolvesUI;
    }

    public int manhattanDistance(int x0, int y0, int x1, int y1) {
        return Math.abs(x1 - x0) + Math.abs(y1 - y0);
    }

    public List<int[]> getWolfViewW(int wolf) {
        List<int[]> wolves = new ArrayList<>();
        for (int i = 0; i < numWolves; i++) {
            int relX = wolfRow[wolf] - wolfRow[i];
            int relY = wolfCol[wolf] - wolfCol[i];

            int[] agent = new int[]{relX, relY};
            wolves.add(agent);
        }

        return wolves;
    }

    public List<int[]> getWolfViewP(int wolf) {
        List<int[]> preys = new ArrayList<>();
        for (int i = 0; i < numPreys; i++) {
            if (manhattanDistance(wolfRow[wolf], wolfCol[wolf], preyRow[i], preyCol[i]) > visibility) {
                continue;
            }

            int relX = wolfRow[wolf] - preyRow[i];
            int relY = wolfCol[wolf] - preyCol[i];
            int[] agent = new int[]{relX, relY};
            preys.add(agent);
        }

        return preys;
    }

    /**
     * Returns the positions of the 5 wolves and 15 preys.
     *
     * @return The positions of the 5 wolves and 15 preys.
     */
    public List<int[]> createGrid5W15P() {
        List<int[]> positions = new ArrayList<>();
        int[] wolvesRow =  {10, 17, 24, 31, 38};
        int[] wolvesCol = {40, 40, 40, 40, 40};
        int[] preysRow = {10, 17, 24, 31, 38, 10, 17, 24, 31, 38, 10, 17, 24, 31, 38};
        int[] preysCol = {16, 16, 16, 16, 16, 20, 20, 20, 20, 20, 24, 24, 24, 24, 24};
        positions.add(wolvesRow);
        positions.add(wolvesCol);
        positions.add(preysRow);
        positions.add(preysCol);

        return positions;
    }

    /**
     * Returns the positions of the 4 wolves and 15 preys.
     *
     * @return The positions of the 4 wolves and 15 preys.
     */
    public List<int[]> createGrid4W15P() {
        List<int[]> positions = new ArrayList<>();
        int[] wolvesRow =  {10, 17, 24, 31};
        int[] wolvesCol = {40, 40, 40, 40};
        int[] preysRow = {10, 17, 24, 31, 38, 10, 17, 24, 31, 38, 10, 17, 24, 31, 38};
        int[] preysCol = {16, 16, 16, 16, 16, 20, 20, 20, 20, 20, 24, 24, 24, 24, 24};
        positions.add(wolvesRow);
        positions.add(wolvesCol);
        positions.add(preysRow);
        positions.add(preysCol);

        return positions;
    }

    /**
     * Returns the positions of the 3 wolves and 15 preys.
     *
     * @return The positions of the 3 wolves and 15 preys.
     */
    public List<int[]> createGrid3W15P() {
        List<int[]> positions = new ArrayList<>();
        int[] wolvesRow =  {17, 24, 31};
        int[] wolvesCol = {40, 40, 40};
        int[] preysRow = {10, 17, 24, 31, 38, 10, 17, 24, 31, 38, 10, 17, 24, 31, 38};
        int[] preysCol = {16, 16, 16, 16, 16, 20, 20, 20, 20, 20, 24, 24, 24, 24, 24};
        positions.add(wolvesRow);
        positions.add(wolvesCol);
        positions.add(preysRow);
        positions.add(preysCol);

        return positions;
    }

    /**
     * Returns the positions of the 5 wolves and 10 preys.
     *
     * @return The positions of the 5 wolves and 10 preys.
     */
    public List<int[]> createGrid5W10P() {
        List<int[]> positions = new ArrayList<>();
        int[] wolvesRow =  {10, 17, 24, 31, 38};
        int[] wolvesCol = {40, 40, 40, 40, 40};
        int[] preysRow = {24, 31, 38, 10, 17, 24, 31, 38, 10, 17};
        int[] preysCol = {16, 16, 16, 20, 20, 20, 20, 20, 24, 24};
        positions.add(wolvesRow);
        positions.add(wolvesCol);
        positions.add(preysRow);
        positions.add(preysCol);

        return positions;
    }

    /**
     * Returns the positions of the 4 wolves and 10 preys.
     *
     * @return The positions of the 4 wolves and 10 preys.
     */
    public List<int[]> createGrid4W10P() {
        List<int[]> positions = new ArrayList<>();
        int[] wolvesRow =  {10, 17, 24, 31};
        int[] wolvesCol = {40, 40, 40, 40};
        int[] preysRow = {24, 31, 38, 10, 17, 24, 31, 38, 10, 17};
        int[] preysCol = {16, 16, 16, 20, 20, 20, 20, 20, 24, 24};
        positions.add(wolvesRow);
        positions.add(wolvesCol);
        positions.add(preysRow);
        positions.add(preysCol);

        return positions;
    }

    /**
     * Returns the positions of the 3 wolves and 10 preys.
     *
     * @return The positions of the 3 wolves and 10 preys.
     */
    public List<int[]> createGrid3W10P() {
        List<int[]> positions = new ArrayList<>();
        int[] wolvesRow =  {17, 24, 31};
        int[] wolvesCol = {40, 40, 40};
        int[] preysRow = {24, 31, 38, 10, 17, 24, 31, 38, 10, 17};
        int[] preysCol = {16, 16, 16, 20, 20, 20, 20, 20, 24, 24};
        positions.add(wolvesRow);
        positions.add(wolvesCol);
        positions.add(preysRow);
        positions.add(preysCol);

        return positions;
    }

    /**
     * Returns the positions of the 5 wolves and 5 preys.
     *
     * @return The positions of the 5 wolves and 5 preys.
     */
    public List<int[]> createGrid5W5P() {
        List<int[]> positions = new ArrayList<>();
        int[] wolvesRow =  {10, 17, 24, 31, 38};
        int[] wolvesCol = {40, 40, 40, 40, 40};
        int[] preysRow = {10, 17, 24, 31, 38};
        int[] preysCol = {20, 20, 20, 20, 20};
        positions.add(wolvesRow);
        positions.add(wolvesCol);
        positions.add(preysRow);
        positions.add(preysCol);

        return positions;
    }

    /**
     * Returns the positions of the 4 wolves and 5 preys.
     *
     * @return The positions of the 4 wolves and 5 preys.
     */
    public List<int[]> createGrid4W5P() {
        List<int[]> positions = new ArrayList<>();
        int[] wolvesRow =  {10, 17, 24, 31};
        int[] wolvesCol = {40, 40, 40, 40};
        int[] preysRow = {10, 17, 24, 31, 38};
        int[] preysCol = {20, 20, 20, 20, 20};
        positions.add(wolvesRow);
        positions.add(wolvesCol);
        positions.add(preysRow);
        positions.add(preysCol);

        return positions;
    }

    /**
     * Returns the positions of the 3 wolves and 5 preys.
     *
     * @return The positions of the 3 wolves and 5 preys.
     */
    public List<int[]> createGrid3W5P() {
        List<int[]> positions = new ArrayList<>();
        int[] wolvesRow =  {17, 24, 31};
        int[] wolvesCol = {40, 40, 40};
        int[] preysRow = {10, 17, 24, 31, 38};
        int[] preysCol = {20, 20, 20, 20, 20};
        positions.add(wolvesRow);
        positions.add(wolvesCol);
        positions.add(preysRow);
        positions.add(preysCol);

        return positions;
    }
}