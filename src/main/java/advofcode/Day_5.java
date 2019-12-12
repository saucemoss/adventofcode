package advofcode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Day_5 {

	private static BufferedReader bufferedReader;
	private static Scanner scan;

	public static void main(String[] args) {

		Integer[] testIntcode1 = { 3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8 };
		Integer[] testIntcode2 = { 3, 3, 1107, -1, 8, 3, 4, 3, 99 };
		Integer[] testIntcode3 = { 3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9 };
		Integer[] testIntcode4 = { 3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98,
				0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1,
				46, 98, 99 };
		Integer[] intcode = getIntcode();

		// compute
		compute(intcode);

	}

	private static Integer[] getIntcode() {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("src/main/resources/day_5_puzzle_input.txt");
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String in = bufferedReader.readLine();
			String[] str = in.split(",");
			Integer[] intcode = new Integer[str.length];

			for (int a = 0; a < str.length; a++) {
				intcode[a] = Integer.parseInt(str[a]);
			}

			return intcode;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private static void compute(Integer[] intcode) {
		int pointer;
		for (int j = 0; j < intcode.length; j += pointer) {
			pointer = 0;
			char[] in = intcode[j].toString().toCharArray();		
			String[] instruction = getInstruction(in);	
			int opcode = Integer.parseInt(instruction[3]);
			int val1 = mode(intcode, j, instruction, 1);
			int val2 = 0;
			if (opcode != 3 && opcode != 4 && opcode != 99) {
				val2 = mode(intcode, j, instruction, 2);
			}

			switch (opcode) {
			case 1:
				
				intcode[intcode[j + 3]] = val1 + val2;
				pointer = 4;
				break;
			case 2:

				intcode[intcode[j + 3]] = val1 * val2;
				pointer = 4;
				break;
			case 3:

				scan = new Scanner(System.in);
				System.out.print("ID of the system: ");
				intcode[intcode[j + 1]] = scan.nextInt();
				pointer = 2;
				break;
			case 4:

				System.out.println("\tOUTPUT: " + val1);
				pointer = 2;
				break;
			case 5:

				if (val1 != 0) {
					j = 0;
					pointer = val2;
				} else
					pointer = 3;
				break;
			case 6:

				if (val1 == 0) {
					j = 0;
					pointer = val2;
				} else
					pointer = 3;
				break;
			case 7:

				if (val1 < val2)
					intcode[intcode[j + 3]] = 1;
				else
					intcode[intcode[j + 3]] = 0;
				pointer = 4;
				break;
			case 8:

				if (val1 == val2)
					intcode[intcode[j + 3]] = 1;
				else
					intcode[intcode[j + 3]] = 0;
				pointer = 4;
				break;
			case 99:
				System.out.println("\tEND.");
				return;
			default:
				System.out.println("\tError: '" + opcode + "' is not a valid opcode.");
				return;
			}
		}
		return;
	}

	private static String[] getInstruction(char[] in) {
		String[] instruction = { "0", "0", "0", "0" };
		
		if (in.length >= 2) {
			instruction[3] = in[in.length - 2] + "" + in[in.length - 1];
			if (in.length >= 3) {
				instruction[2] = in[in.length - 3] + "";
			}
			if (in.length >= 4) {
				instruction[1] = in[in.length - 4] + "";
			}
			if (in.length >= 5) {
				instruction[0] = in[in.length - 5] + "";
			}
		} else {
			instruction[3] = "0" + in[in.length - 1];
		}

		return instruction;
	}

	private static int mode(Integer[] intcode, int j, String[] instruction, int param) {
		int val = 0;
		if(param==2) {		
		if (instruction[1].equals("0")) val = intcode[intcode[j + 2]]; // position mode for parameter 2
		if (instruction[1].equals("1")) val = intcode[j + 2]; // immediate mode for parameter 2
		}else if((param==1)){
		if (instruction[2].equals("0")) val = intcode[intcode[j + 1]]; // position mode for parameter 1
		if (instruction[2].equals("1")) val = intcode[j + 1]; // immediate mode for parameter 1
		}
		return val;
	}

}
