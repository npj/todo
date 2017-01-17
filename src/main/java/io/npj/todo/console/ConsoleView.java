package io.npj.todo.console;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class ConsoleView {
	protected final Console console = new Console();

	protected final List<CommandListener> commandListeners = new ArrayList<>();

	public interface CommandListener {
		void dispatchCommand(Pair<String, String> command) throws CommandException;

		default void handleCommand(String command) throws CommandException {
			dispatchCommand(parseCommand(command));
		};

		default Pair<String,String> parseCommand(String command) {
			String[] parts = command.split("\\s+", 2);
			return new ImmutablePair<>(parts[0].toLowerCase(), parts.length == 2 ? parts[1] : null);
		}
	}

	public void addCommandListener(CommandListener handler) {
		commandListeners.add(handler);
	}

	public void sendCommand(String command) {
		commandListeners.forEach(listener -> {
			try {
				listener.handleCommand(command);
			} catch (CommandException e) {
				console.flash(e.getMessage());
			}
		});
	}

	public static class CommandException extends Exception {
		public CommandException(String message) {
			super(message);
		}
	}
}
