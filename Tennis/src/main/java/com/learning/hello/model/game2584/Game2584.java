package com.learning.hello.model.game2584;
import java.util.Random;
//import java.util.Scanner;

public class Game2584 {
	public Tile[][] board;
//	private Boolean isFull;
//	private Boolean isTargetReached;
	private int score;
	private static final int SIZE = 4;
	private Random random;
	
	public Game2584() {
		reset();
	}

	public void reset() {
		board = new Tile[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				board[i][j] = new Tile();
			}
		}
//		isFull = false;
//		isTargetReached = false;
		random = new Random();
		spawn();
		spawn();
	}

	public void spawn() {
		int row = random.nextInt(SIZE);
		int col = random.nextInt(SIZE);
		while (board[row][col].getValue() != 0) {
			row = random.nextInt(SIZE);
			col = random.nextInt(SIZE);
		}
		
		board[row][col].setValue(1);

	}

	private boolean isConsecutiveFibonacci(int a, int b) {
		return (a == 1 && b == 1) || nextFibonacci(a) == b || nextFibonacci(b) == a;
	}

	private int nextFibonacci(int n) {
		double ratio = (1 + Math.sqrt(5)) / 2;
		return (int) Math.round(n * ratio);
	}

	public String[] getBoard() {
		String[] linearBoard = new String[SIZE * SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				linearBoard[i * SIZE + j] = board[i][j].getValue() == 0  ? "" : board[i][j].toString();
			}
		}
		return linearBoard;
	}

	public int getScore() {
		return score;
	}

//	public boolean getIsFull() {
//		return isFull;
//	}
//
//	public boolean getIsTargetReached() {
//		return isTargetReached;
//	}
	
	public void compress() {
		Tile[][] newBoard = createBoard();
		for (int i = 0; i < SIZE; i++) {
			int posFill = 0;
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j].getValue() != 0) {
					newBoard[i][posFill].setValue(board[i][j].getValue());
					posFill++;
				}
			}
		}
		board = newBoard;
	}
	public void combine() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE - 1; j++) {
				if (board[i][j].getValue() != 0 && board[i][j + 1].getValue() != 0) {
					int a = board[i][j].getValue();
					int b = board[i][j + 1].getValue();
					
					if (isConsecutiveFibonacci(a, b)) {
						board[i][j].setValue(a + b);
						board[i][j + 1].setValue(0);
						score += board[i][j].getValue();
					}
				}
			}
		}
	}
	private Tile[][] createBoard() {
		Tile[][] board = new Tile[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				board[i][j] = new Tile();
			}
		}
		return board;
	}
	private void reverse() {
		Tile[][] newBoard = createBoard();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0;j < SIZE; j++) {
				newBoard[i][j].setValue(board[i][SIZE - 1 - j].getValue());
			}
		}
		board = newBoard;
	}
	
	private void transpose() {
		Tile[][] newBoard = createBoard();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0;j < SIZE; j++) {
				newBoard[i][j].setValue(board[j][i].getValue());
			}
		}
		board = newBoard;
	}
	public void left() {
		compress();
		combine();
		compress();
		spawn();
	}
	public void right() {
		reverse();
		compress();
		combine();
		compress();
		reverse();
		spawn();
	}
	public void up() {
		transpose();
		compress();
		combine();
		compress();
		transpose();
		spawn();
	}
	public void down() {
		transpose();
		reverse();
		compress();
		combine();
		compress();
		reverse();
		transpose();
		spawn();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				sb.append(board[i][j]);
				if (j < SIZE - 1) {
					sb.append(" | ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
//	public static void main(String args[]) {
//		Game2584 game = new Game2584();
//		while (true) {
//			System.out.println(game);
//			Scanner sc = new Scanner(System.in);
//			String command = sc.nextLine();
//
//			switch (command) {
//			case "up":
//				game.up();
//				break;
//			case "down":
//				game.down();
//				break;
//			case "left":
//				game.left();
//				break;
//			case "right":
//				game.right();
//				break; 
//			}
//		}
//	}
}
