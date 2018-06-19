## 如何使用？
### 1、对象缓存
注入redis工具类
```
  @Autowired
  private RedisUtil redisUtil;
```
构建缓存key前缀，参数1:有效时间  参数2：前缀
```
    PrefixKey pk = PrefixKey.with(3600, "jyx");
```
添加对象缓存
```
   redisUtil.set(pk, "q", user1);
```
从缓存获取对象
```
  User user = redisUtil.get(pk, "q", User.class);
```
### 2、分布式锁
注入redis工具类
```
  @Autowired
  private RedisUtil redisUtil;
```
加锁和释放锁
```
 try {
      redisUtil.lock("insertlock");    
     }finally {
      redisUtil.unlock("insertlock");  
   }
```
### 3、限流
通过添加注解 @RedisLimit()，实现请求的限流，参数seconds：时间（单位:秒），参数maxCount：最大请求数，
```
    @RedisLimit(seconds = 5,maxCount = 5)//5秒内最多能请求5次
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> list(Model model, User user, @RequestParam("goodsId") long goodsId) {
        ...
        return Result.success(0);//排队中

    }
```

