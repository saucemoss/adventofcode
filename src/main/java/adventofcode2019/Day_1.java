package adventofcode2019;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Day_1 {

	public static void main(String[] args) {

		InputStream inputStream;
		try {
			inputStream = new FileInputStream("src/main/resources/2019/day_1_puzzle_input.txt");

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String in = bufferedReader.readLine();
			int totalFuelReq = 0;
			while (in != null) {
				int mass = Integer.parseInt(in);
				totalFuelReq += moduleFuelReq(mass);
				in = bufferedReader.readLine();
			}
			bufferedReader.close();
			System.out.println("\nTotal fuel required for all the modules and the fuel: " + totalFuelReq + ".");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int moduleFuelReq(int mass) {
		int req = Math.floorDiv(mass, 3) - 2;
		int totalReq = req;
		while (Math.floorDiv(req, 3) > 0) {
			if (Math.floorDiv(req, 3) - 2 < 0) {
				break;
			}
			req = Math.floorDiv(req, 3) - 2;
			totalReq += req;
			System.out.println(req);
		}
		return totalReq;
	}

}
