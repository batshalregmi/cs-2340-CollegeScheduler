package com.example.collegeschedulerproject;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {
    ArrayList<String> classListView = new ArrayList<>();
    ArrayAdapter<String> classListAdapter;
    ListView classList;
    EditText classSubject;
    EditText classNumber;
    EditText classTime;
    EditText professorName;
    ToggleButton addOrRemoveClass;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        classList = findViewById(R.id.schedule_list_view);
        classSubject = findViewById(R.id.scheduleSubjectTextBox);
        classNumber = findViewById(R.id.scheduleCourseNumber);
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        classTime = findViewById(R.id.scheduleTime);
        professorName = findViewById(R.id.scheduleProfessorBox);
        addOrRemoveClass = findViewById(R.id.scheduleToggleButton);
        submit = findViewById(R.id.scheduleSubmitButton);

        classListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classListView);
        classList.setAdapter(classListAdapter);
        getSupportActionBar().setTitle("Schedule");

        //TODO: CHANGE ELEMENT TO LISTVIEW
        submit.setOnClickListener(v -> {
            if (classSubject.getText().toString().equals("") || classNumber.getText().toString().equals("")) {
                Toast.makeText(this, "Not enough information added!", Toast.LENGTH_SHORT).show();

            } else {
                if (!(addOrRemoveClass.isChecked())) {
                    classListView.add("Subject: " + classSubject.getText().toString() + classNumber.getText().toString() + "\nStart Time: " + classTime.getText().toString() + "\nInstructor: " + professorName.getText().toString());
                    classListAdapter.notifyDataSetChanged();
                } else {
                    //remove entire row from classList if it matches the class subject and number
                    for (int i = 0; i < classListView.size(); i++) {
                        if (classListView.get(i).contains(classSubject.getText().toString() + " " + classNumber.getText().toString())) {
                            classListView.remove(i);
                            classListAdapter.notifyDataSetChanged();
                        }
                    }
                }
                classSubject.getText().clear();
                classNumber.getText().clear();
                professorName.getText().clear();
            }
        });
        classList.setOnItemLongClickListener((parent, view, position, id) -> {
            classListView.remove(position);
            classListAdapter.notifyDataSetChanged();
            return true;
        });
        classTime.setFocusable(false);
        classList.setOnItemClickListener((parent, view, position, id) -> {
            showEditDialog(position);
        });
        classTime.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay, mHour, mMinute;

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Schedule.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                //convert to 12hr time
                                if (hourOfDay > 12) {
                                    hourOfDay = hourOfDay - 12;
                                    classTime.setText(hourOfDay + ":" + minute + " PM");
                                } else if (hourOfDay == 0) {
                                    hourOfDay = 12;
                                    classTime.setText(hourOfDay + ":" + minute + " AM");
                                } else if (hourOfDay == 12) {
                                    classTime.setText(hourOfDay + ":" + minute + " PM");
                                } else {
                                    classTime.setText(hourOfDay + ":" + minute + " AM");
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (Schedule.this).classListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


    }
    private void showEditDialog(int position) {
        classList = findViewById(R.id.ToDo_list_view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Item");

        EditText editText = new EditText(this);
        editText.setText(classListView.get(position));
        builder.setView(editText);

        builder.setPositiveButton("Save", (dialog, which) -> {
            classListView.set(position, editText.getText().toString());
            classListAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }


}