
package cn.featherfly.network;

import java.util.Locale;

/**
 * <p>
 * CodecExceptionCode
 * </p>
 * 
 * @author zhongj
 */
public class CodecExceptionCode extends cn.featherfly.common.exception.SimpleLocalizedExceptionCode {

    private static final String MODULE = "EASYSOCKET";
    
    /**
     * @param num num
     * @param key key
     */
    public CodecExceptionCode(Integer num, String key) {
        this(num, key, new Object[] {});
    }
    /**
     * @param num num
     * @param key key
     * @param argus argus
     */
    public CodecExceptionCode(Integer num, String key, Object[] argus) {
        this(num, key, null, argus);
    }
    
    /**
     * @param num num
     * @param key  key
     * @param locale locale
     */
    public CodecExceptionCode(Integer num, String key, Locale locale) {
        this(num, key, locale, new Object[]{});
    }
    
    /**
     * @param num num
     * @param key  key
     * @param locale locale
     * @param argus argus
     */
    public CodecExceptionCode(Integer num, String key, Locale locale, Object[] argus) {
        super(MODULE, num, key, locale, argus);
    }
    
    public enum CodecExceptionCodes {
        
        EASYSOCKET10000("message_type_register_error", 10000),
        EASYSOCKET10001("key_already_registered_for_serializer", 10001),
        EASYSOCKET10002("key_already_registered_for_type", 10002),
        EASYSOCKET10003("serialize_error", 10003),
        EASYSOCKET10004("deserialize_error", 10004),
        EASYSOCKET10005("not_registered_message_structure_key", 10005),
        EASYSOCKET10006("not_registered_message_type_key", 10006),
        EASYSOCKET10007("not_registered_message_type", 10007),
        EASYSOCKET10008("not_implements_message_structure_deserialize", 10008);
                
        private String key;
        
        private Integer num;
        
        /**
         * @param key key
         * @param num num
         */
        private CodecExceptionCodes(String key, Integer num) {
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
     * create MessageTypeRegisterErrorCode
     * </p>
     * 
     * @return MessageTypeRegisterErrorCode
     */
    public static CodecExceptionCode createMessageTypeRegisterErrorCode(
        ) {
        return new CodecExceptionCode(CodecExceptionCodes.EASYSOCKET10000.num
                , CodecExceptionCodes.EASYSOCKET10000.key);
    }
    
    /**
     * <p>
     * create KeyAlreadyRegisteredForSerializerCode
     * </p>
     * @param key key
     * @param serializerName serializerName
     * 
     * @return KeyAlreadyRegisteredForSerializerCode
     */
    public static CodecExceptionCode createKeyAlreadyRegisteredForSerializerCode(
        java.lang.Byte key, java.lang.String serializerName) {
        return new CodecExceptionCode(CodecExceptionCodes.EASYSOCKET10001.num
                , CodecExceptionCodes.EASYSOCKET10001.key
                , new Object[] {key, serializerName});
    }
    
    /**
     * <p>
     * create KeyAlreadyRegisteredForTypeCode
     * </p>
     * @param key key
     * @param messageType messageType
     * 
     * @return KeyAlreadyRegisteredForTypeCode
     */
    public static CodecExceptionCode createKeyAlreadyRegisteredForTypeCode(
        java.lang.Integer key, java.lang.String messageType) {
        return new CodecExceptionCode(CodecExceptionCodes.EASYSOCKET10002.num
                , CodecExceptionCodes.EASYSOCKET10002.key
                , new Object[] {key, messageType});
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
    public static CodecExceptionCode createSerializeErrorCode(
        java.lang.String className, java.lang.String errorMessage) {
        return new CodecExceptionCode(CodecExceptionCodes.EASYSOCKET10003.num
                , CodecExceptionCodes.EASYSOCKET10003.key
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
    public static CodecExceptionCode createDeserializeErrorCode(
        java.lang.String className, java.lang.String errorMessage) {
        return new CodecExceptionCode(CodecExceptionCodes.EASYSOCKET10004.num
                , CodecExceptionCodes.EASYSOCKET10004.key
                , new Object[] {className, errorMessage});
    }
    
    /**
     * <p>
     * create NotRegisteredMessageStructureKeyCode
     * </p>
     * @param key key
     * 
     * @return NotRegisteredMessageStructureKeyCode
     */
    public static CodecExceptionCode createNotRegisteredMessageStructureKeyCode(
        java.lang.Integer key) {
        return new CodecExceptionCode(CodecExceptionCodes.EASYSOCKET10005.num
                , CodecExceptionCodes.EASYSOCKET10005.key
                , new Object[] {key});
    }
    
    /**
     * <p>
     * create NotRegisteredMessageTypeKeyCode
     * </p>
     * @param key key
     * 
     * @return NotRegisteredMessageTypeKeyCode
     */
    public static CodecExceptionCode createNotRegisteredMessageTypeKeyCode(
        java.lang.Integer key) {
        return new CodecExceptionCode(CodecExceptionCodes.EASYSOCKET10006.num
                , CodecExceptionCodes.EASYSOCKET10006.key
                , new Object[] {key});
    }
    
    /**
     * <p>
     * create NotRegisteredMessageTypeCode
     * </p>
     * @param typeName typeName
     * 
     * @return NotRegisteredMessageTypeCode
     */
    public static CodecExceptionCode createNotRegisteredMessageTypeCode(
        java.lang.String typeName) {
        return new CodecExceptionCode(CodecExceptionCodes.EASYSOCKET10007.num
                , CodecExceptionCodes.EASYSOCKET10007.key
                , new Object[] {typeName});
    }
    
    /**
     * <p>
     * create NotImplementsMessageStructureDeserializeCode
     * </p>
     * @param structure structure
     * 
     * @return NotImplementsMessageStructureDeserializeCode
     */
    public static CodecExceptionCode createNotImplementsMessageStructureDeserializeCode(
        java.lang.String structure) {
        return new CodecExceptionCode(CodecExceptionCodes.EASYSOCKET10008.num
                , CodecExceptionCodes.EASYSOCKET10008.key
                , new Object[] {structure});
    }
    
}
