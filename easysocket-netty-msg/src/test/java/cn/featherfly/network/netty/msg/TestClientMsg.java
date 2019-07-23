
package cn.featherfly.network.netty.msg;

import cn.featherfly.common.lang.ArrayUtils;

/**
 * <p>
 * TestClientMsg
 * </p>
 *
 * @author zhongj
 */
public class TestClientMsg extends ClientMsg {

    public static void p(short s) {
        byte[] bs = toByteArray(s);
        System.out.println(ArrayUtils.toString(bs));
        System.out.println(toShort(bs));
    }

    public static void main(String[] args) {
        p(Short.MAX_VALUE);
        p((short) 1234);
        p(Short.MIN_VALUE);

    }

    private static byte[] toByteArray(short iSource) {
        byte[] bLocalArr = new byte[Short.BYTES];
        for (int i = 0; i < 4 && i < Short.BYTES; i++) {
            bLocalArr[i] = (byte) (iSource >> 8 * i & 0xFF);
        }
        return bLocalArr;
    }

    public static short toShort(byte[] bRefArr) {
        short iOutcome = 0;
        byte bLoop;

        for (int i = 0; i < bRefArr.length; i++) {
            bLoop = bRefArr[i];
            iOutcome += (bLoop & 0xFF) << 8 * i;
        }
        return iOutcome;
    }

    private String message;

    /**
     * 返回message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置message
     *
     * @param message message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TestClientMsg [message=" + message + ", token=" + token + ", secrecy=" + secrecy + ", id=" + id + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClientId() {
        // YUFEI_TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClientId(String clientId) {
        // YUFEI_TODO Auto-generated method stub

    }

}
