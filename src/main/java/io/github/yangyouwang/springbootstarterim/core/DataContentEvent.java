package io.github.yangyouwang.springbootstarterim.core;

import io.github.yangyouwang.springbootstarterim.constant.MsgActionEnum;
import io.github.yangyouwang.springbootstarterim.constant.MsgStatusEnum;
import io.github.yangyouwang.springbootstarterim.constant.MsgTypeEnum;
import io.github.yangyouwang.springbootstarterim.utils.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

/**
 * @author yangyouwang
 * @title: DataContentEvent
 * @projectName springboot-starter-im
 * @description: 聊天数据事件
 * @date 2021/3/129:43 AM
 */
public class DataContentEvent extends ApplicationEvent {

    /** 发送者id **/
    private Long fromUserId;

    /** 接收者id **/
    private Long toUserId;

    /** 消息内容 **/
    private String message;
    /**
     * 动作类型
     */
    private MsgActionEnum action;
    /**
     * 类型 0：文字 1：图片
     */
    private MsgTypeEnum type;
    /**
     * 状态 0：未读 1：已读
     */
    private MsgStatusEnum status;

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

    public MsgActionEnum getAction() {
        return action;
    }

    public void setAction(MsgActionEnum action) {
        this.action = action;
    }

    public MsgTypeEnum getType() {
        return type;
    }

    public void setType(MsgTypeEnum type) {
        this.type = type;
    }

    public MsgStatusEnum getStatus() {
        return status;
    }

    public void setStatus(MsgStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DataContentEvent{" +
                "fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", message='" + message + '\'' +
                ", action=" + action +
                ", type=" + type +
                ", status=" + status +
                '}';
    }

    private static ApplicationContext applicationContext = SpringUtil.getApplicationContext();

    private static class SingletonClassInstance {
        private static final DataContentEvent instance = new DataContentEvent(applicationContext);
    }

    private DataContentEvent(Object source) {
        super(source);
    }

    public static DataContentEvent getInstance(){
        return SingletonClassInstance.instance;
    }
}
