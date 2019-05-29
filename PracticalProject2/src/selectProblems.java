import javafx.util.Pair;
import java.util.Random;
import java.util.HashMap;


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
		return new Pair<Integer, Integer>(arr[k + 1], comps);
	}
	
	/**
	 * 
	 * @param array
	 * @param k
	 * @return number of comparisons between elements in arr
	 * 
	 * @pre  0 <= k < array.length
	 * @pre array is not empty
	 * 
	 *complexity O(n^2)
	 */
	public Pair<Integer, Integer> selectInsertionSort(int [] array, int k)
	{
		int n = array.length;
		int comps = 0;
        for (int i = 1; i < n; i++) { 
            int key = array[i]; 
            int j = i - 1;
            /* Move elements of arr[0..i-1], that are 
               greater than key, to one position ahead 
               of their current position */
            while (j >= 0 && array[j] > key) { 
                array[j + 1] = array[j]; 
                j = j - 1;
                // a compare was done
                comps ++;                
            }
            // a compare was still done but did not going inside the while loop
            // happens when array[j] <= key and j is not -1 
            if(j != -1) {
            	comps++;
            }
            // finally put the wanted position for our current key
            array[j + 1] = key; 
        }
        //making a new Pair
        Pair<Integer, Integer> result_pair = new Pair<Integer, Integer>(array[k-1], comps);
		return result_pair; 
	}
	
	
	/**
	 * @param arr
	 * @param k
	 * @param size
	 * @param isMin
	 * @return number of comparisons during runTime
	 * 
	 * time complexity O(logn) (n here in the parameter size)
	 */
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
	
	
	/**
	 * 
	 * @param arr
	 * @param isMin
	 * @return number of comparisons during runTime
	 * 
	 * time complexity O(n)
	 */
	private int buildHeap(int[] arr, boolean isMin)
	{
		int comps = 0;
		for(int i = (arr.length) / 2; i >= 0; i--) {
			comps += heapifyDown(arr, i, arr.length, isMin);
		}
		return comps;
	}
	
	/**
	 * 
	 * @param arr
	 * @param size
	 * @param isMin
	 * @return Pair(value of minimum, number of comparisons during runTime)
	 * 
	 * time complexity O(logn) (n here in the parameter size)
	 */
	private Pair<Integer, Integer> deleteFirstFromHeap(int[] arr, int size, boolean isMin)
	{
		int res = arr[0];
		swap(arr, 0, size - 1);
		int comps = heapifyDown(arr, 0, size - 1, isMin);
		return new Pair<Integer, Integer>(res, comps);
	}
  
	/**
	 * 
	 * @param array
	 * @param k
	 * @return
	 * 
	 * time complexity O(n + klogn)
	 */
	public Pair<Integer, Integer> selectHeap(int [] array, int k)
	{
		int[] arr = array.clone();
		int comps = buildHeap(arr, MIN);
		for(int i = 0; i < k; i++) {
			comps += deleteFirstFromHeap(arr, array.length - i, MIN).getValue();
		}
		return new Pair<Integer, Integer>(arr[0],comps);
	}
  
	/**
	 * 
	 * @param arr
	 * @param k
	 * @param size
	 * @param isMin
	 * @return number of comparisons during runTime
	 * 
	 * time complexity O(log n) (n here in the parameter size)
	 */
	private int heapifyUp(int[] arr, int k, int size, boolean isMin)
	{
		if(k == 0) return 0;
		int comps = 1;
		int curr = k;
		while((isMin && arr[curr] > arr[curr / 2]) || 
				(!(isMin) && arr[curr] < arr[curr / 2])) {
			swap(arr, curr, curr / 2);
			curr /= 2;
			if(curr == 0) break;
			comps++;
		}
		return comps;
	}
	
	
	/**
	 * 
	 * @param arr
	 * @param size
	 * @param val
	 * @param isMin
	 * @return number of comparisons during runTime
	 * 
	 * time complexity O(log n) (n here in the parameter size)
	 */
	private int insertToHeap(int[] arr, int size, int val, boolean isMin)
	{
		arr[size] = val;
		return  heapifyUp(arr, size, size + 1, isMin);
	}
	
	
	/**
	 * @param array
	 * @param k
	 * @return number of comparisons during runTime
	 */
	public Pair<Integer, Integer> selectDoubleHeap(int [] array, int k)
	{
		int[] bigHeap = array.clone();
		int comps = buildHeap(bigHeap, MIN);
		
		int[] smallHeap = new int[k + 1];
		//HashMap holds the indexes in bigHeap of the values in smallHeap
		HashMap<Integer, Integer> indexes = new HashMap<Integer, Integer>();
		
		int heapSize = 0;
		int currIndex = 0;
		
		smallHeap[0] = bigHeap[0];
		heapSize++;
		indexes.put(bigHeap[0], 0);
		for(int i = 0; i < k;i++) {
			//continue here
		}

		return new Pair<Integer, Integer>(-1,-1);
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