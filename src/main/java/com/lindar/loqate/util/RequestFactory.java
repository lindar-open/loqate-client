package com.lindar.loqate.util;

import com.lindar.wellrested.WellRestedRequest;
import com.lindar.wellrested.vo.WellRestedResponse;

public class RequestFactory {
    public WellRestedResponse makeRequest(String path) {
        return WellRestedRequest.builder().url(path).build().post().submit();
    }
}
