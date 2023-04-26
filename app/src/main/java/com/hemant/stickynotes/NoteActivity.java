package com.hemant.stickynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText noteTitle,noteDesc;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        noteTitle = findViewById(R.id.noteTitle);
        noteDesc = findViewById(R.id.noteDesc);
        saveBtn = findViewById(R.id.saveBtn);

        DBHelper helper = new DBHelper(this);

        int id2 = getIntent().getIntExtra("id2",0);
        if(id2==1){
            String title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");
            noteTitle.setText(title);
            noteDesc.setText(description);
        }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSaved = false;
                int from = getIntent().getIntExtra("coming from",0);
                if(from==100) {
                    isSaved = helper.insertNote(noteTitle.getText().toString(), noteDesc.getText().toString());
                }
                else{
                    int id = getIntent().getIntExtra("id",0);
                    isSaved = helper.updateNote(id,noteTitle.getText().toString(), noteDesc.getText().toString());
                }
                if (isSaved) {
                    Toast.makeText(NoteActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NoteActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}