package aoc2023.d5;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.slinger.ascii.AsciiInputStream;

/** Essentially a map that mostly returns itself, except by exception */
public class SparseExceptionMap {

	String name;
	HashMap<Integer, Integer> exceptions = new HashMap();
	
	public SparseExceptionMap(String aname) {
		name = aname;
	}
	
	public int get(int key) {
		Integer value = exceptions.get(key);
		if (value != null) return value;
		return key;
	}

	public int noisyget(int key) {
		int value = get(key);
		System.out.println("["+name+" "+key+"->"+value+"]");
		return value;
	}

	public void put(int key, int value) {
		exceptions.put(key, value);
	}
	
	public void loadExceptions(String filename) throws IOException {
		AsciiInputStream in = new AsciiInputStream(new FileInputStream(filename));

		in.readLine(); //headerline
		while (!in.isEOFile()) {
				int startvalue = (int) in.readInt(' ');
				int startkey = (int) in.readInt(' ');
				int count = (int) in.readInt();
				System.out.println(startkey+" -> "+startvalue+" for "+count);
				
				for (int key = startkey; key<startkey+count; key++) {
					put(key,startvalue);
					startvalue++;
				}
			in.readToEOL();
		}
		in.close();
		for (int i=0;i<100;i++) { System.out.println(i+" -> "+get(i)); }
	}
	
}
