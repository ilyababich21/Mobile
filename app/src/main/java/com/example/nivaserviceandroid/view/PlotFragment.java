package com.example.nivaserviceandroid.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.example.nivaserviceandroid.R;
import com.example.nivaserviceandroid.model.HarvesterModel;
import com.example.nivaserviceandroid.model.MachineModel;
import com.example.nivaserviceandroid.viewmodel.MainViewModel;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PlotFragment extends Fragment {
    private MainViewModel mainViewModel;
    private XYPlot plot;
    private ArrayList<HarvesterModel> machineModels = new ArrayList<>();
    private IntStream modelStream;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_plot, container, false);

        setupTabhost(rootView);

        mainViewModel.getMachines().observe(requireActivity(), machineModels -> {
            this.machineModels = machineModels;
            getVoltage1140(rootView);
            getAmperageM1M2(rootView);
            getAmperageBerm(rootView);
            getAmperageM6M10(rootView);
            getAmperageM12(rootView);
        });

        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getVoltage1140(View rootView) {
        plot = rootView.findViewById(R.id.plot_voltage);

        setupPlotSettings(plot);

        modelStream = machineModels.stream().flatMapToInt(machineModel ->
                IntStream.of(machineModel.getVoltage1140ACFazeA()));
        plot.addSeries(getXYSeries("Фаза A", modelStream),
                new LineAndPointFormatter(Color.GREEN, Color.GREEN, null, null));

        modelStream = machineModels.stream().flatMapToInt(machineModel ->
                IntStream.of(machineModel.getVoltage1140ACFazeB()));
        plot.addSeries(getXYSeries("Фаза B", modelStream),
                new LineAndPointFormatter(Color.RED, Color.RED, null, null));

        modelStream = machineModels.stream().flatMapToInt(machineModel ->
                IntStream.of(machineModel.getVoltage1140ACFazeC()));
        plot.addSeries(getXYSeries("Фаза С", modelStream),
                new LineAndPointFormatter(Color.BLUE, Color.BLUE, null, null));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAmperageM1M2(View rootView) {
        plot = rootView.findViewById(R.id.plot_amperage_m1_m2);

        setupPlotSettings(plot);

        modelStream = machineModels.stream().flatMapToInt(machineModel ->
                IntStream.of(machineModel.getDustExtractorAmperage()));
        plot.addSeries(getXYSeries("Токи М1", modelStream),
                new LineAndPointFormatter(Color.GREEN, Color.GREEN, null, null));

        modelStream = machineModels.stream().flatMapToInt(machineModel ->
                IntStream.of(machineModel.getConveyorAmperage()));
        plot.addSeries(getXYSeries("Токи М2", modelStream),
                new LineAndPointFormatter(Color.RED, Color.RED, null, null));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAmperageBerm(View rootView) {
        plot = rootView.findViewById(R.id.plot_amperage_berm);

        setupPlotSettings(plot);

        modelStream = machineModels.stream().flatMapToInt(machineModel ->
                IntStream.of(machineModel.getBermAmperage()));
        plot.addSeries(getXYSeries("Токи М4", modelStream),
                new LineAndPointFormatter(Color.GREEN, Color.GREEN, null, null));

        modelStream = machineModels.stream().flatMapToInt(machineModel ->
                IntStream.of(machineModel.getSecondBermAmperage()));
        plot.addSeries(getXYSeries("Токи М5", modelStream),
                new LineAndPointFormatter(Color.RED, Color.RED, null, null));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAmperageM6M10(View rootView) {
        plot = rootView.findViewById(R.id.plot_amperage_m6_m10);

        setupPlotSettings(plot);

        modelStream = machineModels.stream().flatMapToInt(machineModel ->
                IntStream.of(machineModel.getCuttingDiscsAmperage()));
        plot.addSeries(getXYSeries("Токи М6", modelStream),
                new LineAndPointFormatter(Color.GREEN, Color.GREEN, null, null));

        modelStream = machineModels.stream().flatMapToInt(machineModel ->
                IntStream.of(machineModel.getPumpingStationAmperage()));
        plot.addSeries(getXYSeries("Токи М10", modelStream),
                new LineAndPointFormatter(Color.RED, Color.RED, null, null));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAmperageM12(View rootView) {
        plot = rootView.findViewById(R.id.plot_amperage_m12);

        setupPlotSettings(plot);

        modelStream = machineModels.stream().flatMapToInt(machineModel ->
                IntStream.of(machineModel.getBunkerLoaderAmperage()));
        plot.addSeries(getXYSeries("Токи М12", modelStream),
                new LineAndPointFormatter(Color.BLUE, Color.BLUE, null, null));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private XYSeries getXYSeries(String nameOfTheFaze, IntStream intStream){
        List<Number> list = (intStream.boxed().collect(Collectors.toList()));
        XYSeries s1 = new SimpleXYSeries(SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, nameOfTheFaze,
                list.toArray(new Number[list.size()]));

        return s1;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupPlotSettings(XYPlot plot){
        PanZoom.attach(plot, PanZoom.Pan.HORIZONTAL, PanZoom.Zoom.STRETCH_HORIZONTAL, PanZoom.ZoomLimit.MIN_TICKS);

        plot.getOuterLimits().setMinX(0);
        plot.getOuterLimits().setMaxX(machineModels.stream().count() - 1);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).
                setFormat(new DecimalFormat("0"));

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).
                setFormat(new Format() {

                    @SuppressLint("SimpleDateFormat")
                    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                    private final SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm:ss");
                    @Override
                    public StringBuffer format(Object obj,
                                               @NonNull StringBuffer toAppendTo,
                                               @NonNull FieldPosition pos) {
                        int yearIndex = (int) Math.round(((Number) obj).doubleValue());
                        try {
                            Date date = dateFormat.parse(machineModels.get(yearIndex).getDate());
                            return dateFormat1.format(date.getTime(), toAppendTo, pos);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    public Object parseObject(String source, @NonNull ParsePosition pos) {
                        return null;
                    }
                });
    }

    private void setupTabhost(View rootView) {
        TabHost tabhost = rootView.findViewById(R.id.tabhost);
        tabhost.setup();

        TabHost.TabSpec spec = tabhost.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("1140V");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("М1,М2");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("М4,М5");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("Tab Four");
        spec.setContent(R.id.tab4);
        spec.setIndicator("М6,М10");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("Tab Five");
        spec.setContent(R.id.tab5);
        spec.setIndicator("М12");
        tabhost.addTab(spec);

        TextView textview = tabhost.getTabWidget().getChildAt(3).findViewById(android.R.id.title);
        textview.setTextSize(14);
    }
}
