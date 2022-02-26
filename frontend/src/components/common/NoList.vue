<template>
  <div id="noList">
    <img
      src="@/assets/no_search.png"
      alt="" />
    <p>{{ content }} 존재하지 않습니다.</p>
  </div>
</template>

<script>
export default {
	props: {
		content: {
			type: String,
			default: '목록이'
		}
	}
}
</script>

<style lang="scss" scoped>

#noList {
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100%;
	height: 80vh;

	img{
		width: 5%;
		margin-right: 10px;
	}
	p{
		font-size: 25px;
	}
}
</style>