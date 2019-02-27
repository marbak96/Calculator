package com.example.marcin.calculator;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DataBaseHandler database = new DataBaseHandler(this);
        String History = database.getData().toString();
        TextView textView = findViewById(R.id.textViewResult);
        textView.setText(History+" ");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickBackToCounting(View v){
        this.finishAndRemoveTask();
    }

}
