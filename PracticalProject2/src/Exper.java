import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import javafx.util.Pair;

public class Exper {
	private static final int MAX_VALUE = 1000000;
	public static void main(String[] args) {
		// good only for deterministic functions
		for(int i = 1 ; i < 11 ; i++) {
			int n = 10000 * i;
			int[] array = randomArray(n);
			for (int j = 0; j < 11; j++) {
				int k = 1 + j * n / 10;
				if(k > n) {
					break;
				}
				selectProblems s = new selectProblems();
				System.out.print("i:"+ i + "||" + "n:" + n + "||" + "j:" + j + "||" + "k:" + k + "|| #compares ");
				Pair<Integer, Integer> p = s.medOfMedQuickSelect(array, k);
				System.out.println(p.getValue());
			}
		}
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
	
	
}
