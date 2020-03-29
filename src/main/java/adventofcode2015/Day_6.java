package adventofcode2015;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Day_6 {

	public static int[][] grid = new int[1000][1000];
	static JFrame window = new JFrame();

	public static void main(String[] args) throws IOException, InterruptedException {

		populateTheGrid(grid);

		InputStream is = new FileInputStream("src/main/resources/2015/Day_6_puzzle_input.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = br.readLine();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(100, 100, 1100, 1100);
		window.getContentPane().add(new LightsVis(grid));
		window.setVisible(true);

		while (line != null) {

			int[] coordinates = decode(line);
			if (line.contains("turn off")) {
				turnOffLights(coordinates);
			} else if (line.contains("turn on")) {
				turnOnLights(coordinates);
			} else {
				toggleLights(coordinates);
			}
			Thread.sleep(20);
			window.repaint();
			line = br.readLine();

		}
		is.close();

		System.out.println("Part one: " + countTheLights());

	}

	private static int countTheLights() {
		int lightsOn = 0;

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				if (grid[x][y] == 1) {

					lightsOn++;
				}
			}
		}

		return lightsOn;
	}

	private static void turnOffLights(int[] coords) {
		int x1 = coords[0];
		int y1 = coords[1];
		int x2 = coords[2];
		int y2 = coords[3];
		for (int y = y1; y < y2 + 1; y++) {
			for (int x = x1; x < x2 + 1; x++) {
				grid[x][y] = 0;
			}
		}

	}

	private static void turnOnLights(int[] coords) {
		int x1 = coords[0];
		int y1 = coords[1];
		int x2 = coords[2];
		int y2 = coords[3];

		for (int y = y1; y < y2 + 1; y++) {
			for (int x = x1; x < x2 + 1; x++) {
				grid[x][y] = 1;
			}
		}

	}

	private static void toggleLights(int[] coords) {
		int x1 = coords[0];
		int y1 = coords[1];
		int x2 = coords[2];
		int y2 = coords[3];

		for (int y = y1; y < y2 + 1; y++) {
			for (int x = x1; x < x2 + 1; x++) {
				if (grid[x][y] == 1) {
					grid[x][y] = 0;
				} else if (grid[x][y] == 0) {
					grid[x][y] = 1;
				}
			}
		}

	}

	private static int[] decode(String line) {
		String[] strs = new String[4];
		line += "  ";
		String xy1 = line.substring(line.indexOf(",") - 3, line.indexOf(",") + 4) + ",";
		String xy2 = line.substring(line.lastIndexOf(",") - 3, line.lastIndexOf(",") + 4);
		strs = (xy1 + xy2).split(",");
		int[] coords = new int[4];
		for (int i = 0; i < 4; i++) {
			coords[i] = Integer.parseInt(strs[i].replaceAll("\\D", ""));
		}
		return coords;
	}

	private static void populateTheGrid(int[][] grid2) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				grid[x][y] = 0;

			}
		}

	}

}

class LightsVis extends JPanel {

	int[][] grid;

	public LightsVis(int[][] _grid) {
		grid = _grid;
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				if (grid[x][y] == 1) {
					g.setColor(Color.BLACK);
					g.fillRect(x, y, 1, 1);
				} else {
					g.setColor(Color.WHITE);
					g.fillRect(x, y, 1, 1);
				}
			}
		}

	}
}
