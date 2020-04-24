package com.example.smartschedule_students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smartschedule_students.Model.Section;
import com.example.smartschedule_students.Model.faculty;
import com.example.smartschedule_students.Model.userDetails;
import com.example.smartschedule_students.Model.userStudents;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class signUpStudents extends AppCompatActivity {
    //Variabel Umum
    //Activity Atribute
    private Button BtnSignUp;
    private ImageView BtnCancel;
    //private AESCrypt passwordEncrypt;
    private SearchableSpinner spinnerJurusan, spinnerSection;

    //Identity Variable
    private EditText
            Et_email_students,
            Et_nim_students,
            Et_firstname_students,
            Et_lastname_students,
            Et_password_students;
    private DatabaseReference mUsers, mstudents, mFaculty, mSection;
    private FirebaseAuth mAuth;

    private TextInputLayout
            nim_students_error,
            firstName_students_error,
            lastName_students_error,
            email_students_error,
            password_students_error,
            faculty_students_error,
            section_students_error;
    private static boolean
            isNimValid,
            isFirstnameValid,
            isLastnameValid,
            isEmailValid,
            isPasswordValid,
            isFacultyValid,
            isSectionValid;

    private static String
            encrypted_password,
            nim_students,
            firstName_students,
            lastName_students,
            email_students,
            password_students,
            faculty_students,
            section_students;

    private ProgressBar RegistrationProcess;
    //  private ProgressDialog RegistrationProcess;
   // iFirebaseLoadDone IFirebaseLoadDone;

    List<faculty> facultiss;
    List<Section> sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_students);
        //Get Value From XML
        Et_nim_students       = findViewById(R.id.nimStudents_id);
        Et_firstname_students = findViewById(R.id.firstnameStudents_id);
        Et_lastname_students  = findViewById(R.id.lastnameStudents_id);
        Et_email_students     = findViewById(R.id.emailStudents_id);
        Et_password_students  = findViewById(R.id.passwordStudents_id);
//        spinnerJurusan        = findViewById(R.id.facultyStudents_id);
//        spinnerSection        = findViewById(R.id.sectionStudents_id);
      //  RegistrationProcess   = findViewById(R.id.progressBar);


      //  spinnerJurusan.setPositiveButton("Choose faculty");

        mAuth = FirebaseAuth.getInstance();
        //Button
        BtnCancel             = findViewById(R.id.btnCancel);
        BtnSignUp             = findViewById(R.id.btnsign_up_with_email_students_id);
      //  IFirebaseLoadDone     = (iFirebaseLoadDone) this;

        //Error Value From XML
        nim_students_error       = findViewById(R.id.nimStudentsError_id);
        firstName_students_error = findViewById(R.id.firstnameStudentsError_id);
        lastName_students_error  = findViewById(R.id.lastnameStudentsError_id);
        email_students_error     = findViewById(R.id.emailStudentsError_id);
//        faculty_students_error   = findViewById(R.id.facultyStudentserror_id);
//        section_students_error   = findViewById(R.id.sectionStudentsError_id);
        password_students_error  = findViewById(R.id.passwordStudentsError_id);

//        spinnerSection.setEnabled(false);

        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SetValidation()) {
                    nim_students       = Et_nim_students.getText().toString().trim();
                    firstName_students = Et_firstname_students.getText().toString().trim();
                    lastName_students  = Et_lastname_students.getText().toString().trim();
                    email_students     = Et_email_students.getText().toString().trim();
                    password_students  = Et_password_students.getText().toString().trim();
                    faculty_students   = "teknik dan ilmu komputer";//spinnerJurusan.getSelectedItem().toString();
                    section_students   = "teknik informatika"; //spinnerSection.getSelectedItem().toString();
                    mstudents          = FirebaseDatabase.getInstance().getReference("students");
                    mUsers             = FirebaseDatabase.getInstance().getReference("users_detail");
                    mAuth.createUserWithEmailAndPassword(email_students, password_students).addOnCompleteListener(task-> {
                        //    RegistrationProcess.setVisibility(View.VISIBLE);

                        if (task.isSuccessful()) {
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            Date date = timestamp;
                            String users_id = mAuth.getCurrentUser().getUid();
                            userStudents studentsData = new userStudents(
                                    nim_students,
                                    firstName_students,
                                    lastName_students,
                                    email_students,
                                    faculty_students,
                                    section_students,
                                    date.toString());
                            userDetails userDetails = new userDetails(
                                    users_id,
                                    nim_students,
                                    date.toString()
                            );
                            mstudents.child(nim_students).setValue(studentsData);
                            mUsers.push().setValue(userDetails);
                            Toast.makeText(getApplicationContext(), "Data Successfully Registered", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(signUpStudents.this, MainActivity.class);
//                            i.putExtra("nim", nim_students);
//                            i.putExtra("firsname_students", firstName_students);
//                            i.putExtra("lastname_students", lastName_students);
//                            i.putExtra("email_students", email_students);
//                            i.putExtra("faculty_students", faculty_students);
//                            i.putExtra("section_students", section_students);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(), ""+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });

                }
            }
        });

    }

    private boolean SetValidation() {
        // Check for a valid identity number.
        if (Et_nim_students.getText().toString().isEmpty()) {
            nim_students_error.setError(getResources().getString(R.string.nimStudentsError));
            isNimValid = false;
        } else if (Et_nim_students.getText().toString().length()<7){
            nim_students_error.setError(getResources().getString(R.string.nimStudentsError));
            isNimValid = false;
        } else{
            isNimValid = true;
            nim_students_error.setErrorEnabled(false);
        }

        // Check for a valid first name.
        if (Et_firstname_students.getText().toString().isEmpty()) {
            firstName_students_error.setError(getResources().getString(R.string.firstnameStudentsError));
            isFirstnameValid = false;
        } else  {
            isFirstnameValid = true;
            firstName_students_error.setErrorEnabled(false);
        }

        // Check for a valid last name.
        if (Et_lastname_students.getText().toString().isEmpty()) {
            lastName_students_error.setError(getResources().getString(R.string.lastnameStudentsError));
            isLastnameValid = false;
        } else  {
            isLastnameValid = true;
            lastName_students_error.setErrorEnabled(false);
        }

        // Check for a valid email address.
        if (Et_email_students.getText().toString().isEmpty()) {
            email_students_error.setError(getResources().getString(R.string.emailStudentsError));
            isEmailValid = false;
        } else if ((!Patterns.EMAIL_ADDRESS.matcher(Et_email_students.getText().toString()).matches()) && (!Et_email_students.getText().toString().contains("@unai.edu"))) {
            email_students_error.setError(getResources().getString(R.string.emailStudentsError));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
            email_students_error.setErrorEnabled(false);
        }

        // Check for a valid faculty name.
//        if (spinnerJurusan.getSelectedItem().toString().isEmpty()) {
//            faculty_students_error.setError(getResources().getString(R.string.facultyStudentsError));
//            isFacultyValid = false;
//        } else  {
//            isFacultyValid = true;
//            faculty_students_error.setErrorEnabled(false);
//        }
//
//        // Check for a valid section name.
//        if (spinnerSection.getSelectedItem().toString().isEmpty()) {
//            section_students_error.setError(getResources().getString(R.string.sectionStudentsError));
//            isSectionValid = false;
//        } else  {
//            isSectionValid = true;
//            section_students_error.setErrorEnabled(false);
//        }


        // Check for a valid password.
        if (Et_password_students.getText().toString().isEmpty()) {
            password_students_error.setError(getResources().getString(R.string.passwordStudentsError));
            isPasswordValid = false;
        } else if (Et_password_students.getText().length() < 6) {
            password_students_error.setError("Password Less than 6");
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            password_students_error.setErrorEnabled(false);
        }

        if (isNimValid && isEmailValid && isFirstnameValid && isLastnameValid && isPasswordValid) {
            //Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }

    }
}
