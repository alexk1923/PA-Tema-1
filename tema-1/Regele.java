import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Regele {


    private void printMatrix(int[][] mat, int n, int k) {
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= k; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}

    private int getActivation(int city1, int city2, ArrayList<Integer> coords, int n) {
        int noActivation;
        if(city1 == 1) { 
            if(city1 == 2) {
                noActivation = coords.get(city1 + 1) - coords.get(city1) + coords.get(city1);
            }
    
        } else if(city1 == n) {
            noActivation = coords.get(city1) - coords.get(city1 - 1); 
        } else {
            noActivation = coords.get(city1) - coords.get(city1 - 1) + coords.get(city1 + 1) - coords.get(city1);
        }
        return noActivation;
    }

    private void findNoCities(ArrayList<Integer> coords, int m, int[][] d_cities, int n) {
        int count = 0;
        int noCities = 0;
        int[][] dp = new int[n + 1][n + 1];
		for (int j = 0; j <= n; j++) {
			dp[0][j] = 0;
		}
        System.out.println("m = "+ m);
		int lastIndex = -1;
		for (int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
			// for (int op = 0; op <= m; op++) {
				/* Don't use ith number, get solution from previous step */
				// dp[i][j] = dp[i-1][op];
				/* If number of operations until we get target is lower 
				 *than current max operations 
				 */
				// if (getActivation(i, coords, n) + getActivation(j, coords, n) <= 0) {
					// int sol_aux = dp[i - 1][op - getActivation(i, coords, n)] + getActivation(j, coords, n);
					/* Get max between points obtained by using or not using this target */
					dp[i][j] = dp[i - 1][j] + d_cities[j][j + 1];
				// }
            }
			// }
		}
        System.out.println("Pentru matricea DP, avem asa:");
        printMatrix(dp, n, n);
    }


    private void solve() throws NumberFormatException, IOException {
		/* Read input */
		// BufferedReader reader = new BufferedReader(new FileReader("prinel.in"));
		Scanner s = new Scanner(new File("regele.in"));
		final int n = s.nextInt();
        int dim = n;

        ArrayList<Integer> coords = new ArrayList<>();
		coords.add(-1);

        while(dim > 0) {
            coords.add(s.nextInt());
            dim--;
        }

        final int q = s.nextInt();
        ArrayList<Integer> merchants = new ArrayList<>();
        merchants.add(-1);
        dim = q;
        while(dim > 0) {
            merchants.add(s.nextInt());
            dim--;
        }

        System.out.println("n = " + n);
        System.out.println(coords);
        System.out.println(q);
        System.out.println(merchants);


        int[][] d_cities = new int[n + 1][n + 1];

        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                d_cities[i][j] = Math.abs(coords.get(j) - coords.get(i));
            }
        }

        // printMatrix(dp_cities, n, n);

        for(int i = 1; i <= q; i++) {
            findNoCities(coords, merchants.get(i), d_cities, n);
        }

		FileWriter myWriter = new FileWriter("regele.out");
		myWriter.write(String.valueOf(1) + "\n");
		myWriter.close();
		// System.out.println("Rezultat final: \n" + dp[n][k]);

	}
    
	public static void main(String[] args) throws IOException {
		new Regele().solve();
	}
}
