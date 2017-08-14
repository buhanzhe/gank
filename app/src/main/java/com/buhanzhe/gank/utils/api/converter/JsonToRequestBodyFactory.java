package com.buhanzhe.gank.utils.api.converter;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by buhanzhe on 17/6/2.
 */

public class JsonToRequestBodyFactory extends Converter.Factory {

    public static final JsonToRequestBodyFactory INSTANCE = new JsonToRequestBodyFactory();

    public static JsonToRequestBodyFactory create() {
        return INSTANCE;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if(type == JSONObject.class){
            return JsonConverter.INSTANCE;
        }
        return null;
    }

    /**
     * 自定义Converter实现JSONObject到RequestBody的转换
     */
    public static class JsonConverter implements Converter<JSONObject, RequestBody> {

        public static final JsonConverter INSTANCE = new JsonConverter();

        @Override
        public RequestBody convert(JSONObject value) throws IOException {

            return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), value.toString());

        }
    }
}
