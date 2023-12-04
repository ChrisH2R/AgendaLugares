package com.prueba.pruebau2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class DetallesEventoActivity extends AppCompatActivity {

    private TextInputEditText etNombreEvento, etFecha, etHora;
    private double latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_evento);

        etNombreEvento = findViewById(R.id.etNombreEvento);
        etFecha = findViewById(R.id.etFecha);
        etHora = findViewById(R.id.etHora);

        ImageButton btnBackDetalles = findViewById(R.id.btnBackDetalles);
        btnBackDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btnGuardarDetalles = findViewById(R.id.btnGuardarDetalles);
        btnGuardarDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreEvento = etNombreEvento.getText().toString();
                String fecha = etFecha.getText().toString();
                String hora = etHora.getText().toString();
                guardarEventoEnFirebase(nombreEvento, fecha, hora);
            }
        });
    }

    public void mostrarDatePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker view1, int year1, int month1, int dayOfMonth1) -> {
                    etFecha.setText(String.format(Locale.getDefault(), "%d-%02d-%02d", year1, month1 + 1, dayOfMonth1));
                },
                year,
                month,
                dayOfMonth
        );

        datePickerDialog.show();
    }

    public void mostrarTimePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (TimePicker view1, int hourOfDay1, int minute1) -> {
                    etHora.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay1, minute1));
                },
                hourOfDay,
                minute,
                android.text.format.DateFormat.is24HourFormat(this)
        );

        timePickerDialog.show();
    }

    private void guardarEventoEnFirebase(String nombreEvento, String fecha, String hora) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("eventos");
        Evento evento = new Evento(nombreEvento, latitud, longitud, fecha, hora);
        String eventoId = databaseReference.push().getKey();

        if (eventoId != null) {
            databaseReference.child(eventoId).setValue(evento);
            Toast.makeText(this, "Evento guardado con éxito", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al guardar el evento", Toast.LENGTH_SHORT).show();
        }
    }
}
