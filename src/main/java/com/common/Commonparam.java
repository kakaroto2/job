package com.common;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.json.BaseBean;

import sun.misc.BASE64Encoder;

/**
 * 提示信息配置
 * 
 * @author Acer
 * 
 */
public class Commonparam {
	public static Logger logger = LoggerFactory.getLogger(Commonparam.class);
	public static final String path = null;
	public static final String[] orderStatusMsg = new String[] { " 订单未支付",
			"已支付定金", "申请退款中", "退款处理中", "退款完成", "退款失败", "交易完成" };
	public static final long[] FALE_SIZE = new long[] { 1024 * 1024 * 5 };
	public static String[] FILE_PATH = new String[] { "/upload/travelNotes/" };// 图片数组
	public static String productImage = "/upload/product/";
	public static SimpleDateFormat fmt = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat fmtorder = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat fmtshort = new SimpleDateFormat("yyyy-MM-dd");
	public static String p12Path = "/common/cert.p12";
	public static String qrcodefolder = "/upload/qrcode/";
	public static long imageMax = 500 * 1024;
	public static String htmlImage = "/upload/html/";
	public static String apkPath = "/upload/apk/";
	public static String error_param_msg = "参数错误!";
	public static String error_time_msg = "超时!";
	private static final String date_format = "yyyy-MM-dd HH:mm:ss";
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

	public static DateFormat getDateFormat()
	{
		DateFormat df = threadLocal.get();
		if(df==null){
			df = new SimpleDateFormat(date_format);
			threadLocal.set(df);
		}
		return df;
	}

	public static String formatDate() throws ParseException {
		return getDateFormat().format(new Date());
	}

	/**
	 * 随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	public static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 字符串转换成时间
	 *
	 * @return
	 */
	public static Date String2ShortDate(String str) {
		Date time = null;

		try {
			time = fmtshort.parse(str);
		} catch (java.text.ParseException e) {

			e.printStackTrace();
		}
		return time;
	}

	public static String datachenge(Integer st) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, st);
		String s = sdf.format(c.getTime());
		return s;
	}

	/**
	 * 获取年月日
	 * 
	 * @param date
	 * @return
	 */
	public static String Date2ShortStr(Date date) {
		if (date != null)
			return fmtshort.format(date);
		else
			return "";

	}

	/**
	 * 获取完整时间
	 * 
	 * @param date
	 * @return
	 */
	public static String Date2Str(Date date) {
		if (date != null)
			return fmt.format(date);
		else
			return "";
	}

	/**
	 * 获取当前完整时间
	 *
	 * @return
	 */
	public static String Date2Str() {
		return fmt.format(new Date());
	}

	public static Date StringtoDate(String str) {
		Date time = null;

		try {
			time = fmt.parse(str);
		} catch (java.text.ParseException e) {

			e.printStackTrace();
		}
		return time;
	}

	public static String nowDate() {
		Date date = new Date();
		String nowDate = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
				+ "-" + (date.getDate()) + "";
		return nowDate;
	}

	public static Date NowTime() {
		Date now = new Date();
		return now;

	}

	public static BaseBean checkUseDate(String loginDate, int spaceMonth,
			int remainMinutes) throws ParseException {
		BaseBean bean = new BaseBean();
		if (loginDate == null || loginDate.length() == 0) {
			bean.setStatus(200);
			bean.setMsg("未曾登录");
			return bean;
		}
		Calendar cal0 = Calendar.getInstance();
		cal0.setTime(fmt.parse(loginDate));
		cal0.add(Calendar.MONTH, spaceMonth); // 得到�?4个月
		cal0.add(Calendar.MINUTE, remainMinutes); // 得到�?4个月
		long enddate = cal0.getTimeInMillis();

		Calendar cal = Calendar.getInstance();
		cal.setTime(fmt.parse(loginDate));
		cal.add(Calendar.MONTH, 23); // 得到�?3个月
		long date = cal.getTimeInMillis();

		Calendar cal1 = Calendar.getInstance();
		long now = cal1.getTimeInMillis();
		if (now >= date) {
			if (now >= enddate) {
				bean.setStatus(400);
				bean.setMsg("使用期限已经到期");
				System.out.println("使用期限已经到期");
			} else {
				int spacedate = (int) ((enddate - now) / (24 * 60 * 60 * 1000));
				if (spacedate > 0) {
					bean.setStatus(300);
					bean.setMsg("使用期限将到期,剩余" + spacedate + "天");
					System.out.println("使用期限将到期,剩余" + spacedate + "天");
				} else {
					int spacehour = (int) ((enddate - now) / (60 * 60 * 1000));
					if (spacehour > 0) {
						bean.setStatus(300);
						bean.setMsg("使用期限将到期,剩余" + (spacehour) + "小时");
						System.out.println("使用期限将到期,剩余" + (spacehour) + "小时");
					} else {
						int spacem = (int) ((enddate - now) / (60 * 1000));
						bean.setStatus(300);
						bean.setMsg("使用期限将到期,剩余" + (spacem) + "分钟");
						System.out.println("使用期限将到期,剩余" + (spacem) + "分钟");
					}
				}

			}
		} else {
			bean.setStatus(200);
			bean.setMsg("使用期限未到期");
		}
		System.out.println("now:" + fmt.format(new Date(now)) + "---------end:"
				+ fmt.format(new Date(enddate)));
		return bean;
	}

	/**
	 * 剩余分钟数
	 * 
	 * @param loginDate
	 * @param spaceMonth
	 * @param remainMinutes
	 * @return
	 * @throws ParseException
	 */
	public static int calRemainDate(String loginDate, int spaceMonth,
			int remainMinutes) throws ParseException {

		Calendar cal0 = Calendar.getInstance();
		cal0.setTime(fmt.parse(loginDate));
		cal0.add(Calendar.MONTH, spaceMonth); // 得到后24个月
		cal0.add(Calendar.MINUTE, remainMinutes); // 得到后24个月
		long enddate = cal0.getTimeInMillis();

		Calendar cal1 = Calendar.getInstance();
		long now = cal1.getTimeInMillis();

		int spacem = (int) ((enddate - now) / (60 * 1000));
		System.out.println("使用期限将到期,剩余" + (spacem) + "分钟");
		System.out.println("now:" + fmt.format(new Date(now)) + "---------end:"
				+ fmt.format(new Date(enddate)));
		if (spacem < 0)
			spacem = 0;
		return spacem;

	}

	
	public static void makeFile(File file, String absolute, String filename)
			throws IOException {

		File absoluteFolder = new File(absolute);
		if (!absoluteFolder.exists()) {
			absoluteFolder.mkdirs();
		}
		FileOutputStream fos = null;
		FileInputStream fis = null;
		fos = new FileOutputStream(absolute + filename);
		fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.close();
		fis.close();
	}

	public static boolean deleteFile(String filepath) {
		if (filepath == null || filepath.length() == 0) {
			return true;
		} else {
			File file = new File(filepath);
			if (file.isFile() && file.exists()) {
				return file.delete();
			}
		}

		return true;
	}

	/**
	 * 
	 * @param file
	 *            源文件
	 * @param targetFolder
	 *            目标文件夹
	 * @param filename
	 *            最终
	 * @throws IOException
	 */
	public static void makeFile(HttpServletRequest request, File file,
			String targetFolder, String filename) throws IOException {
		String absolute = request.getSession().getServletContext()
				.getRealPath(targetFolder);
		File absoluteFolder = new File(absolute);
		if (!absoluteFolder.exists()) {
			absoluteFolder.mkdirs();
		}
		FileOutputStream fos = null;
		FileInputStream fis = null;
		fos = new FileOutputStream(absolute + "/" + filename);
		fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.close();
		fis.close();

	}

	public static String makeNewFileName(String imgSmallFileName) {
		if (!imgSmallFileName.contains(".")) {
			return UUID.randomUUID().toString();
		}
		String ext = imgSmallFileName.substring(
				imgSmallFileName.lastIndexOf("."), imgSmallFileName.length());
		return UUID.randomUUID().toString() + ext;
	}

	public static SimpleDateFormat yearmonth = new SimpleDateFormat("/yyyy/MM/");
	public static String msgError = "参数错误!";

	public static String makeYearMonth(Date date) {
		String time = "/2014/7/";
		try {
			time = yearmonth.format(date);
		} catch (Exception e) {LogException.printException(e);
			e.printStackTrace();
		}
		return time;
	}

	public static boolean StringIsNull(String account) {
		if (account == null || account.trim().length() == 0
				|| account.trim().toLowerCase().equals("null"))
			return true;
		return false;
	}

	public static boolean StringIsNotNull(String account) {
		if (account != null && account.trim().length() > 0)
			return true;
		return false;
	}

	/**
	 * minute分钟以后
	 * 
	 * @param minute
	 * @return
	 */
	public static String spaceMinute(int minute) {

		Calendar cal0 = Calendar.getInstance();
		cal0.add(Calendar.MINUTE, minute);
		return Date2Str(cal0.getTime());
	}

	// eyJncm91cElkIjoiMSIsInVzZXJJZCI6IjEiLCJhY2NvdW50IjoiemhhbyIsInB3ZCI6IkUxMEFE
	// QzM5NDlCQTU5QUJCRTU2RTA1N0YyMEY4ODNFIiwibmFtZSI6IuWkmuWkmiJ9
	public static String Md5String(String paramStr) throws Exception {
		BASE64Encoder base64 = new BASE64Encoder();
		String str64 = base64.encode(paramStr.getBytes("utf-8"));
		return MD5Util.digest(str64);
	}

	

	public static String createRandcode(String phone) {
		phone = phone.substring(2, 9);
		Integer in = Integer.parseInt(phone);
		Integer t = (int) (Math.random() * in);
		if (t < 100000) {
			t = t + 100000;
		}
		String s = String.valueOf(t).substring(0, 6);
		return s;
	}

	public static String createRandom() {
		String val = "";
		Random random = new Random();

		for (int i = 0; i < 18; i++) {
			String con = random.nextInt(2) % 2 == 0 ? "char" : "num";
			if ("char".equalsIgnoreCase(con)) {
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(con)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	public static String Interception(String result) {
		// String
		// result="<string xmlns=\"http://tempuri.org/\">1|216636338</string>";
		int beginIndex = result
				.indexOf("<string xmlns=\"http://tempuri.org/\">") + 36;
		int endIndex = result.indexOf("</string>");
		result = result.substring(beginIndex, endIndex);
		return result;
	}

	public static String Date2OrderStr() {
		return fmtorder.format(new Date());
	}

	public static Object createRandcode() {
		String code = new DecimalFormat("000000").format(new Random()
				.nextInt(99999));
		return code;
	}

	public static Object createRandOrderCode() {
		String code = new DecimalFormat("000000").format(new Random()
				.nextInt(99999));
		return code;
	}

	public static Object space2DaysMinute(Date startDate, Object endTime) {
		// TODO Auto-generated method stub
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date end = fmt.parse(endTime.toString());
			long dd = (end.getTime() - startDate.getTime()) / (1000 * 60);
			if (dd < 0)
				return 0;
			return dd;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static String createOrderNumber(String orderNumber) {
		if (orderNumber == null || orderNumber.trim().length() == 0) {
			String day = Commonparam.Date2OrderStr();
			orderNumber = day + "" + Commonparam.createRandOrderCode()
					+ "00000001";
		} else {
			orderNumber = orderNumber.substring(0, 8)
					+ orderNumber.substring(14, 22);
			String dayPre = (Long.valueOf(orderNumber)) + 1 + "";
			orderNumber = dayPre.substring(0, 8)
					+ Commonparam.createRandOrderCode()
					+ dayPre.substring(8, 16);
		}
		return orderNumber;
	}

	/**
	 * 获取5位随机的大写字母
	 * @return
	 */
	public static String createRandomUpper(){
		String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random=new Random();
		StringBuffer invitationCode=new StringBuffer();
		for (int i=0;i<5;i++){
			int num=random.nextInt(26);
			invitationCode.append(str.charAt(num));
		}
		return invitationCode.toString();
	}
	/**
	 * 获取5位随机数，包括数字和英文大小写
	 * */
	public static String createRandomNumber() {
		String invitationCode = "";
		Random random = new Random();

		// 参数length，表示生成几位随机数
		for (int j = 0; j < 5; j++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				invitationCode += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				invitationCode += String.valueOf(random.nextInt(10));
			}
		}
		return invitationCode;
	}
	/**
	 * 获取6位随机数，包括数字和英文大小写
	 * */
	public static String createSixRandomNumber() {
		String invitationCode = "";
		Random random = new Random();

		// 参数length，表示生成几位随机数
		for (int j = 0; j < 6; j++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				invitationCode += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				invitationCode += String.valueOf(random.nextInt(10));
			}
		}
		return invitationCode;
	}
	/**
	 * 产生随机出国家的随机数
	 */
	public static int  createRandomNumByCountry(){
		Random random=new Random();
		return random.nextInt(200)+1;
	}
	/**
	 * 生成几个随机数
	 */
	public static int createRandomNumByBig(int num){
		Random random=new Random();
		return random.nextInt(num);
	}
	/**
	 * 保存图片 需要三个参数 文件 uploadFile 文件名称 uploadFileFileName 文件类型 type 保存的路径
	 * imageStorePath 调用时候的IP imageWebHttp
	 * */

//	public static BaseBean saveFile(File uploadFile, String uploadFileFileName,
//			int type) {
//		logger.debug("saveFile start:"+uploadFileFileName);
//		BaseBean bean = new BaseBean();
//		long max = Commonparam.FALE_SIZE[type];
//		if (uploadFile != null) {
//			int length = 0;
//			FileInputStream fis = null;
//			try {
//				fis = new FileInputStream(uploadFile);
//
//				try {
//					length = fis.available();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//
//				if (max >= length) {
//					String newfilename = Commonparam
//							.makeNewFileName(uploadFileFileName);
//					String targetFolder = Commonparam.FILE_PATH[type]
//							+ Commonparam.makeYearMonth(new Date());
//					Properties prop = new Properties();
//					InputStream in = UserAction.class
//							.getResourceAsStream("/util.properties");
//					try {
//						prop.load(in);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					String targetFolderTemp = prop
//							.getProperty("imageStorePath") + targetFolder;
//					File file = new File(targetFolderTemp);
//					if (!file.exists()) {
//						file.mkdirs();
//					}
//					String absolute = targetFolderTemp + "/" + newfilename;
//					absolute=absolute.replaceAll("//", "/");
//					logger.debug("upload picture dir:"+absolute);
//
//					BufferedInputStream fileIn = new BufferedInputStream(fis);
//
//					byte[] buf = new byte[1024];
//					file = new File(absolute);
//
//					BufferedOutputStream fileOut = new BufferedOutputStream(
//							new FileOutputStream(file));
//
//					while (true) {
//						// 读取数据
//						int bytesIn = fileIn.read(buf, 0, 1024);
//						if (bytesIn == -1) {
//							break;
//						} else {
//							fileOut.write(buf, 0, bytesIn);
//						}
//					}
//
//					fileOut.flush();
//					fileOut.close();
//
//					bean.setStatus(200);
//					String localUrl= targetFolder + "/" + newfilename;
//					localUrl=localUrl.replaceAll("//", "/");
//					if(localUrl.startsWith("/")){
//						localUrl=localUrl.substring(1, localUrl.length());
//					}
//					String pictureUrl=prop.getProperty("imageWebHttp")+localUrl;
//
//					bean.setRows(pictureUrl);
//
//					logger.debug("localUrl:"+localUrl);
//					logger.debug("pictureUrl:"+pictureUrl);
//
//					//同步上传到七牛 TODO 后期可以重构，不用存本地
//					QiNiuUtils.upload(absolute,localUrl);
//					logger.debug("saveFile  success:"+QiNiuUtils.getBaseUrl()+localUrl);
//				} else {
//					bean.setStatus(400);
//					bean.setMsg("图片大小" + max / 1024 / 1024 + "M以内!");
//				}
//			} catch (Exception e2) {
//				logger.debug("saveFile  err:"+e2.getMessage());
//				LogException.printException(e2);
//
//			}
//		}
//		return bean;
//	}

	/**
	 * 
	 * @param srcImageFile
	 * @param targetFolder
	 * @param dstImageFileName
	 * @param size
	 *            大小，Kb
	 * @throws Exception
	 */
	public static void compressImage(File srcImageFile, String targetFolder,
			String dstImageFileName, int size) throws Exception {
		File file = new File(targetFolder);
		if (!file.exists()) {
			file.mkdirs();
		}
		String absolute = targetFolder + "/" + dstImageFileName;
		BufferedInputStream fileIn = new BufferedInputStream(
				new FileInputStream(srcImageFile));

		byte[] buf = new byte[1024];
		file = new File(absolute);

		BufferedOutputStream fileOut = new BufferedOutputStream(
				new FileOutputStream(file));

		while (true) {
			// 读取数据
			int bytesIn = fileIn.read(buf, 0, 1024);
			if (bytesIn == -1) {
				break;
			} else {
				fileOut.write(buf, 0, bytesIn);
			}
		}

		fileOut.flush();
		fileOut.close();

		srcImageFile = new File(absolute);
		BufferedImage srcImage = ImageIO.read(srcImageFile);

		int srcWidth = srcImage.getWidth();// 原图片宽度
		int srcHeight = srcImage.getHeight();// 原图片高度
		if (fileSize(srcImageFile) > size) {
			dstImageFileName = targetFolder + dstImageFileName;
			while (fileSize(srcImageFile) > size) {
				srcImageFile = new File(dstImageFileName);
			}
			// // 判断是否需要旋转图
			// srcImage = ImageIO.read(srcImageFile);
			// int targetWidth = srcImage.getWidth(null);// 原图片宽度
			// int targetHeight = srcImage.getHeight(null);// 原图片高度
			// double f1 = srcWidth * 1.0 / srcHeight;
			// double f2 = targetWidth * 1.0 / targetHeight;
			// System.out.println("(" + srcWidth + "," + srcHeight + ")," + "("
			// + targetWidth + "," + targetHeight + ")");
			// System.out.println(f1 + "," + f2);
			// 判断是否需要旋转图片
		} else {
			makeFile(srcImageFile, targetFolder, dstImageFileName);
		}

	}

	

	public static float fileSize(File uploadFile) {
		int length = 0;
		try {
			FileInputStream fis = new FileInputStream(uploadFile);
			try {
				length = fis.available();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		float size = length * 1.0f / 1024;
		return size;
	}

	public static void main(String[] arg) throws Exception {


		System.out.print(createRandomNumber());

	}
}
