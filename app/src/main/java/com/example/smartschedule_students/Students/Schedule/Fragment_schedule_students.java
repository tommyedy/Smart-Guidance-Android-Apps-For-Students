package com.example.smartschedule_students.Students.Schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartschedule_students.R;

public class Fragment_schedule_students extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_students, container, false);
        //btn_addClass = view.findViewById(R.id.create_class);
        //tv_nama_user = view.findViewById(R.id.nama_user);
        return view;
    }
}
