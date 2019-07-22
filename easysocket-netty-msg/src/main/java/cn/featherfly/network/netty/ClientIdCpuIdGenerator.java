
package cn.featherfly.network.netty;

import java.io.IOException;
import java.io.InputStreamReader;

import cn.featherfly.common.lang.SystemPropertyUtils;
import cn.featherfly.common.lang.UUIDGenerator;

/**
 * <p>
 * ClientIdCpuIdGenerator
 * </p>
 *
 * @author zhongj
 */
public class ClientIdCpuIdGenerator implements ClientIdGenerator {

    private static String clientId = null;

    /**
     * <p>
     * 通过cpu序列号获取客户端id
     * </p>
     * 
     * @return ClientId
     */
    @Override
    public String getClientId() {
        if (clientId == null) {
            try {
                if (SystemPropertyUtils.isWindows()) {
                    clientId = getSnFromWindows();
                } else {
                    clientId = getSnFromLinuxPi();
                }
            } catch (IOException e) {
                e.printStackTrace();
                clientId = UUIDGenerator.generateUUID22Letters();
            }
        }
        return clientId;
    }

    private static String getSnFromWindows() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("wmic", "CPU", "get",
                "ProcessorID");
        Process process = builder.start();
        InputStreamReader reader = new InputStreamReader(
                process.getInputStream());
        char[] bs = new char[16];
        reader.skip(21);
        reader.read(bs);
        return new String(bs);
    }

    private static String getSnFromLinuxPi() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("cat", "/proc/cpuinfo", "|",
                "grep", "Serial", "|", "awk", "'{print $3}'");
        Process process = builder.start();
        InputStreamReader reader = new InputStreamReader(
                process.getInputStream());
        char[] bs = new char[32];
        reader.read(bs);
        return new String(bs);
    }
}
