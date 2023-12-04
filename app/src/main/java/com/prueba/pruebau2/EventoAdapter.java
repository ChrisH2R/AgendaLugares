package com.prueba.pruebau2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private Context context;
    private List<Evento> eventosList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(double latitud, double longitud);
        void onItemLongClick(String key);
    }

    public EventoAdapter(Context context, List<Evento> eventosList, OnItemClickListener listener) {
        this.context = context;
        this.eventosList = eventosList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = eventosList.get(position);

        holder.tvNombreEvento.setText(evento.getNombre());
        holder.tvFecha.setText("Fecha: " + evento.getFecha());
        holder.tvHora.setText("Hora: " + evento.getHora());
        holder.tvLatitud.setText("Latitud: " + String.valueOf(evento.getLatitud()));
        holder.tvLongitud.setText("Longitud: " + String.valueOf(evento.getLongitud()));
    }

    @Override
    public int getItemCount() {
        return eventosList.size();
    }

    class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreEvento, tvFecha, tvHora, tvLatitud, tvLongitud;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreEvento = itemView.findViewById(R.id.tvNombreEvento);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvHora = itemView.findViewById(R.id.tvHora);
            tvLatitud = itemView.findViewById(R.id.tvLatitud);
            tvLongitud = itemView.findViewById(R.id.tvLongitud);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Evento evento = eventosList.get(position);
                        listener.onItemClick(evento.getLatitud(), evento.getLongitud());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Evento evento = eventosList.get(position);
                        listener.onItemLongClick(evento.getKey());
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}
