# JFugue格式說明 #

_p.s 僅使用到的部分作介紹,它仍然有許多功能_

**1.音階**

可以用C, D, E, F, G, A, B 表示 do re me fa so la ti

也可以用數字表示  ex：60 則是這個API的”5度音C”

**2.升降記號和小節**

#：升記號

b：降記號

|:小節

**3.音高**

表示音階是幾度音

他的音符default 是 Octave 5 為我們在五線譜上最熟悉的基本 Do

ex： C6 即是C5的高八度

**4.音長**

一個音符的長度：
w：1	全音符

h：1/2	二分音符

q：1/4	四分音符

i：1/8	…

s：1/16	…

t：1/32

x：1/64

o：1/128

ex.C5q 代表四分音符Do

**程式範例:**
```
// pattern1 "兩隻老虎"
Pattern pattern1 = new Pattern("C5q D5q E5q C5q");

// pattern2 "跑得快"
Pattern pattern2 = new Pattern("E5q F5q G5h");

// pattern3 "一隻沒有XX"
Pattern pattern3 = new Pattern("G5i A5i G5i F5i E5q C5q");

// pattern4 "真奇怪"
Pattern pattern4 = new Pattern("C5q G4q C5h");

// 將上面的 pattern1~pattern4 加入歌曲 song
Pattern song = new Pattern();
song.add(pattern1, 2); // 加入兩個 'pattern1' 至歌曲
song.add(pattern2, 2); // 加入兩個 'pattern2' 至歌曲
song.add(pattern3, 2); // 加入兩個 'pattern3' 至歌曲
song.add(pattern4, 2); // 加入兩個 'pattern4' 至歌曲

// 播放歌曲 song
Player player = new Player(); player.play(song);
```