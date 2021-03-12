package io.github.yangyouwang.springbootstarterim.constant;


/**
 * 消息类型
 * @author yangyouwang
 */
public enum MsgTypeEnum {

    MSG_TYPE_TEXT(0, "文字"),
    MSG_TYPE_IMG(1, "图片");

    private int CODE;
    private String TYPE;

    MsgTypeEnum(int CODE, String TYPE) {
        this.CODE = CODE;
        this.TYPE = TYPE;
    }

    public static MsgTypeEnum getEnum(int CODE) {
        MsgTypeEnum[] msgTypeEnums = values();
        for (MsgTypeEnum msgTypeEnum : msgTypeEnums) {
            if (msgTypeEnum.CODE == CODE) {
                return msgTypeEnum;
            }
        }
        return null;
    }

    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
