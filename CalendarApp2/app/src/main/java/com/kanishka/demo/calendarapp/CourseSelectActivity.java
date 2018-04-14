package com.kanishka.demo.calendarapp;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CourseSelectActivity extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    OnFragmentInteractionListener mListener;

    public CourseSelectActivity() {
    }

    public static CourseSelectActivity newInstance(String param1, String param2) {
        CourseSelectActivity fragment = new CourseSelectActivity();
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
        return inflater.inflate(R.layout.course_select, container, false);
    }

    String courseSelection;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        addItemsOnCourseSelectionBox();

        courseSelectionDropdownBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseSelection = String.valueOf(courseSelectionDropdownBox.getSelectedItem());
                Toast.makeText(getActivity(), courseSelection + " was selected.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Button confirmationButton = (Button) getActivity().findViewById(R.id.confirmationButton);
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCourseSelected(courseSelection);
            }
        });
    }

    private Spinner courseSelectionDropdownBox;
    ArrayAdapter<String> dataAdapterCourse;

    void addItemsOnCourseSelectionBox() {
        courseSelectionDropdownBox = (Spinner) getActivity().findViewById(R.id.courseSelectionDropdown);
        List<String> options = new ArrayList<>();
        options.add("CIS 4100");
        options.add("CIS 4820");
        dataAdapterCourse = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, options);
        dataAdapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSelectionDropdownBox.setAdapter(dataAdapterCourse);
    }

    public void onButtonPressed(String val) {
        System.out.println("INSIDE ON BUTTON PRESSED: " + val);

        if (mListener != null) {
            System.out.println("INSIDE ON BUTTON PRESSED IF STATEMENT: " + val);
            mListener.onCourseSelected(val);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onCourseSelected(String val);
    }

}