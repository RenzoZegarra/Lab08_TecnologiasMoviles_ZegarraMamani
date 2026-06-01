package com.renzo.mifirebaseapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements EstudianteListener {

    EditText etNombre;
    EditText etCarrera;
    EditText etCurso;

    Button btnGuardar;

    RecyclerView rvEstudiantes;

    DatabaseReference databaseReference;

    ArrayList<Estudiante> listaEstudiantes;

    EstudianteAdapter adapter;

    private String idEditar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etCarrera = findViewById(R.id.etCarrera);
        etCurso = findViewById(R.id.etCurso);

        btnGuardar = findViewById(R.id.btnGuardar);

        rvEstudiantes =
                findViewById(R.id.rvEstudiantes);

        databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference("Estudiantes");

        listaEstudiantes =
                new ArrayList<>();

        adapter =
                new EstudianteAdapter(
                        listaEstudiantes,
                        this
                );

        rvEstudiantes.setLayoutManager(
                new LinearLayoutManager(this));

        rvEstudiantes.setAdapter(adapter);

        cargarEstudiantes();

        btnGuardar.setOnClickListener(v -> {

            String nombre =
                    etNombre.getText()
                            .toString()
                            .trim();

            String carrera =
                    etCarrera.getText()
                            .toString()
                            .trim();

            String curso =
                    etCurso.getText()
                            .toString()
                            .trim();

            if (nombre.isEmpty()
                    || carrera.isEmpty()
                    || curso.isEmpty()) {

                Toast.makeText(
                        this,
                        "Complete todos los campos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (idEditar.isEmpty()) {

                String id =
                        databaseReference
                                .push()
                                .getKey();

                Estudiante estudiante =
                        new Estudiante(
                                id,
                                nombre,
                                carrera,
                                curso
                        );

                databaseReference
                        .child(id)
                        .setValue(estudiante)
                        .addOnSuccessListener(unused -> {

                            Toast.makeText(
                                    this,
                                    "Estudiante guardado",
                                    Toast.LENGTH_SHORT
                            ).show();

                            limpiarCampos();
                        });

            } else {

                HashMap<String, Object> datos =
                        new HashMap<>();

                datos.put("nombre", nombre);
                datos.put("carrera", carrera);
                datos.put("curso", curso);

                databaseReference
                        .child(idEditar)
                        .updateChildren(datos)
                        .addOnSuccessListener(unused -> {

                            Toast.makeText(
                                    this,
                                    "Estudiante actualizado",
                                    Toast.LENGTH_SHORT
                            ).show();

                            limpiarCampos();

                            idEditar = "";

                            btnGuardar.setText(
                                    "Guardar Estudiante");
                        });
            }
        });
    }

    private void cargarEstudiantes() {

        databaseReference
                .addValueEventListener(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(
                                    DataSnapshot snapshot) {

                                listaEstudiantes.clear();

                                for (DataSnapshot data :
                                        snapshot.getChildren()) {

                                    Estudiante estudiante =
                                            data.getValue(
                                                    Estudiante.class);

                                    if (estudiante != null) {
                                        listaEstudiantes.add(
                                                estudiante);
                                    }
                                }

                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(
                                    DatabaseError error) {

                                Toast.makeText(
                                        MainActivity.this,
                                        error.getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
    }

    private void limpiarCampos() {

        etNombre.setText("");
        etCarrera.setText("");
        etCurso.setText("");
    }

    @Override
    public void onEditar(
            Estudiante estudiante) {

        idEditar = estudiante.getId();

        etNombre.setText(
                estudiante.getNombre());

        etCarrera.setText(
                estudiante.getCarrera());

        etCurso.setText(
                estudiante.getCurso());

        btnGuardar.setText(
                "Actualizar Estudiante");
    }

    @Override
    public void onEliminar(
            Estudiante estudiante) {

        databaseReference
                .child(estudiante.getId())
                .removeValue()
                .addOnSuccessListener(unused ->

                        Toast.makeText(
                                this,
                                "Estudiante eliminado",
                                Toast.LENGTH_SHORT
                        ).show()
                );
    }
}