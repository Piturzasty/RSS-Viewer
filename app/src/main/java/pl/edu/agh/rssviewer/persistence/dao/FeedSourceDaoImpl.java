package pl.edu.agh.rssviewer.persistence.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import pl.edu.agh.rssviewer.persistence.dao.base.FeedSourceDao;
import pl.edu.agh.rssviewer.persistence.model.FeedSource;

public class FeedSourceDaoImpl extends BaseDaoImpl<FeedSource, Long> implements FeedSourceDao {
    public FeedSourceDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, FeedSource.class);
    }
}
