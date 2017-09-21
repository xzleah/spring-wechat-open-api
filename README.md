# spring-wechat-open-api

简化微信调用工作：

* access token和jsApi Ticket会根据微信响应的过期时间智能制定下次刷新的时间，避免无效调用；
* 用java bean封装接口请求和响应数据；
* 和spring整合良好；

## Maven配置

把下面依赖添加到POM.xml中：

```xml
<dependency>
	<groupId>com.rratchet</groupId>
	<artifactId>spring-wechat-open-api</artifactId>
	<version>1.0.0</version>
</dependency>
```

## 快速开始

spring Annotation配置：

	@Configuration
    public class AppConfig {
		@Bean
		public WechatClient wechatClient() {
			return WechatClientBuilder.config()
						.validationToken(token)
						.appId(appId)
						.appSecret(appSecret)
						.enableJsApiTicketManager()
						.build();
		}
    }

现在，可以在你的类中调用微信开放接口了：

	@Component
    public class Example {
		
		@Autowired
		private WechatClient wechatClient;

		public void sendHelloTemplateMessage() {
			TemplateMessageSendAPIRequest request = new TemplateMessageSendAPIRequest();
			//构建request的代码..
			TemplateMessageSendAPIResponse response = wechatClient.templateMessageAPI().send(request);
			//处理response的代码..
		}
	}

## 功能清单

* 生成微信校验签名
* 刷新access token
* 刷新jsapi ticket
* 生成jsapi签名
* 交换web access token
* 发送模板消息
* 创建菜单
* 查询菜单

## 常见问题

###多个公众号怎么配置？

可以配置多个WechatClient，每个WechetClient由独立上下文。

	@Configuration
    public class AppConfig {
		@Bean(name = "weclient1")
		public WechatClient wechatClient1() {
			return WechatClientBuilder.config()
						.validationToken(token)
						.appId(appId)
						.appSecret(appSecret)
						.enableJsApiTicketManager()
						.build();
		}

		@Bean(name = "weclient2")
		public WechatClient wechatClient2() {
			return WechatClientBuilder.config()
						.validationToken(token2)
						.appId(appId2)
						.appSecret(appSecret2)
						.build();
		}

		//..
    }


### 为何会有AccessTokenExpiredException

虽然AccessTokenManager会根据微信Token请求接口的响应制定下次请求Token的时间，但如果在这期间，微信单方面使Token过期，就会抛出AccessTokenExpiredException异常。

对于这种微信不守承诺的情况，客户端代码可以捕捉AccessTokenExpiredException异常，然后调用refresh()刷新后重试：

    try {
		wechatClient.templateMessageAPI().send(request);
	} catch(AccessTokenExpiredException e) {
		wechatClient.accessTokenManager().refresh();
		wechatClient.templateMessageAPI().send(request);
	}

不用担心高并发下refresh()会重复请求微信Token导致API调用次数过高被封，因为refresh()已经做了并发限制，一秒内不能多次调用。也可以自定义间隔时间：

	@Bean(name = "weclient1")
	public WechatClient wechatClient1() {
		return WechatClientBuilder.config()
					.accessTokenInvokeInterval(10)  // 设置10秒内只能获取一次accessToken
					.validationToken(token)
					.appId(appId)
					.appSecret(appSecret)
					.enableJsApiTicketManager()
					.jsApiTicketInvokeInterval(7) // 设置7秒内只能获取一次JS API ticket
					.build();
	}
