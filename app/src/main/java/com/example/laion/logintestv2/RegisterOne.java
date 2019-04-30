package com.example.laion.logintestv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterOne extends AppCompatActivity {

    private EditText nombre;
    private EditText apellidos;

    private Button btnSiguiente1;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_one);

        nombre = findViewById(R.id.etxtNombre);
        apellidos = findViewById(R.id.etxtApellidos);

        btnSiguiente1 = findViewById(R.id.btnSiguiente1);

        btnSiguiente1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarNombreApellidos();
            }
        });
    }

    public void pasarNombreApellidos(){
        if (nombre.getText().toString().equals("")
                || apellidos.getText().toString().equals("")){
            Toast.makeText(RegisterOne.this, "No dejes campos vacios", Toast.LENGTH_LONG).show();
        }else{

            Intent ventanaRegistro2 = new Intent(RegisterOne.this, RegisterTwo.class);
            String nombreBueno = nombre.getText().toString();
            String apellidosBueno = apellidos.getText().toString();
            ventanaRegistro2.putExtra("nombreRegistro1", nombreBueno);
            ventanaRegistro2.putExtra("apellidoRegistro1", apellidosBueno);
            startActivity(ventanaRegistro2);
        }
    }
}