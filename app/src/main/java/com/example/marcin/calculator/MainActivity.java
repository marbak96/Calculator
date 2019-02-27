package com.example.marcin.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String HISTORY = "ovh.marcin.calculator.history";
    private static StringBuffer resultString;
    private static boolean lastPressedButtonIsArithmeticSymbol = false;
    private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();
    private static ArrayList<String> history;
    private static boolean isResultInResultTextView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (history == null) {
            history = new ArrayList<>();
        }

        if (resultString == null) {
            resultString = new StringBuffer(((TextView) findViewById(R.id.textViewResult)).getText());
        } else {
            this.updateResultString();
        }
    }

    public void onClickAddTextToResult(View v) {

        Button senderButton = (Button) v;

        boolean isSenderArithmeticButton = senderButton.getText().toString().equals("/")
                || senderButton.getText().toString().equals("*")
                || senderButton.getText().toString().equals("-")
                || senderButton.getText().toString().equals("+");

        if (isResultInResultTextView && !isSenderArithmeticButton) {
            this.onClickClearResult(v);
        }
        isResultInResultTextView = false;

        if (!(resultString.length() == 0 && isSenderArithmeticButton)) {

            if (isSenderArithmeticButton && lastPressedButtonIsArithmeticSymbol) {
                resultString.replace(resultString.length() - 1, resultString.length(), senderButton.getText().toString());
            } else {
                resultString.append(senderButton.getText());
            }

            lastPressedButtonIsArithmeticSymbol = isSenderArithmeticButton;

            this.updateResultString();
        }
    }

    public void onClickClearResult(View v) {
        resultString = new StringBuffer();
        lastPressedButtonIsArithmeticSymbol = false;
        isResultInResultTextView = false;
        this.updateResultString();
    }

    public void onClickDeleteOneChar(View v){

        resultString.delete(resultString.length()-1,resultString.length());
        this.updateResultString();
    }

    public void onClickGetResult(View v) {
        if (!lastPressedButtonIsArithmeticSymbol && resultString.length() > 0) {
            JexlExpression e = jexl.createExpression(resultString.toString());
            //try {
            if (!resultString.toString().contentEquals(e.evaluate(null).toString())) {
                resultString.append('=');
                resultString.append(e.evaluate(null));

                DataBaseHandler database = new DataBaseHandler(this);
                database.setData(resultString.toString());

                history.add(0, resultString.toString());

                resultString.replace(0, resultString.length(), e.evaluate(null).toString());
                isResultInResultTextView = true;

                updateResultString();
            }
            //} catch (JexlException exception) {
            //Toast.makeText(getApplicationContext(), "Wprowadzono niepoprawne wyrażenie!", Toast.LENGTH_LONG).show();
            //this.onClickClearResult(v);
            //}
        }
    }

    public void onClickClearHistory(View v) {
        DataBaseHandler database = new DataBaseHandler(this);
        database.cleanData();
    }

    public void onClickShowHistory(View v) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putStringArrayListExtra(HISTORY, history);
        startActivity(intent);
    }

    private void updateResultString() {
        ((TextView) findViewById(R.id.textViewResult)).setText(resultString);
    }
}