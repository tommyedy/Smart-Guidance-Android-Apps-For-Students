package com.example.smartschedule_students.Students.Home.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartschedule_students.Model.attendenceList;
import com.example.smartschedule_students.Model.schedule;
import com.example.smartschedule_students.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class ListScheduleAdapter extends RecyclerView.Adapter<ListScheduleAdapter.ListViewHolder>  {
    private Context mctx;

    private List<schedule> list_classData;

    private DatabaseReference mSchedule;



    private String
            id_schedule,
            nim_students,
            firstname_students,
            lastname_students,
            faculty,
            section,
            status;

    public ListScheduleAdapter(Context mctx, List<schedule> list_classData, String nim_students, String firstname_students, String lastname_students, String faculty, String section) {
        this.mctx = mctx;
        this.list_classData = list_classData;
        this.nim_students = nim_students;
        this.firstname_students = firstname_students;
        this.lastname_students = lastname_students;
        this.faculty = faculty;
        this.section = section;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.list_class_home_students, parent, false);
        //  return new ListViewHolder(LayoutInflater.from(mctx).inflate(R.layout.fragment_home_lecturer, parent, false));
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        holder.classCode.setText(list_classData.get(position).getTitle_guidance().toUpperCase());
        holder.faculty.setText(list_classData.get(position).getDate().toUpperCase());
        holder.section.setText(list_classData.get(position).getTime().toUpperCase());
        holder.section.setText(list_classData.get(position).getPlaces().toUpperCase());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mctx);

                // Set a title for alert dialog
                builder.setTitle("Dialog Confirmation");

                // Ask the final question
                builder.setMessage("Are you want to attend on this schedule?");

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mSchedule    = FirebaseDatabase.getInstance().getReference("attendence_list");
                        id_schedule  = list_classData.get(position).getClass_id().toUpperCase();
                        status       = "1";
                        attendenceList AttendenceList = new attendenceList(
                                id_schedule,
                                nim_students,
                                faculty,
                                section,
                                status);

                        //mSchedule.child(nik_lecturer).setValue(lecturer);
                        mSchedule.push().setValue(AttendenceList).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mctx, "Data Successfully Registered", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list_classData.size();
    }



    class ListViewHolder extends RecyclerView.ViewHolder{
        private TextView classCode, faculty, section;
        private CardView cardView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            classCode = itemView.findViewById(R.id.codeClass_id);
            faculty = itemView.findViewById(R.id.faculty_id);
            section = itemView.findViewById(R.id.jurusan_id);
            cardView = itemView.findViewById(R.id.imageView);

        }


    }
}
