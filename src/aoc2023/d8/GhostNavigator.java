package aoc2023.d8;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.mchill.text.StringUtil;
import net.mchill.util.TimeStamp;

/** Inspecting the ghost movements.
 * 
 * The key thing to note here is that because each ghost has to carry on travelling
 * if any of the other ghosts haven't arrived at a Z, then when each ghost reaches a Z the
 * directions from there (whether they go left or right) must take you back to a Z. 
 * 
 * So here we look at each ghost and see what the characteristis fo that is like.
 * 
 * OK, so "it just so happens" that the number of steps to go from **A to **Z for each is the same as
 * going from **Z to **Z. Each ghost has it's own target **Z; that is, they do not cross each other's.
 * And there are no left/right variatiosn (I presume).
 * 
 * So what we have now is how many steps will it take before each of these synchronizes.
 * 
 * That is, it has to be that each of the ghost steps can divide into, ie the steps will be a multiple of all the ghost cycles
 * We can just multiply all of them together, but we want the first time it happens, so that's the smallest multiple
 * 
 *  see eg "LCM" Least Common Multiples
 *  
       19951                            19831294 DQM KJZ!MQM SQM MHN KBG 1
             20513                      19836071 LVS XXQ XTZ!CTH JPT XLC 1
 13207                                  19836914 ZZZ!XNR CGM CQN SLV JGJ 1
                   14893                19837476 CRJ CNK TKM XVZ!SJM NJB 1
                               12083    19840286 PTV RSH BMD CVH QGK SCZ!1
                         22199          19845906 MTD VXV KTG MJJ LDZ!BGC 1
 *  
 *  This gives 12,324,145,107,121
 *  
 *  12324145107121
 *  * 
 */
public class GhostNavigator {

	MapNode[] ghostCursors;
	int steps =0;
	
	public void dumpGhostLocations() {
		int numArrived = 0;
		
		System.out.print(StringUtil.padSpaceL(""+steps, 12)+" ");
		
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

	public void setGhostStarts(Map map) {
		List<MapNode> ghostCursorList = new ArrayList<MapNode>();
		for (String nodeId : map.keySet()) {
			if (nodeId.endsWith("A")) {
				ghostCursorList.add(map.get(nodeId));
			}
		}
		
		//for investigating, select particular ones
		//ghostCursorList.remove(5);
		//ghostCursorList.remove(4);
		//ghostCursorList.remove(3);
		//ghostCursorList.remove(2);
		
		//copy to array for performance
		ghostCursors = new MapNode[ghostCursorList.size()];
		for (int i=0;i<ghostCursors.length;i++) {
			ghostCursors[i] = ghostCursorList.get(i);
		}
	}
	
	public long travel(Map map, char[] directions) {
		setGhostStarts(map);
		int numGhosts = ghostCursors.length;
//to test, just run on the first ghost (starts on AAA) and compare answer with Part 1 answer numGhosts = 1;
		
		System.out.println("Ghosts: "+numGhosts);
		dumpGhostLocations();
		
		System.out.println("Travelling for enough steps to capture all cycles...");
		steps = 0;
		int[] markGhostSteps = new int[numGhosts]; //auto 0
		int[] ghostCycleLength = new int[numGhosts];
		while (steps<100000) {
			for (char d : directions) {
				steps++;
				int numArrived = 0;
				for (int i=0;i<numGhosts;i++) {
					MapNode cursor = ghostCursors[i];
					if (d=='R') cursor = cursor.right;
					if (d=='L') cursor = cursor.left;
					ghostCursors[i] = cursor;

					boolean hasGhostArrived = cursor.name.charAt(2)=='Z';
					
					if (hasGhostArrived) numArrived++;
				}

				if (numArrived>0) {
					for (int g=0;g<numGhosts;g++) {
						if (ghostCursors[g].name.charAt(2)=='Z') {
							ghostCycleLength[g] = steps-markGhostSteps[g];
							System.out.print(StringUtil.padSpaceL(""+(ghostCycleLength[g]), 6)); //number of steps since last one
							markGhostSteps[g] = steps;
						}
						else {
							System.out.print(StringUtil.padSpaceL("", 6)); //leave blank if not arrived to reduce clutter
						}
					}
					dumpGhostLocations();
				}
			}
		}
		
		//we should now have the cycle lengths for each
		System.out.print("Cycle lengths: ");
		for (int g=0;g<numGhosts;g++) {
			System.out.print(ghostCycleLength[g]+" ");
		}
		System.out.println();
		
		System.out.println("Finding Least Common Multiple...");
		TimeStamp ts = new TimeStamp();
		long lcm = ghostCycleLength[0]; //in principle pick the biggest one but this should do
		int progCount = 0;
		while (true) {
			//progCount++; if (progCount>100000) { System.out.println(StringUtil.humanNumCommas(lcm)); progCount=0; }
			boolean isLCM = true;
			for (int g=1;g<numGhosts;g++) {
				if ((lcm % ghostCycleLength[g]) !=0) isLCM = false;  
			}
			if (isLCM) {
				System.out.println("Found LCM "+lcm+" in "+ts.getSecsSince()+"s");
				return lcm;
			}
			lcm = lcm + ghostCycleLength[0];
		}
		
	}
	public static void main(String[] args) throws IOException {
		Map map = new Map();
		map.loadMap("8/input");

		String directions = MapNavigator.loadDirections("8/input");
		
		GhostNavigator nav = new GhostNavigator();
		long steps = nav.travel(map, directions.toCharArray());
		System.out.println(steps);
	}
}
