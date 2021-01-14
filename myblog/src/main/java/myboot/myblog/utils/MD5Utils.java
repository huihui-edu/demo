package myboot.myblog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5算法加密
 */
public class MD5Utils {

    public static final String hexDigest[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String MD5Encoding(String str){
        try {
            //声明采用MD5算法加密
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //将字符串转换成byte类型
            byte[] bStr = str.getBytes();
            //加密得到一个字节数组
            byte[] bytes = digest.digest(bStr);
            //存放结果
            StringBuffer buffer = new StringBuffer();
            for (byte b : bytes) {
                buffer.append(hexDig(b));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String hexDig(byte b){
        int n = b;
        if (n < 0){
            n += 256;
        }
        int x = n / 16;
        int y = n % 16;
        //返回字符
        return hexDigest[x] + hexDigest[y];
    }
}
