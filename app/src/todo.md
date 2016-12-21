1: 获取用户openid
2: 在用户访问到主界面时，请求一个access_token并做好维护，参考https://mp.weixin.qq.com/wiki/11/0e4b294685f817b95cbed85ba5e82b8f.html
3: 如果要维持页面不过期，则需心跳并且在access_token将要过期的时候刷新access_token
4: 由后端提供获取微信用户信息，传入openid，调用微信接口获取，参考https://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
5: 由后端提供根据经纬度获取省份城市