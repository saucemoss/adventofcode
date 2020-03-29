package adventofcode2015;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day_5 {

	static int counterPartOne = 0;
	static int counterPartTwo = 0;

	public static void main(String[] args) throws IOException {
		InputStream is = new FileInputStream("src/main/resources/2015/Day_5_puzzle_input.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = br.readLine();

		while (line != null) {
			partOneFilter(line);
			partTwoFilter(line);
			line = br.readLine();

		}
		is.close();
		System.out.println("Part one: " + counterPartOne + "\nPart Two: " + counterPartTwo);

	}

	private static void partTwoFilter(String line) {

		/*
		 * Part two: It contains a pair of any two letters that appears at least twice
		 * in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not
		 * like aaa (aa, but it overlaps). It contains at least one letter which repeats
		 * with exactly one letter between them, like xyx, abcdefeghi (efe), or even
		 * aaa.
		 */

		boolean pass = false;
		char[] chars = line.toCharArray();
		for (int i = 1; i < chars.length; i++) {
			String doble = chars[i - 1] + "" + chars[i];
			String linetwo = line.replaceFirst(doble, "##");
			if (linetwo.contains(doble)) {
				pass = true;
				break;
			}
		}
		if (pass) {
			for (int i = 1; i < chars.length; i++) {
				if (i + 1 < chars.length) {
					if (chars[i - 1] == chars[i + 1]) {
						counterPartTwo++;
						break;
					}
				}
			}
		}
	}

	private static void partOneFilter(String line) {

		/*
		 * Part one: It contains at least three vowels (aeiou only), like aei, xazegov,
		 * or aeiouaeiouaeiou. It contains at least one letter that appears twice in a
		 * row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd). It does not
		 * contain the strings ab, cd, pq, or xy, even if they are part of one of the
		 * other requirements.
		 */

		boolean pass = false;
		if (line.chars().mapToObj(c -> (char) c).filter(c -> "aeiou".contains(String.valueOf(c))).count() >= 3) {
			char[] chars = line.toCharArray();
			for (int i = 1; i < chars.length; i++) {
				if (chars[i - 1] == chars[i]) {
					pass = true;
				}
			}
			if (!line.contains("ab") && !line.contains("cd") && !line.contains("pq") && !line.contains("xy") && pass) {
				counterPartOne++;
			}
		}
	}

}
