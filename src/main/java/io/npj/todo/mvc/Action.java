package io.npj.todo.mvc;

import java.util.Map;
import java.util.Optional;

/**
 * Created by pbrindisi on 12/20/16.
 */
public interface Action {
	void process(Optional<Map<String, String>> params);
}
