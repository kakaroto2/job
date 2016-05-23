package com.common;

public interface PublicConfig {
	
	public final boolean ISFANGJIA = true;// 是否推送，区别方家true,与幸福小区false
	public final String SNEND = "【方家】";
	public final String JAVAOBJECTNAME = "方家网站";

	/** 短信配置 */
	public final String SN = "SDK-ONE-010-03013";
	public final String PWD = "767022";
	/** 短信配置 */

	/** 手机支付宝充值 */
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public final String PHONE_partner = "2088511937329288";

	// 卖家的支付宝账号.交易成功后,买家资金会转移到该账户中.必须设置
	public final String PHONE_seller_email = "";

	// 交易安全检验码,由数字和字母组成的32位字符串,如果签名方式设置为“MD5”时，请设置该参数
	public final String PHONE_key = "";

	// 商户的私钥,如果签名方式设置为“0001”时，请设置该参数
	public final String PHONE_private_key = "";

	// 支付宝的公钥,如果签名方式设置为“0001”时，请设置该参数,用支付宝公钥
	public static String PHONE_ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	//方家公钥
	//public static String PHONE_ali_public_key  ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJu+gWYZYrHhF6FUqS7nOERQi0aa+eSjwZvs15Y2UKBsfTJ5aVw76+vsIyrf5RpYBdnUXMOKTOMYgbMl4RiuPy+lKV/cn5fj8udfAihRm1boZ4cFZsJBt+gBiJ/d3D1PCzKsqyfPvPdsuccho2DHAQotbBkU/9Mnzp+YHZWou7tQIDAQAB";
	/** 手机支付宝充值 */

	/** 手机支付宝网页充值 */
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public final String WEB_partner = "";

	// 卖家的支付宝账号.交易成功后,买家资金会转移到该账户中.必须设置
	public final String WEB_seller_email = "";

	// 交易安全检验码,由数字和字母组成的32位字符串,如果签名方式设置为“MD5”时，请设置该参数
	public final String WEB_key = "";

	// 商户的私钥,如果签名方式设置为“0001”时，请设置该参数
	public final String WEB_private_key = "";
	// 支付宝的公钥,如果签名方式设置为“0001”时，请设置该参数
	public final String WEB_ali_public_key = "";
	/** 手机支付宝网页充值 */
	

}
