package de.sopamo.uni.sleepminder.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.sopamo.uni.sleepminder.R;
import de.sopamo.uni.sleepminder.storage.FileHandler;

public class SingleNight extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_night);

        File[] files = FileHandler.listFiles();
        String content = FileHandler.readFile(files[files.length-1]);
        Log.e("foo", content);

        String[] parts = content.split(" ");
        String start = parts[0];

        LineChart chart = (LineChart)findViewById(R.id.chart);

        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();

        ArrayList<String> xVals = new ArrayList<String>();

        int j = 0;

        for(int i = 2;i<parts.length;i++) {
            if(parts[i-1].equals(parts[i])) {
                continue;
            }
            addPoint(parts[i-1],i-1,j,start,xVals,valsComp1);
            j++;
            addPoint(parts[i],i,j,start,xVals,valsComp1);
            j++;

        }

        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);

        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);
        chart.invalidate(); // refresh

    }

    private void addPoint(String point, int timeshift, int position, String start, ArrayList<String> xVals, ArrayList<Entry> valsComp1) {
        Entry c1e1 = new Entry(Float.parseFloat(point), position);
        valsComp1.add(c1e1);
        long dv = (Long.valueOf(start) + 5 * timeshift) * 1000;
        Date df = new java.util.Date(dv);
        Log.e("foo",new SimpleDateFormat("HH:mm").format(df));
        xVals.add(new SimpleDateFormat("HH:mm").format(df));
    }
}