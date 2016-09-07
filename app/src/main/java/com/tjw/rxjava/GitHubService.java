package com.tjw.rxjava;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * CopyRight
 * Created by tang-jw on 2016/9/7.
 */
public interface GitHubService {
	
	@GET("/repos/{owner}/{repo}/contributors")
	Call<List<Contributor>> contributors(
			@Path("owner") String owner,
			@Path("repo") String repo);
	
	@GET("/repos/{owner}/{repo}/contributors")
	Observable<List<Contributor>> contributor(
			@Path("owner") String owner,
			@Path("repo") String repo);
}
