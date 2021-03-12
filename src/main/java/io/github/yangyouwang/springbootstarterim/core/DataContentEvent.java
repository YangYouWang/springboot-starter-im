package io.github.yangyouwang.springbootstarterim.core;

import io.github.yangyouwang.springbootstarterim.bean.DataContent;
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

    private DataContent dataContent;

    private static ApplicationContext applicationContext = SpringUtil.getApplicationContext();

    private static class SingletonClassInstance {
        private static final DataContentEvent instance = new DataContentEvent(DataContentEvent.applicationContext);
    }

    private DataContentEvent(Object source) {
        super(source);
    }

    public static DataContentEvent getInstance(){
        return SingletonClassInstance.instance;
    }

    public DataContent getDataContent() {
        return dataContent;
    }

    public void setDataContent(DataContent dataContent) {
        this.dataContent = dataContent;
    }
}
