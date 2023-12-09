package aoc2023.d3;

import java.io.FileInputStream;
import java.io.IOException;

import org.slinger.ascii.AsciiInputStream;

import net.mchill.text.StringUtil;

public class EngineParts {
	public static void main(String[] args) throws IOException {
		
		AsciiInputStream in = new AsciiInputStream(new FileInputStream("3/input"));
		
		int sum =0;
		
		String thisLine = in.readLine();
		String nextLine = in.readLine();
		String lastLine = null;

		while (!thisLine.isEmpty()) {
			int cursor = 0;
			
			System.out.println(thisLine);

			while (cursor<thisLine.length()) {

				//find next digit
				while (cursor<thisLine.length() && !isNumeric(thisLine.charAt(cursor)) ) cursor = cursor +1;
				if (cursor>=thisLine.length()) break; //clumsy
				int partStart = cursor;
				
				//find next non-digit
				while (cursor<thisLine.length() && isNumeric(thisLine.charAt(cursor)) ) cursor = cursor +1;
				int partEnd = cursor;

				String partNum = thisLine.substring(partStart, partEnd); 
				
				boolean partIsValid = false;
				if (lastLine != null) {
					int start = Math.max(0,  partStart-1);
					int end = Math.min(partEnd+1, lastLine.length());
					for (int i=start;i<end;i++) {
						if (isSymbol(lastLine.charAt(i))) {
							partIsValid = true;
						}
					}
				}
				if (partStart>0 && isSymbol(thisLine.charAt(partStart-1))) partIsValid = true;
				if (partEnd<thisLine.length() && isSymbol(thisLine.charAt(partEnd))) partIsValid = true;

				//in this case, at the end of file this will be a blank line
				if (nextLine.length()>1) {
					int start = Math.max(0,  partStart-1);
					int end = Math.min(partEnd+1, nextLine.length());
					for (int i=start;i<end;i++) {
						if (isSymbol(nextLine.charAt(i))) {
							partIsValid = true;
						}
					}
				}

				if (partIsValid) {
					System.out.print(partNum+" Y / ");  
					sum = sum + Integer.parseInt(partNum);
				}
				else System.out.print(partNum+" N / ");
	
			}
			
			System.out.println(sum);

			lastLine = thisLine;
			thisLine = nextLine;
			nextLine = in.readLine();
			
		}
		
		System.out.println("Total "+sum);
		
		in.close();
	}
	
	/** Returns true if it's a 'diagram' - ie not a number or a dot */
	public static boolean isSymbol(char c) {
		return !("1234567890.".contains(""+c));
	}

	/** Returns true if it's a 'gear' - ie a star */
	public static boolean isGear(char c) {
		return (c=='*');
	}

	public static boolean isNumeric(char c) {
		return ("1234567890".contains(""+c));
	}
	
	public static void dumpFields(String[] fields) {
		for (String f : fields) System.out.print(f+"/");
	}
	
}
