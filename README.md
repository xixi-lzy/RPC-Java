基于 Vert.x 和 Etcd 实现的高性能 Java RPC 框架，支持服务注册发现、负载均衡、容错重试等分布式特性。

## 核心特性
- 基于 Vert.x 的 TCP 高性能通信
- SPI 机制支持组件扩展
- 支持多种序列化协议
- 内置三种负载均衡策略
- 心跳检测与服务健康监测
- 支持固定间隔重试策略
- 协议头设计：| 魔数(1B) | 版本(1B) | 序列化(1B) | 类型(1B) | 状态(1B) | 请求ID(8B) | 数据长度(4B) | 数据 |

- ## 快速开始
1. 启动 Provider
```java
// 2.实现服务接口
@RpcService(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
  public User getUser(User user) {
      return user;
  }
}
```

```java
//3.调用服务
UserService userService = ServiceProxyFactory.getProxy(UserService.class);
User result = userService.getUser(new User("xixi"));
```

环境要求
JDK 17+
Maven 3.6+
Etcd 3.5+ (服务注册发现)


配置项 application.yaml
```java
rpc:
  serverPort: 8081
  serializer: json       # 可选 jdk/hessian/kryo
  loadBalancer: random   # 可选 roundRobin/consistentHash
  retryStrategy: fixedInterval
```

注意事项  
1. 配置文件加载顺序：application.yml > application.properties  
2. 通过 SPI 机制可扩展序列化、负载均衡等组件  
3. 使用一致性哈希负载均衡时需确保服务节点均匀分布  
