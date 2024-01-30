package com.example.collegeschedulerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Schedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        TextView classList = findViewById(R.id.scheduleListTextView);
        EditText classSubject = findViewById(R.id.scheduleSubjectTextBox);
        EditText classNumber = findViewById(R.id.scheduleCourseNumber);
        EditText classTime = findViewById(R.id.scheduleTime);
        EditText professorName = findViewById(R.id.scheduleProfessorBox);
        ToggleButton addOrRemoveClass = findViewById(R.id.scheduleToggleButton);
        Button submit = findViewById(R.id.scheduleSubmitButton);

        //TODO: CHANGE ELEMENT TO LISTVIEW
        submit.setOnClickListener(v -> {
            if (classSubject.getText().toString().equals("") || classNumber.getText().toString().equals("")) {
                Toast.makeText(this, "Not enough information added!", Toast.LENGTH_SHORT).show();

            } else {
                if (!(addOrRemoveClass.isChecked())) {
                    classList.append(classSubject.getText().toString() + " " + classNumber.getText().toString() + " " + classTime.getText().toString() + " " + professorName.getText().toString() + "\n");
                } else {
                    //remove entire row from classList if it matches the class subject and number
                    String classListString = classList.getText().toString();
                    String[] classListArray = classListString.split("\n");
                    String classToRemove = classSubject.getText().toString() + " " + classNumber.getText().toString();
                    classListString = "";
                    for (String s : classListArray) {
                        if (!(s.contains(classToRemove))) {
                            classListString += s + "\n";
                        }
                    }
                    classList.setText(classListString);
                }
                classSubject.getText().clear();
                classNumber.getText().clear();
                classTime.getText().clear();
                professorName.getText().clear();

            }
//            } else {
//                if (!(addOrRemoveClass.isChecked())) {
//                    classList.append(classSubject.getText().toString() + " " + classTime.getText().toString() + " " + professorName.getText().toString() + "\n");
//                } else {
//                    String classListString = classList.getText().toString();
//                    classListString = classListString.replace(classSubject.getText().toString() + " " + classTime.getText().toString() + " " + professorName.getText().toString() + "\n", "";
//                    classList.setText(classListString);
//                }
//                classSubject.getText().clear();
//                classTime.getText().clear();
//            }
        });
    }
}