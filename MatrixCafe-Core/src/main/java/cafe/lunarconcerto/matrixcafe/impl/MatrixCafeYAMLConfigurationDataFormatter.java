package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.config.ConfigurationDataFormatter;
import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class MatrixCafeYAMLConfigurationDataFormatter implements ConfigurationDataFormatter {

    private final ObjectMapper yamlMapper = new YAMLMapper();

    @Override
    public String serialize(Object target) {
        try {
            return yamlMapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            log.error("序列化时发生异常 :", e);
            log.error("类型 {} 的对象 {} 的序列化失败.", target.getClass().getName(), target);
        }
        return Strings.EMPTY ;
    }

    @Override
    public <T> T deserialize(String data, Class<T> type) {
        try {
            return yamlMapper.readValue(data, type);
        } catch (JsonProcessingException e) {
            log.error("反序列化时发生异常 :", e);
            log.error("对类型 {} 的反序列化失败. 序列化数据为 : {}", type.getName(), data);
        }
        return null ;
    }

    @Override
    public <T> T deserialize(File data, Class<T> type) {
        try {
            return yamlMapper.readValue(data, type);
        } catch (IOException e) {
            log.error("反序列化时发生异常 :", e);
            log.error("对类型 {} 的反序列化失败. 文件目标为 : {}", type.getName(), data);
        }
        return null ;
    }

}
