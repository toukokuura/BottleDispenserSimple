package com.example.bottledispenser;
// https://www.androidtutorialpoint.com/basics/android-seekbar-tutorial/

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText nro; // chosen product
    TextView products; // lists products
    TextView msg; // used for delivering system messages
    TextView mun; // shows amount of money in machine

    SeekBar amount;
    TextView amnt;

    // Creation of singleton
    BottleDispenser dispenser = BottleDispenser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect text views
        nro = (EditText) findViewById(R.id.nro);
        products = (TextView) findViewById(R.id.products);
        msg = (TextView) findViewById(R.id.systemMsg);
        mun = (TextView) findViewById(R.id.muns);
        amnt = (TextView) findViewById(R.id.progress);

        amount = (SeekBar) findViewById(R.id.seekBar);
        amnt.setText(Integer.toString(amount.getProgress()));

        amount.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    // used to notify the user changes/actions in the SeekBar

                    int progressChange;

                    @Override
                    public void onProgressChanged(SeekBar SeekBar, int progress, boolean fromUser) {
                        progressChange = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar SeekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar SeekBar) {
                        amnt.setText(Integer.toString(progressChange));
                    }
                });

        // List current products
        productList();
    }

    private void productList() {
        ArrayList<String> s = new ArrayList<String>();
        s = dispenser.list();

        // Empty TextView and add current bottles
        products.setText("");
        for (int i=0; i<s.size(); i++) {
            products.append(s.get(i));
        }
    }

    public void add(View x) {
        dispenser.addMoney(msg, amount.getProgress());
        //update money
        mun.setText("Amount of money: " + dispenser.showMoney());
    }

    public void take(View x) {
        dispenser.returnMoney(msg);
        //update money
        mun.setText("Amount of money: " + dispenser.showMoney());
    }

    public void buy(View x) {
        // take number from EditText nro and buy corresponding bottle
        dispenser.buyBottle(Integer.parseInt(nro.getText().toString()), msg);

        //update money
        mun.setText("Amount of money: " + dispenser.showMoney());

        // update product list
        productList();
    }
}