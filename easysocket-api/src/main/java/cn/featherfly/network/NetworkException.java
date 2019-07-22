
package cn.featherfly.network;

/**
 * <p>
 * NetworkException
 * </p>
 * 
 * @author zhongj
 */
public class NetworkException extends cn.featherfly.common.exception.LocalizedCodeException{

    private static final long serialVersionUID = -1;

    
    /**
     * @param exceptionCode exceptionCode
     */
    public NetworkException(NetworkExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    /**
     * @param exceptionCode exceptionCode
     * @param ex ex
     */
    public NetworkException(NetworkExceptionCode exceptionCode, Throwable ex) {
        super(exceptionCode, ex);
    }
}
