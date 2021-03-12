package io.github.yangyouwang.springbootstarterim.bean;

import java.io.Serializable;

/**
 * 聊天数据
 *
 * @author yangyouwang
 */
public class DataContent implements Serializable {
    /**
     * 动作类型
     */
    private Integer action;
    /**
     * 消息内容
     */
    private ChatMsg chatMsg;

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public ChatMsg getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMsg chatMsg) {
        this.chatMsg = chatMsg;
    }

    @Override
    public String toString() {
        return "DataContent{" +
                "action=" + action +
                ", chatMsg=" + chatMsg +
                '}';
    }
}
