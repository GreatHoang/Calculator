package com.calculator.mrgreat.calculator;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static android.widget.AdapterView.*;

public class HistoryActivity extends Activity implements View.OnClickListener {


    Button buttonClear;
    Button buttonClose;
    Button deleteItem;
    RecyclerView recyclerView;

    private List<ExpressionFormat> array;
    protected ExpressionViewAdapter adapter;

    ArrayAdapter<String> arrayAdapter;
    private String current;
    private String dataView;
    //private String clr = "#deletedata";
    private String select;
    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_layout);

        recyclerView = (RecyclerView) findViewById(R.id.listHitory);
        buttonClear = (Button) findViewById(R.id.btnclearHistory);
        buttonClose = (Button) findViewById(R.id.btncloseHistory);



        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        array = new ArrayList<>();
        try {
            MainActivity.dbAdapter.open();
            Cursor cursor = MainActivity.dbAdapter.getAllExpression();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    ExpressionFormat expressionF = new ExpressionFormat();
                    expressionF.id = cursor.getInt(0);
                    expressionF.expression = cursor.getString(1);
                    expressionF.result = cursor.getString(2);
                    expressionF.base = cursor.getInt(3);
                    cursor.moveToNext();
                    array.add(expressionF);

                }

            } else {
                Log.e("data", "#nodata");

            }

        } catch (SQLiteException e) {
            e.printStackTrace();

        }

        adapter = new ExpressionViewAdapter(array);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new ExpressionViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int i = view.getId();
                switch (i) {
                    case R.id.btnDelete:
                        try {
                            MainActivity.dbAdapter.open();
                            MainActivity.dbAdapter.deleteByID(array.get(position).id);
                            MainActivity.dbAdapter.close();
                            array.remove(position);
                            adapter.notifyDataSetChanged();
                        } catch (SQLiteException e) {
                            e.printStackTrace();

                        }

                        break;
                    case R.id.itemView:
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("expression", array.get(position).expression);
                        bundle.putInt("base", array.get(position).base);
                        intent.putExtra("Result", bundle);
                        intent.setClass(HistoryActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;

                }

            }
        });


        buttonClear.setOnClickListener(this);
        buttonClose.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.btnclearHistory:
                try {
                    MainActivity.dbAdapter.open();
                    MainActivity.dbAdapter.deleteAllExpression();
                    MainActivity.dbAdapter.close();

                } catch (SQLiteException e) {
                    e.printStackTrace();

                }
                array.clear();
                adapter.notifyDataSetChanged();
                break;
            case R.id.btncloseHistory:
                this.finish();
                break;
            default:
                break;

        }

    }

    public void sentToMain(String value, int resultCode) {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("valueResult", value);
        intent.putExtra("Result", bundle);
        setResult(resultCode, intent);
        finish();

    }


}
