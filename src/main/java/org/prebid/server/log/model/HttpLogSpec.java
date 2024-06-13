package org.prebid.server.log.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class HttpLogSpec {

    Endpoint endpoint;

    Integer statusCode;

    String account;

    String bidder;

    Boolean debug;

    int limit;

    public enum Endpoint {
        auction, amp
    }
}
