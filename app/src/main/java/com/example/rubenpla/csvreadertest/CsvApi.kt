package com.example.rubenpla.csvreadertest

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface CsvApi {

    @GET("u/0/d/1cOAfpXY7nuc-Llo5UJl0UI-st5UOUgLqCQEfGoTSxco/export?format=csv")
    fun getCsv(): Flowable<String?>

/*    @GET("u/0/d/1cOAfpXY7nuc-Llo5UJl0UI-st5UOUgLqCQEfGoTSxco/export?format=csv")
    fun getCsvFromTUtorial(): List<Recipe> */

    companion object Factory {
        private const val BASE_URL: String = "https://docs.google.com/spreadsheets/"
        private const val ALTERNATIVE_URL  : String = "https://docs.google.com/a/google.com/spreadsheets/d/"

        fun getInstance(): CsvApi {

            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging)  // <-- this is the important line!

            val retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory
                            .createWithScheduler(Schedulers.io()))
                    .addConverterFactory(CsvConverterFactory())
                    .client(httpClient.build())
                    .build()

            return retrofit.create(CsvApi::class.java)
        }
    }
}