package com.example.abalalimoghaddam.jsonlistdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.resource;

/**
 * Created by abalalimoghaddam on 4/3/2017.
 */

public class RepoAdapter extends ArrayAdapter {

    private Context context;
    private List<GithubRepo> repos;

    public RepoAdapter(Context context, List<GithubRepo> repos) {
        super(context, R.layout.repo_item, repos);

        this.repos = repos;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            row = inflator.inflate(R.layout.repo_item, parent, false);
        }
        TextView textView = (TextView)row.findViewById(R.id.repoTextView);
        ImageView repoIcon = (ImageView) row.findViewById(R.id.repoIcon);

        GithubRepo item = repos.get(position);
        textView.setText(item.getName());
        repoIcon.setImageResource(R.mipmap.ic_launcher);

        return row;
    }
}





