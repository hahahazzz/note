# GreenDao3-4

#### 1.自定义类型

- 自定义类型允许实体具有任何类型的属性

- 默认情况下,greenDao支持以下类型

        boolean, Boolean
        int, Integer
        short, Short
        long, Long
        float, Float
        double, Double
        byte, Byte
        byte[]
        String
        Date
---

#### 2. 转换注解和属性转换器

- 要支持自定义的类型,可以使用@Convert注释将它们映射到支持的类型之一,你还需要提供一个 PropertyConverter实现。

        @Entity
        public class User {
            @Id
            private Long id;

            @Convert(converter = RoleConverter.class, columnType = Integer.class)
            private Role role;

            public enum Role {
                DEFAULT(0), AUTHOR(1), ADMIN(2);
                
                final int id;
                
                Role(int id) {
                    this.id = id;
                }
            }

            public static class RoleConverter implements PropertyConverter<Role, Integer> {
                @Override
                public Role convertToEntityProperty(Integer databaseValue) {
                    if (databaseValue == null) {
                        return null;
                    }
                    for (Role role : Role.values()) {
                        if (role.id == databaseValue) {
                            return role;
                        }
                    }
                    return Role.DEFAULT;
                }

                @Override
                public Integer convertToDatabaseValue(Role entityProperty) {
                    return entityProperty == null ? null : entityProperty.id;
                }
            }
        }

- 注意：为了获得最佳性能，greenDAO将为所有转换使用单个转换器实例。确保除了无参数默认构造函数之外，转换器不具有任何其他构造函数。此外，确保线程安全，因为它可能在多个实体上并发调用。
---

#### 3. 自定义类型的查询

- QueryBuild不识别自定义类型,必须对查询使用原语类型,还要注意，数据库内完成的操作总是引用原始类型，例如在ORDER BY子句中。

        RoleConverter converter = new RoleConverter();
        List<User> authors = userDao.queryBuilder()
            .where(Properties.Role.eq(converter.convertToDatabaseValue(Role.AUTHOR)))
            .list();
---

#### 4. 数据库加密

- greenDAO 直接支持具有绑定的SQLCipher。SQLCipher是使用256位AES加密的SQLite的定制构建.

- 数据库初始化

    - 确保使用DaoMaster中提供的OpenHelper子类来创建数据库实例。例如DaoMaster中也提供了简单的 DevOpenHelper。

    - 创建数据库实例时，只需调用getEncryptedWritableDb(String)而不是 。getWritableDb())。最后，像往常一样将数据库传递给DaoMaster.

            DevOpenHelper helper = new DevOpenHelper(this, "notes-db-encrypted.db");
            Database db = helper.getEncryptedWritableDb("<your-secret-password>");
            daoSession = new DaoMaster(db).newSession();