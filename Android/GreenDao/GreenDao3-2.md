# GreenDao3-2

## 1. Sessions

> DaoSession(生成类)是greenDao中核心接口之一.DaoSession为开发人员提供基本的实体类操作,DAOs提供更完整的操作.此外,sessions还管理实体的标识域.

#### 1. DaoMaster和DaoSession

- 数据库连接属于DaoMaster,所以多个session引用着同一个数据库连接.因此,新的session可以很快的创建.但是每个Session都会分配内存,通常是实体的session缓存.

#### 2. Identity scope和session"缓存"

- 如果你有两个查询,返回相同的数据库对象,那你使用的Java对象是几个?1个或2个?这完全取决于标识域.

- greendao的默认配置下(可配置),多次查询相同的数据库对象会返回同一个Java对象的引用.例如,两次查询数据库中User表Id为42的user对象,返回的是同一个Java对象.

- 这样做的副作用是某种实体"缓存",如果一个实体对象任然存在与内存中(GreenDao此处使用了弱引用),那么GreenDao则不会再去构造实体,此外,greenDao也不执行数据库查询来更新实体,对象则会从session缓存中"立刻"返回,这样会将速度提高一个或两个数量级.

- 清除标识域

    - 清除整个Session的标识域,不会有"缓存"的对象返回

            daoSession.clear();

    - 清除单一DAO的标识域

            noteDao = daoSession.getNoteDao();
            noteDao.detachAll();
---

## 2. Query

> 查询符合调价的实体,在GreenDao中,可以使用原始SQL语句来查询,或者使用更加简单的QueryBuilder API.

> 查询支持延迟加载结果,这可能会在大型的结果集上节省内存和性能.

#### 1. QueryBuilder

> 手写SQL语句可能很困难,并且容易出错,这些错误可能在运行时才会被注意到.QueryBuilder类可以让你不用SQL语句来构建查询,帮助在编译时减少错误.

1. 简单条件示例 : 查询名为"Joe"的用户,并按照姓排序

        List<User> joes = userDao.queryBuilder()
            .where(Properties.FirstName.eq("Joe"))
            .orderAsc(Properties.LastName)
            .list();

2. 嵌套条件示例 : 获取1970年10月或以后出生的名为“Joe”的用户。

        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(Properties.FirstName.eq("Joe"),
        qb.or(Properties.YearOfBirth.gt(1970),
        qb.and(Properties.YearOfBirth.eq(1970), Properties.MonthOfBirth.ge(10))));
        List<User> youngJoes = qb.list();

3. Order : 排序

    - 可以将查询到的结果集进行排序,例如以姓氏和出生年龄排序

            // 以姓氏升序
            queryBuilder.orderAsc(Properties.LastName);

            // 倒序
            queryBuilder.orderDesc(Properties.LastName);

            // 以形式升序,并且以出生年份倒序.
            queryBuilder.orderAsc(Properties.LastName).orderDesc(Properties.YearOfBirth);

        > GreenDao默认的排序规则是COLLATE NOCASE,可以通过stringOrderCollation()进行配置.

        > 有关影响结果顺序的其他方法,参见[QueryBuilder类文档](http://greenrobot.org/files/greendao/javadoc/current/org/greenrobot/greendao/query/QueryBuilder.html)

4. Limit,Offset和分页

    - 有时候你可能只需要查询结果的一个子集,例如你只需要展示前10条结果.当你有大量的实体并且你无法通过where条件限制结果时,这将是非常有帮助,非常机制的.

        1. limit(int)

            - 限制查询的结果集数量.

        2. offset(int)

            - 与limit结合使用,设置查询结果的偏移.前offset个结果将被忽略,总的结果数量受limit影响.

            - 必须和limit结合使用.

5. 自定义类型作为参数

    - 通常,greenDao会透明的映射查询中使用的类型,比如,boolean会被映射为0或1的INTEGER,Date映射为(long)INTEGER.

    - 自定义类型是一个例外:在构建查询时,你总是必须使用数据库中的值类型.例如,将一个枚举类型通过转换器映射为int类型,在查询的时候就应该使用int类型构建查询.

6. QUery和LazyList

    - Query类表示一个查询可以被执行多次.当你使用QueryBuilder中的一个方法获取结果时,QueryBuilder内部调用了Query类.如果你想多次运行相同的查询,你应该调用QueryBuilder的build()方法来差un关键一个query而不是直接执行.

    - greenDAO支持唯一结果集(0或者1个结果)和结果集列表.如果你想要一个唯一的结果,你应调用QUery或者QUeryBuilder的unique方法,这个方法会给你一个结果,如果没有找到匹配的实体,则会返回一个null.如果实际情况不允许返回null,你可以调用uniqueOrThrow(),保证返回非null实体,否则抛出DaoException.

    - 如果期望将多个实体作为查询结果,可以使用如下方法之一

        1. list()

            - 所有实体都加载到内存.结果集通常是一个没有逻辑的ArrayList.使用比较简单.

        2. listLazy()

            - 实体按需加载到内存,并且在第一次访问,实体将被加载到内存缓存起来备用.
            
            - 使用完毕必须关闭.

        3. lstLazyUncached()

            - 实体的"虚拟"列表,任何对于结果集元素的访问都将从数据库加载数据,

            - 必须关闭.

        4. listIterator()

            - 通过迭代的方法,按需(懒)加载数据.

            - 数据未缓存,必须关闭.

            ---

            1. listLazy(), listLazyUncached(),和listIterator()方法使用的是greenDao的LazyList类.

            2. 如果按需(懒)加载数据,将会持有数据库的游标的引用,这也就是要确保关闭LazyList和Iterator的原因.

7. 执行多次查询

    - 使用QueryBuilder构建Query后,Query对象可以重用.这比重新创建一个新的Quqery对象效率要高.如果查询参数没有改变,你可以直接再次调用list/unique方法.

    - 但是,查询参数可能会被修改:为每个改变的参数使用setParameter方法.

    - 目前,各个参数通过一个从0开始的索引来指定.索引基于传入QueryBuilder的参数的顺序.

            // 获取FirstName为Joe并且1970年出生的用户
            Query<User> query = userDao.queryBuilder().where(
                Properties.FirstName.eq("Joe"), Properties.YearOfBirth.eq(1970)
            ).build();
            List<User> joesOf1970 = query.list();
            // 同样的Query对象,可以改变参数
            query.setParameter(0, "Maria");
            query.setParameter(1, 1977);
            List<User> mariasOf1977 = query.list();

8. 多线程查询

    - 如果在多线程中使用了查询,你必须调用forCurrentThread()方法为当前线程获取Query实例.

    - Query实例对象绑定到了构建他们的线程.这允许你安全地为Query对象设置参数,防止其他线程的干扰.

    - 如果其他线程尝试设置查询参数,或执行已经绑定到另一个线程的Query,会抛出以异常.

    - 类似的,你也不需要synchronized语句,实际上你应该避免locking,因为如果并发事务使用了相同的Query对象,可能会导致死锁.

    - 每次调用forCurrentThread(),Query对象都会将参数恢复到使用QueryBuilder构建时的状态.

9. Raw queries

    - 有时QueryBuilder无法提供你想要的内容,有两种办法执行原始的SQL语句可以同样返回实体对象.

        1. 首选方法 : 使用QueryBuilder和WhereCondition.StringCondition,这种方式可以将任何SQL语句片段作为where字句传递给QueryBuilder.

        2. QueryBuilder之外的第二种方法是使用queryRaw或queryRawCreate方法。它们允许您传递一个原始SQL字符串，该字符串附加在SELECT和实体列之后。这样，您可以有任何WHERE和ORDER BY子句来查询实体。可以使用别名T来引用实体表。

10. Delete queries

    - 批量删除不会删除单个实体，而是所有符合某些条件的实体。要执行批量删除，请创建一个QueryBuilder，调用其 buildDelete （）方法，然后执行返回的 DeleteQuery

11. 查询疑难排解

    - 您的查询不返回预期结果？在QueryBuilder上启用SQL和参数日志记录有两个静态标志：

            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;