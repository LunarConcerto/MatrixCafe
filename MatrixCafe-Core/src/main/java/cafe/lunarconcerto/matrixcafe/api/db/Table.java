package cafe.lunarconcerto.matrixcafe.api.db;

import java.util.List;
import java.util.Optional;

public interface Table<T> {

    /**
     * 插入一条数据
     *
     * @param entity 对应的数据
     */
    void insert(T entity);

    /**
     * 插入所有数据
     *
     * @param entities 对应的所有数据
     */
    void insertAll(List<T> entities);

    /**
     * 删除一条数据
     *
     * @param id 索引
     */
    void remove(int id);

    /**
     * 删除一条数据
     *
     * @param id 索引
     */
    void remove(String id);

    /**
     * 更新一条数据
     *
     * @param entity 对应的数据
     */
    void update(T entity);

    /**
     * 查找一条数据
     *
     * @param id 索引
     * @return 查找到的数据
     */
    Optional<T> select(int id);

    /**
     * 查找一条数据
     *
     * @param id 索引
     * @return 查找到的数据
     */
    Optional<T> select(String id);

    /**
     * 查找所有数据
     *
     * @return 查找到的数据
     */
    List<T> selectAll();

    /**
     * 查找数据并且分页
     *
     * @param page 页码
     * @param size 每页规模
     * @return 查找到的数据
     */
    List<T> selectAll(int page, int size);

    /**
     * 该类对应的表在数据库中是否存在
     *
     * @return 对应的表在数据库中是否存在
     */
    boolean exist();
}
