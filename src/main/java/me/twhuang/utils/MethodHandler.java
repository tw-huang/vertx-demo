package me.twhuang.utils;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * 方法结构
 */
@Setter
@Getter
public class MethodHandler {

	private String name;
	private Method method;
}
