package com.example.rjhy.mysearch;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by rjhy on 14-9-1.
 */
public class MyRecentSuggestionProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "com.example.rjhy.mysearch.MyRecentSuggestionProvider";
    public static final int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;

    public MyRecentSuggestionProvider(){
        setupSuggestions(AUTHORITY, MODE);
    }
}
