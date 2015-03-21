# Simple Factory #

一個Simple Factory生產成品，而對客戶端隱藏產品產生的細節，

物件如何生成，生成前是否與其它物件建立依賴關係，

客戶端皆不用理會，用以將物件生成方式之變化 與客戶端程式碼隔離。

### 概念圖 ###

![http://i.imgur.com/xqDQt.gif](http://i.imgur.com/xqDQt.gif)

只要給予適當的參數

就可以使用這些物件來執行他們的工作而我們無須知道細節

**FormatTransformer**:執行Jfugue格式和GUI上音符之間的轉換

**MusicHandler**:播放Jfugue格式的字串

**checkBeat**:節拍數的檢查