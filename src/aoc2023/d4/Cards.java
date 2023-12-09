package aoc2023.d4;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import org.slinger.ascii.AsciiInputStream;

public class Cards {
	public static void main(String[] args) throws IOException {
		
		AsciiInputStream in = new AsciiInputStream(new FileInputStream("4/input"));
		
		int sum =0;
		
		String thisLine = in.readLine();

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
						System.out.print("["+cardNum+"="+candidateNum+"]");
						if (cardNum.equals(candidateNum)) {
							if (score == 0) score = 1; else score = score *2;
						}
					}
				}
			}
			
			System.out.println(" -> "+score);
			thisLine = in.readLine();
			sum = sum + score;
			
		}
		
		System.out.println("Total "+sum);
		
		in.close();
	}
	
}
