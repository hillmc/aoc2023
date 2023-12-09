package aoc2023.d5;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import org.slinger.ascii.AsciiInputStream;

import net.mchill.util.TimeStamp;

//probably too much cut and pasting; I would to this for real
//as it helps maintenance to know what expclitily you're dealing with
//at each stage, but in principle these could all be an array of maps
public class SeedsLocator {

	RangeExceptionMap seeds2soil = new RangeExceptionMap("seed2soil"); //the position is the seed, the value the soil
	RangeExceptionMap soil2fert = new RangeExceptionMap("soil2fert");
	RangeExceptionMap fert2water= new RangeExceptionMap("fert2water");
	RangeExceptionMap water2light= new RangeExceptionMap("water2light");
	RangeExceptionMap light2temp= new RangeExceptionMap("light2temp");
	RangeExceptionMap temp2humid= new RangeExceptionMap("temp2humid");
	RangeExceptionMap humid2location= new RangeExceptionMap("humid2loc");
	
	public SeedsLocator(String source) throws IOException {

		//load up exceptions
		seeds2soil.loadExceptions("5/"+source+"/seed2soil");
		soil2fert.loadExceptions("5/"+source+"/soil2fert");
		fert2water.loadExceptions("5/"+source+"/fert2water");
		water2light.loadExceptions("5/"+source+"/water2light");
		light2temp.loadExceptions("5/"+source+"/light2temp");
		temp2humid.loadExceptions("5/"+source+"/temp2humid");
		humid2location.loadExceptions("5/"+source+"/humid2location");
		
	}

	public long getLocationForSeed(long seed) {
		return humid2location.get(temp2humid.get(light2temp.get(water2light.get(fert2water.get(soil2fert.get(seeds2soil.get(seed)))))));
	}

	public long getSeedForLocation(long loc) {
		return seeds2soil.getKey(soil2fert.getKey(fert2water.getKey(water2light.getKey(light2temp.getKey(temp2humid.getKey(humid2location.getKey(loc)))))));
	}

	public long getLocationForSeedNoisily(long seed) {
		return humid2location.noisyget(temp2humid.noisyget(light2temp.noisyget(water2light.noisyget(fert2water.noisyget(soil2fert.noisyget(seeds2soil.noisyget(seed)))))));
	}
	public static void test() throws IOException {
		SeedsLocator locator = new SeedsLocator("example");
		locator.getLocationForSeed(79);
		locator.getLocationForSeed(14);
		locator.getLocationForSeed(55);
		locator.getLocationForSeed(13);
		
	}

	public static void testRange() throws IOException {
		SeedsLocator locator = new SeedsLocator("example");
		for (long seed=79;seed<=79+14;seed++) {
			locator.getLocationForSeedNoisily(seed);
		}
		for (long seed=55;seed<=55+13;seed++) {
			locator.getLocationForSeedNoisily(seed);
		}
		
	}
	public static void realIndividual() throws IOException {
		SeedsLocator locator = new SeedsLocator("input");
		AsciiInputStream in = new AsciiInputStream(new FileInputStream("5/input/input"));
		String s = in.readString(' ');
		long minLoc = -1;
		while (!in.isEOLine()) {
			long seed = in.readInt(' ');
			long loc = locator.getLocationForSeed(seed);
			if (minLoc==-1) minLoc = loc; else minLoc = Math.min(minLoc,  loc);
			System.out.println("  Seed "+Long.toUnsignedString(seed)+" -> "+Long.toUnsignedString(loc));
		}
		System.out.println("Closest "+Long.toUnsignedString(minLoc));
		in.close();
	}

	public static void realRange() throws IOException {
		SeedsLocator locator = new SeedsLocator("input");
		AsciiInputStream in = new AsciiInputStream(new FileInputStream("5/input/input"));
		String s = in.readString(' ');
		long minLoc = -1;
		TimeStamp overall = new TimeStamp();
		while (!in.isEOLine()) {
			TimeStamp t = new TimeStamp();
			long seedStart = in.readInt(' ');
			long seedRange = in.readInt(' ');
			System.out.println("  Seed "+Long.toUnsignedString(seedStart/1000000)+"M-"+Long.toUnsignedString((seedStart+seedRange)/1000000)+"M ("+(seedRange/1000000)+"M)");
			System.out.flush();
			for (long seed=seedStart;seed<=seedStart+seedRange;seed++) {
				long loc = locator.getLocationForSeed(seed);
				if (minLoc==-1) minLoc = loc; else minLoc = Math.min(minLoc,  loc);
			}
			System.out.println("  --> Loc "+minLoc+" "+t.getSecsSince()+"s "+Long.toUnsignedString((seedRange/1000000)/t.getSecsSince())+"M/s");
		}
		System.out.println("Closest "+Long.toUnsignedString(minLoc)+" in "+overall.getTimeSince());
		in.close();
	}

	/** in this one we're going to load up a range of OK seeds, and then
	 * work from 0 location outwards to see if we can find a valid seed from 
	 * the location
	 */
	public static void reverseRange() throws IOException {
		SeedsLocator locator = new SeedsLocator("input");
		UsefulSeeds useeds = new UsefulSeeds();
		TimeStamp overall = new TimeStamp();
		
		AsciiInputStream in = new AsciiInputStream(new FileInputStream("5/input/input"));
		String s = in.readString(' ');
		long minLoc = -1;
		while (!in.isEOLine()) {
			TimeStamp t = new TimeStamp();
			long seedStart = in.readInt(' ');
			long seedRange = in.readInt(' ');
			useeds.addRange(seedStart, seedRange);
		}
		in.close();

		//now look for closest location
		long seed = -1;
		while (seed==-1) {
			minLoc++;
			seed = locator.getSeedForLocation(minLoc);
			if ((minLoc % 1000000)==0) {
				System.out.print(".");
			}
			//is it a candidate seed?
			if (!useeds.isInRange(seed)) {
				seed = -1;
			}
		}
		System.out.println("Closest "+Long.toUnsignedString(minLoc)+" seed is "+seed+ " in "+overall.getTimeSince()+"s");
	}

	//looknig for 69841803
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		realRange();
	}
}
