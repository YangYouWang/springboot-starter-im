package io.github.yangyouwang.springbootstarterim.bean;


import java.io.Serializable;

/**
 * 聊天bean
 * @author yangyouwang
 */
public class ChatMsg implements Serializable {

    /** 发送者id **/
    private Long fromUserId;

    /** 接收者id **/
    private Long toUserId;

    /** 消息内容 **/
    private String message;
    /**
     * 类型 0：文字 1：图片
     */
    private Integer type = 0;
    /**
     * 状态 0：未读 1：已读
     */
    private Integer status = 0 ;

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
