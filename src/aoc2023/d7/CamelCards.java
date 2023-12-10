package aoc2023.d7;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slinger.ascii.AsciiInputStream;

public class CamelCards {

	public static String cardValueSet = "AKQJT98765432";
	public static String cardValueSet2 = "AKQT98765432*"; //for part 2 the joker goes at the end as the lowest
	public static String jokerSubstitutionSet = "AKQT98765432"; //for part 2 the joker goes at the end as the lowest
	
//	public class Card {
//		char value; //dirty, dirty public 
//	}
	
	public static class Hand implements Comparable<Hand> {
//		Card[] cards = new Card[5];
		char[] givenCards;
		char[] sortedCards = new char[5];
		
		int coarseStrength;
		String strength;
		
		int bid;
		
		public Hand(char[] cardValues, int abid) {
			if (cardValues.length != 5) throw new IllegalArgumentException("Need 5 cards");
			givenCards = cardValues;
			//copy in to sortedcards; we don't want a reference to teh same array
			for (int i=0;i<givenCards.length;i++) sortedCards[i] = givenCards[i]; 
			Arrays.sort(sortedCards);
			
			bid = abid;
			
			setCoarseStrength();
//			for (int i=0;i<cardValues.length;i++) {
//				cards[i] = new Card();
//				cards[i].value = cardValues[i];
//			}
		}

		
		
		//these routines are going to be called a lot
		//when sorting - ah wait, let's cache the coarse strength
		//as the hand doesn't change

		public int cardStrength(char card) {
//part 1			return 20-cardValueSet.indexOf(card);
			return 20-cardValueSet2.indexOf(card); //part 2, joker at end
		}
		
		public int compareTo(Hand otherHand) {
			if (this.coarseStrength>otherHand.coarseStrength) return 1;
			if (this.coarseStrength<otherHand.coarseStrength) return -1;
			
			//replace 
			
			for (int card=0;card<5;card++) {
				if (cardStrength(this.givenCards[card])>cardStrength(otherHand.givenCards[card])) return 1;
				if (cardStrength(this.givenCards[card])<cardStrength(otherHand.givenCards[card])) return -1;
			}
			return 0; //same hand
		}

		/*
		public boolean isCardEqual(char card1, char card2) {
			return (card1==card2) 
//no can't do this as J might be one thing for one side and another on the other					|| (card1=='J') || (card2=='J'); //part 2 - if either is a wildcard J, it matches
		}
		*/
		
	
		public String toString() {
			return new String(givenCards)+" "+new String(sortedCards)+" -> "+strength+" ("+bid+")";
		}

		/** Part 1 version
		public void setCoarseStrength() {
			if (isFiveOfAKind()) 	{ coarseStrength = 50; strength = "FiveOfAKind"; return; }
			if (isFourOfAKind()) 	{ coarseStrength = 45; strength = "FourOfAKind"; return; }
			if (isFullHouse()) 		{ coarseStrength = 40; strength = "FullHouse  "; return; }
			if (isThreeOfAKind()) 	{ coarseStrength = 35; strength = "ThreeOfAKind"; return; }
			if (isTwoPairs()) 		{ coarseStrength = 30; strength = "TwoPairs"; return; }
			if (isOnePair()) 		{ coarseStrength = 25; strength = "OnePair"; return; }
			if (isHighCard()) 		{ coarseStrength = 20; strength = "HighCard"; return; }
			throw new IllegalArgumentException("What no ... what?");
		}
		*/

		/** Part 2 version for each joker (J) cylce through all options */
		public void setCoarseStrength() {
			int numWilds=0;
			while ((numWilds<5) && (sortedCards[numWilds]=='*')) numWilds++;

			if (numWilds==0) {
				coarseStrength = getCoarseStrength(sortedCards);
				return;
			}
				//something a bit recursive here; for each J we need to try all 
				//the options with all the other Js
				//ah but the sorting is now all wrong
				//there is at least one five-card J which 
				//if (sorted)
				//	aargh what about the all J; the 'fine' level checks need to happen or
				//	it will just be all 2s. Or we can start at the top and work down.
					
				//	so basically we want to rotate/count down from A to 2 for each J
			//all the Js are at the front fo the sorted as they are now asterisk
				//how many are there?
			coarseStrength = nextSubStrength(0, sortedCards);
				
		}
		

	}
	public static boolean isFiveOfAKind(char[] sortedCards) {
		return (sortedCards[0]==sortedCards[1]) &&
				(sortedCards[1]==sortedCards[2]) &&
				(sortedCards[2]==sortedCards[3]) &&
				(sortedCards[3]==sortedCards[4]); 
	}

	public static boolean isFourOfAKind(char[] sortedCards) {
		//the sorted cards will either have the four at the start or the end.
		return ((sortedCards[0]==sortedCards[1]) &&
				(sortedCards[1]==sortedCards[2]) &&
				(sortedCards[2]==sortedCards[3]))
				||
			   ((sortedCards[1]==sortedCards[2]) &&
				(sortedCards[2]==sortedCards[3]) &&
				(sortedCards[3]==sortedCards[4]));
	}

	//a three and a pair
	public static boolean isFullHouse(char[] sortedCards) {
		//the sorted cards will either have the three at the front or the two at the front
		return ((sortedCards[0]==sortedCards[1]) && //first
				(sortedCards[1]==sortedCards[2]) && //three
				(sortedCards[3]==sortedCards[4])) //last pair
				||
			   ((sortedCards[0]==sortedCards[1]) && //first pair
				(sortedCards[2]==sortedCards[3]) && //last
				(sortedCards[3]==sortedCards[4])); //three
	}

	//this will also return true if it's four or five or full house, so only ask if youve checked those first
	public static boolean isThreeOfAKind(char[] sortedCards) {
		//not neat but following the four version
		//the sorted cards will either have the fhree at the start, middle or end.
		return ((sortedCards[0]==sortedCards[1]) &&
				(sortedCards[1]==sortedCards[2]))
				||
			   ((sortedCards[1]==sortedCards[2]) &&
				(sortedCards[2]==sortedCards[3]))
			   ||
				((sortedCards[2]==sortedCards[3]) &&
				(sortedCards[3]==sortedCards[4]));
	}

	public static boolean isTwoPairs(char[] sortedCards) {
		//first pair and last pair, or first pair and next pair, or odd then pair then pair
		return ((sortedCards[0]==sortedCards[1]) &&
				(sortedCards[2]==sortedCards[3])) ||
			   ((sortedCards[0]==sortedCards[1]) &&
				(sortedCards[3]==sortedCards[4])) ||
			   ((sortedCards[1]==sortedCards[2]) &&
				(sortedCards[3]==sortedCards[4]));
	}

	public static boolean isOnePair(char[] sortedCards) {
		return (sortedCards[0]==sortedCards[1]) ||
				(sortedCards[1]==sortedCards[2]) ||
				(sortedCards[2]==sortedCards[3]) ||
				(sortedCards[3]==sortedCards[4]); 
	}

	// all different
	public static boolean isHighCard(char[] sortedCards) {
		return (sortedCards[0]!=sortedCards[1]) &&
				(sortedCards[1]!=sortedCards[2]) &&
				(sortedCards[2]!=sortedCards[3]) &&
				(sortedCards[3]!=sortedCards[4]); 
	}

	public static int getCoarseStrength(char[] sortedCards) {
		if (isFiveOfAKind(sortedCards)) 	 return 50; 
		if (isFourOfAKind(sortedCards)) 	 return 45; 
		if (isFullHouse(sortedCards)) 		 return 40; 
		if (isThreeOfAKind(sortedCards)) 	 return 35; 
		if (isTwoPairs(sortedCards)) 		 return 30; 
		if (isOnePair(sortedCards)) 		 return 25; 
		if (isHighCard(sortedCards)) 		 return 20; 
		throw new IllegalArgumentException("What no ... what?");
	}
	
	//headfuck
	//note that char[] is a reference to an array, so any changes here will come back to 
	//the calling routine
	public static int nextSubStrength(int cursor, char[] hand) {
		int maxStrength = -1;
		//rotate throigh all card from top down (so fine strength works - no fine strength works differently)
		for (char card : jokerSubstitutionSet.toCharArray()) {
			hand[cursor]=card;
//			System.out.print(new String(hand)+" ");
			if (cursor<4 && hand[cursor+1]=='*') {
				int str = nextSubStrength(cursor+1, hand);
				maxStrength=Math.max(maxStrength, str);
			}
			else {
				//all substituted - sort copy and check
				char[] sorted = new char[5];
				for (int i=0;i<5;i++) sorted[i] = hand[i];
				Arrays.sort(sorted);
				int str = getCoarseStrength(sorted);
				maxStrength=Math.max(maxStrength, str);
//				System.out.println(" -> "+str);
			}
		}
		hand[cursor]= '*'; //restore for next round
		return maxStrength;
	}

	public static List<Hand> loadHands() throws IOException {
		AsciiInputStream in = new AsciiInputStream(new FileInputStream("7/input"));

		List<Hand> hands = new ArrayList<Hand>();
		while (!in.isEOFile()) {
			String handValue = in.readString(' ');
			if (handValue.length()<5) break; //end of file
			handValue = handValue.replace('J', '*'); //for this human, replace with wildcard char
			int bid = (int) in.readInt(' ');
			Hand hand = new Hand(handValue.toCharArray(), bid);
			System.out.println(hand);
			hands.add(hand);
			in.readToEOL();
		}
		in.close();
		
		return hands;
	}
	
	public static void main(String[] args) throws IOException {
		//nextSubStrength(0,"***AK".toCharArray());
		runCalc();
	}
	
	public static void runCalc() throws IOException {
		List<Hand> hands = loadHands();
		System.out.println("-------------------------------");
		Collections.sort(hands);
		for (Hand h : hands) {
			System.out.println(h);
		}
		int sum = 0;
		for (int i=0;i<hands.size();i++) {
			int rank = i+1;
			sum = sum + (rank * hands.get(i).bid);
		}
		System.out.println(sum);
	}
}	
	
	