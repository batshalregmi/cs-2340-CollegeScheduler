package com.example.collegeschedulerproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class ToDo extends AppCompatActivity {
    ArrayList<String> toDoList = new ArrayList<>();
    ListView toDoListView;
    Button addButton;
    EditText textBox;
    ArrayAdapter<String> ToDoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        toDoListView = findViewById(R.id.todo_list_view);
        addButton = findViewById(R.id.todo_add_button);
        textBox = findViewById(R.id.todo_edit_text);
        ToDoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDoList);
        getSupportActionBar().setTitle("To-Do List");


        toDoListView.setAdapter(ToDoAdapter);
        addButton.setOnClickListener(v -> {
            if (textBox.getText().toString().equals("")) {
                Toast.makeText(this, "Not enough information added!", Toast.LENGTH_SHORT).show();
            } else {
                toDoList.add(textBox.getText().toString());
                ToDoAdapter.notifyDataSetChanged();
                textBox.getText().clear();
            }
        });
        toDoListView.setOnItemLongClickListener((parent, view, position, id) -> {
            toDoList.remove(position);
            ToDoAdapter.notifyDataSetChanged();
            return true;
        });
        toDoListView.setOnItemClickListener((parent, view, position, id) -> {
            showEditDialog(position);
        });


    }

    private void showEditDialog(int position) {
        toDoListView = findViewById(R.id.todo_list_view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Item");

        EditText editText = new EditText(this);
        editText.setText(toDoList.get(position));
        builder.setView(editText);

        builder.setPositiveButton("Save", (dialog, which) -> {
            toDoList.set(position, editText.getText().toString());
            ToDoAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

}