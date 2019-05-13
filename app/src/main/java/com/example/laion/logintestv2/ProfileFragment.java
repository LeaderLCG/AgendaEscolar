package com.example.laion.logintestv2;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText NombreInput;
    EditText ApellidoInput;
    EditText UserNameInput;
    EditText ContrasenaInput;
    EditText TelefonoInput;
    EditText CorreoInput;
    EditText CarreraInput;
    EditText InstitucionInput;
    Switch switchhorario;

    public static String PersonalData[];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *
     */

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public  void onActivityCreated(Bundle state) {

        super.onActivityCreated(state);
        NombreInput = (EditText) getView().findViewById(R.id.NombreInput);
        ApellidoInput = (EditText) getView().findViewById(R.id.ApellidoInput);
        UserNameInput = (EditText) getView().findViewById(R.id.UserNameInput);
        ContrasenaInput = (EditText) getView().findViewById(R.id.ContrasenaInput);
        TelefonoInput = (EditText) getView().findViewById(R.id.TelefonoInput);
        CorreoInput = (EditText) getView().findViewById(R.id.CorreoInput);
        CarreraInput = (EditText) getView().findViewById(R.id.CarreraInput);
        InstitucionInput = (EditText) getView().findViewById(R.id.InstitucionInput);
        switchhorario = (Switch) getView().findViewById(R.id.switch1);

        UserDBHelper UDB = new UserDBHelper(this.getActivity().getApplicationContext());
        PersonalData=UDB.getPersonalInfo();


        NombreInput.setText(PersonalData[2]);
        ApellidoInput.setText(PersonalData[3]);
        UserNameInput.setText(PersonalData[0]);
        TelefonoInput.setText(PersonalData[5]);
        CorreoInput.setText(PersonalData[4]);
        CarreraInput.setText(PersonalData[6]);
        InstitucionInput.setText(PersonalData[7]);

        NombreInput.setEnabled(false);
        ApellidoInput.setEnabled(false);
        UserNameInput.setEnabled(false);
        ContrasenaInput.setEnabled(false);
        TelefonoInput.setEnabled(false);
        CorreoInput.setEnabled(false);
        CarreraInput.setEnabled(false);
        InstitucionInput.setEnabled(false);

        NombreInput.setBackground(null);
        ApellidoInput.setBackground(null);
        UserNameInput.setBackground(null);
        ContrasenaInput.setBackground(null);
        TelefonoInput.setBackground(null);
        CorreoInput.setBackground(null);
        CarreraInput.setBackground(null);


        /*ModificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NombreInput.setEnabled(true);
                ApellidoInput.setEnabled(true);
                UserNameInput.setEnabled(true);
                TelefonoInput.setEnabled(true);
                CorreoInput.setEnabled(true);
                CarreraInput.setEnabled(true);
                InstitucionInput.setEnabled(true);
            }
        });

        CancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NombreInput.setText(PersonalData[2]);
                ApellidoInput.setText(PersonalData[3]);
                UserNameInput.setText(PersonalData[0]);
                TelefonoInput.setText(PersonalData[4]);
                CorreoInput.setText(PersonalData[5]);
                CarreraInput.setText(PersonalData[6]);
                InstitucionInput.setText(PersonalData[7]);


                NombreInput.setEnabled(false);
                ApellidoInput.setEnabled(false);
                UserNameInput.setEnabled(false);
                TelefonoInput.setEnabled(false);
                CorreoInput.setEnabled(false);
                CarreraInput.setEnabled(false);
                InstitucionInput.setEnabled(false);
            }
        });*/

    }


    public void modify(){
        NombreInput.setEnabled(true);
        ApellidoInput.setEnabled(true);
        UserNameInput.setEnabled(true);
        ContrasenaInput.setEnabled(true);
        TelefonoInput.setEnabled(true);
        CorreoInput.setEnabled(true);
        CarreraInput.setEnabled(true);
        InstitucionInput.setEnabled(true);
    }

    public void cancel(){
        NombreInput.setText(PersonalData[2]);
        ApellidoInput.setText(PersonalData[3]);
        UserNameInput.setText(PersonalData[0]);
        TelefonoInput.setText(PersonalData[5]);
        CorreoInput.setText(PersonalData[4]);
        CarreraInput.setText(PersonalData[6]);
        InstitucionInput.setText(PersonalData[7]);


        NombreInput.setEnabled(false);
        ApellidoInput.setEnabled(false);
        UserNameInput.setEnabled(false);
        ContrasenaInput.setEnabled(false);
        TelefonoInput.setEnabled(false);
        CorreoInput.setEnabled(false);
        CarreraInput.setEnabled(false);
        InstitucionInput.setEnabled(false);
    }

    public void save() throws Exception {

        if(!"".equals(ContrasenaInput.getText())){
            PersonalData[1]= String.valueOf(ContrasenaInput.getText());
        }


        UserDBHelper UDB = new UserDBHelper(this.getActivity().getApplicationContext());
        PersonalData[0]= String.valueOf(UserNameInput.getText());
        PersonalData[2]= String.valueOf(NombreInput.getText());
        PersonalData[3]= String.valueOf(ApellidoInput.getText());
        PersonalData[4]= String.valueOf(TelefonoInput.getText());
        PersonalData[5]= String.valueOf(CorreoInput.getText());
        PersonalData[6]= String.valueOf(CarreraInput.getText());
        PersonalData[7]= String.valueOf(InstitucionInput.getText());


        if(switchhorario.isChecked()){
            UDB.setPrivacidadHorario(true);
        }else{
            UDB.setPrivacidadHorario(false);
        }


        if(UDB.refreshData(PersonalData)){
            PersonalData=UDB.getPersonalInfo();
            NombreInput.setText(PersonalData[2]);
            ApellidoInput.setText(PersonalData[3]);
            UserNameInput.setText(PersonalData[0]);
            TelefonoInput.setText(PersonalData[5]);
            CorreoInput.setText(PersonalData[4]);
            CarreraInput.setText(PersonalData[6]);
            InstitucionInput.setText(PersonalData[7]);
            Toast.makeText(getActivity().getBaseContext(), "Actualizacion exitosa", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity().getBaseContext(), "Hubo un fallo al actualizar", Toast.LENGTH_LONG).show();
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
