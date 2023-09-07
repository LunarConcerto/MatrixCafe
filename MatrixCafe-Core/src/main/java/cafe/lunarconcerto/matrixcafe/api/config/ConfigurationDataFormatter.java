package cafe.lunarconcerto.matrixcafe.api.config;

import java.io.File;

/**
 * <h1>配置文件数据格式化器</h1>
 * 该接口的实现类对象用于将{@link Configuration}标注的配置对象格式化成为文本.
 */
public interface ConfigurationDataFormatter {

    /**
     * 将某对象序列化为字符串
     * @param target 任意目标对象
     * @return 目标对象序列化而成的字符串
     */
    String serialize(Object target);

    <T> T deserialize(String data, Class<T> type);

    <T> T deserialize(File data, Class<T> type);

}
