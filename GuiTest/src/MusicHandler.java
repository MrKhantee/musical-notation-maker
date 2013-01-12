import java.util.Vector;

import org.jfugue.*;

public class MusicHandler 
{
	Vector MusicString;
	MusicHandler(Vector s)
	{
		MusicString=s;
	}
	public void Run()
	{
		Pattern song=new Pattern();
		song.add("I24");
		Player player = new Player();
		for(int i=0;i<MusicString.size();i++)
		{
			if(MusicString.get(i)!=null)
			{
				if(MusicString.get(i).toString()=="|")
				{
					song.add(MusicString.get(i).toString());
				}
				else
				{
					
					song.add(MusicString.get(i).toString());
				}
			}
		}
		player.play(song);
	}
}
