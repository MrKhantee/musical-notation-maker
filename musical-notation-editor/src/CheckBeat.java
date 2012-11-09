/*
Author:Anna
Description:Read All files in folder "song" then calculate beats and check.
Update date:2012/11/9 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckBeat 
{
	private static String[] Input_String = new String[10000];//行數最大值:10000
	private static int InputIndex;
	private String FilePath;
	public Float TotalBeat;
	/*讀取song資料夾下所有檔案*/
	
	public CheckBeat(String Path)
	{
		FilePath=Path;
		try
		{
			ReadFile();
		}
		catch(Exception e)	{}

	}
	
	private void ReadFile() throws IOException
	{
			FileReader fr = new FileReader(FilePath);  
	        BufferedReader br=new BufferedReader(fr);  
	        String Line;  
	        InputIndex = 0;
	        while((Line = br.readLine()) != null)//讀取每一行
	        {  
	            Input_String[InputIndex] = Line;//存入Input_String Array
	            InputIndex++;
	        }  
	        br.close();  
	        Process(); 

	}
	/*從檔案分割出每一小節*/
	private void Process()
	{
		String[] FirstRow = Input_String[0].split("/");
		String[] Section = new String[10];
		int CorrectSectionBeat = Integer.valueOf(FirstRow[0]).intValue();//每一音檔中規定每一小節應該要幾拍
		int Standard = Integer.valueOf(FirstRow[1]).intValue();//每一音檔規定幾分音符是一拍
		int SectionId = 1;
		float TotalBeat=0;
		/*抓出小節*/
		for(int i=1 ; i<InputIndex ; i++)
		{
			Section = Input_String[i].split("\\|");
			int HowManySection;
			HowManySection = Section.length;
			for(int j=1;j<=HowManySection-1;j++)
			{
				float  Beat = ReturnSectionBeat(Section[j],Standard);
				TotalBeat=TotalBeat+Beat;
				//System.out.println("節拍數:"+Beat+" "+"小節數:"+SectionId+" 標準節拍數:"+CorrectSectionBeat);//印出是音檔中哪一小節和算出的節拍數
				/*
				 可以在這邊check每一音檔每一小節是否有節拍錯誤 
				if(sectionBeat == Beat)  
				{
					正確!!!
				}  
				*/
				SectionId++;
			}
		}
		this.TotalBeat=new Float(TotalBeat);
	}
	/*計算每一小節節拍數*/
	private float ReturnSectionBeat(String Section,int standerd)
	{
		Pattern Pattern2;
        Matcher Matcher2;
        int Note = 0;//這是幾分音符
        float Delay = 0;//這是多少延音
        float AnsBeat = 0;//最後那一小節的節拍數
		//System.out.println(Section);
		Pattern pattern = Pattern.compile("\\([#|b]??,.*?,.*?\\)");//(給自己的註解:match最短    p.s括號跟|都算特殊字元)
        Matcher Matcher = pattern.matcher(Section);
        while (Matcher.find())
        {
            String Section1;
            Section1 = Matcher.group();
            Pattern2 = Pattern.compile("\\d+");
            Matcher2 = Pattern2.matcher(Section1);
            int HasSecond = 0;//看有沒有延音的變數
            while (Matcher2.find())
            { 
                if(HasSecond == 0)
                {
                	Note = Integer.valueOf(Matcher2.group());
                }
                else
                {
                	Delay = Integer.valueOf(Matcher2.group());
                }
                HasSecond++;
            }
            float Rbeat = (float)Note/standerd;//節拍的倒數
            float Beat = 1/Rbeat;//節拍
            if(HasSecond == 1)
            {
            	AnsBeat += Beat+Delay;//把每一小節的節拍加起來
            }
            else
            {
            	AnsBeat += Beat;//如果有延音,加上去
            }
        }
		return AnsBeat;
	}
	/**
	 * @param args
	 * @throws IOException 
	 */

}
