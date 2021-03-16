package io.github.yangyouwang.springbootstarterim.constant;

/**
 * 
 * @author yangyouwang
 * @Description: 发送消息的动作 枚举
 */
public enum MsgActionEnum {
	
	CONNECT(1, "第一次(或重连)初始化连接"),
	CHAT(2, "聊天消息"),	
	SIGNED(3, "消息签收"),
	KEEP_ALIVE(4, "客户端保持心跳");

	public final Integer TYPE;
	public final String CONTENT;

	MsgActionEnum(Integer TYPE, String CONTENT){
		this.TYPE = TYPE;
		this.CONTENT = CONTENT;
	}

	public static MsgActionEnum getEnum(Integer type) {
		MsgActionEnum[] msgActionEnums = values();
		for (MsgActionEnum msgActionEnum : msgActionEnums) {
			if (msgActionEnum.TYPE == type) {
				return msgActionEnum;
			}
		}
		return null;
	}

	public int getTYPE() {
		return TYPE;
	}

	public String getCONTENT() {
		return CONTENT;
	}
}
