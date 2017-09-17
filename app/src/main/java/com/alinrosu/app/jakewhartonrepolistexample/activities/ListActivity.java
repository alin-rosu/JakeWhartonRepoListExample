package com.alinrosu.app.jakewhartonrepolistexample.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.alinrosu.app.jakewhartonrepolistexample.R;
import com.alinrosu.app.jakewhartonrepolistexample.entities.Repository;
import com.alinrosu.app.jakewhartonrepolistexample.interfaces.StringCallback;
import com.alinrosu.app.jakewhartonrepolistexample.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.RealmResults;

public class ListActivity extends AppCompatActivity {
    @InjectView(R.id.list)
    RecyclerView list;
    @InjectView(R.id.loader)
    ProgressBar loader;
    int page = 1;
    RealmResults<Repository> repositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.inject(ListActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.networkIsAvailable(ListActivity.this)){
            Repository.fetchRepositories(ListActivity.this, page, loader, new StringCallback() {
                @Override
                public void onResponse(Integer code, String string) {
                    repositories = Repository.fetchRepositoriesFromDB();
                }
            });
        }else {
            repositories = Repository.fetchRepositoriesFromDB();
        }
    }
}
