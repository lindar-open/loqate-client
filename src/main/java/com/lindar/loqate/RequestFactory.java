package com.lindar.loqate;

import com.lindar.wellrested.WellRestedRequest;
import com.lindar.wellrested.vo.WellRestedResponse;

class RequestFactory {
    WellRestedResponse makeRequest(String path) {
        return WellRestedRequest.builder().url(path).build().post().submit();
    }
}
