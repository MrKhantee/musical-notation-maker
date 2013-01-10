import java.util.Vector;

import org.jfugue.*;

public class MusicHandler 
{
	Vector MusicString;
	final static String NoteCorrespond[]={"x","C","D","E","F","G","A","B"};
	MusicHandler(Vector s)
	{
		MusicString=s;
	}
	public void Run()
	{
		Pattern song=new Pattern();
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
					
					song.add(NoteCorrespond[MusicString.get(i).toString().charAt(0)-'0']);
					//System.out.println(NoteCorrespond[MusicString.get(i).toString().charAt(0)-'0']);
				}
			}
		}
		player.play(song);
	}
}
