package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grid extends JPanel implements ActionListener {
    private int rows;
    private int cols;
    private int cellSize;
    private int[][] grid;  // 2D array representing the grid state

    public Grid(int rows, int cols, int cellSize, boolean randomStart, boolean customStart) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.grid = new int[rows][cols];  // Initialize the grid with default state 0 (empty)
        
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
        if(customStart){
            //ADD YOUR INTIALIZATION HERE
            this.grid[5][5] = 1;
            this.grid[5][6] = 1;
            this.grid[6][5] = 1;
            this.grid[6][6] = 1;
        }else if(randomStart){
            //initialize grid to random values:
            for(int i = 0; i < rows;i++){
                for(int j = 0; j < cols;j++){
                    this.grid[i][j] = (int)(Math.random()*2);
                }
            }
        } else {
            //create a glider for testing
            this.grid[2][0] = 1;
            this.grid[3][1] = 1;
            this.grid[1][2] = 1;
            this.grid[2][2] = 1;
            this.grid[3][2] = 1;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Loop through each cell and paint based on alive or dead
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                
                if (grid[row][col] == 0) {//dead
                    g.setColor(Color.BLACK);
                } else if (grid[row][col] == 1) {
                    // Wall (brown)
                    g.setColor(Color.WHITE); // alive
                } else {
                    throw new Error("Not a valid grid value");
                }
                

                // Draw the cell
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);

                // Draw grid lines (optional)
                g.setColor(Color.BLACK);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }

    //ALL YOUR CODE GOES HERE
    public void nextGeneration() {
        // 1 Create a new temporary array to store the values of the next generation
        int[][] tempGrid = new int[rows][cols];

        // 2 Visit every cell in the new temporary grid. Check the number of neighboring cells, and based on the rules determine whether the cell will be alive or dead.
        // Watch out for edge cases!
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                int numAliveNeighbors = 0;
                for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
                    for (int colOffset = -1; colOffset <= 1; colOffset++) {
                        if (rowOffset == 0 && colOffset == 0) continue;
                        int neighborRow = row + rowOffset;
                        int neighborCol = col + colOffset;
                        // CHECK IN BOUNDS
                        if (neighborRow >= 0 && neighborRow < rows && neighborCol >= 0 && neighborCol < cols) {
                            numAliveNeighbors += this.grid[neighborRow][neighborCol];
                        }
                    }
                }

                if (this.grid[row][col] == 1) { 
                    if (numAliveNeighbors < 2 || numAliveNeighbors > 3) {
                        tempGrid[row][col] = 0; 
                    } else {
                        tempGrid[row][col] = 1; 
                    }
                } else { 
                    if (numAliveNeighbors == 3) {
                        tempGrid[row][col] = 1; 
                    } else {
                        tempGrid[row][col] = 0; 
                    }
                }
            }
        }
        // 3 Copy the values of your temporary grid to the real grid
        this.grid = tempGrid;
        // don't mess with this part
        repaint();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        //don't put code here
    }
}