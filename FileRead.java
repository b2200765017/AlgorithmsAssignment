import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileRead {
    private static BufferedReader CSV_File;
    private static int[] data;
    public static void ReadFromCSV(String filePath) throws IOException {
        CSV_File = new BufferedReader(new FileReader(filePath));
        data = new int[250000];
        String line = CSV_File.readLine();
        int i = 0;
        while((line = CSV_File.readLine()) != null && i < 250000){
            data[i] = Integer.parseInt(line.split(",")[6]);
            i++;
        }
    }
    public static int[]  GetData(int size) throws IOException {
        int[] res = new int[size];
        res = Arrays.copyOf(data, size);
        return res;
    }
}
