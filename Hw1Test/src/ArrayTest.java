/*
作者:Jeffreyshe
簡單描述:對MyArray做unit test
更新日期:2012/10/24
*/
import static org.junit.Assert.*;
import org.junit.*;

public class ArrayTest {
	private MyArray array;
	private static final double DELTA = 0.000001;  //double型態之expected和result相差多少視為相等
	
	/*test開始前會執行的函式*/
	@Before
	public void setUp()
	{
		array=new MyArray("1,2,3,4,5,6,7,8,9,10");
	}
	
	/*test完後執行的函式*/
	@After
	public void tearDown()
	{
		array=null;
	}
	
	/*測試array中的最大值*/
	@Test
	public void testMaximum()
	{
		double expected=10;
		double result=array.getMaximumValue();
		assertEquals(expected, result,DELTA);
	}
	
	/*測試array中的最小值*/
	@Test
	public void testMinimum()
	{
		double expected=1;
		double result=array.getMinimumValue();
		assertEquals(expected, result,DELTA);
	}
	
	/*測試array中的平均值*/
	@Test
	public void testMean()
	{
		double expected=5.5;
		double result=array.getMean();
		assertEquals(expected, result,DELTA);
	}
	
	/*測試array中的中位數*/
	@Test
	public void testMedian()
	{
		double expected=5.5;
		double result=array.getMedian();
		assertEquals(expected, result,DELTA);
	}
	
	/*測試array的變異數*/
	@Test
	public void testVariance()
	{
		double expected=8.25;
		double result=array.getVariance();
		assertEquals(expected, result,DELTA);
	}
	
	/*測試array的標準差*/
	@Test
	public void testStandardDeviation()
	{
		double expected=2.872281;
		double result=array.getStandardDeviation();
		assertEquals(expected, result,DELTA);
	}
}
