package com.example.collegeschedulerproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.*;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Assignments extends AppCompatActivity {
    ArrayList<String> AssignmentsList = new ArrayList<>();
    ListView AssignmentsListView;
    Button addButton;
    EditText textBox;
    ArrayAdapter<String> AssignmentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
//        initSearchWidgets();
        getSupportActionBar().setTitle("Assignments Info");
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        AssignmentsListView = findViewById(R.id.Assignments_list_view);
        addButton = findViewById(R.id.Assignments_add_button);
        textBox = findViewById(R.id.Assignments_edit_text);
        AssignmentsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AssignmentsList);

        AssignmentsListView.setAdapter(AssignmentsAdapter);
        addButton.setOnClickListener(v -> {
            if (textBox.getText().toString().equals("")) {
                Toast.makeText(this, "Not enough information added!", Toast.LENGTH_SHORT).show();
            } else {
                AssignmentsList.add(textBox.getText().toString());
                AssignmentsAdapter.notifyDataSetChanged();
                textBox.getText().clear();
            }
        });
        AssignmentsListView.setOnItemLongClickListener((parent, view, position, id) -> {
            AssignmentsList.remove(position);
            AssignmentsAdapter.notifyDataSetChanged();
            return true;
        });
        AssignmentsListView.setOnItemClickListener((parent, view, position, id) -> {
            showEditDialog(position);
        });
        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (Assignments.this).AssignmentsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void showEditDialog(int position) {
        AssignmentsListView = findViewById(R.id.todo_list_view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Item");

        EditText editText = new EditText(this);
        editText.setText(AssignmentsList.get(position));
        builder.setView(editText);

        builder.setPositiveButton("Save", (dialog, which) -> {
            AssignmentsList.set(position, editText.getText().toString());
            AssignmentsAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

}
