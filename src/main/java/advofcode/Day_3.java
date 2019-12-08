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

	private static BufferedReader bufferedReader;
	static ArrayList<Integer[]> pointsOfPath1 = new ArrayList<Integer[]>();
	static ArrayList<Integer[]> pointsOfPath2 = new ArrayList<Integer[]>();
	static ArrayList<Integer[]> xs = new ArrayList<Integer[]>();
	static ArrayList<Integer> xsteps = new ArrayList<Integer>();
	static int minstep = 0;
	static int minx = 0;
	static int scale = 30;
	static Integer[] manhattanPoint;

	static int startPosX = 950 * scale;
	static int startPosY = 500 * scale;
	static JFrame window = new JFrame();

	static boolean done = false;

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
		manhattanPoint = new Integer[2];
		// compute + visualization
		showVisualisation(path7, path8);
		xs = getXs();

	}

	public static void showVisualisation(String[] path1, String[] path2) {

		pointsOfPath1 = getPointsOfPath(path1);
		pointsOfPath2 = getPointsOfPath(path2);

		// graphics
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, 1900, 1000);
		window.getContentPane()
				.add(new Visualisation(pointsOfPath1, pointsOfPath2, scale, xs, manhattanPoint, startPosX, startPosY));
		window.setVisible(true);
	}

	private static ArrayList<Integer[]> getPointsOfPath(String[] path) {
		int currentPosX = startPosX;
		int currentPosY = startPosY;
		int totalStepsToThisPoint = 0;
		ArrayList<Integer[]> points = new ArrayList<Integer[]>();

		for (int i = 0; i < path.length; i++) {
			String sc = path[i].substring(1);
			int stepCount = Integer.parseInt(sc);
			if (path[i].charAt(0) == 'R') {
				for (int j = 0; j < stepCount; j++) {
					Integer[] xy = new Integer[3];
					xy[0] = currentPosX + j;
					xy[1] = currentPosY;
					xy[2] = totalStepsToThisPoint;
					points.add(xy);
					totalStepsToThisPoint++;
				}
				currentPosX += stepCount;
			} else if (path[i].charAt(0) == 'L') {
				for (int j = 0; j < stepCount; j++) {
					Integer[] xy = new Integer[3];
					xy[0] = currentPosX - j;
					xy[1] = currentPosY;
					xy[2] = totalStepsToThisPoint;
					points.add(xy);
					totalStepsToThisPoint++;
				}
				currentPosX -= stepCount;
			} else if (path[i].charAt(0) == 'U') {
				for (int j = 0; j < stepCount; j++) {
					Integer[] xy = new Integer[3];
					xy[0] = currentPosX;
					xy[1] = currentPosY - j;
					xy[2] = totalStepsToThisPoint;
					points.add(xy);
					totalStepsToThisPoint++;
				}
				currentPosY -= stepCount;
			} else if (path[i].charAt(0) == 'D') {
				for (int j = 0; j < stepCount; j++) {
					Integer[] xy = new Integer[3];
					xy[0] = currentPosX;
					xy[1] = currentPosY + j;
					xy[2] = totalStepsToThisPoint;
					points.add(xy);
					totalStepsToThisPoint++;
				}
				currentPosY += stepCount;
			}

		}
		return points;
	}

	private static ArrayList<Integer[]> getXs() {
		int prev = 0;

		for (int x = 0; x < pointsOfPath1.size(); x++) {
			Integer p1[] = pointsOfPath1.get(x);
			for (int y = 0; y < pointsOfPath2.size(); y++) {
				Integer p2[] = pointsOfPath2.get(y);
				if (p1[0].equals(p2[0]) && p1[1].equals(p2[1])) {
					System.out.println(
							"Bingo! At " + p1[0] + " and " + p1[1] + ". Steps to reach this point for red wire: "
									+ p1[2] + ". Blue wire: " + p2[2] + ". Total steps: " + (p1[2] + p2[2]));
					xs.add(p1);
					if ((p1[2] + p2[2]) != 0) {
						xsteps.add(p1[2] + p2[2]);
					}
				}
			}
			int percentDone = (x * 100) / pointsOfPath1.size();
			if (prev != percentDone) {

				window.repaint();
				prev = percentDone;
				if (percentDone == 100) {
					done = true;
				}
				System.out.println(percentDone + "%");
			}
		}

		ArrayList<Integer> manhattanPoints = new ArrayList<Integer>();

		for (int i = 0; i < xs.size(); i++) {
			Integer p[] = xs.get(i);
			int x = Math.abs(startPosY - p[1]) + Math.abs(startPosX - p[0]);
			if (x != 0) {
				manhattanPoints.add(x);
			}
		}
		minstep = Collections.min(xsteps);
		minx = Collections.min(manhattanPoints);
		Integer p[] = xs.get(manhattanPoints.indexOf(minx) + 1);
		System.out.println("Done. Manhattan distance: " + minx + " at coordinates " + p[0] + ", " + p[1]
				+ ". Fewest steps to intersection: " + Collections.min(xsteps));
		manhattanPoint[0] = p[0];
		manhattanPoint[1] = p[1];
		window.repaint();
		return xs;

	}

}

class Visualisation extends JPanel {

	ArrayList<Integer[]> pointsOfPath1;
	ArrayList<Integer[]> pointsOfPath2;
	ArrayList<Integer[]> xs;
	Integer[] manhattanPoint;
	int startPosX;
	int startPosY;

	int scale;

	public Visualisation(ArrayList<Integer[]> pointsOfPath12, ArrayList<Integer[]> pointsOfPath22, int s,
			ArrayList<Integer[]> x, Integer[] mp, int spX, int spY) {
		pointsOfPath1 = pointsOfPath12;
		pointsOfPath2 = pointsOfPath22;
		startPosX = spX;
		startPosY = spY;
		manhattanPoint = mp;
		xs = x;
		scale = s;
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1900, 1000);

		g.setColor(Color.RED);
		for (int i = 0; i < pointsOfPath1.size(); i++) {
			Integer[] xy = pointsOfPath1.get(i);
			g.drawOval(xy[0] / scale, xy[1] / scale, 1, 1);

		}
		g.setColor(Color.BLUE);
		for (int i = 0; i < pointsOfPath2.size(); i++) {
			Integer[] xy = pointsOfPath2.get(i);
			g.drawOval(xy[0] / scale, xy[1] / scale, 1, 1);

		}

		g.setColor(Color.YELLOW);
		for (int i = 0; i < xs.size(); i++) {
			Integer _xs[] = xs.get(i);
			g.fillRect((_xs[0] / scale) - 1, (_xs[1] / scale) - 1, 5, 5);

		}
		g.setColor(Color.GREEN);
		if (manhattanPoint[0] != null && manhattanPoint[1] != null) {
			g.drawOval((manhattanPoint[0] / scale) - 3, (manhattanPoint[1] / scale) - 3, 8, 8);
		}
		g.setColor(Color.PINK);
		g.fillOval((startPosX / scale) - 3, (startPosY / scale) - 3, 8, 8);
	}

}