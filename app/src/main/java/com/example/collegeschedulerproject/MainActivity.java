package com.example.collegeschedulerproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.collegeschedulerproject.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        getSupportActionBar().setTitle("College Scheduler");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button schedule = findViewById(R.id.schedule_button);
        schedule.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Schedule.class)));

        Button assignments = findViewById(R.id.assignment_button);
        assignments.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Assignments.class)));

        Button toDo = findViewById(R.id.todo_button);
        toDo.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ToDo.class)));

        Button examInfo = findViewById(R.id.examinfo_button);
        examInfo.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ExamInfo.class)));

    }
}