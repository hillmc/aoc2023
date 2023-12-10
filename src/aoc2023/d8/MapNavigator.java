package aoc2023.d8;

import java.io.FileInputStream;
import java.io.IOException;

import org.slinger.ascii.AsciiInputStream;

/** AOC 2023 Day 8 - Part 1 navigating a node map */
public class MapNavigator {

	MapNode cursor = null;
	int steps = 0;

	public int travel(Map map, char[] directions) {
		MapNode start = map.get("AAA");
		cursor = start;
		while (true) {
			for (char d : directions) {
				System.out.print(cursor.name);
				if (d=='R') cursor = cursor.right;
				if (d=='L') cursor = cursor.left;
				System.out.print(" -> ");
				steps++;
				
				if (cursor.name.equals("ZZZ")) {
					System.out.println();
					System.out.println("Arrived!! in "+steps);
					return steps;
				}
			}
			System.out.print("\n["+steps+"] "); //restart directions, just show progress a bit
		}
	}

	public static String loadDirections (String filename) throws IOException {
		AsciiInputStream in = new AsciiInputStream(new FileInputStream(filename));
		String d = in.readLine();
		in.close();
		return d;
	}
	
	public static void main(String[] args) throws IOException {
		Map map = new Map();
		map.loadMap("8/input");

		String directions = loadDirections("8/input");

		MapNavigator nav = new MapNavigator();
		int steps = nav.travel(map, directions.toCharArray());
		System.out.println(steps);
	}
}
