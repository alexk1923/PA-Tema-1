import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;




public class Crypto {

	private static final int MOD = 1000000000 + 7;

	private void print_matrix(int[][] mat, int n, int k) {
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= k; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}


	private long getMatches(String original, String subsequence, int string_length,
							int substr_length) {

		long[][] dp = new long[string_length + 1][substr_length + 1];

		/* Get number of different characters in substring */
		long diff = subsequence.chars().distinct().count();

		/* dp[i][j] = occurences for substring from 0 to j - 1 in string from 0 to i - 1 */
		/* first column && first row - special values for algorithm */
		dp[0][0] = 1;
		for (int i = 1; i <= string_length; i++) {
			if (original.charAt(i - 1) == '?') {
				dp[i][0] = (diff * dp[i - 1][0]) % MOD;
			} else {
				dp[i][0] = dp[i - 1][0];
			}
		}

		for (int j = 1; j <= substr_length; j++) {
			dp[0][j] = 0;
		}

		/* Apply formula based on (i-1)th string char compared to (j-1)th substring char */
		for (int i = 1; i <= string_length; i++) {
			for (int j = 1; j <= substr_length; j++) {
				if (original.charAt(i - 1) == '?') {
					/* Multiply existing occurences for each different char from substring */
					long prod = (dp[i - 1][j] * diff) % MOD;
					dp[i][j] = (dp[i - 1][j - 1] + prod) % MOD;
				} else if (original.charAt(i - 1) == subsequence.charAt(j - 1)) {
					/* characters matched on i-1 and j-1, so add at existing occurences 
					 * for each branch
					 */
					dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j]) % MOD;
				} else {
					/* new char in string[i-1] is different from char of substring[j-1]
					 * then same number of occurences
					 */
					dp[i][j] = dp[i - 1][j];
				}
			}
		}

		return (dp[string_length][substr_length] % MOD);
	}


	private void solve() throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader("crypto.in"));
		Scanner s = new Scanner(reader.readLine());
		int string_length = s.nextInt();
		int substr_length = s.nextInt();

		String k = reader.readLine();
		String substring = reader.readLine();

		long res = getMatches(k, substring, string_length, substr_length);

		FileWriter myWriter = new FileWriter("crypto.out");
		myWriter.write(String.valueOf(res) + "\n");
		myWriter.close();
		System.out.println("Rezultat final: \n" + res);

	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		Crypto c = new Crypto();
		c.solve();
	}
}
