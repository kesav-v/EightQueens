import java.util.ArrayList;
import java.util.Arrays;

public class EightQueens {

	private boolean[][] board;
	private int[][] locations;
	private int start;
	private int numSolutions;

	public EightQueens(int x, int... coordinates) {
		if (coordinates.length % 2 == 1 || coordinates.length > 2 * x) {
			System.err.println("ERROR: Could not create board");
			System.exit(0);
		}
		start = coordinates.length / 2;
		board = new boolean[x][x];
		locations = new int[x][2];
		for (int i = 0; i < locations.length; i++) {
			locations[i][0] = -1;
			locations[i][1] = -1;
		}
		for (int i = 0; i < coordinates.length; i += 2) {
			board[coordinates[i]][coordinates[i + 1]] = true;
			locations[i][0] = coordinates[i];
			locations[i][1] = coordinates[i + 1];
		}
		for (int i = 0; i < locations.length; i++) {
			int m = locations[i][0];
			int n = locations[i][1];
			if (m < 0) continue;
			for (int j = 0; j < board.length; j++) {
				board[m][j] = true;
				board[j][n] = true;
				if (m + j < board.length && n + j < board.length) board[m + j][n + j] = true;
				if (m + j < board.length && n - j >= 0) board[m + j][n - j] = true;
				if (m - j >= 0 && n + j < board.length) board[m - j][n + j] = true;
				if (m - j >= 0 && n - j >= 0) board[m - j][n - j] = true;
			}
		}
		solve(board, start);
	}

	public static void main(String[] args) {
		for (int i = 3; i < 100; i++) {
			long l = System.nanoTime();
			EightQueens eq = new EightQueens(i);
			long l2 = System.nanoTime();
			System.out.print(eq.solutions() + " solutions in ");
			double seconds = (l2 - l) / 1E9;
			System.out.println(seconds + " seconds.");
		}
	}

	public int solutions() {
		return numSolutions;
	}

	public void solve(boolean[][] b, int row) {
		if (row == b.length) {
			addSolution(b);
			return;
		}
		// print(b);
		for (int i = 0; i < b.length; i++) {
			if (b[row][i]) continue;
			b[row][i] = true;
			ArrayList<Integer[]> changed = new ArrayList<Integer[]>();
			changed.add(new Integer[] {row, i});
			int count = 0;
			locations[row][0] = row;
			locations[row][1] = i;
			for (int j = 0; j < b.length; j++) {
				if (!b[row][j]) {
					b[row][j] = true;
					changed.add(new Integer[] {row, j});
				}
				if (!b[j][i]) {
					b[j][i] = true;
					changed.add(new Integer[] {j, i});
				}
				if (row + j < b.length && i + j < b.length && !b[row + j][i + j]) {
					b[row + j][i + j] = true;
					changed.add(new Integer[] {row + j, i + j});
				}
				if (row + j < b.length && i - j >= 0 && !b[row + j][i - j]) {
					b[row + j][i - j] = true;
					changed.add(new Integer[] {row + j, i - j});
				}
				if (row - j >= 0 && i + j < b.length && !b[row - j][i + j]) {
					b[row - j][i + j] = true;
					changed.add(new Integer[] {row - j, i + j});
				}
				if (row - j >= 0 && i - j >= 0 && !b[row - j][i - j]) {
					b[row - j][i - j] = true;
					changed.add(new Integer[] {row - j, i - j});
				}
			}
			solve(b, row + 1);
			for (Integer[] locs : changed) {
				b[locs[0]][locs[1]] = false;
			}
			locations[row][0] = -1;
			locations[row][1] = -1;
		}
	}

	public void print(boolean[][] bool) {
		for (int i = 0; i < bool.length; i++) {
			for (int j = 0; j < bool.length; j++) {
				boolean b = bool[i][j];
				if (!b) System.out.print("-");
				else {
					if (indexOf(i, j) != -1) System.out.print("Q");
					else System.out.print("*");
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public int indexOf(int x, int y) {
		for (int i = 0; i < locations.length; i++) {
			if (locations[i][0] == x && locations[i][1] == y) return i;
		}
		return -1;
	}

	public void addSolution(boolean[][] b) {
		numSolutions++;
	}

	public boolean allTrue(boolean[][] bool) {
		for (boolean[] bo : bool) {
			for (boolean b : bo) {
				if (!b) return false;
			}
		}
		return true;
	}
}