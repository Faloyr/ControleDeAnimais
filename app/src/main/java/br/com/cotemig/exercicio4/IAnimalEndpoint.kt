package br.com.cotemig.exercicio4

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IAnimalEndpoint {
    @GET("animal")
    fun get(): Call<ArrayList<AnimalModel>>

    @GET("animal/{id}")
    fun getById(@Path("id")id: Int): Call<AnimalModel>

    @PUT("animal/{id}")
    fun put(@Path("id")id: Int, @Body obj: AnimalModel): Call<AnimalModel>

    @DELETE("animal/{id}")
    fun delete(@Path("id") id: Int): Call<Void>

    @POST("animal")
    fun post(@Body obj: AnimalModel): Call<AnimalModel>

}