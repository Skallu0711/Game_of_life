/**
 *
 * @Author : S18610
 *
 */

package gol;

public class Automata {

    int width;
    int height;
    int[][] board;

    public Automata(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    // test
//    public void printBoard() {
//        System.out.println("---");
//
//        for (int i = 0; i < height; i++) { // y axis
//            String line = "|";
//
//            for (int j = 0; j < width; j++) { // x axis
//                if (this.board[j][i] == 0) { // alive cell
//                    line += ".";
//                } else {    // dead cell
//                    line += "*";
//                }
//            }
//            line += "|";
//            System.out.println(line);
//        }
//        System.out.println("---\n");
//    }

    public void setAlive(int x, int y) {
        this.board[x][y] = 1;
    }

    public void setDead(int x, int y) {
        this.board[x][y] = 0;
    }

    public int countAliveNeighbours(int x, int y) {
        int counter = 0;

        counter += getState(x - 1,y - 1);
        counter += getState(x,y - 1);
        counter += getState(x + 1,y - 1);

        counter += getState(x - 1, y);
        counter += getState(x + 1, y);

        counter += getState(x - 1,y + 1);
        counter += getState(x,y + 1);
        counter += getState(x + 1,y + 1);

        return counter;
    }

    public int getState(int x, int y) {
        if (x < 0 || x >= width) {
            return 0;
        }

        if (y < 0 || y >= height) {
            return 0;
        }

        return this.board[x][y];
    }

    public void move() {
        int[][] newBoard = new int[width][height];

        for (int i = 0; i < height; i++) { // y axis
            for (int j = 0; j < width; j++) { // x axis
                int aliveNeighbours = countAliveNeighbours(j,i);

                if (getState(j, i) == 1) {

                    if (aliveNeighbours < 2) {
                        newBoard[j][i] = 0;

                    } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        newBoard[j][i] =1;

                    } else if (aliveNeighbours > 3) {
                        newBoard[j][i] = 0;
                    }

                } else {
                    if (aliveNeighbours == 3) {
                        newBoard[j][i] = 1;
                    }
                }
            }
        }

        this.board = newBoard;
    }

    //test
//    public static void main(String[] args) {
//        Automata automata = new Automata(8,5);
//
//        automata.setAlive(2,2);
//        automata.setAlive(3,2);
//        automata.setAlive(4,2);
//
//        automata.printBoard();
//
//        automata.move();
//        automata.printBoard();
//
//        automata.move();
//        automata.printBoard();
//
//        System.out.println("alive neighbours: " + automata.countAliveNeighbours(3,2));
//    }

}
