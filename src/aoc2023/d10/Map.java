package aoc2023.d10;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.slinger.ascii.AsciiInputStream;

public class Map extends HashMap<String, MapNode>{

	public void loadMap(String filename) throws IOException {
		AsciiInputStream in = new AsciiInputStream(new FileInputStream(filename));

		while (!in.isEOFile()) {
			String mapLine = in.readString(3);
			if (mapId.length()<2) break; //last line
			MapNode node = new MapNode();
			node.name = mapId;
			put(mapId, node);
			in.readToEOL();
		}
		in.close();

		//second pass, read the lefts and rights and insert
		in = new AsciiInputStream(new FileInputStream(filename));
		in.readLine();
		in.readLine();
		while (!in.isEOFile()) {
			String line = in.readLine();
			if (line.length()<2) break; //last line
			String nodeId = line.substring(0,3);
			String leftId = line.substring(7,10);
			String rightId = line.substring(12,15);

			System.out.println(nodeId+"/"+leftId+"/"+rightId);
			
			get(nodeId).left = get(leftId);
			get(nodeId).right = get(rightId);
			
		}
		in.close();

		
	}

	public static void main(String[] args) throws IOException {
		Map map = new Map();
		map.loadMap("8/input");
	}
}