package aoc2023.d8;

public class MapNode {

	String name;
	MapNode left;
	MapNode right;
	
	@Override
	public String toString() {
		return name+" L"+left.name+" R"+right.name;
	}
}

