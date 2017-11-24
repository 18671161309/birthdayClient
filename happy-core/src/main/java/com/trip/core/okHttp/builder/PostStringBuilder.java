package com.trip.core.okHttp.builder;



import com.trip.core.okHttp.request.PostStringRequest;
import com.trip.core.okHttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by Administrator on 2017/3/10.
 */

public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder> {


    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }

}
