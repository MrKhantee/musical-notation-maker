Unit Test說明:

我們用了數首簡譜檔案做為測試檔案,**主要是要測試此編輯器輸出之簡譜檔案是否符合預期**

我們以確認節拍數的方式來進行檢查

利用所寫之checkBeat來對檔案做批次測試

checkBeat會檢查每個小節之節拍是否符合拍號所規定,如果整個簡譜檔案有任何

一小節錯誤,即回傳False,均符合規定就回傳True

而Unit Test Case則如下:

```
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
```


**{".\\song\\little\_bee.song",true}**

代表Junit會將.\\song\\little\_bee.song和true作為參數

以前者作為路徑讀入整個簡譜檔案的內容至time(拍號),InputString(音符內容)

並且預期checkBeat會回傳true

### Result: ###

```
Buildfile: C:\Users\jeffreyshe\workspace\musical-notation-maker\GuiTest\build.xml
clean:
   [delete] Deleting directory C:\Users\jeffreyshe\workspace\musical-notation-maker\GuiTest\bin
   [delete] Deleting directory C:\Users\jeffreyshe\workspace\musical-notation-maker\GuiTest\report
prepare:
    [mkdir] Created dir: C:\Users\jeffreyshe\workspace\musical-notation-maker\GuiTest\bin
    [mkdir] Created dir: C:\Users\jeffreyshe\workspace\musical-notation-maker\GuiTest\report
compile:
    [javac] C:\Users\jeffreyshe\workspace\musical-notation-maker\GuiTest\build.xml:31: warning: 'includeantruntime' was not set, defaulting to build.sysclasspath=last; set to false for repeatable builds
    [javac] Compiling 5 source files to C:\Users\jeffreyshe\workspace\musical-notation-maker\GuiTest\bin
    [javac] Note: Some input files use unchecked or unsafe operations.
    [javac] Note: Recompile with -Xlint:unchecked for details.
test:
    [junit] Running BeatTester
    [junit] Tests run: 4, Failures: 0, Errors: 0, Time elapsed: 0.019 sec
BUILD SUCCESSFUL
Total time: 952 milliseconds
```

![http://i.imgur.com/WE31a.png](http://i.imgur.com/WE31a.png)