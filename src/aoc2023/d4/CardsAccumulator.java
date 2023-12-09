package aoc2023.d4;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import org.slinger.ascii.AsciiInputStream;

public class CardsAccumulator {
	public static void main(String[] args) throws IOException {
		
		AsciiInputStream in = new AsciiInputStream(new FileInputStream("4/input"));
		
		int sum =0;
		
		String thisLine = in.readLine();
		
		int[] accumulator = new int[20];
		for (int i=0;i<accumulator.length;i++) {
			accumulator[i]=1;
		}

		while (!in.isEOFile() && thisLine != null && thisLine.length()>1) {
			
			System.out.println(thisLine);
			
			// split on pipe
			String card = thisLine.split("\\|")[0].split(":")[1]; //before pipe and after colon
			String candidates = thisLine.split("\\|")[1];
			System.out.println(card+"/"+candidates);
			
			String[] cardNums = card.split("\\s+");
			String[] candidateNums = candidates.split("\\s+");
			
			int score = 0;
			
			for (String cardNum : cardNums) {
				for (String candidateNum : candidateNums) {
					if (!cardNum.trim().isBlank()) {
						if (cardNum.equals(candidateNum)) {
							score = score +1;
						}
					}
				}
			}
			
			if (score>0) {
				//a win. How many of these cards do we have? 
				for (int j=0;j<accumulator[0];j++) { //each of these
					for (int i=1;i<score+1;i++) {
						accumulator[i]++; //yes yes I know you can add the other thing never mind eh
					}
				}
			}
			System.out.println(" -> "+score+" x"+accumulator[0]);
			sum = sum + accumulator[0];

			//move accumulator one back lots of ways of doing this
			for (int i=0;i<accumulator.length-1;i++) {
				accumulator[i] = accumulator[i+1];
				accumulator[i+1] =1;
			}
			
			for (int i=0;i<accumulator.length;i++) {
				System.out.print("["+accumulator[i]+"]");
			}
			System.out.println();
			
			
			thisLine = in.readLine();
		}
		
		System.out.println("Total "+sum);
		
		in.close();
	}
	
}
