package aoc2023.d5;

import java.util.Vector;

public class UsefulSeeds {

	class Range {
		public long start;
		public long count;
		
		public Range(long astart, long acount) {
			start = astart;
			count = acount;
		}
		
		//inclusive exclusive question here. 50-52 (start 50, count 3) 
		public boolean isInRange(long value) {
			return ((value>=start) && (value<(start+count)));
		}
	}
	
	Vector<Range> ranges = new Vector();
	
	public void addRange(long start, long count) {
		ranges.add(new Range(start, count));
	}
	
	public boolean isInRange(long value) {
		for (Range r : ranges) {
			if (r.isInRange(value)) return true;
		}
		return false;
	}
}
