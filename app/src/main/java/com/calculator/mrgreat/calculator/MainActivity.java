package com.calculator.mrgreat.calculator;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

public class MainActivity extends Activity implements View.OnClickListener {

    public static final int SHOW = 1;
    public static final int CLEAR = 2;
    public static final int CLOSE = 3;
    public static final int SELECT = 4;

    TextView textView1;
    TextView textView2;

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;

    Button buttonC;
    Button buttonCE;
    Button buttonMem;
    Button buttonOpen;
    Button buttonClose;

    Button buttonResult;
    Button buttonDiv;
    Button buttonMul;
    Button buttonAdd;
    Button buttonSub;

    CalculateExpression calculateExpression;
    DatabaseAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView) findViewById(R.id.txtView1);
        textView2 = (TextView) findViewById(R.id.txtView2);

        button0 = (Button) findViewById(R.id.btnNum0);
        button1 = (Button) findViewById(R.id.btnNum1);
        button2 = (Button) findViewById(R.id.btnNum2);
        button3 = (Button) findViewById(R.id.btnNum3);
        button4 = (Button) findViewById(R.id.btnNum4);
        button5 = (Button) findViewById(R.id.btnNum5);
        button6 = (Button) findViewById(R.id.btnNum6);
        button7 = (Button) findViewById(R.id.btnNum7);
        button8 = (Button) findViewById(R.id.btnNum8);
        button9 = (Button) findViewById(R.id.btnNum9);

        buttonC = (Button) findViewById(R.id.btnC);
        buttonCE = (Button) findViewById(R.id.btnCE);
        buttonMem = (Button) findViewById(R.id.btnMEM);
        buttonOpen = (Button) findViewById(R.id.btnOpen);
        buttonClose = (Button) findViewById(R.id.btnClose);

        buttonResult = (Button) findViewById(R.id.btnResult);
        buttonDiv = (Button) findViewById(R.id.btnDiv);
        buttonMul = (Button) findViewById(R.id.btnMul);
        buttonAdd = (Button) findViewById(R.id.btnAdd);
        buttonSub = (Button) findViewById(R.id.btnSub);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);

        buttonC.setOnClickListener(this);
        buttonCE.setOnClickListener(this);
        buttonMem.setOnClickListener(this);
        buttonOpen.setOnClickListener(this);
        buttonClose.setOnClickListener(this);

        buttonResult.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        buttonMul.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);

        calculateExpression = new CalculateExpression();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        String str;
        String result = new String();
        switch (i) {
            case R.id.btnC:
                textView1.setText("");
                textView2.setText("0");
                break;
            case R.id.btnCE:
                str = textView1.getText().toString();
                if (str.length() > 0) {
                    str = str.subSequence(0, str.length() - 1).toString();

                }
                textView1.setText(str);
                break;
            case R.id.btnOpen:
            case R.id.btnClose:
            case R.id.btnDiv:
            case R.id.btnMul:
            case R.id.btnAdd:
            case R.id.btnSub:
            case R.id.btnNum0:
            case R.id.btnNum1:
            case R.id.btnNum2:
            case R.id.btnNum3:
            case R.id.btnNum4:
            case R.id.btnNum5:
            case R.id.btnNum6:
            case R.id.btnNum7:
            case R.id.btnNum8:
            case R.id.btnNum9:
                //if(!result.equals("")) textView1.setText("");
                Button btn = (Button) v;
                textView1.setText(textView1.getText() + btn.getText().toString());
                break;
            case R.id.btnResult:
                try {
                    String postfix = calculateExpression.convertToPolishNotation(textView1.getText().toString());
                    result = calculateExpression.calculatePolishNotation(postfix);
                    textView2.setText(result);
                    dbAdapter.open();
                    long id = dbAdapter.insertExpression(textView1.getText().toString(), result);
                    Log.e("id", "" + id);
                    dbAdapter.close();
                } catch (ExpressionFormatException | SQLiteException e) {
                    ExpressionError(e);

                }
                break;
            case R.id.btnMEM:
//                Fragment history = new HistoryFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(android.R.id.content, history);
//                fragmentTransaction.commit();
                String current = new String();
                if (textView1.getText().toString().equals("")) {
                    current = "#nodata=0";

                } else {
                    current = textView1.getText().toString() + "=" + textView2.getText().toString();

                }
                String dataView = new String();

                try {
                    dbAdapter.open();
                    Cursor cursor = dbAdapter.getAllExpression();
                    if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {
                            dataView += cursor.getString(0)
                                    + "="
                                    + cursor.getString(1);
                            dataView += "#";
                            cursor.moveToNext();

                        }

                    } else {
                        dataView = "#nodata";

                    }
                    dbAdapter.close();
                } catch (SQLiteException e) {
                    e.printStackTrace();

                }

                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("valueCurrentSent", current);

                bundle.putString("valueDataSent", dataView);
                intent.putExtra("Expression", bundle);
                startActivityForResult(intent, SHOW);
            default:
                break;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;

        }
        if (requestCode == SHOW) {
            Bundle bundle = data.getBundleExtra("Result");
            String reslt = bundle.getString("valueResult");
            StringTokenizer strT = new StringTokenizer(reslt, "=");
            Stack<String> stack = new Stack<>();
            while (strT.hasMoreTokens()) {
                String token = strT.nextToken();
                stack.push(token);

            }
            switch (resultCode) {
                case CLEAR:
                    dbAdapter.open();
                    dbAdapter.deleteAllExpression();
                    dbAdapter.close();

                    if (!reslt.equals("#nodata=0")) {
                        textView2.setText(stack.pop());
                        textView1.setText(stack.pop());

                    } else {
                        textView2.setText("0");
                        textView1.setText("");

                    }
                    break;
                case SELECT:
                case CLOSE:
                    if (!reslt.equals("#nodata=0")) {
                        textView2.setText(stack.pop());
                        textView1.setText(stack.pop());

                    } else {
                        textView2.setText("0");
                        textView1.setText("");

                    }
                    break;
                default:
                    break;

            }

        }

    }

    private void ExpressionError(Exception e) {
        String st = "Syntax error";
        if (e.getMessage() != null) st += ":" + e.getMessage();
        textView2.setText(st);

    }

}
