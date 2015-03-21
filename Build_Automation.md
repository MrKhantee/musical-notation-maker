Step1.請先下載project壓縮檔

[載點](http://musical-notation-maker.googlecode.com/files/musical-notation-maker.zip)

Step2.在eclipse將project import

Step3.點擊build.xml

![http://i.imgur.com/tUV7F.png](http://i.imgur.com/tUV7F.png)

Step4.更改
```
<property name="ECLIPSE_HOME" value="../../../Desktop/eclipse"/>
```

將value改成自己eclipse的目錄(ex.C:\Users\JeffreyShe\Desktop\eclipse)

Step5.右擊build.xml -> Run As -> Ant Build 進行編譯和測試

Step6.察看結果,junit測試的結果同時會存於./report底下

![http://i.imgur.com/gYwWW.png](http://i.imgur.com/gYwWW.png)


測試檔案說明:

我們用了數首簡譜檔案做為測試檔案,**主要是要測試此簡譜的總拍數是否符合預期**

格式是我們自訂的,詳細格式請參照[此處](http://musical-notation-maker.googlecode.com/files/%E6%AA%94%E6%A1%88%E6%A0%BC%E5%BC%8F.docx)

然後將測試檔案路徑和此首簡譜預期有的總拍數作為參數來進行unit test

ex.{".\\song\\little\_star.song",64.0} -- {測試用簡譜檔案路徑,預期總拍數}

而little\_star\_error.song為錯誤的簡譜,故測試完此case會有failure

細部部分可參照BeatTester.java

而CheckBeat.java主要是return此樂譜檔案的總拍數



_運行環境:_

_Win7 32位元_

_JDK1.7_

_Junit4_