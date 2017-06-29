# GreenDao3

## 参考

1. [GreenDao Documentation](http://greenrobot.org/greendao/documentation/how-to-get-started/)

2. [GreenDao](https://github.com/greenrobot/greenDAO)
---

## 1. GreenDao3的基本使用

#### 1. 引入

    // 项目根目录下build.gradle
    buildscript {
        repositories {
            jcenter()
            mavenCentral() // add repository
        }
        dependencies {
            classpath 'com.android.tools.build:gradle:2.3.1'
            classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2' // add plugin
        }
    }
 
    // App目录下build.gradle
    apply plugin: 'com.android.application'
    apply plugin: 'org.greenrobot.greendao' // apply plugin
    
    dependencies {
        compile 'org.greenrobot:greendao:3.2.2' // add library
    }

#### 2. 创建实体类

- 实体类以@Entity标注
>

    @Entity(indexes = {
        @Index(value = "text, date DESC", unique = true)
    })
    public class Note {

        @Id
        private Long id;

        @NotNull
        private String text;
        private String comment;
        private java.util.Date date;
        // 转换器,枚举和字符串互转
        @Convert(converter = NoteTypeConverter.class, columnType = String.class)
        private NoteType type;
        
        // 生成的方法,不需要手动添加.
        @Generated(hash = 1272611929)
        public Note() {
        }
        // 手动添加的构造
        public Note(Long id) {
            this.id = id;
        }

        // 生成的方法,不需要手动添加.
        @Generated(hash = 1686394253)
        public Note(Long id, @NotNull String text, String comment, java.util.Date date, NoteType type) {
            this.id = id;
            this.text = text;
            this.comment = comment;
            this.date = date;
            this.type = type;
        }
        // 其他get/set方法
        ....
    }

#### 3. 创建DaoSession

    // 参数分别为Context,db文件的文件名
    DevOpenHelper helper = new DevOpenHelper(Context,String);
    // 加密,参数是加密密码
    Database encryptedReadableDb = helper.getEncryptedWritableDb(String);
    // Database writableDb = helper.getWritableDb();
    // 创建DaoSession
    DaoSession daoSession = new DaoMaster(encryptedReadableDb).newSession();

#### 4. 获取NoteDao

    DaoSession daoSession = ((App) getApplication()).getDaoSession();
    NoteDao noteDao = daoSession.getNoteDao();

#### 5. 数据操作

    // 初始化Note对象
    Note note=...;
    note.setXXX....
    // insert
    noteDao.insert(note);
    // update
    noteDao.update(note);
    // delete
    noteDao.deleteByKey(id);
---

## 2. 核心类

- DaoMaster

    - 使用GreenDao的入口点,持有数据库对象(SQLiteDatabase),以及管理特定模式的DAO类(不是对象).

    - 拥有创建和删除表的静态方法.

    - 两个内部类:OpenHelper 和DevOpenHelper ,是在SQLite数据库中创建模式的SQLiteOpenHelper实现。

- DaoSession

    - 管理特定模式的所有可用的Dao对象

    - 提供一些通用的持久性方法,如插入,加载,更新,刷新和删除.

- DAOs

    - 持久化和查询实体.

    - 每个实体都会生成一个DAO

    - 很多的持久化方法,如count,loadAll,insertInTx等.

- Entities

    - 持久对象,通常以标准Java属性表达,如POJO或JavaBean.
---

## 3. 建模实体

> 要在项目中使用greenDao,可以创建一个表示应用程序中持久数据的实体模型,然后让greenDao基于此模型生成Java代码.

- 配置schema版本

        greendao {
            schemaVersion 2
        }

    - schemaVersion：当前版本的数据库模式。用于OpenHelpers类在模式版本之间迁移。如果您更改了实体/数据库模式，则必须增加该值。默认为1。

#### 1. 实体和注解

- greenDAO 3使用注释来定义模式和实体

        @Entity
        public class User {
            @Id
            private Long id;

            private String name;

            @Transient
            private int tempUsageCount; // not persisted

            // getters and setters for id and user ...
        }

    - @Entity注解
    
        > 将Java类转变为数据库对象,将该Java类标记为需要持久化的实体,同时也会让greenDao3生成必要的代码,例如Dao操作类.

    - @Entity注解的一些参数

        - schema : schema= "myschema"

            - 如果有多个schema,可以使用此参数指定实体类所属的schema.

        - active : active = true

            - 标记某个实体处于active状态,处于active状态的实体拥有update,delete和刷新方法.

        - nameInDb : nameInDb = "AWESOME_USERS"

            - 指定实体在数据库中的表名,默认情况下,表名就是类名.

        - indexes : indexes = {@Index(value = "name DESC", unique = true)}

            - 定义多列索引

        - createInDb : createInDb = false

            - DAO是否应该创建表的标记,默认为true.
            
            - 如果有多个实体映射到同一张表,或者表已经在greenDao之外完成创建,那么将此标记设为false

        - generateConstructors : generateConstructors = true

            - 是否生成含有所有属性字段的构造函数

            - 必须:一个无参构造函数.

        - generateGettersSetters : generateGettersSetters = true

            - 如果属性字段缺少get/set方法,是否自动生成
---

#### 2. 基本属性

- @Id

    - 标注实体ID,在数据库中表现为主键.

    -  @Id(autoincrement = true),主键自增长