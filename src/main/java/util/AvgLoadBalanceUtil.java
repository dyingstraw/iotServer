package util;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program:
 * @description:加权平滑负载均衡
 * @author: dyingstraw
 * @create: 2019-07-05 10:01
 **/
public class AvgLoadBalanceUtil {

    @Data
    class LoadServer{
        /**
         * 当前权重
         */
        private int currentWeight;
        /**
         * 原始权重
         */
        private int originWeight;
        /**
         * 服务名
         */
        private String name;

        public LoadServer( String name,int currentWeight) {
            this.currentWeight = currentWeight;
            this.originWeight = currentWeight;
            this.name = name;
        }
    }

    private Map<String,LoadServer> serverMap = new ConcurrentHashMap<>();
    public void initServerWeight(){
        serverMap.put("101",new LoadServer("101",5));
        serverMap.put("102",new LoadServer("102",2));
        serverMap.put("103",new LoadServer("103",2));
    }


    /**
     * 选择最优负载
     * @return
     */

    public LoadServer selectBestServer(){


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t");
        for (Map.Entry<String,LoadServer> server:serverMap.entrySet()){
            stringBuilder.append(server.getValue().currentWeight+",");
        }
        stringBuilder.append("\t\t");
        System.out.println("\t配置前\t\t配置后\t最优配置名称");



        int sumBalance = 0;
        LoadServer maxServer=null;
        for (Map.Entry<String,LoadServer> server:serverMap.entrySet()){
            /** 求总负载 **/
            sumBalance+=server.getValue().originWeight;
            /** 求当前时刻最大权重的负载 **/
            if (maxServer==null){
                maxServer = server.getValue();
            }else {
                if (server.getValue().getCurrentWeight()>maxServer.getCurrentWeight()){
                    maxServer = server.getValue();
                }
            }
        }
        /** 找到最大负载之后，恒心当前负载 **/
        maxServer.setCurrentWeight(maxServer.getCurrentWeight()-sumBalance);

        for (Map.Entry<String,LoadServer> server:serverMap.entrySet()){
           server.getValue().setCurrentWeight(server.getValue().getCurrentWeight()+server.getValue().getOriginWeight());
           stringBuilder.append(server.getValue().getCurrentWeight()+",");
        }
        stringBuilder.append("\t"+maxServer.getName());
        System.out.println(stringBuilder);
        System.out.println("--------------------------------\n");

        return maxServer;

    }

    public static void main(String[] args) {
        AvgLoadBalanceUtil balance = new AvgLoadBalanceUtil();
        balance.initServerWeight();
        for (int i =0;i<10;i++){
            balance.selectBestServer();
        }
    }








}
