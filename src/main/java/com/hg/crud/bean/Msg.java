package com.hg.crud.bean;

import java.util.HashMap;
import java.util.Map;

public class Msg {

	// ״̬��
	private int code;
	// ��ʾ��Ϣ
	private String msg;

	// �û�Ҫ���ظ������������
	private Map<String, Object> extend = new HashMap<String, Object>();
	
	public static Msg success() {
		Msg res = new Msg();
		res.setCode(200);
		res.setMsg("����ɹ���");
		return res;
	}
	
	public static Msg fail() {
		Msg res = new Msg();
		res.setCode(100);
		res.setMsg("����ʧ�ܣ�");
		return res;
	}
	
	public Msg add(String key,Object value) {
		this.getExtend().put(key, value);
		return this;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getExtend() {
		return extend;
	}

	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
}
