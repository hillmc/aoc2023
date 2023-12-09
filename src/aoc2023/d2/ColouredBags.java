package aoc2023.d2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slinger.ascii.AsciiInputStream;

/*
Game 1: 20 green, 3 red, 2 blue; 9 red, 16 blue, 18 green; 6 blue, 19 red, 10 green; 12 red, 19 green, 11 blue
Game 2: 12 green, 3 blue, 16 red; 6 red, 4 blue, 12 green; 11 green, 4 red, 3 blue; 8 green, 15 red, 5 blue
Game 3: 13 blue, 4 red, 8 green; 2 green, 4 red, 19 blue; 5 blue; 10 blue, 6 green, 2 red; 19 blue; 8 blue, 6 red

 */

public class ColouredBags {

	public static void main(String[] args) throws IOException {
		AsciiInputStream in = new AsciiInputStream(new FileInputStream("2/input"));
		
		int sum =0;
		
		do {
			String line = in.readLine();
			String[] fields = line.split("[:,; ]+");

			if (fields.length<3) break; //end of file, pop out
			
			dumpFields(fields);

			int maxRed = 0;
			int maxBlue = 0;
			int maxGreen = 0;
			
			int game = Integer.parseInt(fields[1]);
			
			for (int i=2; i<fields.length-1; i=i+2) {
				int red =0; int blue =0; int green = 0;
				if (fields[i+1].equals("red")) red = Integer.parseInt(fields[i]);
				else if (fields[i+1].equals("blue")) blue = Integer.parseInt(fields[i]);
				else if (fields[i+1].equals("green")) green = Integer.parseInt(fields[i]);
				else {
					System.out.println("Unknown colour "+fields[i+1]);
				}
				
				if (maxRed<red) maxRed = red;
				if (maxBlue<blue) maxBlue = blue;
				if (maxGreen<green) maxGreen = green;

			}
			System.out.println("MAX R"+maxRed+", B"+maxBlue+", G"+maxGreen);
			
			int power = maxRed*maxBlue*maxGreen;
			sum=sum+power;
		}
		while (!in.isEOFile());
		
		System.out.println("Total "+sum);
		
		in.close();
		
	}
	
	public static void dumpFields(String[] fields) {
		for (String f : fields) System.out.print(f+"/");
	}
	
}
