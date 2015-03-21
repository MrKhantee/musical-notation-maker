## 本次進度 ##
完成簡譜編輯器音長,音高的部分

![http://i.imgur.com/tMrCX.png](http://i.imgur.com/tMrCX.png)

---


## 音符說明 ##

**音長:**

通常只有數字的是四分音符。數字下加一條橫線，就可令四分音符的長度減半，即成為八分音符；兩條橫線可令八分音符的長度減半，即成為十六分音符，餘此類推；簡單來説，下加橫線數目與五線譜的符尾數目相對應。數字後方的橫線延長音符，每加一條橫線延長一個四分音符的長度。

正如五線譜的附點一樣，數字後方加一點會將音符長度增加一半。

4th:四分音符

8th:八分音符...依此類推

**音高:**

如果是高一個八度，就會在數字上方加上一點。如果是低一個八度，就會數字下方加上一點。在中間的那一個八度就什麼也不用加。如果要再高一個八度，就在上方垂直加上兩點；要再低一個八度，就在下方垂直加上兩點，如此類推。

HTune:高八度
LTune:低八度...依此類推

![http://i.imgur.com/UF7G1.png](http://i.imgur.com/UF7G1.png)

## Design pattern ##

### **Mediator 模式** ###

由於一次只能選擇一種音高,一種音長,所以這些按鈕勢必得有互斥關係

java內建的button group已經可以做到選擇一個button時,自動取消其他button

但實際上這些被取消的button並不知道自己被取消了,所以沒辦法做出自己被取消時的action

我們可以實作一個中介者做為button間的溝通介面

但由於按鈕被取消時,我們要做的只有讓按鈕在gui上呈現下陷的狀態

所以可以單純用一個物件作為簡單的中介者,紀錄上一個被選擇的按鈕
```
private JToggleButton selectedNoteButton;   //音長中介者
private JToggleButton selectedTuneButton;   //音高中介者
```

當有新的按鈕被選擇時,讓上一個被選擇的按鈕下陷


```
if(selectedNoteButton!=null)
{
	selectedNoteButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
}
```