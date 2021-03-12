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
	KEEP_ALIVE(4, "客户端保持心跳"),
	PULL_FRIEND(5, "拉取好友");
	
	public final int TYPE;
	public final String CONTENT;
	
	MsgActionEnum(Integer TYPE, String CONTENT){
		this.TYPE = TYPE;
		this.CONTENT = CONTENT;
	}

	public static String getContent(int type) {
		MsgActionEnum[] msgActionEnums = values();
		for (MsgActionEnum msgActionEnum : msgActionEnums) {
			if (msgActionEnum.TYPE == type) {
				return msgActionEnum.CONTENT;
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
