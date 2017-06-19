package com.franktan.githubusers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.franktan.githubusers.network.GithubUserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class UserListActivity : AppCompatActivity() {
    private val GITHUB_BASE_URL = "https://api.github.com/"
    private val GITHUB_ID_BEFORE_ME = "3487860";
    private val TAG = "UserListActivity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_layout)

        val retrofit = Retrofit.Builder()
                .baseUrl(GITHUB_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<GithubUserService>(GithubUserService::class.java)
        service.fetchUsersSince(GITHUB_ID_BEFORE_ME)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ users ->
                            for (user in users.orEmpty()) {
                                Log.e(TAG, user.toString())
                            }},
                        { error -> Log.e(TAG, "error", error) },
                        { Log.e(TAG, "Complete") })

    }
}
