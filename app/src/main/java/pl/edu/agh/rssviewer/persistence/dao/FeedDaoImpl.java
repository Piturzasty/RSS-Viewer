package pl.edu.agh.rssviewer.persistence.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import pl.edu.agh.rssviewer.persistence.dao.base.FeedDao;
import pl.edu.agh.rssviewer.persistence.model.Feed;

public class FeedDaoImpl extends BaseDaoImpl<Feed, Long> implements FeedDao {
    public FeedDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Feed.class);
    }
}
