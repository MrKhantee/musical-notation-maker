/*
Author:JeffreyShe
Update date:2012/11/9 
 */

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class BeatTester 
{
	private String FilePath;
	private Double ExpectedBeat;
	private static final double DELTA = 0.000001;
	
	public BeatTester(String FilePath,Double ExpectedBeat)
	{
		this.FilePath=FilePath;
		this.ExpectedBeat=ExpectedBeat;
	}
	
	@Parameters
	public static Collection FileAndBeat()
	{
		Object[][] Parameters=new Object[][]
				{
					{".\\song\\little_star.song",64.0},
					{".\\song\\little_star_error.song",64.0},
					{".\\song\\two_tigers.song",32.0},
					{".\\song\\two_tigers_error.song",32.0}
				};
		return Arrays.asList(Parameters);
	}
	
	@Test
	public void testBeat()
	{
		assertEquals(ExpectedBeat.doubleValue(),new CheckBeat(FilePath).TotalBeat.doubleValue(),DELTA);
	}
}
