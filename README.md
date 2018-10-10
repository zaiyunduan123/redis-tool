## 如何使用？
### 1、对象缓存
注入redis工具类
```java
  @Autowired
  private RedisUtil redisUtil;
```


构建缓存key前缀，PrefixKey.with(int expireSeconds, String prefix)

1. expireSeconds：过期时间（单位：秒）

2. prefix：key前缀

```java
 PrefixKey pk = PrefixKey.with(3600, "jesper");
```


添加对象缓存，set(KeyPrefix prefix, String key, T value, boolean isRandomSeconds) 

1. prefix：前缀对象
2. key：缓存key
3. value：要缓存的对象
4. isRandomSeconds：是否开启避免雪崩（会产生过期时间小误差）

```java
redisUtil.set(pk, "q", user1, true);
```


从缓存获取对象，get(KeyPrefix prefix, String key, Class<T> clazz)

1. prefix：前缀对象
2. key：缓存key
3. clazz：对象类型

```java
User user = redisUtil.get(pk, "q", User.class);
```


### 2、分布式锁

注入redis工具类
```java
  @Autowired
  private RedisUtil redisUtil;
```


加锁和释放锁，lock(String lockKey)， lockKey：标记锁，保证锁唯一性

```java
 try {
      redisUtil.lock("insertlock");    
     }finally {
      redisUtil.unlock("insertlock");  
   }
```


### 3、限流

通过添加注解 @RedisLimit(seconds = n, maxCount = n)实现请求的限流

1. seconds：单位时间（单位:秒）
2. maxCount：最大请求数

```java
    @RedisLimit(seconds = 5,maxCount = 5)//5秒内最多能请求5次
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> list(Model model, User user, @RequestParam("goodsId") long goodsId) {
        ...
        return Result.success(0);//排队中

    }
```

