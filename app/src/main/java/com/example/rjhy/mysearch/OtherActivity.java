package com.example.rjhy.mysearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by rjhy on 14-8-28.
 */
public class OtherActivity extends Activity {

    private Button button;
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchRequested();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OtherActivity.this, SearchWidgetActivity.class));
            }
        });
    }
    @Override
    public boolean onSearchRequested() {
        //传参
        Bundle bundle = new Bundle();
        bundle.putString("data", "bundle");
        startSearch(null, false, bundle, false);
        return true;
    }
}
