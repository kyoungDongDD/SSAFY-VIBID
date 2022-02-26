<template>
  <div id="userMenu">
    <ul id="userButtons">
      <li
        v-for="menuButton in menuButtons"
        :key="menuButton.name">
        <route-button
          :data="menuButton"
          class="button home">
          <img
            :src="menuButton.src"
            alt="" />
        </route-button>
      </li>
    </ul>
  </div>
</template>

<script>
import RouteButton from "@/components/common/RouteButton.vue"
export default {
  components: {
    RouteButton,
  },
  setup() {
    const menuButtons = [
      {
        name: 'Main',
        path: '/',
        src: require('@/assets/home.png'),
        content: '홈'
      },
      {
        name: 'SearchPage',
        path: '/search',
        src: require('@/assets/menu_btn.png'),
        content: '전체 경매'
      },
      {
        name: 'SearchPage',
        path: '/search',
        src: require('@/assets/search.png'),
        content: '검색'
      },
      {
        name: 'MyPage',
        path: '/mypage',
        src: require('@/assets/user.png'),
        content: '마이페이지'
      },
    ]
    return {
      menuButtons,
    }
  }
}
</script>

<style lang="scss">
	#userButtons {
		position: fixed;
		display: flex;
		width: 100vw;
		bottom: 0;
		z-index: 500;
		background-color: #fff;
		>li {
			width: 25%;
      &:first-child{
        color: $main-color1;
      }
			.button {
				width: 100%;
				height: 65px;
				display: flex;
				flex-direction: column;
				align-items: center;
				padding-top: 10px;
				box-sizing: border-box;
				border-top: 1px solid rgba($color: #000000, $alpha: .2);
        cursor: pointer;

				img {
					width: 20px;
					margin-bottom: 5px;
				}
			}
		}
	}
</style>