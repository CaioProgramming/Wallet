package com.myself.wallet;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myself.wallet.Beans.Wallet;
import com.myself.wallet.Database.WalletRepository;

import java.util.List;

public class DepositoAdapter extends RecyclerView.Adapter<DepositoAdapter.MyViewHolder> {


    private Context mContext;
    private Dialog myDialog;

    private List<Wallet> mData;
    WalletRepository lst;

    public DepositoAdapter(Context mContext, List<Wallet> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.depositocard,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DepositoAdapter.MyViewHolder holder, final int position) {
        holder.title.setText(mData.get(position).getItem());
        holder.descricao.setText(mData.get(position).getData());
        holder.data.setText(String.valueOf(mData.get(position).getDinheiro()));
    }



    @Override
    public int getItemCount() {
        if(mData.size() == 0){
            return 0;

        }else{
            return mData.size();}
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  title,descricao,data;

        CardView card;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView)itemView.findViewById(R.id.dinheiroinsert);
            descricao = (TextView)itemView.findViewById(R.id.datainsert);

            card = itemView.findViewById(R.id.insertcard);


        }
    }
}