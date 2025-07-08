package training;

import java.util.*;

class Cell {
	private boolean hasMine;
	private boolean isRevealed;
	private boolean isFlagged;
	private int adjacentMines;
	
	//Constructor, setting all values to false and zero
	public Cell() {
		this.hasMine= false;
		this.isRevealed= false;
		this.isFlagged= false;
		this.adjacentMines= 0;
	}
	
	//Getters to get the values from all booleans and integers
	 public boolean hasMine() {
	        return hasMine;
	    }

	    public boolean isRevealed() {
	        return isRevealed;
	    }

	    public boolean isFlagged() {
	        return isFlagged;
	    }

	    public int getAdjacentMines() {
	        return adjacentMines;
	    }
	    
	    //Setters to modify the cell's data
	    public void setHasMine(boolean hasMine) {
	        this.hasMine = hasMine;
	    }

	    public void setRevealed(boolean isRevealed) {
	        this.isRevealed = isRevealed;
	    }

	    public void setFlagged(boolean isFlagged) {
	        this.isFlagged = isFlagged;
	    }

	    public void setAdjacentMines(int adjacentMines) {
	        this.adjacentMines = adjacentMines;
	    }
}
public class Minesweeper {
	

	public static void main(String[] args) {
		int rows = 10;
		int cols = 10;
		
		Scanner sc=new Scanner(System.in);
		
		int randomminesx,randomminesy;
		int mines= 10;
		int placedmines=0;
		
		
		System.out.println("Welcome to Minesweeper! \n how many rows do you want?!");
		rows=sc.nextInt();
		System.out.println("how many columns do you want?");
		cols=sc.nextInt();
		
		//wincondition setup
				int maxreveal=(rows*cols)-mines; 
		
		System.out.println("How many mines do you want?");
		mines=sc.nextInt();
		if (mines<0 || mines>maxreveal-1)
		{
			while(mines<0 || mines>cols)
			{
				System.out.println("too high or too low number, please reenter");
				mines=sc.nextInt();
			}
		}
		Cell[][] board =new Cell[rows][cols];
		
		
		// Initializing each cell in the board
		for (int i = 0; i < rows; i++) {
		    for (int j = 0; j < cols; j++) {
		        board[i][j] = new Cell(); // Create a new Cell object for each position
		    }
		}
		
		
		while (placedmines<mines) //mines generation
		{
			randomminesx= ( (int)(Math.random()* rows));
			randomminesy= ( (int)(Math.random()* cols));
			if (board[randomminesx][randomminesy].hasMine()==false) 
			{
				board[randomminesx][randomminesy].setHasMine(true);
				placedmines++;
			}
		}
		
		
		
		//test reveal and test flagged
		
		/*board[1][1].setRevealed(true);
		board[3][3].setFlagged(true);*/
		
		//Calculate all numbers
		for (int row = 0; row < rows; row++) {
		    for (int col = 0; col < cols; col++) {
		        calculateAdjacentMines(board, row, col);
		    }
		}
		
		//Full reveal
		/*for (int row = 0; row < rows; row++) {
		    for (int col = 0; col < cols; col++) {
		        board[row][col].setRevealed(true);
		    }
		}*/
		
		//Display board
		//DisplayBoard(board,rows,cols);
		
		
		int choice=0;
		int ycord=0,xcord=0;
		
		DisplayBoard(board,rows,cols);
		
		int run=1;
		
		
		while (run==1){
		System.out.println("Which one do you want to do? Reveal-1 Flag-2");
		
		choice=sc.nextInt();
		
		switch(choice)
		{
		case 1: System.out.println("Which row?");
			ycord=sc.nextInt();
			if (ycord<0 || ycord>rows)
			{
				while(ycord<0 || ycord>rows)
				{
					System.out.println("invalid number, please reenter");
					ycord=sc.nextInt();
				}
			}
			System.out.println("Which col?");
			xcord=sc.nextInt();
			if (xcord<0 || xcord>cols)
			{
				while(xcord<0 || xcord>cols)
				{
					System.out.println("invalid number, please reenter");
					xcord=sc.nextInt();
				}
			}
			RevealCell(board,ycord,xcord);
			break;
			
		case 2:System.out.println("Which row?");
		ycord=sc.nextInt();
		if (ycord<0 || ycord>rows)
		{
			while(ycord<0 || ycord>rows)
			{
				System.out.println("invalid number, please reenter");
				ycord=sc.nextInt();
			}
		}
		System.out.println("Which col?");
		xcord=sc.nextInt();
		if (xcord<0 || xcord>cols)
		{
			while(xcord<0 || xcord>cols)
			{
				System.out.println("invalid number, please reenter");
				xcord=sc.nextInt();
			}
		}
		FlagCell(board,ycord,xcord);
		break;
		}
		//Self check counting flags correctly
		//System.out.println("Correct flags:"+CountCorrectFlags(board,rows,cols));
		
		//Win Game over
		if (CountRevealedCells(board, rows, cols)==maxreveal && CountCorrectFlags(board, rows, cols)==mines)
		{
			System.out.println("Congratulations! \n You have successfully revealed all safe cells and flagged all mines!");
			run=0;
		}
		
		//Lose Game over
		if (CheckMineRevealed(board, rows, cols)==true)
		{
			System.out.println("You have Revealed a mine! \n Game over!");
			run=0;
		}
		BasicSpace();
		DisplayBoard(board,rows,cols);
		
		
		
		
		}
	}
	
	//Reveal cell method
	public static void RevealCell(Cell[][] board,int row,int col) {
		
		
		if (board[row - 1][col - 1].isRevealed()) {
	        return;
	    }
		
		board[row-1][col-1].setRevealed(true);
		
		if (board[row - 1][col - 1].getAdjacentMines() == 0) {
	        RevealSafeZone(board, row - 1, col - 1);
	    }
		
	}
	
	//Count all Revealed cells
	public static int CountRevealedCells(Cell[][] board, int rows, int cols)
	{
		int revealedCells=0;
		for (int x=0;x<rows;x++)
		{
			
			for (int y=0;y<cols;y++)
			{
				if (board[x][y].isRevealed()==true)
				{
					revealedCells++;
					
				}
				
			}
			
		}
		return revealedCells;
	}
	
	//Reveal safezone chain method
	
	public static void RevealSafeZone(Cell[][] board, int row, int col) {
	    // Directions for neighboring cells
	    final int[] DIRECTIONS = {-1, 0, 1};

	    for (int x : DIRECTIONS) //rows
	    { 
	        for (int y : DIRECTIONS) //cols
	        { 
	            // Skip the current cell itself
	            if (x == 0 && y == 0) 
	            {
	                continue;
	            }

	            int neighborRow = row + x;
	            int neighborCol = col + y;

	            // Check if the neighboring cell is within bounds
	            if (isInBounds(board, neighborRow, neighborCol)) 
	            {
	            	
	                // If the neighbor is not revealed and not a mine, reveal it
	                if (!board[neighborRow][neighborCol].isRevealed() && !board[neighborRow][neighborCol].hasMine()) 
	                {
	                	
	                    RevealCell(board, neighborRow + 1, neighborCol + 1); // Recursive call
	                }
	            }
	        }
	    }
	}

	// Helper method to check if the cell is within bounds
	private static boolean isInBounds(Cell[][] board, int row, int col) {
	    return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
	}
	
	
	//Mine Revealed check
	public static boolean CheckMineRevealed(Cell[][] board, int rows, int cols)
	{
		boolean minerevealed=false;
		for (int x=0;x<rows;x++)
		{
			
			for (int y=0;y<cols;y++)
			{
				if (board[x][y].isRevealed()==true && board[x][y].hasMine()==true)
				{
					minerevealed=true;
					
				}
				
			}
			
		}
		return minerevealed;
	}
	
	//Change cell state to flagged
	public static void FlagCell(Cell[][] board,int row,int col) {
		
		if (board[row-1][col-1].isFlagged()==false)
		{
			board[row-1][col-1].setFlagged(true);
		}
		
		else if (board[row-1][col-1].isFlagged()==true)
		{
			board[row-1][col-1].setFlagged(false);
		}
	}
	//just two empty lines
	public static void BasicSpace()
	{
		System.out.println();
		System.out.println();
	}
	
	//Count the correct Flags that has been placed
	public static int CountCorrectFlags(Cell[][] board, int rows, int cols)
	{
		int correctFlags=0;
		for (int x=0;x<rows;x++)
		{
			
			for (int y=0;y<cols;y++)
			{
				if (board[x][y].hasMine()==true && board[x][y].isFlagged()==true)
				{
					correctFlags++;
				}
			}
		}
		return correctFlags;
	}
	
	//Display the current state of the board
	public static void DisplayBoard(Cell[][] board, int rows, int cols) {
		
		for (int x=0;x<rows;x++)
		{
			
			for (int y=0;y<cols;y++)
			{
				//Hidden
				if (board[x][y].isRevealed()==false && board[x][y].isFlagged()==false) 
				{
					System.out.print("H ");
				}
				//Safe and there is no mine around it
				else if (board[x][y].isRevealed()==true && board[x][y].hasMine()==false && board[x][y].getAdjacentMines()==0) 
				{
					System.out.print("S ");
				}
				//Flag mark
				if (board[x][y].isRevealed()==false && board[x][y].isFlagged()==true) 
				{
					System.out.print("F ");
				} 
				//number of adjacent mines
				else if (board[x][y].isRevealed()==true && board[x][y].getAdjacentMines()>0 && board[x][y].hasMine()==false)
				{
					System.out.print(board[x][y].getAdjacentMines()+" ");
				}
				
				
				//Display mines
				else if (board[x][y].isRevealed()==true && board[x][y].hasMine())
				{
					System.out.print("* ");
				}
				
			}
			
			System.out.println();
			
		} 
			
			
			
		}
		//Calculating the value of Adjacentmines for each cell in the board
		public static void calculateAdjacentMines(Cell[][] board, int row, int col) {
	    int adjacentMines = 0;

	    for (int x = -1; x <= 1; x++) { //rows
	    	
	        for (int y = -1; y <= 1; y++) { //columns
	            // Skip the current cell itself
	            if (x == 0 && y == 0) {
	                continue;
	            }

	            int neighborRow = row + x;
	            int neighborCol = col + y;

	            // Boundary checks
	            if (neighborRow >= 0 && neighborRow < board.length && neighborCol >= 0 && neighborCol < board[0].length) {
	            	
	                // Check if the neighboring cell has a mine
	                if (board[neighborRow][neighborCol].hasMine()) {
	                    adjacentMines++;
	                }
	            }
	        }
	    }

	    // Update the current cell's adjacentMines count
	    board[row][col].setAdjacentMines(adjacentMines);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
