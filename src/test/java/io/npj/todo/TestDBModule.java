package io.npj.todo;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Optional;

@Module
public class TestDBModule {
    @Provides @Singleton static Optional<DB> provideDB() {
        try {
            DB db = DB.getInstance();
            db.setConnectionUri("jdbc:sqlite:");
            return Optional.of(db);
        } catch(SQLException | DB.DataFileException e) {
            return Optional.empty();
        }
    }
}

