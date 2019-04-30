package com.example.laion.logintestv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

public class RegisterTwo extends AppCompatActivity {

    private EditText nombreUsuario;
    private EditText correo;

    private Button btnSiguiente2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_two);

        nombreUsuario = findViewById(R.id.etxtNombreUsuario);
        correo = findViewById(R.id.etxtCorreo);

        btnSiguiente2 = findViewById(R.id.btnSiguiente2);

        nombreUsuario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validarUsuario();
            }
        });

        btnSiguiente2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarUsuarioCorreo();
            }
        });


    }

    public void pasarUsuarioCorreo(){
        try {

            if(nombreUsuario.getText().toString().equals("") || correo.getText().toString().equals("")){

                Toast.makeText(RegisterTwo.this, "Por favor no dejes campos vacíos", Toast.LENGTH_LONG).show();

            }else if(validarCorreo() == true){



                Intent ventanaRegistroTres = new Intent(RegisterTwo.this, RegisterThree.class);

                Bundle recuperarRegistro1;
                recuperarRegistro1 = getIntent().getExtras();

                String nombreRegistro1 = recuperarRegistro1.getString("nombreRegistro1");
                String apellidoRegistro1 = recuperarRegistro1.getString("apellidoRegistro1");
                String usuario = nombreUsuario.getText().toString();
                String correoBueno = correo.getText().toString();

                ventanaRegistroTres.putExtra("nombreRegistro1", nombreRegistro1);
                ventanaRegistroTres.putExtra("apellidoRegistro1", apellidoRegistro1);
                ventanaRegistroTres.putExtra("usuarioRegistro2", usuario);
                ventanaRegistroTres.putExtra("correoRegistro2", correoBueno);
                startActivity(ventanaRegistroTres);
            }else {
                Toast.makeText(RegisterTwo.this, "Algo se encuentra mal", Toast.LENGTH_LONG);
            }


        }catch (Exception e){
            Toast.makeText(RegisterTwo.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void validarUsuario(){
        try {
            Conexion conBDusuario = new Conexion();
            Connection conn = conBDusuario.conectar();

            Statement declaracion;
            declaracion = conn.createStatement();

            ResultSet obtenerResultados;

            String nombreUsuarioValidar = nombreUsuario.getText().toString();
            String nombreUsuarioValidos;

            obtenerResultados = declaracion.executeQuery("SELECT NombreUsuario FROM usuarios");
            while (obtenerResultados.next()){
                nombreUsuarioValidos = obtenerResultados.getString("NombreUsuario");
                if (nombreUsuarioValidar.equals(nombreUsuarioValidos)){
                    nombreUsuario.setError("Ya existe ese usuario");
                }
            }

        }catch (Exception e){
            Toast.makeText(RegisterTwo.this, e.getMessage(), Toast.LENGTH_LONG);
        }

    }

    boolean valiCorreo = true;
    public boolean validarCorreo(){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        //String email = correo.getText().toString();
        if(pattern.matcher(correo.getText().toString()).matches() == false) {
            correo.setError("Correo no valido");
            valiCorreo = false;
        }else{
            valiCorreo = true;
        }
        return valiCorreo;
    }
}
