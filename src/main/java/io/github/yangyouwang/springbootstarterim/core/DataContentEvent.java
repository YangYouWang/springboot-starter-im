package io.github.yangyouwang.springbootstarterim.core;

import io.github.yangyouwang.springbootstarterim.bean.DataContent;
import org.springframework.context.ApplicationEvent;

/**
 * @author yangyouwang
 * @title: DataContentEvent
 * @projectName springboot-starter-im
 * @description: 聊天数据事件
 * @date 2021/3/129:43 AM
 */
public class DataContentEvent extends ApplicationEvent {

    private DataContent dataContent;

    public DataContentEvent(Object source) {
        super(source);
    }

    public DataContent getDataContent() {
        return dataContent;
    }

    public void setDataContent(DataContent dataContent) {
        this.dataContent = dataContent;
    }
}
