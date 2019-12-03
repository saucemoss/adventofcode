package advofcode;

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
			inputStream = new FileInputStream("src/main/resources/day_1_puzzle_input.txt");

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String in = bufferedReader.readLine();
			int totalFuelReq = 0;
			while(in != null) {	
				int mass = Integer.parseInt(in);
				totalFuelReq += fuelReq(mass);	
				in = bufferedReader.readLine();
				}		
			bufferedReader.close();
			System.out.println(totalFuelReq);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int fuelReq(int x) {
		int req = Math.floorDiv(x, 3) - 2;
		return req;
	}

}
