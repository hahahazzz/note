# GreenDao3-3

## Join

> Join可以将多个表的查询结果合并为一个结果集.

- 考虑一种情况:用户User实体类可能对应着多个地址Address实体,如果我们想查询指定address的user,我们必须用join将address和user实体类连接起来.
                
        QueryBuilder<User> queryBuilder = userDao.queryBuilder();
        queryBuilder.join(Address.class, AddressDao.Properties.userId)
        .where(AddressDao.Properties.Street.eq("Sesame Street"));
        List<User> users = queryBuilder.list();
---

#### 1.QueryBuilder Join API

> 如果主键可用,那么可以省略join属性.所以QueryBuilder中有三个重载的连接方法可用

        /**
         * 通过使用JOIN将查询扩展到另一个实体类型。 此QueryBuilder的主要实体的主键属性用于匹配给定的destinationProperty。
         */
         public <J> Join<T, J> join(Class<J> destinationEntityClass, Property destinationProperty)

        /**
         * 通过使用JOIN将查询扩展到另一个实体类型。sourceProperty用于匹配给定destinationEntity的主键属性。
         */
         public <J> Join<T, J> join(Property sourceProperty, Class<J> destinationEntityClass)

        /**
         * 通过使用JOIN将查询扩展到另一个实体类型。 sourceProperty用于匹配给定destinationEntity的destinationProperty。
         */
         public <J> Join<T, J> join(Property sourceProperty, Class<J> destinationEntityClass,Property destinationProperty)
---

#### 2. Chained Joins

- greenDao允许链式join多张表.你可以使用另一个join和目标实体来定义一个join,这种情况下,第一个连接的目标实体成为第二个连接的起始实体.

        /**
         * 通过JOIN将查询扩展到另外一个实体类型.给定的sourceJoin的属性用于匹配给定目标实体的目标属性.
         * 需要注意的是,给定join的目标实体将用作要添加的新的join的源.这样的话,可以在多个实体中组合复杂的join.
         */
         public <J> Join<T, J> join(Join<?, T> sourceJoin, Property sourceProperty,Class<J> destinationEntityClass, Property destinationProperty)

- 实例:城市国家和大陆,如果说我们想查询Europe至少有100万人口的Cities,大概是这样

        QueryBuilder<City> qb = cityDao.queryBuilder().where(Properties.Population.ge(1000000));
        Join country = qb.join(Properties.CountryId, Country.class);
        Join continent = qb.join(country, CountryDao.Properties.ContinentId,
        Continent.class, ContinentDao.Properties.Id);
        continent.where(ContinentDao.Properties.Name.eq("Europe"));
        List<City> bigEuropeanCities = qb.list();
---

#### 3. Self Join / Tree example

- 连接也可以用于引用单个实体的关系。

- 例如，我们想找到所有的人，他的祖父的名字叫“Lincoln”。假设我们有一个Person实体，一个指向同一个实体的parentId属性。那么查询是这样构建的：

        QueryBuilder<Person> qb = personDao.queryBuilder();
        Join father = qb.join(Person.class, Properties.FatherId);
        Join grandfather = qb.join(father, Properties.FatherId, Person.class, Properties.Id);
        grandfather.where(Properties.Name.eq("Lincoln"));
        List<Person> lincolnDescendants = qb.list();
---

## Relations实体的关系

- 数据库表可能会有1:1,1:N或者N:M的关系.

- greenDao中,实体使用一对一或多对关系进行关联

    - 例如,要在greenDao中简历1:n关系,你讲拥有1:1关系.注意,1:1和1:n关系并不不相关联,所以需要更新两者.

#### 1. 建立1:1关系

- @ToOne

    - 定义与其他实体的关系,应用于持有其他实体引用的属性.

    - 在内部，greenDAO需要一个附加属性，指向由joinProperty参数指定的目标实体的ID 。如果此参数不存在，则会自动创建一个附加列来保存该键。

            @Entity
            public class Order {
                @Id private Long id;

                private long customerId;

                @ToOne(joinProperty = "customerId")
                private Customer customer;
            }

            @Entity
            public class Customer {
                @Id private Long id;
            }

#### 2. 建立1:n关系

- @ToMany

    - 定义与一组其他实体(多个实体对象)的关系,将其应用于表示目标实体列表的属性.引用的实体必须有一个或多个属性指向拥有@ToMany的实体.

    - referencedJoinProperty
    
        - 指定指向该实体的ID的目标实体中的“foreign key”属性的名称。

                @Entity
                public class Customer {
                    @Id private Long id;

                    @ToMany(referencedJoinProperty = "customerId")
                    @OrderBy("date ASC")
                    private List<Order> orders;
                }

                @Entity
                public class Order {
                    @Id private Long id;
                    private Date date;
                    private long customerId;
                }


    - joinProperties
    
        - 对于更复杂的关系，您可以指定@JoinProperty注释的列表。每个@JoinProperty需要原始实体中的源属性和目标实体中的引用属性。

                @Entity
                public class Customer {
                    @Id private Long id;
                    @Unique private String tag;

                    @ToMany(joinProperties = {
                            @JoinProperty(name = "tag", referencedName = "customerTag")
                    })
                    @OrderBy("date ASC")
                    private List<Site> orders;
                }

                @Entity
                public class Order {
                    @Id private Long id;
                    private Date date;
                    @NotNull private String customerTag;
                }

    - @JoinEntity
    
        - 如果您正在执行涉及另一个连接实体/表的N：M（多对多）关系，请将此附加注释放在属性上

                @Entity
                public class Product {
                    @Id private Long id;

                    @ToMany
                    @JoinEntity(
                            entity = JoinProductsWithOrders.class,
                            sourceProperty = "productId",
                            targetProperty = "orderId"
                    )
                    private List<Order> ordersWithThisProduct;
                }

                @Entity
                public class JoinProductsWithOrders {
                    @Id private Long id;
                    private Long productId;
                    private Long orderId;
                }

                @Entity
                public class Order {
                    @Id private Long id;
                }
- 1:n关系实体的更新

    - 第一次请求时解析关系,然后缓存到List的源实体中.所以后续的get方法不会查询数据库.

    - 更新1:n关系,需要一些额外的工作,例如添加操作,需要先将添加的实体保存到数据库,然后再添加到List里.删除也类似,先从数据库delete实体,然后再从List中remove掉.

#### 3. 双向1:n关系

- 有时你想在两个方向上浏览1:n关系.在greenDao中,必须添加一对一和多对多关系才能实现.

- 示例:客户和订单实体

        @Entity
        public class Customer {
            @Id private Long id;

            @ToMany(referencedJoinProperty = "customerId")
            @OrderBy("date ASC")
            private List<Order> orders;
        }

        @Entity
        public class Order {
            @Id private Long id;
            private Date date;
            private long customerId;

            @ToOne(joinProperty = "customerId")
            private Customer customer;
        }

    - 假设我们有一个订单实体。使用这两种关系，我们可以得到客户和客户所做的所有订单

            List<Order> allOrdersOfCustomer = order.getCustomer().getOrders();

#### 4.示例：建模树关系

        @Entity
        public class TreeNode {
            @Id private Long id;

            private Long parentId;

            @ToOne(joinProperty = "parentId")
            private TreeNode parent;

            @ToMany(referencedJoinProperty = "parentId")
            private List<TreeNode> children;
        }