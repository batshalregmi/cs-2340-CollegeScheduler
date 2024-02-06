package com.example.collegeschedulerproject;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.*;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
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
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_info);
        getSupportActionBar().setTitle("Exam Info");
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        ExamInfoListView = findViewById(R.id.exam_list_view);
        addButton = findViewById(R.id.exam_add_button);
        txtTime = findViewById(R.id.btnTimePicker);
        textBox = findViewById(R.id.exam_edit_text);
        txtDate = findViewById(R.id.btnDatePicker);
        ExamInfoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ExamInfoList);
        ExamInfoListView.setAdapter(ExamInfoAdapter);
        addButton.setOnClickListener(v -> {
            if (textBox.getText().toString().equals("")) {
                Toast.makeText(this, "Not enough information added!", Toast.LENGTH_SHORT).show();
            } else {
                ExamInfoList.add("Exam Name: " + textBox.getText().toString() + "\nExam Date: " + txtDate.getText().toString() + "\nTime: " +  txtTime.getText().toString());
                ExamInfoAdapter.notifyDataSetChanged();
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                textBox.getText().clear();
                txtDate.getText().clear();
                txtTime.getText().clear();

            }
        });
        ExamInfoListView.setOnItemLongClickListener((parent, view, position, id) -> {
            ExamInfoList.remove(position);
            ExamInfoAdapter.notifyDataSetChanged();
            return true;
        });
        ExamInfoListView.setOnItemClickListener((parent, view, position, id) -> {
            showEditDialog(position);
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        });
        txtTime.setFocusable(false);
        txtDate.setFocusable(false);
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

                                    //convert to 12hr time
                                    if (hourOfDay > 12) {
                                        hourOfDay = hourOfDay - 12;
                                        txtTime.setText(hourOfDay + ":" + minute + " PM");
                                    } else if (hourOfDay == 0) {
                                        hourOfDay = 12;
                                        txtTime.setText(hourOfDay + ":" + minute + " AM");
                                    } else if (hourOfDay == 12) {
                                        txtTime.setText(hourOfDay + ":" + minute + " PM");
                                    } else {
                                        txtTime.setText(hourOfDay + ":" + minute + " AM");
                                    }
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        });
        txtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(ExamInfo.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText((monthOfYear + 1) + "-" + dayOfMonth +  "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
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
        ExamInfoListView = findViewById(R.id.ToDo_list_view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Item");

        EditText editText = new EditText(this);
        editText.setText(ExamInfoList.get(position));
        builder.setView(editText);

        builder.setPositiveButton("Save", (dialog, which) -> {
            ExamInfoList.set(position, editText.getText().toString());
            ExamInfoAdapter.notifyDataSetChanged();
            dialog.dismiss();
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        builder.show();
        InputMethodManager imm2 = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm2.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


}
