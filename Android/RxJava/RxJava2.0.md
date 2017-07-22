# RxJava 2.0

> 参考文章 <b>[给初学者的RxJava2.0教程](http://www.jianshu.com/u/c50b715ccaeb)</b>

### 1. 最基本的用法

        Observable
                .create(new ObservableOnSubscribe<String>() {
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        for (int i = 0; i < 5; i++) {
                            emitter.onNext(String.valueOf(i));
                        }
                        emitter.onComplete();
                    }
                })
                .subscribe(new Observer<String>() {
                    public void onSubscribe(Disposable disposable) {

                    }

                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    public void onError(Throwable throwable) {

                    }

                    public void onComplete() {

                    }
                });

- 如果没有事件订阅,则不会有事件发送.

- ObservableEmitter<T>,事件发射器,用于发射事件,常用以下几个方法(方法定义于父接口Emitter<T>)
    
    1. onNext
    2. onComplete
    3. onError

        - onComplete与onError互斥

- Disposable,可将事件从发射器和订阅断开

    - disposable.dispose();调用之后,发射器可继续发射事件,但订阅将不再收到事件.
---

#### 2. 线程切换

- subscribeOn()

    - 发射事件的线程
    - 多次调用,只取第一次

- observeOn()

    - 订阅事件的线程
    - 多次调用,每次都会起作用

    ---

    1. Schedulers.io()
        - 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
    2. Schedulers.computation()
        - 代表CPU计算密集型的操作, 例如需要大量计算的操作
    3. Schedulers.newThread()
        - 代表一个常规的新线程
    4. AndroidSchedulers.mainThread()
        - 代表Android的主线程
---

#### 3. map操作符

- 对发送的每一个事件应用一个指定的函数,使其变为指定的事件.

    - 示例:将一组字符串转换为数值.


            Observable
                    .just("1", "2", "3")
                    .map(new Function<String, Integer>() {
                        public Integer apply(String s) throws Exception {
                            return Integer.parseInt(s);
                        }
                    })
                    .subscribe(new Observer<Integer>() {
                        public void onSubscribe(Disposable disposable) {

                        }

                        public void onNext(Integer integer) {
                            System.out.println(integer);
                        }

                        public void onError(Throwable throwable) {

                        }

                        public void onComplete() {

                        }
                    });
---

#### 4. flatMap操作符

- 将发射出的事件转换为多个Observables,然后将它们发射的事件合并后放进一个单独的Observable里.

- FlatMap不保证事件顺序.

    ##### 4.1 concatMap

    - 类似flatMap,保证事件发送顺序.
---

#### 5. zip操作符

- 将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件.

- 按照严格的顺序应用这个函数.

- 只发射与发射数据项最少的那个Observable一样多的数据。

- 两个事件在同一线程会存在先后顺序的问题.
---

#### 6. Flowable

- 基本使用

        Flowable
            .create(new FlowableOnSubscribe<String>() {
                public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                    for (int i = 0; i < 100000; i++) {
                        emitter.onNext(String.valueOf(i));
                    }
                    emitter.onComplete();
                }
            }, BackpressureStrategy.ERROR)
            .subscribe(new Subscriber<String>() {
                public void onSubscribe(Subscription subscription) {
                    subscription.request(100);
                }

                public void onNext(String s) {
                    System.out.println(s);
                }

                public void onError(Throwable throwable) {

                }

                public void onComplete() {

                }
            });

- BackpressureStrategy

    - 指定事件发射与订阅之间的关系策略.