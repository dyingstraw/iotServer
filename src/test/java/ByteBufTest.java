import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

/**
 * @program: netty_study
 * @description: netty缓存区学习
 * @author: dyingstraw
 * @create: 2019-05-14 20:59
 **/
public class ByteBufTest {

    static void mallocTest(){
        ByteBuf byteBuf = Unpooled.directBuffer();
        byteBuf.writeByte('a');
        byteBuf.writeByte('b');
        byteBuf.writeByte('a');
        byte t = byteBuf.getByte(2);
        System.out.println(t);
        System.out.println("");
        System.out.println(byteBuf.hasArray());
        byte[] a = byteBuf.array();
        byte[]buff = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buff);
        System.out.println(Arrays.toString(buff));

        while (byteBuf.readableBytes()>0){
            System.out.println(byteBuf.readByte());
        }
    }
    public static void main(String[] args) {

        CompositeByteBuf cb = Unpooled.compositeBuffer();
        ByteBuf byteBuf1 = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            byteBuf1.writeByte(i);
        }
        ByteBuf byteBuf2 = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            byteBuf2.writeByte(i);
        }
        System.out.println(byteBuf1.readableBytes());
        cb.addComponents(byteBuf1,byteBuf2);

        byte[] buff = new byte[cb.readableBytes()];
        cb.getBytes(cb.readerIndex(),buff);
        System.out.println(Arrays.toString(buff));

    }





}
