package com.example.laion.logintestv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemClassAdapter extends BaseAdapter {

    public static LayoutInflater inflater=null;

    Context contexto;
    String[][] horario;

    public ItemClassAdapter(Context contexto, String horario[][]){
        this.contexto=contexto;
        this.horario=horario;

        inflater = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.class_layout, null);

        TextView EE = (TextView)vista.findViewById(R.id.EE);
        TextView EEPlace = (TextView)vista.findViewById(R.id.EEPlace);
        TextView EETime = (TextView)vista.findViewById(R.id.EETime);

        EE.setText(horario[0][i]);
        EEPlace.setText(horario [1][i]);
        EETime.setText(horario [2][i]);

        return vista;
    }

    @Override
    public int getCount() {
        return horario.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
