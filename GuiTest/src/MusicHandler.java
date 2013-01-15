import java.util.Vector;

import org.jfugue.*;

public class MusicHandler 
{
	Vector MusicString;
	int length;
	MusicHandler(Vector s,int l)
	{
		MusicString=s;
		length=l;
	}
	public void Run()
	{
		Pattern song=new Pattern();
		song.add("I24");
		Player player = new Player();
		for(int i=0;i<length;i++)
		{
			if(MusicString.get(i)!=null)
			{
				if(MusicString.get(i).toString().matches("[A-G|R|\\|].*"))
				{
					song.add(MusicString.get(i).toString());
				}
			}
		}
		player.play(song);
	}
}
