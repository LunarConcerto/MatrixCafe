package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.db.Database;
import cafe.lunarconcerto.matrixcafe.api.config.DatabaseConnectionInfo;
import cafe.lunarconcerto.matrixcafe.api.config.DatabaseType;
import cafe.lunarconcerto.matrixcafe.api.config.MatrixCafeConfiguration;
import com.google.inject.Singleton;
import com.mysql.cj.jdbc.Driver;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.pager.Pager;
import org.sqlite.JDBC;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Singleton
public class MatrixCafeDatabase implements Database {

    private Dao daoImplements ;

    private DataSource dataSource ;

    @Inject
    public MatrixCafeDatabase(MatrixCafeConfiguration configuration) {
        init(configuration);
    }

    private void init(@NotNull MatrixCafeConfiguration configuration){
        DatabaseConnectionInfo dbInfo = configuration.getDb();

        HikariDataSource dataSource = new HikariDataSource();

        String driverClassname = getDriverClassname(dbInfo.getType());
        if (driverClassname==null){
            return;
        }
        dataSource.setDriverClassName(driverClassname);

        dataSource.setJdbcUrl(dbInfo.getUrl());
        dataSource.setUsername(dbInfo.getUsername());
        dataSource.setPassword(dbInfo.getPassword());

        this.dataSource = dataSource ;
        daoImplements = new NutDao(dataSource);
    }

    private @Nullable String getDriverClassname(@NotNull DatabaseType type){
        switch (type) {
            case MYSQL -> {
                return Driver.class.getName() ;
            }
            case SQLITE -> {
                return JDBC.class.getName() ;
            }
            default -> {
                return null ;
            }
        }
    }

    @Override
    public void execute(@NotNull Consumer<Connection> sqlConsumer) {
        daoImplements.run(sqlConsumer::accept);
    }

    @Override
    public <T> void create(Class<T> tableClass) {
        daoImplements.create(tableClass, false);
    }

    @Override
    public <T> void insert(T entity) {
        daoImplements.insert(entity);
    }

    @Override
    public <T> void insertAll(List<T> entities) {
        daoImplements.insert(entities);
    }

    @Override
    public <T> void insertAll(T[] entities) {
        daoImplements.insert(entities);
    }

    @Override
    public <T> void remove(Class<T> tableClass, int id) {
        daoImplements.delete(tableClass, id);
    }

    @Override
    public <T> void remove(Class<T> tableClass, String id) {
        daoImplements.delete(tableClass, id);
    }

    @Override
    public <T> void update(T entity) {
        daoImplements.update(entity);
    }

    @Override
    public <T> Optional<T> select(Class<T> tableClass, int id) {
        return Optional.of(daoImplements.fetch(tableClass, id));
    }

    @Override
    public <T> Optional<T> select(Class<T> tableClass, String id) {
        return Optional.of(daoImplements.fetch(tableClass, id));
    }

    @Override
    public <T> List<T> selectAll(Class<T> tableClass) {
        return daoImplements.query(tableClass, null);
    }

    @Override
    public <T> List<T> selectAll(Class<T> tableClass, int page, int size) {
        return daoImplements.query(tableClass, null, new Pager(page, size));
    }

    @Override
    public <T> boolean exist(Class<T> tableClass) {
        return daoImplements.exists(tableClass);
    }

}
