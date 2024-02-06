package com.example.collegeschedulerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        classList = findViewById(R.id.schedule_list_view);
        classSubject = findViewById(R.id.scheduleSubjectTextBox);
        classNumber = findViewById(R.id.scheduleCourseNumber);
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
                classTime.getText().clear();
                professorName.getText().clear();
            }
        });
    }
}