# CompilerProject

### 一、简介

> 2018~2019学年*《编译原理》*课程Project

> *肖仁杰(15307130260)*、*张政(15307130133)*

### 二、功能

项目中已实现的功能如下：

+ 语法词法分析输出AST
+ 语义分析
    + 符号重复定义
    + 循环继承
    + 类型未定义
    + 符号未定义

+ 错误处理
    + 输出错误类型
    + 错误恢复

+ 简易的后端

### 三、使用方法

> **待完成**

在out文件夹中打开cmd命令行，输入如下命令即可运行：

​	``` java -jar CompilerProject.jar -noVisitor < Right_factorial.java```

或	```java -jar CompilerProject.jar -Visitor < factorial.java```

其中，使用了输入重定向运算符 ``` < ```，测试其他样例代码时只需要修改后面的文件名即可，out文件夹下已经提供了几个测试样例。第二行的命令通过参数```-Visitor```指定使用后端解释器输出（由于功能不够完善，此处的测试例子为factorial.java，不可更改）

### 四、分工

+ 张政_15307130133
    + 语义分析，包括两次遍历的类（ScopeChecker和SymbolChecker）以及相关的数据结构（ClassNode和MethodNode）
    + 简易的后端解释器，MiniJavaVisitor类
    + 部分实验报告
    + README.md
    + PPT制作
    + 错误样例代码
    + 程序打包

+ 肖仁杰_15307130260
    + 