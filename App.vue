<script>
	export default {
		onLaunch: function() {
			if (!uni.getStorageSync('user') && !uni.getStorageSync('isLogin')) {
				uni.redirectTo({
					url: '/pages/login/login'
				})
			}
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	@import "./uni_modules/vk-uview-ui/index.scss";

	/*每个页面公共css */
	page {
		background-color: #f8f8f8;
	}

	* {
		margin: 0;
		padding: 0;
		box-sizing: border-box;
	}

	.border {
		position: relative;
		width: 100%;
	}

	.border:not(:last-child)::after {
		content: '';
		position: absolute;
		bottom: 0;
		left: 0;
		border-top: 1px solid #e3e3e3;
		transform: scaleY(0.3);
		width: 100%;
	}
</style>