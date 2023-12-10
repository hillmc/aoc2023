package aoc2023.d8;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.mchill.text.StringUtil;
import net.mchill.util.TimeStamp;

/** Follow all the paths - this is a 'pure' approach and assumes the map and directions are 
 * not cyclic, but its runtime is far too long on any ordinary machine. 
 */
public class BruteGhostNavigator  {

	MapNode[] ghostCursors;
	int steps =0;
	
	public void dumpGhostLocations() {
		int numArrived = 0;
		
		System.out.print(StringUtil.padSpaceL(""+steps, 12));
		
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

	public long travel(Map map, char[] directions) {
		
		//to track progress and estimate runtimes as this takes ages.
		//which should be a clue I'm doing it wrong
		TimeStamp ts = new TimeStamp();
		
		//find all the nodes that end with 'A', these are the ghost starting positions
		List<MapNode> ghostCursorList = new ArrayList<MapNode>();
		for (String nodeId : map.keySet()) {
			if (nodeId.endsWith("A")) {
				ghostCursorList.add(map.get(nodeId));
			}
		}
		//copy to array for performance. Marginal. 
		ghostCursors = new MapNode[ghostCursorList.size()];
		for (int i=0;i<ghostCursors.length;i++) {
			ghostCursors[i] = ghostCursorList.get(i);
		}
		int numGhosts = ghostCursors.length;
		
		numGhosts = 5; //to test, run on subsets of the ghosts		
		
		//1 took 0s			   13,207
		//2 took 0s           937,697 x72 
		//3 took 1-2s,     68,451,881 x73
		//4 took 100s   3,627,949,693 x53
		//5 (est 100x100/60 mins = 2hours ish)
		// --> which makes 6 ghosts 2 hours x100 is a bit too long.

		//bit of a cheat; 100 000 000 000 000 is too high in answers, 
		//and 10 000 000 000 was too low so just wait

		System.out.println("Ghosts: "+numGhosts);
		dumpGhostLocations();
		
		int fineProgCount=0;//progress count
		int coarseProgCount=0;
		while (true) {
			//follow directions
			for (char d : directions) {
				steps++;
				boolean allArrived = true;
				for (int i=0;i<numGhosts;i++) {
					MapNode cursor = ghostCursors[i];
					if (d=='R') cursor = cursor.right;
					if (d=='L') cursor = cursor.left;
					ghostCursors[i] = cursor;

					if (!(cursor.name.charAt(2)=='Z')) allArrived = false;
				}

				if (allArrived) return steps;

				fineProgCount++;
				if (fineProgCount>10000000) {
					System.out.print(".");
					fineProgCount=0;
					coarseProgCount++;
					if (coarseProgCount>100) {
						System.out.println(" "+ts.getSecsSince()+"s "+(steps/1000000000L)+"x10^9");; 
						coarseProgCount = 0;
					}
				}
				
			}
		}
	}
	public static void main(String[] args) throws IOException {
		Map map = new Map();
		map.loadMap("8/input");

		String directions = MapNavigator.loadDirections("8/input");

		BruteGhostNavigator bruteNav = new BruteGhostNavigator();
		long steps = bruteNav.travel(map, directions.toCharArray());
		System.out.println(StringUtil.humanNumCommas(steps)+" ("+steps+")");
	}
}
