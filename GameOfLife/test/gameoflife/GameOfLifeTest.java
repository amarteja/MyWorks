package gameoflife;

import java.util.Arrays;

import junit.framework.TestCase;

public class GameOfLifeTest extends TestCase {

	GameOfLife _gameOfLife;
	boolean DEAD = false;
	boolean ALIVE = true;

	protected void setUp()
	{
		_gameOfLife = new GameOfLife();
	}

	public void testCanary(){
		assertTrue(true);
	}

	public void testDeadToDeadwithZeroAliveNeighbors(){
		assertEquals(DEAD, _gameOfLife.isAlive(DEAD, 0));
	}

	public void testDeadToDeadwithOneAliveNeighbors(){
		assertEquals(DEAD, _gameOfLife.isAlive(DEAD, 1));
	}

	public void testDeadToDeadwithTwoAliveNeighbors(){
		assertEquals(DEAD, _gameOfLife.isAlive(DEAD, 2));
	}

	public void testDeadToAlivewithThreeAliveNeighbors(){
		assertEquals(ALIVE, _gameOfLife.isAlive(DEAD, 3));
	}

	public void testDeadToDeadwithFourAliveNeighbors(){
		assertEquals(DEAD, _gameOfLife.isAlive(DEAD, 4));
	}

	public void testDeadToDeadwithFiveAliveNeighbors(){
		assertEquals(DEAD, _gameOfLife.isAlive(DEAD, 5));
	}

	public void testDeadToDeadwithEightAliveNeighbors(){
		assertEquals(DEAD, _gameOfLife.isAlive(DEAD, 8));
	}

	public void testAliveToDeadWithZeroAliveNeighbors(){
		assertEquals(DEAD, _gameOfLife.isAlive(ALIVE, 0));
	}

	public void testAliveToDeadWithOneAliveNeighbor(){
		assertEquals(DEAD, _gameOfLife.isAlive(ALIVE, 1));
	}

	public void testAliveToAlivewithTwoAliveNeighbors(){
		assertEquals(ALIVE, _gameOfLife.isAlive(ALIVE, 2));
	}

	public void testAliveToAlivewithThreeAliveNeighbors(){
		assertEquals(ALIVE, _gameOfLife.isAlive(ALIVE, 3));
	}

	public void testAliveToDeadWithFourAliveNeighbors(){
		assertEquals(DEAD, _gameOfLife.isAlive(ALIVE, 4));
	}

	public void testAliveToDeadWithFiveNeighbors(){
		assertEquals(DEAD, _gameOfLife.isAlive(ALIVE, 5));
	}

	public void testAliveToDeadWithEightNeighbors(){
		assertEquals(DEAD, _gameOfLife.isAlive(ALIVE, 8));
	}

	public boolean[][] makeCellAlive(int positions[][]){
		boolean[][] cells = new boolean[5][5];

		for(int i = 0; i < positions.length; i++ )
		{
			cells[positions[i][0]][positions[i][1]] = ALIVE;
		}

		return cells;
	}

	public void testZeroLiveNeighborsForInitialBoard()
	{
		int positions[][] = {};
		boolean[][] deadUniverse = makeCellAlive(positions);
		assertEquals(0, _gameOfLife.numberOfLiveNeighbors(deadUniverse, 2, 2));
	}

	public void testZeroLiveNeighborsForCellAtCenter()
	{
		int positions[][] = {{0, 1}, {1, 4}, {4 , 4}};
		assertEquals(0, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 2, 2));
	} 

	public void testOneLiveNeighborsWithCellAtCenter()
	{
		int positions[][] = {{1, 1}};
		assertEquals(1, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 2, 2));
	}

	public void testOneLiveNeighborsWithCellAtCenterAlive()
	{
		int positions[][] = {{1,1},{2,2}};
		assertEquals(1, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 2, 2));
	}

	public void testTwoLiveNeighborsWithCellAtCenter()
	{
		int positions[][] = {{3, 3},{1, 2}};
		assertEquals(2, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 2, 2));
	}

	public void testTwoLiveNeighborsWithCellAtCenterAlive()
	{
		int positions[][] = {{3, 3},{1, 2},{2, 2}};
		assertEquals(2, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 2, 2));
	}

	public void testFiveLiveNeighborsWithCellAtCenter()
	{
		int positions[][] = {{1,1}, {1, 2}, {1, 3}, {3, 2}, {3, 3} };
		assertEquals(5, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 2, 2));
	}

	public void testOneAliveNeighborWithCellAtTopLeftCorner()
	{
		int positions[][] = {{1,1}};
		assertEquals(1, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 0, 0));
	}

	public void testTwoAliveNeighborWithCellAtTopLeftCorner()
	{
		int positions[][] = {{1,1},{0,1}};
		assertEquals(2, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 0, 0));
	}

	public void testOneAliveNeighborWithCellAtBottomLeftCorner()
	{
		int positions[][] = {{3, 0}};
		assertEquals(1, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 4, 0));
	}

	public void testOneAliveNeighborWithCellAtTopRightCorner()
	{
		int positions[][] = {{1, 3}};
		assertEquals(1, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 0, 4));
	}

	public void testOneAliveNeighborWithCellAtBottomRightCorner()
	{
		int positions[][] = {{3, 3}};
		assertEquals(1, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 4, 4));
	}

	public void testZeroLiveNeighborWithCellAtMiddleLeft()
	{
		int positions[][] = {{4,4}};
		assertEquals(0, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 2, 0));
	}

	public void testOneLiveNeighborWithCellAtMiddleLeft()
	{
		int positions[][] = {{2,1}};
		assertEquals(1, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 2, 0));
	}

	public void testTwoLiveNeighborWithCellAtMiddleLeft()
	{
		int positions[][] = {{2,1},{3,1}};
		assertEquals(2, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 2, 0));
	}

	public void testZeroLiveNeighborWithCellAtMiddleTop()
	{
		int positions[][] = {{4,4}};
		assertEquals(0, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 0, 3));
	}

	public void testOneLiveNeighborWithCellAtMiddleTop()
	{
		int positions[][] = {{4,4},{1,3}};
		assertEquals(1, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 0, 3));
	}

	public void testTwoLiveNeighborWithCellAtMiddleTop()
	{
		int positions[][] = {{4,4},{1,3},{1,2}};
		assertEquals(2, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 0, 3));
	}

	public void testOneLiveNeighborWithCellAtMiddleRight()
	{
		int positions[][] = {{1, 3}};
		assertEquals(1, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 2, 4));
	}

	public void testOneLiveNeighborWithCellAtMiddleBottom()
	{
		int positions[][] = {{4, 3}};
		assertEquals(1, _gameOfLife.numberOfLiveNeighbors(makeCellAlive(positions), 4, 2));
	}

	public void testNextPatternWithNullGrid()
	{
		int positions[][] = {};
		boolean[][] cells = makeCellAlive(positions);
		boolean[][] expectedGrid = makeCellAlive(positions);

		assertTrue(Arrays.deepEquals(expectedGrid, _gameOfLife.nextGeneration(cells)));
	}

	public void testNextPatternWithGliderPattern()
	{
		int positions[][] = {{1, 2}, {2, 3}, {3, 1}, {3, 2}, {3, 3}};
		boolean[][] cells = makeCellAlive(positions);
		int newPositions[][] = {{2, 1},{2, 3}, {3, 2}, {3, 3}, {4, 2}};
		boolean[][] expectedGrid = makeCellAlive(newPositions);

		assertTrue(Arrays.deepEquals(expectedGrid, _gameOfLife.nextGeneration(cells)));
	}

	public void testNextPatternWithPreviousPattern()
	{
		int positions[][] = {{2, 1},{2, 3},{3, 2}, {3, 3}, {4, 2}};
		boolean[][] cells = makeCellAlive(positions);
		int newPositions[][] = {{2, 3},{3, 1}, {3, 3}, {4, 2}, {4, 3}};
		boolean[][] expectedGrid = makeCellAlive(newPositions);

		assertTrue(Arrays.deepEquals(expectedGrid, _gameOfLife.nextGeneration(cells)));
	}

	public void testNextPatternForStillLifeBoat()
	{
		int positions[][] = {{1, 1}, {1, 2}, {2, 1}, {2, 3}, {3, 2}};
		boolean[][] cells = makeCellAlive(positions);
		int newPositions[][] = {{1, 1}, {1, 2}, {2, 1}, {2, 3}, {3, 2}};
		boolean[][] expectedGrid = makeCellAlive(newPositions);

		assertTrue(Arrays.deepEquals(expectedGrid, _gameOfLife.nextGeneration(cells)));
	}

	public void testNextPatternInALoop()
	{
		int positions[][] = {{1, 2}, {2, 3}, {3, 1}, {3, 2}, {3, 3}};
		boolean[][] cells = makeCellAlive(positions);
		int newPositions[][] = {{2, 2}, {3, 3}, {3, 4}, {4, 2}, {4, 3}};
		boolean[][] expectedGrid = makeCellAlive(newPositions);
		for(int i = 0; i < 3; i++)
		{
			cells = _gameOfLife.nextGeneration(cells);
		}
		assertTrue(Arrays.deepEquals(expectedGrid, cells));
	}

}