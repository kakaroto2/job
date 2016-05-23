import com.alibaba.fastjson.JSONArray;
import com.qiniu.util.Json;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;
import sun.nio.cs.UnicodeEncoder;

import java.util.*;

/**
 * Created by CoderZhao on 2015/10/15.
 */
public class test {
    public static void main(String[] args){
      //String s={"keyList": "+8615850242850"};
        String s="[\"+86?18601662369\",\"+86 1850343443\"]";
//       String s="hello";
//        String s1="hello";
//        System.out.println(s.equals(s1));
//        String s="+86\\U00a015850242850";
//        String s1="+86 15850242850";
//        //System.out.println(s1.substring(s1.indexOf(" ")+1));
//       // System.out.println();
//        if (s.contains("\\U00a0")){
//            s=s.substring(s.indexOf("\\U00a0")+6);
//        }else{
//            s=s.substring(s.indexOf(" ")+1);
//        }
//        JSONArray array=JSONArray.parseArray(s);
//        for (int i=0;i<array.size();i++) {
//            System.out.print(array.get(i));
//        }
//        String s1=" ";
//        String s2=" ";
//        System.out.println(s1.contains(" "));
//        System.out.println(s2.contains(" "));
        List<String> l = new ArrayList<String>();
        l.add("hello");
        l.add("halo");
        l.add("bonjour");
        l.add("salut");
        l.remove("hello");
        System.out.println(l);

    }

}
