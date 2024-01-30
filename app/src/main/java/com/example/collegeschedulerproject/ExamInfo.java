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

public class ExamInfo extends AppCompatActivity {
    ArrayList<String> ExamInfoList = new ArrayList<>();
    ListView ExamInfoListView;
    Button addButton;
    EditText textBox;
    ArrayAdapter<String> ExamInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_info);
        ExamInfoListView = findViewById(R.id.exam_list_view);
        addButton = findViewById(R.id.exam_add_button);
        textBox = findViewById(R.id.exam_edit_text);
        ExamInfoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ExamInfoList);

        ExamInfoListView.setAdapter(ExamInfoAdapter);
        addButton.setOnClickListener(v -> {
            if (textBox.getText().toString().equals("")) {
                Toast.makeText(this, "Not enough information added!", Toast.LENGTH_SHORT).show();
            } else {
                ExamInfoList.add(textBox.getText().toString());
                ExamInfoAdapter.notifyDataSetChanged();
                textBox.getText().clear();
            }
        });
        ExamInfoListView.setOnItemLongClickListener((parent, view, position, id) -> {
            ExamInfoList.remove(position);
            ExamInfoAdapter.notifyDataSetChanged();
            return true;
        });
        ExamInfoListView.setOnItemClickListener((parent, view, position, id) -> {
            showEditDialog(position);
        });


    }

    private void showEditDialog(int position) {
        ExamInfoListView = findViewById(R.id.todo_list_view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Item");

        EditText editText = new EditText(this);
        editText.setText(ExamInfoList.get(position));
        builder.setView(editText);

        builder.setPositiveButton("Save", (dialog, which) -> {
            ExamInfoList.set(position, editText.getText().toString());
            ExamInfoAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

}
