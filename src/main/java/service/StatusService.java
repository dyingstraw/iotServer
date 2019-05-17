package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-05-12 14:37
 **/
public class StatusService {
    private int connectionCount;
    private int aliveConnection;
    private long statisticBytesReceive;
    private long statisticBytesSend;
    private long runningTime;
    private int period =2000;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    public final static StatusService INSTANCE = new StatusService();

    private StatusService() {
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        Runnable task = new Runnable() {
            public void run() {
                runningTime++;
                // logger.info(INSTANCE.toString());
            }
        };
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(task,0,2000, TimeUnit.MILLISECONDS);
    }

    public synchronized int increaseAliveConnection(int num){
        this.aliveConnection+=num;
        return aliveConnection;
    }
    public synchronized int decreaseAliveConnection(int num){
        this.aliveConnection-=num;
        return aliveConnection;
    }

    public synchronized int increaseConnections(int num){
        this.connectionCount+=num;
        return connectionCount;
    }
    public synchronized int decreaseConnections(int num){
        this.connectionCount-=num;
        return connectionCount;
    }
    public synchronized long increaseStatisticBytesReceive(long num){
        this.statisticBytesReceive+=num;
        return statisticBytesReceive;
    }
    public synchronized long decreaseStatisticBytesReceive(long num){
        this.statisticBytesReceive-=num;
        return statisticBytesReceive;
    }

    public synchronized long increaseStatisticBytesSend(long num){
        this.statisticBytesSend+=num;
        return statisticBytesSend;
    }
    public synchronized long decreaseStatisticBytesSend(long num){
        this.statisticBytesSend-=num;
        return statisticBytesSend;
    }

    public ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() {
        return scheduledThreadPoolExecutor;
    }

    public void setScheduledThreadPoolExecutor(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        this.scheduledThreadPoolExecutor = scheduledThreadPoolExecutor;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(int connectionCount) {
        this.connectionCount = connectionCount;
    }

    public int getAliveConnection() {
        return aliveConnection;
    }

    public void setAliveConnection(int aliveConnection) {
        this.aliveConnection = aliveConnection;
    }

    public long getStatisticBytesReceive() {
        return statisticBytesReceive;
    }

    public void setStatisticBytesReceive(long statisticBytesReceive) {
        this.statisticBytesReceive = statisticBytesReceive;
    }

    public long getStatisticBytesSend() {
        return statisticBytesSend;
    }

    public void setStatisticBytesSend(long statisticBytesSend) {
        this.statisticBytesSend = statisticBytesSend;
    }

    public long getRunningTime() {
        return runningTime*period;
    }

    public void setRunningTime(long runningTime) {
        this.runningTime = runningTime;
    }

    @Override
    public String toString() {
        return "ServerStatus{" +
                "connectionCount=" + connectionCount +
                ", aliveConnection=" + aliveConnection +
                ", statisticBytesReceive=" + statisticBytesReceive +
                ", statisticBytesSend=" + statisticBytesSend +
                ", runningTime=" + runningTime*period +
                '}';
    }
}
