package com.cm.controller;

public class AESFunc
{   
	/**
	 * 128位密钥扩展 
	 * @param key
	 * @param w
	 */
	 public  void  keyExpan(String key,char[] w)
	 {
		 for (int i = 0; i < 4; i++) //赋值
		 {
			w[i*4+0] = key.charAt(i*4+0);
			w[i*4+1] = key.charAt(i*4+1);
			w[i*4+2] = key.charAt(i*4+2);
			w[i*4+3] = key.charAt(i*4+3);
		 }
		 
		 //扩展1-10轮 一个字是4个字节
		 for (int i =4; i < 44; i++) 
		 {
			 char temp[] = new char[4];
			 for (int j = 0; j < 4; j++)
			      temp[j] = w[(i-1)*4+j];
			 if(i%4==0)
			 {
				rotWord (temp);//移位
				subWord (temp);//替换
				for (int j = 0; j < temp.length; j++) 
					temp[j] ^= AESTables.Rcon[(i-1)/4*4+j]; //一定要注意是R[]看作长度是10的二维数组 并且每一轮使用一个
			 }//进行疑惑操作 
			w[i*4+0] =(char) (temp[0]^w[(i-4)*4+0]);
			w[i*4+1] =(char) (temp[1]^w[(i-4)*4+1]);
			w[i*4+2] =(char) (temp[2]^w[(i-4)*4+2]);
			w[i*4+3] =(char) (temp[3]^w[(i-4)*4+3]);
		 }
	 }
	 
	 //循环左移一个字节
	 private  void rotWord(char []b)
	 {
		 char  temp= b[0];
		 for (int i = 0; i <b.length-1; i++)
			 b[i] = b[i+1];
		 b[b.length-1]=temp;
	 }
	 
	 //根据S盒替换字节
	 private  void subWord(char []b)
	 {
		 for (int i = 0; i < b.length; i++) {
			b[i] = AESTables.sBox[b[i]&0xff];
		}
	 }
	 
	 /**
	 * @param state  //明文
	 * @param w //密钥
	 * @param rounds 加密的轮数
	 */
	 public  byte[]  encrypt(char[] state,char[] w,int rounds)
	 {
		  addRoundKey(state, w, 0);  //先进行一次亦或
		
		  for (int round = 1; round < rounds; round++) //1--9轮
		  {
			 subChar(state); //替换
			 shiftRows(state);//行移动
		     mixColums(state);//列混淆
			 addRoundKey(state, w, round*4);//亦或
		  }
	
	   subChar(state); //替换
	   shiftRows(state);//行移动
	   addRoundKey(state, w, rounds*4);//亦或
      
      return charTObyte(state);
	 }
		/**
		 * 加密用的的操作 : 亦或 替换  行移动 列混淆
		 * 本函数执行 亦或操作
		 * @param state  要加密的数组
		 * @param w  扩展密钥
		 * @param round  加密的轮数
		 * 
		 */
		 private  void addRoundKey(char[] state,char[] w,int round)
		 {
             //数据的列对应密钥的行
			 for(int c=0;c<4;c++,round++)
					 for(int r=0;r<4;r++)
			          state[r*4+c] = (char)(state[r*4+c]^w[round*4+r]);
		 }
	    /**
		 * 加密用的的操作 : 亦或 替换  行移动 列混淆
		 * 本函数执行 替换  操作
		 * @param state  要加密的数组
		 */
		 private  void subChar(char[] state)
		 {
			 for (int i = 0; i < state.length; i++)
			     state[i] = AESTables.sBox[state[i]&0xff];
		 }	
		 /**
		 * 加密用的的操作 : 亦或 替换  行移动 列混淆
		 * 本函数执行 行移动  操作
		 * @param state  要加密的数组
		 */
		 private  void shiftRows(char[] state)
		 {
			 char[] temp=new char[4];
			 for (int i = 1; i <4 ; i++)  //第一行不移动 所以从1开始循环
			 {
				for (int j = 0; j <4 ; j++)
				      temp[j] = state[i*4+((i+j)%4)] ; //i+j%4循环移动
				for (int j = 0; j < 4; j++) 
					  state[i*4+j] = temp[j];
			}
		 }	
		 /**
		 * 加密用的的操作 : 亦或 替换  行移动 列混淆
		 * 本函数执行 列混淆  操作
		 * @param state  要加密的数组
		 * --------------------------------------------
		 * 列混淆是一次进行一列的线性运算
		 * --------------------------------------------
		 */
		 private  void mixColums(char[] state)
		 {
			 char[] t=new char[4];
			 for (int c = 0;c <4 ;c++) //列
			 {
				for (int r = 0; r <4 ; r++)//行
				      t[r] = state[r*4+c] ; //每次取出一列放到临时数组中
				for (int r = 0; r < 4; r++) //对列循环进行 0x02 0x03 0x01 0x01  
					  state[r*4+c] =  (char)   (eFmul(0x02,t[r])
								                       ^eFmul(0x03,t[(r+1)%4])
								                       ^eFmul(0x01,t[(r+2)%4])
								                       ^eFmul(0x01,t[(r+3)%4]));
			}
		 }
       //在域F(2^8)进行数乘操作 并返回结果
		private  int eFmul(int i, int c)
		{
		   int temp[] = new int[4];
		   temp[0]=c;
		   int result = 0;
		   
		   for (int j = 1; j < temp.length; j++)  //乘以0x01 0x02 0x04 0x08 都计算并存储
		   {
			  temp[j] = temp[j-1]<<1;  //左移一位 
			  if(((temp[j-1]>>7)&1) ==1 ) //若第八位为1 则亦或0x1b
				    temp[j]^=0x1b;
		   }
			   
		  for (int j = 0; j < temp.length; j++) //根据参数i 取出相应得结果
			  	if(((i>>j)&1)==1)
		            result ^= temp[j]; 
		   
		return result&0xff;
		}

	/**
	 * 加解密过程需要用到的 字符互转字节  
	 * 本函数： 字节  转 字符
	 */
	public char[] byteTOchar(byte[] array)
	{
		char[] result = new char[array.length];
		for (int i = 0; i < array.length; i++) 
			  result[i] = (char)array[i];
	return result;
	}
	/**
	 * 加解密过程需要用到的 字符互转字节  
	 * 本函数： // 字符 转 字节  
	 */
	public byte[] charTObyte(char[] array)
	{
		byte[] result = new byte[array.length];
		for (int i = 0; i < array.length; i++) 
			  result[i] = (byte)(array[i]&0xff);
	return result;
	}
	
	/**
	 *  做和加密相反的操作
	 * @param state  //密文
	 * @param w //密钥
	 * @param rounds 加密的轮数
	 */
	 public  byte[]  decrypt(char[] state,char[] w,int rounds)
	 {
		 //轮密钥加10轮
		addRoundKey(state, w, rounds*4);
		//9--1轮
		for (rounds= 9; rounds > 0; rounds--)
		{
			inShiftRows(state);//逆向行移动
			inSubChar(state);//逆向替换
			addRoundKey(state, w, rounds*4);//亦或
			inMixColums(state);//逆向泪混淆
		}
		inShiftRows(state);//逆向行移动
		inSubChar(state);//逆向替换
		addRoundKey(state, w, rounds);//亦或
		return charTObyte(state);
	 }
	 
	  /**
	 * 解密用的的操作 :  逆行移动 逆替换   逆列混淆
	 * 本函数执行 逆行移动操作
	 * @param state  要解密的数组
     */
     private  void inShiftRows(char[] state)
	  {
		 char[] temp=new char[4];
		 for (int i = 1; i <4 ; i++)  //第一行不移动 所以从1开始循环
		 {
			for (int j = 0; j <4 ; j++)
			      temp[(i+j)%4] = state[i*4+j] ; //把后一位的元素 给前一位 达到右移的目的
			for (int j = 0; j < 4; j++) 
				  state[i*4+j] = temp[j];
		 }
	  }	
     /**
	 * 解密用的的操作 :  逆行移动 逆替换   逆列混淆
	 * 本函数执行 逆替换  操作
	 * @param state  要加密的数组
	 */
	 private  void inSubChar(char[] state)
	 {
		 for (int i = 0; i < state.length; i++)
		     state[i] = AESTables.invsBox[state[i]&0xff];
	 }	
     /**
	 * 解密用的的操作 : 逆 替换  逆行移动 逆列混淆
	 * 本函数执行 逆列混淆  操作
	 * @param state  要解密的数组
	 * --------------------------------------------
	 * 逆列混淆是一次进行一列的线性运算
	 * --------------------------------------------
	 */
	 private  void inMixColums(char[] state)
	 {
		 char[] t=new char[4];
		 for (int c = 0;c <4 ;c++) //列
		 {
			for (int r = 0; r <4 ; r++)//行
			      t[r] = state[r*4+c] ; //每次取出一列放到临时数组中
			for (int r = 0; r < 4; r++) //对列循环进行 0x02 0x03 0x01 0x01  
				  state[r*4+c] =  (char)   (eFmul(0x0e,t[r])
							                       ^eFmul(0x0b,t[(r+1)%4])
							                       ^eFmul(0x0d,t[(r+2)%4])
							                       ^eFmul(0x09,t[(r+3)%4]));
		}
	 }
}
