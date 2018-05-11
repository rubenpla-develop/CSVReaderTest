package com.example.rubenpla.csvreadertest

import okhttp3.ResponseBody
import retrofit2.Converter

class CustomConverter : Converter<ResponseBody, String> {


    override fun convert(value: ResponseBody?): String {
        //var customList : List<CustomModel> = ArrayList()
        var rawResponse : String = value.toString().replace("\"","")

        //Log.d("Converter", "Response result : $rawResponse")

        return rawResponse
    }
}