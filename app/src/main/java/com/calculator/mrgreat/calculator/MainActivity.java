package com.calculator.mrgreat.calculator;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;
import java.util.StringTokenizer;

public class MainActivity extends ActionBarActivity{

    public static final int SHOW = 1;
    public static final int CLEAR = 2;
    public static final int CLOSE = 3;
    public static final int SELECT = 4;

    boolean basic = true;

    public static DatabaseAdapter dbAdapter;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragment(basic);
        dbAdapter = new DatabaseAdapter(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_basic) {
            basic = true;
            getFragment(basic);
        }else if(id == R.id.action_programming) {
            basic = false;
            getFragment(basic);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        bundle = intent.getBundleExtra("Result");
        int base = bundle.getInt("base");
        if (base==10){
            getFragment(true, bundle);

        }else {
            getFragment(false, bundle);

        }

    }

    public void getFragment(boolean b){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (b){
            BasicFragment basicFragment = new BasicFragment();
            fragmentTransaction.replace(R.id.content, basicFragment);

        }else {
            ProgamingFragment progamingFragment = new ProgamingFragment();
            fragmentTransaction.replace(R.id.content, progamingFragment);

        }
        fragmentTransaction.commit();

    }

    public void getFragment(boolean b, Bundle bundle){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (b){
            BasicFragment basicFragment = new BasicFragment();
            basicFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.content, basicFragment);

        }else {
            ProgamingFragment progamingFragment = new ProgamingFragment();
            progamingFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.content, progamingFragment);

        }
        fragmentTransaction.commit();

    }


}
