import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;


public class Experiments {
	
	private static final int MAX_VALUE = 10000;
	
	public static enum method {
		selectRandQuickSort,//("Random QuickSort"),
		selectInsertionSort,//("Insertion Sort"),
		selectHeap,//("Heap (single)"),
		selectDoubleHeap,//("DoubleHeap"),
		randQuickSelect,//("Random Quick Select"),
		medOfMedQuickSelect//("Med Of Med Quick Select");
		
		//privtae final String displayName;
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
	
	private static int doExperiment() {
		return 42;
	}
	
}
