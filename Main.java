import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.IOException;
import java.util.Arrays;

class Main {
    public static void main(String args[]) throws IOException {
        FileRead.ReadFromCSV(args[0]);

        Tester.Test(500);
        Tester.Test(1000);
        Tester.Test(2000);
        Tester.Test(4000);
        Tester.Test(8000);
        Tester.Test(16000);
        Tester.Test(32000);
        Tester.Test(64000);
        Tester.Test(128000);
        Tester.Test(250000);
        Tester.TimeIndex = 0;

        Tester.TestSearch(500);
        Tester.TestSearch(1000);
        Tester.TestSearch(2000);
        Tester.TestSearch(4000);
        Tester.TestSearch(8000);
        Tester.TestSearch(16000);
        Tester.TestSearch(32000);
        Tester.TestSearch(64000);
        Tester.TestSearch(128000);
        Tester.TestSearch(250000);

        Algorithms.PrintMSTimes();



        // Setting Axis Data
        SetAxis(0);
        // Save the char as .png and show it
        showAndSaveChart("Random Data", Axis.inputAxis, Axis.yAxis);
        SetAxis(1);
        // Save the char as .png and show it
        showAndSaveChart("Sorted Data", Axis.inputAxis, Axis.yAxis);
        SetAxis(2);
        // Save the char as .png and show it
        showAndSaveChart("Reversed Sorted Data", Axis.inputAxis, Axis.yAxis);

        SetSearchAxis();
        showAndSaveChart("Search Datas", Axis.inputAxis, Axis.yAxis);

    }

    private static void SetAxis(int i) {
        // X axis data
        Axis.inputAxis = new int[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};

        // Create sample data for linear runtime
        Axis.yAxis = new double[3][10];
        Axis.yAxis[0] = Algorithms.AlgorithmTimes.get(AlgorithmType.quicksort)[i];
        Axis.yAxis[1] = Algorithms.AlgorithmTimes.get(AlgorithmType.selectionsort)[i];
        Axis.yAxis[2] = Algorithms.AlgorithmTimes.get(AlgorithmType.bucketsort)[i];
    }
    private static void SetSearchAxis() {
        // X axis data
        Axis.inputAxis = new int[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};

        // Create sample data for linear runtime
        Axis.yAxis = new double[3][10];
        Axis.yAxis[0] = Algorithms.AlgorithmTimes.get(AlgorithmType.linearsearch)[0];
        Axis.yAxis[1] = Algorithms.AlgorithmTimes.get(AlgorithmType.linearsearch)[1];
        Axis.yAxis[2] = Algorithms.AlgorithmTimes.get(AlgorithmType.binarysearch)[0];
    }

    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Quick Sort", doubleX, yAxis[0]);
        chart.addSeries("Selection Sort", doubleX, yAxis[1]);
        chart.addSeries("Bucket Sort" , doubleX, yAxis[2]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
    static class Axis{
        static int[] inputAxis;
        static double[][] yAxis;
    }
}
