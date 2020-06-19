package pl.edu.agh.rssviewer.persistence.repository;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import pl.edu.agh.rssviewer.persistence.dao.FeedSourceDaoImpl;
import pl.edu.agh.rssviewer.persistence.model.FeedSource;
import pl.edu.agh.rssviewer.persistence.repository.base.SqLiteRepository;

public class FeedSourceRepository extends SqLiteRepository<FeedSource, Long> {
    public FeedSourceRepository(ConnectionSource connectionSource) throws SQLException {
        super(new FeedSourceDaoImpl(connectionSource));
    }
}
