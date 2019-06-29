package config;

public enum Key {
    AUTH("auth"),PUBLISH("publish"),LOGINSUCCESS("login");

    private String value;
    Key(String value) {
        this.value =value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
