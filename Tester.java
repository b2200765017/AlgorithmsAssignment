import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Tester {
    public static long Timer = 0;
    public static int TimeIndex = 0;
    public static int MsArrayIndex = 0;
    public static List<Long> SelectionSortMSArray;
    public static List<Long> QuickSortMSArray;
    public static List<Long> BucketSortMSArray;
    public static List<Long> LinearSearchNanoArray;
    public static List<Long> BinarySearchNanoArray;
    private static Random random = new Random();
    public static void Test(int DataSize) throws IOException {
        SelectionSortMSArray= new ArrayList<>();
        QuickSortMSArray= new ArrayList<>();
        BucketSortMSArray= new ArrayList<>();
        long ms = 0;
        int[] data = FileRead.GetData(DataSize);
        // iterate 10 times for avarage
        System.out.println("Sorting the data for size: " + DataSize);

        for(int i = 0; i < 10; i++){
            // Unsorted Tests
            data = TestSortingAlgorithms(data, ms);
        }
        SetSortAlgorithmAvarageTimeMS();
        for(int i = 0; i < 10; i++) {
            TestSortingAlgorithms(data, ms);
        }

        SetSortAlgorithmAvarageTimeMS();
        Algorithms.reverse(data);

        for(int i = 0; i < 10; i++){
            // Reversely Sorted Tests
            TestSortingAlgorithms(data, ms);
        }
        SetSortAlgorithmAvarageTimeMS();
        TimeIndex++;
        MsArrayIndex = 0;
    }
    public static void TestSearch(int dataSize) throws IOException {
        long nano = 0;
        LinearSearchNanoArray = new ArrayList<>();
        BinarySearchNanoArray = new ArrayList<>();
        System.out.println("Searching the data for: " + dataSize);
        int[] data = FileRead.GetData(dataSize);
        for(int i = 0; i < 1000; i++){
            TestLinearSearchUnsorted(data,nano);
        }
        Algorithms.SetSearchAlgorithmTime(AlgorithmType.linearsearch,GetAvarage(LinearSearchNanoArray), TimeIndex, 0);
        LinearSearchNanoArray = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            TestSearchAlgorithmsSorted(data,nano);
        }
        Algorithms.SetSearchAlgorithmTime(AlgorithmType.linearsearch,GetAvarage(LinearSearchNanoArray), TimeIndex, 1);
        Algorithms.SetSearchAlgorithmTime(AlgorithmType.binarysearch,GetAvarage(BinarySearchNanoArray), TimeIndex, 0);
        TimeIndex++;
    }

    private static void TestSearchAlgorithmsSorted(int[] data, long nano) {
        int i = random.nextInt(0,data.length);
        Arrays.sort(data);


        StartNanoTimer();
        Algorithms.linearSearch.Search(data, data[i]);
        nano = EndNanoTimer();
        LinearSearchNanoArray.add(nano);

        StartNanoTimer();
        Algorithms.binarySearch.Search(data, data[i]);
        nano = EndNanoTimer();
        BinarySearchNanoArray.add(nano);

    }

    public static void TestLinearSearchUnsorted(int[] data, long nano){
        int i = random.nextInt(0,data.length);

        StartNanoTimer();
        Algorithms.linearSearch.Search(data, data[i]);
        nano = EndNanoTimer();
        LinearSearchNanoArray.add(nano);
    }

    public static int GetAvarage(List<Long> arr){
        int sum = 0;
        for(int i = 0; i < arr.size(); i++){
            sum += arr.get(i);
        }
        if (arr.size() != 0)
            return sum / arr.size();
        return  0;
    }
    private static void SetSortAlgorithmAvarageTimeMS() {
        Algorithms.SetSortAlgorithmTime(AlgorithmType.selectionsort,GetAvarage(SelectionSortMSArray),TimeIndex, MsArrayIndex);
        Algorithms.SetSortAlgorithmTime(AlgorithmType.quicksort, GetAvarage(QuickSortMSArray),TimeIndex, MsArrayIndex);
        Algorithms.SetSortAlgorithmTime(AlgorithmType.bucketsort, GetAvarage(BucketSortMSArray),TimeIndex, MsArrayIndex);
        MsArrayIndex++;
    }

    public static int[] TestSortingAlgorithms(int[] data, long ms){
        StartMSTimer();
        Algorithms.selectionSort.Sort(data, data.length);
        ms = EndMSTimer();
        SelectionSortMSArray.add(ms);


        StartMSTimer();
        Algorithms.quickSort.Sort(data,0, data.length-1);
        ms = EndMSTimer();
        QuickSortMSArray.add(ms);



        StartMSTimer();
        data = Algorithms.bucketsort.Sort(data, data.length);
        ms = EndMSTimer();
        BucketSortMSArray.add(ms);



        return data;
    }
    private static void StartMSTimer(){
        Timer = 0;
        Timer = System.currentTimeMillis();
    }
    private static long EndMSTimer(){
        return System.currentTimeMillis() - Timer;
    }
    private static void StartNanoTimer(){
        Timer = 0;
        Timer = System.nanoTime();
    }
    private static long EndNanoTimer(){
        return System.nanoTime() - Timer;
    }
}
