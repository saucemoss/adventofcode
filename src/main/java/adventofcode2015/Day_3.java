package adventofcode2015;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Day_3 {
	static int sanPosX = 0;
	static int sanPosY = 0;
	static int roboPosX = 0;
	static int roboPosY = 0;

	public static void main(String[] args) throws IOException {
		InputStream is = new FileInputStream("src/main/resources/2015/Day_3_puzzle_input.txt");
		char[] line = new BufferedReader(new InputStreamReader(is)).readLine().toCharArray();
		is.close();

		partOne(line);
		partTwo(line);

	}

	private static void partTwo(char[] line) {
		ArrayList<String> positionsSanta = new ArrayList<String>();
		ArrayList<String> positionsRobo = new ArrayList<String>();

		for (int i = 0; i < line.length; i++) {
			char arrow = line[i];
			if (i % 2 == 0) {
				int[] posXY = move(arrow, sanPosX, sanPosY);
				sanPosX = posXY[0];
				sanPosY = posXY[1];
				positionsSanta.add(posXY[0] + ", " + posXY[1]);
			} else {
				int[] posXY = move(arrow, roboPosX, roboPosY);
				roboPosX = posXY[0];
				roboPosY = posXY[1];
				positionsRobo.add(posXY[0] + ", " + posXY[1]);
			}
		}

		positionsSanta.addAll(positionsRobo);
		System.out.println(positionsSanta.stream().filter(i -> Collections.frequency(positionsSanta, i) >= 1)
				.collect(Collectors.toSet()).size());
	}

	private static void partOne(char[] line) {
		int posX = 0;
		int posY = 0;
		ArrayList<String> positions = new ArrayList<String>();

		for (int i = 0; i < line.length; i++) {
			char arrow = line[i];
			int[] posXY = move(arrow, posX, posY);
			posX = posXY[0];
			posY = posXY[1];
			positions.add(posXY[0] + ", " + posXY[1]);
		}

		System.out.println(positions.stream().filter(i -> Collections.frequency(positions, i) >= 1)
				.collect(Collectors.toSet()).size());
	}

	private static int[] move(char line, int posX, int posY) {
		switch (line) {
		case '^':
			posY--;
			break;
		case '>':
			posX++;
			break;
		case '<':
			posX--;
			break;
		case 'v':
			posY++;
			break;
		}

		int[] posXY = { posX, posY };
		return posXY;
	}
}
