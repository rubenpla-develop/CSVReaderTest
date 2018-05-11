package com.example.rubenpla.csvreadertest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Converter;

public class CsvConverterFactory extends Converter.Factory{
    public CsvConverterJava fromResponseBody(Type type, Annotation[] annotations) {
        return new CsvConverterJava(type);
    }
}