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
public class fmAlumbrado  extends Fragment{

    public static android.support.v4.app.Fragment newInstance(Context context) {

        fmAlumbrado f = new fmAlumbrado();

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View root =  inflater.inflate(R.layout.option_2_alumbrado, null);
        root.getContext();

        return root;


    }
}
