package util;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigUtilTest  {

    @Test
    public void getConfigAsString() {
        String s = ConfigUtil.get("app");
        System.out.println(s);
    }
}