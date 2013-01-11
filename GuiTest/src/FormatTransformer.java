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
	final static String NoteCorrespond="XCDEFGAB";
	FormatTransformer()
	{
		Output=new Vector();
		Index=0;
	}

	Vector NoteTransform(String s,int note,int Tune)
	{
		//String input = s;
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
			}
			else if(Character.isDigit(Input[i]))
			{
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
				String ans=new String(Ans).trim();
				Output.addElement(ans);
				Ans = new char[50];
				index = 0;
			}
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
				try
				{
					Doc.insertString(Index,"|",null);
					Index++;
				}
				catch(Exception ex){}
				process(back);
			}
			else if(InputSection.matches("\\|.*"))//BEGIN
			{
				InputSection = InputSection.replaceAll("\\|","");
				try
				{
					Doc.insertString(Index,"|",null);
					Index++;
				}
				catch(Exception ex){}
				process(InputSection);
			}
			else if(InputSection.matches(".*\\|.*"))//END
			{
				InputSection = InputSection.replaceAll("\\|","");
				process(InputSection);
				try
				{
					Doc.insertString(Index,"|",null);
					Index++;
				}
				catch(Exception ex){}
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
		
		if(NoteInform.matches("[A-G]+.*[h|q|i|s|t]"))
		{
			if(NoteInform.charAt(NoteInform.length()-1)=='h')
			{
				attr.addAttribute("Note",new Integer(-1)); 
			}
			else if(NoteInform.charAt(NoteInform.length()-1)=='q')
			{
				attr.addAttribute("Note",new Integer(0)); 
			}
			else if(NoteInform.charAt(NoteInform.length()-1)=='i')
			{
				attr.addAttribute("Note",new Integer(1)); 
			}
			else if(NoteInform.charAt(NoteInform.length()-1)=='s')
			{
				attr.addAttribute("Note",new Integer(2)); 
			}
			else if(NoteInform.charAt(NoteInform.length()-1)=='t')
			{
				attr.addAttribute("Note",new Integer(3)); 
			}
		}
		
		try
		{
			Doc.insertString(Index,NoteCorrespond.indexOf(NoteInform.charAt(0))+" ",attr);
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
