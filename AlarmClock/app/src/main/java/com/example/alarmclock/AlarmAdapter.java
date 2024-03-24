package com.example.alarmclock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private ArrayList<String> alarmList;
    private ArrayList<Boolean> alarmStates; // List to store the state of each alarm

    public AlarmAdapter(ArrayList<String> alarmList, ArrayList<Boolean> alarmStates) {
        this.alarmList = alarmList;
        this.alarmStates = alarmStates;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        String alarm = alarmList.get(position);
        boolean isAlarmOn = alarmStates.get(position); // Get the state of the alarm at this position
        holder.bind(alarm, isAlarmOn, alarmStates); // Pass alarmStates to the bind method
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    static class AlarmViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAlarm;
        private Switch switchAlarm;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAlarm = itemView.findViewById(R.id.textViewAlarm);
            switchAlarm = itemView.findViewById(R.id.switchAlarm);
        }

        public void bind(String alarm, boolean isAlarmOn, ArrayList<Boolean> alarmStates) {
            textViewAlarm.setText(alarm);
            switchAlarm.setChecked(isAlarmOn); // Set the state of the Switch based on the alarm state

            // Add a listener to handle switch state changes
            switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Handle switch state change
                    if (isChecked) {
                        // Switch is ON
                        // Update the state of the alarm in the list to ON
                        alarmStates.set(getAdapterPosition(), true);
                    } else {
                        // Switch is OFF
                        // Update the state of the alarm in the list to OFF
                        alarmStates.set(getAdapterPosition(), false);
                    }
                }
            });
        }
    }
}
