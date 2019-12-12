## oauth2 的四种模式
---
- 授权码模式(authorization code)
+ 简化模式(implicit)
* 密码模式(resource owner password credentials)
- 客户端模式(client credentials)
## 授权码模式
1. 访问oauth2服务
```
请求code：
http://localhost:18084/oauth/authorize?response_type=code&client_id=client&redirect_uri=http://baidu.com&state=123
返回：
https://www.baidu.com/?code=p1NMy4&state=123

请求token：
http://localhost:18084/oauth/token?client_id=client&client_secret&grant_type=authorization_code&redirect_uri=http://baidu.com&code=p1NMy4
返回：
{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzYxNzYxNTIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiZTQ3NmExN2QtNjRkMi00ZTM2LTljNWQtZmFjZjdmMWI0ZTQ4IiwiY2xpZW50X2lkIjoiY2xpZW50Iiwic2NvcGUiOlsiYXBwIl19.y4r5QJRGsPcS12NsjMrN7WSSF0ts886pTRvlJQY5594","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFwcCJdLCJhdGkiOiJlNDc2YTE3ZC02NGQyLTRlMzYtOWM1ZC1mYWNmN2YxYjRlNDgiLCJleHAiOjE1Nzg3MjQ5NTIsImF1dGhvcml0aWVzIjpbImFkbWluIl0sImp0aSI6ImVkNmZhOTQyLTMxOTAtNDdkOS04NzAxLTNhODMxMWY5MmI4OCIsImNsaWVudF9pZCI6ImNsaWVudCJ9.iqpfJaEJr-YmPo8rvVWedL8JnBKNka-sp5ht9gKnl54","expires_in":43199,"scope":"app","user_name":"admin","jti":"e476a17d-64d2-4e36-9c5d-facf7f1b4e48"}

携带token请求资源：
http://localhost:18084/get/users/list?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzYxNzYxNTIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiZTQ3NmExN2QtNjRkMi00ZTM2LTljNWQtZmFjZjdmMWI0ZTQ4IiwiY2xpZW50X2lkIjoiY2xpZW50Iiwic2NvcGUiOlsiYXBwIl19.y4r5QJRGsPcS12NsjMrN7WSSF0ts886pTRvlJQY5594
请求成功：
[{"sex":"man","name":"user0","tel":"18148689820"},{"sex":"man","name":"user1","tel":"18148689821"},{"sex":"man","name":"user2","tel":"18148689822"},{"sex":"man","name":"user3","tel":"18148689823"},{"sex":"man","name":"user4","tel":"18148689824"},{"sex":"man","name":"user5","tel":"18148689825"},{"sex":"man","name":"user6","tel":"18148689826"},{"sex":"man","name":"user7","tel":"18148689827"},{"sex":"man","name":"user8","tel":"18148689828"},{"sex":"man","name":"user9","tel":"18148689829"}]


请求刷新token：
http://localhost:18084/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFwcCJdLCJhdGkiOiJlNDc2YTE3ZC02NGQyLTRlMzYtOWM1ZC1mYWNmN2YxYjRlNDgiLCJleHAiOjE1Nzg3MjQ5NTIsImF1dGhvcml0aWVzIjpbImFkbWluIl0sImp0aSI6ImVkNmZhOTQyLTMxOTAtNDdkOS04NzAxLTNhODMxMWY5MmI4OCIsImNsaWVudF9pZCI6ImNsaWVudCJ9.iqpfJaEJr-YmPo8rvVWedL8JnBKNka-sp5ht9gKnl54&client_id=client&client_secret=secret
返回：
{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzYxNzY0NTgsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiYjQxMWYyOTctMTc4MC00ODE5LWI3YWMtNTYyNDYzYzdlODU2IiwiY2xpZW50X2lkIjoiY2xpZW50Iiwic2NvcGUiOlsiYXBwIl19.wWM0f6lpCuVPdONd-0JtfmSZbHMfC3eavl1E6OE6jLk","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFwcCJdLCJhdGkiOiJiNDExZjI5Ny0xNzgwLTQ4MTktYjdhYy01NjI0NjNjN2U4NTYiLCJleHAiOjE1Nzg3MjQ5NTIsImF1dGhvcml0aWVzIjpbImFkbWluIl0sImp0aSI6ImVkNmZhOTQyLTMxOTAtNDdkOS04NzAxLTNhODMxMWY5MmI4OCIsImNsaWVudF9pZCI6ImNsaWVudCJ9.K2fKCDkYGF7wiwF2DiI8hd3IItfcZN6GO4PI2vfsrjE","expires_in":43199,"scope":"app","user_name":"admin","jti":"b411f297-1780-4819-b7ac-562463c7e856"}
```
## 简单模式
```
请求：
http://localhost:18084/oauth/authorize?response_type=token&client_id=client2&redirect_uri=http://baidu.com&state=123
登录授权后返回：
https://www.baidu.com/#access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzYxNzY5MDcsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiZjBlZjlmN2YtZjczZi00MjllLWEzMTEtN2ExNzdkZWQ3MjEzIiwiY2xpZW50X2lkIjoiY2xpZW50MiIsInNjb3BlIjpbImFwcCJdfQ.3g8-_Rw2gDEqYCCILwt9-8tbZO5FAXzm3mhuuC6Vgjw&token_type=bearer&state=123&expires_in=43199&scope=app&user_name=admin&jti=f0ef9f7f-f73f-429e-a311-7a177ded7213

请求资源：
http://localhost:18084/get/users/list?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzYxNzY5MDcsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiZjBlZjlmN2YtZjczZi00MjllLWEzMTEtN2ExNzdkZWQ3MjEzIiwiY2xpZW50X2lkIjoiY2xpZW50MiIsInNjb3BlIjpbImFwcCJdfQ.3g8-_Rw2gDEqYCCILwt9-8tbZO5FAXzm3mhuuC6Vgjw
返回：
[{"sex":"man","name":"user0","tel":"18148689820"},{"sex":"man","name":"user1","tel":"18148689821"},{"sex":"man","name":"user2","tel":"18148689822"},{"sex":"man","name":"user3","tel":"18148689823"},{"sex":"man","name":"user4","tel":"18148689824"},{"sex":"man","name":"user5","tel":"18148689825"},{"sex":"man","name":"user6","tel":"18148689826"},{"sex":"man","name":"user7","tel":"18148689827"},{"sex":"man","name":"user8","tel":"18148689828"},{"sex":"man","name":"user9","tel":"18148689829"}]

```

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