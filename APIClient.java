package com.example.abalalimoghaddam.jsonlistdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIClient {
     @GET("/users/{user}/repos")
     Call<List<GithubRepo>> reposForUser(
        @Path("user") String user
     );
}
