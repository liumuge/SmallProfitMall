package cn.xgtd.util.img;

import cn.xgtd.util.file.PathUtil;
import cn.xgtd.util.qiNiuYun.SimpleUpload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * 文件上传工具类
 * @author 86185
 */
public class UploadFileUtil {

    /**
     * 上传文件到七牛云
     * @param space
     * @param productId
     * @param imageName
     * @return
     * @throws IOException
     */
    public static String uploadFileUtil(String space,String productId,String imageName) throws IOException {

        //上传文件地址
        String fileSite = PathUtil.getImgBasePath()+"/"+imageName;
        String key = imageName+"_"+productId;
        //上传文件到七牛云
        SimpleUpload uploading = new SimpleUpload();
        uploading.upload(fileSite,key,space);
        //根据地址删除文件图片
        PathUtil.deleteFile(fileSite);
        //返回图片地址 + 时间戳 用于覆盖上传更新
        String site =" http://img.isdfmk.xyz/"+key;
        return site;
    }

}