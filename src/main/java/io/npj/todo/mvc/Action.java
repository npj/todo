package io.npj.todo.mvc;

import java.util.Map;
import java.util.Optional;

public interface Action {
	void process(Optional<Map<String, String>> params);
}
