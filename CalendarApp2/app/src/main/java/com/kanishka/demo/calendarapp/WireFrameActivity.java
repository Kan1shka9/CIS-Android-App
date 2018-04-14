package com.kanishka.demo.calendarapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

public class WireFrameActivity extends Activity implements CalendarActivity.OnFragmentInteractionListener, CourseSelectActivity.OnFragmentInteractionListener {

    private CourseUpdateInterface courseUpdateListener;

    public void setCourseUpdateListener(Fragment listener) {
        this.courseUpdateListener = (CourseUpdateInterface) listener;
    }

    public void onCalendarFragInteraction(boolean val) {
    }

    public void onCourseSelected(String val) {
        Toast.makeText(this, ("COURSE SELECTION INTERACTION: " + val), Toast.LENGTH_SHORT).show();
        courseUpdateListener.courseUpdated(val);
        showFrag(activeFragTag, "CalendarFrag");
    }

    String activeFragTag = null;
    String previousFragTag = null;
    Fragment calendarFrag;
    Fragment courseSelectionFrag;

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wire_frame);
        initFragments();
    }

    void initFragments() {
        calendarFrag = new CalendarActivity();
        setCourseUpdateListener(calendarFrag);
        courseSelectionFrag = new CourseSelectActivity();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, calendarFrag, "CalendarFrag");
        transaction.hide(calendarFrag);
        transaction.commit();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, courseSelectionFrag, "CourseSelectionFrag");
        transaction.show(courseSelectionFrag);
        activeFragTag = "CourseSelectionFrag";
        transaction.commit();
    }

    void hideCurrentFrag(String targetFragTag) {
        transaction = fragmentManager.beginTransaction();
        transaction.hide(fragmentManager.findFragmentByTag(targetFragTag)).commit();
        previousFragTag = targetFragTag;
    }

    void showFrag(String activeTag, String tag) {
        hideCurrentFrag(activeTag);
        transaction = fragmentManager.beginTransaction();
        transaction.show(fragmentManager.findFragmentByTag(tag)).commit();
        activeFragTag = tag;
    }

    void updateCalendarFragTitle(String val) {
        System.out.println("UPDATE CALENDAR FRAG TITLE HIT + " + val);
    }

    @Override
    public void onBackPressed() {
        if (activeFragTag.equals("CourseSelectionFrag")) {
            moveTaskToBack(true);
        } else {
            showFrag(activeFragTag, previousFragTag);
        }
    }
}