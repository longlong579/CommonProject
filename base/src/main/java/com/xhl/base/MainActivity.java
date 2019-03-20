package com.xhl.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import searchview.xhl.com.scanner.qrcode.zxingScaner.ZingScannerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ZingScannerActivity.launch(this);
    }
}
