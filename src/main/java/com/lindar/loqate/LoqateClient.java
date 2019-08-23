package com.lindar.loqate;

public class LoqateClient {

    private final AddressVerificationResource addressVerificationResource;

    private LoqateClient(String key, RequestFactory requestFactory){
        this.addressVerificationResource = new AddressVerificationResource(requestFactory, key);
    }

    public AddressVerificationResource addressVerification(){
        return addressVerificationResource;
    }

    public static LoqateClient of(String key) {
        return new LoqateClient(key, new RequestFactory());
    }
    public static LoqateClient of(String key, RequestFactory requestFactory) {
        return new LoqateClient(key, requestFactory);
    }
}
