package io.npj.todo.lists;

import io.npj.todo.DbTestCase;
import io.npj.todo.mvc.Model;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Optional;

/**
 * Created by pbrindisi on 12/23/16.
 *
 * Problem: we would like to use a test database, but DB.getInstance() is a static method
 * that deals with one database configuration. When calls to commit() are made,
 * actual database writes will occur which could affect the state of other tests. With the
 * original v1.0 code, it is impossible to test this method in complete isolation.
 *
 * Solution: remove the evil DB.getInstance() method and use dependency injection to manage the singleton
 * DB instance, and provide it to instances that need it.
 *
 * Steps:
 * 1) Enable annotation processing in IntelliJ:
 * Preferences... -> Build, Execution, Deployment -> Compiler -> Annotation Processors
 * Check "Enable annotation processing" and "Obtain processors from project classpath."
 * Update build.gradle to use the gradle apt plugin to pick up code-generated types in IntelliJ.
 *
 * 2) Drop the singleton pattern from the DB class. We generally want to avoid non-pure static methods.
 * getInstance() is non-pure because it modifies some external state (the instance static member).
 *
 * 3) Create a TodoModule class as a DI module and provide a singleton DB instance. To do this,
 * we will have the providing method return an Optional<DB> in case the constructor throws an
 * exception.
 *
 * 4) Now we need to inject DB into the places that need it, namely the service classes. This involves
 * making a private DB field in the Service super class, and a constructor which takes a DB as a
 * parameter. Then we'll add constructors to ListService and ItemService and annotate them with @Inject.
 *
 * 5) Next, since we're injecting the DB into ItemService and ListService, we'll need to inject those
 * into places that need them -- namely ListsController and ItemsController.
 *
 * 6) Since the Controller class manages controller instantiation, we'll need to give it access
 * to the injected controller instances. To do this, we create a Todo component to be the
 * root of the dependency tree, and update the Controllers class to use controllers returned by the
 * component.
 *
 * Now that we've changed ListService by adding a constructor that takes a DB as a parameter, we
 * can simply instantiate the ListService under test with a testing-specific database.
 */
public class ListServiceTest extends DbTestCase {
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