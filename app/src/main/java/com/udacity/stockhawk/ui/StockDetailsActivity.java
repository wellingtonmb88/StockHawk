package com.udacity.stockhawk.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockDetailsActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    LineChart lineChart;

    private final Typeface defaultBold = Typeface.DEFAULT_BOLD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        ButterKnife.bind(this);

        final String history = getIntent().getExtras()
                .getString(Contract.Quote.COLUMN_HISTORY);

        final ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            final String symbol = getIntent().getExtras()
                    .getString(Contract.Quote.COLUMN_SYMBOL);
            if (symbol != null) {
                supportActionBar.setTitle(symbol);
            }
        }

        if (history != null) {

            setupLineDataEntry(history);
            setupLegend();
            setupLeftYAxis();
            setupRightYAxis();

            lineChart.getDescription().setEnabled(false);
            lineChart.setMaxVisibleValueCount(10);
            lineChart.setPinchZoom(true);
            lineChart.setDrawGridBackground(false);

            lineChart.invalidate();
        }
    }

    private void setupLineDataEntry(final String history) {
        final String[] historiesArray = history.split("\n");
        final int lastElement = historiesArray.length > 10 ? 10 : historiesArray.length;
        final String[] histories = Arrays.copyOfRange(historiesArray, 0, lastElement);

        Arrays.sort(histories);

        final List<Entry> entries = new ArrayList<>();
        final List<String> labels = new ArrayList<>();

        for (int i = 0; i < lastElement; i++) {

            final String data = histories[i];
            final String[] splitData = data.split(",");
            long time = Long.parseLong(splitData[0]);
            float price = Float.parseFloat(splitData[1]);

            final String dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(time);

            entries.add(new Entry(i, price));

            labels.add(dateFormat);
        }

        final LineDataSet lineDataSet = new LineDataSet(entries, getString(R.string.num_of_prices_chart_entries));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCircleHoleRadius(20f);
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        LineData data = new LineData(lineDataSet);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(12f);

        lineChart.setData(data);

        setupRightXAxis(labels);
    }

    private void setupLegend() {
        final Legend legend = lineChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setFormSize(8f);
        legend.setTypeface(defaultBold);
        legend.setTextColor(Color.WHITE);
        legend.setFormToTextSpace(4f);
        legend.setXEntrySpace(6f);
    }

    private void setupRightXAxis(final List<String> labels) {

        final XAxis xAxis = lineChart.getXAxis();
        xAxis.setTypeface(defaultBold);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisMinimum(0);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels.get((int) Math.abs(value));
            }
        });
    }

    private void setupLeftYAxis() {
        final YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTypeface(defaultBold);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(5f);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMinimum(0);
    }

    private void setupRightYAxis() {
        final YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setTypeface(defaultBold);
        rightAxis.setValueFormatter(new LargeValueFormatter());
        rightAxis.setDrawGridLines(false);
        rightAxis.setSpaceTop(5f);
        rightAxis.setTextColor(Color.WHITE);
        rightAxis.setAxisMinimum(0);
    }
}
