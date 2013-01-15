import java.util.Scanner;
import java.util.Vector;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;


public class FormatTransformer
{
	Vector Output;
	String Input;
	int Index;
	StyledDocument Doc;
	final static String NoteCorrespond="RCDEFGAB89";
	FormatTransformer()
	{
		Index=0;
	}

	Vector NoteTransform(String s,int note,int Tune,int Dot)
	{
		//String input = s;
		Output=new Vector();
		char[] Input = s.toCharArray();
		int len = s.length();
		char [] Ans = new char[50];
		int index = 0,Octave=5;
		boolean flag = false;
		char record = ' ';
		for(int i=0;i<len;i++)
		{
			
			if(Input[i] == 'b' || Input[i] == '#')
			{
				flag = true;
				record = Input[i];
				Ans[index]=Input[i];
				index++;
			}
			else if((Input[i]-'0')>=0 && (Input[i]-'0')<=7)
			{
				if(Input[i]=='0')
				{
					Ans[index]='R';
					index++;
					String ans=new String(Ans).trim();
					Output.addElement(ans);
					Ans = new char[50];
					index = 0;
					continue;
				}
				Ans[index] = NoteCorrespond.charAt(Input[i]-'0');
				index++;
				if(flag == true)
				{
					Ans[index] = record;
					index++;
					flag = false;
				}
				Ans[index]=Character.forDigit(Octave+Tune,10);
				index++;
				switch(note)
				{
					case 1:
						Ans[index] = 'w';
						index++;
					break;
					case 2:
						Ans[index] = 'h';
						index++;
						break;
					case 4:
						Ans[index] = 'q';
						index++;
						break;
					case 8:
						Ans[index] = 'i';
						index++;
						break;
					case 16:
						Ans[index] = 's';
						index++;
						break;
					case 32:
						Ans[index] = 't';
						index++;
						break;
				
				}
				if(Dot>0)
				{
					Ans[index] = '.';
					index++;
				}
			}
			else
			{
				Ans[index]=Input[i];
				index++;
			}
			String ans=new String(Ans).trim();
			Output.addElement(ans);
			Ans = new char[50];
			index = 0;
		}
		return Output;
	}
	
	public void FormatToNote(String s,StyledDocument d)
	{
		Input=s;
		Doc=d;
		Scanner scanner = new Scanner(Input);
		String InputSection = scanner.next();
		while(InputSection != null)
		{
			if(InputSection.matches(".+\\|.+")) 
			{
				String[] tmp1 = InputSection.split("\\|");
				String front = tmp1[0];
				String back = tmp1[1];
				process(front);
				/*try
				{
					Doc.insertString(Index,"|",null);
					Index++;
				}
				catch(Exception ex){}*/
				process("|");
				process(back);
			}
			else if(InputSection.matches("\\|.*"))//BEGIN
			{
				InputSection = InputSection.replaceAll("\\|","");
				process("|");
				process(InputSection);
			}
			else if(InputSection.matches(".*\\|.*"))//END
			{
				InputSection = InputSection.replaceAll("\\|","");
				process(InputSection);
				process("|");
			}
			else
			{
				process(InputSection);
			}
			if(!scanner.hasNext())
			{
				try
				{
					Doc.insertString(Index,"\n",null);
					Index++;
				}
				catch(Exception ex){}
				return;
			}
			InputSection = scanner.next();
		}

	}
	
	public void process(String NoteInform)
	{
		MutableAttributeSet attr = new SimpleAttributeSet(); 
		if(NoteInform=="|")
		{
			try
			{
				Doc.insertString(Index,"|",null);
				Index=Index+1;
			}
			catch(Exception ex){}
			return;
		}
		if(NoteInform.matches("[A-G]+b.*"))
		{
			try
			{
				Doc.insertString(Index,"b",null);
				Index++;
			}
			catch(Exception ex){}
		}
		
		else if(NoteInform.matches("[A-G]+#.*"))
		{
			try
			{
				Doc.insertString(Index,"#",null);
				Index++;
			}
			catch(Exception ex){}
		}
		
		if(NoteInform.matches("[A-G]+.*[1-9].*"))
		{
			if(Character.isDigit(NoteInform.charAt(1)))
			{
				attr.addAttribute("Tune",new Integer(NoteInform.charAt(1)-'5')); 
			}
			else
			{
				attr.addAttribute("Tune",new Integer(NoteInform.charAt(2)-'5')); 
			}
		}
		
		if(NoteInform.matches("[A-G]+.*[w|h|q|i|s|t]\\.?"))
		{
			if(NoteInform.indexOf('.')>0)
				attr.addAttribute("Dot",new Integer(1)); 
			else
				attr.addAttribute("Dot",new Integer(0)); 
			if(NoteInform.indexOf('w')>0)
			{
				attr.addAttribute("Note",new Integer(-2)); 
			}
			if(NoteInform.indexOf('h')>0)
			{
				attr.addAttribute("Note",new Integer(-1)); 
			}

			
			if(NoteInform.indexOf('q')>0)
			{
				attr.addAttribute("Note",new Integer(0)); 
			}
			
			if(NoteInform.indexOf('i')>0)
			{
				attr.addAttribute("Note",new Integer(1)); 
			}
			
			if(NoteInform.indexOf('s')>0)
			{
				attr.addAttribute("Note",new Integer(2)); 
			}
			
			if(NoteInform.indexOf('t')>0)
			{
				attr.addAttribute("Note",new Integer(3)); 
			}
		}
		
		try
		{
			Doc.insertString(Index,NoteCorrespond.indexOf(NoteInform.charAt(0))+"",attr);
			Index=Index+2;
		}
		catch(Exception ex){}
		/*if(notion[2].matches(".+\\)"))
		{
			String[] delay = notion[2].split("\\)");
			System.out.println("有延音 : "+ delay[0]);
		}
		else
		{
			System.out.println("沒有延音");
		}*/
	}
	
}
