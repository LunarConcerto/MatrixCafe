package cafe.lunarconcerto.matrixcafe.api.db;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 数据库操作封装类
 */
public interface Database {

    /**
     * 直接运行某条SQL语句
     *
     * @param sqlConsumer 语句
     */
    void execute(Consumer<Connection> sqlConsumer);

    /**
     * 建表
     *
     * @param tableClass 对应该表的Javabean类
     */
    <T> void create(Class<T> tableClass);

    /**
     * 插入一条数据
     *
     * @param entity 数据
     * @param <T>    表类
     */
    <T> void insert(T entity);

    /**
     * 插入所有数据
     *
     * @param entities 数据
     * @param <T>      表类
     */
    <T> void insertAll(List<T> entities);

    /**
     * 插入所有数据
     *
     * @param entities 数据
     * @param <T>      表类
     */
    <T> void insertAll(T[] entities);

    /**
     * 删除一条数据，以ID作为索引
     *
     * @param tableClass 表类
     * @param id         索引
     * @param <T>        表类
     */
    <T> void remove(Class<T> tableClass, int id);

    /**
     * 删除一条数据，以ID作为索引
     *
     * @param tableClass 表类
     * @param id         索引
     * @param <T>        表类
     */
    <T> void remove(Class<T> tableClass, String id);

    /**
     * 更新一条数据
     *
     * @param entity 数据
     * @param <T>    表类
     */
    <T> void update(T entity);

    /**
     * 查找一条数据
     *
     * @param tableClass 表类
     * @param id         索引
     * @param <T>        表类
     * @return 查找到的数据
     */
    <T> Optional<T> select(Class<T> tableClass, int id);

    /**
     * 查找一条数据
     *
     * @param tableClass 表类
     * @param id         索引
     * @param <T>        表类
     * @return 查找到的数据
     */
    <T> Optional<T> select(Class<T> tableClass, String id);

    /**
     * 查找对应表的所有数据
     *
     * @param tableClass 表类
     * @param <T>        表类
     * @return 查找到的数据
     */
    <T> List<T> selectAll(Class<T> tableClass);

    /**
     * 查找对应表的所有数据并且分页
     *
     * @param tableClass 表类
     * @param page       页码
     * @param size       每页的规模
     * @param <T>        表类
     * @return 查找到的数据
     */
    <T> List<T> selectAll(Class<T> tableClass, int page, int size);

    /**
     * 判断某个类对应的表在数据库中是否存在
     *
     * @param tableClass 表类
     * @param <T>        表类
     * @return 某个类对应的表在数据库中是否存在
     */
    <T> boolean exist(Class<T> tableClass);

}
