import java.util.Arrays;
import java.util.Random;

public class SpecifitTest {
	
	/*public static void main(String[] args) {

		selectProblems s = new selectProblems();
	
		int[] array = {843419, 341250, 769253, 255689, 442605, 616669, 747434, 582420, 201880, 586256, 961247, 509047, 773462, 845959, 379853, 512119};
	
		//int res1 = s.selectDoubleHeap(array, 4).getKey();
		//int res2 = s.selectDoubleHeap(array, 11).getKey();
		//System.out.println(res1);
		//System.out.println(res2);
		
		int[] arr = SelectTest.randomArray(15);
		s.buildHeap(arr, true);
		isLegalMinHeap(arr);
		Random rand = new Random();
		for(int i = 0; i < 10; i++) {
			int rndMun = rand.nextInt();
			s.insertToHeap(arr, arr.length - 1, rndMun, true);
		}
		for(int i = 0; i < 10; i++) {
			int rndMun = rand.nextInt();
			s.deleteFirstFromHeap(arr, arr.length, true);
			s.insertToHeap(arr, arr.length - 1, rndMun, true);
		}
		s.buildHeap(array, true);
		if(isLegalMinHeap(arr)) {
			System.out.println("array: " + Arrays.toString(array));
		}
	}
	
	public static boolean isLegalMinHeap(int[] arr) {
		for(int i = arr.length - 1; i > 0; i--) {
			int curr = arr[i];
			int parr = arr[((i + 1)/2) - 1];
			if(parr > curr) {
				System.out.println("problem in index " + i + 
						": \tparrent: " + parr + "\tnode: " + curr);
				System.out.println("array: " + Arrays.toString(arr));
				return false;
			}
		}
		
		System.out.println("legal heap");
		return true;
	}*/
}
