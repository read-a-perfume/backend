package io.perfume.api.user.stub;

import encryptor.OneWayEncryptor;

public class StubEncryptor implements OneWayEncryptor {

    @Override
    public String hash(String plainText) {
        return plainText;
    }
}
