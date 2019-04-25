package com.example.laion.logintestv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class UserDBHelper extends SQLiteOpenHelper {

    public static int DATA_VERSION = 1;
    public static String DATA_BASE= "agendaescolar.db";
    Conexion conectar = new Conexion();
    Connection conn = null;

    public UserDBHelper(Context context) {
        super(context, DATA_BASE, null, DATA_VERSION);
    }


    public String getMD5(final String s) {
        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for(int i = 0; i < messageDigest.length; i++){
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while(h.length() < 2){
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        }catch (Exception e){
            e.getMessage();
        }
        return "";
    }


    @Override
    public void onCreate(SQLiteDatabase agendaescolar) {
        agendaescolar.execSQL("CREATE TABLE `usuarios` (`ID` integer PRIMARY KEY AUTOINCREMENT, `NombreUsuario` varchar(50) NOT NULL, " +
                "`Contrasena` varchar(50) NOT NULL, `Nombre` varchar(50), `Apellidos` varchar(80), `Estado` varchar(10));");
        agendaescolar.execSQL("CREATE TABLE `Horario` (`NombreUsuario`varchar(50) NOT NULL, `Materia` varchar(45) NOT NULL, "+
                "`Dia` varchar(45) NOT NULL, `HrInicio` varchar(45) NOT NULL, `HrFin` varchar(45) NOT NULL, `Lugar` varchar(45) NOT NULL, "+
                "`hrsOcupadas` varchar(45) NOT NULL)");
        agendaescolar.execSQL("INSERT INTO usuarios (ID, NombreUsuario, Contrasena, Estado) VALUES (1, '-', '-', 'offline');" );
    }

    public void newUser(String UserName, String Contrasena){
        SQLiteDatabase agendaescolar = this.getReadableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("NombreUsuario", UserName);
        valores.put("Contrasena", Contrasena);
        valores.put("Estado", "online");

        agendaescolar.update("usuarios", valores, "ID=1", null);

        agendaescolar.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void newHorario(String UserName){
        try {
            conn = conectar.conectar();
            Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Horario WHERE NombreUsuario="+UserName);
            while (rs.next()){
                SQLiteDatabase agendaescolar = this.getReadableDatabase();
                ContentValues valores = new ContentValues();
                valores.put("NombreUsuario", rs.getString("NombreUsuario"));
                valores.put("Materia", rs.getString("Materia"));
                valores.put("Dia", rs.getString("Dia"));
                valores.put("HrInicio", rs.getString("HrInicio"));
                valores.put("HrFin", rs.getString("HrFin"));
                valores.put("Lugar", rs.getString("Lugar"));
                valores.put("hrsOcupadas", rs.getString("hrsOcupadas"));
                agendaescolar.update("Horario", valores, null, null);
            }
        }catch(Exception e){
            e.getMessage();
        }
    }


    public void getHorario(String UserName){
        SQLiteDatabase agendaescolar = this.getReadableDatabase();
        String [] args = new String[] {UserName};
        Cursor c = agendaescolar.rawQuery("SELECT * FROM Horario WHERE NombreUsuario=?", args);
    }

    public boolean LogTry(String UserName, String Password){
        SQLiteDatabase agendaescolar = this.getReadableDatabase();
        String [] args = new String[] {UserName, Password};
        Cursor c = agendaescolar.rawQuery("SELECT * FROM usuarios WHERE NombreUsuario=? AND Contrasena=?", args);
        if(c !=null){
            setSession(true);
            return true;
        }else{
            return false;
        }
    }

    public void setSession(boolean state){
            SQLiteDatabase agendaescolar = this.getReadableDatabase();
            ContentValues valores = new ContentValues();

            valores.put("Estado", state);
            agendaescolar.update("usuarios", valores, "ID=1", null);

            agendaescolar.close();
    }

    public boolean getSession(){
        SQLiteDatabase agendaescolar = this.getReadableDatabase();
        String[] args = new String[] {"1"};
        Cursor c = agendaescolar.rawQuery("SELECT Estado FROM usuarios WHERE ID=?", args);
        if(c.moveToFirst()){
            String state = c.getString(0);
            if("online".equals(state)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
