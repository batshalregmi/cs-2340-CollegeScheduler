package com.example.collegeschedulerproject;

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
    Button classTime;
    EditText professorName;
    ToggleButton addOrRemoveClass;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                    classListView.add(classSubject.getText().toString() + " " + classNumber.getText().toString() + " " + classTime.getText().toString() + " " + professorName.getText().toString() + "\n");
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
        classList.setOnItemClickListener((parent, view, position, id) -> {
            showEditDialog(position);
        });

        classTime.setOnClickListener(l -> {
            new TimePickerFragment().show(getSupportFragmentManager(), "timePicker");
            //override onTimeSet
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
        classList = findViewById(R.id.todo_list_view);
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
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker.
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it.
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time the user picks.
        }
    }

}