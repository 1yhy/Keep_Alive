### [uni-app原生插件开发](https://www.yhy.gd.cn/articles/140)

### 使用方法

在 `script` 中引入组件

```javascript
    const KeepAlive = uni.requireNativePlugin('XY-Keep_Alive')
```

在 `script` 中调用

```javascript
const start = () => {
		const KeepAlive = uni.requireNativePlugin('XY-Keep_Alive')
		// 启动服务
		KeepAlive.start({
				"title": "app",
				"text": "正在后台运行"
			},
			function(ret) {
				console.log(ret)
				if (ret.code == 1) {
					uni.showToast({
						title: '启动成功',
						icon: 'none'
					})
				}
			})
	}
	const stop = () => {
		const KeepAlive = uni.requireNativePlugin('XY-Keep_Alive')
		// 销毁服务
		KeepAlive.destroy(
			function(ret) {
				if (ret.code === 0) {
					uni.showToast({
						title: '停止服务',
						icon: 'none'
					})
				}

			})
	}
```

## 方法清单

|  名称   |     说明     |
| :-----: | :----------: |
|  start  | 开启保活服务 |
| destroy | 注销保活服务 |

### register 方法参数

注册保活服务

|    属性名    |  类型   | 必填  | 默认值 |                     说明                      |
| :----------: | :-----: | :---: | :----: | :-------------------------------------------: |
|    title     | String  | 建议  |        |              通知栏标题，非必传               |
|   content    | String  | 建议  |        |              通知栏内容，非必传               |
| workManager  | Boolean | false |        | 是否可以使用WorkManager，默认可以使用，非必传 |
| onePxEnabled | Boolean | false |  true  |   是否可以使用一像素，默认可以使用，非必传    |
