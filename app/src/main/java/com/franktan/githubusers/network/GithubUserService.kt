package com.franktan.githubusers.network

import com.franktan.githubusers.domain.GithubUser
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by frank.tan on 6/19/17.
 */

interface GithubUserService {
    @GET("users")
    fun fetchUsersSince(@Query("userId") userId: String): Observable<List<GithubUser>>
}
