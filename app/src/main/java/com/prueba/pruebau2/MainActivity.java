package com.prueba.pruebau2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EventoAdapter.OnItemClickListener {

    Button agregarButton;
    DatabaseReference databaseReference;
    List<Evento> eventosList;
    EventoAdapter eventoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agregarButton = findViewById(R.id.btnAgregar);
        eventosList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("eventos");

        configurarRecyclerView();

        obtenerEventosDesdeFirebase();

        agregarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(MainActivity.this, Mapa.class);
                startActivityForResult(mapIntent, 1);
            }
        });
    }

    private void configurarRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEventos);
        eventoAdapter = new EventoAdapter(this, eventosList, this);
        recyclerView.setAdapter(eventoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void obtenerEventosDesdeFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventosList.clear();

                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    Evento evento = eventSnapshot.getValue(Evento.class);
                    if (evento != null) {
                        eventosList.add(evento);
                    }
                }

                eventoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores si es necesario
            }
        });
    }

    @Override
    public void onItemClick(double latitud, double longitud) {
        // Hacer lo que necesites al hacer clic en un elemento (por ejemplo, abrir el mapa)
        abrirMapaConUbicacion(latitud, longitud);
    }

    @Override
    public void onItemLongClick(String key) {
        // Hacer lo que necesites al hacer una pulsación larga en un elemento (por ejemplo, eliminar el evento)
        eliminarEventoDeFirebase(key);
    }

    private void abrirMapaConUbicacion(double latitud, double longitud) {
        // Implementa esta función según tus necesidades para abrir el mapa con la ubicación
    }

    private void eliminarEventoDeFirebase(String key) {
        // Implementa esta función según tus necesidades para eliminar el evento de Firebase
    }
}
