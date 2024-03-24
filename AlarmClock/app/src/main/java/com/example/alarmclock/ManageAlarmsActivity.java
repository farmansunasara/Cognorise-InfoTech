package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ManageAlarmsActivity extends AppCompatActivity {

    private ArrayList<String> alarmList;
    private ArrayList<Boolean> alarmStates; // ArrayList to store alarm states (ON/OFF)
    private RecyclerView recyclerViewAlarms;
    private SharedPreferences alarmPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_alarms);
        alarmPreferences = getSharedPreferences("AlarmPreferences", MODE_PRIVATE);

        // Retrieve the set of alarms from SharedPreferences
        int hour = alarmPreferences.getInt("hour", 0);
        int minute = alarmPreferences.getInt("minute", 0);
        String alarmTime = String.format("%02d:%02d", hour, minute);

        // Initialize alarmList and alarmStates ArrayLists
        alarmList = new ArrayList<>();
        alarmStates = new ArrayList<>();

        // Add the alarm time to the list
        alarmList.add(alarmTime);

        // Initialize all alarm states to false (OFF) initially
        alarmStates.add(false);

        Log.d("AlarmListSize", String.valueOf(alarmList.size()));

        // Initialize RecyclerView
        recyclerViewAlarms = findViewById(R.id.recyclerViewAlarms);
        recyclerViewAlarms.setLayoutManager(new LinearLayoutManager(this));

        // Create and set the adapter for RecyclerView
        AlarmAdapter adapter = new AlarmAdapter(alarmList, alarmStates); // Pass both ArrayLists to the adapter
        recyclerViewAlarms.setAdapter(adapter);
    }
}
