/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.aqil.katalogfilmuiux;

import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * Fragment that displays "Wednesday".
 */
public class SearchFragment extends Fragment implements LoaderCallbacks<ArrayList<Movie>> {
    private static final String TAG = "TAG";
    MovieAdapter adapter;
    SearchView edtSearch;
    RecyclerView recyclerViewList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        edtSearch = rootView.findViewById(R.id.edt_search);
        edtSearch.setOnQueryTextListener(myListener);
        recyclerViewList = rootView.findViewById(R.id.rv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new MovieAdapter(getActivity());
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerViewList.setAdapter(adapter);
        return rootView;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(2, bundle, this);

    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        String mNamaMovie = null;
        if (args != null) {
            mNamaMovie = args.getString(EXTRAS_SEARCH);
        }
        return new QueryUtils(getContext(), mNamaMovie);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

        adapter.setListMovie(data);
        adapter.notifyDataSetChanged();
        Log.d(TAG, "onLoadFinished: " + data.size());

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        adapter.setListMovie(null);
    }

    private String EXTRAS_SEARCH = "pencarian";
    SearchView.OnQueryTextListener myListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            String pencarian = edtSearch.getQuery().toString();
            String fixPencarian = null;

            fixPencarian = pencarian.replace(" ", "%20");

            if (TextUtils.isEmpty(pencarian)) return false;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_SEARCH, fixPencarian);
            getLoaderManager().restartLoader(2, bundle, SearchFragment.this);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }


    };

}
