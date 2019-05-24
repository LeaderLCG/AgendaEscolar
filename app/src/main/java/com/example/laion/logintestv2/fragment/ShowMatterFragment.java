package com.example.laion.logintestv2.fragment;

import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.laion.logintestv2.Conexion;
import com.example.laion.logintestv2.R;
import com.example.laion.logintestv2.UserDBHelper;
import com.example.laion.logintestv2.adapter.ScheduleAdapter;
import com.example.laion.logintestv2.pojo.Horario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShowMatterFragment extends Fragment {

    private ArrayList<Horario> lsHorario;
    private RecyclerView rvHorarios;

    private Conexion conn;
    private Statement st;
    private ResultSet rs;
    private UserDBHelper UDB;

    public ScheduleAdapter adapterSche;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        try {
            //return super.onCreateView(inflater, container, savedInstanceState);
            rvHorarios = v.findViewById(R.id.rvHorario);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            //GridLayoutManager glm = new GridLayoutManager(this, 2);

            //getActivity().setTitle("Materias registradas");

            conn = new Conexion();
            UDB = new UserDBHelper(getActivity());
            Connection cnt = conn.conectar();
            lsHorario = new ArrayList<>();

            rvHorarios.setLayoutManager(llm);

            adapterSche = new ScheduleAdapter(lsHorario, getActivity());
            rvHorarios.setAdapter(adapterSche);


            st = cnt.createStatement();
            rs = st.executeQuery("SELECT Materia, Dia, HrInicio, HrFin, Lugar FROM Horario WHERE NombreUsuario='" + UDB.getSession2() + "'");

            if (!rs.wasNull()){
                while (rs.next()) {
                    lsHorario.add(new Horario(rs.getString("Materia") + "   -",rs.getString("Dia"),
                            rs.getString("HrInicio")+"hrs       a  ", rs.getString("HrFin")+"hrs",
                            "Salon: "+rs.getString("Lugar"),R.drawable.icons8_book_stack_48));
                }
            }else{
                Toast.makeText(getActivity(), "No se pudo obtener los datos", Toast.LENGTH_LONG).show();
            }

            //Esta dos lineas, eran para hacer lo mismo que hace la consulta a la base de datos remota
            String[][] recuperarHoario;
            recuperarHoario = UDB.getHorarioDia();


        }catch (Exception e)  {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
        return v;
    }


}
