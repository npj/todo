package io.npj.todo.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Created by npj on 12/13/16.
 */
public class Console {
	private static final int CONSOLE_WIDTH = 80;

	private final PrintStream out = System.out;
	private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private String header;
	private String body;
	private String footer;

	public void setHeader(String header) {
		this.header = header;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public void write() {
		out.println(header);
		printSeparator();
		out.println(body);
		printSeparator();
		out.println(footer);
		out.flush();
		header = null;
		body = null;
		footer = null;
	}

	private void printSeparator() {
		for (int i = 0; i < CONSOLE_WIDTH; ++i) {
			out.print("-");
		}
		out.println("");
	}

	public String readLine(String prompt) throws IOException {
		out.print("> ");
		out.flush();
		return in.readLine().trim();
	}

	public void flash(String msg) {
		out.print(msg);
		out.println("");
		out.flush();
	}

	public void pageBreak() {
		out.println("\n\n");
	}
}
