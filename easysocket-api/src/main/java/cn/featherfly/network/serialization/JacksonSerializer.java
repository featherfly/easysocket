
package cn.featherfly.network.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.featherfly.network.NetworkException;
import cn.featherfly.network.NetworkExceptionCode;

/**
 * <p>
 * JacksonSerializer
 * </p>
 * 
 * @author zhongj
 */
public class JacksonSerializer implements Serializer {

    private ObjectMapper mapper;

    /**
     */
    public JacksonSerializer() {
        mapper = new ObjectMapper();
    }

    /**
     * @param mapper
     */
    protected JacksonSerializer(ObjectMapper mapper) {
        super();
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <O> byte[] serialize(O obj) {
        try {
            return mapper.writerFor(obj.getClass()).writeValueAsBytes(obj);
        } catch (Exception e) {
            throw new NetworkException(
                    NetworkExceptionCode.createSerializeErrorCode(
                            obj.getClass().getName(), e.getLocalizedMessage()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <O> O deserialize(byte[] bytes, Class<O> type) {
        try {
            return mapper.readerFor(type).readValue(bytes);
        } catch (Exception e) {
            throw new NetworkException(
                    NetworkExceptionCode.createDeserializeErrorCode(
                            type.getName(), e.getLocalizedMessage()),
                    e);
        }
    }

}
