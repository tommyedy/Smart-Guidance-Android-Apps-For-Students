package com.example.smartschedule_students.Students.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smartschedule_students.Model.classData;
import com.example.smartschedule_students.R;
import com.example.smartschedule_students.Students.Home.Adapter.ListClassAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class result_seach_students extends AppCompatActivity {
    private SearchView searchView;
    private TextView tv_nama_user;
    private FirebaseAuth mAuth;
    private static String
            firstname_student,
            lastname_student,
            email_student,
            Faculty_student,
            Section_student,
            nik_lecturer,
            nim;
    // List<classData> ListScheduleAdapter;
    private ListClassAdapter list_class_adapter;
    private RecyclerView recyclerView;
    private ArrayList<classData> list_class_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_seach_students);
        nik_lecturer = getIntent().getExtras().getString("nik_lecturer");
//        System.out.println(nik_lecturer);
        recyclerView = findViewById(R.id.ListClass);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list_class_ = new ArrayList<classData>();

        Query dbListClass = FirebaseDatabase.getInstance().getReference("class")
                .orderByChild("nik_stat")
                .equalTo(nik_lecturer+"_"+"0");
        dbListClass.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot listLecturer : dataSnapshot.getChildren()) {
                        classData Lecturer = listLecturer.getValue(classData.class);
                        list_class_.add(Lecturer);
                    }
                }
                list_class_adapter = new ListClassAdapter(result_seach_students.this, list_class_);
                recyclerView.setAdapter(list_class_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(result_seach_students.this, ""+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
