package pl.edu.agh.rssviewer.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import pl.edu.agh.rssviewer.persistence.model.Feed;
import pl.edu.agh.rssviewer.persistence.model.FeedSource;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "rss.db";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Feed.class);
            TableUtils.createTable(connectionSource, FeedSource.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Feed.class, true);
            TableUtils.dropTable(connectionSource, FeedSource.class, true);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
