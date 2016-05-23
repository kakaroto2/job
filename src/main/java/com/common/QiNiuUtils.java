package com.common;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;


/**
 * 
 * @author zzg zheng__guang@sina.com
 *
 */

@Component
public class QiNiuUtils {
	
	private static Logger logger = LoggerFactory.getLogger(QiNiuUtils.class);

	private static String accessKey="jKzENkeOdxdpV2Y60Mb15qC470IU-w9Khhxjzb1L";

	private static String secretKey="K3sGUV_EphYv9VFXB0ZIrCfbsSaR3h6Q0h58QIJw";

	private static String bucket="yoloboo";
	
	private static String BaseUrl="http://7xlamk.com2.z0.glb.qiniucdn.com";
	private static String yolobooUrl="http://images.yoloboo.com";
  
	
	public static String getBaseUrl(){
		return BaseUrl;
	}
	public static String getSaveDBUrl(String qiniukey){
		return BaseUrl+"/"+qiniukey;
	}
	
	public static String getSaveDBUrl2(String qiniukey){
		return BaseUrl+"/"+qiniukey;
	}
	
	public static String getUrl(String path){
		if(path.contains("http://app.yoloboo.com/yolobooManager/")){
			path=path.replace("http://app.yoloboo.com/yolobooManager/", "");
		}
		if(!path.startsWith("/")){
			path="/"+path;
		}
		return BaseUrl+path;
	}
	public static String uploadToken() {
		logger.debug("get qiniu uploadToken start");
		Auth auth = Auth.create(accessKey, secretKey);
		// 简单上传，使用默认策略
		String token = auth.uploadToken(bucket);
		logger.debug("get qiniu uploadToken success");
		return token;
	}

	
    /**
     * 生成上传token
     * @param key     key，可为 null
     * @param expires 有效时长，单位秒
     * @return 生成的上传token
     */
	public static String uploadToken(String fileName,Long expires) {
		Auth auth = Auth.create(accessKey, secretKey);
		// 简单上传，使用默认策略
		String token = auth.uploadToken(bucket, fileName, expires, null);
		return token;
	}
	/**
	 * 以fileName存到qiniu
	 * @param filePath
	 * @param fileName
	 * @return 
	 * @throws QiniuException
	 */
	@Async
	public static void upload(String fileDir,String uploadfileName) throws QiniuException {
		logger.debug("update qiniu start:"+fileDir+" uploadfileName");
		UploadManager uploadManager = new UploadManager();
		Response result= uploadManager.put(new File(fileDir), uploadfileName, uploadToken());
		logger.debug("update qiniu success:"+result.bodyString());

	}
	
	

	public static String uploadFile(String filePath,String fileName) throws QiniuException {
		UploadManager uploadManager = new UploadManager();
		Response result= uploadManager.put(new File(filePath+fileName), fileName, uploadToken());
		logger.debug("update qiniu:"+result.bodyString());

		return result.address;
	}
	
	
	
	public static void main(String[] args) {
		String testUrl="http://app.yoloboo.com/yolobooManager//upload/travelNotes//2015/06//023c3503-2478-470c-a21e-8104fe44d0b2.jpg";
		System.out.println("url fro "+testUrl+"\n"+QiNiuUtils.getUrl(testUrl));
		testUrl="/upload/travelNotes//2015/06//023c3503-2478-470c-a21e-8104fe44d0b2.jpg";
		System.out.println("url fro "+testUrl+"\n"+QiNiuUtils.getUrl(testUrl));
		try {
			QiNiuUtils.upload("d:/xiazai/", "test.jpg");
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
