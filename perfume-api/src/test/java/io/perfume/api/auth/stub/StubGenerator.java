package io.perfume.api.auth.stub;

import generator.Generator;

public class StubGenerator implements Generator {

    private String code;

    @Override
    public String generate(int length) {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void clear() {
        this.code = null;
    }
}
