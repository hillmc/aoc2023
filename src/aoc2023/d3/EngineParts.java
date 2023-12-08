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
				while (cursor<thisLine.length() && !StringUtil.isNumeric(thisLine.charAt(cursor)) ) cursor = cursor +1;
				if (cursor>=thisLine.length()) break; //clumsy
				int partStart = cursor;
				
				//find next non-digit
				while (cursor<thisLine.length() && StringUtil.isNumeric(thisLine.charAt(cursor)) ) cursor = cursor +1;
				int partEnd = cursor;

				String partNum = thisLine.substring(partStart, partEnd); 
				System.out.print(partNum);
				
				boolean partIsValid = false;
				int pos = thisLine.indexOf(partNum); 
				if (lastLine != null) {
					int start = Math.max(0,  pos-1);
					int end = Math.min(pos+partNum.length()+1, lastLine.length());
					for (int i=start;i<end;i++) {
						if (isSymbol(lastLine.charAt(i))) {
							partIsValid = true;
						}
					}
				}
				if (pos>0 && isSymbol(thisLine.charAt(pos-1))) partIsValid = true;
				if ((pos+partNum.length())<thisLine.length() && isSymbol(thisLine.charAt(pos+partNum.length()))) partIsValid = true;

				//in this case, at the end of file this will be a blank line
				if (nextLine.length()>1) {
					int start = Math.max(0,  pos-1);
					int end = Math.min(pos+partNum.length()+1, nextLine.length());
					for (int i=start;i<end;i++) {
						if (isSymbol(nextLine.charAt(i))) {
							partIsValid = true;
						}
					}
				}

				if (partIsValid) {
					System.out.print(" Y / ");  
					sum = sum + Integer.parseInt(partNum);
				}
				else System.out.print(" N / ");
	
			}
			
			System.out.println();

			lastLine = thisLine;
			thisLine = nextLine;
			nextLine = in.readLine();
			
		}
		
		System.out.println("Total "+sum);
	}
	
	/** Returns true if it's a 'diagram' - ie not a number or a dot */
	public static boolean isSymbol(char c) {
		return !("01234567890.".contains(""+c));
	}
	
	public static void dumpFields(String[] fields) {
		for (String f : fields) System.out.print(f+"/");
	}
	
}
