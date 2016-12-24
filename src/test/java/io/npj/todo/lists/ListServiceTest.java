package io.npj.todo.lists;

import junit.framework.TestCase;

/**
 * Created by pbrindisi on 12/23/16.
 */
public class ListServiceTest extends TestCase {
	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {

	}

	public void testFetchOne() throws Exception {
		/*
			Given an id of a list in the database, verify that the correct data is retrieved and returned in
			in a ListModel instance.

			Problem: we would like to use a test database, but DB.getInstance() is a static method
			that one deals with one database configuration. When calls to commit() are made,
			actual database writes will occur which could affect the state of other tests. With the
			original v1.0 code, it is impossible to test this method in complete isolation.

			Solution: use dependency injection to set up a singleton database dependency which
			uses a "production" configuration when running, and a "test" configuration when executing
			tests.

			Steps:
			1) Enable annotation processing in IntelliJ:
			   Preferences... -> Build, Execution, Deployment -> Compiler -> Annotation Processors
			   Check "Enable annotation processing" and "Obtain processors from project classpath."
			   Update build.gradle to use the gradle apt plugin
			2) Drop the singleton pattern from the DB class. We generally want to avoid non-pure static methods.
			   getInstance() is non-pure because it modifies some external state (the instance static member).
			3) Create a TodoModule class as a DI module and provide a singleton DB instance. To do this,
			   we will have the providing method return an Optional<DB> in case the constructor throws an
			   exception.
			4) Now we need to inject DB into the places that need it, namely the service classes. This involves
			   making a private DB field in the Service super class, and a constructor which takes a DB as a
			   parameter. Then we'll add constructors to ListService and ItemService and annotate them with @Inject.
			5) Next, since we're injecting the DB into ItemService and ListService, we'll need to inject those
			   into places that need them -- namely ListsController and ItemsController.
			6) Lastly, since the Controller class manages controller instantiation, we'll need to give it access
			   to the injected controller instances. To do this, we create a TodoControllers component to be the
			   root of the dependency tree, and update the Controllers class to use controllers returned by the
			   component.
		 */
	}

	public void testFetchAll() throws Exception {

	}

	public void testCreate() throws Exception {

	}

	public void testDelete() throws Exception {

	}

}