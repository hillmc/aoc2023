package aoc2023.d6;

/** Day 6 of the Advent of Code puzzle - the boat race */
public class BoatRace {

	/*
	 * distance = speed * time.
	 * In this case speed = time that button is down, and time is remaining time after button released
 	 * distance travelled = (buttonTime) * (raceLengthTime-buttonTime)
	 * multiply out buttonTime
	 * -> travelled = raceLengthTime*buttonTime - buttonTime^2
	 * gives the quadratic equation
	 * -> buttonTime^2 - raceLengthTime*buttonTime + travelled = 0;
	 * probably have to solve that using the infamous quadratic equation solving equation
	 */

	/** Quadratic equation solver. All in double as some of the numbers can get big */
	public static double[] solveQuad(double a, double b, double c) {
		double[] answers = new double[2];
		answers[0] = (-b - Math.sqrt(b*b - 4*a*c))/(2*a);
		answers[1] = (-b + Math.sqrt(b*b - 4*a*c))/(2*a);
		return answers;
	}
	
	/** calculate number of seconds that holding power button would beat given record */
	public static int secsRangeToBeat(long raceLengthTime, long recordTravel) {
		//get the time the button was held down for to get the record (which will be between integer values)
		double[] recordButtonTimes = solveQuad(1,-raceLengthTime, recordTravel); 
		System.out.print(raceLengthTime+"/"+recordTravel+":"+recordButtonTimes[0]+","+recordButtonTimes[1]);
		//get the number of whole seconds (ie integers) *between* the record button value times.
		//this needs to cope with integer values; ie where the record time was held by holding down eg 5s.
		int secsRange = (int) Math.ceil(recordButtonTimes[1]) - (int) Math.floor(recordButtonTimes[0])-1; 
		System.out.println(" -> "+secsRange);
		return secsRange;
	}
	
	public static void testEx1() {
		int sum = secsRangeToBeat(7, 9);
		sum = sum * secsRangeToBeat(15, 40);
		sum = sum * secsRangeToBeat(30, 200);
		System.out.println(sum);
	}

	public static void testEx2() {
		int sum = secsRangeToBeat(71530, 940200);
		System.out.println(sum);
	}

	public static void realRaces1() {
//		Time:        63     78     94     68
//		Distance:   411   1274   2047   1035
		int sum = secsRangeToBeat(63, 411);
		sum = sum * secsRangeToBeat(78, 1274);
		sum = sum * secsRangeToBeat(94, 2047);
		sum = sum * secsRangeToBeat(68, 1035);
		System.out.println(sum);
	}

	public static void realRaces2() {
//		Time:        63     78     94     68
//		Distance:   411   1274   2047   1035
		int sum = secsRangeToBeat(63789468, 411127420471035L);
		System.out.println(sum);
	}

	public static void main(String[] args) {
		realRaces2();
	}
}
