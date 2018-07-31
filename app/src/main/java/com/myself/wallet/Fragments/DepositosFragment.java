package com.myself.wallet.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.myself.wallet.Beans.Wallet;
import com.myself.wallet.Database.WalletRepository;
import com.myself.wallet.DepositoAdapter;
import com.myself.wallet.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepositosFragment extends Fragment {

     WalletRepository walletRepository;
    Double gastomoney = 0.0;

    public DepositosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        walletRepository = new WalletRepository(getActivity());
        View view = inflater.inflate(R.layout.carteira,container,false);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.carteirafragment);
        LinearLayout empty = view.findViewById(R.id.empty);
        List<Wallet> depositolst = new ArrayList<>();
        getGastos();
        CreateChart(view);
        CreateDeposits(view, empty, depositolst);
        return view;
    }

    private void CreateDeposits(View view, LinearLayout empty, List<Wallet> depositolst) {
        RecyclerView recycler = view.findViewById(R.id.recyclercarteira);
        walletRepository = new WalletRepository(getContext());
        Cursor evento = walletRepository.obterDepositos();
        System.out.println(evento.getCount());
        evento.moveToFirst();

        if (evento.getCount() == 0) {

        }else {

            empty.setVisibility(View.INVISIBLE);
            recycler.setVisibility(View.VISIBLE);

        }
        for (int i = 0 ;i < evento.getCount();i++){

            depositolst.add(walletRepository.criadeposito(evento));
            evento.moveToNext();

        }
        GridLayoutManager llm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recycler.setHasFixedSize(true);
        System.out.println(depositolst.size());
        walletRepository.fecha();

        DepositoAdapter myadapter = new DepositoAdapter(getActivity(),depositolst);
        recycler.setAdapter(myadapter);
        recycler.setLayoutManager(llm);
    }

    private void CreateChart(View view) {
        walletRepository.abrir();
        Wallet wallet = walletRepository.criacarteira();

        PieChartView chart = (PieChartView) view.findViewById(R.id.chart);
        chart.setCircleFillRatio(1.0f);
        chart.setChartRotation(2,true);

        List<SliceValue> values = new ArrayList<SliceValue>();
        SliceValue sliceValue = new SliceValue((float)wallet.getDinheiro().floatValue(), ChartUtils.COLOR_GREEN);
        SliceValue sliceValue2 = new SliceValue((float)gastomoney.floatValue(), ChartUtils.COLOR_RED);

        values.add(sliceValue);
        values.add(sliceValue2);
        PieChartData data = new PieChartData(values);
        data.setHasLabels(true);
    }

    private void getGastos( ) {
        walletRepository.abrir();

        Cursor gasto = walletRepository.obtergastos();
        for (int i= 0;i < gasto.getCount();i++){
            
            Wallet gastob = new Wallet();
            gastob.setDinheiro(Double.valueOf(gasto.getColumnIndex("DINHEIRO")));

            gastomoney += gastob.getDinheiro();



        }
    }

}
