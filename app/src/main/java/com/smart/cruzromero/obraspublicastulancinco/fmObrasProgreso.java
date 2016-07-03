package com.smart.cruzromero.obraspublicastulancinco;

/**
 * Created by cruz on 05/03/2016.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fmObrasProgreso extends Fragment{

    public static android.support.v4.app.Fragment newInstance(Context context) {

        fmObrasProgreso f = new fmObrasProgreso();

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View root =  inflater.inflate(R.layout.option_3_obras_progreso, null);
        root.getContext();

        return root;


    }
}
