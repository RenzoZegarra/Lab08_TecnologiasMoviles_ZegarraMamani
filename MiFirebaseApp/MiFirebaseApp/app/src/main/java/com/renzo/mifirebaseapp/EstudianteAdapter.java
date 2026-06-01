package com.renzo.mifirebaseapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EstudianteAdapter
        extends RecyclerView.Adapter<EstudianteAdapter.ViewHolder> {

    private List<Estudiante> listaEstudiantes;
    private EstudianteListener listener;

    public EstudianteAdapter(
            List<Estudiante> listaEstudiantes,
            EstudianteListener listener) {

        this.listaEstudiantes = listaEstudiantes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_estudiante,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Estudiante estudiante =
                listaEstudiantes.get(position);

        holder.tvNombre.setText(
                estudiante.getNombre());

        holder.tvCarrera.setText(
                "Carrera: " +
                        estudiante.getCarrera());

        holder.tvCurso.setText(
                "Curso: " +
                        estudiante.getCurso());

        holder.btnEditar.setOnClickListener(v ->
                listener.onEditar(estudiante));

        holder.btnEliminar.setOnClickListener(v ->
                listener.onEliminar(estudiante));
    }

    @Override
    public int getItemCount() {
        return listaEstudiantes.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvNombre;
        TextView tvCarrera;
        TextView tvCurso;

        Button btnEditar;
        Button btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre =
                    itemView.findViewById(R.id.tvNombre);

            tvCarrera =
                    itemView.findViewById(R.id.tvCarrera);

            tvCurso =
                    itemView.findViewById(R.id.tvCurso);

            btnEditar =
                    itemView.findViewById(R.id.btnEditar);

            btnEliminar =
                    itemView.findViewById(R.id.btnEliminar);
        }
    }
}