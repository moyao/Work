package com.guansu.management.api;
import com.guansu.management.config.HttpConstants;
import com.guansu.management.utils.ParameterizedTypeImpl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.BodyRequest;
import com.lzy.okgo.request.base.Request;
import com.lzy.okrx2.adapter.ObservableBody;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by dongyaoyao
 */
public class ApiWrapper {

    public static <T> Observable<T> request(HttpMethod method, String url, Type type) {
        return request(method, url, type, null);
    }

    public static <T> Observable<T> request(HttpMethod method, String url, Type type, HttpParams params) {
        return request(method, url, type, params, CacheMode.DEFAULT);
    }
    public static <T> Observable<T> babyrequest(HttpMethod method, String url, Type type, Map<String,Object> params) {
        System.out.println("+++++"+params);
        return babyrequest(method, url, type, params, CacheMode.DEFAULT);
    }
    public static <T> Observable<T> request(HttpMethod method, String url, Type type, HttpParams params, CacheMode cacheMode) {
        Request<T, ? extends Request> request;
        url = HttpConstants.BASE_URL + url;
        if (method == HttpMethod.GET) request = OkGo.get(url);
        else if (method == HttpMethod.POST) request = OkGo.post(url);
        else if (method == HttpMethod.PUT) request = OkGo.put(url);
        else if (method == HttpMethod.DELETE) request = OkGo.delete(url);
        else if (method == HttpMethod.HEAD) request = OkGo.head(url);
        else if (method == HttpMethod.PATCH) request = OkGo.patch(url);
        else if (method == HttpMethod.OPTIONS) request = OkGo.options(url);
        else if (method == HttpMethod.TRACE) request = OkGo.trace(url);
        else request = OkGo.get(url);
        request.cacheKey(url+"?"+params.toString());
        request.cacheMode(cacheMode);
        if (null == params) {
            params =new HttpParams();
        }
        request.params(params);
        Type typeAll = new ParameterizedTypeImpl(CommonResponse.class, new Type[]{type});
        request.converter(new JsonConvert<T>(typeAll));
        return request.adapt(new ObservableBody<T>()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static <T> Observable<T> babyrequest(HttpMethod method, String url, Type type, Map<String,Object> params, CacheMode cacheMode) {
        BodyRequest<T, ? extends BodyRequest> request = null;
        url = HttpConstants.BASE_URL + url;
         if (method == HttpMethod.POST) request = OkGo.post(url);
        else if (method == HttpMethod.PUT) request = OkGo.put(url);
        else if (method == HttpMethod.DELETE) request = OkGo.delete(url);
        else if (method == HttpMethod.PATCH) request = OkGo.patch(url);
        else if (method == HttpMethod.OPTIONS) request = OkGo.options(url);
        request.cacheKey(url+"?"+params.toString());
        request.cacheMode(cacheMode);
        if (null == params) {
            params =new HashMap();
        }
        JSONObject jsonObject=new JSONObject(params);
        request.upJson(jsonObject);
        Type typeAll = new ParameterizedTypeImpl(CommonResponse.class, new Type[]{type});
        request.converter(new JsonConvert<T>(typeAll));
        return request.adapt(new ObservableBody<T>()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
