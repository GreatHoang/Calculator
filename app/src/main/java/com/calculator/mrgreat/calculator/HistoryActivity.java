package com.calculator.mrgreat.calculator;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static android.widget.AdapterView.*;

public class HistoryActivity extends Activity implements View.OnClickListener {

    ListView listView;
    Button buttonClear;
    Button buttonClose;

    private ArrayList<String> array;

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

        listView = (ListView) findViewById(R.id.listView);
        buttonClear = (Button) findViewById(R.id.btnclearHistory);
        buttonClose = (Button) findViewById(R.id.btncloseHistory);

        array = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Expression");
        current = bundle.getString("valueCurrentSent");
        dataView = bundle.getString("valueDataSent");

        if (!dataView.equals("#nodata")){
            StringTokenizer strT = new StringTokenizer(dataView, "#");
            while (strT.hasMoreTokens()){
                String token = strT.nextToken();
                array.add(token);

            }

        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //type = 1;
                select = array.get(position);
                sentToMain(select, MainActivity.SELECT);

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
            case R.id.btncloseHistory:
                if (type == 1){
                    sentToMain(current, MainActivity.CLOSE);

                }else {
                    sentToMain(current, MainActivity.CLEAR);

                }
                break;
            case R.id.btnclearHistory:
                array.clear();
                listView.setAdapter(arrayAdapter);
                type = 2;
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
