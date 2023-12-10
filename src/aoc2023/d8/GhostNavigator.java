package aoc2023.d8;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slinger.ascii.AsciiInputStream;

import net.mchill.util.TimeStamp;

//the map is only a few hundred nodes, so might as well load it
public class GhostNavigator {

	public static String directions (String filename) throws IOException {
		AsciiInputStream in = new AsciiInputStream(new FileInputStream(filename));
		String d = in.readLine();
		in.close();
		return d;
	}
	
	public static void dumpGhostLocations(MapNode[] ghostCursors) {
		int numArrived = 0;
		for (MapNode cursor : ghostCursors) {
			System.out.print(cursor.name);
			if (cursor.name.endsWith("Z")) {
				numArrived++;
				System.out.print("!"); 
			}
			else {
				System.out.print(" ");
			}
		}
		System.out.println(numArrived);
		
	}

	public static long travel(Map map, char[] directions) {
		TimeStamp t = new TimeStamp();
		List<MapNode> ghostCursorList = new ArrayList<MapNode>();
		for (String nodeId : map.keySet()) {
			if (nodeId.endsWith("A")) {
				ghostCursorList.add(map.get(nodeId));
			}
		}
		//copy to array for performance
		MapNode[] ghostCursors = new MapNode[ghostCursorList.size()];
		for (int i=0;i<ghostCursors.length;i++) {
			ghostCursors[i] = ghostCursorList.get(i);
		}
		int numGhosts = ghostCursors.length;
//to test, just run on the first ghost		numGhosts = 1;
		
		System.out.println("Ghosts: "+numGhosts);
		dumpGhostLocations(ghostCursors);
		
		long steps = 0;
		while (true) {
			for (char d : directions) {
				steps++;
				boolean arrived = true;
				for (int i=0;i<numGhosts;i++) {
					MapNode cursor = ghostCursors[i];
					if (d=='R') cursor = cursor.right;
					if (d=='L') cursor = cursor.left;
					ghostCursors[i] = cursor;

					if (!(cursor.name.charAt(2)=='Z')) arrived = false;
				}

				if (arrived) return steps;

				//progress monitor, this takes ages
				if ((steps % 10000000)==0) {
					System.out.print("."); //ten million
					if ((steps % 1000000000)==0) {
						System.out.println(" "+t.getSecsSince()+"s "+(steps/1000000000L)+"x10^9");; 
					}
				}
				//bit of a cheat; 100 000 000 000 000 is too high in answers, 
				//and 10 000 000 000 was too low so just wait
//				System.out.println();
			}
		}
	}
	public static void main(String[] args) throws IOException {
		Map map = new Map();
		map.loadMap("8/input");

		String directions = directions("8/input");
		
		long steps = travel(map, directions.toCharArray());
		System.out.println(steps);
	}
}
