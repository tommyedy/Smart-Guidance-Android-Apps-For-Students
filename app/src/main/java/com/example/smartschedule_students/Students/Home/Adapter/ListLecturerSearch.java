package com.example.smartschedule_students.Students.Home.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.smartschedule_students.Model.userLecturer;
import com.example.smartschedule_students.R;
import com.example.smartschedule_students.Students.Home.result_seach_students;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListLecturerSearch extends RecyclerView.Adapter<ListLecturerSearch.ListViewHolder> implements Filterable {
    private Context mctx;
    private List<userLecturer> listlecturerData;
    private List<userLecturer> listlecturerDataAll;

    public ListLecturerSearch(Context mctx, List<userLecturer> listlecturerData) {
        this.mctx = mctx;
        this.listlecturerData = listlecturerData;
        this.listlecturerDataAll = new ArrayList<>(listlecturerData);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.list_lecturer_students, parent, false);
        //  return new ListViewHolder(LayoutInflater.from(mctx).inflate(R.layout.fragment_home_lecturer, parent, false));
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.nik_lecturer.setText(listlecturerData.get(position).getNik().toUpperCase());
        holder.name_students.setText(listlecturerData.get(position).getFirstname().concat(" ").concat(listlecturerData.get(position).getLastname()).toUpperCase());
        //holder.lastname_students.setText(listlecturerData.get(position).getFirstname().toUpperCase());
        holder.faculty.setText(listlecturerData.get(position).getFaculty().toUpperCase());
        holder.section.setText(listlecturerData.get(position).getSection().toUpperCase());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mctx, result_seach_students.class);
                i.putExtra("nik_lecturer", listlecturerData.get(position).getNik());
                mctx.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listlecturerData.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<userLecturer> lecturer = new ArrayList<userLecturer>();
            if (charSequence.toString().isEmpty()){
                lecturer.addAll(listlecturerData);
            }else {

                            for (userLecturer list : listlecturerData) {
                                if (list.getFirstname().toLowerCase().contains(charSequence.toString().toLowerCase()) || (list.getLastname().toLowerCase().contains(charSequence.toString().toLowerCase()))) {
                                    lecturer.add(list);
                                }
                            }
                        }
            FilterResults filterResults =new FilterResults();
            filterResults.values = lecturer;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listlecturerData.clear();
            listlecturerData.addAll((Collection<? extends userLecturer>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class ListViewHolder extends RecyclerView.ViewHolder{
        private TextView nik_lecturer, name_students, lastname_students, faculty, section;
        private ImageView imageView;
        private CardView cardView;


        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            nik_lecturer = itemView.findViewById(R.id.codeLecturer_id);
            name_students = itemView.findViewById(R.id.nameLecturer_id);
            //lastname_students = itemView.findViewById(R.id.codeClass_id);
            faculty = itemView.findViewById(R.id.faculty_id);
            section = itemView.findViewById(R.id.jurusan_id);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.home_mRide);

        }
    }
}