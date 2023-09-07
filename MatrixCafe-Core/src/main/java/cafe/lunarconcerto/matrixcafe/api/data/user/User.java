package cafe.lunarconcerto.matrixcafe.api.data.user;

/**
 * 持久化实体,
 * <p>
 * 当发送者使用过MatrixCafe的服务时, 应标记为用户,
 * 此外当发送者尝试使用需要权限的服务时, 应从此查找其权限.
 * @param adapter 产生该用户的适配器
 * @param id 该用户的id
 * @param nickname 该用户的昵称
 * @param auth 该用户的权限级别
 * @param sex 该用户的性别
 */
public record User(

        String adapter,

        String id,

        String nickname,

        Authorization auth,

        Sex sex

) {

}
