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

	static int boardWidth = 25;
	static int boardHeight = 25;
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

		// "production" paths
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
		//translatePath(path1, board); //feed the board with path 1
		//translatePath(path2, board); //feed the board with path 2	
		//printBoard(board, 25, 25); //will look cool with small boards dimensions like 25x25 (path1 and path2), not needed for solution
		//getXposDistances(board); //compute closest distances

		showVisualisation(path7, path8); //better visual representation of paths/wires, just for fun.

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
			String sc = path[i].substring(1);
			int stepCount = Integer.parseInt(sc);
			if (path[i].charAt(0) == 'R') {
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

	private static void printBoard(String[][] board, int bWidth, int bHeight) {
		boardWidth = bWidth;
		boardHeight = bHeight;		
		printBoard(board);
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

	public static void showVisualisation(String[] path1, String[] path2) {
		
		// graphics
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(100, 100, 1200, 800);		
		window.getContentPane().add(new Visualisation(path1, path2));
		window.setVisible(true);
	}
}

class Visualisation extends JPanel {

    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;

    ArrayList<Integer[]> linesOfPath1 = new ArrayList<Integer[]>();
    ArrayList<Integer[]> linesOfPath2 = new ArrayList<Integer[]>();
    
	int scale = 40;
	
	int startPosX = 600 * scale;
	int startPosY = 400 * scale;
    
	public Visualisation(String[] path1, String[] path2) {

		linesOfPath1 = getLinesOfPath(path1);
		linesOfPath2 = getLinesOfPath(path2);

	}

	private ArrayList<Integer[]> getLinesOfPath (String[] path) {
		int currentPosX = startPosX;
		int currentPosY = startPosY;
		ArrayList<Integer[]> lines = new ArrayList<Integer[]>();
		for (int i = 0; i < path.length; i++) {
			String sc = path[i].substring(1);
			int stepCount = Integer.parseInt(sc);
			if (path[i].charAt(0) == 'R') {												
				x1 = currentPosX;
				y1 = currentPosY;
				x2 = (currentPosX + stepCount);
				y2 = currentPosY;				
				Integer[] pos = { x1, y1, x2, y2 } ;
				lines.add(pos);
				currentPosX += stepCount;				
			} else if (path[i].charAt(0) == 'L') {				
				x1 = currentPosX;
				y1 = currentPosY;
				x2 = (currentPosX - stepCount);
				y2 = currentPosY;				
				Integer[] pos = { x1, y1, x2, y2 } ;
				lines.add(pos);
				currentPosX -= stepCount;
			} else if (path[i].charAt(0) == 'U') {
				x1 = currentPosX;
				y1 = currentPosY;
				x2 = currentPosX;
				y2 = (currentPosY - stepCount);				
				Integer[] pos = { x1, y1, x2, y2 } ;
				lines.add(pos);
				currentPosY -= stepCount;				
			} else if (path[i].charAt(0) == 'D') {	
				x1 = currentPosX;
				y1 = currentPosY;
				x2 = currentPosX;
				y2 = (currentPosY + stepCount);				
				Integer[] pos = { x1, y1, x2, y2 } ;
				lines.add(pos);
				currentPosY += stepCount;
			}
		}
		return lines;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g); 
		g.setColor(Color.RED);
		for(int i = 0; i < linesOfPath1.size(); i++) {
		Integer[] a = linesOfPath1.get(i);
		g.drawLine(a[0]/scale, a[1]/scale, a[2]/scale, a[3]/scale);
		}
		
		g.setColor(Color.BLUE);
		
		for(int i = 0; i < linesOfPath2.size(); i++) {
		Integer[] a = linesOfPath2.get(i);
		g.drawLine(a[0]/scale, a[1]/scale, a[2]/scale, a[3]/scale);
		}
	}

}
