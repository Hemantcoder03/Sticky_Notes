package com.hemant.stickynotes.adapters;

import com.hemant.stickynotes.DBHelper;
import com.hemant.stickynotes.MainActivity;
import com.hemant.stickynotes.NoteActivity;
import com.hemant.stickynotes.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hemant.stickynotes.models.NotesModel;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.viewHolder> {



    ArrayList<NotesModel> list;
    Context context;

    public NotesAdapter(ArrayList<NotesModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_note,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        int id = list.get(position).getId();
        StringBuilder noteTitle = new StringBuilder(list.get(position).getTitle());
        StringBuilder noteDesc = new StringBuilder(list.get(position).getDescription());
        if(noteTitle.length()>28 && noteDesc.length()>45){
            holder.title.setText(noteTitle.replace(28,noteTitle.length(),"...."));
            holder.description.setText(noteDesc.replace(45,noteDesc.length(),"...."));
        }
        else if(noteTitle.length()>28){
            holder.title.setText(noteTitle.replace(28,noteTitle.length(),"...."));
            holder.description.setText(noteDesc);
        }
        else if(noteDesc.length()>45){
            holder.title.setText(noteTitle);
            holder.description.setText(noteDesc.replace(45,noteDesc.length(),"...."));
        }
        else{
            holder.title.setText(noteTitle);
            holder.description.setText(noteDesc);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra("coming from",101);
                intent.putExtra("id",id);
                intent.putExtra("id2",1);
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("description",list.get(position).getDescription());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete !!");
                builder.setIcon(R.drawable.delete_icon);
                builder.setCancelable(false);
                builder.setMessage("You really want to delete ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper helper = new DBHelper(context);
                        boolean delete = helper.deleteNote(id);
                        if (delete)
                            Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{
        TextView title,description;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }

    }
}
