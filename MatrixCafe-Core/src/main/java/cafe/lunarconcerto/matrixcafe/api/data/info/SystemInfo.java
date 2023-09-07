package cafe.lunarconcerto.matrixcafe.api.data.info;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.management.*;
import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/7/10
 */
@AllArgsConstructor
@NoArgsConstructor
public class SystemInfo {

    public static SystemInfo INSTANCE ;

    public static SystemInfo create(){
        if (INSTANCE != null) return INSTANCE ;

        INSTANCE = new SystemInfo();
        return INSTANCE;
    }

    /**
     * 项目启动时间
     */
    private long startTime  = System.currentTimeMillis();

    private MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    private MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

    private OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

    private RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();


    /**
     * 返回当前总计运行时间 (毫秒值)
     */
    public long totalRunningTimeMillis(){
        return System.currentTimeMillis() - startTime ;
    }

    /**
     * 返回当前已使用的内存比特数
     */
    public long usedMemoryBytes(){
        return heapMemoryUsage.getUsed();
    }

    /**
     * 返回格式化为Mb的已使用内存值
     */
    public String formattedUsedMemoryMB(){
        return usedMemoryBytes() / 1024 / 1024 + "MB";
    }

    /**
     * 返回当前可使用的最大内存比特数
     */
    public long maxMemoryBytes(){
        return heapMemoryUsage.getMax();
    }

    /**
     * 返回格式化为Mb的可使用最大内存值
     */
    public String formattedMaxMemoryMB(){
        return maxMemoryBytes() / 1024 / 1024 + "MB";
    }

    /**
     * 返回当前CPU占用数
     */
    public double cpuLoad(){
        return operatingSystemMXBean.getSystemLoadAverage();
    }


    /**
     * 返回当前虚拟机的名称
     */
    public String getJvmName(){
        return runtimeMXBean.getVmName();
    }

    /**
     * 返回当前虚拟机的版本
     */
    public String getJvmVersion(){
        return runtimeMXBean.getVmVersion();
    }

    /**
     * 返回当前虚拟机的运行参数
     */
    public List<String> getJvmArgument(){
        return runtimeMXBean.getInputArguments();
    }

}
