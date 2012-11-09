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
	/*讀取song資料夾下所有檔案*/
	private static void ReadFile() throws IOException
	{
		File dir = new File("./song");//當前目錄的"song"目錄下的相對路徑
		String[] filenames = dir.list();//列出所有附錄下檔名
		for(int i=0;i<filenames.length;i++)
		{
			System.out.println(filenames[i]);
			FileReader fr = new FileReader("./song/"+filenames[i]);  
	        BufferedReader br=new BufferedReader(fr);  
	        String line;  
	        InputIndex = 0;
	        while((line = br.readLine()) != null)//讀取每一行
	        {  
	            Input_String[InputIndex] = line;//存入Input_String Array
	            InputIndex++;
	        }  
	        br.close();  
	        Process(); 
		}
	}
	/*從檔案分割出每一小節*/
	private static void Process()
	{
		String[] FirstRow = Input_String[0].split("/");
		String[] Section = new String[10];
		int sectionBeat = Integer.valueOf(FirstRow[0]).intValue();//每一音檔中規定每一小節應該要幾拍
		int standard = Integer.valueOf(FirstRow[1]).intValue();//每一音檔規定幾分音符是一拍
		int sectionId = 1;
		/*抓出小節*/
		for(int i=1 ; i<InputIndex ; i++)
		{
			Section = Input_String[i].split("\\|");
			int howManySection;
			howManySection = Section.length;
			for(int j=1;j<=howManySection-1;j++)
			{
				float  Beat = Check(Section[j],standard);
				System.out.println(Beat+" "+sectionId);//印出是音檔中哪一小節和算出的節拍數
				/*
				 可以在這邊check每一音檔每一小節是否有節拍錯誤 
				if(sectionBeat == Beat)  
				{
					正確!!!
				}  
				*/
				sectionId++;
			}
		}
	}
	/*計算每一小節節拍數*/
	private static float Check(String Section,int standerd)
	{
		Pattern pattern2;
        Matcher matcher2;
        int note = 0;//這是幾分音符
        float delay = 0;//這是多少延音
        float ansBeat = 0;//最後那一小節的節拍數
		System.out.println(Section);
		Pattern pattern = Pattern.compile("\\([#|b]??,.*?,.*?\\)");//(給自己的註解:match最短    p.s括號跟|都算特殊字元)
        Matcher matcher = pattern.matcher(Section);
        while (matcher.find())
        {
            String Section1;
            Section1 = matcher.group();
            pattern2 = Pattern.compile("\\d+");
            matcher2 = pattern2.matcher(Section1);
            int hasSecond = 0;//看有沒有延音的變數
            while (matcher2.find())
            { 
                if(hasSecond == 0)
                {
                	note = Integer.valueOf(matcher2.group());
                }
                else
                {
                	delay = Integer.valueOf(matcher2.group());
                }
                hasSecond++;
            }
            float Rbeat = (float)note/standerd;//節拍的倒數
            float beat = 1/Rbeat;//節拍
            if(hasSecond == 1)
            {
            	ansBeat += beat+delay;//把每一小節的節拍加起來
            }
            else
            {
            	ansBeat += beat;//如果有延音,加上去
            }
        }
		return ansBeat;
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub
		ReadFile(); 
	}

}
