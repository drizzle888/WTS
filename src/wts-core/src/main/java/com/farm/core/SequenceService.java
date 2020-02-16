package com.farm.core;

import java.net.NetworkInterface;
import java.util.Enumeration;

import com.farm.core.auth.util.MD5;

/**
 * 机器码生成的通用服务
 * 
 * @author 杨尚川
 */
public class SequenceService {
	/**
	 * 对一段String生成MD5摘要信息
	 * 
	 * @param message
	 *            要摘要的String
	 * @return 生成的MD5摘要信息
	 */
	protected static String getMD5(String message) {
		String md5 = new MD5().getMD5ofStr(message);
		return getSplitString(md5);
	}

	public static String InitKey() {
		return getMD5(getSigarSequence());
	}

	/**
	 * 将很长的字符串以固定的位数分割开，以便于人类阅读
	 * 
	 * @param str
	 * @return
	 */
	protected static String getSplitString(String str) {

		return getSplitString(str.substring(0, 16), "-", 4);
	}

	/**
	 * 将很长的字符串以固定的位数分割开，以便于人类阅读 如将 71F5DA7F495E7F706D47F3E63DC6349A
	 * 以-，每4个一组，则分割为 71F5-DA7F-495E-7F70-6D47-F3E6-3DC6-349A
	 * 
	 * @param str
	 *            字符串
	 * @param split
	 *            分隔符
	 * @param length
	 *            长度
	 * @return
	 */
	protected static String getSplitString(String str, String split, int length) {
		int len = str.length();
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < len; i++) {
			if (i % length == 0 && i > 0) {
				temp.append(split);
			}
			temp.append(str.charAt(i));
		}
		String[] attrs = temp.toString().split(split);
		StringBuilder finalMachineCode = new StringBuilder();
		for (String attr : attrs) {
			if (attr.length() == length) {
				finalMachineCode.append(attr).append(split);
			}
		}
		String result = finalMachineCode.toString().substring(0, finalMachineCode.toString().length() - 1);
		return result;
	}

	/**
	 * 利用sigar来生成机器码，当然这个实现不是很好，无法获得CPU ID，希望有兴趣的朋友来改进这个实现
	 * 
	 * @param osName
	 *            操作系统类型
	 * @return 机器码
	 */
	protected static String getSigarSequence() {
		return "asdf";
	}
	  /** 
     * 按照"XX-XX-XX-XX-XX-XX"格式，获取本机MAC地址 
     * @return 
     * @throws Exception 
     */  
    public static String getMacAddress() throws Exception{  
        Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();  
          
        while(ni.hasMoreElements()){  
            NetworkInterface netI = ni.nextElement();  
              
            byte[] bytes = netI.getHardwareAddress();  
            if(netI.isUp() && netI != null && bytes != null && bytes.length == 6){  
                StringBuffer sb = new StringBuffer();  
                for(byte b:bytes){  
                     //与11110000作按位与运算以便读取当前字节高4位  
                     sb.append(Integer.toHexString((b&240)>>4));  
                     //与00001111作按位与运算以便读取当前字节低4位  
                     sb.append(Integer.toHexString(b&15));  
                     sb.append("-");  
                 }  
                 sb.deleteCharAt(sb.length()-1);  
                 return sb.toString().toUpperCase();   
            }  
        }  
        return null;  
    }  
	public static void main(String[] args) {
		System.out.println(SequenceService.InitKey());
	}
}