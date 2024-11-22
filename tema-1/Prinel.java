import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Prinel {
	private void readInitArrays(ArrayList<Integer> target, ArrayList<Integer> points, int n,
								Scanner s) throws IOException {
		int k = n;
		s.nextLine();
		while (k > 0) {
			target.add(s.nextInt());
			k--;
		}
		k = n;
		while (k > 0) {
			points.add(s.nextInt());
			k--;
		}
	}



	private void getNoOperations(int[] dp_operations, int target) {
		/* Start from 1 */
		dp_operations[1] = 0;

		/* Default number of operations for each number is itself (case when we just
		 * add 1 each step)
		 */
		for (int i = 2; i <= target; i++) {
			dp_operations[i] = i;
		}

		for (int i = 1; i <= target; i++) {

			int sol_aux = dp_operations[i] + 1;
			for (int div = 1; i + div <= target && div <= Math.sqrt(i); div++) {
				if (i % div == 0) {
					dp_operations[i + div] = Math.min(dp_operations[i + div], sol_aux);
					/* Check for divisors greater than target root */
					if (i + i / div <= target) {
						dp_operations[i + i / div] = Math.min(dp_operations[i + i / div], sol_aux);
					}
				}
			}
		}

	}

	private void printMatrix(int[][] mat, int n, int k) {
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= k; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}

	private void solve() throws NumberFormatException, IOException {
		/* Read input */
		Scanner s = new Scanner(new File("prinel.in"));
		final int n = s.nextInt();
		final int k = s.nextInt();

		/* Store something on index 0 */
		ArrayList<Integer> target = new ArrayList<>();
		target.add(-1);
		ArrayList<Integer> points = new ArrayList<>();
		points.add(-1);
		readInitArrays(target, points, n, s);

		/* Check if maximum cost is 0 */
		if (k == 0) {
			for (int i = 1; i <= n; i++) {
				if (target.get(i) != 1) {
					FileWriter myWriter = new FileWriter("prinel.out");
					myWriter.write(String.valueOf(0) + "\n");
					myWriter.close();
					return;
				}
			}
		}

		/* Get max target from the input */
		Integer max_target = Collections.max(target);
		int[] dp_operations = new int[max_target +  1];


		/* Get no of operations for any number between 1 and 10000 */
		getNoOperations(dp_operations, max_target);

		/* Get sum of all operations cost and check if it is lower than k */
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			sum += dp_operations[target.get(i)];
		}


		if (sum < k) {
			int pointsSum = 0;
			for (int i = 1; i <= n; i++) {
				pointsSum += points.get(i);
			}
			FileWriter myWriter = new FileWriter("prinel.out");
			myWriter.write(String.valueOf(pointsSum) + "\n");
			myWriter.close();
			return;
		}

		/* Use rucksack problem */
		int[][] dp = new int[2][k + 1];
		for (int op = 0; op <= k; op++) {
			dp[0][op] = 0;
		}

		int lastIndex = -1;
		for (int i = 1; i <= n; i++) {
			if (i % 2 == 0) {
				lastIndex = 1;
			} else {
				lastIndex = 0;
			}

			for (int op = 0; op <= k; op++) {
				/* Don't use ith number, get solution from previous step */
				dp[1 - lastIndex][op] = dp[lastIndex][op];
				/* If number of operations until we get target is lower 
				 * than current max operations 
				 */
				if (op - dp_operations[target.get(i)] >= 0) {
					int sol_aux = dp[lastIndex][op - dp_operations[target.get(i)]] + points.get(i);
					/* Get max between points obtained by using or not using this target */
					dp[1 - lastIndex][op] = Math.max(dp[1 - lastIndex][op], sol_aux);
				}
			}
		}

		FileWriter myWriter = new FileWriter("prinel.out");
		myWriter.write(String.valueOf(dp[1 - lastIndex][k]) + "\n");
		myWriter.close();

	}
	public static void main(String[] args) throws IOException {
		new Prinel().solve();
	}
}
