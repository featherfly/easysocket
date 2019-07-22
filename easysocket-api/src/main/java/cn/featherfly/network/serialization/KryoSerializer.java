
package cn.featherfly.network.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import cn.featherfly.network.NetworkException;
import cn.featherfly.network.NetworkExceptionCode;

/**
 * <p>
 * KryoSerializer
 * </p>
 * 
 * @author zhongj
 */
public class KryoSerializer implements Serializer {

    private Kryo kryo;

    /**
     */
    public KryoSerializer() {
        kryo = new Kryo();
    }

    /**
     * @param kryo
     */
    protected KryoSerializer(Kryo kryo) {
        super();
        this.kryo = kryo;
    }

    /**
     * 返回kryo
     * 
     * @return kryo
     */
    public Kryo getKryo() {
        return kryo;
    }

    /**
     * 设置kryo
     * 
     * @param kryo
     *            kryo
     */
    public void setKryo(Kryo kryo) {
        this.kryo = kryo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <O> byte[] serialize(O obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                Output output = new Output(bos)) {
            kryo.writeObject(output, obj);
            output.flush();
            return bos.toByteArray();
        } catch (Exception e) {
            throw new NetworkException(
                    NetworkExceptionCode.createSerializeErrorCode(
                            obj.getClass().getName(), e.getLocalizedMessage()),
                    e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <O> O deserialize(byte[] bytes, Class<O> type) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                Input input = new Input(bais)) {
            return kryo.readObject(input, type);
        } catch (Exception e) {
            throw new NetworkException(
                    NetworkExceptionCode.createDeserializeErrorCode(
                            type.getName(), e.getLocalizedMessage()),
                    e);
        }
    }

}
