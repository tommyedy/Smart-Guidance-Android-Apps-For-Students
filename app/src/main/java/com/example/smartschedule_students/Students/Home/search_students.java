package com.example.smartschedule_students.Students.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartschedule_students.Model.userLecturer;
import com.example.smartschedule_students.R;
import com.example.smartschedule_students.Students.Home.Adapter.ListLecturerSearch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search_students extends AppCompatActivity {
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
    private ListLecturerSearch ListLecturer;
    private RecyclerView recyclerView;
    private ArrayList<userLecturer> lecturer_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_students);


        recyclerView = findViewById(R.id.ListLecturer);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        lecturer_list = new ArrayList<userLecturer>();

        Query dbListLecturer = FirebaseDatabase.getInstance().getReference("lecturer");
        dbListLecturer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot listLecturer : dataSnapshot.getChildren()) {
                        userLecturer Lecturer = listLecturer.getValue(userLecturer.class);
                        lecturer_list.add(Lecturer);
                    }
                }
                ListLecturer = new ListLecturerSearch(search_students.this, lecturer_list);
                recyclerView.setAdapter(ListLecturer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(search_students.this, ""+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        searchView = findViewById(R.id.search_class);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                ListLecturerSearch ls = new ListLecturerSearch(search_students.this, lecturer_list);
//                ls.getFilter().filter(s);
//                return false;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
