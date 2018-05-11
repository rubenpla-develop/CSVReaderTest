package com.example.rubenpla.csvreadertest

import io.reactivex.Flowable
import okhttp3.ResponseBody

class CsvRepository(private val apiService : CsvApi?) {

    fun getCsv() : Flowable<String?>? {
         return apiService?.getCsv()
    }
}