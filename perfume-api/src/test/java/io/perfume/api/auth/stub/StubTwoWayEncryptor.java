package io.perfume.api.auth.stub;

import encryptor.TwoWayEncryptor;

public class StubTwoWayEncryptor implements TwoWayEncryptor {

    @Override
    public String encrypt(String plainText) throws Exception {
        return plainText + "encrypted";
    }

    @Override
    public String decrypt(String encryptedText) throws Exception {
        return encryptedText;
    }
}
