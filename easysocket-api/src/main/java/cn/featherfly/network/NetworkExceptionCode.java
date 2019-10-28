
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
    
    public enum NetworkExceptionCodes {
        
        EASYSOCKET10000("no_registered_client", 10000),
        EASYSOCKET10001("connect_failure", 10001),
        EASYSOCKET10002("not_connected", 10002),
        EASYSOCKET10003("serialize_error", 10003),
        EASYSOCKET10004("deserialize_error", 10004),
        EASYSOCKET10005("message_type_register_error", 10005);
                
        private String key;
        
        private Integer num;
        
        /**
         * @param key key
         * @param num num
         */
        private NetworkExceptionCodes(String key, Integer num) {
            this.key = key;
            this.num = num;
        }
        /**
         * get key
         * @return key
         */
        public String getKey() {
            return key;
        }
        /**
         * get num
         * @return num
         */
        public Integer getNum() {
            return num;
        }
    }
   
    
    /**
     * <p>
     * create NoRegisteredClientCode
     * </p>
     * @param clientId clientId
     * 
     * @return NoRegisteredClientCode
     */
    public static NetworkExceptionCode createNoRegisteredClientCode(
        java.lang.String clientId) {
        return new NetworkExceptionCode(NetworkExceptionCodes.EASYSOCKET10000.num
                , NetworkExceptionCodes.EASYSOCKET10000.key
                , new Object[] {clientId});
    }
    
    /**
     * <p>
     * create ConnectFailureCode
     * </p>
     * @param networkAddress networkAddress
     * 
     * @return ConnectFailureCode
     */
    public static NetworkExceptionCode createConnectFailureCode(
        cn.featherfly.network.NetworkAddress networkAddress) {
        return new NetworkExceptionCode(NetworkExceptionCodes.EASYSOCKET10001.num
                , NetworkExceptionCodes.EASYSOCKET10001.key
                , new Object[] {networkAddress});
    }
    
    /**
     * <p>
     * create NotConnectedCode
     * </p>
     * @param networkAddress networkAddress
     * 
     * @return NotConnectedCode
     */
    public static NetworkExceptionCode createNotConnectedCode(
        cn.featherfly.network.NetworkAddress networkAddress) {
        return new NetworkExceptionCode(NetworkExceptionCodes.EASYSOCKET10002.num
                , NetworkExceptionCodes.EASYSOCKET10002.key
                , new Object[] {networkAddress});
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
    public static NetworkExceptionCode createSerializeErrorCode(
        java.lang.String className, java.lang.String errorMessage) {
        return new NetworkExceptionCode(NetworkExceptionCodes.EASYSOCKET10003.num
                , NetworkExceptionCodes.EASYSOCKET10003.key
                , new Object[] {className, errorMessage});
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
    public static NetworkExceptionCode createDeserializeErrorCode(
        java.lang.String className, java.lang.String errorMessage) {
        return new NetworkExceptionCode(NetworkExceptionCodes.EASYSOCKET10004.num
                , NetworkExceptionCodes.EASYSOCKET10004.key
                , new Object[] {className, errorMessage});
    }
    
    /**
     * <p>
     * create MessageTypeRegisterErrorCode
     * </p>
     * 
     * @return MessageTypeRegisterErrorCode
     */
    public static NetworkExceptionCode createMessageTypeRegisterErrorCode(
        ) {
        return new NetworkExceptionCode(NetworkExceptionCodes.EASYSOCKET10005.num
                , NetworkExceptionCodes.EASYSOCKET10005.key);
    }
    
}
