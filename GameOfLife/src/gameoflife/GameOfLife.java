package gameoflife;

public class GameOfLife {
	public boolean DEAD = false;
	public boolean ALIVE = true;

	public boolean isAlive(boolean alive, int _neighborcount) {
		return _neighborcount == 2 && alive || _neighborcount == 3;	
	}

	public int numberOfLiveNeighbors(boolean[][] cells, int row, int column) {
		int count = 0;

		for(int i = row - 1; i < row + 2; i++)
			for(int j = column - 1; j < column + 2; j++)
				if(isCellAlive(cells, i, j))  
					count++;

		return cells[row][column] ? count - 1 : count;
	}

	public boolean isCellAlive(boolean[][] cells, int row, int column){

		return row >= 0 && column >= 0 && row < cells.length && column < cells[0].length && cells[row][column];
	}

	public boolean[][] nextGeneration(boolean[][] cells) {

		boolean[][] nextGen = new boolean[cells.length][cells[0].length];

		for( int i = 0; i < cells.length; i++)
			for( int j = 0; j < cells[0].length; j++){
				nextGen[i][j] = isAlive(cells[i][j], numberOfLiveNeighbors(cells, i, j));
			}

		return nextGen;
	}

}