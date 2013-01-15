	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;

	public class checkBeat
	{
		private static String[] Input_String = new String[10000];//行數最大值:10000
		private static int InputIndex;
		private String Time,MusicString;
		public Float TotalBeat;
		
		public checkBeat(String t,String s)
		{
			Time=t;
			MusicString=s;
		}
		
		public boolean Start() throws IOException
		{
		        return Process(); 
		}
		
		/*從檔案分割出每一小節*/
		private boolean Process()
		{
			String[] FirstRow = Time.split("/");
			String[] Section;
			double CorrectSectionBeat = Integer.valueOf(FirstRow[0]).doubleValue();//每一音檔中規定每一小節應該要幾拍
			int Standard = Integer.valueOf(FirstRow[1]).intValue();//每一音檔規定幾分音符是一拍
			float TotalBeat=0;
			/*抓出小節*/
			Section = MusicString.split("\\|");
			int HowManySection;
			HowManySection = Section.length;
			for(int j=1;j<=HowManySection-1;j++)
			{
				if(Section[j].length()>0)
				{
					float  Beat = ReturnSectionBeat(Section[j],Standard);
					TotalBeat=TotalBeat+Beat;
					if(CorrectSectionBeat != Beat)  
					{
						return false;
					}  
				}

			}
			this.TotalBeat=new Float(TotalBeat);
			return true;
		}
		/*計算每一小節節拍數*/
		private float ReturnSectionBeat(String Section,int standerd)
		{
			char Note;//這是幾分音符
	        float AnsBeat = 0;
			String[] notion = Section.split(" ");
			int len = notion.length;
			for(int i=0;i<len;i++)
			{
				char[] Notion_Array = notion[i].toCharArray();
				if(Notion_Array[1] =='#' || Notion_Array[1] =='b')
				{
					Note = Notion_Array[3];
				}
				else
				{
					Note = Notion_Array[2];
				}
				int Duration = 0;
				switch(Note)
				{
					case 'w':
						Duration = 1;
						break;
					case 'h':
						Duration = 2;
						break;
					case 'q':
						Duration = 4;
						break;
					case 'i':
						Duration = 8;
						break;
					case 's':
						Duration = 16;
						break;
					case 't':
						Duration = 32;
						break;
					case 'x':
						Duration = 64;
						break;
					case 'o':
						Duration = 128;
						break;
					default:
						System.out.println("格式錯誤");
						break;
				}
				float Rbeat = (float)Duration/standerd;//節拍的倒數
		        float Beat = 1/Rbeat;//節拍
				AnsBeat += Beat;
			}
			//System.out.println(AnsBeat);
			return AnsBeat;
		}
	}

