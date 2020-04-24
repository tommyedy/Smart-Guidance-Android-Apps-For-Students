package com.example.smartschedule_students;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartschedule_students.GlobalFunction.Connection;
import com.example.smartschedule_students.Model.userDetails;
import com.example.smartschedule_students.Model.userStudents;
import com.example.smartschedule_students.Students.Home.mainMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {
    private Button btnSignInEmail;
    private EditText Et_email, Et_password;
    private TextView Signupwithemail;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mUserDetails, mStudents;  //mUserDetails,
    //private DatabaseReference mUserDetails;


    Connection cd;
    // private Boolean exit = false;
    // GoogleSignInClient gsc;

    private TextInputLayout
            email_lecturer_error,
            password_lecturer_error;
    private static boolean isEmailValid,
            isPasswordValid;

    private static String email, password;

    // private List<userDetails> listUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        cd = new Connection(getApplicationContext());
        if (cd.hasInternetConnection()) {
            setContentView(R.layout.activity_main);
        //    FirebaseApp.initializeApp(this);
            mAuth = FirebaseAuth.getInstance();
            // mUserDetails = FirebaseDatabase.getInstance();
            //mUserDetails = DatabaseRefe
            mStudents = FirebaseDatabase.getInstance();
            Signupwithemail = findViewById(R.id.sign_up_with_email);
            btnSignInEmail = findViewById(R.id.login);
            Et_email = findViewById(R.id.email);
            Et_password = findViewById(R.id.password);
            email = Et_email.getText().toString().trim();
            password = Et_password.getText().toString().trim();


            //Error Value From XML

            email_lecturer_error = findViewById(R.id.emailError);
            password_lecturer_error = findViewById(R.id.passError);


            btnSignInEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SetValidation()) {
                        Et_email = findViewById(R.id.email);
                        Et_password = findViewById(R.id.password);
                        email = Et_email.getText().toString().trim();
                        password = Et_password.getText().toString().trim();
                      //  System.out.println(email);
                      //  System.out.println(password);
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            AuthResult result = task.getResult();
                                            //Get UID
                                            String idUser = result.getUser().getUid();
                                            Query userDetail = FirebaseDatabase.getInstance().getReference("users_detail");
                                            userDetail.orderByChild("uid")
                                                    .equalTo(idUser)
                                                    .addChildEventListener(new ChildEventListener() {
                                                        @Override
                                                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                                            // System.out.println(dataSnapshot.getValue().toString());
                                                            System.out.println(dataSnapshot.getValue());
                                                            userDetails detail = dataSnapshot.getValue(userDetails.class);
                                                            // System.out.println(detail.getNik_lecturer());
                                                            String nim = detail.getIdentity_number();
                                                            Query Lecturer = FirebaseDatabase.getInstance().getReference("students");//.child(nik);
                                                            Lecturer.orderByChild("nim")
                                                                    .equalTo(nim)
                                                                    .addChildEventListener(new ChildEventListener() {
                                                                        @Override
                                                                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                                                            userStudents students = dataSnapshot.getValue(userStudents.class);
                                                                            String Firstname = students.getFirstname();
                                                                            String lastname = students.getLastname();
                                                                            String email = students.getEmail();
                                                                            String faculty = students.getFaculty();
                                                                            String section = students.getSection();
                                                                            String nimStudents = students.getNim();
                                                                            System.out.println(email);
                                                                            Intent i = new Intent(MainActivity.this, mainMenu.class);

                                                                            i.putExtra("firstname_student", Firstname);
                                                                            i.putExtra("lastname_student", lastname);
                                                                            i.putExtra("email_student", email);
                                                                            i.putExtra("faculty_student", faculty);
                                                                            i.putExtra("section_student", section);
                                                                            i.putExtra("nim", nimStudents);

                                                                            startActivity(i);
                                                                        }

                                                                        @Override
                                                                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                                        }

                                                                        @Override
                                                                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                                                        }

                                                                        @Override
                                                                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                            Toast.makeText(getApplicationContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }

                                                        @Override
                                                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                        }

                                                        @Override
                                                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                                        }

                                                        @Override
                                                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });


                                        }
                                    }
                                }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

            Signupwithemail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // mUserDetails    = FirebaseDatabase.getInstance().getReference("class");
                    // mUserDetails.removeValue();
                    Intent i = new Intent(MainActivity.this, signUpStudents.class);
                    startActivity(i);
                }
            });
        } else {
            //   Intent i = new Intent(MainActivity.this, Try_connection.class);
            //  startActivity(i);
        }
    }

    private boolean SetValidation() {
        // Check for a valid identity number.
        // Check for a valid email address.
        if (Et_email.getText().toString().isEmpty()) {
            email_lecturer_error.setError(getResources().getString(R.string.emailStudentsError));
            isEmailValid = false;
        } else if ((!Patterns.EMAIL_ADDRESS.matcher(Et_email.getText().toString()).matches()) && (!Et_email.getText().toString().contains("@unai.edu"))) {
            email_lecturer_error.setError(getResources().getString(R.string.emailStudentsError));
            isEmailValid = false;
        } else {
            isEmailValid = true;
            email_lecturer_error.setErrorEnabled(false);
        }


        // Check for a valid password.
        if (Et_password.getText().toString().isEmpty()) {
            password_lecturer_error.setError(getResources().getString(R.string.passwordStudentsError));
            isPasswordValid = false;
        } else if (Et_password.getText().length() < 6) {
            password_lecturer_error.setError("Password Less than 6");
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
            password_lecturer_error.setErrorEnabled(false);
        }

        if (isEmailValid && isPasswordValid) {
            //Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }

    }
}
