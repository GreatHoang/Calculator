package com.calculator.mrgreat.calculator;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;


public class ProgamingFragment extends Fragment implements View.OnClickListener {

    private static String[] RADIX = {"Binary", "Octal", "Hexa"};

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
    Button buttonA;
    Button buttonB;
    Button buttonC;
    Button buttonD;
    Button buttonE;
    Button buttonF;

    Button buttonClear;
    Button buttonCE;
    Button buttonMem;
    //Button buttonOpen;
    //Button buttonClose;

    Button buttonResult;
    Button buttonDiv;
    Button buttonMul;
    Button buttonAdd;
    Button buttonSub;

    Spinner selectRadix;
    int select = 0;
    ArrayList<View> groupButton;
    HashMap<View, Integer> gButton;

    CalculateExpression calculateExpression;

    public ProgamingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progaming_layout, container, false);

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
        buttonA = (Button) view.findViewById(R.id.btnNumA);
        buttonB = (Button) view.findViewById(R.id.btnNumB);
        buttonC = (Button) view.findViewById(R.id.btnNumC);
        buttonD = (Button) view.findViewById(R.id.btnNumD);
        buttonE = (Button) view.findViewById(R.id.btnNumE);
        buttonF = (Button) view.findViewById(R.id.btnNumF);

        buttonClear = (Button) view.findViewById(R.id.btnC);
        buttonCE = (Button) view.findViewById(R.id.btnCE);
        buttonMem = (Button) view.findViewById(R.id.btnMEM);
        //buttonOpen = (Button) view.findViewById(R.id.btnOpen);
        //buttonClose = (Button) view.findViewById(R.id.btnClose);

        buttonResult = (Button) view.findViewById(R.id.btnResult);
        buttonDiv = (Button) view.findViewById(R.id.btnDiv);
        buttonMul = (Button) view.findViewById(R.id.btnMul);
        buttonAdd = (Button) view.findViewById(R.id.btnAdd);
        buttonSub = (Button) view.findViewById(R.id.btnSub);

        selectRadix = (Spinner) view.findViewById(R.id.listRadix);

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
        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);
        buttonE.setOnClickListener(this);
        buttonF.setOnClickListener(this);

        buttonClear.setOnClickListener(this);
        buttonCE.setOnClickListener(this);
        buttonMem.setOnClickListener(this);
        //buttonOpen.setOnClickListener(this);
        //buttonClose.setOnClickListener(this);

        buttonResult.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        buttonMul.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProgamingFragment.this.getActivity(), android.R.layout.simple_spinner_item, RADIX);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        selectRadix.setAdapter(arrayAdapter);
        selectRadix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setButton(i);
                setView(i, select);
                select = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        calculateExpression = new CalculateExpression();

        Bundle bundle = this.getArguments();
        if (bundle!=null){
            switch (bundle.getInt("base")){
                case 2:
                    select = 0;
                    break;
                case 8:
                    select = 1;
                    break;
                case 16:
                    select = 2;
                    break;
                default:
                    break;
            }
            selectRadix.setSelection(select);
            setButton(select);
            textView1.setText(bundle.getString("expression"));

        }

        setButton(select);
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
            case R.id.btnNumA:
            case R.id.btnNumB:
            case R.id.btnNumC:
            case R.id.btnNumD:
            case R.id.btnNumE:
            case R.id.btnNumF:
                //if(!result.equals("")) textView1.setText("");
                Button btn = (Button) view;
                textView1.setText(textView1.getText() + btn.getText().toString());
                break;
            case R.id.btnResult:
                try {
                    String postfix = calculateExpression.convertToPolishNotation(textView1.getText().toString());
                    long id;
                    switch (select) {
                        case 0:
                            result = calculateExpression.calculatePolishNotation(postfix, 2);
                            MainActivity.dbAdapter.open();
                            id = MainActivity.dbAdapter.insertExpression(textView1.getText().toString().toUpperCase(), result.toUpperCase(), 2);
                            Log.e("id", "" + id);
                            MainActivity.dbAdapter.close();
                            break;
                        case 1:
                            result = calculateExpression.calculatePolishNotation(postfix, 8);
                            MainActivity.dbAdapter.open();
                            id = MainActivity.dbAdapter.insertExpression(textView1.getText().toString().toUpperCase(), result.toUpperCase(), 8);
                            Log.e("id", "" + id);
                            MainActivity.dbAdapter.close();
                            break;
                        case 2:
                            result = calculateExpression.calculatePolishNotation(postfix, 16);
                            MainActivity.dbAdapter.open();
                            id = MainActivity.dbAdapter.insertExpression(textView1.getText().toString().toUpperCase(), result.toUpperCase(), 16);
                            Log.e("id", "" + id);
                            MainActivity.dbAdapter.close();
                            break;
                        default:
                            break;

                    }
                    textView2.setText(result.toUpperCase());

                } catch (ExpressionFormatException | SQLiteException e) {
                    ExpressionError(e);

                }
                break;
            case R.id.btnMEM:
                Intent intent = new Intent();
                intent.setClass(ProgamingFragment.this.getActivity(), HistoryActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }

    }

    private void setButton(int select) {
        switch (select) {
            case 0:
                button0.setEnabled(true);
                button1.setEnabled(true);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);
                buttonA.setEnabled(false);
                buttonB.setEnabled(false);
                buttonC.setEnabled(false);
                buttonD.setEnabled(false);
                buttonE.setEnabled(false);
                buttonF.setEnabled(false);
                break;
            case 1:
                button0.setEnabled(true);
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
                button5.setEnabled(true);
                button6.setEnabled(true);
                button7.setEnabled(true);
                button8.setEnabled(false);
                button9.setEnabled(false);
                buttonA.setEnabled(false);
                buttonB.setEnabled(false);
                buttonC.setEnabled(false);
                buttonD.setEnabled(false);
                buttonE.setEnabled(false);
                buttonF.setEnabled(false);
                break;
            case 2:
                button0.setEnabled(true);
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
                button5.setEnabled(true);
                button6.setEnabled(true);
                button7.setEnabled(true);
                button8.setEnabled(true);
                button9.setEnabled(true);
                buttonA.setEnabled(true);
                buttonB.setEnabled(true);
                buttonC.setEnabled(true);
                buttonD.setEnabled(true);
                buttonE.setEnabled(true);
                buttonF.setEnabled(true);
                break;
            default:
                break;

        }

    }

    private void setView(int select, int baseCurrent) {
        StringTokenizer strConvert = new StringTokenizer(textView1.getText().toString(), CalculateExpression.OPERATORS, true);
        String strView = new String();
        while (strConvert.hasMoreTokens()) {
            String token = strConvert.nextToken();
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("÷") || token.equals("n-") || token.equals("n+")) {
                strView += token;

            } else {
                strView += convert(token, select, baseCurrent);

            }

        }
        textView1.setText(strView.toUpperCase());
        String view2 = textView2.getText().toString();
        textView2.setText(convert(view2, select, baseCurrent).toUpperCase());


    }

    private String convert(String token, int select, int baseCurrent){
        int convert = 0;
        switch (select) {
            case 0:
                switch (baseCurrent){
                    case 0:
                        convert = Integer.parseInt(token, 2);
                        break;
                    case 1:
                        convert = Integer.parseInt(token, 8);
                        break;
                    case 2:
                        convert = Integer.parseInt(token, 16);
                        break;
                    default:
                        break;

                }
                return Integer.toString(convert, 2);
            case 1:
                switch (baseCurrent){
                    case 0:
                        convert = Integer.parseInt(token, 2);
                        break;
                    case 1:
                        convert = Integer.parseInt(token, 8);
                        break;
                    case 2:
                        convert = Integer.parseInt(token, 16);
                        break;
                    default:
                        break;

                }
                return Integer.toString(convert, 8);
            case 2:
                switch (baseCurrent){
                    case 0:
                        convert = Integer.parseInt(token, 2);
                        break;
                    case 1:
                        convert = Integer.parseInt(token, 8);
                        break;
                    case 2:
                        convert = Integer.parseInt(token, 16);
                        break;
                    default:
                        break;

                }
                return Integer.toString(convert, 16);
            default:
                return null;

        }

    }

    private void createGroupButton() {
        groupButton = new ArrayList<>();
        groupButton.add(button0);
        groupButton.add(button1);
        groupButton.add(button2);
        groupButton.add(button3);
        groupButton.add(button4);
        groupButton.add(button5);
        groupButton.add(button6);
        groupButton.add(button7);
        groupButton.add(button8);
        groupButton.add(button9);
        groupButton.add(buttonA);
        groupButton.add(buttonB);
        groupButton.add(buttonC);
        groupButton.add(buttonD);
        groupButton.add(buttonE);
        groupButton.add(buttonF);

        gButton = new HashMap<>();
        gButton.put(button0, 0);
        gButton.put(button1, 0);

        gButton.put(button0, 1);
        gButton.put(button1, 1);
        gButton.put(button2, 1);
        gButton.put(button3, 1);
        gButton.put(button4, 1);
        gButton.put(button5, 1);
        gButton.put(button6, 1);
        gButton.put(button7, 1);
        gButton.put(button8, 1);

    }

    private void ExpressionError(Exception e) {
        String st = "Syntax error";
        if (e.getMessage() != null) st += ":" + e.getMessage();
        textView2.setText(st);

    }

}
