## Abstract Factory ##
**描述**:

我們找的reference內有許多不同的class，每個class都將該物件所有的函式(功能)集中在一個class內定義，並會互相結合。類似多個factory，每個factory有很多個產品一般的情況，每個factory將產品與功能都集合在一塊，多個factory之間的結合得到程式想要的結果。

**範例**:

整個Toolbar會用一個class包起來，定義所有有關Toolbar的函式，例如: UpdateButton()，DrawButton()，DoPaint()等等，並宣告他們的屬性(public，protect，private)。
Reference內共有14個class，例如Beatbox.cpp就會與其他三個class互相結合等。