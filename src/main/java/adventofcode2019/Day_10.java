package adventofcode2019;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Day_10 {
	private static BufferedReader bufferedReader;
	private static char[][] map;
	static JFrame window = new JFrame();
	static ArrayList<Integer[]> asteroidLocations;

	public static void main(String[] args) {

		map = inToMap();
		asteroidLocations = getAsteroidLocations(map);

		// helper methods
		printMap(map);
		// printAsteroidLocations();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, 1900, 1000);
		window.getContentPane()
				.add(new AsteroidVis(map, asteroidLocations));
		window.setVisible(true);

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {

				if (map[j][i] == '#') {
					for (int x = 0; x < asteroidLocations.size(); x++) {
						Integer d[] = asteroidLocations.get(x);
						int x2 = d[0];
						int y2 = d[1];

						int dx = x2 - i;
						if (dx < 0)
							dx = dx * -1;
						int dy = y2 - j;
						if (dy < 0)
							dy = dy * -1;
						
						

						//System.out.println("Asteroid in " + i + "," + j + " see asteroid in " + x2 + "," + y2
						//		+ ". Distance is: " + dx + "," + dy + ". GCD is : " + GCD(dx,dy));

					}
				}
			}
		}
	}
	
	static int getGcd(int n1, int n2) {
	    int gcd = 1;
	    for (int i = 1; i <= n1 && i <= n2; i++) {
	        if (n1 % i == 0 && n2 % i == 0) {
	            gcd = i;
	        }
	    }
	    return gcd;
	}

	private static void printAsteroidLocations() {
		for (int i = 0; i < asteroidLocations.size(); i++) {
			Integer[] xy = asteroidLocations.get(i);
			System.out.println(xy[0] + ", " + xy[1]);
		}
	}

	private static char[][] inToMap() {
		InputStream inputStream;
		char[][] map = new char[48][48];
		try {
			inputStream = new FileInputStream("src/main/resources/2019/Day_10_puzzle_input.txt");
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String in = bufferedReader.readLine();
			char[] x = in.toCharArray();
			for (int i = 0; i < x.length; i++) {
				map[0][i] = x[i];
			}
			int y = 0;
			while (in != null) {
				x = in.toCharArray();
				in = bufferedReader.readLine();
				for (int i = 0; i < x.length; i++) {
					map[y][i] = x[i];
				}
				y++;
			}
			bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	private static void printMap(char[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}

	public static ArrayList<Integer[]> getAsteroidLocations(char[][] map) {
		ArrayList<Integer[]> asteroidLocations = new ArrayList<Integer[]>();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[j][i] == '#') {
					Integer[] xy = new Integer[2];
					xy[0] = i;
					xy[1] = j;
					asteroidLocations.add(xy);
				}
			}
		}

		return asteroidLocations;
	}

}

class AsteroidVis extends JPanel {

	char[][] map;
	ArrayList<Integer[]> asteroidLocations;

	public AsteroidVis(char[][] m, ArrayList<Integer[]> al) {
		map = m;
		asteroidLocations = al;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1200, 1200);

		for (int x = 0; x < asteroidLocations.size(); x++) {
			Integer[] xy = asteroidLocations.get(x);
			g.setColor(Color.GRAY);
			g.fillRect((xy[0] * 20) - 2, (xy[1] * 20) - 2, 5, 5);

			if (x == 150) {
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map.length; j++) {
						if (map[j][i] == '#') {
							g.setColor(Color.YELLOW);
							g.drawLine(i * 20, j * 20, xy[0] * 20 - 2, xy[1] * 20);
						}
					}
				}
			}
		}
	}
}