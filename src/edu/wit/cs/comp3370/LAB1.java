package edu.wit.cs.comp3370;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

/* Sorts integers from command line using various algorithms 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 1
 * Rachel Palmer
 */

public class LAB1 {

	/* countingSort takes array input a.
	 * count array is created to store a count of each number in a (max has been previously set)
	 * The first for loop cycles through the array, increments count[a[x]] (the corresponding
	 * index for each item in a)
	 * output array is created to hold count values in order
	 * The second for loop cycles through count, starting at 0
	 * If count[x] isnt zero, then x is added to the output, and count[x] is decremented
	 * At the end of the loop, output holds all values from a in order
	 */
	public static int[] countingSort(int[] a) {
		int max = MAX_INPUT + 1;
		
		int count[] = new int[max];

		//increment count index for each a value
		for (int x = 0; x<a.length; x++){
			count[a[x]]++;
		}
		
		int output[] = new int[a.length];
		int i = 0;
		for(int x=0; x < count.length; x++){
			while(count[x] != 0){
				output[i] = x;
				count[x]--;
				i++;
			}
		}

		return output;
	}

	/*
	 * radixSort takes input array a.
	 * d represents the highest number of digits that any number will have
	 * since our MAX_INPUT is set to 524287, then d will not be any higher than 6
	 * k represents how many numbers can appear in a value of a (this sorting method will
	 * use the decimal system, so only numbers 0-9 will appear)
	 * place represents which position (starting from the right) the sort will check
	 * count and pos are created which will represent values 0-9
	 * output will contain the sorted array
	 * The first for loop encases the reset of the function, and loops d times so that each 
	 * place can be checked and sorted.
	 * A for loop fills count and pos with 0's (to clear the array from previous loop)
	 * currPos calculates which number in the input is being sorted (ex: for the first loop, 
	 * number 2345, currPos will be 5, in the second loop will be 4, etc).
	 * A for loop increments the number at the appropriate index for count, (similar 
	 * to the counting sort above). Using the array of counted values, pos array is filled
	 * where each index represents the count of numbers that are less than x.
	 * In the next loop, the pos array determines where the input number from a will be stored.
	 * pos[currPos] will represent the appropriate index in output, and then the value is 
	 * increased. By increasing the value, this ensures that if the loop encounters that index
	 * again, it will not store the number in the same index, and will instead move it up by
	 * one position.
	 * Finally, once output contains the sorted values, a is updated with these values, and 
	 * place is multiplied by ten, so that when it loops again, the position to the left will
	 * can be sorted.
	 */
	public static int[] radixSort(int[] a) {
		int d = 6; 
		int k = 10; 
		int place = 1; 

		int[] count = new int[k]; 
		int[] pos = new int[k];
		int[] output = new int[a.length];
		
		for(int i = 0; i < d; i++){ //i represents digit in place from right to left
			for(int x=0; x < k; x++){
				count[x] = 0;
				pos[x] = 0;
			}
			
			int currPos;
			for(int x = 0; x < a.length; x++){ //x represent current digits 0-9
				currPos = (a[x]/place)%10;
				count[currPos]++;
			}
			
			//use count to create pos array
			for(int x = 1; x < k; x++){
				pos[x] = pos[x-1] + count[x-1];
			}

			for(int x = 0; x < a.length; x++){
				currPos = (a[x]/place)%10; 
				output[pos[currPos]] = a[x];
				pos[currPos]++;
			}
			
			for(int x = 0; x < a.length; x++){
				a[x] = output[x];
			}
			
			place *= 10; //move to next digit place (tens -> hundreds -> etc )
		}
		return output;
	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	public final static int MAX_INPUT = 524287;
	public final static int MIN_INPUT = 0;

	// example sorting algorithm
	public static int[] insertionSort(int[] a) {

		for (int i = 1; i < a.length; i++) {
			int tmp = a[i];
			int j;
			for (j = i-1; j >= 0 && tmp < a[j]; j--)
				a[j+1] = a[j];
			a[j+1] = tmp;
		}

		return a;
	}

	/* Implementation note: The sorting algorithm is a Dual-Pivot Quicksort by Vladimir Yaroslavskiy,
	 *  Jon Bentley, and Joshua Bloch. This algorithm offers O(n log(n)) performance on many data 
	 *  sets that cause other quicksorts to degrade to quadratic performance, and is typically 
	 *  faster than traditional (one-pivot) Quicksort implementations. */
	public static int[] systemSort(int[] a) {
		Arrays.sort(a);
		return a;
	}

	// read ints from a Scanner
	// returns an array of the ints read
	private static int[] getInts(Scanner s) {
		ArrayList<Integer> a = new ArrayList<Integer>();

		while (s.hasNextInt()) {
			int i = s.nextInt();
			if ((i <= MAX_INPUT) && (i >= MIN_INPUT))
				a.add(i);
		}

		return toIntArray(a);
	}

	// copies an ArrayList of Integer to an array of int
	private static int[] toIntArray(ArrayList<Integer> a) {
		int[] ret = new int[a.size()];
		for(int i = 0; i < ret.length; i++)
			ret[i] = a.get(i);
		return ret;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.printf("Enter the sorting algorithm to use ([c]ounting, [r]adix, [i]nsertion, or [s]ystem): ");
		char algo = s.next().charAt(0);

		System.out.printf("Enter the integers that you would like sorted, followed by a non-integer character: ");
		int[] unsorted_values = getInts(s);
		int[] sorted_values = {};

		s.close();

		switch (algo) {
		case 'c':
			sorted_values = countingSort(unsorted_values);
			break;
		case 'r':
			sorted_values = radixSort(unsorted_values);
			break;
		case 'i':
			sorted_values = insertionSort(unsorted_values);
			break;
		case 's':
			sorted_values = systemSort(unsorted_values);
			break;
		default:
			System.out.println("Invalid sorting algorithm");
			System.exit(0);
			break;
		}

		System.out.println(Arrays.toString(sorted_values));
	}

}
