import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class Statistics {
	private void readStrings(ArrayList<String> stringsArr, int n, BufferedReader bufferedReader)
							throws IOException {
		while (n > 0) {
			stringsArr.add(bufferedReader.readLine());
			n--;
		}
	}

	/* Calc frequency as permissiveness */
	private double getFrqeuency(String s, Character c) {
		long count = s.chars().filter(ch -> ch == c).count();
		double res = 2 * count - s.length();
		return res;
	}

	private int concatStrings(ArrayList<String> stringsArr, Character c) {
		int count = 0;
		int remainingChars = 0;
		
		while (count < stringsArr.size()) {
			remainingChars += getFrqeuency(stringsArr.get(count), c);
			if (remainingChars <= 0) {
				break;
			}
			count++;
		}
		return count;
	}

	private void solve() throws NumberFormatException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("statistics.in"));
		String s = bufferedReader.readLine();
		Integer n = Integer.parseInt(s);
		ArrayList<String> stringsArr = new ArrayList<>();
		readStrings(stringsArr, n, bufferedReader);

		int countMax = 0;
		for (Character c = 'a'; c <= 'z'; c++) {
			Character currChar = c;
			stringsArr.sort(new Comparator<String>() {
				public int compare(String arg0, String arg1) {
					int compare1 = Double.compare(getFrqeuency(arg1, currChar),
									getFrqeuency(arg0, currChar));
					if (compare1 != 0) {
						return compare1;
					} else {
						return arg0.length() - arg1.length();
					}
				}
				;
			}
			);

			/* If the first string doesn't contain any searched character, ignore it */
			if (!stringsArr.get(0).contains(String.valueOf(currChar))) {
				continue;
			}

			countMax = Math.max(countMax, concatStrings(stringsArr, currChar));

			FileWriter myWriter = new FileWriter("statistics.out");
			myWriter.write(String.valueOf(countMax) + "\n");
			myWriter.close();

		}
	}
	public static void main(String[] args) throws IOException {
		new Statistics().solve();
	}
}
