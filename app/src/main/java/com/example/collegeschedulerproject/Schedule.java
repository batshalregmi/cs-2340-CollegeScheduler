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
        EditText className = findViewById(R.id.scheduleSubjectTextBox);
        EditText classTime = findViewById(R.id.scheduleTime);
        ToggleButton addOrRemoveClass = findViewById(R.id.scheduleToggleButton);
        Button submit = findViewById(R.id.scheduleSubmitButton);

        submit.setOnClickListener(v -> {
            if (className.getText().toString().equals("") || classTime.getText().toString().equals("")) {
                Toast.makeText(this, "No Class/Time Added", Toast.LENGTH_SHORT).show();
            } else {
                if (!(addOrRemoveClass.isChecked())) {
                    classList.append(className.getText().toString() + " " + classTime.getText().toString() + "\n");
                } else {
                    String classListString = classList.getText().toString();
                    classListString = classListString.replace(className.getText().toString() + " " + classTime.getText().toString() + "\n", "");
                    classList.setText(classListString);
                }
            }
        });
    }
}