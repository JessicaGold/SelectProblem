import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class testOri {
	
	public static void main(String[] args) {
		int size = 10000; // The size of the array check.
		List<Integer> sortedList = IntStream.range(0, size).boxed().collect(Collectors.toList());
		java.util.Collections.shuffle(sortedList);
		int[] arr = sortedList.stream().mapToInt(Integer::intValue).toArray();
		selectProblems sp = new selectProblems();
		for (int k=0; k<size; k++) {
			if (sp.selectInsertionSort(arr,k+1).getKey()!=k) { // Fill the <methodName> you wanna check
				System.out.println(k + "is not o-key");
			}
		}
		System.out.println("done");
	}
}
