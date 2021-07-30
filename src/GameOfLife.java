import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class GameOfLife {
    public JButton[][] panels = new JButton[100][100];
    public int[][] grid = new int[100][100];
    JFrame frame = new JFrame();
    JMenuBar menu = new JMenuBar();
    JButton newGen = new JButton();
    Border line = BorderFactory.createLineBorder(Color.BLACK);

    public static void main(String[] args) {
        new GameOfLife();
    }

    GameOfLife() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 1080);
        frame.setLayout(new GridLayout(100, 100));
        createCustomPattern();
        //createRandomPattern();
        nextGeneration();
        frame.setVisible(true);
    }

    private void createCustomPattern() {
        for (int r = 0; r < 100; r++) {
            for (int c = 0; c < 100; c++) {
                panels[r][c] = new JButton();
                panels[r][c].setBorder(line);
                panels[r][c].setBackground(Color.BLACK);
                int finalC = c;
                int finalR = r;

                panels[r][c].addActionListener(e -> colorSquare(finalR, finalC));
                frame.add(panels[r][c]);
            }
        }
    }

    private void createRandomPattern() {
        for (int r = 0; r < 100; r++) {
            for (int c = 0; c < 100; c++) {
                panels[r][c] = new JButton();
                panels[r][c].setBorder(line);
                panels[r][c].setBackground(Color.BLACK);
                frame.add(panels[r][c]);
            }
        }

        Random rand = new Random();
        for (int r = 0; r < 100; r++) {
            for (int c = 0; c < 100; c++) {
                if (rand.nextInt(2) == 0) {
                    panels[r][c].setBackground(Color.BLACK);
                    grid[r][c] = 0;
                }
                else {
                    panels[r][c].setBackground(Color.WHITE);
                    grid[r][c] = 1;
                }
            }
        }
    }

    private void colorSquare(int r, int c) {
        panels[r][c].setBackground(Color.WHITE);
    }

    private void nextGeneration() {
        int[][] nextGen = new int[100][100];
        for(int r = 1; r < 99; r++) {

            for (int c = 1; c < 99; c++) {

                int numNB = 0;

                for (int i = -1; i <=1; i++) {

                    for (int j = -1; j <=1; j++) {
                        numNB += grid[r + i][c + j];
                    }
                }
                numNB -= grid[r][c];

                if((grid[r][c] == 1) && (numNB < 2)) {
                    nextGen[r][c] = 0;
                }

                else if((grid[r][c] == 1) && (numNB > 3)) {
                    nextGen[r][c] = 0;
                }

                else if((grid[r][c] == 0) && (numNB == 3)) {
                    nextGen[r][c] = 1;
                }

                else {
                    nextGen[r][c] = grid[r][c];
                }
            }
        }
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                if(nextGen[i][j] == 0) {
                    panels[i][j].setBackground(Color.BLACK);
                }
                else {
                    panels[i][j].setBackground(Color.GREEN);
                }
            }
        }
    }
}