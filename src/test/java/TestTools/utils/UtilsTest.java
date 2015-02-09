package TestTools.utils;

import org.apache.xerces.impl.dv.util.Base64;
import org.junit.Test;
import sun.misc.BASE64Encoder;

/**
 * Created by def on 27.12.14.
 */
public class UtilsTest {

    @Test
    public void base64Convert(){
        System.out.println(Base64.encode("bot:newnewpl".getBytes()));
    }
}
