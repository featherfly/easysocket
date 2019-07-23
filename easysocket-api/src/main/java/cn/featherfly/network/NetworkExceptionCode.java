
package cn.featherfly.network;

import java.util.Locale;

/**
 * <p>
 * NetworkExceptionCode
 * </p>
 * 
 * @author zhongj
 */
public class NetworkExceptionCode extends cn.featherfly.common.exception.SimpleLocalizedExceptionCode {

    private static final String MODULE = "EASYSOCKET";
    
    /**
     * @param num num
     * @param key key
     * @param argus argus
     */
    public NetworkExceptionCode(Integer num, String key) {
        this(num, key, new Object[] {});
    }
    /**
     * @param num num
     * @param key key
     * @param argus argus
     */
    public NetworkExceptionCode(Integer num, String key, Object[] argus) {
        this(num, key, null, argus);
    }
    
    /**
     * @param num num
     * @param key  key
     * @param locale locale
     * @param argus argus
     */
    public NetworkExceptionCode(Integer num, String key, Locale locale) {
        this(num, key, locale, new Object[]{});
    }
    
    /**
     * @param num num
     * @param key  key
     * @param locale locale
     * @param argus argus
     */
    public NetworkExceptionCode(Integer num, String key, Locale locale, Object[] argus) {
        super(MODULE, num, key, locale, argus);
    }
   
    
    /**
     * <p>
     * create NoRegisteredClientCode
     * </p>
     * @param clientId clientId
     * 
     * @return NoRegisteredClientCode
     */
    public static NetworkExceptionCode createNoRegisteredClientCode(java.lang.String clientId) {
        return new NetworkExceptionCode(10000, "no_registered_client",
                new Object[] {clientId});
    }
    
    /**
     * <p>
     * create ConnectFailureCode
     * </p>
     * @param networkAddress networkAddress
     * 
     * @return ConnectFailureCode
     */
    public static NetworkExceptionCode createConnectFailureCode(cn.featherfly.network.NetworkAddress networkAddress) {
        return new NetworkExceptionCode(10001, "connect_failure",
                new Object[] {networkAddress});
    }
    
    /**
     * <p>
     * create NotConnectedCode
     * </p>
     * @param networkAddress networkAddress
     * 
     * @return NotConnectedCode
     */
    public static NetworkExceptionCode createNotConnectedCode(cn.featherfly.network.NetworkAddress networkAddress) {
        return new NetworkExceptionCode(10002, "not_connected",
                new Object[] {networkAddress});
    }
    
    /**
     * <p>
     * create SerializeErrorCode
     * </p>
     * @param className className
     * @param errorMessage errorMessage
     * 
     * @return SerializeErrorCode
     */
    public static NetworkExceptionCode createSerializeErrorCode(java.lang.String className, java.lang.String errorMessage) {
        return new NetworkExceptionCode(10003, "serialize_error",
                new Object[] {className, errorMessage});
    }
    
    /**
     * <p>
     * create DeserializeErrorCode
     * </p>
     * @param className className
     * @param errorMessage errorMessage
     * 
     * @return DeserializeErrorCode
     */
    public static NetworkExceptionCode createDeserializeErrorCode(java.lang.String className, java.lang.String errorMessage) {
        return new NetworkExceptionCode(10004, "deserialize_error",
                new Object[] {className, errorMessage});
    }
    
}
