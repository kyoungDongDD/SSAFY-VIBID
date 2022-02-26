<template>
  <div
    id="userVideo"
    ref="video"
    :style="{height: videoHeight}">
    <div
      v-if="streamManager">
      <time-bar />
      <ov-video :stream-manager="streamManager" />
    </div>
  </div>
</template>

<script>
import TimeBar from '@/components/auction/TimeBar.vue'
import OvVideo from '@/components/auction/OvVideo';
import {computed} from '@vue/runtime-core';

export default {
	components: {
    TimeBar,
		OvVideo,
	},
	props: {
		streamManager: Object,
	},
	setup (props) {
		const getConnectionData = () => {
			const { connection } = props.streamManager.stream;
			return JSON.parse(connection.data.slice(0, -17));
		}
		const clientData = computed(()=> {
			const { clientData } = getConnectionData();
			return clientData;
		})
		return {clientData}
	},
	data () {
		return {
			videoHeight: '',
			throttle: 0
		}
	},
	mounted() {
		this.setVideoHeight();
		window.addEventListener('resize', ()=>this.setVideoHeight());
		window.removeEventListener('resize', ()=>{});
	},
	methods: {
		setVideoHeight () {
			if(this.throttle) return;

			this.throttle = setTimeout(() => {
				this.videoHeight = `${this.$refs.video.clientWidth * 9 / 16}px`
				this.throttle = null
			}, 200);
		}
	}
};
</script>

<style lang="scss" scoped>
#userVideo {
	width: 100%;
	background-color: #000;
}
</style>