package org.owl.web.common;


import java.lang.reflect.Method;

import org.owl.web.common.RequestMapping.RequestMethod;

public class MethodInfo {
	// 메서드를 실행하는 컨트롤러의 인스턴스
	private Object controller = null;
	// request method
	private RequestMapping.RequestMethod requestMethod = null;
	private Method method = null;

	public MethodInfo(Object controller, RequestMethod requestMethod,
			Method method) {
		this.controller = controller;
		this.requestMethod = requestMethod;
		this.method = method;
	}

	public Object getController() {
		return controller;
	}

	public RequestMapping.RequestMethod getRequestMethod() {
		return requestMethod;
	}

	public Method getMethod() {
		return method;
	}
}