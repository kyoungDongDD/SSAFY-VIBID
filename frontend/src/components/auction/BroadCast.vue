<template>
  <div id="broadCast">
    <auction-alert
      v-if="startAlert"
      @start="start"
      @cancel="cancel" />
    <user-video :stream-manager="mainStreamManager" />
    <!-- <user-video
      v-for="(sub, index) in subscriber"
      :key="index"
      :stream-manager="sub" /> -->
    <div
      :class="{'vertical':isVertical}"
      class="buttons">
      <seller-buttons
        :is-vertical="isVertical"
        :is-start="isStart"
        :is-published="isPublished"
        :audio-active="audioActive"
        @leave-session="leaveSession"
        @audio-toggle="audioToggle"
        @auction-start="auctionStart"
        @camera-on="cameraOn"
        @camera-off="cameraOff" />
    </div>
  </div>
</template>

<script>
import { OpenVidu } from '../../../node_modules/openvidu-browser/';
import { ref } from 'vue'
import { postRequest, deleteRequest, putRequest } from '@/api/requests.js'

import UserVideo from '@/components/auction/UserVideo';
import AuctionAlert from '@/components/auction/AuctionAlert';
import SellerButtons from '@/components/auction/SellerButtons';
// import buyerButtons from '@/components/auction/buyerButtons';
export default {
	components: {
		UserVideo,
		SellerButtons,
		AuctionAlert
		// buyerButtons
	},
	props: {
		isVertical: {
			type: Boolean,
			default: false
		},
	},
	setup(props) {
		const publisher = ref(undefined)
		const subscriber = ref([])
		const mainStreamManager = ref(undefined)
		const isPublished = ref(false)
		const audioActive = ref(false)
		const startAlert = ref(false)
		const isStart = ref(false)

		//오픈비두 오브젝트 생성
		let OV = new OpenVidu();
		//세션 초기화
		let session = OV.initSession();
		//세션 이벤트 할당
		session.on('streamCreated', ({ stream }) => { subscriber.value.push(session.subscribe(stream))	});
		session.on('streamDestroyed', () => { subscriber.value = [] });
		session.on('exception', ({ exception }) => { console.warn(exception); });

		//토큰 발급 함수
		const getToken = async () => {
			const { data } = await postRequest('/api/live/10', {}, localStorage.getItem('jwt'))
			console.log(data)
			return data.response
		}
		//토큰 발급
		let token = ref(undefined)

		getToken()
		.then(data => {
			console.log('get token')
			token.value = data; 
			console.log(token.value)
			session.connect(token.value, { clientData: "asdfasdf" });
		})
		.catch(error => { console.log('에러 발생'); console.log(error)})
	
		//퍼블리싱
		const cameraOn = () => {
			const tmp = {
				audioSource: undefined, 
				videoSource: undefined, 
				publishAudio: false, 
				publishVideo: true,  
				resolution: '1600x900', 
				frameRate: 30,	
				insertMode: 'APPEND',
				mirror: false
			}
			let pub = OV.initPublisher(undefined, tmp)
			mainStreamManager.value = pub
			publisher.value = pub

			session.publish(publisher.value)
			isPublished.value = true;	
		}
		const cameraOff = () => {
			session.forceUnpublish(publisher.value);
		}
		const start = async () => {
			startAlert.value = false
			isStart.value = true
			await putRequest('/api/live/start/10', {}, localStorage.getItem('jwt'))
			.then(res => console.log(res))
		}
		const cancel = () => {
			startAlert.value = false
		}
		const auctionStart = () => {
			startAlert.value = true
		}
		const audioToggle = () => { 
			if(!publisher.value.stream.audioActive){
				publisher.value.publishAudio(true);
				audioActive.value = true;
			}
			else{
				publisher.value.publishAudio(false);
				audioActive.value = false;		
			}
		}

		const leaveSession = () => {
			if (session) session.disconnect();
			deleteRequest('/api/live',{ "boardId":10, "token":null }, localStorage.getItem('jwt'))

			session = undefined;
			OV = undefined;
			token.value = undefined;
			mainStreamManager.value = undefined;
			publisher.value = undefined;
			subscriber.value = [];

			window.removeEventListener('beforeunload', leaveSession);
		}

		//server side
		return {
			mainStreamManager,
			subscriber,
			isPublished,
			audioActive,
			cameraOn,
			cameraOff,
			audioToggle,
			token,
			leaveSession,
			isStart,
			auctionStart,
			startAlert,
			start,
			cancel
		}
	},
}
</script>

<style lang="scss" scoped>
#broadCast{
	position: relative;
	display: flex;
	flex-direction: column;
	justify-content: space-evenly;
	height: 100%;
	align-items: center;
	background-color: rgba($color: #000000, $alpha: .85);
	.vertical {
		position: absolute;
		bottom: 5px;
	}
}

</style>
