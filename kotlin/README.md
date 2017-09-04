# Kotlin笔记

## 基本语法

#### package

- package的声明应该位于源文件的顶部

- 不需要匹配任何目录或包,源文件可以放在任何文件系统上.

---

#### 方法

- 格式

    fun 方法名([参数:参数类型])[:返回值类型]{

    }
    
    
   或者


    fun 方法名([参数:参数类型])=xxx

- 方法没有返回值,则返回值类型为Unit,可以省略

---

#### 变量

- 可变变量

    var x =5 // 类型推断,x为Int
    x += 1  // 自增1

- 只读变量

    val a:Int = 1   // 赋值
    val b = 2       // 类型推断,b为Int
    val c:Int       // 没有赋值,则需要明确指定类型
    c=3

---

#### 字符串模板

    var a = 1
    val s1 = "a is $a"

    a = 2
    val s2 = "${s1.replace("is", "was")}, but now is $a"

---

#### 条件表达式

    fun maxOf(a: Int, b: Int): Int {
        if (a > b) {
            return a
        } else {
            return b
        }
    }

    // 可以写作

    fun maxOf(a: Int, b: Int) = if (a > b) a else b

---

#### 类型检查和自动类型转换

- is 关键字:检查一个表达式是否是一个类型的实例

- 如果一个不可变的局部变量或者属性属于特定的类型,则没有必要进行显式的类型转换

        fun getStringLength(obj: Any): Int? {
            if (obj is String) {
                // `obj`会被自动转换为String
                return obj.length
            }

            // `obj`属于Any类型
            return null
        }

        // 或者

        fun getStringLength(obj: Any): Int? {
            if (obj !is String) return null

            // `obj`会被自动转换为String
            return obj.length
        }

        // 或者

        fun getStringLength(obj: Any): Int? {
            if (obj is String && obj.length > 0) {
                return obj.length
            }

            return null
        }

---

#### 循环语句

- for

        // 直接遍历Item
        val items = listOf("apple", "banana", "kiwi")
        for (item in items) {
            println(item)
        }

        // 或者

        // 通过index遍历
        val items = listOf("apple", "banana", "kiwi")
        for (index in items.indices) {
            println("item at $index is ${items[index]}")
        }

- while

        val items = listOf("apple", "banana", "kiwi")
        var index = 0
        while (index < items.size) {
            println("item at $index is ${items[index]}")
            index++
        }

---

#### when表达式

        fun describe(obj: Any): String =
        when (obj) {
            1          -> "One"
            "Hello"    -> "Greeting"
            is Long    -> "Long"
            !is String -> "Not a string"
            else       -> "Unknown"
        }