<template>
  <div style="height: inherit">
    <swiper
      :slides-per-view="option.slidesPerView"
      :space-between="option.spaceBetween"
      :slides-per-group="option.slidesPerGroup"
      :free-mode="option.freeMode"
      :navigation="option.navigation"
      :clickable="true"
      class="swiper">
      <swiper-slide
        v-for="item in items"
        :key="item.id">
        <card :item="item" />
      </swiper-slide>
    </swiper>
    <div
      class="swiper-next"
      :class="`${el}-next`"></div>
    <div
      class="swiper-prev"
      :class="`${el}-prev`"></div>
  </div>
</template>

<script>
import Card from '@/components/common/Card.vue'
import RouteButton from '@/components/common/RouteButton.vue';
import { Swiper, SwiperSlide } from 'swiper/vue';
import 'swiper/css';
import "swiper/css/navigation"
import "swiper/css/free-mode"
import SwiperCore, {
  Pagination,Navigation,FreeMode
} from 'swiper';
SwiperCore.use([FreeMode,Pagination,Navigation]);

export default {
	components: {
    Swiper,
    SwiperSlide,
    Card,
		RouteButton
  },
	props: {
		items: {
			type: Array,
			default () {
				return []
			}
		},
		el: {
			type: String,
			default: ''
		},
		option: {
			type: Object,
			default () {
				return {
					slidesPerView: 1,
					spaceBetween:10,
					freeMode:false,
					slidesPerGroup:1,
					navigation:false
				}
			}
		},
	},
}
</script>

<style lang="scss" scoped>
	.swiper {
		width: 100%;
		height: 100%;
	}

	.swiper-slide {
		height: inherit;
		text-align: center;
		font-size: 18px;
		background: #fff;

		/* Center slide text vertically */
		display: -webkit-box;
		display: -ms-flexbox;
		display: -webkit-flex;
		display: flex;
		-webkit-box-pack: center;
		-ms-flex-pack: center;
		-webkit-justify-content: center;
		justify-content: center;
		-webkit-box-align: center;
		-ms-flex-align: center;
		-webkit-align-items: center;
		align-items: center;
		&>button {
			width: 100%;
		}
	}
	.swiper-next{
		right: -25px;
		background: url('@/assets/next.png');
		box-shadow: 2px 0px 10px rgba($color: #000000, $alpha: .1);
		&:hover {
			box-shadow: 2px 0px 10px rgba($color: #000000, $alpha: .2);
		}
	}
	.swiper-prev{
		left: -25px;
		background: url('@/assets/prev.png');
		box-shadow: -2px 0px 10px rgba($color: #000000, $alpha: .1);
		&:hover {
			box-shadow: -2px 0px 10px rgba($color: #000000, $alpha: .2);
		}}
	.swiper-next,
	.swiper-prev
	{
		@media #{$tablet}{
			display: none;
		}
		position: absolute;
		cursor: pointer;
		top: calc(200px - 25px);
		z-index: 100;
		width: 50px;
		height: 50px;
		border-radius: 50%;
		background-size: 50px;
		transition: box-shadow .3s;
		

		&::after {
			display: none;
			right: 1000px;
		}
	}
</style>