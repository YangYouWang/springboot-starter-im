package io.github.yangyouwang.springbootstarterim.strategy;

import io.github.yangyouwang.springbootstarterim.bean.DataContent;

/**
 * @author yangyouwang
 * @title: MsgStrategy
 * @projectName springboot-starter-im
 * @description: 消息策略
 * @date 2021/3/159:04 AM
 */
public interface MsgStrategy {

    public void doAction(DataContent dataContent);
}
