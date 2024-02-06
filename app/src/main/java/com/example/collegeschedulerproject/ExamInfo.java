package com.example.collegeschedulerproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.*;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class ExamInfo extends AppCompatActivity {
    ArrayList<String> ExamInfoList = new ArrayList<>();
    ListView ExamInfoListView;
    Button addButton;
    EditText textBox;
    ArrayAdapter<String> ExamInfoAdapter;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_info);
        getSupportActionBar().setTitle("Exam Info");
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        ExamInfoListView = findViewById(R.id.exam_list_view);
        addButton = findViewById(R.id.exam_add_button);
        txtTime = findViewById(R.id.btnTimePicker);
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
        txtTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                {

                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(ExamInfo.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    txtTime.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });
        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ExamInfo.this).ExamInfoAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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
