package io.npj.todo.mvc;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class Model {
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date parseDate(String updatedAt) {
		try {
			return dateFormatter.parse(updatedAt);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String unparseDate(Date date) {
		return dateFormatter.format(date);
	}

	private int id = 0;
	private Date createdAt = null;
	private Date updatedAt = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
