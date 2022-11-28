package com.lindar.loqate.util;

import com.lindar.wellrested.WellRestedRequest;
import com.lindar.wellrested.vo.WellRestedResponse;

public class RequestFactory {
    public WellRestedResponse makeRequest(String path) {
        return WellRestedRequest.builder()
                                .url(path)
                                .build()
                                .post()
                                .jsonContent("") // this ensures 'Content-Length: 0' header gets added to the request
                                .submit();
    }
}
