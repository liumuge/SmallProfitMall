package cn.itcast.Test.ys;

import cn.itcast.util.compressPicture.PathUtil;
import cn.itcast.util.compressPicture.PictureUtilOne;
import cn.itcast.util.picture.SimpleUpload;
import org.apache.ibatis.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static cn.itcast.util.compressPicture.PathUtil.getImgBasePath;


public class demo {

    @Test
    public void  a() throws IOException {
        PictureUtilOne a =new PictureUtilOne();
        InputStream in = Resources.getResourceAsStream("temporaryFiles/Bjxu9RqIzP3Ee5QkFP6d1A.jpg");
        System.out.println(in);
        File file = new File(getImgBasePath());
        if(!file.exists()){
            // 创建该文件夹
            file.mkdirs();
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        a.pictureUtilOne(in,getImgBasePath()+"\\"+uuid);
        SimpleUpload c = new SimpleUpload();
        System.out.println(getImgBasePath()+"\\"+uuid+".JPEG");
        c.overrideUpload(getImgBasePath()+"\\"+uuid+".JPEG",uuid,"mugebl");
        PathUtil.deleteFile("\\"+uuid+".JPEG");
    }


}

