package advofcode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Day_2 {

	private static BufferedReader bufferedReader;

	public static void main(String[] args) {

		for (int i = 1; i < 100; i++) {
			for (int j = 1; j < 100; j++) {
				if (compute(getIntcode(i, j)) == 19690720) {
					System.out.println("Found 19690720 at noun: " + i + ", and verb: " + j + ". The solution is "
							+ (100 * i + j) + ".");
				}
			}
		}
	}

	private static int[] getIntcode(int noun, int verb) {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("src/main/resources/day_2_puzzle_input.txt");
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String in = bufferedReader.readLine();
			String[] str = in.split(",");
			int[] intcode = new int[str.length];

			for (int a = 0; a < str.length; a++) {
				intcode[a] = Integer.parseInt(str[a]);
			}

			intcode[1] = noun;
			intcode[2] = verb;
			return intcode;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private static int compute(int[] intcode) {

		for (int j = 0; j < intcode.length; j += 4) {

			int opcode = intcode[j];
			int val1 = intcode[intcode[j + 1]];
			int val2 = intcode[intcode[j + 2]];
			int pos = intcode[j + 3];
			if (opcode == 1) {
				intcode[pos] = val1 + val2;
			} else if (opcode == 2) {
				intcode[pos] = val1 * val2;
			} else if (opcode == 99) {
				break;
			}
		}
		return intcode[0];
	}

}
