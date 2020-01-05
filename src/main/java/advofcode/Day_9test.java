package advofcode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Day_9test {
	private static BufferedReader bufferedReader;
	private static Scanner scan;
	private static int base = 0;
	private static ArrayList<Long> memory = new ArrayList<Long>();

	public static void main(String[] args) {

		Long[] testIntcode1 = { 109l, 1l, 204l, -1l, 1001l, 100l, 1l, 100l, 1008l, 100l, 16l, 101l, 1006l, 101l, 0l,
				99l }; // pass
		Long[] testIntcode2 = { 1102l, 34915192l, 34915192l, 7l, 4l, 7l, 99l, 0l }; // pass
		Long[] testIntcode3 = { 104l, 1125899906842624l, 99l }; // pass
		Long[] testIntcode4 = { 109l, 1l, 3l, 3l, 204l, 2l, 99l }; // outputs input pass
		Long[] testIntcode5 = { 109l, 1l, 203l, 2l, 204l, 2l, 99l }; // outputs input pass
		Long[] testIntcode6 = { 109l, 1l, 109l, 9l, 204l, -6l, 99l }; // outputs 204 pass 
		Long[] testIntcode7 = { 109l, 1l, 209l, -1l, 204l, -106l, 99l }; // outputs 204 fail
		Long[] testIntcode8 = { 109l, 1l, 9l, 2l, 204l, -6l, 99l }; // outputs 204 pass
		Long[] testIntcode9 = { 109l, -1l, 204l, 1l, 99l }; // outputs 109 pass
		Long[] testIntcode10 = { 109l, -1l, 4l, 1l, 99l }; // outputs -1 pass
		Long[] intcode = getIntcode();

		// compute
		
//		System.out.println("test 1. Should output 109l, 1l, 204l, -1l, 1001l, 100l, 1l, 100l, 1008l, 100l, 16l, 101l, 1006l, 101l, 0l, 99l");
//		compute(testIntcode1); 
//		
//		System.out.println("test 2. Should output 16 digit");
//		compute(testIntcode2);
//
//		System.out.println("test 3. outputs 1125899906842624l ");
//		compute(testIntcode3);
//		
//		System.out.println("test 4. outputs input ");
//		compute(testIntcode4);
//		
//		System.out.println("test 5. outputs input ");
//		compute(testIntcode5);
//		
//		System.out.println("test 6. outputs 204 ");
//		compute(testIntcode6);
//		
//		System.out.println("test 7. outputs 204 ");
//		compute(testIntcode7);
//		
//		System.out.println("test 8. outputs 204 ");
//		compute(testIntcode8);
//		
//		System.out.println("test 9. outputs 109 ");
//		compute(testIntcode9);
//		
//		System.out.println("test 10. outputs -1 ");
//		compute(testIntcode10);
		
		compute(intcode);


	}

	private static Long[] getIntcode() {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("src/main/resources/Day_9_puzzle_input.txt");
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String in = bufferedReader.readLine();
			String[] str = in.split(",");
			Long[] intcode = new Long[str.length];

			for (int i = 0; i < 5000; i++) {
				memory.add(i, (long) 0);
			}

			for (int a = 0; a < str.length; a++) {
				intcode[a] = Long.parseLong(str[a]);
			}

			return intcode;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private static void compute(Long[] intcode) {
		Long pointer;
		base = 0;
		for (int j = 0; j < intcode.length; j += pointer) {
			pointer = 0l;
			char[] in = intcode[j].toString().toCharArray();
			String[] instruction = getInstruction(in);
			//System.out.println(instruction[0] + " " + instruction[1] + " " + instruction[2] + " " + instruction[3]);
			int opcode = Integer.parseInt(instruction[3]);
			
			long val1 = 0l, val2 = 0l;
			
			if (opcode != 3 && opcode != 4 && opcode != 99) {
				val2 = mode(intcode, j, instruction, 2);		
			}
			if (opcode != 99) {
				val1 = mode(intcode, j, instruction, 1);
			}

			switch (opcode) {
			case 1:

				if (intcode[j + 3] > intcode.length) {
					memory.add(Math.toIntExact(intcode[j + 3]), (long) val1 + val2);
				} else {
					intcode[Math.toIntExact(intcode[j + 3])] = (long) (val1 + val2);
				}
				pointer = 4l;
				break;
			case 2:

				if (intcode[j + 3] > intcode.length) {
					memory.add(Math.toIntExact(intcode[j + 3]), (long) (val1 * val2));
				} else {
					intcode[Math.toIntExact(intcode[j + 3])] = (long) (val1 * val2);
				}
				pointer = 4l;
				break;
			case 3:

				scan = new Scanner(System.in);
				System.out.print("Input: ");
				Long inp = scan.nextLong();
				if (instruction[2].equals("2")) {
					if (intcode[j + 1] + base >= intcode.length) {
						memory.add((Math.toIntExact(intcode[j + 1]) + base), inp); // relative mode for parameter 1 to																					
					} else {
						intcode[Math.toIntExact(intcode[j + 1]) + base] = inp; // relative mode for parameter 1 to																		
					}
				} else if (intcode[j + 1] >= intcode.length) {
					memory.add(Math.toIntExact(intcode[j + 1]), inp);
				} else {
					intcode[Math.toIntExact(intcode[j + 1])] = inp;
				}
				pointer = 2l;
				break;
			case 4:

				System.out.println("\tOUTPUT: " + val1);
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

				if (val1 < val2) {
					if (intcode[j + 3] > intcode.length) {
						memory.add(j + 3 + base, (long) 1);
					} else {
						intcode[j + 3] = (long) 1;
					}
				} else {
					if (intcode[j + 3] > intcode.length) {
						memory.add(j + 3 + base, (long) 0);
					} else {
						intcode[j + 3] = (long) 0;
					}
				}
				pointer = 4l;
				break;
			case 8:
                
				if (val1 == val2) {
					if (intcode[j + 3] >= intcode.length) {
						memory.add(j + 3 + base, (long) 1);
					} else {
						intcode[j + 3] = (long) 1;
					}
				} else {

					if (intcode[j + 3] >= intcode.length) {
						memory.add(j + 3 + base, (long) 0);
						//System.out.println("test" + memory.get(Math.toIntExact(intcode[j + 3] )));
					} else {
						intcode[j + 3] = (long) 0;
						//System.out.println("test" + intcode[j + 3]);
					}
				}

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

	private static Long mode(Long[] intcode, int j, String[] instruction, int param) {
		Long val = 0l;

		if (param == 2) {
			if (instruction[1].equals("0"))
				if (intcode[j + 2] > intcode.length) {
					val = memory.get(Math.toIntExact(intcode[j + 2])); // position mode for parameter 2 from memory
				} else {
					val = (long) intcode[Math.toIntExact(intcode[j + 2])]; // position mode for parameter 2
				}
			if (instruction[1].equals("1"))
				val = (long) intcode[j + 2]; // immediate mode for parameter 2
			if (instruction[1].equals("2")) {
				if (intcode[j + 2] + base >= intcode.length) {
					val = memory.get(Math.toIntExact(intcode[j + 2]) + base); // relative mode for parameter 2 from																				// memory
				} else {
					val = (long) intcode[Math.toIntExact(intcode[j + 2]) + base]; // relative mode for parameter 2
				}
			}
		} else if ((param == 1)) {
			if (instruction[2].equals("0")) {
				if (intcode[j + 1] >= intcode.length) {
					val = memory.get(Math.toIntExact(intcode[j + 1])); // position mode for parameter 1 from memory
				} else {
					val = (long) intcode[Math.toIntExact(intcode[j + 1])]; // position mode for parameter 1
				}
			}
			if (instruction[2].equals("1")) {
				val = (long) intcode[j + 1]; // immediate mode for parameter 1
			}
			if (instruction[2].equals("2")) {
				if (intcode[j + 1] + base >= intcode.length) {
					val = memory.get(Math.toIntExact(intcode[j + 1]) + base); // relative mode for parameter 1 from
				} else {
					val = (long) intcode[Math.toIntExact(intcode[j + 1]) + base]; // relative mode for parameter 1
				}
			}

		}
		return val;
	}

}
