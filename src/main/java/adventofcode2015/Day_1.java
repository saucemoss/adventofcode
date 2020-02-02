package adventofcode2015;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Day_1 {
	static int floor = 0;
	static int counter = 0;

	public static void main(String[] args) throws IOException {
		InputStream is = new FileInputStream("src/main/resources/2015/Day_1_puzzle_input.txt");
		String line = new BufferedReader(new InputStreamReader(is)).readLine();
		is.close();

		line.chars().mapToObj(c -> (char) c).forEach((c) -> {
			counter++;
			if (c.equals('(')) {
				floor++;
			} else {
				if(floor==0) {
					System.out.println(counter);
				}
				floor--;
			}
		});
		System.out.println(floor);
	}
}