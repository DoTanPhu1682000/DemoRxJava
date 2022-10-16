package com.dotanphu.demorxjava.network

import com.dotanphu.demorxjava.model.ListItems
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET

interface ItemsService {

    @GET("repositories?q=newyork/")
    fun getAllItemsWithRx(): Observable<ListItems>

}