package LifeGame;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameOfLifeAPI {

	public static int checkInput() {
		
		Scanner scan = new Scanner(System.in);
		int input = 0;
		boolean incorrectInput = true;
		
		while(incorrectInput) {	
			try {
				input = scan.nextInt();
				incorrectInput = false;
			} catch (InputMismatchException exception) 
			{ 
			    System.out.println("Integers only, please."); 
			    scan.nextLine();
			} 
		}
		//scan.close();
		return input;

	}

	
		public static void main(String[] args) {
			
			boolean inputting = true;
			
			Scanner scan = new Scanner(System.in);  // Reading from System.in
			System.out.print("Welcome to the Board of Life! \nEnter the number of rows of your desired board: ");
			int row = checkInput();
			System.out.print("Enter the number of columns of your desired board: ");
			int column = checkInput();
			
			System.out.print("Fill in the cells you which to be alive when the board is made");
			GameOfLife game = new GameOfLife(row, column);
			
			while(inputting) {
				System.out.print("\nRow: ");
				row = checkInput() - 1;
				System.out.print("Column: ");
				column = checkInput() - 1;
				game.setCellValue(row, column);
				System.out.print("\nCurrent board\n");
				game.PrintBoard();
				System.out.print("Would you like to enter another value?(Y for yes N for no) \nAnswer:");
				char input = scan.next().charAt(0);
				if(input == 'Y' || input == 'y' ) {
					inputting = true;
				} else {
					inputting = false;
				}
			}
			
			System.out.print("How many cycles do you want to print: ");
			int cycles = checkInput();

			
			System.out.println("Starting board\n");
			game.PrintBoard();
			for(int i=0; i<cycles; i++) {
				game.getNextCycleValue();
				game.swapArrays();
				game.PrintBoard();
			}
			scan.close();
		}
}



class GameOfLife {
	int sizeX, sizeY;
	int[][] board;
	int[][] blankBoard;
	
	
	public void PrintBoard() {
		for(int i=0; i<sizeX; i++) {
			for(int j=0; j<sizeY; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("-------------");
	}
	
	public GameOfLife(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		board = new int[sizeX][sizeY];
		blankBoard = new int[sizeX][sizeY];
	}
	
	public void setCellValue(int row, int column) {
		board[row][column] = 1;
		blankBoard[row][column] = 1;
	}
	
	public int getCellValue(int row, int column) {
		if(row < 0 || row >= sizeX || column < 0 || column >= sizeY) {
			return 0;
		} else {
			return board[row][column];
		}
		
	}
	
	public int getLiveNeighboursCount(int row, int column) {
		int counter = 0;
		for(int i=-1; i<=1; i++) {
			for(int j=-1; j<=1; j++) {
				 counter += getCellValue(row+i, column+j);
			}
		}
		return counter - getCellValue(row, column);
	}
	
	public void getNextCycleValue() {
		for(int i=0; i<sizeX; i++) {
			for(int j=0; j<sizeY; j++) {
				if(getLiveNeighboursCount(i, j) < 2) {
					blankBoard[i][j] = 0;
				} else if( getLiveNeighboursCount(i, j) == 3) {
					blankBoard[i][j] = 1;
				} else if(getLiveNeighboursCount(i, j) > 3) {
					blankBoard[i][j] = 0;
				} else if (getLiveNeighboursCount(i, j) == 2 && getCellValue(i, j) == 1) {
					blankBoard[i][j] = 1;
				} else {
					blankBoard[i][j] = 0;
				}
			}
		}
	}
	
	public void swapArrays() {
		for(int i=0; i<sizeX; i++) {
			for(int j=0; j<sizeY;j++) {
				board[i][j] = blankBoard[i][j];
				blankBoard[i][j] = 0;
			}
		}
	}
}



		

