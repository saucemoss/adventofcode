package advofcode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Day_3 extends JPanel {

	static int boardWidth = 50000;
	static int boardHeight = 50000;
	static String board[][] = new String[boardHeight][boardWidth];
	static int startPosX = boardWidth / 2;
	static int startPosY = boardHeight / 2;
	private static BufferedReader bufferedReader;

	public static void main(String[] args) {

		// example paths
		String path1[] = { "R8", "U5", "L5", "D3" };
		String path2[] = { "U7", "R6", "D4", "L4" };

		String path3[] = { "R75", "D30", "R83", "U83", "L12", "D49", "R71", "U7", "L72" };
		String path4[] = { "U62", "R66", "U55", "R34", "D71", "R55", "D58", "R83" };
		
		String path5[] = { "R98", "U47", "R26", "D63", "R33", "U87", "L62", "D20", "R33", "U53", "R51" };
		String path6[] = { "U98", "R91", "D20", "R16", "D67", "R40", "U7", "R15", "U6", "R7" };

		//"production" paths
		String p1 = null;
		String p2 = null;
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("src/main/resources/day_3_puzzle_paths.txt");
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			p1 = bufferedReader.readLine();
			p2 = bufferedReader.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] path7 = p1.split(",");
		String[] path8 = p2.split(",");

		// compute
		translatePath(path7, board);
		translatePath(path8, board);
		// printBoard(board, null);
		getXposDistances(board);

		// graphics
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, boardHeight, boardWidth);
		window.getContentPane().add(new Day_3());
		window.setVisible(true);
		

	}

	private static void getXposDistances(String[][] board2) {
		ArrayList<Integer> xpos = new ArrayList<Integer>();
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (board[i][j] == "[X]") {
					int x = Math.abs(startPosY - j) + Math.abs(startPosX - i);
					xpos.add(x);
					System.out.println("x = " + j + ", y = " + i + ". Total distance from center is: " + x);
				}
			}
		}

		Collections.sort(xpos);
		if (xpos.get(0) == 0) {
			System.out.println(
					"The closest point of intersection from the center is " + xpos.get(1) + " grid tiles away.");
		} else {
			System.out.println(
					"The closest point of intersection from the center is " + xpos.get(0) + " grid tiles away.");
		}
	}

	private static String[][] translatePath(String[] path, String[][] board) {

		int currentPosX = startPosX;
		int currentPosY = startPosY;

		System.out.println("Instructions for path are: ");

		for (int i = 0; i < path.length; i++) {
			if (path[i].charAt(0) == 'R') {
				String sc = path[i].substring(1);
				int stepCount = Integer.parseInt(sc);
				System.out.println("go right " + stepCount + " times, then");

				// input to board coordinates
				for (int j = 0; j <= stepCount; j++) {
					if (board[currentPosY][currentPosX + j] == "[#]") {
						board[currentPosY][currentPosX + j] = "[X]";
					} else {
						board[currentPosY][currentPosX + j] = "[.]";
					}
				}
				currentPosX += stepCount;

			} else if (path[i].charAt(0) == 'L') {
				String sc = path[i].substring(1);
				int stepCount = Integer.parseInt(sc);
				System.out.println("go left " + stepCount + " times, then");

				// input to board coordinates
				for (int j = 0; j <= stepCount; j++) {
					if (board[currentPosY][currentPosX - j] == "[#]") {
						board[currentPosY][currentPosX - j] = "[X]";
					} else {
						board[currentPosY][currentPosX - j] = "[.]";
					}
				}
				currentPosX -= stepCount;

			} else if (path[i].charAt(0) == 'U') {
				String sc = path[i].substring(1);
				int stepCount = Integer.parseInt(sc);
				System.out.println("go up " + stepCount + " times, then");

				// input to board coordinates
				for (int j = 0; j <= stepCount; j++) {
					if (board[currentPosY - j][currentPosX] == "[#]") {
						board[currentPosY - j][currentPosX] = "[X]";
					} else {
						board[currentPosY - j][currentPosX] = "[.]";
					}
				}
				currentPosY -= stepCount;
			} else if (path[i].charAt(0) == 'D') {
				String sc = path[i].substring(1);
				int stepCount = Integer.parseInt(sc);
				System.out.println("go down " + stepCount + " times, then");

				// input to board coordinates
				for (int j = 0; j <= stepCount; j++) {
					if (board[currentPosY + j][currentPosX] == "[#]") {
						board[currentPosY + j][currentPosX] = "[X]";
					} else {
						board[currentPosY + j][currentPosX] = "[.]";
					}
				}
				currentPosY += stepCount;
			}
		}
		System.out.println("end.\n");

		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (board[i][j] == "[.]") {
					board[i][j] = "[#]";
				}
			}
		}

		return board;
	}

	private static void printBoard(String[][] board) {

		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (board[i][j] == null) {
					board[i][j] = "[_]";
				}
				board[startPosY][startPosX] = "[o]";

				System.out.print(board[i][j]);
			}
			System.out.println();
		}

	}

	public void paint(Graphics g) {
		
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (board[i][j] == "[#]") {
					g.drawRect((j/10000), (i/10000), 1, 1);
				}
				if (board[i][j] == "[X]") {
					g.fillRect((j/10000) - 2, (i/10000) - 2, 6, 6);
				}
				g.fillOval((startPosY/10000) - 5, (startPosX/10000) - 5, 10, 10);
			}
		}
	}

}
