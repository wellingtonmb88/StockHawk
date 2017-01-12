package com.udacity.stockhawk.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.udacity.stockhawk.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockDetailsActivity extends AppCompatActivity {

    @BindView(R.id.chart1)
    BarChart barChart;

    Typeface mTfLight = Typeface.DEFAULT_BOLD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        ButterKnife.bind(this);

        barChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(40);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(true);

        barChart.setDrawValueAboveBar(false);
        barChart.setHighlightFullBarEnabled(false);

        List<BarEntry> entriesGroup1 = new ArrayList<>();
        List<BarEntry> entriesGroup2 = new ArrayList<>();
        List<BarEntry> entriesGroup3 = new ArrayList<>();
        List<BarEntry> entriesGroup4 = new ArrayList<>();

        float startYear = 1979f;
        int count = 1;
        for (int i = (int) startYear; i < startYear + 5; i++) {

            entriesGroup1.add(new BarEntry(i, 100 + count));
            entriesGroup2.add(new BarEntry(i, 120 + count));
            entriesGroup3.add(new BarEntry(i, 140 + count));
            entriesGroup4.add(new BarEntry(i, 160 + count));
            count += 10;
        }

        BarDataSet set1 = new BarDataSet(entriesGroup1, "Group 1");
        BarDataSet set2 = new BarDataSet(entriesGroup2, "Group 2");
        BarDataSet set3 = new BarDataSet(entriesGroup3, "Group 3");
        BarDataSet set4 = new BarDataSet(entriesGroup4, "Group 4");

        set1.setColor(ColorTemplate.MATERIAL_COLORS[2]);
        set2.setColor(ColorTemplate.MATERIAL_COLORS[1]);
        set3.setColor(ColorTemplate.JOYFUL_COLORS[0]);
        set4.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);

        float groupSpace = 0.1f;
        float barSpace = 0.05f; // x4 DataSet
        float barWidth = 0.1f; // x4 DataSet
        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData data = new BarData(dataSets);
        data.setBarWidth(barWidth); // set the width of each bar
//        data.setValueTextColor(Color.WHITE);

        barChart.setData(data);
        barChart.setDrawBarShadow(true);
        barChart.groupBars(startYear, groupSpace, barSpace); // perform the "explicit" grouping

//        barChart.setFitBars(true);

//        Legend l = barChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setFormSize(8f);
//        l.setTypeface(mTfLight);
//        l.setFormToTextSpace(4f);
//        l.setXEntrySpace(6f);
//
        XAxis xAxis = barChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setGranularity(0.5f);

        // restrict the x-axis range
        xAxis.setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
//        xAxis.setAxisMaximum(startYear + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * dataSets.size());
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) Math.floor(value));
            }
        });
////
//        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
//        leftAxis.setValueFormatter(new LargeValueFormatter());
//        leftAxis.setDrawGridLines(false);
//        leftAxis.setSpaceTop(1f);
//        leftAxis.setAxisMinimum(0); // this replaces setStartAtZero(true)
//
//        barChart.getAxisRight().setEnabled(false);

        barChart.invalidate(); // refresh
    }

    private int[] getColors() {

        int stacksize = 2;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i];
        }

        return colors;
    }

    static class MyData {

        float value1;
        float value2;
        float value3;
        float value4;

        public MyData(float value1, float value2, float value3, float value4) {
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
            this.value4 = value4;
        }
    }
}
