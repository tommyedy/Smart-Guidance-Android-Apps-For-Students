package com.example.smartschedule_students.Students.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.smartschedule_students.R;

public class details_note extends AppCompatActivity {
    private ImageView btn_cancel;
    private EditText
            nim_student,
            firstname,
            lastname,
            email,
            faculty,
            section,
            note;

    private static String
            firstname_students,
            lastname_students,
            email_students,
            Faculty_students,
            Section_students,
            nim_students,
            notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);
        firstname_students = getIntent().getExtras().getString("firstname_students");
        lastname_students = getIntent().getExtras().getString("lastname_students");
        email_students = getIntent().getExtras().getString("email_students");
        Faculty_students = getIntent().getExtras().getString("Faculty_students");
        Section_students = getIntent().getExtras().getString("Section_students");
        nim_students = getIntent().getExtras().getString("nim_students");
        notes = getIntent().getExtras().getString("note");


        nim_student = findViewById(R.id.nimStudents_id);
        firstname   = findViewById(R.id.firstname);
        lastname    = findViewById(R.id.lastname);

        email       = findViewById(R.id.email);
        faculty     = findViewById(R.id.faculty);
        section     = findViewById(R.id.section);
        note        = findViewById(R.id.notes);

        nim_student.setEnabled(false);
        nim_student.setText(nim_students);
        firstname.setText(firstname_students);
        firstname.setEnabled(false);
        lastname.setText(lastname_students);
        lastname.setEnabled(false);
        email.setText(email_students);
        email.setEnabled(false);
        faculty.setText(Faculty_students);
        faculty.setEnabled(false);
        section.setText(Section_students);
        section.setEnabled(false);
        note.setText(notes);
        note.setEnabled(false);

        btn_cancel = findViewById(R.id.btnCancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
