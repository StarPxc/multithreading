#有助于提高“锁”性能的几点建议
##减少锁持有的时间
不要直接在方法上加锁，在需要同步的代码上加锁
##减小锁的粒度
&emsp;&emsp;ConcurrentHashMap 内部细分了16个细小的HaspMap，称之为段（SEGMENT），
当ConcurrentHashMap中增加一个新的表项时，不是将整个HashMap加锁，而是先根据
hashcode得到该表项应该被放在哪个段中，然后对该段加锁。然而，当系统需要获得全局锁时
其消耗的资源会比较多，size()方法会先使用无锁技术求和，如果失败才会尝试加锁的方法，在高并发
的场合，ConcurrentHashMap的size()的性能要差于同步后的HashMap。<br>
**只有类似于size()这种获取全局信息的方法调用不频繁时，这种减小粒度的方法才能真正意义上的提高系统吞吐量**
##锁分离
&emsp;&emsp;LinkBlockingQueue底层实现有点长，以后再看
##锁粗化
&emsp;&emsp;虚拟机在遇到一连串地对统一锁不断进行请求和释放的操作时，便会把所有的锁操作整合成对锁的一次请求。
锁粗化的思想和减少锁持有时间的相反的。
# JVM对锁优化所做的努力
## 锁偏向
&emsp;&emsp;默认是开启的，核心思想是如果一个线程获得了锁，那么锁就进入偏向模式。当这个
线程再次请求锁时，无需做任何同步操作，如果每次都是不同的线程来请求相同的锁。这样偏向模式就会失败。
##轻量级锁
当偏向锁失败时，JVM不会立即挂起线程。它会使用一种叫做轻量级锁的优化手段，它只是简单的将对象头部指针指向持有锁的线程堆栈
的内部，来判断一个线程是否持有该锁，若轻量级锁失败那么当前线程的锁请求就会膨胀为重量锁。
##自旋锁
&emsp;&emsp;锁膨胀后，虚拟机为了避免线程真实地在操作系统层面挂起，虚拟机还会再做最后的努力---自旋锁，系统会假设在不久的
将来，现场可以得到这把锁，因此，虚拟机会让当前线程做几个空循环，如果还不能得到锁，才会真实的将线程在操作系统层面挂起。
##锁消除
锁消除是一种更加彻底的锁优化，JVM在JIT编译时，通过对运行上下文的扫描，去除不可能存在共享资源竞争的锁。通过锁消除，可以节
省毫无意义的请求锁时间。<br>
```java
public String[] creatString(){
    Vector<String> v=new Vector<String>;
    for(int i=0;i<100;i++){
        v.add(Integer.toString(1));
    }
    return v.toArray(new String[]{});
}
```
注意上面的代码，由于变量v只在creatStrings()函数中调用，因此，它只是一个单纯的局部变量，Vector内部加锁是没有必要的，如果
JVM检测到这种没有必要的锁就会去清除<br>
消除锁的一个关键技术就是逃逸分析，所谓逃逸分析就是观察某一个变量是否会逃出某一个作用域。

