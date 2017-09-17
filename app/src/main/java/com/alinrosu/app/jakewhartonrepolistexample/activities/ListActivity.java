package com.alinrosu.app.jakewhartonrepolistexample.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alinrosu.app.jakewhartonrepolistexample.R;
import com.alinrosu.app.jakewhartonrepolistexample.adapter.RepositoryAdapter;
import com.alinrosu.app.jakewhartonrepolistexample.entities.Repository;
import com.alinrosu.app.jakewhartonrepolistexample.interfaces.StringCallback;
import com.alinrosu.app.jakewhartonrepolistexample.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ListActivity extends AppCompatActivity {
    @InjectView(R.id.list)
    RecyclerView list;
    @InjectView(R.id.loader)
    ProgressBar loader;
    int page = 1;
    ArrayList<Repository> repositories;
    RepositoryAdapter repositoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.inject(ListActivity.this);
        initAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.networkIsAvailable(ListActivity.this)){
            Repository.fetchRepositories(ListActivity.this, page, loader, new StringCallback() {
                @Override
                public void onResponse(Integer code, String string) {
                    repositories = new ArrayList<Repository>(Repository.fetchRepositoriesFromDB());
//                    setDataToRepository();
                }
            });
        }else {
            Toast.makeText(ListActivity.this, getString(R.string.no_internet) , Toast.LENGTH_SHORT).show();
            repositories = new ArrayList<Repository>(Repository.fetchRepositoriesFromDB());
//            setDataToRepository();
        }
    }

    public void initAdapter(){
        repositoryAdapter = new RepositoryAdapter(ListActivity.this);
        list.setAdapter(repositoryAdapter);
    }

    public void setDataToRepository(){
        repositoryAdapter.clearItems();
        repositoryAdapter.addItems(repositories);
        list.setAdapter(repositoryAdapter);
    }
}
