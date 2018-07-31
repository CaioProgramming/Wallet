package com.myself.wallet.Fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.golovin.fluentstackbar.FluentSnackbar;
import com.myself.wallet.Database.WalletRepository;
import com.myself.wallet.R;
import com.vicmikhailau.maskededittext.MaskedEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepositoFragment extends Fragment {


    public DepositoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deposito,container,false);
        final MaskedEditText money =   view.findViewById(R.id.moneytxt);
        Button moneybtn = view.findViewById(R.id.addmoneybtn);

        moneybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WalletRepository wlt= new WalletRepository(getActivity());
                wlt.abrir();
                wlt.inseridinheiro(Double.valueOf(money.getUnMaskedText()));
                message(money.getUnMaskedText());
            }
        });
        return view;

    }
    public void message(String text){
        FluentSnackbar mFluentSnackbar;
        mFluentSnackbar = FluentSnackbar.create(getActivity()); // you can also use any View instead of Activity
        mFluentSnackbar.create("Depositou " + text)
                .maxLines(2) // default is 1 line
                .backgroundColorRes(R.color.white) // default is #323232
                .textColorRes(R.color.green_500) // default is Color.WHITE
                .duration(Snackbar.LENGTH_SHORT) // default is Snackbar.LENGTH_LONG
                .important()
                .show();

    }

}
