package com.lindar.loqate;

import com.lindar.loqate.util.RequestFactory;

public class LoqateClient {

    private final AddressVerificationResource addressVerificationResource;
    private final EmailVerificationResource   emailVerificationResource;

    private LoqateClient(String key, RequestFactory requestFactory) {
        this.addressVerificationResource = new AddressVerificationResource(requestFactory, key);
        this.emailVerificationResource = new EmailVerificationResource(requestFactory, key);
    }

    public AddressVerificationResource addressVerification() {
        return addressVerificationResource;
    }

    public EmailVerificationResource emailVerification() {
        return emailVerificationResource;
    }

    public static LoqateClient of(String key) {
        return new LoqateClient(key, new RequestFactory());
    }

    public static LoqateClient of(String key, RequestFactory requestFactory) {
        return new LoqateClient(key, requestFactory);
    }
}
