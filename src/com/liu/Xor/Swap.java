package com.liu.Xor;

public class Swap {
	
	public static void main(String[] args) {
		int a = 16;
		int b = 603;
		
		System.out.println(a);
		System.out.println(b);
		/**
		 * 说明：异或的性质：异或可以直接理解为无进位相加
		 * 0 ^ N = N;
		 * N ^ N = 0;
		 * 异或运算满足交换率和结合了
		 * => 同一批数不管满足什么样的顺序，最后的结果只能是一个
		 * =>一个数出现了偶数次 异或结果为0 （0 ^ a ^a  ^a ^ a ^ b = b)
		 *
		 */
		a = a ^ b;
		b = a ^ b;
		a = a ^ b;

		System.out.println(a);
		System.out.println(b);

		int[] arr = {3,1,100};
		/**
		 * 说明：异或交换两个数必须不在同一个内存区域内
		 * 如果都指向同一个内存区域，交换结果为0！！！切记！！！
		 */
		int i = 0;
		int j = 0;
		
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
		
		System.out.println(arr[i] + " , " + arr[j]);

		System.out.println(arr[0]);
		System.out.println(arr[2]);
		
		swap(arr, 0, 0);
		
		System.out.println(arr[0]);
		System.out.println(arr[2]);

	}
	
	
	public static void swap (int[] arr, int i, int j) {
		// arr[0] = arr[0] ^ arr[0];
		arr[i]  = arr[i] ^ arr[j];
		arr[j]  = arr[i] ^ arr[j];
		arr[i]  = arr[i] ^ arr[j];
	}

}
