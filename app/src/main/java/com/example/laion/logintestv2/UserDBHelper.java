package com.example.laion.logintestv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        agendaescolar.execSQL("CREATE TABLE `Horario` (\n" +
                "  `NombreUsuario` varchar(50) NOT NULL,\n" +
                "  `Materia` varchar(45) NOT NULL,\n" +
                "  `Dia` varchar(45) NOT NULL,\n" +
                "  `HrInicio` varchar(45) NOT NULL,\n" +
                "  `HrFin` varchar(45) NOT NULL,\n" +
                "  `Lugar` varchar(45) NOT NULL,\n" +
                "  `hrsOcupadas` varchar(45) NOT NULL\n" +
                ");");
        agendaescolar.execSQL("CREATE TABLE `usuarios` (\n" +
                "  `ID` integer PRIMARY KEY AUTOINCREMENT,\n" +
                "  `NombreUsuario` varchar(50) NOT NULL,\n" +
                "  `Contrasena` varchar(50) NOT NULL,\n" +
                "  `Nombre` varchar(50),\n" +
                "  `Apellidos` varchar(80),\n" +
                "  `NumeroTelefonico` varchar (15), \n"+
                "  `CorreoElectronico` varchar (50), \n"+
                "  `Carrera` varchar (50), \n"+
                "  `Institucion` varchar (70), \n"+
                "  `Estado` varchar(10)\n" +
                ");");
        agendaescolar.execSQL("INSERT INTO usuarios (ID, NombreUsuario, Contrasena, Nombre, Apellidos, " +
                "NumeroTelefonico, CorreoElectronico, Carrera, Institucion, Estado) " +
                "VALUES (1, '-', '-', '-', '-', '-', '-', '-', '-', 'offline');" );
        agendaescolar.execSQL("INSERT INTO Horario (NombreUsuario, Materia, Dia, HrInicio, HrFin, Lugar, hrsOcupadas) VALUES ('-', '-', '-', '-', '-', '-', '-');");
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

    public void setPersonalInfo(String PersonalInfo[]){
        SQLiteDatabase agendaescolar = this.getReadableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("Nombre", PersonalInfo[0]);
        valores.put("Apellidos", PersonalInfo[1]);
        valores.put("NumeroTelefonico", PersonalInfo[2]);
        valores.put("CorreoElectronico", PersonalInfo[3]);
        valores.put("Carrera", PersonalInfo[4]);
        valores.put("Institucion", PersonalInfo[5]);

        agendaescolar.update("usuarios", valores, "ID=1", null);

        agendaescolar.close();
    }

    public String [] getPersonalInfo(){
        String [] PersonalInformation = new String [8];
        SQLiteDatabase agendaescolar = this.getReadableDatabase();
        Cursor c = agendaescolar.rawQuery("SELECT * FROM usuarios", null);
        c.moveToFirst();
        for(int i=1; i<9; i++){
            PersonalInformation[i-1]=c.getString(i);
        }

        return PersonalInformation;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void newHorario(String UserName){
        try {
            conn = conectar.conectar();
            Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Horario WHERE NombreUsuario='"+UserName+"'");
            SQLiteDatabase agendaescolar = this.getReadableDatabase();
            agendaescolar.execSQL("DELETE FROM Horario;");
            rs.last();
            int a = rs.getRow();
            rs.first();
            for (int i =0; i<a; i++){
                ContentValues valores = new ContentValues();
                valores.put("NombreUsuario", rs.getString("NombreUsuario"));
                valores.put("Materia", rs.getString("Materia"));
                valores.put("Dia", rs.getString("Dia"));
                valores.put("HrInicio", rs.getString("HrInicio"));
                valores.put("HrFin", rs.getString("HrFin"));
                valores.put("Lugar", rs.getString("Lugar"));
                valores.put("hrsOcupadas", rs.getString("hrsOcupadas"));
                agendaescolar.insert("Horario", null, valores);
                rs.next();
            }
            agendaescolar.close();
        }catch(Exception e){
            e.getMessage();
        }
    }

    /*public String TestHorario(String UserName) {
        try {
            conn = conectar.conectar();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Horario WHERE NombreUsuario='" + UserName + "'");
            SQLiteDatabase agendaescolar = this.getReadableDatabase();
            rs.last();
            int a = rs.getRow();
            rs.first();
            for (int i =0; i<a; i++){
                ContentValues valores = new ContentValues();
                valores.put("NombreUsuario", rs.getString("NombreUsuario"));
                valores.put("Materia", rs.getString("Materia"));
                valores.put("Dia", rs.getString("Dia"));
                valores.put("HrInicio", rs.getString("HrInicio"));
                valores.put("HrFin", rs.getString("HrFin"));
                valores.put("Lugar", rs.getString("Lugar"));
                valores.put("hrsOcupadas", rs.getString("hrsOcupadas"));
                agendaescolar.update("Horario", valores, null, null);
                rs.next();
            }
            Cursor c = agendaescolar.rawQuery("SELECT * FROM Horario", null);
            c.moveToFirst();
            return String.valueOf(c.getColumnNames());
            //}
        } catch (Exception e) {
            return e.getMessage();
        }
    }*/

    public int testGetCursor(){
        SQLiteDatabase agendaescolar = this.getReadableDatabase();
        Cursor c = agendaescolar.rawQuery("SELECT * FROM Horario WHERE Dia='Lunes'", null);
        //String[][] Horario = new String[3][c.getCount()]; // = new String[c.getCount()][3];
        //c.moveToFirst();
        return c.getCount();
    }


    public String[][] getHorarioDia(String Dia){
        SQLiteDatabase agendaescolar = this.getReadableDatabase();
        String [] args = new String[] {Dia};
        Cursor c = agendaescolar.rawQuery("SELECT * FROM Horario WHERE Dia=? ORDER BY HrInicio", args);
        String[][] Horario = new String[c.getCount()][3]; // = new String[c.getCount()][3];
        c.moveToFirst();
        for(int i=0; i<c.getCount(); i++){
            Horario[i][0]=c.getString(1);
            Horario[i][1]=c.getString(3)+":00 - "+c.getString(4)+":00";
            Horario[i][2]=c.getString(5);
            c.moveToNext();
        }
        return Horario;
    }

    public boolean LogTry(String UserName, String Password){
        SQLiteDatabase agendaescolar = this.getReadableDatabase();
        String [] args = new String[] {UserName, Password};
        Cursor c = agendaescolar.rawQuery("SELECT * FROM usuarios WHERE NombreUsuario=? AND Contrasena=?", args);
        if(c.getCount()!=0){
            setSession(true);
            return true;
        }else{
            return false;
        }
    }

    /*public String TESTOP(String UserName, String Password){
        SQLiteDatabase agendaescolar = this.getReadableDatabase();
        String [] args = new String[] {UserName, Password};
        Cursor c = agendaescolar.rawQuery("SELECT * FROM usuarios WHERE NombreUsuario=? AND Contrasena=?", args);
        if(c !=null){
            return String.valueOf(c.getCount());
        }else{
            return "nope";
        }
    }*/

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

    public boolean refreshData(String[] personalData) {
        try {
            personalData[1]=getMD5(personalData[1]);
            conn = conectar.conectar();
            PreparedStatement stm = conn.prepareStatement("UPDATE usuarios SET NombreUsuario=?, " +
                    "Contrasena=?, Nombre=?, Apellidos=?, Telefono=?, Correo=?, Carrera=?, Institucion=?");
            for(int i=0; i<8; i++){
                stm.setString(i+1, personalData[i]);
            }
            stm.executeUpdate();
            SQLiteDatabase agendaescolar = this.getReadableDatabase();
            ContentValues valores = new ContentValues();

            valores.put("NombreUsuario", personalData[0]);
            valores.put("Contrasena", personalData[1]);
            valores.put("Nombre", personalData[2]);
            valores.put("Apellidos", personalData[3]);
            valores.put("NumeroTelefonico", personalData[4]);
            valores.put("CorreoElectronico", personalData[5]);
            valores.put("Carrera", personalData[6]);
            valores.put("Institucion", personalData[7]);
            agendaescolar.update("usuarios", valores, "ID=1", null);
            agendaescolar.close();

            return true;
        }catch(Exception e){
            return false;
        }
    }
}
