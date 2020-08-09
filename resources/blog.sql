/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 09/08/2020 11:48:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (5, '一篇springboot文章', '## 第一行\n111\n## 第二行\n222', '2020-08-05 16:26:42');
INSERT INTO `article` VALUES (11, '面试算法模板', '## ACM入门算法\n\n测试用，找来的代码\n\n## 一、排序\n\n先给出一个swap函数，代表交换数组两个位置的值，很多排序用到这个函数:\n\n```java\nstatic void swap(int[] arr, int a, int b){\n    int t = arr[a];\n    arr[a] = arr[b];\n    arr[b] = t;\n}\n```\n\n面试主要考察**比较排序**(`O(N^2)、O(NlogN)`)排序(非比较排序可以看下面的详细总结)。给出两篇博客:\n\n### 1、冒泡\n\n```java\nstatic void bubbleSort(int[] arr){\n    for(int end = arr.length - 1; end > 0; end--){\n        boolean isSort = true;\n        for(int i = 0; i < end; i++){\n            if(arr[i] > arr[i+1]) {\n                swap(arr, i, i + 1);\n                isSort = false;\n            }\n        }\n        if(isSort) break;\n    }\n}\n```\n\n### 2、选择\n\n```java\nstatic void selectSort(int[] arr){\n    for(int i = 0; i < arr.length; i++){\n        int minIdx = i;\n        for(int j = i + 1; j < arr.length; j++) minIdx = arr[j] < arr[minIdx] ? j : minIdx;\n        swap(arr, minIdx, i);\n    }\n}\n```\n\n### 3、插入\n\n```java\n// 几个边界: i=1开始(不是必须)、j >= 0, arr[j+1] = key注意一下\nstatic void insertSort(int[] arr) {\n    for (int i = 1; i < arr.length; i++) {\n        int key = arr[i], j;\n        for (j = i - 1; j >= 0 && arr[j] > key; j--) arr[j + 1] = arr[j];\n        arr[j + 1] = key;\n    }\n}\n```\n\n第二种写法:\n\n```java\n// 边界 j > 0\nstatic void insertSort2(int[] arr) {\n    for (int i = 1; i < arr.length; i++) {\n        for (int j = i; j > 0 && arr[j - 1] > arr[j]; j--) swap(arr, j, j - 1);\n    }\n}\n```\n\n二分插入排序:\n\n```java\n// 注意 R = i-1，注意找第一个>=key的，注意arr[i]先用key保存\nstatic void binaryInsertSort(int[] arr) {\n    for (int i = 1; i < arr.length; i++) {\n        int L = 0, R = i - 1;\n        // 找第一个大于的 二分边界搞不清的看下面的二分链接\n        int key = arr[i];\n        while (L <= R) {\n            int m = L + (R - L) / 2;\n            if (arr[m] > arr[i]) {\n                R = m - 1;\n            } else {\n                L = m + 1;\n            }\n        }\n        for (int j = i - 1; j >= L; j--) arr[j + 1] = arr[j];\n        arr[L] = key;\n    }\n}\n```\n\n### 4、希尔排序\n\n采取的是**增量序列每次减半**的策略。\n\n```java\nstatic void shellSort(int[] arr) {\n    for (int g = arr.length; g > 0; g /= 2) { // 增量序列 gap\n        for (int end = g; end < arr.length; end++) { // 每一个组的结束元素, 从数组第gap个元素开始\n            // 每组做插入排序\n            int key = arr[end], i;\n            for (i = end - g; i >= 0 && key < arr[i]; i -= g) arr[i + g] = arr[i];\n            arr[i + g] = key;\n        }\n    }\n}\n```\n\n### 5、快排\n\n给出的是三路快排，其他的看我给的博客。\n\n```java\nstatic void quickSort(int[] arr){\n    if(arr == null || arr.length == 0) return;\n    quickRec(arr, 0, arr.length - 1);\n}\n\nstatic void quickRec(int[] arr, int L, int R) {\n    if (L >= R) return;\n    swap(arr, L, L + (int) (Math.random() * (R - L + 1)));\n    int[] p = partition(arr, L, R);\n    quickRec(arr, L, p[0] - 1);\n    quickRec(arr, p[1] + 1, R);\n}\n\n// 用arr[L]作为划分点\nstatic int[] partition(int[] arr, int L, int R) {\n    int key = arr[L];\n    int less = L, more = R + 1;\n    int cur = L + 1;\n    while (cur < more) {\n        if (arr[cur] < key) {\n            swap(arr, ++less, cur++);\n        } else if (arr[cur] > key) {\n            swap(arr, --more, cur);\n        } else {\n            cur++;\n        }\n    }\n    swap(arr, L, less);\n    // 返回相等的两个下标，　less位置是我最后交换过来的划分值，more位置是>的，所以返回more-1\n    return new int[]{less, more - 1};\n}\n```\n\n### 6、归并排序\n\n```java\nstatic void mergeSort(int[] arr){\n    if(arr == null || arr.length == 0) return;\n    mergeRec(arr, 0, arr.length - 1);\n}\n\n//注意是mergeSort(arr, L, m); 不是mergeSort(arr, L, m-1)\nstatic void mergeRec(int[] arr, int L, int R) {\n    if (L >= R) return;\n    int m = L + (R - L) / 2;\n    mergeRec(arr, L, m);\n    mergeRec(arr, m + 1, R);\n    merge(arr, L, m, R);\n}\n\nstatic void merge(int[] arr, int L, int mid, int R) {\n    int[] h = new int[R - L + 1];\n    int p1 = L, p2 = mid + 1;\n    int k = 0;\n    while (p1 <= mid && p2 <= R)\n        h[k++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];  // 注意保证稳定性\n    while (p1 <= mid) h[k++] = arr[p1++];\n    while (p2 <= R) h[k++] = arr[p2++];\n    for (int i = 0; i < k; i++) arr[L + i] = h[i];\n}\n```\n\n非递归归并排序: \n\n```java\nstatic void mergeSortBU(int[] arr) {\n    for (int sz = 1; sz <= arr.length; sz += sz) { // 区间的个数，1..2..4..8\n        for (int i = 0; sz + i < arr.length; i += sz + sz) {  // 对[i...i+sz-1]和[i+sz...i+2*sz-1]内归并\n            merge(arr, i, i + sz - 1, Math.min(arr.length - 1, i + 2 * sz - 1)); // min防止越界\n        }\n    }\n}\n```\n\n### 7、堆排\n\n```java\n// if(arr == null || arr.length <= 1) return; 是必须的\nstatic void heapSort(int[] arr) {\n    if (arr == null || arr.length <= 1) return;\n    for (int i = 0; i < arr.length; i++) siftUp(arr, i);//上浮方式建堆\n    int size = arr.length - 1;\n    swap(arr, 0, size);\n    while (size > 0) {\n        siftDown(arr, 0, size);\n        swap(arr, 0, --size);\n    }\n}\n\n// 上浮\nstatic void siftUp(int[] arr, int i) {\n    while (arr[i] > arr[(i - 1) / 2]) {\n        swap(arr, i, (i - 1) / 2);\n        i = (i - 1) / 2;\n    }\n}\n\n// 下沉\nstatic void siftDown(int[] arr, int i, int heapSize) {\n    int L = 2 * i + 1;\n    while (L < heapSize) {\n        int maxIndex = L + 1 < heapSize && arr[L + 1] > arr[L] ? L + 1 : L;\n        maxIndex = arr[i] > arr[maxIndex] ? i : maxIndex;\n        if (maxIndex == i) break;\n        swap(arr, i, maxIndex);\n        i = maxIndex;\n        L = 2 * i + 1;\n    }\n}\n```\n\n第二种方式，使用`heapfiy`的优化，只需要使用`siftDown`过程即可。\n\n```java\n// 注意这里是size+1,因为这个不是交换了最后一个，所以要考虑arr[size]，下面不要考虑arr[size]\n//   if (arr == null || arr.length <= 1) return; 是必须的\nstatic void heapSort2(int[] arr) {\n    if (arr == null || arr.length <= 1) return;\n    int size = arr.length - 1;\n    for (int i = (size - 1) / 2; i >= 0; i--)\n        siftDown(arr, i, size + 1);\n    swap(arr, 0, size);\n    while (size > 0) {\n        siftDown(arr, 0, size);\n        swap(arr, 0, --size);\n    }\n}\n```\n\n其中`siftDown`过程也可以使用递归的写法: \n\n```java\nstatic void siftDown(int[] arr, int i, int heapSize) { //从arr[i] 开始往下调整\n    int L = 2 * i + 1;\n    int R = 2 * i + 2;\n    int maxIdx = i;\n    if (L < heapSize && arr[L] > arr[maxIdx]) maxIdx = L;\n    if (R < heapSize && arr[R] > arr[maxIdx]) maxIdx = R;\n    if (maxIdx != i) {\n        swap(arr, i, maxIdx);\n        siftDown(arr, maxIdx, heapSize);\n    }\n}\n```\n\n## 二、二分\n\n\n二分最主要的就是边界问题: \n\n* 第一个`=key`的，不存在返回`-1`；\n* 第一个`>=key`的；\n* 第一个`>key`的；\n* 最后一个`=key`的；\n* 最后一个`<=key`的；\n* 最后一个`<key`的；\n\n最基本的二分查找: ', '2020-08-07 14:53:52');
INSERT INTO `article` VALUES (12, '测试图片', '## 上传一张图片试试\n![一个img.jpeg](http://localhost:8081/uploadImg/6de1578f-a4e6-427f-b46c-7b4ad77d96bf_一个img.jpeg)\n## 第二部分\n#### 图片展示成功了吗', '2020-08-07 19:35:52');
INSERT INTO `article` VALUES (13, '并发编程', '## 一、上下文切换\n\n### 1、时间片\n\n即使是单核处理器也支持多线程执行代码，CPU通过给每个线程分配**CPU时间片**来实现这个机制。\n\n时间片是CPU分配给各个线程的时间，因为时间片非常短，所以CPU通过不停地切换线程执行，让我们感觉多个线程是同时执行的，时间片一般是几十毫秒（ms）。\n\n### 2、并行一定更快吗？\n\n相同的程序，并行版本不一定比串行快。\n\n因为CPU在由一个线程切换到另一个线程时，需要保留**该线程当下的执行状态**（执行到了哪一行，有哪些变量和变量值），在下次继续执行该线程时恢复到原来的状态，这个保存-恢复会消耗一定得到时间。\n\n### 3、如何避免频繁的上下文切换?\n\n* 1)、降低锁粒度: 如将数据的ID按照Hash算法取模分段，不同的线程处理不同段的数据，这在`ConcurrentHashMap`中有所体现。\n* 2)、CAS算法(CAS无所操作)。**Java的Atomic包使用CAS算法来更新数据，而不需要加锁**。不同于`synchronized`在获得释放锁的过程中会引起线程的切换。\n* 3)、使用最少线程。避免创建不需要的线程，比如任务很少，但是创建了很多线程来处理，这\n  样会造成大量线程都处于等待状态，尽量使用线程池技术。\n\n协程：在单线程里实现多任务的调度，并在单线程里维持多个任务间的切换\n\n## 二、死锁\n\n死锁：多个线程相互等待对方资源，在得到所有资源继续运行之前，都不会释放自己已有的资源，这样造成了循环等待的现象。\n\n例子，线程`t1`持有`A`的锁，想要得到`B`锁，但是线程`t2`持有`B`锁，却想得到`A`锁。\n\n```java\npublic class DeadLockDemo {\n\n    private static String A = \"A\";\n    private static String B = \"B\";\n\n    public static void main(String[] args) {\n        new DeadLockDemo().deadLock();\n    }\n\n    private void deadLock() {\n        Thread t1 = new Thread(() -> {\n            synchronized (A) {\n\n                try {\n                    Thread.sleep(2000);\n                } catch (InterruptedException e) {\n                    e.printStackTrace();\n                }\n\n                synchronized (B) {\n                    System.out.println(\"1\");\n                }\n            }\n        });\n\n        Thread t2 = new Thread(() -> {\n            synchronized (B) {\n                synchronized (A) {\n                    System.out.println(\"2\");\n                }\n            }\n        });\n        t1.start();\n        t2.start();\n    }\n}\n```\n\n如何避免死锁?	\n\n1. **避免一个线程同时获取多个锁**；\n2. 避免一个线程同时占用多个资源，尽量保证每个锁只占用一个资源；\n3. 尝试使用**定时锁**，使用`lock.tryLock(timeout)`来替代使用内部锁机制，一定时间获取不到就返回；\n4. 对于数据库锁，**加锁和解锁必须在一个数据库连接里**，否则会出现解锁失败的情况；', '2020-08-07 19:40:15');
INSERT INTO `article` VALUES (14, '并发机制底层实现原理', '## 一、本地内存和线程安全问题\n\n### 1、缓存行\n\nCPU不会直接和内存(主存)交互，而是通过总线将数据读到自己的缓存行中。\n\n缓存行: \n\n- 缓存是分段（line）的，一个段对应一块存储空间，我们称之为缓存行；\n- 它是CPU缓存中可分配的最小存储单元，通常来说是64字节；\n- 当CPU看到一条读取内存的指令时，它会把内存地址传递给一级数据缓存，一级数据缓存会检查它是否有这个内存地址对应的缓存段，如果没有就把整个缓存段从内存（或更高一级的缓存）中加载进来；\n\n### 2、写缓冲区\n\nCPU不会直接和内存(主存)交互，会将要读取的数据先写入到自己的写缓冲区，随后才会刷新到内存。\n\n### 3、线程安全问题\n\n* 实际中都是由很多CPU来执行并发程序，不同处理器同时执行不同的线程（每个线程都有一个仅对执行自己的处理器可见的本地内存)；\n* 所以就会出现主内存中`i = 1`，线程A读取到自己的本地内存`i++`，于此同时线程B也读取到主内存`i= 1`到自己的本地内存执行`i++`，待两个线程的本地内存刷新到主内存时`i = 2`。于是引发了线程安全问题。\n\n线程安全问题总结:\n\n* **线程都是在自己的本地内存中操作共享变量的，仅对执行自己的处理器可见而对其他处理器不可见**。\n* 而它们在自己的本地内存对共享变量的更新何时会刷新到主内存、会按照什么顺序刷新到主内存是不可预见的。\n\n## 二、volatile实现原理\n\n总结:\n\n* 对于声明了`volatile`的变量进行写操作的时候，JVM会向处理器发送一条LOCK前缀的指令。**会把这个变量所在缓存行的数据写回到主内存中**；\n* 在多处理器的情况下，保证各个**处理器缓存一致性的特点，实现缓存一致性协议**；\n\n通过加入**内存屏障**和**禁止重排序优化**实现。\n\n* 对volatile变量写操作时，会在写操作后面加入一条`store`屏障指令，将本地内存中的共享变量值刷新到主内存；\n* 对volatile变量读操作时，会在读操作前加入一条load屏障指令，从主内存中读取共享变量；\n\n### 1、volatile语义\n\n使用volatile关键字可以**保证共享变量之间的可见性**，被`volatile`修饰的变量在线程之间就是可见的，能保证变量被一致性的更新。\n\n`volatile`做的两件事: \n\n- 1、锁定缓存行；\n  - 在某个处理器将共享数据写入自己的缓冲区 (对应线程对本地内存中的共享记量做修改) 时，**会使用缓存锁定其他也读取了该共享记量的缓存行，使其他处理器不能访问该共享专量**。 \n  - 早期使用的是总线锁定，即一经锁定，其他处理器就不能访问所有共享记量，但是这会影响处理器读写其他共享变量，影响效率。\n- 2、刷新内存，保证数据一致性；\n  - 该处理器将自己写缓冲区中的所有数据刷新到主内存 (包括非volatile变量) 。\n  - 由**缓存一致性协议**来保证其他CPU重新读数据 (其他处理器会通过总线嗅探其他处理器写组冲区中的更改，一经发现就会将自己的缓存行置为无效状态(看自己的是不是过期了)，下次访问数据时需要到主内存中重新读)\n\n一旦一个共享变量（类的成员变量、类的静态成员变量）被`volatile`修饰之后，那么就具备了两层语义：\n\n* 1、保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。\n* 2、**禁止进行指令重排序**。\n\n> 由于缓存行为`32`字节宽或者`64`字节宽，因此避免缓存锁定多个共享资源，可以采用**字节填充**的方式来提高对象并发性能。\n\n### 2、Lock前缀指令\n\n加入volatile关键字和没有加入volatile关键字时所生成的汇编代码发现，**加入volatile关键字时，会多出一个lock前缀指令**\n\nlock前缀指令实际上相当于一个**内存屏障**（也称内存栅栏），内存屏障会提供3个功能：\n\n* 1、它确保指令重排序时**不会把其后面的指令排到内存屏障之前的位置**，也不会把前面的指令排到内存屏障的后面；即在执行到内存屏障这句指令时，在它前面的操作已经全部完成；\n* 2、**它会强制将对缓存的修改操作立即写入主存**；\n* 3、如果是写操作，它会导致其他CPU(处理器)中对应的缓存行无效。\n\n在JVM底层volatile是采用“内存屏障”来实现的。\n\n可以得出lock指令的几个作用：\n\n* 1、锁总线，其它CPU对内存的读写请求都会被阻塞，直到锁释放，不过实际后来的处理器都采用锁缓存替代锁总线，因为锁总线的开销比较大，锁总线期间其他CPU没法访问内存\n* 2、lock后的写操作会**回写已修改的数据，同时让其它CPU相关缓存行失效**，从而重新从主存中加载最新的数据\n* 3、不是内存屏障却能完成类似内存屏障的功能，阻止屏障两遍的指令重排序\n\n这种场景下多缓存的数据一致是通过缓存一致性协议来保证的，我们来看一下什么是缓存一致性协议。 \n\n### 3、缓存一致性\n\n`LOCK#`会锁总线，实际上这不现实，因为锁总线效率太低了。因此最好能做到：使用多组缓存，但是它们的行为看起来只有一组缓存那样。缓存一致性协议就是为了做到这一点而设计的，就像名称所暗示的那样，**这类协议就是要使多组缓存的内容保持一致**。\n\n缓存一致性协议有多种，但是日常处理的大多数计算机设备都属于\"嗅探（snooping）\"协议，基本思想如下: \n\n* 所有内存的传输都发生在一条共享的总线上，而所有的处理器都能看到这条总线：缓存本身是独立的，但是内存是共享资源，所有的内存访问都要经过仲裁（同一个指令周期中，只有一个CPU缓存可以读写内存）。\n* CPU缓存不仅仅在做内存传输的时候才与总线打交道，**而是不停在嗅探总线上发生的数据交换，跟踪其他缓存在做什么。所以当一个缓存代表它所属的处理器去读写内存时，其它处理器都会得到通知，它们以此来使自己的缓存保持同步**。只要某个处理器一写内存，其它处理器马上知道这块内存在它们的缓存段中已**失效**。\n\n### 4、回看volatile原理\n\n工作内存Work Memory其实就是对CPU寄存器和高速缓存的抽象，或者说每个线程的工作内存也可以简单理解为CPU寄存器和高速缓存。\n\n那么当写两条线程`Thread-A`与`Threab-B`同时操作主存中的一个volatile变量`i`时，Thread-A写了变量`i`，那么：\n\n- `Thread-A`发出`LOCK#`指令\n- 发出的`LOCK#`指令锁总线（或锁缓存行），同时让`Thread-B`高速缓存中的缓存行内容失效\n- `Thread-A`向主存回写最新修改的`i`\n\n`Thread-B`读取变量`i`，那么：\n\n- `Thread-B`发现对应地址的缓存行被锁了，等待锁的释放，缓存一致性协议会保证它读取到最新的值\n\n由此可以看出，volatile关键字的读和普通变量的读取相比基本没差别，差别主要还是在变量的写操作上。\n\n## 三、sychronized实现原理\n\n### 1、Monitor\n\n利用synchronized实现同步的基础，Java中的每一个对象都可以作为锁，有以下3种形式\n\n- 对于普通同步方法，锁是当前实例对象；\n- 对于静态同步方法，锁是当前类的Class对象；\n- 对于同步方法块，锁住的是synchonized括号里配置的对象；\n\nJava 虚拟机中的同步(Synchronization)是基于进入和退出**Monitor对象**实现， **无论是显式同步(有明确的 monitorenter 和 monitorexit 指令**，即同步代码块)还是隐式同步都是如此。\n\n`monitorenter`指令实在编译后插入到同步代码块的开始位置，而`monitorexit`是插入到方法结束和异常处，每个`monitorenter`必须要有对应的额`monitorexit`与之配对，任何对象都有`monitor`与之关联。\n\n线程只有持有到`monitor`，才会属于锁定状态，线程会尝试获取对象对应的`monitor`的所有权，即尝试获得对象的锁。\n\n在 Java 语言中，同步用的最多的地方可能是被 synchronized 修饰的同步方法。**同步方法并不是由monitorenter 和 monitorexit 指令来实现同步的，而是由方法调用指令读取运行时常量池中方法的 ACC_SYNCHRONIZED 标志来隐式实现的**。\n\n```java\n1    public void add(Object obj){\n2        synchronized (obj){\n3            //do something\n4        }\n5    }\n\n反编译后：\n 1public class com.zxin.thread.SynchronizedDemo {\n 2  public com.wuzy.thread.SynchronizedDemo();\n 3    Code:\n 4       0: aload_0\n 5       1: invokespecial #1              \n 6       4: return\n 7\n 8  public void add(java.lang.Object);\n 9    Code:\n10       0: aload_1\n11       1: dup\n12       2: astore_2\n13       3: monitorenter //进入同步方法\n14       4: aload_2\n15       5: monitorexit //退出同步方法\n16       6: goto          14\n17       9: astore_3\n18      10: aload_2\n19      11: monitorexit //退出同步方法\n20      12: aload_3\n21      13: athrow\n22      14: return\n23    Exception table:\n24       from    to  target type\n25           4     6     9   any\n26           9    12     9   any\n27}\n```\n\n看下第13行~15行代码**，发现同步代码块是使用monitorenter和monitorexit指令来进行代码同步的,注意看第19行代码，为什么会多出一个monitorexit指令，主要是JVM为了防止代码出现异常**，也能正确退出同步方法。\n\n同步方法并不是用`monitorenter`和`monitorexit`指令来进行同步的，实际上同步方法会被翻译成普通的方法调用和返回指令如:invokevirtual、areturn指令，在VM字节码层面并没有任何特别的指令来实现被synchronized修饰的方法，而是**在Class文件的方法表中将该方法的access_flags字段中的synchronized标志位置设为1**，表示该方法是同步方法并使用调用该方法的对象或该方法所属的Class在JVM的内部对象表示做为锁对象。\n\n总结: \n\n* 同步代码块: 使用`monitorenter`和`monitorexit`；\n* 同步方法: 使用方法修饰符`ACC_ASYNCHRONIZED`。\n\n### 2、Java对象头\n\n在JVM内存中，对象在内存中的布局分为`3`块: **对象头、实例数据和对其填充**。\n\n其中对象头包括 : `Mark Word、Class Meta Data、Array Length`。\n\n* `Mark Word`: 锁标志位等跟锁有关的信息，2个字节存储(数组则3个)；\n* `Class Meta Data`: 对象所属类的类元数据信息；\n* `Array Length` : 对象为数组类型时才有，记录了数组长度；\n\nMark Word的状态变化:\n\n锁标志的意义:\n\n* 锁标识`lock == 00`标识轻量级锁；\n* 锁标识`lock = 10`标识重量级锁；\n* 偏向锁标识`biased_lock = 1`表示偏向锁；\n* 偏向锁标识`biased_lock = 0`且锁标识`=01`表示无锁状态；\n\n### 3、锁的升级\n\n锁的状态: **无锁、偏向锁、轻量级锁、重量级锁**。\n\n锁升级: **JVM检测到不同的竞争状态时，自动切换到合适的锁实现**。\n\n**无锁**:\n\n* 初始情况(没有任何线程访问过同步块)；\n\n**偏向锁**:\n\n* 大多数场景不会出现多线程并发访问共享资源的情况，针对并发强度小的情况，引入了偏向锁，在一个线程访问同步块时有如下操作: \n  * 1、判断对象头的`Mark Word`中的线程ID是否指的就是当前线程，如果是，直接进入同步块，如果不是，进入步骤`2`；\n  * 2、如果对象头的`Mark Word`中的线程ID不是指向当前线程，那么查看Mark Word中\"是否是偏向锁\"这一标志位。如果是`1`，指向步骤`3`；否则表示是无锁状态，CAS将Mard Word 中的线程ID指向当前线程，进入同步块；\n  * 3、如果是`1`就说明是偏向锁，而且出现了锁争用的情况，偏向锁升级为轻量级锁；\n* 偏向锁撤销\n  * 由于偏向锁是\"偏向\"某一个线程的，如果线程\"挂了\"怎么办？这就需要偏向锁撤销机制；\n  * 即在一个安全点（没有字节码执行），首先暂停锁\"偏向\"的线程，然后检查线程状态，如果线程\"挂了\"那么将锁置为无锁状态；\n\n**轻量级锁**:\n\n* 获取锁\n  * 1、首先将同步对象(synchronize内的对象)的Mark Word复制一份到当前线程栈桢的一块空间中，并使用CAS将同步对象的Mark Word更新为指向该空间的指针，如果更新成功那么成功获取锁；否则CAS自旋；\n  * 2、获取锁的线程在执行完同步块释放锁时，CAS将同步对象的Mark Word替换回占中原先保存的Mark Word，如果成功，则表示成功释放锁，没有竞争发生。否则表明当前锁存在竞争，升级为重量级锁；\n\n**重量级锁(排他锁)**:\n\n* 想进入同步快需要获取对象的`monitor`，退出时释放`monitor`。\n* 获取对象`monitor`时如果`monitor`已被持有，则该线程将进入`monitor`的阻塞队列，直到`monitor`被释放，`monitor`阻塞队列上的线程将开启一轮新的竞争。\n\n## 四、原子操作实现原理\n\n### 1、处理器实现原子操作\n\n1、使用总线锁保证原子性\n\n所谓总线锁就是使用处理器提供的一个`LOCK＃`信号，当一个处理器在总线上输出此信号时，其他处理器的请求将被阻塞住，那么该处理器可以独占使用共享内存。\n\n2、使用缓存锁保证原子性\n\n所谓“**缓存锁定**”就是如果缓存在处理器缓存行中内存区域在LOCK操作期间被锁定，当它执行锁操作回写内存时，处理器不在总线上声言`LOCK＃`信号，**而是修改内部的内存地址，并允许它的缓存一致性机制**来保证操作的原子性；\n\n### 2、Java实现原子操作\n\n两种方式：**锁和循环CAS**\n\nCAS全称`Compare-and-Swap`（比较并交换），JVM中的CAS操作是依赖处理器提供的`cmpxchg`指令完成的，CAS指令中有3个操作数，分别是内存位置V、旧的预期值A和新值B。\n\n当CAS指令执行时，**当且仅当内存位置V符合旧预期值时A时，处理器才会用新值B去更新V的值，否则就不执行更新，但是无论是否更新V，都会返回V的旧值，该操作过程就是一个原子操作**\n\nJDK1.5之后才可以使用CAS，由`sun.misc.Unsafe`类里面的`compareAndSwapInt()`和`compareAndSwapLong()`等方法包装实现，虚拟机在即时编译时，对这些方法做了特殊处理，会编译出一条相关的处理器CAS指令\n\n> CAS就是Compare And Swap，涉及到两个术语: 预期值、更新值。在对内存中的值进行更新时，拿A和B两线程同时对`i`变量进行`i++`举例:\n>\n> 首先A线程读到`i`的值为1，执行`i++`操作并刷新到主内存时，比较一下主内存中的还是不是1 (预期值) ，如果是就将共享记量替换为2 (更新值) 。\n>\n> 后来B也准备将`i= 2`刷新到主内存时，发现主内存中的不等于1 (预期值) ，于是更新失败，重新读取`i=2`，进行CAS更新，这个不断尝试CAS更新的过程称为自旋。\n\n**CAS实现原子操作的三大问题**\n\n**1、ABA问题**：初次读取内存旧值时是A，再次检查之前这段期间，如果内存位置的值发生过从A变成B再变回A的过程，我们就会错误的检查到旧值还是A，认为没有发生变化，其实已经发生过A-B-A得变化，这就是CAS操作的ABA问题\n\n解决方法：使用版本号，即`1A-2B-3A`，这样就会发现1A到3A的变化，不存在ABA变化无感知问题，JDK的atomic包中提供一个带有标记的原子引用类`AtomicStampedReference`来解决ABA问题\n\n**2、循环时间长开销大**：自旋CAS如果长时间不成功，会给CPU带来非常大的执行开销\n\n**3、只能保证一个共享变量的原子操作**：当对一个共享变量执行操作时，可以使用循环CAS来保证原子操作，但是多个共享变量操作时，就无法保证了\n\n解决方法：\n\n- 将多个变量**组合成一个共享变量**，jdk提供了`AtomicReference`类来保证引用对象之间的原子性，那么就可以把多个变量放在一个对象里来进行CAS操作\n- 使用锁\n\n>除了偏向锁，JVM实现锁的方式都用了循环CAS，即当一个线程想进入同步块的时候使用循环CAS的方式来获取锁，当它退出同步块的时候使用循环CAS释放锁。', '2020-08-07 19:42:17');

-- ----------------------------
-- Table structure for article_category
-- ----------------------------
DROP TABLE IF EXISTS `article_category`;
CREATE TABLE `article_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) NULL DEFAULT NULL,
  `cid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `aid`(`aid`) USING BTREE,
  INDEX `cid`(`cid`) USING BTREE,
  CONSTRAINT `article_category_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `article` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `article_category_ibfk_2` FOREIGN KEY (`cid`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_category
-- ----------------------------
INSERT INTO `article_category` VALUES (5, 5, 2);
INSERT INTO `article_category` VALUES (15, 13, 1);
INSERT INTO `article_category` VALUES (16, 14, 1);
INSERT INTO `article_category` VALUES (17, 12, 5);
INSERT INTO `article_category` VALUES (18, 11, 1);

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) NULL DEFAULT NULL,
  `tid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `aid`(`aid`) USING BTREE,
  INDEX `tid`(`tid`) USING BTREE,
  CONSTRAINT `article_tag_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `article` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `article_tag_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_tag
-- ----------------------------
INSERT INTO `article_tag` VALUES (9, 5, 5);
INSERT INTO `article_tag` VALUES (10, 5, 3);
INSERT INTO `article_tag` VALUES (31, 13, 5);
INSERT INTO `article_tag` VALUES (32, 13, 7);
INSERT INTO `article_tag` VALUES (33, 13, 8);
INSERT INTO `article_tag` VALUES (34, 14, 5);
INSERT INTO `article_tag` VALUES (35, 12, 7);
INSERT INTO `article_tag` VALUES (36, 12, 10);
INSERT INTO `article_tag` VALUES (37, 12, 11);
INSERT INTO `article_tag` VALUES (38, 11, 1);
INSERT INTO `article_tag` VALUES (39, 11, 5);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '基础');
INSERT INTO `category` VALUES (2, 'Java框架');
INSERT INTO `category` VALUES (3, '开发工具');
INSERT INTO `category` VALUES (4, '数据库');
INSERT INTO `category` VALUES (5, '测试分类1');
INSERT INTO `category` VALUES (6, '测试修改文章分类');
INSERT INTO `category` VALUES (7, '修改后的分类');

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, '算法');
INSERT INTO `tag` VALUES (2, 'MySQL');
INSERT INTO `tag` VALUES (3, 'springboot');
INSERT INTO `tag` VALUES (4, 'mybatis');
INSERT INTO `tag` VALUES (5, 'java');
INSERT INTO `tag` VALUES (6, '计算机网络');
INSERT INTO `tag` VALUES (7, '测试标签1');
INSERT INTO `tag` VALUES (8, '测试标签2');
INSERT INTO `tag` VALUES (9, '测试修改标签1');
INSERT INTO `tag` VALUES (10, '测试修改文章标签2');
INSERT INTO `tag` VALUES (11, '测试修改标签3');
INSERT INTO `tag` VALUES (12, '修改后的标签');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (5, 'admin', '$2a$10$DVo7SHQbAjO5a.ZtziLYKe0wVTNtlU6wX9ssf.sVDnlRQDJRpvcX6', '超级管理员');

SET FOREIGN_KEY_CHECKS = 1;
