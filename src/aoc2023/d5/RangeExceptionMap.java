package aoc2023.d5;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import org.slinger.ascii.AsciiInputStream;

//returns the same value as the key except for specific ranges
public class RangeExceptionMap {

	class ExceptionRange {
		
		long startkey;
		long startvalue;
		long count;
		
		public ExceptionRange(long astartkey, long astartvalue, long acount) {
			startkey = astartkey;
			startvalue = astartvalue;
			count = acount;
		}
		
		public boolean isKeyInRange(long key) {
			return ((key >= startkey) && (key < startkey+count));
		}
		
		public long get(long key) {
			if (!isKeyInRange(key)) throw new IllegalArgumentException("key not in range "+startkey+" - "+(startkey+count));
			return key-startkey+startvalue;
		}

		public boolean isValueInRange(long value) {
			return ((value >= startvalue) && (value < startvalue+count));
		}
		
		public long getKey(long value) {
			if (!isValueInRange(value)) throw new IllegalArgumentException("key not in range "+startkey+" - "+(startkey+count));
			return value-startvalue+startkey;
		}
}
	
	String name;
	Vector<ExceptionRange> exceptions = new Vector();
	
	public RangeExceptionMap(String aname) {
		name = aname;
	}

	public long get(long key) {
		for (ExceptionRange er : exceptions) {
			if (er.isKeyInRange(key)) return er.get(key);
		}
		return key;
	}

	public long getKey(long value) {
		for (ExceptionRange er : exceptions) {
			if (er.isValueInRange(value)) return er.getKey(value);
		}
		return -1; //mo value found
		
	}
	
	public long noisyget(long key) {
		long value = get(key);
		System.out.println("["+name+" "+key+"->"+value+"]");
		return value;
	}

	public void loadExceptions(String filename) throws IOException {
		AsciiInputStream in = new AsciiInputStream(new FileInputStream(filename));

		in.readLine(); //headerline
		while (!in.isEOFile()) {
			long startvalue = in.readInt(' ');
			long startkey = in.readInt(' ');
			long count = in.readInt();
			ExceptionRange er = new ExceptionRange(startkey,  startvalue, count);
			exceptions.add(er);
			//System.out.println(startkey+" -> "+startvalue+" for "+count);
				
			in.readToEOL();
		}
		in.close();
		//for (long i=0;i<100;i++) { System.out.println(i+" -> "+get(i)); }
	}
	

}
