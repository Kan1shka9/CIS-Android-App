package com.kanishka.demo.calendarapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StudentAdapter sStudentAdapter;
    private ArrayList<StudentsClass> sStudentList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle(R.string.attendance_tracker);
        setContentView(R.layout.activity_attendance);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        sStudentList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }

    public String loadJSONFromAsset() {

        String json = null;

        try {
            InputStream is = this.getAssets().open("attendance.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;

    }

    private void parseJSON() {

        try {

            JSONArray jsonArr = new JSONArray(loadJSONFromAsset());
            JSONObject obj = new JSONObject();

            for (int i = 0; i < jsonArr.length(); i++) {

                JSONObject student = jsonArr.getJSONObject(i);

                String name = student.getString("name");
                String attendance = student.getString("attendance");

                Log.d(name, attendance);

                sStudentList.add(new StudentsClass(name, attendance));

            }

            sStudentAdapter = new StudentAdapter(AttendanceActivity.this, sStudentList);
            mRecyclerView.setAdapter(sStudentAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}