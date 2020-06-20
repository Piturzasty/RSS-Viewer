package pl.edu.agh.rssviewer.di;

import android.app.Application;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import dagger.Module;
import dagger.Provides;
import pl.edu.agh.rssviewer.persistence.DatabaseHelper;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.persistence.repository.FeedSourceRepository;

@Module
class DatabaseModule {
    @Provides
    ConnectionSource providesConnectionSource(Application application) {
        return new AndroidConnectionSource(new DatabaseHelper(application.getApplicationContext()));
    }

    @Provides
    FeedRepository providesFeedRepository(ConnectionSource connectionSource) {
        try {
            return new FeedRepository(connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Provides
    FeedSourceRepository providesFeedSourceRepository(ConnectionSource connectionSource) {
        try {
            return new FeedSourceRepository(connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
