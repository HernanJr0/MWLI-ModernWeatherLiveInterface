package com.android.ecoweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Locale;

public class LembreteActivity extends AppCompatActivity {

    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembretes);

        ImageButton backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void popTimePicker(View view) {
        Button timeButton = findViewById(R.id.timeButton);

        int sysFormat = DateFormat.is24HourFormat(this) ? TimeFormat.CLOCK_24H : TimeFormat.CLOCK_12H;

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);

        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(sysFormat)
                .setHour(hour)
                .setMinute(minute)
                .setTitleText("Select Date")
                .build();

        picker.addOnPositiveButtonClickListener(view1 ->
                timeButton.setText(MessageFormat.format("{0}:{1}", String.format(Locale.getDefault(), "%02d", picker.getHour()),
                                                                        String.format(Locale.getDefault(), "%02d", picker.getMinute()))
                )
        );

        picker.show(getSupportFragmentManager(), picker.toString());
    }
}