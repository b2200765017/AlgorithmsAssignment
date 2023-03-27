import java.util.*;

enum AlgorithmType{
    selectionsort,
    quicksort,
    bucketsort,
    linearsearch,
    binarysearch
}
public class Algorithms {
    // Sorting Algorithms Are Referenced Here
    static Dictionary<AlgorithmType, double[][]> AlgorithmTimes = new Hashtable<>();
    public static SelectionSort selectionSort = new SelectionSort();
    public static QuickSort quickSort = new QuickSort();
    public static BucketSort bucketsort = new BucketSort();

    // Search Algorithms Are Referenced Here

    public static LinearSearch linearSearch = new LinearSearch();
    public static BinarySearch binarySearch = new BinarySearch();


    public static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    public static void reverse(int[] arr)
    {
        int n = arr.length;

        for (int i = 0; i < n / 2; i++) {

            int temp = arr[i];
            arr[i] = arr[n - i - 1];
            arr[n - i - 1] = temp;
        }
    }
    public static int Max(int[] input) {
        int m = Integer.MIN_VALUE;
        for (int i : input) m = Math.max(i, m);
        return m;
    }
    public static void SetSortAlgorithmTime(AlgorithmType type, long time, int i, int j){
        double[][] arr = AlgorithmTimes.get(type);
        if (arr == null){
            arr = new double[3][];
            for (int k = 0; k < 3; k++){
                arr[k] = new double[10];
            }
        }
        arr[j][i] = time;
        AlgorithmTimes.put(type,arr);
    }
    public static void SetSearchAlgorithmTime(AlgorithmType type, long time, int i, int j){
        double[][] arr = AlgorithmTimes.get(type);
        if (arr == null){
            arr = new double[2][];
            for (int k = 0; k < 2; k++){
                arr[k] = new double[10];
            }
        }
        arr[j][i] = time;
        AlgorithmTimes.put(type,arr);
    }
    public static void PrintMSTimes(){
        Enumeration<AlgorithmType> keys = AlgorithmTimes.keys();
        while (keys.hasMoreElements()) {
            AlgorithmType key = keys.nextElement();
            System.out.print("Key: " + key + "\n");
            System.out.print("Random : ");
            for (int j = 0; j < AlgorithmTimes.get(key)[0].length; j++){
                    System.out.print(AlgorithmTimes.get(key)[0][j] + " ");
                }
            System.out.println();
            if(AlgorithmTimes.get(key).length > 1) {
                System.out.print("Sorted : ");
                for (int j = 0; j < AlgorithmTimes.get(key)[1].length; j++) {
                    System.out.print(AlgorithmTimes.get(key)[1][j] + " ");
                }
                System.out.println();
            }
            if(AlgorithmTimes.get(key).length > 2){
                System.out.print("Reversely Sorted : ");
                for (int j = 0; j < AlgorithmTimes.get(key)[2].length; j++){
                    System.out.print(AlgorithmTimes.get(key)[2][j] + " ");
                }
                System.out.println();
            }

        }
    }
}
class SelectionSort {

    // array: Given array to sort
    // n: Size of the array
    public int[] Sort(int[] array, int n){
        for(int i = 0; i < n - 1; i++){
            int min = i;
            for (int j = i+1; j < n; j++){
                if(array[j] < array[min]) min = j;
            }
            if(min != i){
                Algorithms.swap(array, min, i);
            }
        }
        return array;
    }

}
class QuickSort {

    // array: Given array to sort
    // l: first element of the list
    // h: last element of the list
    public int[] Sort(int[] array, int l, int h){
        int stackSize = h - l +1;
        int[] stack = new int[stackSize];
        int top = -1;
        stack[++top] = l;
        stack[++top] = h;
        while (top >= 0) {
            h = stack[top--];
            l = stack[top--];
            int pivotIndex = partition(array, l, h);
            if (pivotIndex - 1 > l) {
                stack[++top] = l;
                stack[++top] = pivotIndex - 1;
            }
            if (pivotIndex + 1 < h) {
                stack[++top] = pivotIndex + 1;
                stack[++top] = h;
            }
        }
        return array;
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                Algorithms.swap(arr, i, j);
            }
        }
        Algorithms.swap(arr, i+1, high);
        return i + 1;
    }


}
class BucketSort {

    // array: Given array to sort
    // n: Size of the array
    public int[] Sort(int[] array, int n){
        int numberOfBuckets = (int) Math.pow(n, 0.5);
        List<List<Integer>> bucketsArray = new ArrayList<>(numberOfBuckets);
        for (int i = 0; i < numberOfBuckets; i++) {
            bucketsArray.add(new ArrayList<>());
        }

        int max = Algorithms.Max(array);
        for (int i = 0; i < n; i++) {
            int index = hash(array[i], max, numberOfBuckets);
            bucketsArray.get(index).add(array[i]);
        }
        for (List<Integer> bucket : bucketsArray) {
            Collections.sort(bucket);
        }
        List<Integer> sortedArray = new ArrayList<>(n);
        for (List<Integer> bucket : bucketsArray) {
            sortedArray.addAll(bucket);
        }
        int[] sorted = new int[n];
        for(int i = 0; i < n; i++){
            sorted[i] = sortedArray.get(i);
        }
        return sorted;
    }

    public static int hash(int i, int max, int numberOfBuckets) {
        return (int) Math.floor((double) i / max * (numberOfBuckets - 1));
    }
}
class LinearSearch{
    // array: Given array to search
    // x: Value to be Searched
    public int Search(int[] array, int x){
    int n = array.length;
    for (int i = 0; i < n; i++){
        if (array[i] == x){
            return i;
        }
    }
    return -1;
    }
}
class BinarySearch{
    // array: Given array to search(sorted)
    // x: Value to be Searched
    public int Search(int[] array, int x){
        int l = 0;
        int h = array.length-1;

        // Half it until size is only 2
        while (h - l > 1){
            int mid = l + (h-l)/2;
            if(array[mid] < x){
                l = mid + 1;
            }
            else{
                h = mid;
            }
        }
    // Check if these 2 sizes match than return
    if(array[l] == x) return l;
    else if (array[h] == x) return h;
    else return -1;
    }
}