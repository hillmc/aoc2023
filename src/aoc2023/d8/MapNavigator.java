package aoc2023.d8;

import java.io.FileInputStream;
import java.io.IOException;

import org.slinger.ascii.AsciiInputStream;

//the map is only a few hundred nodes, so might as well load it
public class MapNavigator {

	public static String directions (String filename) throws IOException {
		AsciiInputStream in = new AsciiInputStream(new FileInputStream(filename));
		String d = in.readLine();
		in.close();
		return d;
	}

	public static int travel(Map map, String directions) {
		MapNode start = map.get("AAA");
		MapNode cursor = start;
		int steps = 0;
		while (true) {
			for (char d : directions.toCharArray()) {
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
		}
	}
	public static void main(String[] args) throws IOException {
		Map map = new Map();
		map.loadMap("8/input");

		String directions = directions("8/input");
		
		int steps = travel(map, directions);
		System.out.println(steps);
	}
}
