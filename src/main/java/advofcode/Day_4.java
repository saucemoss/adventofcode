package advofcode;

import java.util.ArrayList;

public class Day_4 {

	public static void main(String[] args) {
		int start = 234208;
		int finish = 765869;
		Integer pass;
		int sixGood = 0;
		boolean registeredDouble = false;
		int rdCounter = 0;
		ArrayList<Integer> passList = new ArrayList<Integer>();
		ArrayList<Integer> finalPassList = new ArrayList<Integer>();

		for (pass = start; pass < finish; pass++) {
			char[] passArray = pass.toString().toCharArray();
			sixGood = 0;
			registeredDouble = false;
			//check for every next digit in the pass
			for (int i = 0; i < passArray.length - 1; i++) {

				//check if all digits are either increasing or the same value than previous
				if (passArray[i + 1] >= passArray[i]) {
					sixGood++;
				}
				// check if there is at least one double digit combination
				if (passArray[i + 1] == passArray[i]) {
					registeredDouble = true;
				}
			}
			if (sixGood >= 5 && registeredDouble) {
				passList.add(pass);
			}
		}
		
		
		for(int i = 0; i < passList.size(); i ++) {
			pass = passList.get(i);
			char[] passArray = pass.toString().toCharArray();	
			registeredDouble = false;
			for (int j = 0; j < passArray.length; j++) {				
				rdCounter = 0;
				for (int k = 0; k < passArray.length; k++) {					
					if(passArray[j]==passArray[k]) {
						rdCounter++;
					}
				}
				System.out.print(rdCounter);
				if(rdCounter==2) {
					registeredDouble = true;
				}
				
			}
			System.out.println(" - " + registeredDouble);
			if(registeredDouble == true) {
				finalPassList.add(pass);
			}
		}
		
		System.out.println(finalPassList.size() + " " + finalPassList);

	}

}
