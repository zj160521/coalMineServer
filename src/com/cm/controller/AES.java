package com.cm.controller;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AES {
   
	
	private static AESFunc aesFun = new AESFunc();
	
	
	
	/**
	  * 针对txt加密
	  * @param openFile 要加密的文件
	  * @param saveFile  加密后的文件
	  * @param state  明文
	  * @param key 密钥
     * @paramNb 是加密时明文的分组长度（以32bit为单位）；
     * @param  Nk是密钥的长度（以32bit为单位）；
     * @return  字节数
	 * @throws IOException 
	  */
	 @SuppressWarnings("resource")
	public static int  encrypt(StringBuffer openFile,String saveFile,String key,int Nb,int Nk) throws IOException {
		 
		 int Nr = 10 ;      //轮密钥加的轮数
	//	 	  File  saveF = new File(openFile);
	//	 	  if((!saveF.exists()) || saveF.isDirectory())
	//	 		  return 0;
	//	 	 //以二进制读的方式打开要加密的文件；
	//	     //以二进制写的方式打开保存密文的文件；   
	//	     FileInputStream fp1 = new FileInputStream(openFile);
		 InputStream fp1 = new ByteArrayInputStream(openFile.toString().getBytes("UTF-8"));
	     FileOutputStream fp2 = new FileOutputStream(saveFile,true);
	     
	     int Length = fp1.available();       //得到要加密的文件的长度；
	     if(Length==0)
	    	 return 0;               //内容为空则直接返回
	     int  leave = Length%(4*Nb);       //求剩余的字块的字节数；
		 long rounds = Length/(4*Nb);    //得到整块的加密轮数；
		
	     byte[] state = new byte[4*4];     //作为加密时存放要加密的明文块；
	   //  byte[] copy = new byte[4*4];     //用来进行短块处理时的缓存区；
	    
		 char [] w = new char[16*11];//存放密钥
	     aesFun.keyExpan(key, w);//生成各轮子密钥
	     //以下处理的明文是分组的整数倍的情况；
         while(rounds>0){          
            fp1.read(state,0,4*Nb);
            state = aesFun.encrypt(aesFun.byteTOchar(state), w, Nr);
            fp2.write(state,0,4*Nb);
            rounds--;
         }
       //明文是分组的整数倍的处理完毕，处理非整数部分
	     if(leave != 0){  
		   fp1.read(state,0,leave);//明文的长度小于八个字符；
	       for(int i= leave; i < 4*Nb; i++)
	    	   		state[i]=0;             //后面用空格补齐；  
	       state = aesFun.encrypt(aesFun.byteTOchar(state), w, Nr);
	       fp2.write(state,0,4*Nb);
	     }
	     fp1.close();
	     fp2.close();
	     return Length;//返回文件长度；
	 }
	 /**
	  * 针对txt
	  * @param openFile 要解密的文件
	  * @param saveFile  解密后的文件
	  * @param state  密文
	  * @param key 密钥
      * @paramNb 是加密时明文的分组长度（以32bit为单位）；
      * @param  Nk是密钥的长度（以32bit为单位）；
      * @return  字节数
	 * @throws IOException 
	  */
	 @SuppressWarnings("resource")
	public static String  decrypt(InputStream fp1,String saveFile,String key,int Nb,int Nk) throws IOException{
		 int Nr = 10 ;      //轮密钥加的轮数
//		 File  openF = new File(openFile);
//		 if((!openF.exists()) || openF.isDirectory())
//			  return 0;
		 //以二进制读的方式打开要加密的文件；
	     //以二进制写的方式打开保存密文的文件；   
//		 InputStream fp1 = new ByteArrayInputStream(openFile);
	     FileOutputStream fp2 = new FileOutputStream(saveFile);
	     
	     int Length = fp1.available();       //得到要加密的文件的长度；
	     if(Length==0)
	    	 return null;               //内容为空则直接返回
	   
		 long rounds = Length/(4*Nb);    //得到整块的加密轮数；
		
	     byte[] state = new byte[4*4];     //作为加密时存放要加密的明文块；
	  
	     char [] w = new char[16*11];//存放密钥
         aesFun.keyExpan(key, w);//生成各轮子密钥
       
         String data = "";
         while(rounds>0){ //对后面是分组长度的整数倍的密文块解密；
	           fp1.read(state,0,4*Nb);//读取密文块；
	           state = aesFun.decrypt(aesFun.byteTOchar(state), w, Nr);//解密变换；
	           fp2.write(state,0,4*Nb);//将解密后的明文写入目标文件；
	           String str = new String(state);
	           data += str;
	           rounds--;               //轮数减一；
         }
         
         fp1.close();//关闭源文件和目标文件；
         fp2.close();
	     return data;//返回文件长度
	 }
}