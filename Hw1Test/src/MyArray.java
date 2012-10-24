/*
作者:Jeffreyshe
簡單描述:這是一支將輸入的字串分割成double Array,並對double Array做各種操作的程式.
更新日期:2012/10/24
*/

import java.util.Arrays;

public class MyArray
{
	private double[] allValues;

	public MyArray()
	{
		this("0");
	}

	/*
	MyArray類別的建構子
	參數為一字串"a,b,c,......"
	a,b,c為任意浮點數,再根據","做分割將a,b,c依序存入Array中
	*/
	public MyArray(String input)
	{
		String[] tokens = input.split(",");
		allValues = new double[tokens.length];
		for(int i=0;i<tokens.length;i++)
			allValues[i] = Double.parseDouble(tokens[i]);
	}

	/* 得allValues最大值 */
	public double getMaximumValue()
	{
		double max = allValues[0];
		for(int i=1;i<allValues.length;i++)
		{
			if(allValues[i]>max) 
				max = allValues[i];
		}
		return max;
	}

	/* 得allValues最小值 */
	public double getMinimumValue()
	{
		double min = allValues[0];
		for(int i=1;i<allValues.length;i++)
		{
			if(allValues[i]<min) 
				min = allValues[i];
		}
		return min;
	}

	/* 得allValues第index個值 */
	public double getValue(int index)
	{
		if(index < allValues.length) 
			return allValues[index];
		else return 0;
	}

	/* 得allValues平均值 */
	public double getMean()
	{
		double sum = 0,average;
		for(int i=0;i<allValues.length;i++)
			sum = sum+allValues[i];
		average = sum/allValues.length;
		return average;
	}

	/* 得allValues中位數,對長度為奇數或偶數個有不同處理 */
	public double getMedian()
	{
		MyArray temp = getSortedArray(true);
		if(temp.allValues.length%2 == 0) 
			return (temp.allValues[(temp.allValues.length/2)-1]+temp.allValues[temp.allValues.length/2])/2;
		else 
			return temp.allValues[temp.allValues.length/2];
	}

	/* 得allValues變異數 */
	public double getVariance()
	{
		double Xi_square=0,Var;
		for(int i=0;i<allValues.length;i++)
			Xi_square=Xi_square+(allValues[i]*allValues[i]); //計算每一項平方的總和
		Var = (Xi_square/allValues.length)-(getMean()*getMean());  //變異數公式-平方的平均-平均的平方
		return Var;
	}
		
	/* 得allValues標準差 */	
	public double getStandardDeviation()
	{
		double Dev;
		Dev = Math.sqrt(getVariance());
		return Dev;
	}

	/* 得 allValues Array 長度 */
	public int getSize() 
	{
		return allValues.length;
	}

	/* 將輸入的 Double type Array 翻轉 */
	public void reverse(double[] ValueArray) 
	{
		double temp;
		for(int i=0;i<(ValueArray.length+1)/2;i++)
		{
			temp = ValueArray[i];
			ValueArray[i] = ValueArray[ValueArray.length-1-i];
			ValueArray[ValueArray.length-1-i] = temp;
		}
	}

	/* 對 allValues 排序,true->升冪排序 false->降冪排序 */
	public void sort(boolean r)
	{
		Arrays.sort(allValues);
		if(r == false)  
			reverse(allValues);
	}

	/* 傳回copy allValues的新object */
	public MyArray Arraycopy()
	{
		MyArray copy = new MyArray();
		copy.allValues = new double[allValues.length];
		for(int i=0;i<allValues.length;i++)
			copy.allValues[i] = allValues[i];
		return copy;
	}

	/* 將 MyArray object 的 allValues 排序 ,true->升冪排序 false->降冪排序 */
	public MyArray getSortedArray(boolean r)
	{
		MyArray	SortedArray = Arraycopy();
		Arrays.sort(SortedArray.allValues);
		if(r == false)	
			reverse(SortedArray.allValues);	
		return SortedArray;
	}

	/* 將 MyArray object 的 allValues 無條件進位到整數*/
	public MyArray getAllCeilingValues()
	{
		MyArray CeilArray = Arraycopy();
		for(int i=0;i<CeilArray.allValues.length;i++)
			CeilArray.allValues[i] = Math.ceil(CeilArray.allValues[i]);
		return CeilArray;
	}

	/* 將 MyArray object 的 allValues 無條件捨去到整數*/
	public MyArray getAllFloorValues()
	{
		MyArray FloorArray = Arraycopy();
		for(int i=0;i<FloorArray.allValues.length;i++)
			FloorArray.allValues[i] = Math.floor(FloorArray.allValues[i]);
		return FloorArray;	
	}

	/* 將 allValues Array 轉成字串 */
	public String toString()
	{
		String arrayToS = String.valueOf(allValues[0]);
		for(int i=1;i<allValues.length;i++)
			arrayToS = arrayToS+","+String.valueOf(allValues[i]);
		return arrayToS;
	}

	/* 將 MyArray object 的 allValues Array 轉成字串 */
	public String toString(MyArray PrintArray)
	{
		String arrayToS = String.valueOf(PrintArray.allValues[0]);
		for(int i=1;i<PrintArray.allValues.length;i++)
			arrayToS = arrayToS+","+String.valueOf(PrintArray.allValues[i]);
		return arrayToS;
	}

}

