package com.example.smartschedule_students.Students.Note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartschedule_students.Model.note;
import com.example.smartschedule_students.R;
import com.example.smartschedule_students.Students.Note.Adapter.ListNoteAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_note_students extends Fragment {
    private ListNoteAdapter listnotedapter;
    private RecyclerView recyclerView;
    private ArrayList<note> arrayListNote;//list;
    private static String
            firstname_students,
            lastname_students,
            email_students,
            Faculty_students,
            Section_students,
            nim_students;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_students, container, false);
        firstname_students = getActivity().getIntent().getExtras().getString("firstname_students");
        lastname_students = getActivity().getIntent().getExtras().getString("lastname_students");
        email_students = getActivity().getIntent().getExtras().getString("email_students");
        Faculty_students = getActivity().getIntent().getExtras().getString("Faculty_students");
        Section_students = getActivity().getIntent().getExtras().getString("Section_students");
        nim_students = getActivity().getIntent().getExtras().getString("nim_students");
        //btn_addClass = view.findViewById(R.id.create_class);
        //tv_nama_user = view.findViewById(R.id.nama_user);

        recyclerView = view.findViewById(R.id.ListNote);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        arrayListNote = new ArrayList<note>();

        Query dbListNote = FirebaseDatabase.getInstance().getReference("note")
                .orderByChild("nim_students")
                .equalTo(nim_students);

        dbListNote.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayListNote.clear();
                // System.out.println(dataSnapshot.getChildren());
                for (DataSnapshot listNote : dataSnapshot.getChildren()){
                    if (listNote.exists()){
                        note Note = listNote.getValue(note.class);
                        arrayListNote.add(Note);
                    }
                }
                listnotedapter = new ListNoteAdapter(getActivity(), arrayListNote);
                recyclerView.setAdapter(listnotedapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        btn_fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), AddNoteLecturer.class);
//                startActivity(i);
//            }
//        });
        return view;
    }
}
