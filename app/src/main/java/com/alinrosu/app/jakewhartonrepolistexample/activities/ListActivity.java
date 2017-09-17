package com.alinrosu.app.jakewhartonrepolistexample.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alinrosu.app.jakewhartonrepolistexample.R;
import com.alinrosu.app.jakewhartonrepolistexample.adapter.RepositoryAdapter;
import com.alinrosu.app.jakewhartonrepolistexample.entities.Repository;
import com.alinrosu.app.jakewhartonrepolistexample.interfaces.StringCallback;
import com.alinrosu.app.jakewhartonrepolistexample.utils.EndlessRecyclerOnScrollListener;
import com.alinrosu.app.jakewhartonrepolistexample.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static java.security.AccessController.getContext;

public class ListActivity extends AppCompatActivity {
    @InjectView(R.id.list)
    RecyclerView list;
    @InjectView(R.id.loader)
    ProgressBar loader;
    int page = 1;
    ArrayList<Repository> repositories;
    RepositoryAdapter repositoryAdapter;
    boolean moreData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.inject(ListActivity.this);
        initAdapter();
        if(Utils.networkIsAvailable(ListActivity.this)){
            Repository.invalidateRealmData();
            moreData = true;
            willFetchNewData();
        }else {
            Toast.makeText(ListActivity.this, getString(R.string.no_internet) , Toast.LENGTH_SHORT).show();
            repositories = new ArrayList<Repository>(Repository.fetchRepositoriesFromDB());
            setDataToRepository(false);
        }
    }

    private void willFetchNewData() {
        Repository.fetchRepositories(ListActivity.this, page, loader, new StringCallback() {
            @Override
            public void onResponse(Integer code, String string) {
                if(string.contentEquals("noMore"))
                    moreData = false;
                repositories = new ArrayList<Repository>(Repository.fetchRepositoriesFromDB());
                setDataToRepository(moreData);
            }
        });
    }

    public void initAdapter(){
        list.setLayoutManager(new LinearLayoutManager(ListActivity.this));
        repositoryAdapter = new RepositoryAdapter(ListActivity.this);
        list.setAdapter(repositoryAdapter);
    }

    public void setDataToRepository(boolean moreData){
        repositoryAdapter.clearItems();
        repositoryAdapter.addItems(repositories);
        if(moreData){
            setEndlessScrollListener();
        }else list.setOnScrollListener(null);
    }

    public void setEndlessScrollListener(){
        list.setOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) list.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                Log.i("", "here is the code to populate the next items for the list");
                page++;
                willFetchNewData();
            }
        });
    }
}
