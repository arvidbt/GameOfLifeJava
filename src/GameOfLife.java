import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;

public class GameOfLife {

    private int rows;
    private int cols;

    public JButton[][] panels;
    public int[][] grid;
    public JFrame gameFrame = new JFrame();
    public JPanel gameBoard = new JPanel();
    public JPanel menuPanel = new JPanel();
    public JButton randomGen = new JButton();
    public JButton customGen = new JButton();
    public JButton clear = new JButton();
    public JButton nextGen = new JButton();

    private final Border line = BorderFactory.createLineBorder(Color.BLACK);

    public static void main(String[] args) {
        new GameOfLife();
    }

    GameOfLife() {
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(1080, 1160);
        gameFrame.setLayout(null);
        gameFrame.setTitle("Conway's Game of Life");
        menuPanel.setBounds(0,0,1080,80);
        gameBoard.setBounds(0,45,1080,1080);
        gameBoard.setBackground(Color.DARK_GRAY);
        menuPanel.setBackground(Color.DARK_GRAY);

        try {
            int xyDim = setSquareDimensions();
            rows = xyDim;
            cols = xyDim;
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(gameBoard, "You must enter a dimension for the gameboard!");
            int xyDim = setSquareDimensions();
            rows = xyDim;
            cols = xyDim;
        }

        panels = new JButton[rows][cols];
        grid = new int[rows][cols];
        gameBoard.setLayout(new GridLayout(rows,cols));

        addButtonsToPanel();
        setButtonColor();
        initBoard();
        setButtonText();
        addActionListeners();

        gameFrame.add(menuPanel);
        gameFrame.setVisible(true);
    }

    private void setButtonColor() {
        randomGen.setBackground(Color.gray);
        customGen.setBackground(Color.gray);
        clear.setBackground(Color.red);
        nextGen.setBackground(Color.green);
    }

    private void addButtonsToPanel() {
        menuPanel.add(randomGen);
        menuPanel.add(customGen);
        menuPanel.add(clear);
        menuPanel.add(nextGen);
    }

    private void setButtonText() {
        randomGen.setText("Generate Random Cell Pattern");
        customGen.setText("Custom Cell Pattern");
        clear.setText("Clear board");
        nextGen.setText("Move to next generation");
    }

    private void addActionListeners() {
        customGen.addActionListener(e->createCustomPattern());
        randomGen.addActionListener(e->createRandomPattern());
        clear.addActionListener(e->clearBoard());
        nextGen.addActionListener(e->nextGeneration());
    }

    private int setSquareDimensions() {
        return Integer.parseInt(JOptionPane.showInputDialog(gameBoard, "Set dimensions for board."));
    }

    private void initBoard() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                panels[r][c] = new JButton();
                panels[r][c].setBorder(line);
                panels[r][c].setBackground(Color.DARK_GRAY);
                gameBoard.add(panels[r][c]);
            }
        }
        gameFrame.add(gameBoard);
    }

    private void clearBoard() {
        for(int r = 0; r < rows; r++) {
            for( int c = 0; c < cols; c++) {
                grid[r][c] = 0;
                panels[r][c].setBackground(Color.DARK_GRAY);

            }
        }
    }

    private void createCustomPattern() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int finalC = c;
                int finalR = r;
                panels[r][c].addActionListener(e -> colorSquare(finalR, finalC));
            }
        }
    }

    private void createRandomPattern() {
        Random rand = new Random();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
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
        if(panels[r][c].getBackground() == Color.WHITE) {
            panels[r][c].setBackground(Color.DARK_GRAY);
            grid[r][c] = 0;
        }
        else {
            panels[r][c].setBackground(Color.WHITE);
            grid[r][c] = 1;
        }
    }

    private void nextGeneration() {
        int[][] nextGen = new int[rows][cols];
        for(int r = 1; r < rows -1; r++) {
            for (int c = 1; c < cols -1; c++) {
                int numNB = 0;

                for (int i = -1; i <=1; i++) {

                    for (int j = -1; j <=1; j++) {
                        numNB += grid[r + i][c + j];
                    }
                }
                numNB -= grid[r][c];
                decideCellFate(nextGen, numNB, r, c);
            }
        }
        updateNextGeneration(nextGen);
    }

    private void decideCellFate(int[][] nextGen, int numNB, int r, int c) {
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

    private void updateNextGeneration(int[][] nextGen) {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(nextGen[i][j] == 0) {
                    panels[i][j].setBackground(Color.BLACK);
                    grid[i][j] = nextGen[i][j];
                }
                else {
                    panels[i][j].setBackground(Color.WHITE);
                    grid[i][j] = nextGen[i][j];
                }
            }
        }
    }
}