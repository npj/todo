package io.npj.todo.mvc;

import io.npj.todo.TodoApp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by pbrindisi on 12/16/16.
 */
public abstract class Controller {
	private static Map<Class<? extends Controller>, Controller> controllers = new HashMap<>();

	public static <C extends Controller> Optional<C> get(Class<C> controllerClass) {
		return Optional.ofNullable((C)controllers.computeIfAbsent(controllerClass, k -> instantiate(controllerClass)));
	}

	private static <C extends Controller> C instantiate(Class<C> controllerClass) {
		try {
			return controllerClass.newInstance();
		} catch(InstantiationException | IllegalAccessException e) {
			TodoApp.logException(e);
			return null;
		}
	}

	private final String name;

	public Controller(String name) {
		this.name = name;
	}
}
