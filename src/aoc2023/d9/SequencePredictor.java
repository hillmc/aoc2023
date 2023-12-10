package aoc2023.d9;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slinger.ascii.AsciiInputStream;

import net.mchill.text.StringUtil;

public class SequencePredictor {

	public static class Line {
		List<Long> sequence = new ArrayList<Long>();
		Line diffLine = null;
	}
	
	public static void dumpLine(Line line) {
		dumpLine(0,line);
	}

	public static void dumpLine(int off, Line line) {
		System.out.print(StringUtil.padSpaceL("", off));
		for (Long l : line.sequence) {
			System.out.print(l+" ");
		}
		System.out.println();
		if (line.diffLine != null) dumpLine(off+2,line.diffLine);
	}
	
	
	public static List<Long> readSequence(AsciiInputStream in) throws IOException {
		List<Long> sequence = new ArrayList<Long>(); 
		while (!in.isEOLine()) {
			Long e = in.readInt(' ');
			sequence.add(e);
		}
		in.readToEOL();
		return sequence;
	}
	
	
	public static void findDiffs(Line line) {
		List<Long> diffSequence = new ArrayList<Long>(line.sequence.size());
		boolean isAllZero=true;
		for (int i=0;i<line.sequence.size()-1;i++) {
			long diff = line.sequence.get(i+1) - line.sequence.get(i);
			diffSequence.add(diff);
			if (diff != 0) isAllZero=false;
		}
		if (isAllZero) return;
		
		line.diffLine = new Line();
		line.diffLine.sequence = diffSequence;
		
		//and recurse to construct the diff of this diff
		findDiffs(line.diffLine);
	}
	
	public static long predict(Line line) {
		if (line.diffLine != null) {
			long diff = predict(line.diffLine);
			long prediction = line.sequence.get(line.sequence.size()-1)+diff;
			line.sequence.add(prediction);
			return prediction;
		}
		//next line would be 0s, so this line is all the same; return last value which is first
		return line.sequence.get(0);
	}
	
	/** add onto beginning */
	public static long hindcast(Line line) {
		if (line.diffLine != null) {
			long diff = hindcast(line.diffLine);
			long prediction = line.sequence.get(0)-diff;
			line.sequence.add(0,prediction); //add to beginning, performance not a problem here
			return prediction;
		}
		//next line would be 0s, so this line is all the same; return last value which is first
		return line.sequence.get(0);
		
	}
	
	public static void main(String[] args) throws IOException {
		AsciiInputStream in = new AsciiInputStream(new FileInputStream("9/input"));
		
		long sum = 0;
		while (!in.isEOFile()) {
			System.out.println("---");
			List<Long> sequence = readSequence(in);
			Line line = new Line();
			line.sequence = sequence;
			findDiffs(line);
	
			dumpLine(line);
			
			//predict(line); part 1
			hindcast(line); 
			dumpLine(line);

//part 1			long predicted = line.sequence.get(line.sequence.size()-1); //new last value 
			long predicted = line.sequence.get(0); //new first value 
			System.out.println("---> "+predicted);
			
			sum = sum + predicted; 
		}
		System.out.println(sum);
		
		
		in.close();
		
	}
	
}
