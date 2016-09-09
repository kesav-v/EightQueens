import java.util.ArrayList;

public class Solver extends Thread {

	private ArrayList<String> solutions;
	private int[][] locations;
	private boolean[][] board;
	private int start;
	private int number;

	public Solver(int num, boolean[][] b, int start) {
		number = num;
		board = b;
		this.start = start;
		locations = new int[b.length][2];
		for (int i = 0; i < locations.length; i++) {
			locations[i][0] = -1;
			locations[i][1] = -1;
		}
		solutions = new ArrayList<String>();
	}

	public void run() {
		solve(board, start);
		// System.out.println("Thread " + number + " finished on size " + board.length);
	}

	public int numSolns() {
		return solutions.size();
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
		String bstr = "";
		for (boolean[] bool : b) {
			for (boolean bo : bool) {
				if (bo) bstr += "1";
				else bstr += "0";
			}
		}
		// System.out.println("Solution found");
		// print(b);
		solutions.add(bstr);
	}
}