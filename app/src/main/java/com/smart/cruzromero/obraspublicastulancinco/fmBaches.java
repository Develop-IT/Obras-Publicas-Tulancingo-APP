package com.smart.cruzromero.obraspublicastulancinco;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cruz on 05/03/2016.
 */
public class fmBaches extends Fragment {

    public static android.support.v4.app.Fragment newInstance(Context context) {

        fmBaches f = new fmBaches();

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View root =  inflater.inflate(R.layout.option_1_baches, null);
        root.getContext();

        return root;


    }
}
