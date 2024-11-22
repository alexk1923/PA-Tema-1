

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Walsh {

	/* Return a value depending on quarter */
	private int retValueByQuarter(int quarter) {
		if (quarter == 4) {
			return 1;
		} else {
			return 0;
		}
	}

	private int findValueByCoords(int x, int y, int currDim) {
		int mid = currDim >> 1;
		int quarter;
		if (x <= mid && y <= mid) {
			quarter =  1;
		} else if (x > mid && y <= mid) {
			quarter =  2;
		} else if (x <= mid && y > mid) {
			quarter =  3;
		} else {
			quarter =  4;
		}
		
		if (quarter == 0) {
			System.out.println("Error in finding quarter");
		}

		if (mid == 1) {
			return retValueByQuarter(quarter);
		}

		if (quarter == 1) {
			return findValueByCoords(x, y, mid);
		} else if (quarter == 2) {
			int newX = x - mid;
			return findValueByCoords(newX, y, mid);
		} else if (quarter == 3) {
			int newY = y - mid;
			return findValueByCoords(x, newY, mid);
		} else {
			int newX = x - mid;
			int newY = y - mid;
			return 1 - findValueByCoords(newX, newY, mid);
		}
		
		/* For 4th quarter, return 0 if value is 1 and vice-versa */
	}

	private void solve() throws NumberFormatException, IOException {

		Scanner s = new Scanner(new File("walsh.in"));
		int n = s.nextInt();
		int k = s.nextInt();

		FileWriter myWriter = new FileWriter("walsh.out");
		while (k > 0) {
			int x = s.nextInt();
			int y = s.nextInt();
			myWriter.write(String.valueOf(findValueByCoords(x, y, n)) + "\n");
			k--;
		}  
		myWriter.close();

	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		new Walsh().solve();
	}

}