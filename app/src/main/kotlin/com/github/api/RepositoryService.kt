package com.github.api

import com.github.app.homepage.model.entity.event.Repository
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Github API for Repository related operations
 */
interface RepositoryService {

    @GET("repos/{owner}/{repo}")
    fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Observable<Repository>
}
