package com.example.abalalimoghaddam.jsonlistdemo;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.abalalimoghaddam.jsonlistdemo.R.id.repoTextView;

public class MainActivity extends AppCompatActivity {

    private static final String GITHUB_BASE_URL = "https://api.github.com/";
    private static final String LOG_TAG = "Github List";
    private GridView repoList;
    int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orientation = this.getResources().getConfiguration().orientation;
        repoList = (GridView) findViewById(R.id.repoGridView);

        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            repoList.setNumColumns(4);
        } else {
            repoList.setNumColumns(2);
        }

        repoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),""+i,Toast.LENGTH_SHORT).show();
            }
        });

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(GITHUB_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder.client(
                        httpClient.build()
                ).build();

        APIClient client =  retrofit.create(APIClient.class);

        // Fetch a list of the Github repositories.
        Call<List<GithubRepo>> call = client.reposForUser("arasbm");

        Log.i(LOG_TAG, "Attempting to load list of repositories ... ");

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                // The network call was a success and we got a response
                // TODO: use the repository list and display it
                for (GithubRepo repo:response.body()) {
                    String repoName = repo.getName().toString();
                    Log.i(LOG_TAG, "repo: " + repoName);
                }

                RepoAdapter adapter = new RepoAdapter(getApplicationContext(), response.body());
                repoList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }
}
