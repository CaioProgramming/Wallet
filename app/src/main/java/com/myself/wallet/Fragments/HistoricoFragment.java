package com.myself.wallet.Fragments;


import android.database.Cursor;
import android.graphics.Color;
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
import com.myself.wallet.GastosAdapter;
import com.myself.wallet.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricoFragment extends Fragment {

     WalletRepository listRepository;
    public HistoricoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historico,container,false);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.gastoframe);
        LinearLayout empty = view.findViewById(R.id.empty);
        List<Wallet> gastolst = new ArrayList<>();
        LineChartView  chart = (LineChartView) view.findViewById(R.id.linechart);
        chart.animate();
        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values = new ArrayList<PointValue>();
        listRepository = new WalletRepository(getActivity());
        listRepository.abrir();
        Cursor gastos = listRepository.obtergastos();
        for (int j = 0; j < 5; ++j) {
            values.add(new PointValue(j, gastos.getColumnIndexOrThrow("DINHEIRO")));
        }
        Line line = new Line(values);
        line.setColor(Color.WHITE);
        line.setFilled(true);
        LineChartData  data = new LineChartData(lines);
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);


        RecyclerView recycler = view.findViewById(R.id.recyclergasto);
        listRepository = new WalletRepository(getContext());
        listRepository.abrir();
        Cursor evento = listRepository.obtergastos();
        System.out.println(evento.getCount());
        evento.moveToFirst();


        if (evento.getCount() == 0) {

        }else {

            empty.setVisibility(View.INVISIBLE);
            recycler.setVisibility(View.VISIBLE);

        }
        for (int i = 0 ;i < evento.getCount();i++){

            gastolst.add(listRepository.criagastos(evento));
            evento.moveToNext();

        }
        GridLayoutManager llm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recycler.setHasFixedSize(true);
        System.out.println(gastolst.size());
        listRepository.fecha();

        GastosAdapter myadapter = new GastosAdapter(getActivity(),gastolst);
        recycler.setAdapter(myadapter);
        recycler.setLayoutManager(llm);





        return view;


    }

}
