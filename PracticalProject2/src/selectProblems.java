import javafx.util.Pair;
import java.util.Random;
import java.util.HashMap;


public class selectProblems
{
	
	private static final boolean MIN = true;
	//private static final boolean MAX = false;
	
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
		if(arr.length == 0 || start >= end) {
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
		while(right > left) {
			if(pivot > arr[left]) {
				left++;
			}
			else {
				swap(arr, left, right);
				right--;
			}
		}
		//at this point left is greater than right by 1
		swap(arr, right, end);
		return end - start + recQuickSort(arr, left, end) 
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
		return new Pair<Integer, Integer>(arr[k - 1], comps);
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
		for(int i = (arr.length / 2); i >= 0; i--) {
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
		for(int i = 1; i < k; i++) {
			comps += deleteFirstFromHeap(arr, array.length - i + 1, MIN).getValue();
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
	private int heapifyUp(int[] arr, int k, boolean isMin)
	{
		if(k == 0) return 0;
		int comps = 1;
		int curr = k + 1;
		while((isMin && arr[curr - 1] < arr[(curr/2) - 1]) || 
				(!(isMin) && arr[curr - 1] > arr[(curr/2) - 1])) {
			swap(arr, curr - 1, (curr/2) - 1);
			curr = curr/2;
			if(curr == 1) break;
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
	 * @pre arr.length > size of heap
	 * time complexity O(log n) (n here in the parameter size)
	 */
	private int insertToHeap(int[] arr, int size, int val, boolean isMin)
	{
		arr[size] = val;
		return  heapifyUp(arr, size, isMin);
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
		
		int[] smallHeap = new int[k];
		//HashMap holds the indexes in bigHeap of the values in smallHeap
		HashMap<Integer, Integer> indexes = new HashMap<Integer, Integer>();
		
		int heapSize = 0;
		int currIndex = 0;
		int rightNode;
		int leftNode;
		
		smallHeap[0] = bigHeap[0];
		heapSize++;
		indexes.put(bigHeap[0], 0);
		for(int i = 1; i < k; i++) {
			
			currIndex = (indexes.get(smallHeap[0]) + 1);
			
			if(bigHeap.length <= currIndex * 2) {
				comps += deleteFirstFromHeap(smallHeap, heapSize--, MIN).getValue();
				if(bigHeap.length == currIndex * 2) {
					comps += insertToHeap(smallHeap, heapSize++, bigHeap[currIndex * 2 - 1], MIN);
					indexes.put(bigHeap[currIndex * 2 - 1], currIndex * 2 - 1);
				}
				
				continue;
			}

			leftNode = bigHeap[currIndex * 2 - 1];
			rightNode = bigHeap[currIndex * 2];
			comps += deleteFirstFromHeap(smallHeap, heapSize--, MIN).getValue();
			comps += insertToHeap(smallHeap, heapSize++, leftNode, MIN) + 
					insertToHeap(smallHeap, heapSize++, rightNode, MIN);
			indexes.put(leftNode, currIndex * 2 - 1);
			indexes.put(rightNode, currIndex * 2);

		}

		return new Pair<Integer, Integer>(smallHeap[0], comps);
	}
	
	/**
	 * a shell function for the recursive function
	 * 
	 * @param array
	 * @param k
	 * @return a pair with the kth element - as key, and the number of compares was done - as value
	 * 
	 * average time complexity O(n)
	 * worst case time complexity O(n^2)
	 */
	
	
	public Pair<Integer, Integer> randQuickSelect(int [] array, int k)
	{
		return recursiveRandQuic(array, 0, array.length - 1, k-1, 0);
	}
	
	/**
	 * the recursive function of Randomized-QuickSelect as learned in class
	 * 
	 * @param array
	 * @param k
	 * @param array boundaries
	 * @param number of compares that already done in the recursive loop
	 * @return a pair with the kth element - as key, and the number of compares was done - as value
	 * 
	 * average time complexity O(n)
	 * worst case time complexity O(n^2)
	 */
	
	public Pair<Integer, Integer> recursiveRandQuic(int[] array, int left, int right, int k, int comp) {
		if (left == right) { // If the list contains only one element,
			//making a new Pair
	        Pair<Integer, Integer> result_pair = new Pair<Integer, Integer>(array[left], comp);// return that element
			return result_pair; 
			
		}
		
		// select a pivotIndex between left and right
		int pivotIndex = randomPivot(left, right);
		// making a partition and saving the returned pair
		Pair<Integer, Integer> partition_pair = partition(array, left, right, pivotIndex, 0);
		// get the pivot index
		pivotIndex = partition_pair.getKey();
		// add to compares was done
		int new_comp = comp + partition_pair.getValue();
		// The pivot is in its final sorted position
		if (k == pivotIndex) {
			//making a new Pair
	        Pair<Integer, Integer> result_pair = new Pair<Integer, Integer>(array[k], new_comp);// return that element
			return result_pair; 
		} else if (k < pivotIndex) {
			return recursiveRandQuic(array, left, pivotIndex - 1, k, new_comp);
		} else {
			return recursiveRandQuic(array, pivotIndex + 1, right, k, new_comp);
		}
	}
	
	
	
	/**
	 * partitions the given array in such a way that elements at lower indexes should be less than the given pivot element.
	 * Similarly, elements at higher indexes will be greater or equal than the pivot element
	 * 
	 * @param array, his boundaries and the random pivot index 
	 * @return a pair with pivot new index after the partition - as key, and the number of compares was done - as value
	 * 
	 * time complexity O(n)
	 */
	public Pair<Integer, Integer> partition(int[] array, int left, int right, int pivotIndex, int comp) {
		int pivotValue = array[pivotIndex];
		swap(array, pivotIndex, right); // move pivot to end
		int storeIndex = left;
		for(int i = left; i < right; i++) {
			// we are doing a compare by array[i] < pivotValue
			comp++;
			if(array[i] < pivotValue) {
				swap(array, storeIndex, i);
				storeIndex++;
			}
		}
		swap(array, right, storeIndex); // Move pivot to its final place
        //making a new Pair
        Pair<Integer, Integer> result_pair = new Pair<Integer, Integer>(storeIndex, comp);
		return result_pair; 

	}
	
	/**
	 * @param array boundaries : left and right  
	 * @return a random pivot index between the given boundaries
	 */
	
	public int randomPivot(int left, int right) {
	    Random rand = new Random();
		int pivot = left + rand.nextInt(right - left);
	    return pivot;
	}
	
		  
	public Pair<Integer, Integer> medOfMedQuickSelect(int [] array, int k)
	{
		return new Pair<Integer, Integer>(-1,-1); // to be replaced by student code. (The k'th element,#of comparsion)
	}
	  
}