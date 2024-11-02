package br.com.picpay.entity.enums;

public enum OwnerType {

    USER(1), MERCHANT(2);

    private int value;

    OwnerType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
