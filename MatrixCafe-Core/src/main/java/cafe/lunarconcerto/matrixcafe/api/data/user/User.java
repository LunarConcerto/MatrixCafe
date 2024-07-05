package cafe.lunarconcerto.matrixcafe.api.data.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 持久化实体,
 * <p>
 * 当发送者使用过MatrixCafe的服务时, 应标记为用户,
 * 此外当发送者尝试使用需要权限的服务时, 应从此查找其权限.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * 产生该用户的适配器ID
     */
    private  String adapter;

    /**
     * 该用户的id
     */
    private  String id;

    /**
     * 该用户的昵称
     */
    private  String nickname;

    /**
     * 该用户的权限级别
     */
    private  Authorization auth;

    /**
     * 该用户的性别
     */
    private  Sex sex;


}
