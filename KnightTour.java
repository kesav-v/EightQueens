import java.util.ArrayList;
import java.util.Scanner;

public class KnightTour {

	private int maxDepth = 0;
	ArrayList<String> currentMoves = new ArrayList<String>();
	private int numSolutions = 0;
	
	public KnightTour(int x) {
		boolean[][] board = new boolean[x][x];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = false;
			}
		}
		solve(board, 0, 0, 1);
	}

	public static void main(String[] args) {
		int val = 0;
		try {
			val = Integer.parseInt(args[0]);
		} catch (Exception e) {
			Scanner kb = new Scanner(System.in);
			System.out.print("Enter a board size -> ");
			val = kb.nextInt();
		}
		KnightTour kt = new KnightTour(val);
		System.out.println(kt.getSolutions());
	}

	public int getSolutions() {
		return numSolutions;
	}

	public void solve(boolean[][] board, int x, int y, int depth) {
		String move = (char)('a' + x) + "" + (board.length - y);
		currentMoves.add(move);
		board[x][y] = true;
		if (allTrue(board)) {
			for (int i = 0; i < currentMoves.size(); i++) {
				System.out.print(currentMoves.get(i));
				if (i != currentMoves.size() - 1) System.out.print("-");
			}
			System.exit(0);
		}
		boolean movable = false;
		int[] choices = new int[8];
		for (int i = 0; i < choices.length; i++) {
			choices[i] = i;
		}
		for (int i = 0; i < choices.length; i++) {
			int ind = (int)(Math.random() * choices.length);
			int temp = choices[i];
			choices[i] = choices[ind];
			choices[ind] = temp;
		}
		for (int i = 0; i < choices.length; i++) {
			System.out.print(choices[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < choices.length; i++) {
			switch (choices[i]) {
				case 0:
				if (canMove(board, x + 2, y - 1)) {
					movable = true;
					solve(board, x + 2, y - 1, depth + 1);
					board[x + 2][y - 1] = false;
					currentMoves.remove(currentMoves.size() - 1);
				}
				break;
				case 1:
				if (canMove(board, x + 2, y + 1)) {
					movable = true;
					solve(board, x + 2, y + 1, depth + 1);
					board[x + 2][y + 1] = false;
					currentMoves.remove(currentMoves.size() - 1);
				}
				break;
				case 2:
				if (canMove(board, x - 2, y - 1)) {
					movable = true;
					solve(board, x - 2, y - 1, depth + 1);
					board[x - 2][y - 1] = false;
					currentMoves.remove(currentMoves.size() - 1);
				}
				break;
				case 3:
				if (canMove(board, x - 2, y + 1)) {
					movable = true;
					solve(board, x - 2, y + 1, depth + 1);
					board[x - 2][y + 1] = false;
					currentMoves.remove(currentMoves.size() - 1);
				}
				break;
				case 4:
				if (canMove(board, x + 1, y - 2)) {
					movable = true;
					solve(board, x + 1, y - 2, depth + 1);
					board[x + 1][y - 2] = false;
					currentMoves.remove(currentMoves.size() - 1);
				}
				break;
				case 5:
				if (canMove(board, x + 1, y + 2)) {
					movable = true;
					solve(board, x + 1, y + 2, depth + 1);
					board[x + 1][y + 2] = false;
					currentMoves.remove(currentMoves.size() - 1);
				}
				break;
				case 6:
				if (canMove(board, x - 1, y + 2)) {
					movable = true;
					solve(board, x - 1, y + 2, depth + 1);
					board[x - 1][y + 2] = false;
					currentMoves.remove(currentMoves.size() - 1);
				}
				break;
				case 7:
				if (canMove(board, x - 1, y - 2)) {
					movable = true;
					solve(board, x - 1, y - 2, depth + 1);
					board[x - 1][y - 2] = false;
					currentMoves.remove(currentMoves.size() - 1);
				}
				break;
			}
		}
		// System.out.println("\n");
	}

	public boolean canMove(boolean[][] board, int x, int y) {
		return (x >= 0 && x < board.length && y >= 0 && y < board.length && !board[x][y]);
	}

	public boolean allTrue(boolean[][] board) {
		for (boolean[] bool : board) {
			for (boolean bo : bool) {
				if (!bo) return false;
			}
		}
		return true;
	}
}