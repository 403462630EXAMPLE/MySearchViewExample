package com.example.rjhy.mysearch;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class SearchDialogActivity extends Activity {
    public static final String TAG = "SearchDialogActivity";
    private Button button;
    private ListView listView;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_my);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchRequested();
            }
        });

        Intent intent = getIntent();
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        //因为每次search，都会发出android.intent.action.SEARCH动作，所以在AndroidManifest.xml文件中SearchDialogActivity配置了这个action
        //所以每次search都会启动这个activity，将结果显示在这个activity中
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            //接收参数
            Bundle bundle = intent.getBundleExtra(SearchManager.APP_DATA);
            if(bundle!=null && bundle.getString("data")!=null){
                Toast.makeText(this, bundle.getString("data"), Toast.LENGTH_SHORT).show();
            }

            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "onNewIntent");
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    public void doMySearch(String query){
        Log.i(TAG, "doMySearch");
        adapter.clear();
        String[] data = getResources().getStringArray(R.array.data);
        for(String str: data){
            if(str.contains(query)){
                adapter.add(str);
            }
        }
    }
}
