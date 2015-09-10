package com.calculator.mrgreat.calculator;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BasicFragment extends Fragment implements View.OnClickListener{

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

    Button buttonClear;
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
    //DatabaseAdapter dbAdapter;


    public BasicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic_layout, container, false);

        textView1 = (TextView) view.findViewById(R.id.txtView1);
        textView2 = (TextView) view.findViewById(R.id.txtView2);

        button0 = (Button) view.findViewById(R.id.btnNum0);
        button1 = (Button) view.findViewById(R.id.btnNum1);
        button2 = (Button) view.findViewById(R.id.btnNum2);
        button3 = (Button) view.findViewById(R.id.btnNum3);
        button4 = (Button) view.findViewById(R.id.btnNum4);
        button5 = (Button) view.findViewById(R.id.btnNum5);
        button6 = (Button) view.findViewById(R.id.btnNum6);
        button7 = (Button) view.findViewById(R.id.btnNum7);
        button8 = (Button) view.findViewById(R.id.btnNum8);
        button9 = (Button) view.findViewById(R.id.btnNum9);

        buttonClear = (Button) view.findViewById(R.id.btnC);
        buttonCE = (Button) view.findViewById(R.id.btnCE);
        buttonMem = (Button) view.findViewById(R.id.btnMEM);
        buttonOpen = (Button) view.findViewById(R.id.btnOpen);
        buttonClose = (Button) view.findViewById(R.id.btnClose);

        buttonResult = (Button) view.findViewById(R.id.btnResult);
        buttonDiv = (Button) view.findViewById(R.id.btnDiv);
        buttonMul = (Button) view.findViewById(R.id.btnMul);
        buttonAdd = (Button) view.findViewById(R.id.btnAdd);
        buttonSub = (Button) view.findViewById(R.id.btnSub);

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

        buttonClear.setOnClickListener(this);
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

        Bundle bundle = this.getArguments();
        if(bundle!=null){
            String expression = bundle.getString("expression");
            textView1.setText(expression);

        }

        return view;

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
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
                Button btn = (Button) view;
                textView1.setText(textView1.getText() + btn.getText().toString());
                break;
            case R.id.btnResult:
                try {
                    String postfix = calculateExpression.convertToPolishNotation(textView1.getText().toString());
                    result = calculateExpression.calculatePolishNotation(postfix, 10);
                    textView2.setText(result);
                    MainActivity.dbAdapter.open();
                    long id = MainActivity.dbAdapter.insertExpression(textView1.getText().toString(), result, 10);
                    Log.e("id", "" + id);
                    MainActivity.dbAdapter.close();
                } catch (ExpressionFormatException | SQLiteException e) {
                    ExpressionError(e);

                }
                break;
            case R.id.btnMEM:
                /*String current = new String();
                if (textView1.getText().toString().equals("")) {
                    current = "#nodata=0";

                } else {
                    current = textView1.getText().toString() + "=" + textView2.getText().toString();

                }
                String dataView = new String();

                try {
                    MainActivity.dbAdapter.open();
                    Cursor cursor = MainActivity.dbAdapter.getAllExpression();
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
                    MainActivity.dbAdapter.close();
                } catch (SQLiteException e) {
                    e.printStackTrace();

                }

                Intent intent = new Intent(BasicFragment.this.getActivity(), HistoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("valueCurrentSent", current);

                bundle.putString("valueDataSent", dataView);
                intent.putExtra("Expression", bundle);
                startActivityForResult(intent, MainActivity.SHOW);*/

                Intent intent = new Intent();
                intent.setClass(BasicFragment.this.getActivity(), HistoryActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }

    }

    /*@Override
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
                    try {
                        dbAdapter.open();
                        dbAdapter.deleteAllExpression();
                        dbAdapter.close();

                    } catch (SQLiteException e) {
                        e.printStackTrace();

                    }

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

    }*/

    private void ExpressionError(Exception e) {
        String st = "Syntax error";
        if (e.getMessage() != null) st += ":" + e.getMessage();
        textView2.setText(st);

    }

}
