<template>
	<view style="font-size: 20px;color: #bfa;">
		{{msg}}
		{{code}}
	</view>
	<button @tap="start">开始服务</button>
	<button @tap="stop">结束服务</button>
</template>

<script setup>
	import {
		ref
	} from 'vue'
	import {
		onLoad
	} from '@dcloudio/uni-app'
	const msg = ref('')
	const code = ref('')

	const start = () => {
		const KeepAlive = uni.requireNativePlugin('Keep_Alive')
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
		const KeepAlive = uni.requireNativePlugin('Keep_Alive')
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
</script>

<style lang="scss">

</style>