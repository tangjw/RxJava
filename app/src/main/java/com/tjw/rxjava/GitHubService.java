package com.tjw.rxjava;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * CopyRight
 * Created by tang-jw on 2016/9/7.
 */
public interface GitHubService {
	
	@GET("/repos/{owner}/{repo}/contributors")
	Call<List<Contributor>> contributors(
			@Path("owner") String owner,
			@Path("repo") String repo);
}
