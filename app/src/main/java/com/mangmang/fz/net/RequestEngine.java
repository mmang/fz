package com.mangmang.fz.net;

import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Response;
import retrofit2.Call;

/**
 * Created by mangmang on 2017/9/19.
 */

public class RequestEngine {

    static ApiService apiService;

    public static <T> void requestForGet(final Class<T> tClass) {

        final Call<Response> responseCall = apiService.requestForGet(null, null);
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                retrofit2.Response<Response> response = responseCall.execute();
                int code = response.code();
                String s = response.body().toString();
                Gson gson = new Gson();
                T t = gson.fromJson(s, tClass);
                e.onNext(t);
                e.onComplete();
            }
        });
    }
}
