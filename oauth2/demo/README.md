## oauth2 的四种模式
---
- 授权码模式(authorization code)
+ 简化模式(implicit)
* 密码模式(resource owner password credentials)
- 客户端模式(client credentials)

## 密码模式
通过POST/GET方法请求以下地址：
```
http://localhost:8080/oauth/token?username=user_1&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456
```
返回样例：
```
{
    "access_token": "5b30f83b-fe10-4ecb-bf6a-9ef7ae93a31d",
    "token_type": "bearer",
    "refresh_token": "44742dde-402e-4310-9bb1-d10463ac6a71",
    "expires_in": 41579,
    "scope": "select"
}
```
通过以下请求数据：
```$xslt
http://localhost:8080/order/1?access_token=55f91048-68ea-47dd-8156-34d5ce1c1136
```
## 客户端模式
通过POST、GET方法请求以下地址：
```
http://localhost:8080/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456
```
返回样例：
```$xslt
{
    "access_token": "55f91048-68ea-47dd-8156-34d5ce1c1136",
    "token_type": "bearer",
    "expires_in": 43146,
    "scope": "select"
}
```
通过以下请求数据：
```$xslt
http://localhost:8080/order/1?access_token=55f91048-68ea-47dd-8156-34d5ce1c1136
http://localhost:8081/product/1?access_token=1569e6a5-3703-4b3e-af4c-3554fbd78aa0
```
可以发现用户1可以访问产品，用户2不可以访问产品。   
用户1和用户2都可以访问订单。
## token 刷新
默认client模式不支持刷新token，password模式支持刷新token。
通过以下请求刷新token：
```$xslt
http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=44742dde-402e-4310-9bb1-d10463ac6a71&client_id=client_2&client_secret=123456
```
返回样例：
```$xslt
{
    "access_token": "3f818686-df4b-40dd-9d91-91a184b7eb34",
    "token_type": "bearer",
    "refresh_token": "44742dde-402e-4310-9bb1-d10463ac6a71",
    "expires_in": 43199,
    "scope": "select"
}
```

###### 如何刷新token
A: 定时器主动续约刷新token   
B: 定义请求过滤器，发现返回token无效时主动刷新token

###### 注意事项
配置 oauth2 filter 的顺序极其重要，因为springSecurity 的设计为：   
一次请求，只会被 FilterProxyChain 中的最多一个过滤器链处理。
在springboot 1.3.x-----1.5.x版本中，需要在配置文件中设置oauth2 filter的顺序：
```$xslt
security:
      oauth2:
        resource:
          filter-order: 3
```