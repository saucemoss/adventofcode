package adventofcode2019;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Day_9 {
	private static BufferedReader bufferedReader;
	private static Scanner scan;
	private static int base = 0;
	private static ArrayList<Long> memory = new ArrayList<Long>();

	public static void main(String[] args) {

		System.out.println("\n\n\t--- Day 9: Sensor Boost ---");
		ArrayList<Long> intcode = getIntcode();
		compute(intcode);

	}

	private static void resetMemory() {
		memory.clear();
		for (int i = 0; i < 25000; i++) {
			memory.add(i, (long) 0);
		}
	}

	private static ArrayList<Long> getIntcode() {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("src/main/resources/2019/Day_9_puzzle_input.txt");
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String in = bufferedReader.readLine();
			String[] str = in.split(",");
			resetMemory();
			for (int a = 0; a < str.length; a++) {
				memory.add(a, Long.parseLong(str[a]));
			}

			return memory;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private static void compute(ArrayList<Long> intcode) {

		Long pointer;
		base = 0;
		for (int j = 0; j < memory.size(); j += pointer) {
			// System.out.println(memory);
			pointer = 0l;

			char[] in = memory.get(j).toString().toCharArray();
			String[] instruction = getInstruction(in);

			int opcode = Integer.parseInt(instruction[3]);

			long val1 = 0l, val2 = 0l, val3 = 0l;
			if (opcode != 99) {
				val1 = mode(intcode, j, instruction, 1);
			}
			if (opcode != 3 && opcode != 4 && opcode != 99) {
				val2 = mode(intcode, j, instruction, 2);
			}
			if (opcode == 1 || opcode == 2 || opcode == 7 || opcode == 8) {
				val3 = mode(intcode, j, instruction, 3);
			}

			switch (opcode) {
			case 1:
				memory.set(Math.toIntExact(val3), (val1 + val2));
				pointer = 4l;
				break;
			case 2:

				memory.set(Math.toIntExact(val3), (val1 * val2));
				pointer = 4l;
				break;
			case 3:

				scan = new Scanner(System.in);
				System.out.print("\tInput: ");
				Long inp = scan.nextLong();

				if (instruction[2].equals("2")) {
					memory.set(Math.toIntExact(memory.get(j + 1) + base), inp);
				} else {
					memory.set(Math.toIntExact(memory.get(j + 1)), inp);

				}
				pointer = 2l;
				break;
			case 4:

				System.out.println("\tOutput: " + val1);
				pointer = 2l;
				break;
			case 5:

				if (val1 != 0) {
					j = 0;
					pointer = val2;
				} else
					pointer = 3l;
				break;
			case 6:

				if (val1 == 0) {
					j = 0;
					pointer = val2;
				} else
					pointer = 3l;
				break;
			case 7:

				if (val1 < val2)
					memory.set(Math.toIntExact(val3), (long) 1);
				else
					memory.set(Math.toIntExact(val3), (long) 0);

				pointer = 4l;
				break;
			case 8:

				if (val1 == val2)
					memory.set(Math.toIntExact(val3), (long) 1);
				else
					memory.set(Math.toIntExact(val3), (long) 0);

				pointer = 4l;
				break;
			case 9:
				base += val1;
				pointer = 2l;
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

	private static Long mode(ArrayList<Long> memory, int j, String[] instruction, int param) {
		Long val = 0l;

		switch (param) {
		case 1:
			if (instruction[2].equals("0"))
				val = memory.get(Math.toIntExact(memory.get(j + 1))); // position mode for parameter 1
			if (instruction[2].equals("1"))
				val = (long) memory.get(j + 1); // immediate mode for parameter 1
			if (instruction[2].equals("2"))
				val = memory.get(Math.toIntExact(memory.get(j + 1) + base)); // relative mode for parameter 1
			break;

		case 2:
			if (instruction[1].equals("0"))
				val = memory.get(Math.toIntExact(memory.get(j + 2))); // position mode for parameter 2
			if (instruction[1].equals("1"))
				val = (long) memory.get(j + 2); // immediate mode for parameter 2
			if (instruction[1].equals("2"))
				val = memory.get(Math.toIntExact(memory.get(j + 2) + base)); // relative mode for parameter 2
			break;

		case 3:
			if (instruction[0].equals("0"))
				val = memory.get(j + 3);
			else if (instruction[0].equals("2"))
				val = memory.get(j + 3) + base;
			else
				System.out.println("Error, wrong instruction for third parameter: " + instruction[0]);
			break;
		}

		return val;
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

}
