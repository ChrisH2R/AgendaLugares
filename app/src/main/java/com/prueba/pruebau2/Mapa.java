package com.prueba.pruebau2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.text.DateFormat;
import java.util.Calendar;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double selectedLatitude, selectedLongitude;
    private String selectedEventName;
    private String selectedDate;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        ImageButton btnvolver = findViewById(R.id.btnBack);
        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Cerrar la actividad actual y regresar a MainActivity
            }
        });

        // Configurar el botón "Guardar Evento"
        Button btnGuardarEvento = findViewById(R.id.btnGuardarEvento);
        btnGuardarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir DetallesEventoActivity
                Intent detallesIntent = new Intent(Mapa.this, DetallesEventoActivity.class);
                startActivityForResult(detallesIntent, 1);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Mapview);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Muestra un marcador predeterminado en la ubicación seleccionada
        LatLng selectedLocation = new LatLng(selectedLatitude, selectedLongitude);
        mMap.addMarker(new MarkerOptions().position(selectedLocation).title("Ubicación Seleccionada"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 15));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Borra el marcador existente y coloca uno nuevo en la nueva ubicación
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación Seleccionada"));
                selectedLatitude = latLng.latitude;
                selectedLongitude = latLng.longitude;
            }
        });
    }

    @Override
    public void onBackPressed() {
        // No hacer nada al presionar el botón de retroceso en este punto
    }
}
