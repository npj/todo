package io.npj.todo;

import io.npj.todo.mvc.Action;

import java.util.Map;
import java.util.Optional;

/**
 * Created by pbrindisi on 12/20/16.
 */
public class Loop {
	private static Loop instance;

	public static Loop getInstance() {
		if (instance == null) {
			instance = new Loop();
		}
		return instance;
	}

	private boolean stopped = false;

	private Action nextAction;
	private Optional<Map<String, String>> nextParams;

	private Loop() { }

	void run() {
		while (!stopped) {
			processAction();
		}
	}

	public void onNext(Action action, Optional<Map<String, String>> params) {
		nextAction = action;
		nextParams = params;
	}

	private void processAction() {
		if (nextAction == null) {
			stopped = true;
		} else {
			Action action = nextAction;
			Optional<Map<String, String>> params = nextParams;

			nextAction = null;
			nextParams = null;
			action.process(params);
		}
	}
}
