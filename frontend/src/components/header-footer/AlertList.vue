<template>
  <div id="alertList">
    <alert-card
      v-for="(data, index) in datas"
      :key="`${index}=${data.time}`"
      :data="data" />
  </div>
</template>

<script>
import AlertCard from '@/components/header-footer/AlertCard.vue'
export default {
	components: {
		AlertCard
	},
	setup () {
		const datas = [
			{ 
				content: "누구누구님의 경매가 몇 시간 남았습니다.", 
				time: '0000 00 00',
			},
			{ 
				content: "누구누구님의 경매가 몇 시간 남았습니다.", 
				time: '0000 00 00',
			},
			{ 
				content: "누구누구님의 경매가 몇 시간 남았습니다.", 
				time: '0000 00 00',
			},
			{ 
				content: "누구누구님의 경매가 몇 시간 남았습니다.", 
				time: '0000 00 00',
			},
			{ 
				content: "누구누구님의 경매가 몇 시간 남았습니다.", 
				time: '0000 00 00',
			},
			{ 
				content: "누구누구님의 경매가 몇 시간 남았습니다.", 
				time: '0000 00 00',
			},
			{ 
				content: "누구누구님의 경매가 몇 시간 남았습니다.", 
				time: '0000 00 00',
			},
			{ 
				content: "누구누구님의 경매가 몇 시간 남았습니다.", 
				time: '0000 00 00',
			},
		]
		return {
			datas
			}
	}
}
</script>