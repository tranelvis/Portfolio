package edu.ucsd.cse110.personalbestappteam24.report;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;

import edu.ucsd.cse110.personalbestappteam24.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyReportFragment extends Fragment {


    public WeeklyReportFragment() {
        // Required empty public constructor
    }

    private final int daysToDisplay = 7;

    public WeeklyReport report;
    CombinedChart chart;

    public CombinedChart getChart() {
        return chart;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_report, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.fragment_weekly_report);

        chart = (CombinedChart) getActivity().findViewById(R.id.chart1);
        //SetGraph set = new SetGraph(getActivity(), 7);
        //chart = set.getChart();
    }
}
