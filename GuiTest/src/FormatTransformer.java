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
		int index = 0	;
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
				if(Tune>0)
				{
					for(int x=0;x<Tune;x++)
					{
						Ans[index]='.';
						index++;
					}
				}
				Ans[index] = Input[i];
				index++;
				if(Tune<0)
				{
					for(int x=0;x<Math.abs(Tune);x++)
					{
						Ans[index]='.';
						index++;
					}
				}
				Ans[index] = '(';
				index++;
				if(flag == true)
				{
					Ans[index] = record;
					index++;
					flag = false;
				}
				Ans[index] = ',';
				index++;
				switch(note)
				{
					case 4:
						Ans[index] = '4';
						index++;
						break;
					case 8:
						Ans[index] = '8';
						index++;
						break;
					case 16:
						Ans[index] = '1';
						index++;
						Ans[index] = '6';
						index++;
						break;
					case 32:
						Ans[index] = '3';
						index++;
						Ans[index] = '2';
						index++;
						break;
				
				}
				Ans[index] = ',';
				index++;
				Ans[index] = ')';
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
			if(InputSection.matches(".*\\)\\|.+")) 
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
		String[] tmp2 = NoteInform.split("\\(");
		String front2 = tmp2[0];
		String back2 = tmp2[1];
		MutableAttributeSet attr = new SimpleAttributeSet(); 
		if(front2.matches(".[0-9]+")) 
		{
			attr.addAttribute("Tune",new Integer(1)); 
		}
		else if(front2.matches("..[0-9]+"))
		{
			attr.addAttribute("Tune",new Integer(2)); 
		}
		else if(front2.matches("...[0-9]+"))
		{
			attr.addAttribute("Tune",new Integer(3)); 
		}
		else if(front2.matches("[0-9]+."))
		{
			attr.addAttribute("Tune",new Integer(-1)); 
		}
		else if(front2.matches("[0-9]+.."))
		{
			attr.addAttribute("Tune",new Integer(-2)); 
		}
		else if(front2.matches("[0-9]+..."))
		{
			attr.addAttribute("Tune",new Integer(-3)); 
		}
		else
		{
			attr.addAttribute("Tune",new Integer(0)); 
		}
		front2 = front2.replaceAll("\\.","");
		if(back2.matches("#.*")) 
		{
			try
			{
				Doc.insertString(Index,"#",null);
				Index++;
			}
			catch(Exception ex){}
		}
		else if(back2.matches("b.*"))
		{
			try
			{
				Doc.insertString(Index,"b",null);
				Index++;
			}
			catch(Exception ex){}
		}
		String[] notion = back2.split(",");   
		if(Integer.parseInt(notion[1])==4)
		{
			attr.addAttribute("Note",new Integer(0)); 
		}
		else if(Integer.parseInt(notion[1])==8)
		{
			attr.addAttribute("Note",new Integer(1)); 
		}
		
		else if(Integer.parseInt(notion[1])==16)
		{
			attr.addAttribute("Note",new Integer(2)); 
		}
		
		else if(Integer.parseInt(notion[1])==32)
		{
			attr.addAttribute("Note",new Integer(3)); 
		}
		
		try
		{
			Doc.insertString(Index,front2,attr);
			Index++;
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
