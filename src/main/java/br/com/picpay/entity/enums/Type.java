package br.com.picpay.entity.enums;

import br.com.picpay.entity.OwnerType;

public enum Type {

    USER(1L, "USER"), MERCHANT(2L, "MERCHANT");

    private Long id;
    private String type;

    Type(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public OwnerType toOwnerType() {
        return new OwnerType(id, type);
    }
}
