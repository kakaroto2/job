package com.common;

/**
 * Created by CoderZhao on 2015/10/26.
 */
public class PhoneUtil {
    public static String checkPhone(String s){
        //String c="?";
//        s=s.replace("(","").replace(")","").replace("]","").replace("[","").replace("-","");
//        if (s.contains("\\U00a0")){
//            s=s.substring(s.indexOf("\\U00a0")+6).replace("\\U00a0","");
//        }else if(s.contains(" ")){
//            s=s.substring(s.indexOf(" ")+1).replace(" ","");
//        }else if(s.contains("?")){
//            s=s.substring(s.indexOf("?")+1).replace("?","");
//        }
//        return  s;
        s=s.replace("(","").replace(")","").replace("]","").replace("[","").replace("-","").replace("+","");
        s=s.replaceAll("\\D"," ");
        if (s.contains(" ")){
            s=s.substring(s.indexOf(" ")+1);
        }
        return  s;
    }

    public static void main(String[] args){
        String s="+86?18601662369";
        System.out.println(PhoneUtil.checkPhone(s));
    }
}
