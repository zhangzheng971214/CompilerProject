# CompilerProject

### 一、简介

> 2018~2019学年《编译原理》课程Project

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

### 三、使用

在```/src/out```目录下存放了编译好的CompilerProject.jar包和若干个测试脚本。在Windows环境下可直接双击脚本运行测试。
其中Run_noInterpreter.bat是不带解释器的测试脚本，展示了本编译器的常规运行方式；run_Interpreter.bat中开启了解释器。
文件名以Error开头的脚本用于演示错误处理，其输入文件中有人为设置的错误。

在```/src/out```文件夹中打开cmd命令行，输入如下命令即可运行：
    ``` java -jar CompilerProject.jar -noVisitor < filename```

例如
​	``` java -jar CompilerProject.jar -noVisitor < ../test/Right_factorial.java```

其中，```filename```为目标文件名。参数```-noVisitor```指定不使用后端解释器（由于功能不够完善，不建议在测试其他文件时开启解释器）

### 四、分工

+ 张政_15307130133
    + 语义分析，包括两次遍历的类（ScopeChecker和SymbolChecker）以及相关的数据结构（ClassNode和MethodNode）
    + 简易的后端解释器，MiniJavaVisitor类
    + 实验报告
    + README.md
    + Pre的PPT制作
    + 错误样例代码的构造
    + 打包项目文件

+ 肖仁杰_15307130260
    + BNF到ANTLR语法描述格式的翻译，编写了MiniJava.g4文件
    + 错误处理部分，完成了ExceptionHandler类，实现了错误的统一收集、分类、管理、处理
    + 完成了Scope接口和Node虚基类的逻辑和代码
    + 使用测试文件对程序的功能和稳定性进行测试
    + 准备并演示Pre
    + 撰写了实验报告的主体和部分README内容