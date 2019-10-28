
package cn.featherfly.network;

/**
 * <p>
 * CodecException
 * </p>
 * 
 * @author zhongj
 */
public class CodecException extends cn.featherfly.common.exception.LocalizedCodeException{

    private static final long serialVersionUID = -1;

    
    /**
     * @param exceptionCode exceptionCode
     */
    public CodecException(CodecExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    /**
     * @param exceptionCode exceptionCode
     * @param ex ex
     */
    public CodecException(CodecExceptionCode exceptionCode, Throwable ex) {
        super(exceptionCode, ex);
    }
}
