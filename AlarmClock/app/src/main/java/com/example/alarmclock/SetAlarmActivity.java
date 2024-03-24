package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetAlarmActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private SharedPreferences alarmPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        timePicker = findViewById(R.id.timePicker);
        Button btnSetAlarm = findViewById(R.id.btnSetAlarm);

        alarmPreferences = getSharedPreferences("AlarmPreferences", Context.MODE_PRIVATE);

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
                Intent intent = new Intent(SetAlarmActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setAlarm() {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        Log.d("SetAlarmActivity", "Alarm set for " + hour + ":" + minute);

        // Save alarm settings
        saveAlarmSettings(hour, minute);

        // Schedule alarm using AlarmManager
        scheduleAlarm(hour, minute);
    }

    private void saveAlarmSettings(int hour, int minute) {
        SharedPreferences.Editor editor = alarmPreferences.edit();

        editor.putInt("hour", hour);
        editor.putInt("minute", minute);
        editor.apply();
        Log.d("SetAlarmActivity", "Alarm settings saved: Hour = " + hour + ", Minute = " + minute);
    }

    private void scheduleAlarm(int hour, int minute) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        long alarmTime = calendar.getTimeInMillis();

        Log.d("SetAlarmActivity", "Alarm scheduled for: " + calendar.getTime());

        // Set alarm to trigger at the specified time
        try {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        } catch (SecurityException e) {
            Log.e("SetAlarmActivity", "SecurityException: " + e.getMessage());
        }
    }
}
