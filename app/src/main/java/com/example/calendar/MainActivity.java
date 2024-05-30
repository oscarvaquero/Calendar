package com.example.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private TextView tvSelectedDate;
    private Button btnSelectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Crear el RelativeLayout como contenedor principal
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        relativeLayout.setBackgroundColor(Color.WHITE);  // Fondo blanco para mejor contraste

        // Crear y configurar el TextView
        tvSelectedDate = new TextView(this);
        int tvSelectedDateId = View.generateViewId();
        tvSelectedDate.setId(tvSelectedDateId);
        RelativeLayout.LayoutParams tvLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tvLayoutParams.setMargins(0, 100, 0, 0);
        tvSelectedDate.setLayoutParams(tvLayoutParams);
        tvSelectedDate.setText("Selected Date");
        tvSelectedDate.setTextSize(24);  // Aumentar tamaño de texto
        tvSelectedDate.setTextColor(Color.BLUE);  // Cambiar color del texto

        // Crear y configurar el Button
        btnSelectDate = new Button(this);
        int btnSelectDateId = View.generateViewId();
        btnSelectDate.setId(btnSelectDateId);
        RelativeLayout.LayoutParams btnLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        btnLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        btnSelectDate.setLayoutParams(btnLayoutParams);
        btnSelectDate.setText("Select Date");
        btnSelectDate.setTextSize(18);  // Aumentar tamaño de texto
        btnSelectDate.setBackgroundColor(Color.BLUE);  // Cambiar color de fondo del botón
        btnSelectDate.setTextColor(Color.WHITE);  // Cambiar color del texto del botón

        // Agregar TextView y Button al RelativeLayout
        relativeLayout.addView(btnSelectDate);
        relativeLayout.addView(tvSelectedDate);

        // Establecer el RelativeLayout como la vista principal de la actividad
        setContentView(relativeLayout);

        // Configurar el listener para el botón
        btnSelectDate.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        long today = MaterialDatePicker.todayInUtcMilliseconds();

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(today)
                .setCalendarConstraints(constraintsBuilder.build())
                .build();

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            // Convertir la fecha seleccionada a la zona horaria local
            Calendar utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            utcCalendar.setTimeInMillis(selection);
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.set(utcCalendar.get(Calendar.YEAR), utcCalendar.get(Calendar.MONTH), utcCalendar.get(Calendar.DAY_OF_MONTH));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String selectedDate = sdf.format(localCalendar.getTime());
            tvSelectedDate.setText(selectedDate);
        });
    }
}
