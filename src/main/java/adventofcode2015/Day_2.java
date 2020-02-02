package adventofcode2015;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Day_2 {
	static int totalSurface  = 0, totalRibbon  = 0;

	public static void main(String[] args) throws IOException {
		InputStream is = new FileInputStream("src/main/resources/2015/Day_2_puzzle_input.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = br.readLine();
		while (line != null) {
			String[] sr = line.split("x");

			int l = Integer.parseInt(sr[0]);
			int w = Integer.parseInt(sr[1]);
			int h = Integer.parseInt(sr[2]);

			int[] faces = { l * 2 + w * 2, w * 2 + h * 2, h * 2 + l * 2};
			int[] sides = { l * w, w * h, h * l };
			
			Arrays.sort(sides);
			Arrays.sort(faces);
			
			totalRibbon += faces[0] + l * w * h;
			totalSurface += 2 * l * w + 2 * w * h + 2 * h * l + sides[0];

			line = br.readLine();
		}
		System.out.println(totalSurface + ", " + totalRibbon);
		br.close();
		is.close();
	}

}
