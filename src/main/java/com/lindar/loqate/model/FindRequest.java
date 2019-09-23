package com.lindar.loqate.model;


import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data @Builder
public class FindRequest {
    @Builder.Default
    private String       text         = "";
    @Builder.Default
    private String       container    = "";
    @Builder.Default
    private String       origin       = "";
    @Builder.Default
    private List<String> countries    = new ArrayList<>();
    @Builder.Default
    private int          limit        = 10;
    @Builder.Default
    private String       language     = "en-gb";
    @Builder.Default
    private boolean      isMiddleware = true;
    private List<String> fields;
}
