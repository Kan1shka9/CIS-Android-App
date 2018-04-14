package com.kanishka.demo.calendarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button attendanceButton;
    private Button assignmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.title_main);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attendanceButton = (Button) findViewById(R.id.attendanceButton);
        attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AttendanceActivity.class);
                startActivity(intent);
            }
        });

        assignmentButton = (Button) findViewById(R.id.assignmentButton);
        assignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WireFrameActivity.class);
                startActivity(intent);
            }
        });
    }
}