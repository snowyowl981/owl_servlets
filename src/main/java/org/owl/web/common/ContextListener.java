package org.owl.web.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.reflections.Reflections;

public class ContextListener implements ServletContextListener {

	private ServletContext context = null;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 컨텍스트 초기화
		context = event.getServletContext();
		String basePackage = context.getInitParameter("basePackage");

		// Controller의 메서드들을 담는 맵
		Map<String, MethodInfo> invokeMap = new HashMap<>();
		Reflections reflections = new Reflections(basePackage);
		// @Controller annotated 클래스들 목록
		for (Class<? extends Object> controllerClass : reflections
				.getTypesAnnotatedWith(Controller.class)) {
			try {
				Object controllerInstance = controllerClass
						.getDeclaredConstructor().newInstance();
				// 컨트롤러의 메서드 목록
				Method[] methods = controllerClass.getDeclaredMethods();
				for (Method method : methods) {
					// @RequestMapping annotation
					RequestMapping requestMapping = method
							.getDeclaredAnnotation(RequestMapping.class);
					if (requestMapping != null) {
						if (invokeMap.containsKey(requestMapping.value())) {
							new ServletException(
									requestMapping.value() + " is duplicated.");
						}
						invokeMap.put(requestMapping.value(),
								new MethodInfo(controllerInstance,
										requestMapping.method(), method));
						System.out.println(
								"RequestMapping : " + requestMapping.value()
										+ " => " + controllerClass.getName()
										+ "." + method.getName());
					}
				}
				context.setAttribute("invokeMap", invokeMap);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		System.out.println("메서드 매핑이 완료되었습니다.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		context = event.getServletContext();
		context.removeAttribute("invokeMap");
		System.out.println("컨텍스트가 종료되었습니다.");
	}
}