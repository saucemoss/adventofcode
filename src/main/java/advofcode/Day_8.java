package advofcode;

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

public class Day_8 {
	private static BufferedReader bufferedReader;
	public static ArrayList<char[]> layers;
	static JFrame window = new JFrame();

	public static void main(String[] args) throws IOException, InterruptedException {
		InputStream inputStream;
		ArrayList<char[]> layers = new ArrayList<char[]>();
		Thread.sleep(3000);
		inputStream = new FileInputStream("src/main/resources/Day_8_puzzle_input.txt");
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String in = bufferedReader.readLine();
		in.toCharArray();

		int counter = 0;
		char[] layer = new char[151];
		int i = 0;
		while (i < in.length()) {

			counter++;
			layer[counter] = in.charAt(i);
			i++;
			if (i % 150 == 0) {
				layers.add(layer);
				layer = new char[151];
				counter = 0;
			}

		}

		char[] finalLayer = new char[151];

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(100, 100, 300, 150);
		window.getContentPane().add(new LayerVis(finalLayer));
		window.setVisible(true);

		for (int j = layers.size() - 1; j > -1; j--) {
			System.out.println("layer: " + j);
			layer = layers.get(j);
			for (int k = 1; k < layer.length; k++) {

				if (layer[k] == '0') {
					finalLayer[k] = '0';
				} else if (layer[k] == '1') {
					finalLayer[k] = '1';
				} else if (layer[k] == '2' && (finalLayer[k] != '0' && finalLayer[k] != '1')) {
					finalLayer[k] = ' ';
				}

				// System.out.print(layer[k]);
				System.out.print(finalLayer[k]);
				if (k != 0 && k % 25 == 0) {
					System.out.println();
				}

			}
			System.out.println();
			Thread.sleep(20);
			window.repaint();

		}

	}

}

class LayerVis extends JPanel {

	char[] layer;

	public LayerVis(char[] l) {
		layer = l;
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		int y = 0;
		int counter = 0;
		for (int i = 1; i < layer.length; i++) {
			counter++;
			if (layer[i] == '0') {
				g.setColor(Color.white);
				g.fillRect(counter * 10, y, 10, 10);
			} else if (layer[i] == '1') {
				g.setColor(Color.black);
				g.fillRect(counter * 10, y, 10, 10);
			}
			if (i != 0 && i % 25 == 0) {
				y += 10;
				counter = 0;
			}

		}

	}
}
