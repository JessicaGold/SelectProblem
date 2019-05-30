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
	

	private static String methodToString(method mtd) {
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
	
	
	private static int runFunc(int[] array, int k, selectProblems s, method mtd) {
		switch(mtd) {
		case selectRandQuickSort:
			return s.selectRandQuickSort(array, k).getValue();
		case selectInsertionSort:
			return s.selectInsertionSort(array, k).getValue();
		case selectHeap:
			return s.selectHeap(array, k).getValue();
		case selectDoubleHeap:
			return s.selectDoubleHeap(array, k).getValue();
		case randQuickSelect:
			return s.randQuickSelect(array, k).getValue();
		case medOfMedQuickSelect:
			return s.medOfMedQuickSelect(array, k).getValue();
		}
		return -1;
	}
	
	
	private static void doExperiment(selectProblems s, method mtd) {
		if(mtd == method.randQuickSelect || mtd == method.selectRandQuickSort) {
			doRandExperiment(s, mtd);
			return;
		}
		int[] array;
		int k;
		int n;
		for(int i = 1; i < 11; i++) {
			n = i * MAX_VALUE;
			array = randomArray(n);
			k = 1;
			for(int j = 0; j < 11; j++) {
				System.out.println("Number of comparisons for k: " + k +
						"in array size " + n);
				System.out.println("using " + methodToString(mtd) + ":");
				System.out.println(runFunc(array, k, s, mtd));
				k += n/10;
			}
		}
	}
	
	
	
	private static void doRandExperiment(selectProblems s, method mtd) {
		int[] array;
		int k;
		int n;
		for(int i = 1; i < 11; i++) {
			n = i * MAX_VALUE;
			array = randomArray(n);
			k = 1;
			for(int j = 0; j < 11; j++) {
				int avg = 0;
				for(int r = 0; r < 10; r++) {
					avg += runFunc(array, k, s, mtd);
				}
				avg /= 10;
				System.out.println("Number of comparisons for k: " + k +
						"in array size " + n);
				System.out.println("using " + methodToString(mtd) + 
						" avarage over 10 atempts:");
				System.out.println(avg);
				k += n/10;
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		//run the function doExperiment
	}
	
}
