package cafe.lunarconcerto.matrixcafe.api.web.http;

import java.util.HashMap;
import java.util.Map;

public enum HttpStatus {

    UNDEFINED(-1),

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Informational Response
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    CONTINUE(100),

    SWITCHING_PROTOCOLS(101),

    PROCESSING(102),

    EARLY_HINTS(103),

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Success
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    OK(200),

    CREATED(201),

    ACCEPTED(202),

    NON_AUTHORITATIVE_INFORMATION(203),

    NO_CONTENT(204),

    RESET_CONTENT(205),

    PARTIAL_CONTENT(206),

    MULTI_STATUS(207),

    ALREADY_REPORTED(208),

    IM_USED(226),

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Redirection
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    MULTIPLE_CHOICES(300),

    MOVED_PERMANENTLY(301),

    FOUND(302),

    SEE_OTHER(303),

    NOT_MODIFIED(304),

    USE_PROXY(305),

    SWITCH_PROXY(306),

    TEMPORARY_REDIRECT(307),

    PERMANENT_REDIRECT(308),

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Client Errors
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    BAD_REQUEST(400),

    UNAUTHORIZED(401),

    PAYMENT_REQUIRED(402),

    FORBIDDEN(403),

    NOT_FOUND(404),

    METHOD_NOT_ALLOWED(405),

    NOT_ACCEPTABLE(406),

    PROXY_AUTHENTICATION_REQUIRED(407),

    REQUEST_TIMEOUT(408),

    CONFLICT(409),

    GONE(410),

    LENGTH_REQUIRED(411),

    PRECONDITION_FAILED(412),

    PAYLOAD_TOO_LARGE(413),

    URI_TOO_LONG(414),

    UNSUPPORTED_MEDIA_TYPE(415),

    RANGE_NOT_SATISFIABLE(416),

    EXPECTATION_FAILED(417),

    I_AM_A_TEAPOT(418),

    MISDIRECTED_REQUEST(421),

    UNPROCESSABLE_ENTITY(422),

    LOCKED(423),

    FAILED_DEPENDENCY(424),

    TOO_EARLY(425),

    UPGRADE_REQUIRED(426),

    PRECONDITION_REQUIRED(428),

    TOO_MANY_REQUESTS(429),

    REQUEST_HEADER_FIELDS_TOO_LARGE(431),

    UNAVAILABLE_FOR_LEGAL_REASONS(451),

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Server Errors
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    INTERNAL_SERVER_ERROR(500),

    NOT_IMPLEMENTED(501),

    BAD_GATEWAY(502),

    SERVICE_UNAVAILABLE(503),

    GATEWAY_TIMEOUT(504),

    HTTP_VERSION_NOT_SUPPORTED(505),

    VARIANT_ALSO_NEGOTIATES(506),

    INSUFFICIENT_STORAGE(507),

    LOOP_DETECTED(508),

    NOT_EXTENDED(510),

    NETWORK_AUTHENTICATION_REQUIRED(511);

    private final int code ;

    HttpStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private static final Map<Integer, HttpStatus> codeToStatusMap;

    static {
        codeToStatusMap = new HashMap<>();
        for (HttpStatus status : HttpStatus.values()) {
            codeToStatusMap.put(status.getCode(), status);
        }
    }

    /**
     * 返回状态码对应的枚举值
     * @param code 状态码
     * @return 对应的枚举值
     */
    public static HttpStatus valueOf(int code) {
        HttpStatus status = codeToStatusMap.get(code);
        return status == null ? UNDEFINED : status ;
    }

    /**
     * 判断状态是否为成功
     */
    public boolean isInformationalResponse() {
        return this.code >= 100 && this.code < 200;
    }

    /**
     * 判断状态是否为成功
     */
    public boolean isSuccess() {
        return this.code >= 200 && this.code < 300;
    }

    /**
     * 判断状态是否为重定向
     */
    public boolean isRedirection() {
        return this.code >= 300 && this.code < 400;
    }

    /**
     * 判断状态是否为客户端错误
     */
    public boolean isClientError() {
        return this.code >= 400 && this.code < 500;
    }

    /**
     * 判断状态是否为服务器错误
     */
    public boolean isServerError() {
        return this.code >= 500 && this.code < 600;
    }
}
