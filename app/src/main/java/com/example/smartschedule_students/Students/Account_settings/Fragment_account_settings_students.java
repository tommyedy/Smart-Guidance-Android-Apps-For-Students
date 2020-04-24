package com.example.smartschedule_students.Students.Account_settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartschedule_students.MainActivity;
import com.example.smartschedule_students.Model.userStudents;
import com.example.smartschedule_students.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_account_settings_students extends Fragment {
    private static String
            firstname_student,
            lastname_student,
            email_student,
            Faculty_student,
            Section_student,
            nim_student;

    private static String
            firstname_student_change,
            lastname_student_change,
            email_student_change,
            Faculty_student_change,
            Section_student_change,
            nim_student_change;

    private Button
            btn_logout,
            btn_edit,
            btn_password;

    private EditText
            Firstname_student,
            Lastname_student,
            Email_student,
            faculty_student,
            section_student,
            Nim_student;

    private FirebaseAuth mAuth;
    private DatabaseReference musers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_settings_students, container, false);

        btn_logout = view.findViewById(R.id.logout);
        btn_edit = view.findViewById(R.id.edit_profile);

        firstname_student = getActivity().getIntent().getExtras().getString("firstname_student");
        lastname_student  = getActivity().getIntent().getExtras().getString("lastname_student");
        email_student     = getActivity().getIntent().getExtras().getString("email_student");
        Faculty_student   = getActivity().getIntent().getExtras().getString("faculty_student");
        Section_student   = getActivity().getIntent().getExtras().getString("section_student");
        nim_student = getActivity().getIntent().getExtras().getString("nim");

        Firstname_student = view.findViewById(R.id.firstname);
        Lastname_student = view.findViewById(R.id.lastname);
        Email_student = view.findViewById(R.id.email);
        faculty_student = view.findViewById(R.id.faculty);
        section_student = view.findViewById(R.id.section);
        Nim_student = view.findViewById(R.id.nimStudent_id);

        Firstname_student.setText(firstname_student);
        Lastname_student.setText(lastname_student);
        Email_student.setEnabled(false);
        Email_student.setText(email_student);
        faculty_student.setEnabled(false);
        faculty_student.setText(Faculty_student);
        section_student.setEnabled(false);
        section_student.setText(Section_student);
        Nim_student.setEnabled(false);
        Nim_student.setText(nim_student);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musers = FirebaseDatabase.getInstance().getReference("students");
                firstname_student_change = Firstname_student.getText().toString().trim();
                lastname_student_change = Lastname_student.getText().toString().trim();
                email_student_change = Email_student.getText().toString().trim();
                Faculty_student_change = faculty_student.getText().toString().trim();
                Section_student_change = section_student.getText().toString().trim();
                nim_student_change = Nim_student.getText().toString().trim();
                userStudents Userstudent = new userStudents(
                        nim_student_change,
                        firstname_student_change,
                        lastname_student_change,
                        email_student_change,
                        Faculty_student_change,
                        Section_student_change,
                        ""
                );
                musers.child(nim_student_change)
                        .setValue(Userstudent)
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Data Successfully Edited", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                // mAuth.getInstance().signOut();
                // Intent i = new Intent(getActivity(), MainActivity.class);
                // startActivity(i);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getInstance().signOut();
                getActivity().getIntent().removeExtra("Firstname_lecturer");
                getActivity().getIntent().removeExtra("Lastname_lecturer");
                getActivity().getIntent().removeExtra("email_lecturer");
                getActivity().getIntent().removeExtra("Faculty_lecturer");
                getActivity().getIntent().removeExtra("Section_lecturer");
                getActivity().getIntent().removeExtra("nik_lecturer");
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}
