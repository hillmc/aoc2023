package aoc2023.d10;

public class MapNode {

	int x;
	int y;
	MapNode a;
	MapNode b;
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}

