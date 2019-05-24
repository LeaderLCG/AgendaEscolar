package com.example.laion.logintestv2.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.laion.logintestv2.Conexion;
import com.example.laion.logintestv2.R;
import com.example.laion.logintestv2.User;
import com.example.laion.logintestv2.UserDBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterMatterFragment extends  Fragment implements AdapterView.OnItemSelectedListener {

    private EditText etxtMateria;
    private EditText etxtAula;

    private Spinner spDias;
    private Spinner spHrIni;
    private Spinner spHrFin;

    private Button btnAddMatter;
    private Button btnUpdateMatter;
    private Button btnDeleteMatter;

    private Conexion conex;
    private UserDBHelper UDB;

    private Connection conn;
    private Statement st;
    private ResultSet rs;

    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;
    private boolean flag5;

    private View v;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        v = inflater.inflate(R.layout.fragment_register_matter, container, false);

        etxtMateria =  v.findViewById(R.id.etxtMateria);
        spDias =  v.findViewById(R.id.spDias);
        spHrIni = v.findViewById(R.id.spHini);
        spHrFin = v.findViewById(R.id.spHfin);
        etxtAula = v.findViewById(R.id.etxtAula);

        //getActivity().setTitle("CRUDE de materias");

        btnAddMatter = v.findViewById(R.id.btnAddMatter);
        btnUpdateMatter = v.findViewById(R.id.btnUpdateMatter);
        btnDeleteMatter = v.findViewById(R.id.btnDeleteMatter);

        spHrIni.setOnItemSelectedListener(this);
        spHrFin.setOnItemSelectedListener(this);

        btnAddMatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(etxtMateria.getText().toString().equals("") || spDias.getSelectedItem().toString().equals("Seleccionar") || spHrIni.getSelectedItem().toString().equals("Seleccionar")
                    || spHrFin.getSelectedItem().toString().equals("Seleccionar") || etxtAula.getText().toString().equals("")){
                        Toast.makeText(getContext(), "Por favor no dejes campos vacios",Toast.LENGTH_LONG).show();
                    }else if(validarMateria()==true){
                        Toast.makeText(getContext(), "La EE ya existe en el día que digitáste",Toast.LENGTH_LONG).show();
                    }else if(validarHoras()==true){
                        Toast.makeText(getContext(), "Horas de inicio y fin de EE no validas", Toast.LENGTH_LONG).show();
                    }else if(validarMismaHora()==true){
                        Toast.makeText(getContext(), "La EE esta translapando con otra EE en sus horas", Toast.LENGTH_LONG).show();
                    }else if(validarMismaHora2()==true){
                        Toast.makeText(getContext(), "Ya hay una EE en medio de las horas registradas", Toast.LENGTH_LONG).show();
                    }else if(validarCantidadHoras()==true){
                        Toast.makeText(getContext(), "Una EE no puede durar mas de 5 horas", Toast.LENGTH_LONG).show();
                    }else{
                        UDB = new UserDBHelper(v.getContext());
                        conex = new Conexion();
                        conn = conex.conectar();
                        st = conn.createStatement();

                        String hrsOcupadas = contarHrsOcupadas();

                        st.executeUpdate("INSERT INTO Horario(NombreUsuario, Materia, Dia, HrInicio, HrFin, Lugar, hrsOcupadas) VALUES" +
                                "('" + UDB.getSession2() + "','" + etxtMateria.getText().toString() + "', '" + spDias.getSelectedItem().toString() + "'," +
                                "'" + spHrIni.getSelectedItem().toString() + "', '" + spHrFin.getSelectedItem().toString() + "', '" + etxtAula.getText().toString() + "', '"
                                + hrsOcupadas + "');");
                        UDB.newHorario(UDB.getSession2());
                        conn.close();

                        Toast.makeText(v.getContext(), "¡Experiencia educativa registrada con exito!",Toast.LENGTH_LONG).show();

                        Intent regresarUser = new Intent(v.getContext(), User.class);
                        startActivity(regresarUser);
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        btnUpdateMatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(etxtMateria.getText().toString().equals("") || spDias.getSelectedItem().toString().equals("") || spHrIni.getSelectedItem().toString().equals("Seleccionar")
                            || spHrFin.getSelectedItem().toString().equals("Seleccionar") || etxtAula.getText().toString().equals("")){
                        Toast.makeText(getContext(), "Por favor no dejes campos vacios",Toast.LENGTH_LONG).show();
                    }else if(validarHoras()==true){
                        Toast.makeText(getContext(), "Horas de inicio y fin de EE no validas", Toast.LENGTH_LONG).show();
                    }else if(validarMismaHora2()==true){
                        Toast.makeText(getContext(), "Ya hay una EE en medio de las horas registradas", Toast.LENGTH_LONG).show();
                    }else if(validarCantidadHoras()==true){
                        Toast.makeText(getContext(), "Una EE no puede durar mas de 5 horas", Toast.LENGTH_LONG).show();
                    }else{
                        UDB = new UserDBHelper(v.getContext());
                        conex = new Conexion();
                        conn = conex.conectar();
                        st = conn.createStatement();

                        String hrsOcupadas = contarHrsOcupadas();

                        st.executeUpdate("UPDATE Horario SET HrInicio= '"+ spHrIni.getSelectedItem().toString() +"', HrFin= '"+
                                spHrFin.getSelectedItem().toString() +"', " +
                                "Lugar= '"+ etxtAula.getText().toString() +"', hrsOcupadas= '" + hrsOcupadas + "'" +
                                " WHERE NombreUsuario='" + UDB.getSession2()+ "' AND Materia= '"+ etxtMateria.getText().toString() + "' " +
                                "AND Dia= '"+ spDias.getSelectedItem().toString() +"';");

                        UDB.newHorario(UDB.getSession2());
                        Toast.makeText(v.getContext(), "¡Experiencia educativa actualizada con exito!",Toast.LENGTH_LONG).show();

                        Intent regresarUser = new Intent(v.getContext(), User.class);
                        startActivity(regresarUser);
                        conn.close();
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDeleteMatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                    if(etxtMateria.getText().toString().equals("") || spDias.getSelectedItem().toString().equals("")){
                        Toast.makeText(getContext(), "Por favor no dejes campos vacios",Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(), "Para eliminar la EE solo necesitas:",Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(), "El nombre de la EE y el dia.",Toast.LENGTH_LONG).show();
                    }else{
                        new AlertDialog.Builder(v.getContext())
                                .setMessage("¿Estas seguro de querer eliminar la EE?")
                                .setCancelable(false)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            UDB = new UserDBHelper(v.getContext());
                                            conex = new Conexion();
                                            conn = conex.conectar();
                                            st = conn.createStatement();

                                            st.executeUpdate("DELETE FROM Horario WHERE NombreUsuario='" +
                                                    UDB.getSession2() + "' AND Materia= '" + etxtMateria.getText().toString() +
                                                    "' AND Dia='" + spDias.getSelectedItem().toString() + "';");
                                            //DELETE FROM Horario Where Dia = "Miercoles" AND NombreUsuario="John" AND Materia="Base de datos avanzadas";
                                            UDB.newHorario(UDB.getSession2());


                                            Toast.makeText(v.getContext(), "¡Experiencia educativa eliminada con exito!", Toast.LENGTH_LONG).show();

                                            Intent regresarUser = new Intent(v.getContext(), User.class);
                                            startActivity(regresarUser);
                                        }catch (Exception e){
                                            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
            }
        });



        return v;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public boolean validarMismaHora2(){
        try {
            UDB = new UserDBHelper(v.getContext());
            conex = new Conexion();
            conn = conex.conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT Dia, HrInicio, HrFin FROM Horario WHERE NombreUsuario='"  +
                    UDB.getSession2() +"';");

            rs.next();
            int hri = Integer.valueOf(spHrIni.getSelectedItem().toString());
            int hrf = Integer.valueOf(spHrFin.getSelectedItem().toString());
            hri += 1;
            hrf -= 1;
            String shri = String.valueOf(hri);
            String shrf = String.valueOf(hrf);
            while(rs.next()){
                if((spDias.getSelectedItem().toString().equals(rs.getString("Dia")) &&
                        shri.equals(rs.getString("HrInicio"))) ||
                        (spDias.getSelectedItem().toString().equals(rs.getString("Dia")) &&
                                shrf.equals(rs.getString("HrFin")))) {
                    flag5 = true;
                    break;
                } else {
                    flag5 = false;
                }
            }
            conn.close();
        }catch (Exception e) {
            Toast.makeText(v.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return flag5;
    }

    public boolean validarCantidadHoras(){
        int hri = Integer.valueOf(spHrIni.getSelectedItem().toString());
        int hrf = Integer.valueOf(spHrFin.getSelectedItem().toString());
        if((hrf-hri)>=6){
            flag4=true;
        }else {
            flag4=false;
        }
        return flag4;
    }

    public String contarHrsOcupadas(){
        String hrsTotales = spHrIni.getSelectedItem().toString();
        int hri = Integer.valueOf(spHrIni.getSelectedItem().toString());
        int hrf = Integer.valueOf(spHrFin.getSelectedItem().toString());
        int total = hrf - hri;
        String convertir;

        for(int i=0; i<total-1; i++){
            convertir = String.valueOf(hri + 1);
            hrsTotales += " " + convertir;
            hri = Integer.valueOf(convertir);
        }
        return hrsTotales;
    }

    public boolean validarMismaHora(){
        try {
            UDB = new UserDBHelper(v.getContext());
            conex = new Conexion();
            conn = conex.conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT Dia, HrInicio, HrFin FROM Horario WHERE NombreUsuario='"  +
                    UDB.getSession2() +"';");

            //rs.next();
            while(rs.next()){
                if((spDias.getSelectedItem().toString().equals(rs.getString("Dia")) &&
                 spHrIni.getSelectedItem().toString().equals(rs.getString("HrInicio"))) ||
                        (spDias.getSelectedItem().toString().equals(rs.getString("Dia")) &&
                            spHrFin.getSelectedItem().toString().equals(rs.getString("HrFin"))))  {
                    flag3 = true;
                    break;
                } else {
                    flag3 = false;
                }
            }
        conn.close();
        }catch (Exception e) {
            Toast.makeText(v.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return flag3;
    }

    public boolean validarHoras(){
        int hrIni, hrFin;
        hrIni = Integer.valueOf(spHrIni.getSelectedItem().toString());
        hrFin = Integer.valueOf(spHrFin.getSelectedItem().toString());
        if((hrFin-hrIni)<1) {
            flag2=true;
        }else{
            flag2=false;
        }
        return flag2;
    }

    public boolean validarMateria(){
        try {
            //UDB = new UserDBHelper(v.getContext());
            conex = new Conexion();
            conn = conex.conectar();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM Horario WHERE Materia='"+ etxtMateria.getText().toString() +
                    "'" + " AND Dia='"+ spDias.getSelectedItem().toString()+ "';");
            rs.next();
            if(rs.next()){
                flag1=true;
            }else {
                flag1=false;
            }
            conn.close();
        }catch (Exception e) {
            Toast.makeText(v.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return flag1;
    }
}
