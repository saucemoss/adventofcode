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

public class Day_7_Part2 {

	// TODO synchronize the threads
	// TODO refactoring

	private static ArrayList<int[]> phaseSequences;
	private static ArrayList<Integer> maxOutputs;

	public static synchronized void main(String[] args) throws InterruptedException {

		getPhaseSequences(56789, 98765, "5 to 9");
		maxOutputs = new ArrayList<Integer>();
		for (int i = 0; i < phaseSequences.size(); i++) {
			int[] phaseSequence = phaseSequences.get(i);
			ComputerP2 ampA = new ComputerP2(phaseSequence[0]);
			ComputerP2 ampB = new ComputerP2(phaseSequence[1]);
			ComputerP2 ampC = new ComputerP2(phaseSequence[2]);
			ComputerP2 ampD = new ComputerP2(phaseSequence[3]);
			ComputerP2 ampE = new ComputerP2(phaseSequence[4]);
			ampA.setInput(0);
			Thread.sleep(5);
			ampA.setWaiting(true);
			ampA.start();
			Thread.sleep(5);
			ampB.setWaiting(true);
			ampB.start();
			Thread.sleep(5);
			ampC.setWaiting(true);
			ampC.start();
			Thread.sleep(5);
			ampD.setWaiting(true);
			ampD.start();
			Thread.sleep(5);
			ampE.setWaiting(true);
			ampE.start();
			Thread.sleep(5);
			ampA.setWaiting(false);
			Thread.sleep(5);

			while (!ampA.isHalted()) {
				Thread.sleep(50);
				ampB.setInput(ampA.getOutput());
				ampB.setWaiting(false);
				Thread.sleep(50);
				ampC.setInput(ampB.getOutput());
				ampC.setWaiting(false);
				Thread.sleep(50);
				ampD.setInput(ampC.getOutput());
				ampD.setWaiting(false);
				Thread.sleep(50);
				ampE.setInput(ampD.getOutput());
				ampE.setWaiting(false);
				Thread.sleep(50);
				ampA.setInput(ampE.getOutput());
				ampA.setWaiting(false);

			}
			maxOutputs.add(ampE.getOutput());
			System.out.println(i + ", " + ampE.getOutput());

		}
		System.out.println("Maximum output is: " + Collections.max(maxOutputs));

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

class ComputerP2 implements Runnable {
	private static BufferedReader bufferedReader;
	int phase = 0;
	int input = 0;
	static int output = 0;
	static private Integer[] intcode;
	boolean halted = false;
	boolean waiting = false;
	private Thread thread;

	public int getInput() {
		return input;
	}

	public void setInput(int input) {
		this.input = input;
	}

	public boolean isWaiting() {
		return waiting;
	}

	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}

	Thread getThread() {
		return thread;
	}

	void setThread(Thread thread) {
		this.thread = thread;
	}

	public Integer getOutput() {
		return output;
	}

	public ComputerP2(int phase) {
		this.phase = phase;
	}

	public ComputerP2(int phase, Integer input) {
		this.phase = phase;
		this.input = input;

	}

	public boolean isHalted() {
		return halted;
	}

	public void setHalted(boolean halted) {
		this.halted = halted;
	}

	public void start() {

		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public synchronized void run() {
		intcode = getIntcode();
		int pointer;
		boolean phaseTurn = true;
		for (int j = 0; j < intcode.length; j += pointer) {
			pointer = 0;
			char[] in = intcode[j].toString().toCharArray();
			String[] instruction = getInstruction(in);
			int opcode = Integer.parseInt(instruction[3]);
			if (isHalted()) {
				break;
			}
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
					// System.out.println(thread + " Phase set to " + phase);
					break;
				} else if (!isWaiting()) {
					intcode[intcode[j + 1]] = input;
					pointer = 2;
					// System.out.println(thread + " Took input of " + input);
					break;
				} else {
					continue;
				}
			case 4:
				output = val1;
				pointer = 2;
				setWaiting(true);
				// System.out.println(thread + " Gave output of " + output);
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
				// System.out.println(thread + "\tEND. 99");
				setHalted(true);
				break;
			default:
				System.out.println("\tError: '" + opcode + "' is not a valid opcode.");
				setHalted(true);
				break;
			}

			if (halted) {
				break;
			}
		}
		setHalted(true);

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
