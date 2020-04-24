package com.example.smartschedule_students.Students.Note.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartschedule_students.Model.note;
import com.example.smartschedule_students.R;
import com.example.smartschedule_students.Students.Note.details_note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListNoteAdapter extends RecyclerView.Adapter<ListNoteAdapter.ListViewHolder> {
    private Context mctx;
    private List<note> list_noteData;


    public ListNoteAdapter(Context mctx, List<note> list_noteData) {
        this.mctx = mctx;
        this.list_noteData = list_noteData;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.list_notes_students, parent, false);
        //  return new ListViewHolder(LayoutInflater.from(mctx).inflate(R.layout.fragment_home_lecturer, parent, false));
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.classCode.setText(list_noteData.get(position).getNote_title().toUpperCase());
        holder.faculty.setText(list_noteData.get(position).getNim_students().toUpperCase());
        holder.section.setText(list_noteData.get(position).getClassCode().toUpperCase());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mctx, details_note.class);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String getDate = dateFormat.format(list_noteData.get(position).getDate());
                i.putExtra("classCode", list_noteData.get(position).getClassCode());
                i.putExtra("note", list_noteData.get(position).getNote());
                i.putExtra("note_title", list_noteData.get(position).getNote_title());
                i.putExtra("students_nim", list_noteData.get(position).getNim_students());
//                i.putExtra("nik_stat", listClassData.get(position).getNik_stat());
//                i.putExtra("timestamp", getDate);
//                i.putExtra("Faculty_lecturer", listClassData.get(position).getFaculty());
                mctx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_noteData.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        private TextView classCode, faculty, section, Schedule_id;
        private ImageView imageView;
        private CardView linearLayout;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            classCode = itemView.findViewById(R.id.title_guidance_id);
            faculty = itemView.findViewById(R.id.students_nim_id);
            section = itemView.findViewById(R.id.class_code_id);
            imageView = itemView.findViewById(R.id.imageView);
            linearLayout = itemView.findViewById(R.id.home_mRide);
        }
    }
}