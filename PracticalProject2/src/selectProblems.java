import javafx.util.Pair;
import java.util.Random;



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
	public Pair<Integer, Integer> selectInsertionSort(int [] arr, int k)
	{
		
		int n = arr.length;
		int[] array = new int[n];
		System.arraycopy(arr, 0, array, 0, n);
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
	private int heapifyDown(int[] arr, int k, int size,int[] indx, boolean isMin)
	{
		int ordIndx = k + 1;
		if(size < 2*ordIndx) {
			return 0;
		}
		if(size == 2*ordIndx) {
			if((isMin && arr[k] > arr[size - 1]) || 
					(!(isMin) && arr[k] < arr[size - 1])) {
				swap(arr, k, size - 1);
				if(indx != null) {
					swap(indx, k, size - 1);
				}
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
		if(indx != null) {
			swap(indx, k, nextNodeIndex);
		}
		return 2 + heapifyDown(arr, nextNodeIndex, size, indx, isMin);
		
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
			comps += heapifyDown(arr, i, arr.length, null, isMin);
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
	private Pair<Integer, Integer> deleteFirstFromHeap(int[] arr, int size, int[] indx, boolean isMin)
	{
		int res = arr[0];
		swap(arr, 0, size - 1);
		if(indx != null) {
			swap(indx, 0, size - 1);
		}
		int comps = heapifyDown(arr, 0, size - 1, indx, isMin);
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
			comps += deleteFirstFromHeap(arr, array.length - i + 1, null, MIN).getValue();
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
	private int heapifyUp(int[] arr, int k, int[] indx, boolean isMin)
	{
		if(k == 0) return 0;
		int comps = 1;
		int curr = k + 1;
		while((isMin && arr[curr - 1] < arr[(curr/2) - 1]) || 
				(!(isMin) && arr[curr - 1] > arr[(curr/2) - 1])) {
			swap(arr, curr - 1, (curr/2) - 1);
			if(indx != null) {
				swap(indx, curr - 1, (curr/2) - 1);
			}
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
	private int insertToHeap(int[] arr, int size, int val, int[] indx, boolean isMin)
	{
		arr[size] = val;
		return  heapifyUp(arr, size, indx, isMin);
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

		int[] indexes = new int[k];
		
		int heapSize = 0;
		int currIndex = 0;
		int rightNode;
		int leftNode;
		
		smallHeap[0] = bigHeap[0];
		heapSize++;
		indexes[0] = 0;
		for(int i = 1; i < k; i++) {
			
			currIndex = (indexes[0] + 1);
			
			if(bigHeap.length <= currIndex * 2) {
				comps += deleteFirstFromHeap(smallHeap, heapSize--, indexes, MIN).getValue();
				if(bigHeap.length == currIndex * 2) {
					indexes[heapSize] = currIndex * 2 - 1;
					comps += insertToHeap(smallHeap, heapSize++, bigHeap[currIndex * 2 - 1], indexes, MIN);
				}
				
				continue;
			}

			leftNode = bigHeap[currIndex * 2 - 1];
			rightNode = bigHeap[currIndex * 2];
			comps += deleteFirstFromHeap(smallHeap, heapSize--, indexes, MIN).getValue();
			indexes[heapSize] = currIndex * 2 - 1;
			indexes[heapSize + 1] = currIndex * 2;
			comps += insertToHeap(smallHeap, heapSize++, leftNode, indexes, MIN) + 
					insertToHeap(smallHeap, heapSize++, rightNode, indexes, MIN);

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
	 * @pre  0 <= k < array.length
	 * @pre array is not empty
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
		// 
		int new_comp = comp;
		swap(array, pivotIndex, right); // move pivot to end
		int storeIndex = left;
		for(int i = left; i < right; i++) {
			// we are doing a compare by array[i] < pivotValue
			new_comp++;
			if(array[i] < pivotValue) {
				swap(array, storeIndex, i);
				storeIndex++;
			}
		}
		swap(array, right, storeIndex); // Move pivot to its final place
        //making a new Pair
        Pair<Integer, Integer> result_pair = new Pair<Integer, Integer>(storeIndex, new_comp);
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
	
	/**
	 * a shell function for the recursive function
	 * 
	 * @param array
	 * @param k
	 * @return a pair with the kth element - as key, and the number of compares was done - as value
	 * 
	 * @pre  0 <= k < array.length
	 * @pre array is not empty
	 * 
	 * worst case time complexity O(n)
	 */
	
		  
	public Pair<Integer, Integer> medOfMedQuickSelect(int [] arr, int k)
	{
		int n = arr.length;
		int[] array = new int[n];
		System.arraycopy(arr, 0, array, 0, n);
		return recursiceMedOfMed(array, 0, array.length - 1, k, 0);
	}
	
	/**
	 * the recursive function of medOfMedQuickSelect as learned in class
	 * 
	 * @param array
	 * @param k
	 * @param array boundaries
	 * @param number of compares that already done in the recursive loop
	 * @return a pair with the kth element - as key, and the number of compares was done - as value
	 * 
	 * @pre all elements in arr are distinct
	 * worst case time complexity O(n)
	 */
	public Pair<Integer, Integer> recursiceMedOfMed(int[] arr , int l , int r, int k, int comps){
		// first find the medofmed value
		Pair<Integer, Integer> medofmed_pair = MedOfMed(arr, l, r, 0);
		// add the compares was done
		int new_comps = comps;
//		int new_comps = comps + medofmed_pair.getValue();
		// this is the median
		int medOfMed = medofmed_pair.getKey();
		// find index of med of med
        int MedIndex = 0;
        for(int m = l ; m <= r ; m++) {
        	// a compare is done
        	new_comps++;
        	if(arr[m] == medOfMed) {
        		MedIndex = m;
        		break;
        	}
        }
		// Partition the array around medofmed element and 
        // get position of pivot element in sorted array 
        Pair<Integer, Integer> partition = partition(arr, l, r, MedIndex, 0); 
        int pos = partition.getKey();
        new_comps = new_comps + partition.getValue();
  
        // If position is same as k 
        if (pos-l == k-1) {
//        	System.out.println(arr[pos]);
        	return new Pair<Integer, Integer>(arr[pos], new_comps);
        }
        if (pos-l > k-1)  // If position is more, recur for left 
            return recursiceMedOfMed(arr, l, pos-1, k, new_comps); 
  
        // Else recur for right subarray 
        return recursiceMedOfMed(arr, pos+1, r, k-pos+l-1, new_comps); 
 
	}
	
	/**
	 * a recursice function to find the median of medians in given array
	 * Divide arr[] in groups of size 5, calculate median of every group
	 * repeat the process until you left with one element
	 * 
	 * @param array
	 * @param array boundaries
	 * @param number of compares that already done in the recursive loop
	 * @return a pair with the value of median of medians - as key, and the number of compares was done - as value
	 * 
	 * @pre all elements in arr are distinct
	 * time complexity O(n)
	 */
	
	public Pair<Integer, Integer> MedOfMed(int[] arr, int l, int r, int comps){
		{ 
		     
	        int n = r-l+1; // Number of elements in arr[l..r] 
	        int new_comps = comps;
	        // Divide arr[] in groups of size 5, calculate median 
	        // of every group and store it in median[] array. 
	        int i; 
	        int[] median = new int[(n+4)/5]; // There will be floor((n+4)/5) groups; 
	        for (i=0; i< n / 5; i++) { 
	        	// sort this five
	        	// sort like insertion sort
	            for (int b = l + i * 5 + 1 ; b < l + i * 5 + 5; b++) { 
	                int key = arr[b]; 
	                int j = b - 1;
	                while (j >= i * 5 && arr[j] > key) { 
	                    arr[j + 1] = arr[j]; 
	                    j = j - 1;
	                    // a compare was done
	                    new_comps ++;                
	                }
	                // a compare was still done but did not going inside the while loop
	                // happens when array[j] <= key and j is not l + i * 5 - 1 
	                // -1 the starting point
	                if(j != l + i * 5 - 1) {
	                	new_comps++;
	                }
	                // finally put the wanted position for our current key
	                arr[j + 1] = key;
	            }
	            // the median of a 5 element is the elemnent in place #2
	            median[i] = arr[l + i * 5 + 2]; 
	        }
	        if (i*5 < n) //For last group with less than 5 elements 
	        { 
	        	// sort this five
	        	// sort like insertion sort
	            for (int b = l + i * 5 + 1; b < r + 1; b++) { 
	                int key = arr[b]; 
	                int j = b - 1;
	                while (j >= i * 5 && arr[j] > key) { 
	                    arr[j + 1] = arr[j]; 
	                    j = j - 1;
	                    // a compare was done
	                    new_comps ++;                
	                }
	                // a compare was still done but did not going inside the while loop
	                // happens when array[j] <= key and j is not l + i * 5 - 1 
	                if(j != l + i * 5 - 1) {
	                	new_comps++;
	                }
	                // finally put the wanted position for our current key
	                arr[j + 1] = key;
	            }
	            median[i] = arr[l + i * 5 + (r - l - i * 5 ) / 2];  
	            i++; 
	        }     
	  
	        // Find median of all medians using recursive call. 
	        // If median[] has only one element, then no need 
	        // of recursive call 
	        if (i == 1) {
	        	return new Pair<Integer, Integer>(median[i - 1], new_comps);
	        }
	        else {
	        	return MedOfMed(median, 0, i - 1, new_comps);
	        }
		}
	}
}
	  
