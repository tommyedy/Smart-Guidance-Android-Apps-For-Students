package com.example.smartschedule_students.Students.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartschedule_students.Model.schedule;
import com.example.smartschedule_students.R;
import com.example.smartschedule_students.Students.Home.Adapter.ListScheduleAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_home_students extends Fragment {
    private SearchView searchView;
    private TextView tv_nama_user;
    private FirebaseAuth mAuth;
    private static String
            firstname_student,
            lastname_student,
            email_student,
            Faculty_student,
            Section_student,
            nim;
    // List<classData> ListScheduleAdapter;
    private ListScheduleAdapter list_schedule_adapter;
    private RecyclerView recyclerView;
    private ArrayList<schedule> list_schedule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_students, container, false);
        searchView = view.findViewById(R.id.search_class);
        tv_nama_user = view.findViewById(R.id.nama_user);
        
        firstname_student = getActivity().getIntent().getExtras().getString("firstname_student");
        lastname_student  = getActivity().getIntent().getExtras().getString("lastname_student");
        email_student     = getActivity().getIntent().getExtras().getString("email_student");
        Faculty_student   = getActivity().getIntent().getExtras().getString("faculty_student");
        Section_student   = getActivity().getIntent().getExtras().getString("section_student");
        nim               = getActivity().getIntent().getExtras().getString("nim");

        String full_name  = firstname_student+ " "+lastname_student;
        tv_nama_user.setText(full_name);

        recyclerView = view.findViewById(R.id.ListSchedule);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        list_schedule = new ArrayList<schedule>();

        Query dbListClass = FirebaseDatabase.getInstance().getReference("detail_class")
                .orderByChild("nik_stat")
                .equalTo(nim+"_"+"1");
        dbListClass.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot list_class : dataSnapshot.getChildren()) {
                        if (dataSnapshot.exists()){
                            schedule kelas = list_class.getValue(schedule.class);
                            Query dbListSchedule = FirebaseDatabase.getInstance().getReference("schedule")
                                    .orderByChild("class_id")
                                    .equalTo(kelas.getClass_id());
                            dbListSchedule.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        list_schedule.clear();
                                        for (DataSnapshot listschedule : dataSnapshot.getChildren()) {
                                            if (dataSnapshot.exists()){
                                                schedule Schedule = listschedule.getValue(schedule.class);
                                                list_schedule.add(Schedule);
                                            }
                                        }
                                        //  if (kelas.fac_sec_stat.equals(where)) {

                                        list_schedule_adapter = new ListScheduleAdapter(getActivity(),
                                                list_schedule,
                                                //Pass Extras to Adapter
                                                firstname_student,
                                                lastname_student,
                                                Faculty_student,
                                                Section_student,
                                                nim);
                                        recyclerView.setAdapter(list_schedule_adapter);
                                        //list.add(kelas);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getActivity(), ""+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), ""+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(getActivity(), search_students.class);
                startActivity(i);
            }
        });
        //Db_list_class.addListenerForSingleValueEvent();
//        ListScheduleAdapter List_class = new ListScheduleAdapter();
//
//        ListScheduleAdapter.add(new classData("BX01",
//                "",
//                "",
//                "TEknik",
//                "SI",
//                ""));
//
//        recyclerView.setAdapter(List_class);




//        btn_list_class = view.findViewById(R.id.list_kelas);
//        btn_list_class.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signOut();
//            }
//        });
        return view;
    }
}
