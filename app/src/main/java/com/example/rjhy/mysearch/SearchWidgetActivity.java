package com.example.rjhy.mysearch;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * Created by rjhy on 14-8-28.
 */
public class SearchWidgetActivity extends Activity {
    private ListView listView;
    private ArrayAdapter adapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(SearchDialogActivity.TAG, "SearchWidgetActivity.onCreate");
        setContentView(R.layout.search_widget);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        //这是SearchView的位置在layout文件中配置
        searchView = (SearchView) findViewById(R.id.action_search);
        initSearchView(searchView);
    }



    public void initSearchView(final SearchView searchView){
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);//false->searchView一直是展开状态, true->可展开和关闭
//        searchView.setIconified(false);//false->展开，true->关闭
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                doMySearch(s);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
////                doMySearch(s);
////                return true;
//            }
//        });
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                Log.i(SearchDialogActivity.TAG, "searchView.onSuggestionSelect");
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Log.i(SearchDialogActivity.TAG, "searchView.onSuggestionClick");
                setSuggestion(searchView, position);
                return true;
            }
        });
    }

    private void setSuggestion(SearchView searchView, int position){
        Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
        searchView.setQuery(cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1)), true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(SearchDialogActivity.TAG, "SearchWidgetActivity.onCreateOptionsMenu");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my, menu);

        //此SearchView显示在actionbar上
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        initSearchView(searchView);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_search){
            onSearchRequested();//显示SearchDialog控件
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(SearchDialogActivity.TAG, "SearchWidgetActivity.onNewIntent");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            //接收参数
            Bundle bundle = intent.getBundleExtra(SearchManager.APP_DATA);
            if(bundle!=null && bundle.getString("data")!=null){
                Toast.makeText(this, bundle.getString("data"), Toast.LENGTH_SHORT).show();
            }

            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i(SearchDialogActivity.TAG, "query="+query);
            doMySearch(query);
        }
    }

    public void doMySearch(String query){
        Log.i(SearchDialogActivity.TAG, "SearchWidgetActivity.doMySearch");

        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, MyRecentSuggestionProvider.AUTHORITY, MyRecentSuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, "LINE2:"+query);

        adapter.clear();
        String[] data = getResources().getStringArray(R.array.data);
        for(String str: data){
            if(str.contains(query)){
                adapter.add(str);
            }
        }
    }


}
