package advofcode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Day_7 {

	private static ArrayList<int[]> phaseSequences;
	private static ArrayList<Integer> maxOutputs;

	public static void main(String[] args) throws InterruptedException {

		part1();

	}

	private static void part1() {
		getPhaseSequences(1234, 43210, "0 to 4");

		maxOutputs = new ArrayList<Integer>();
		for (int i = 0; i < phaseSequences.size(); i++) {
			System.out.println("\tIteration: " + i + " \t========================|");
			int[] phaseSequence = phaseSequences.get(i);
			Integer[] outputs = new Integer[6];
			Computer amp = new Computer(phaseSequence[0]);
			amp.run();
			outputs[0] = amp.getOutput();
			for (int j = 1; j < 5; j++) {
				amp = new Computer(phaseSequence[j], outputs[j - 1]);
				amp.run();
				outputs[j] = amp.getOutput();
			}
			maxOutputs.add(outputs[4]);
		}
		System.out.println("\t|=======================================|");
		System.out.println("\t| The highest output possible is " + Collections.max(maxOutputs) + "\t|");
		System.out.println("\t|=======================================|");
	}

	private static void getPhaseSequences(int start, int finish, String phaseMode) {
		phaseSequences = new ArrayList<int[]>();

		for (int seqNum = start; seqNum <= finish; seqNum++) {
			int[] sequence = new int[5];
			int j = 4;
			int sN = seqNum;

			while (sN > 0) {
				sequence[j] = sN % 10;
				sN = sN / 10;
				j--;
			}

			int pass = 0;
			int fail = 0;
			for (int i = 0; i < sequence.length; i++) {
				if (phaseMode.equals("0 to 4") && sequence[i] < 5)
					pass++;
				if (phaseMode.equals("5 to 9") && sequence[i] >= 5)
					pass++;
				for (int l = 0; l < sequence.length; l++) {
					if (sequence[i] == sequence[l])
						fail++;
				}
			}
			if (pass == 5 && fail < 6) {
				phaseSequences.add(sequence);

			}

		}
	}

}

class Computer implements Runnable {
	private static BufferedReader bufferedReader;
	int phase = 0;
	int prevOutput = 0;
	int input = 0;
	static int output = 0;
	static private Integer[] intcode = getIntcode();
	static boolean halted = false;

	public Integer getOutput() {
		return output;
	}

	public Computer(int phase) {
		this.phase = phase;
	}

	public Computer(int phase, Integer prevOutput) {
		this.phase = phase;
		this.input = prevOutput;

	}

	@Override
	public void run() {
		compute(phase, input);
	}

	private static int compute(int phase, int input) {
		int pointer;
		boolean phaseTurn = true;
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
				if (phaseTurn) {
					intcode[intcode[j + 1]] = phase;
					phaseTurn = false;
					pointer = 2;
					break;
				} else {
					intcode[intcode[j + 1]] = input;
					pointer = 2;
					break;
				}
			case 4:
				output = val1;
				System.out.println("\t|Output: " + val1 + "\tInput: " + input + "\tPhase:" + phase + " |");
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
				//System.out.println("\tEND.");
				return output;
			default:
				System.out.println("\tError: '" + opcode + "' is not a valid opcode.");
				halted = true;
				return output;
			}
		}
		return output;
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
		if (param == 2) {
			if (instruction[1].equals("0"))
				val = intcode[intcode[j + 2]]; // position mode for parameter 2
			if (instruction[1].equals("1"))
				val = intcode[j + 2]; // immediate mode for parameter 2
		} else if ((param == 1)) {
			if (instruction[2].equals("0"))
				val = intcode[intcode[j + 1]]; // position mode for parameter 1
			if (instruction[2].equals("1"))
				val = intcode[j + 1]; // immediate mode for parameter 1
		}
		return val;
	}

	private static Integer[] getIntcode() {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("src/main/resources/Day_7_puzzle_input.txt");
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

}
