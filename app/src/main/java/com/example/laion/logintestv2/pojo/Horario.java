package com.example.laion.logintestv2.pojo;

public class Horario {
    private String materia;
    private String dia;
    private String hInicio;
    private String hFin;
    private String salon;
    private int imgEE;

    public Horario(String materia, String dia, String hInicio, String hFin, String salon, int imgEE) {
        this.materia = materia;
        this.dia = dia;
        this.hInicio = hInicio;
        this.hFin = hFin;
        this.salon = salon;
        this.imgEE = imgEE;
    }

    public Horario(String materia, String dia, String hInicio, String hFin, String salon) {
        this.materia = materia;
        this.dia = dia;
        this.hInicio = hInicio;
        this.hFin = hFin;
        this.salon = salon;
    }

    public Horario(String materia, int imgEE) {
        this.materia = materia;
        this.imgEE = imgEE;
    }

    public int getImgEE() {
        return imgEE;
    }

    public void setImgEE(int imgEE) {
        this.imgEE = imgEE;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String gethInicio() {
        return hInicio;
    }

    public void sethInicio(String hInicio) {
        this.hInicio = hInicio;
    }

    public String gethFin() {
        return hFin;
    }

    public void sethFin(String hFin) {
        this.hFin = hFin;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }
}
