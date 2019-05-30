import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;


public class Experiments {
	
	private static final int MAX_VALUE = 10000;
	
	public static enum method {
		selectRandQuickSort,
		selectInsertionSort,
		selectHeap,
		selectDoubleHeap,
		randQuickSelect,
		medOfMedQuickSelect
	}
	

	private String methodToString(method mtd) {
		switch(mtd) {
		case selectRandQuickSort:
			return "Random Quick Sort";
		case selectInsertionSort:
			return "Insertion Sort";
		case selectHeap:
			return "Heap";
		case selectDoubleHeap:
			return "Double Heap";
		case randQuickSelect:
			return "Random Quick Select";
		case medOfMedQuickSelect:
			return "Median Of Medians Quick Select";
		}
		return "";
	}

	public static int[] randomArray(int n) {

		int[] res = new int[n];

		for (int i = 0; i < n; i++) {
			int next = randInt(0, MAX_VALUE);
			while(numInArray(res, next)) {
				next = randInt(0, MAX_VALUE);
			}
			res[i] = next;
		}

		return res;
	}
	
	public static boolean numInArray(int[] arr, int num) {
		for(int elem : arr) {
			if(num == elem) return true;
		}
		return false;
	}

	private static int randInt(int min, int max) {

		return ThreadLocalRandom.current().nextInt(min, max + 1);

	}
	
	private static int doExperiment(method mtd) {
		int[] array;
		int k;
		int n;
		int comps;
		for(int i = 1; i < 11; i++) {
			n = i * MAX_VALUE;
			array = randomArray(n);
			k = 1;
			for(int j = 0; j < 11; j++) {
				
				
				k += n/10;
			}
		}
		return 42;
	}
	
}
