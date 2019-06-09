package handler;

import config.MongoUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import model.dto.RecordDTO;
import org.bson.Document;

import java.util.Date;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 22:05
 **/
@Slf4j
public class RecordHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (RecordDTO.class.isInstance(msg)){
            RecordDTO record = (RecordDTO) msg;
            Document document = MongoUtil.objectToDocument(msg);
            log.info(document.toString());
            log.info(new Date().toString());
            MongoUtil.getRecordCollection().insertOne(document);
            log.info(Thread.currentThread().toString());
        }else {
            super.channelRead(ctx, msg);
        }
    }
}
