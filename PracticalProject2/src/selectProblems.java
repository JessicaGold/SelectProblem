import javafx.util.Pair;
import java.util.Random;


public class selectProblems
{
	
	private static final boolean MIN = true;
	
	private static final boolean MAX = false;
	
	/**
	 * @param arr
	 * @param i
	 * @param j
	 * swaps values of arr[i] and arr[j]
	 * 
	 * @pre  0 <= i,j < arr.length
	 * @post arr[i] and arr[j] have changed places
	 * 
	 * time complexity O(1)
	 */
	private void swap(int[] arr, int i, int j)
	{
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	
	/**
	 * 
	 * @param arr
	 * @param start
	 * @param end
	 * @return number of comparisons
	 * 
	 * @pre 0 <= start <= end < arr.length
	 * @post arr is sorted
	 * 
	 * average time complexity O(nlogn)
	 * worst case complexity O(n^2)
	 */
	private int recQuickSort(int[] arr, int start, int end)
	{
		if(arr.length == 0 || start == end) {
			return 0;
		}
		if(end == start + 1) {
			if (arr[end] < arr[start]) {
				swap(arr, start, end);
			}
			return 1;
		}
		Random rand = new Random();
		int pivotIndex = start + rand.nextInt(end - start);
		int pivot = arr[pivotIndex];
		int left = start;
		int right = end - 1;
		swap(arr, pivotIndex, end);
		while(right >= left) {
			if(pivot > arr[left]) {
				left++;
			}
			else {
				swap(arr, left, right);
				right--;
			}
		}
		//at this point left is greater than right by 1
		swap(arr, left, end);
		return end - start + recQuickSort(arr, left + 1, end) 
			+ recQuickSort(arr, start, right);
		
	}

	/**
	 * 
	 * @param array
	 * @param k
	 * @return number of comparisons between elements in arr
	 * 
	 * @pre  0 <= k < array.length
	 * @pre array is not empty
	 */
	public Pair<Integer, Integer> selectRandQuickSort(int [] array, int k)
	{
		int[] arr = array.clone();
		int comps = recQuickSort(arr, 0, arr.length - 1);
		return new Pair<Integer, Integer>(arr[k], comps);
	}
	
	
	public Pair<Integer, Integer> selectInsertionSort(int [] array, int k)
	{
		return new Pair<Integer, Integer>(-1,-1); // to be replaced by student code. (The k'th element,#of comparsion)
	}
	
	
	
	private int heapifyDown(int[] arr, int k, int size, boolean isMin)
	{
		int ordIndx = k + 1;
		if(size < 2*ordIndx) {
			return 0;
		}
		if(size == 2*ordIndx) {
			if((isMin && arr[k] > arr[size - 1]) || 
					(!(isMin) && arr[k] < arr[size - 1])) {
				swap(arr, k, size - 1);
			}
			return 1;
		}
		
		int parr = arr[k];
		int nextNodeIndex;
		if(isMin){
			nextNodeIndex = arr[2*ordIndx - 1] < arr[2*ordIndx] ? 
					2*ordIndx - 1 : 2*ordIndx;
		}
		else {
			nextNodeIndex = arr[2*ordIndx - 1] > arr[2*ordIndx] ? 
					2*ordIndx - 1 : 2*ordIndx;
		}
		
		if(isMin && parr <= arr[nextNodeIndex]) return 2;
		if(!(isMin) && parr >= arr[nextNodeIndex]) return 2;
		
		swap(arr, k, nextNodeIndex);
		return 2 + heapifyDown(arr, nextNodeIndex, size, isMin);
		
	}
	
	
	
	private int buildHeap(int[] arr, boolean isMin)
	{
		int comps = 0;
		for(int i = (arr.length) / 2; i >= 0; i--) {
			comps += heapifyDown(arr, i, arr.length, isMin);
		}
		return comps;
	}
	
	
	private Pair<Integer, Integer> deleteFirstFromHeap(int[] arr, int size, boolean isMin)
	{
		int res = arr[0];
		swap(arr, 0, size - 1);
		int comps = heapifyDown(arr, 0, size - 1, isMin);
		return new Pair<Integer, Integer>(res, comps);
	}
  
	
	public Pair<Integer, Integer> selectHeap(int [] array, int k)
	{
		int[] arr = array.clone();
		int res;
		int comps = buildHeap(arr, MIN);
		for(int i = 0; i <= k; i++) {
			if(i == k) {
				
			}
		}
		return new Pair<Integer, Integer>(-1,-1);
	}
  
  public Pair<Integer, Integer> selectDoubleHeap(int [] array, int k)
  {
    return new Pair<Integer, Integer>(-1,-1); // to be replaced by student code. (The k'th element,#of comparsion)
  }
  
  public Pair<Integer, Integer> randQuickSelect(int [] array, int k)
  {
    return new Pair<Integer, Integer>(-1,-1); // to be replaced by student code. (The k'th element,#of comparsion)
  }
  
  public Pair<Integer, Integer> medOfMedQuickSelect(int [] array, int k)
  {
    return new Pair<Integer, Integer>(-1,-1); // to be replaced by student code. (The k'th element,#of comparsion)
  }
  
}