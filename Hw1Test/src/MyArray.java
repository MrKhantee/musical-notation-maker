import java.util.Arrays;

public class MyArray
{
private double[] allValues;

public MyArray()
{
	this("0");
}

public MyArray(String input) 
{
	String[] tokens = input.split(",");
	allValues=new double[tokens.length];
	for(int i=0;i<tokens.length;i++)
		allValues[i]=Double.parseDouble(tokens[i]);
}

public double getMaximumValue()
{
	double max=allValues[0];
	for(int i=1;i<allValues.length;i++)
	{
		if(allValues[i]>max) max=allValues[i];
	}
	return max;
}

public double getMinimumValue()
{
	double min=allValues[0];
	for(int i=1;i<allValues.length;i++)
	{
		if(allValues[i]<min) min=allValues[i];
	}
	return min;
}

public double getValue(int index)
{
	if(index<allValues.length) return allValues[index];
	else return 0;
}

public double getMean()
{
	double sum=0,average;
	for(int i=0;i<allValues.length;i++)
		sum=sum+allValues[i];
	average=sum/allValues.length;
	return average;
}

public double getMedian()
{
    MyArray temp=getSortedArray(true);
	if(temp.allValues.length%2==0) return (temp.allValues[(temp.allValues.length/2)-1]+temp.allValues[temp.allValues.length/2])/2;
	else return temp.allValues[temp.allValues.length/2];
}

public double getVariance()
{
	double Xi_square=0,Var;
	for(int i=0;i<allValues.length;i++)
		Xi_square=Xi_square+(allValues[i]*allValues[i]); //計算每一項平方的總和
	Var=(Xi_square/allValues.length)-(getMean()*getMean());
	return Var;
}
	
public double getStandardDeviation()
{
	double Dev;
	Dev=Math.sqrt(getVariance());
	return Dev;
}

public int getSize() 
{
	return allValues.length;
}

public void reverse(double[] ValueArray) 
{
	double temp;
	for(int i=0;i<(ValueArray.length+1)/2;i++)
	{
		temp=ValueArray[i];
		ValueArray[i]=ValueArray[ValueArray.length-1-i];
		ValueArray[ValueArray.length-1-i]=temp;
	}
}

public void sort(boolean r)
{
	Arrays.sort(allValues);
	if(r==false)  reverse(allValues);
}

public MyArray Arraycopy()
{
	MyArray copy=new MyArray();
	copy.allValues=new double[allValues.length];
	for(int i=0;i<allValues.length;i++)
		copy.allValues[i]=allValues[i];
	return copy;
}

public MyArray getSortedArray(boolean r)
{
	MyArray	SortedArray=Arraycopy();
	Arrays.sort(SortedArray.allValues);
	if(r==false)	reverse(SortedArray.allValues);	
	return SortedArray;
}

public MyArray getAllCeilingValues()
{
	MyArray CeilArray=Arraycopy();
	for(int i=0;i<CeilArray.allValues.length;i++)
		CeilArray.allValues[i]=Math.ceil(CeilArray.allValues[i]);
	return CeilArray;
}

public MyArray getAllFloorValues()
{
	MyArray FloorArray=Arraycopy();
	for(int i=0;i<FloorArray.allValues.length;i++)
		FloorArray.allValues[i]=Math.floor(FloorArray.allValues[i]);
	return FloorArray;	
}

public String toString()
{
	String arrayToS=String.valueOf(allValues[0]);
	for(int i=1;i<allValues.length;i++)
		arrayToS=arrayToS+","+String.valueOf(allValues[i]);
	return arrayToS;
}

public String toString(MyArray PrintArray)
{
	String arrayToS=String.valueOf(PrintArray.allValues[0]);
	for(int i=1;i<PrintArray.allValues.length;i++)
		arrayToS=arrayToS+","+String.valueOf(PrintArray.allValues[i]);
	return arrayToS;
}

}

