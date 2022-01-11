import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChartCreator extends JFrame {

    static final String CHART_FILE_NAME = "chart.png";

    public ChartCreator() {

    }

    private XYSeriesCollection createSeries(ResultSet queryResult) {
        XYSeries temperatureSeries = new XYSeries("temperature");
        XYSeries pressureSeries = new XYSeries("pressure");
        XYSeries humiditySeries = new XYSeries("humidity");
        try {
            while (queryResult.next()) {
                long ts = queryResult.getLong("ts");
                double temperature = queryResult.getDouble("temperature");
                double pressure = queryResult.getDouble("pressure");
                double humidity = queryResult.getDouble("humidity");

                temperatureSeries.add(ts, temperature);
                pressureSeries.add(ts, pressure);
                humiditySeries.add(ts, humidity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(temperatureSeries);
        dataset.addSeries(pressureSeries);
        dataset.addSeries(humiditySeries);
        return dataset;
    }

    private JFreeChart createChart(XYSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "",
                "Time",
                "C/hPa/%",
                dataset,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Weather in time",
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        return chart;
    }

    public void renderChart(ResultSet chartQueryResult) {
        XYSeriesCollection dataset = createSeries(chartQueryResult);
        JFreeChart chart = createChart(dataset);
        try {
            ChartUtils.saveChartAsPNG(new File(CHART_FILE_NAME), chart, 500, 500);
        } catch (IOException e) {
            System.out.println("Failed saving chart.");
            e.printStackTrace();
        }
    }
}