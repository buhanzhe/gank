package com.buhanzhe.gank.utils.api.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by buhanzhe on 17/6/2.
 */

public class ResponseBodyToTypeFactory extends Converter.Factory {
    public static ResponseBodyToTypeFactory create() {
        return create(new Gson());
    }

    public static ResponseBodyToTypeFactory create(Gson gson) {
        return new ResponseBodyToTypeFactory(gson);
    }
    private final Gson gson;

    private ResponseBodyToTypeFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new TypeConverter<>(gson, adapter);
    }
    class TypeConverter<T> implements Converter<ResponseBody,T> {

        private final Gson gson;
        private final TypeAdapter<T> adapter;

        TypeConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {

            JsonReader jsonReader = gson.newJsonReader(value.charStream());
            try {
                return adapter.read(jsonReader);
            } finally {
                value.close();
            }
        }
    }
}
