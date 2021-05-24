// cameron campbell
// advanced java
// occc spring 2021
// sudoku solver program

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

public class SudokuSolver 
{
	
	
	/*
	 * the main method handles the user's input (a file) and passes it to the FileReader for processing
	 * into an array, whether through command line or runtime. The result of FileReader is then passed
	 * into a new array, which in turn is passed to solvePuzzle via the final if-else statement's call
	 * to isSolvedPuzzle. if isSolvedPuzzle returns true, solvePuzzle was able to solve the passed array,
	 * and subsequently displays it for the user. otherwise, the array is deemed unsolvable, and the program
	 * ends.
	 */
	public static void main(String args[]) 
	{
		Scanner sc = new Scanner(System.in);
		String fileName;
		
		if(args.length == 1) 
		{
			fileName = args[0];
		}
		else
		{
			System.out.println("Welcome to Sudoku Solver!");
			System.out.println("Please give the file name of a sudoku puzzle you'd like solved: ");
			fileName = sc.nextLine();
		}
		
		char[][] sudokuPuzzle = FileReader(fileName);
		if(isSolvedPuzzle(sudokuPuzzle)) 
		{
			System.out.println("\nSudoku puzzle solved!\n");
			for(int r = 0; r < 9; r++) 
			{
				for(int c = 0; c < 9; c++) 
				{
					System.out.printf("%4s", sudokuPuzzle[r][c]);
				}
				System.out.println();
			}
		} 
		else 
		{
			System.out.println("Oops! The sudoku puzzle is unsolvable!");
	    	System.exit(0);
		}
	}
	
	
	/*
	 * the FileReader method reads the sudoku file passed to it, creates a double character array to
	 * store the sudoku values, then passes that back to the calling method. if the file name passed
	 * is invalid or the isValidValue method returns false on any character in the array, the program
	 * exits with an error.
	 */
	static char[][] FileReader(String fileName)
	{
		BufferedReader reader;
	    try
	    {
	    	reader = new BufferedReader(new FileReader(fileName));
	    	char [] [] puzzleTable = new char[9][9];
	    	
	    	System.out.println();
    		String line = reader.readLine();
	    	for(int r = 0; r < puzzleTable.length; r++)
	    	{
	    		String strings[] = line.trim().split("\\s+");
	    		for(int c = 0; c < puzzleTable[r].length; c++)
		    	{
		    		if (isValidValue(strings[c].charAt(0))) 
		    		{
		    			puzzleTable[r][c] = strings[c].charAt(0);
			    		System.out.printf("%4s", puzzleTable[r][c]);
		    		}
		    		else 
		    		{
		    			System.out.println("Your file contains an illegal value not supported by the Puzzle Reader.");
		    			throw new Exception();
		    		}
		    	}
	    		System.out.println();
	    		line = reader.readLine();
	    	} // writing of file to vector has ended
	    	
	    	System.out.println("\n\nSudoku read. Solving...\n\n");
	    	
	    	return puzzleTable;
	    }
	    catch(Exception e)
	    {
	    	System.out.println(e.toString());
	    	System.exit(0);
	    }
		return null;
	 }
	
	
	/*
	 * the solvePuzzle method solves the passed array by looking for the first blank space ("-" or "*") in
	 * the puzzle, drops a value in, then compares it to the other values in its row, column, and subsection.
	 * if it's valid within those parameters, it continues into the recursive case. otherwise, it increments to
	 * the next value it can drop in and repeats the process. the recursive case then calls solvePuzzle with the
	 * now updated puzzle, and continues on until every blank space is filled in and registers the puzzle as a
	 * valid whole, returning a final true statement that is not recursive via the isCompletePuzzle method.
	 */
	static boolean solvePuzzle(char[][] passedArray) 
	{
			for(int r = 0; r < 9; r++)
			{
				for(int c = 0; c < 9; c++)
				{
					if (passedArray[r][c] == '-' || passedArray[r][c] == '*')
					{
						for(int i = 1; i <= 9; i++)
						{
							if(isValidPuzzle(passedArray, r, c, i))
							{
								passedArray[r][c] = String.valueOf(i).charAt(0);
								
								if(solvePuzzle(passedArray))
								{
									return true;
								}
								else 
								{

									passedArray[r][c] = '-';
								}
							}
						}
						return false;
					}
				}
			}
			if(isCompletePuzzle(passedArray))
			{
				return true;
			}
			return false;
	}
	
	
	/*
	 * the isCompletePuzzle method checks if there are still any spaces in the passed array that are blank. if there
	 * are, the method returns false. otherwise, true.
	 */
	static boolean isCompletePuzzle(char[][] passedArray) 
	{
		for (int r = 0; r < passedArray.length; r++) 
		{
			for (int c = 0; c < passedArray[r].length; c++) 
			{
				if (passedArray[r][c] == '-' || passedArray[r][c] == '*')
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	/*
	 * the isValidValue method is simply a file input validation method used by the FileReader method to ensure that there
	 * are no illegal values within the constraints of the program. for a sudoku puzzle, illegal values are anything other
	 * than numbers 1 - 9, "-", or "*".
	 */
	static boolean isValidValue(char passedChar) 
	{
		if ((passedChar >= '0' && passedChar <= '9') ||
			           (passedChar == '-' || passedChar == '*'))
		{
			return true;
		}
		return false;
	}
	
	
	/*
	 * the isSolvedPuzzle method checks if the passed array is both complete and valid by solvePuzzle's standards. if it is, 
	 * the method returns true. otherwise, false.
	 */
	static boolean isSolvedPuzzle(char[][] passedArray)
	{
		if (solvePuzzle(passedArray))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	
	/*
	 * The rules of Sudoku:
	 * The integers 1-9 appear in a 9x9 grid. The grid is subdivided into nine 3x3 subgrids.
	 * 
	 * 
	 * The three Sudoku constraints are:
	 * Each digit 1-9 appears in each row, without omissions or duplicates
	 * Each digit 1-9 appears in each column, without omissions or duplicates
	 * Each digit 1-9 appears in each 3x3 subgrid, without omissions or duplicates
	 * 
	 * checks if the current value is valid within the passed array.
	 */
	static boolean isValidPuzzle(char[][] passedArray, int passedRow, int passedColumn, int passedValue) 
	{
		if(isInRow(passedArray, passedRow, passedValue) ||
				isInColumn(passedArray, passedColumn, passedValue) ||
				isInSubsection(passedArray, passedRow, passedColumn, passedValue))
		{
				return false;
		}
		return true;
	}
	
	
	/*
	 * the isInRow method checks if the passed value shares the same row with an
	 * identical value. if it does, the method passes true. otherwise, false.
	 */
	static boolean isInRow(char[][] passedArray, int r, int passedValue)
	{
		for(int c = 0; c < 9; c++) 
		{
			if (!(passedArray[r][c] == '-' || passedArray[r][c] == '*'))
			{
				int charInt = Integer.valueOf(String.valueOf(passedArray[r][c]));
				if(charInt == passedValue) 
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	/*
	 * the isInRow method checks if the passed value shares the same column with an
	 * identical value. if it does, the method passes true. otherwise, false.
	 */
	static boolean isInColumn(char[][] passedArray, int c, int passedValue)
	{
		for(int r = 0; r < 9; r++) 
		{
			
			if (!(passedArray[r][c] == '-' || passedArray[r][c] == '*'))
			{
				int charInt = Integer.valueOf(String.valueOf(passedArray[r][c]));
				if(charInt == passedValue) 
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	/*
	 * the isInRow method checks if the passed value shares the same subsection with an
	 * identical value. if it does, the method passes true. otherwise, false.
	 */
	static boolean isInSubsection(char[][] passedArray, int passedRow, int passedColumn, int passedValue)
	{
		int subRow = passedRow - passedRow % 3;
		int subColumn = passedColumn - passedColumn % 3;
		
		for(int r = subRow; r < subRow + 3; r++) 
		{
			for(int c = subColumn; c < subColumn + 3; c++) 
			{
				
				if (!(passedArray[r][c] == '-' || passedArray[r][c] == '*'))
				{
					int charInt = Integer.valueOf(String.valueOf(passedArray[r][c]));
					if(charInt == passedValue) 
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}
