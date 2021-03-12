package io.github.yangyouwang.springbootstarterim.constant;


/**
 * 消息状态
 * @author yangyouwang
 */
public enum MsgStatusEnum {

    MSG_STATUS_UNREAD(0, "未读"),
    MSG_STATUS_HAVE_READ(1, "已读");

    public final int CODE;
    public final String TYPE;

    MsgStatusEnum(int CODE, String TYPE) {
        this.CODE = CODE;
        this.TYPE = TYPE;
    }

    public static String getType(int CODE) {
        MsgStatusEnum[] msgTypeEnums = values();
        for (MsgStatusEnum msgTypeEnum : msgTypeEnums) {
            if (msgTypeEnum.CODE == CODE) {
                return msgTypeEnum.TYPE;
            }
        }
        return null;
    }

    public int getCODE() {
        return CODE;
    }

    public String getTYPE() {
        return TYPE;
    }
}
