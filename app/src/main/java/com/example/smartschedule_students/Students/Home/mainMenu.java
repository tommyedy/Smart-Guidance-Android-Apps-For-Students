package com.example.smartschedule_students.Students.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.smartschedule_students.R;
import com.example.smartschedule_students.Students.Account_settings.Fragment_account_settings_students;
import com.example.smartschedule_students.Students.Note.Fragment_note_students;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class mainMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        BottomNavigationView bottomNav = findViewById(R.id.BottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame_container, new Fragment_home_students());
        tx.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.navigation_home:
                    selectedFragment = new Fragment_home_students();
                    break;
//                case R.id.navigation_schedule:
//                    selectedFragment = new Fragment_schedule_students();
//                    break;
                case R.id.navigation_note:
                    selectedFragment = new Fragment_note_students();
                    break;
                case R.id.navigation_account:
                    selectedFragment = new Fragment_account_settings_students();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
            return true;
        }
    };
}
