<template>
  <div>
    <div id="userData">
      <div class="inner">
        <div class="profile">
          <div class="user-image">
            <img
              src="@/assets/user.png"
              alt="" />
          </div>
          <div class="user-name">
            <div class="nick-name">
              닉네임 없는 너구리
            </div>
            <div class="real-name">
              이주현
            </div>
          </div>
        </div>
        <div class="buttons container">
          <my-page-menu />
        </div>
      </div>
    </div>
    <div class="inner">
      <div class="selected">
        {{ $store.state.MyPage.menu }}
      </div>
    </div>
  </div>
</template>
<script>
import MyPageMenu from '@/components/Member/MyPageMenu.vue'
export default {
  components: { MyPageMenu },
}
</script>

<style lang="scss" scoped>
#userData {
	background-color: $main-color9;
	.inner{
		.profile{
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 50px 0 20px 0;
			.user-image {
				display: flex;
				justify-content: center;
				align-items: center;
				width: 20%;
				margin-left: 5%;
				border-radius: 50%;
				overflow: hidden;
				img {
					width: 100%;
				}
			}
			.user-name {
				width: 60%;
				text-align: center;
				.nick-name {
					font-size: 30px;
					font-weight: bold;
					margin-bottom: 30px;
				}
				.real-name {
					font-size: 15px;
					color:  $main-color3;
				}
				@media #{$mobile} {
					.nick-name {
						font-size: 30px;
						font-weight: bold;
						margin-bottom: 30px;
					}
					.real-name {
						font-size: 15px;
						color:  $main-color3;
					}
				}
			}
		}
		.buttons {
			position: relative;
			text-align: center;
			margin: 20px auto;
			button {
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				width: 70px;
				height: 70px;
				margin: 0 auto;
				border-radius: 50%;
				background-color: #fff;
				overflow: hidden;
				img {
					width: 40px;
				}
			}
			p {
				font-size: 14px;
				color: $main-color5;						
			}
		}
	}
}
.selected {
	margin-bottom: 20px;
	padding: 30px 0;
	font-size: 35px;
	font-weight: bold;
	text-align: center;
	line-height: 35px;
	@media #{$tablet} {
		font-size: 25px;
		line-height: 25px;
	}
	@media #{$mobile} {
		font-size: 20px;
		line-height: 20px;
	}
}
</style>