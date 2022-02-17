package com.lindar.loqate;

import com.lindar.loqate.util.RequestFactory;

import static com.lindar.loqate.util.LoqateConstants.BASE_URL;

public class LoqateClient {

    private final AddressVerificationResource addressVerificationResource;
    private final EmailVerificationResource   emailVerificationResource;

    private LoqateClient(String key, String baseUrl, RequestFactory requestFactory) {
        this.addressVerificationResource = new AddressVerificationResource(key, baseUrl, requestFactory);
        this.emailVerificationResource = new EmailVerificationResource(key, baseUrl, requestFactory);
    }

    public AddressVerificationResource addressVerification() {
        return addressVerificationResource;
    }

    public EmailVerificationResource emailVerification() {
        return emailVerificationResource;
    }

    public static LoqateClient of(String key) {
        return new LoqateClient(key, BASE_URL, new RequestFactory());
    }

    public static LoqateClient of(String key, String baseUrl) {
        return new LoqateClient(key, baseUrl, new RequestFactory());
    }

    public static LoqateClient of(String key, RequestFactory requestFactory) {
        return new LoqateClient(key, BASE_URL, requestFactory);
    }
}
