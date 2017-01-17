package io.npj.todo.lists;

import io.npj.todo.FunctionalTestCase;
import io.npj.todo.mvc.Model;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Optional;

public class ListServiceTest extends FunctionalTestCase {
	ListService listService;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		listService = new ListService(Optional.of(testDB));
	}

	/*
	 * Given an id of a list in the database, verify that the correct data is retrieved and returned in
	 * in a ListModel instance.
	 */
	public void testFetchOne() throws Exception {
		final String sql = "INSERT INTO todo_lists (name, created_at, updated_at) VALUES (?, ?, ?)";
		final String name = "Test List";
		final Date createdAt = new Date();
		final Date updatedAt = new Date();
		PreparedStatement stmt = testDB.getConnection().prepareStatement(sql);

		stmt.setString(1, name);
		stmt.setString(2, Model.unparseDate(createdAt));
		stmt.setString(3, Model.unparseDate(updatedAt));

		stmt.execute();

		int lastId = getLastInsertId(stmt);

		ListModel model = listService.fetchOne(lastId);
		assertEquals(lastId, model.getId());
		assertEquals(name, model.getName());
		assertEquals(Model.unparseDate(createdAt), Model.unparseDate(model.getCreatedAt()));
		assertEquals(Model.unparseDate(updatedAt), Model.unparseDate(model.getUpdatedAt()));
	}
}