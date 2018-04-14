package com.kanishka.demo.calendarapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context sContext;
    private ArrayList<StudentsClass> sStudentsClass;

    public StudentAdapter(Context context, ArrayList<StudentsClass> StudentsClass) {
        sContext = context;
        sStudentsClass = StudentsClass;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(sContext).inflate(R.layout.student, parent, false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        StudentsClass currentItem = sStudentsClass.get(position);
        String name = currentItem.getName();
        String attend = currentItem.getAttendance();
        holder.s_name.setText(name);
        holder.s_attendance.setText("Attendance: " + attend);
    }

    @Override
    public int getItemCount() {
        return sStudentsClass.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        public TextView s_name;
        public TextView s_attendance;

        public StudentViewHolder(View itemView) {
            super(itemView);
            s_name = itemView.findViewById(R.id.text_view_name);
            s_attendance = itemView.findViewById(R.id.text_view_attend);
        }
    }
}