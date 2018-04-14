package com.kanishka.demo.calendarapp;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CalendarActivity extends Fragment implements CourseUpdateInterface {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CalendarActivity() {
    }

    public static CalendarActivity newInstance(String param1, String param2) {
        CalendarActivity fragment = new CalendarActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_calendar, container, false);
    }

    String targetCourse;
    TextView mainHeading;
    TextView subHeading;
    CalendarView mainCalendar;
    TextView assignmentText;

    ArrayList<String> calendarData;
    String[][] currData;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        System.out.println("Calender OnViewCreated");

        mainCalendar = (CalendarView) getActivity().findViewById(R.id.mainCalendar);
        assignmentText = (TextView) getActivity().findViewById(R.id.assignmentContainer);

        mainHeading = (TextView) getActivity().findViewById(R.id.mainHeading);
        subHeading = (TextView) getActivity().findViewById(R.id.subHeading);

        final String[][] currData = organizeData(updateData());

        mainCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                System.out.println("Day Changed to(Month // Day): " + (month + 1) + "/" + dayOfMonth);
                assignmentText.setText(verifyHomework(currData, mainHeading.getText().toString(), (month + 1), dayOfMonth));
            }
        });

    }

    @Override
    public void courseUpdated(String val) {
        updateTargetedCourse(val);
        calendarData = updateData();
        resetTextViewDisplay(assignmentText);
    }

    String verifyHomework(String[][] dataSet, String courseTitle, int month, int day) {
        for (int i = 0; i < dataSet.length; i++) {
            if (dataSet[i][0].equals(courseTitle) && Integer.parseInt(dataSet[i][1]) == month && Integer.parseInt(dataSet[i][2]) == day) {
                return dataSet[i][3];
            }
        }
        return "No Homework Listed";
    }

    String[][] organizeData(ArrayList<String> dataInput) {

        ArrayList<String[]> tempData = new ArrayList<>();

        for (int i = 0; i < dataInput.size(); i++) {
            System.out.println("ORGANIZING DATA: Index At (" + i + ") " + dataInput.get(i));
            StringTokenizer tokenizer = new StringTokenizer(dataInput.get(i), ",");
            String[] tempStringArray = new String[4];
            for (int j = 0; j < 4; j++) {
                tempStringArray[j] = tokenizer.nextToken();
            }
            tempData.add(tempStringArray);
            System.out.println("ORGANIZED DATA @ Index (" + i + ")");
            System.out.println("Data Stored: " + tempData.get(i));
        }

        String[][] temp = new String[tempData.size()][];

        for (int i = 0; i < tempData.size(); i++) {
            temp[i] = tempData.get(i);
        }

        System.out.println(temp[0][0]);
        System.out.println(temp[0][1]);
        System.out.println(temp[0][2]);
        System.out.println(temp[0][3]);

        return temp;

    }

    ArrayList<String> updateData() {

        ArrayList<String> calendarData;
        AssetManager am = getActivity().getAssets();
        InputStream inputStream;
        dataParser dataParser;

        try {
            inputStream = am.open("CourseData.txt");
            dataParser = new dataParser(inputStream);
            calendarData = dataParser.getData();
            return calendarData;
        } catch (Exception e) {
            System.out.println("EXCEPTION IN UPDATE DATA: " + e.toString());
        }

        return null;
    }

    public void updateTargetedCourse(String val) {
        targetCourse = val;
        mainHeading.setText(val);
    }

    void resetTextViewDisplay(TextView targetTV) {
        targetTV.setText("");
    }

    public void onButtonPressed(boolean val) {
        if (mListener != null) {
            mListener.onCalendarFragInteraction(val);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new RuntimeException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onCalendarFragInteraction(boolean val);
    }

}