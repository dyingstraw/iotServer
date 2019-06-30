package util;

import com.sun.management.OperatingSystemMXBean;
import model.RunTimeStatus;

import java.lang.management.ManagementFactory;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-30 15:49
 **/
public class RuntimeUtil {
    public static RunTimeStatus getRunTimeStatus() {
        int kb = 1024;
        // 可使用内存
        long totalMemory = Runtime.getRuntime().totalMemory() / kb;
        // 剩余内存
        long freeMemory = Runtime.getRuntime().freeMemory() / kb;
        // 最大可使用内存
        long maxMemory = Runtime.getRuntime().maxMemory() / kb;

        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();

        // 操作系统
        String osName = System.getProperty("os.name");
        // 总的物理内存
        long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / kb;
        // 已使用的物理内存
        long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb
                .getFreePhysicalMemorySize())
                / kb;
        RunTimeStatus status = new RunTimeStatus();
        status.setFreeMemory(freeMemory);
        status.setCpuRatio(10);
        status.setMaxMemory(maxMemory);
        status.setOsName(osName);
        status.setFreePhysicalMemorySize(freePhysicalMemorySize);
        return status;
    }
}
