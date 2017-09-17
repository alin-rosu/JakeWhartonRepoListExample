package com.alinrosu.app.jakewhartonrepolistexample.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alinrosu.app.jakewhartonrepolistexample.R;
import com.alinrosu.app.jakewhartonrepolistexample.entities.Owner;
import com.alinrosu.app.jakewhartonrepolistexample.entities.Repository;
import com.alinrosu.app.jakewhartonrepolistexample.utils.Alert;
import com.alinrosu.app.jakewhartonrepolistexample.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by alin on 9/17/2017.
 */

public class RepositoryAdapter  extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder>{

    private ArrayList<Repository> mItems = new ArrayList<>();
    private Activity context;
    private boolean showLoader = false;

    public class ViewHolder extends RecyclerView.ViewHolder { //viewholder for my adapter
        @InjectView(R.id.userpic) ImageView pic;
        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.user) TextView user;
        @InjectView(R.id.url) TextView url;
        @InjectView(R.id.created) TextView created;
        @InjectView(R.id.loader) ProgressBar loader;

        public ViewHolder(View view){
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public RepositoryAdapter(Activity activity){ //constructor for my adapter
        this.context = activity;
    }

    public void clearItems(){ //function to clear all items from the adapter
        mItems.clear();
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<Repository> items){ //function to add items to the adapter
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void setShowLoader(boolean showLoader) {
        this.showLoader = showLoader;
    }

    @Override
    public int getItemCount(){
        return mItems.size();
    }

    public Object getItem(int position){
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_repository, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int position) { //here I set the values for the views inside my viewHolder
        final Repository item = mItems.get(position);
        Log.i("","onBindViewHolder called");
        Owner owner = item.getOwner();
        try {
            viewHolder.title.setText(item.getName());
            viewHolder.created.setText(item.getCreated_at());
            viewHolder.url.setText(item.getHtml_url());
            viewHolder.url.setPaintFlags(viewHolder.url.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            viewHolder.url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.getHtml_url())));
                    }catch (Exception e){
                        Alert.show(context, context.getString(R.string.error), e.getMessage(), null);
                    }
                }
            });
            if (owner != null) {
                viewHolder.user.setText(item.getOwner().getLogin());
                if (owner.getAvatar_url() != null) {
                    Picasso.with(context).load(owner.getAvatar_url()).placeholder(R.mipmap.ic_launcher).into(viewHolder.pic); //I use picasso to load the image
                } else {
                    viewHolder.pic.setVisibility(View.GONE);
                }
            } else viewHolder.user.setText(item.getFull_name());
            if(position == mItems.size()-1 && showLoader){
                viewHolder.loader.setVisibility(View.VISIBLE);
            }else viewHolder.loader.setVisibility(View.GONE);
        }catch (Exception e){
            Log.e("","Error onBindViewHolder: " + e.getMessage());
        }
    }
}