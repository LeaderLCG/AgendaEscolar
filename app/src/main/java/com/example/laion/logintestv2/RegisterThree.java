package com.example.laion.logintestv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterThree extends AppCompatActivity {

    private EditText contrasena1;
    private EditText contrasena2;

    private Button btnRegistrar;

    private UserDBHelper UDBH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_three);

        contrasena1 = findViewById(R.id.etxtContrasena1);
        contrasena2 = findViewById(R.id.etxtContrasena2);

        btnRegistrar = findViewById(R.id.registrarNuevo);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    guardarUsuario();
                }catch (Exception e){
                    Toast.makeText(RegisterThree.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });

    }



    int resContra;
    public int validarContrasena(){
        UDBH = new UserDBHelper(this);
        String contra1 = UDBH.getMD5(contrasena1.getText().toString());
        String contra2 = UDBH.getMD5(contrasena2.getText().toString());

        if (contra1.equals(contra2)){
            resContra = 1;
        }else{
            resContra = 0;
        }

        return resContra;
    }

    public void guardarUsuario(){
        try {

            if(contrasena1.getText().toString().equals("")
                    || contrasena2.getText().toString().equals("")){

                Toast.makeText(RegisterThree.this, "Por favor no dejes campos vacíos", Toast.LENGTH_LONG).show();

            }else if(validarContrasena() == 1){

                Conexion connBD = new Conexion();
                Connection conn = connBD.conectar();

                PreparedStatement instruccion = conn.prepareStatement("INSERT INTO usuarios (NombreUsuario, Contrasena," +
                        " Nombre, Apellidos, Correo) VALUES " +
                        "(?, ?, ?, ?, ?)");

                String nomUsuario, nom, apelli, cor;

                if(getIntent().hasExtra("nombreRegistro1")){

                    nom = getIntent().getStringExtra("nombreRegistro1");
                    apelli = getIntent().getStringExtra("apellidoRegistro1");
                    nomUsuario = getIntent().getStringExtra("usuarioRegistro2");
                    cor = getIntent().getStringExtra("correoRegistro2");
                    String contraAbd = UDBH.getMD5(contrasena1.getText().toString());

                    instruccion.setString(1, nomUsuario);
                    instruccion.setString(2, contraAbd);
                    instruccion.setString(3, nom);
                    instruccion.setString(4, apelli);
                    instruccion.setString(5, cor);

                    instruccion.executeUpdate();
                }else {
                    Toast.makeText(RegisterThree.this, "No se recibieron los datos", Toast.LENGTH_LONG).show();
                }

                Intent ventanaLogin = new Intent(RegisterThree.this, Login.class);
                startActivity(ventanaLogin);
            }else {
                contrasena1.setText("");
                contrasena2.setText("");
                Toast.makeText(RegisterThree.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }


        }catch (Exception e){
            Toast.makeText(RegisterThree.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
