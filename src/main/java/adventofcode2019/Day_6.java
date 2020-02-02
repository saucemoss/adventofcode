package adventofcode2019;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Day_6 {

	private static BufferedReader bufferedReader;
	static ArrayList<String> orbits = new ArrayList<String>();
	static ArrayList<String> path1 = new ArrayList<String>();
	static ArrayList<String> path2 = new ArrayList<String>();

	public static void main(String[] args) {

		InputStream inputStream;
		try {
			inputStream = new FileInputStream("src/main/resources/2019/Day_6_puzzle_input.txt");
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String in = bufferedReader.readLine();

			while (in != null) {
				orbits.add(in);
				in = bufferedReader.readLine();

			}
			bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int indirectOrbitCount = 0;
		int orbitalChecksum = 0;

		for (int i = 0; i < orbits.size(); i++) {
			String[] orbit = orbits.get(i).split("\\)");

			while (!whatObjectAmIOrbiting(orbit[1]).equals("null")) {
				orbit[1] = whatObjectAmIOrbiting(orbit[1]);
				System.out.println(orbit[1]);
				orbitalChecksum++;
			}

		}

		for (int i = 0; i < orbits.size(); i++) {
			String[] orbit = orbits.get(i).split("\\)");

			if (orbit[1].equals("YOU")) {
				while (!whatObjectAmIOrbiting(orbit[1]).equals("null")) {
					orbit[1] = whatObjectAmIOrbiting(orbit[1]);
					path1.add(orbit[1]);
				}

			}

			if (orbit[1].equals("SAN")) {
				while (!whatObjectAmIOrbiting(orbit[1]).equals("null")) {
					orbit[1] = whatObjectAmIOrbiting(orbit[1]);
					path2.add(orbit[1]);
				}
			}

		}

		String COM = "";
		boolean foundCommonObj = false;
		for (int i = 0; i < path1.size(); i++) {
			for (int j = 0; j < path2.size(); j++) {
				if (path1.get(i).equals(path2.get(j))) {
					COM = path1.get(i);
					foundCommonObj = true;
					break;
				}
				if (foundCommonObj)
					break;
			}
		}

		for (int i = 0; i < orbits.size(); i++) {
			String[] orbit = orbits.get(i).split("\\)");

			if (orbit[1].equals("YOU")) {
				while (!whatObjectAmIOrbiting(orbit[1]).equals(COM)) {
					orbit[1] = whatObjectAmIOrbiting(orbit[1]);
					path1.add(orbit[1]);
					indirectOrbitCount++;
				}

			}

			if (orbit[1].equals("SAN")) {
				while (!whatObjectAmIOrbiting(orbit[1]).equals(COM)) {
					orbit[1] = whatObjectAmIOrbiting(orbit[1]);
					path2.add(orbit[1]);
					indirectOrbitCount++;
				}
			}
		}

		System.out.println("The total number of direct and indirect orbits is " + orbitalChecksum
				+ ". The minimum number of orbital transfers required to move from the object YOU are orbiting to the object SAN is orbiting is "
				+ indirectOrbitCount);

	}

	public static String whatObjectAmIOrbiting(String in) {
		String out = "";
		boolean found = false;
		for (int i = 0; i < orbits.size(); i++) {
			String[] orbit = orbits.get(i).split("\\)");
			if (in.equals(orbit[1])) {
				out = orbit[0];
				found = true;
			} else if (!found) {
				out = "null";
			}

		}
		return out;
	}

}
