package com.example.smartschedule_students.Students.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartschedule_students.Model.detailClass;
import com.example.smartschedule_students.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.Date;

public class detailClassSearchStudents extends AppCompatActivity {
    private Button btn_join;
    private TextView ClassCode;
    private EditText
    Faculty,
    Section,
    Nik_lecturer,
    ActivationCode;
    private static String
            classCode_str,
            Faculty_student_str,
            Section_student_str,
            nik_lecturer_str,
            activationCode_str;

    private static String
            classCode,
            Faculty_student,
            Section_student,
            nik_lecturer,
            activationCode;
    private DatabaseReference mDetailClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_class_search_students);
        nik_lecturer = getIntent().getExtras().getString("nik_lecturer");
        classCode = getIntent().getExtras().getString("classCode");
        Faculty_student = getIntent().getExtras().getString("Faculty_student");
        Section_student = getIntent().getExtras().getString("Section_student");
        activationCode = getIntent().getExtras().getString("codeActivation");
       // System.out.println(activationCode);

        ClassCode = findViewById(R.id.class_code_id);
        Faculty = findViewById(R.id.faculty);
        Section = findViewById(R.id.section);
        Nik_lecturer = findViewById(R.id.nik_lecturer);
        ActivationCode = findViewById(R.id.activationCode_id);

        ClassCode.setText(classCode);
        ClassCode.setEnabled(false);

        Nik_lecturer.setText(nik_lecturer);
        Nik_lecturer.setEnabled(false);

        Faculty.setText(Faculty_student);
        Faculty.setEnabled(false);

        Section.setText(Section_student);
        Section.setEnabled(false);

        activationCode_str = ActivationCode.getText().toString().trim();


        btn_join = findViewById(R.id.btn_join_id);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classCode_str = ClassCode.getText().toString().trim();
                activationCode_str = ActivationCode.getText().toString().trim();
                Faculty_student_str = Faculty.getText().toString().trim();
                Section_student_str = Section.getText().toString().trim();
                nik_lecturer_str = Nik_lecturer.getText().toString().trim();
                System.out.println(activationCode_str);
                if (!activationCode.equals(activationCode_str)){
                    Toast.makeText(getApplicationContext(), "Activation Code Invalid", Toast.LENGTH_SHORT).show();
                }else{

                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    Date date = timestamp;
                    mDetailClass     = FirebaseDatabase.getInstance().getReference("detail_class");

                    detailClass DetailClass = new detailClass(
                            classCode_str,
                            "",
                            "0",
                            "",
                            ""+"",
                            "",
                            "",
                            date.toString(),
                            ""
                    );
                    mDetailClass.push().setValue(DetailClass);
                    Toast.makeText(getApplicationContext(), "Activation Code Valid", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
