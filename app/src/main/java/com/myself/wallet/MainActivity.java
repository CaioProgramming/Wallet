package com.myself.wallet;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.golovin.fluentstackbar.FluentSnackbar;
import com.myself.wallet.Database.WalletRepository;
import com.myself.wallet.Fragments.DepositoFragment;
import com.myself.wallet.Fragments.DepositosFragment;
import com.myself.wallet.Fragments.GastoFragment;
import com.myself.wallet.Fragments.HistoricoFragment;
import com.vicmikhailau.maskededittext.MaskedEditText;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    WalletRepository walletRepository;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment,new GastoFragment())
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment,new HistoricoFragment())
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment,new DepositosFragment())
                            .commit();

                    return true;
                case R.id.guardar:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment,new DepositoFragment())
                            .commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment,new HistoricoFragment())
                    .commit();


        }
        walletRepository = new WalletRepository(this);
        walletRepository.abrir();
        Cursor evento = walletRepository.obtercarteira();
        System.out.println(evento.getCount());

        if (evento.getCount() == 0) {
            MessageRegister();

        }else {

            navigation.setSelectedItemId(R.id.navigation_notifications);
        }

        walletRepository.fecha();



    }

    public void MessageRegister() {
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.primeiroacesso);
        final MaskedEditText money = myDialog.findViewById(R.id.money1);
        Button Itembtn = (Button) myDialog.findViewById(R.id.itembtn);
        myDialog.show();
        Itembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WalletRepository wlt = new WalletRepository(getApplicationContext());
                wlt.inseridinheiro(Double.valueOf(money.getUnMaskedText()));
                message(money.getUnMaskedText());
                myDialog.dismiss();
            }
        });
    }
    public void message(String text){
        FluentSnackbar mFluentSnackbar;
        mFluentSnackbar = FluentSnackbar.create(this); // you can also use any View instead of Activity
        mFluentSnackbar.create("Depositou " + text)
                .maxLines(2) // default is 1 line
                .backgroundColorRes(R.color.white) // default is #323232
                .textColorRes(R.color.green_500) // default is Color.WHITE
                .duration(Snackbar.LENGTH_SHORT) // default is Snackbar.LENGTH_LONG
                .important()
                .show();

    }
}

