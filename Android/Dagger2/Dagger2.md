# Dagger2

> ### 参考文章[Dagger2 入门,以初学者角度.](http://www.jianshu.com/p/1d84ba23f4d2)

#### 1. Dagger2的三个模块

- 依赖提供方Module

    - 负责提供以来中所需要的对象.实际编码中类似于工厂类.

- 依赖需求方实例

    - 声明依赖对象,在实际编码中对应业务类.

- 依赖注入组件Component

    - 负责将对象注入到依赖需求放,在实际编码中是一个接口.

#### 2. Dagger2的工作流程

- 将依赖需求方实例差un如给Component实现类.

- Component实现类根据依赖需求方实例中依赖声明,确定该实例需要依赖哪些对象.

- 确定依赖对象之后,Component会在于自己关联的Module类中查找有没有提供这些依赖对象的方法,如果有的话就将Module类中提供的依赖设置到依赖需求方实例中.

#### 3. 提供依赖的方式

- 将构造函数用@Inject标注

- 在Module里提供依赖.

    ---

    Dagger选择依赖提供的过程:

    - 查找Module中是否存在创建该类的方法

        - 若存在

            1. 若该方法存在参数,则按照步骤1依次初始化每个参数.

            2. 若不存在参数,直接初始化该类实例,一次依赖注入结束.

        - 若不存在,则查找@Inject标注的构造函数

            1. 构造函数存在参数,按照步骤1依次初始化每个参数.

            2. 构造函数不存在,直接初始化该类实例,一次依赖注入结束.
---

#### 4. @Named和@Qulifier注解

- @Named

    - 继承@Qulifier,可在参数中添加名称.用于指定待注入的对象调用的依赖方法.

- @Qulifier

    - 需要自定义注解.方法同@Name一样.解决依赖迷失问题.

        - 如果提供依赖的Module中有两个返回值一样的Provider,Dagger就不知道该用哪个方法.
---

#### 5. @Scope注解

- 声明作用域,与@Qulifier一样,需要自定义注解.

- @Singleton注解

    - 继承自@Scope,用于实现单例.
---

#### 6. Dagger2在MVP中的一个问题

- MVP中Model-View-Presenter全部使用接口实现

- 示例:

    - MainActivity : MainContract.View

    - MainPresenter : MainContract.Presenter

    - MainModel : MainContract.Model

    ##### 问题被描述:

    - 现MainActivity有@Inject MainContract.Presenter presenter,具体注入对象实例为实现类MainPresenter.且MainPresenter的相关构造函数已用@Inject标注.但由于Dagger不知道MainContract.Presenter的实现类为MainPresenter,所以导致无法注入.

    ##### 解决方案及步骤

    - 新建抽象Module类,名称自定,如PresenterModule,添加@Module注释
    
    - 添加抽象方法,返回值为所需的接口类型,无需实现方法,参数为所需接口类型的实现类

    - 将添加的抽象方法使用@Binds注释.

    - 在对应的注入Module对象中添加该抽象Module的依赖.

    ##### 代码

    - MainContract仿照[googlesamples/android-architecture](https://github.com/googlesamples/android-architecture)
    >

        public interface MainContract {
            interface Model {
                
            }

            interface View {
                void sayOk();
            }

            interface Presenter {
                void sayHello();
            }
        }
        ========================

        // View实现类MainActivity
        public class MainActivity extends AppCompatActivity implements MainContract.View {
            // 需要注入的Presenter对象,类型为接口类.
            @Inject
            MainContract.Presenter presenter;

            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
            }
            
            // MainContract.View中定义的方法
            @Override
            public void sayOk() {
                System.out.println("ok");
            }
        }

        ========================

        // Presenter的实现类,一般都需要持有View的引用.
        public class MainPresenter implements MainContract.Presenter {
            private MainContract.View view;

            // 构造使用@Inject标注
            @Inject
            public MainPresenter(MainContract.View view) {
                this.view = view;
            }

            // MainContract.Presenter定义的方法
            @Override
            public void sayHello() {
                System.out.println("hello");
            }
        }

        ========================

        // MainComponent,连接注入对象与Module的桥梁.依赖MainModule,PresenterModule
        @Component(modules = {MainModule.class, PresenterModule.class})
        public interface MainComponent {
            void inject(MainActivity mainActivity);
        }

        ========================

        // 抽象PresenterModule,这里给出该Module的主要作用是关联接口与各自的实现类
        @Module
        public abstract class PresenterModule {
            // 告诉Dagger,MainContract.Presenter实现类为MainPresenter
            @Binds
            public abstract MainContract.Presenter bindMainPresenter(MainPresenter presenter);
        }

        ========================

        @Module
        public class MainModule {
            private MainActivity activity;

            public MainModule(MainActivity activity) {
                this.activity = activity;
            }
            // MainPresenter的构造方法会从这个方法获取需要的参数
            @Provides
            public MainContract.View provideView() {
                return activity;
            }
        }
        
        ========================
---

#### 7. 其他相关文章

- [Dagger 2 完全解析（二），进阶使用 Lazy、Qualifier、Scope 等](http://www.bijishequ.com/detail/392746?p=)

- [解析Dagger中的Scope](http://www.jianshu.com/p/c4ed826c1473)

- [Inject interfaces without provide methods on Dagger 2](https://android.jlelse.eu/inject-interfaces-without-providing-in-dagger-2-618cce9b1e29)