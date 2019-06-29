import lombok.extern.slf4j.Slf4j;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-29 21:28
 **/
@Slf4j
public class IpAddr {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> inters = NetworkInterface.getNetworkInterfaces();
        while (inters.hasMoreElements()){
            log.warn("{}",inters.nextElement().getInetAddresses());
        }
    }
}
