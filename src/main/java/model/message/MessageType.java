package model.message;

public enum  MessageType {
    VERSION_01(1,"v1"),
    VERSION_02(2,"v2"),
    LOGIN(0,"login"),



    REDIRECT(3,"Redirect"),
    SEND(1,"send"),
    RESP(9,"resp"),
    CONTROL(8,"ctrl");

    private Integer key;
    private String value;
    MessageType(Integer key,String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
