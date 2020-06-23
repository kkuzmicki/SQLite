package com.example.a012sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentRecyclerViewAdapter extends RecyclerView.Adapter<StudentRecyclerViewAdapter.ViewHolder>
{
    List<Student> studentList;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;

    StudentRecyclerViewAdapter(Context context, List<Student> studentList)
    {
        this.inflater = LayoutInflater.from(context);
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Student student = studentList.get(position);
        holder.studentTextView.setText(student.toString());
        holder.student = student;
    }

    @Override
    public int getItemCount()
    {
        return studentList.size();
    }

    public interface ItemClickListener
    {
        void onDeleteItemClick(View view, int position, Student student); // tu mozna dodać inne akcje np. edycję
        void onEditItemClick(View view, int position, Student student);
        void onTextClick(View view, int position, Student student);
    }
    void setClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView studentTextView;
        ImageButton deleteButton;
        ImageButton editButton;
        Student student;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            studentTextView = itemView.findViewById(R.id.studentTV);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (itemClickListener != null) itemClickListener.onDeleteItemClick(view, getAdapterPosition(), student);
                }
            });

            editButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(itemClickListener != null) itemClickListener.onEditItemClick(view, getAdapterPosition(), student);
                }
            });

            studentTextView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(itemClickListener != null) itemClickListener.onTextClick(view, getAdapterPosition(), student);
                }
            });
        }
    }
}