package com.example.laion.logintestv2.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laion.logintestv2.R;
import com.example.laion.logintestv2.pojo.Horario;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.HorarioViewHolder>{

    ArrayList<Horario> horarios;
    Activity activity;
    public ScheduleAdapter(ArrayList<Horario> horarios, Activity activity){
        this.horarios = horarios;
        this.activity = activity;
    }

    //Va a inflar el layout y lo pasara al ViewHolder para que el obtenga cada elemento (viewa)
    @NonNull
    @Override
    public HorarioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Esta linea de codigo es la encargada de asociar nuestro recycler view con el cardview
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_horario, viewGroup, false);
        return new HorarioViewHolder(v);
    }

   //asocia cada elemnto de la lista a cada view
    @Override
    public void onBindViewHolder(@NonNull HorarioViewHolder horarioViewHolder, int position) {
        final Horario horario = horarios.get(position);
        horarioViewHolder.imgEE.setImageResource(horario.getImgEE());
        horarioViewHolder.tvNameEE.setText(horario.getMateria());
        horarioViewHolder.tvDiaEE.setText(horario.getDia());
        horarioViewHolder.tvHrIEE.setText(horario.gethInicio());
        horarioViewHolder.tvHrFEE.setText(horario.gethFin());
        horarioViewHolder.tvSalonEE.setText(horario.getSalon());
    }

    @Override
    public int getItemCount() { //cantidad de elementos que contiene la lista de facultades
        return horarios.size();
    }

    public static class HorarioViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgEE;
        private TextView tvNameEE;
        private TextView tvDiaEE;
        private TextView tvHrIEE;
        private TextView tvHrFEE;
        private TextView tvSalonEE;

        public HorarioViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEE = itemView.findViewById(R.id.imgEE);
            tvNameEE = itemView.findViewById(R.id.tvNameEE);
            tvDiaEE = itemView.findViewById(R.id.tvDiaEE);
            tvHrIEE = itemView.findViewById(R.id.tvHrIEE);
            tvHrFEE = itemView.findViewById(R.id.tvHrFEE);
            tvSalonEE = itemView.findViewById(R.id.tvSalonEE);
        }
    }
}
