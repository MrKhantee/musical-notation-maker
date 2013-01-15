import java.io.BufferedReader;
import java.io.FileReader;
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
	private boolean Answer;
	
	public BeatTester(String s,boolean b)
	{
		this.FilePath=s;
		Answer=b;
	}
	
	@Parameters
	public static Collection FileAndBeat()
	{
		Object[][] Parameters=new Object[][]
				{
					{".\\song\\little_bee.song",true},
					{".\\song\\little_bee_error.song",false},
					{".\\song\\two_tigers.song",true},
					{".\\song\\two_tigers_error.song",false}
				};
		return Arrays.asList(Parameters);
	}
	
	@Test
	public void testBeat()
	{
		try
		{
			FileReader fr = new FileReader(FilePath);  
	        BufferedReader br=new BufferedReader(fr);  
	        String Line,InputString="",time=null;  
	        while((Line = br.readLine()) != null)
	        {  
	        	if(time==null) time=Line;
	        	else InputString=InputString+Line;
	        }  
	        br.close();  
			assertEquals(new checkBeat(time,InputString).Start(),Answer);
		}
		catch(Exception e){}
	}
}