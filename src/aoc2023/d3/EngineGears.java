package aoc2023.d3;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import org.slinger.ascii.AsciiInputStream;

public class EngineGears {
	public static void main(String[] args) throws IOException {
		
		AsciiInputStream in = new AsciiInputStream(new FileInputStream("3/input"));
		
		int sum =0;
		
		String thisLine = in.readLine();
		String nextLine = in.readLine();
		String lastLine = null;

		while (!thisLine.isEmpty()) {
			int cursor = 0;
			
			System.out.println(lastLine);
			System.out.println(thisLine);
			System.out.println(nextLine);

			while (cursor<thisLine.length()) {

				//find next asterisk
				while (cursor<thisLine.length() && !isGear(thisLine.charAt(cursor)) ) cursor = cursor +1;
				if (cursor>=thisLine.length()) break; //clumsy
				int gearPos = cursor;
				
				System.out.print("Gear at "+gearPos+": ");
				
				//we need exactly two gears. If there are more, there are too many
				Vector<Integer> gears = new Vector<Integer>();
				
				//find any number before it
				System.out.print("current:");
				addGear(gears, getNumberBefore(thisLine, gearPos));
				addGear(gears, getNumberAfter(thisLine, gearPos));
				
				//if the char directly above is a number, there can only be one number above;
				//or they may be two (on the diagonals) (or none)
				if (lastLine != null) {
					System.out.print(", last:");
					if (!isNumeric(lastLine.charAt(gearPos))) {
						addGear(gears, getNumberBefore(lastLine, gearPos));
						addGear(gears, getNumberAfter(lastLine, gearPos));
					}
					else {
						addGear(gears, getNumberAround(lastLine, gearPos));
					}
				}

				//in this case, at the end of file this will be a blank line
				if (nextLine.length()>1) {
					System.out.print(", next:");
					if (!isNumeric(nextLine.charAt(gearPos))) {
						addGear(gears, getNumberBefore(nextLine, gearPos));
						addGear(gears, getNumberAfter(nextLine, gearPos));
					}
					else {
						addGear(gears, getNumberAround(nextLine, gearPos));
					}
				}

				if (gears.size()==2) {
					System.out.println(" Gears "+gears.get(0)+"*"+gears.get(1));
					sum = sum + (gears.get(0)*gears.get(1));
				}
				else {
					System.out.println(" nothing ("+gears.size()+")");
				}
				cursor = cursor +1; //move on, start looking for next
			}
			
			lastLine = thisLine;
			thisLine = nextLine;
			nextLine = in.readLine();
			
		}
		
		System.out.println("Total "+sum);
		
		in.close();
	}
	
	public static Integer getNumberBefore(String line, int startPos) {
		//System.out.print("looking before "+startPos);
		int cursor = startPos-1;
		while (cursor>=0 && isNumeric(line.charAt(cursor))) cursor = cursor -1;
		try {
			Integer i = Integer.parseInt(line.substring(cursor+1, startPos));
			System.out.print(" found "+(cursor+1)+"-"+startPos+"("+i+"), ");
			return i;
		}
		catch (NumberFormatException nfe) {
			return null;
		}
	}

	public static Integer getNumberAfter(String line, int startPos) {
		//System.out.print("looking after "+startPos);
		int cursor = startPos+1;
		while (cursor<line.length() && isNumeric(line.charAt(cursor)))  cursor = cursor +1;
		try {
			Integer i= Integer.parseInt(line.substring(startPos+1, cursor));
			System.out.print(" found "+(startPos+1)+"-"+cursor+"("+i+"), ");
			return i;
		}
		catch (NumberFormatException nfe) {
			return null;
		}
	}

	public static Integer getNumberAround(String line, int startPos) {
		//System.out.print("looking around "+startPos);
		int end = startPos+1;
		while (end<line.length() && isNumeric(line.charAt(end)))  end = end +1;
		int start = startPos-1;
		while (start>0 && isNumeric(line.charAt(start)))  start = start -1;
		try {
			System.out.print("found "+(start+1)+"-"+end+" ("+line.substring(start+1,end)+")");
			Integer i= Integer.parseInt(line.substring(start+1, end));
			return i;
		}
		catch (NumberFormatException nfe) {
			return null;
		}
	}
	
	public static void addGear(Vector<Integer> gears, Integer gear) {
		if (gear != null) {
			gears.add(gear);
			System.out.print(" added "+gear);
		}
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
