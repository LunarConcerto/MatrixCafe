package cafe.lunarconcerto.matrixcafe.api.data.message;

/**
 * 表示一条消息被响应的状态
 */
public enum MessageResult {

    /**
     * 表示消息被成功地响应.
     */
    SUCCESS,

    /**
     * 表示消息被忽略.
     */
    IGNORE,

    /**
     * 表示该消息的响应由于某种原因失败了.
     */
    FAILURE

}
