<template>
  <div
    id="loader"
    :style="{height: height}">
    <dot-loader
      :size="size"
      :color="'#6993c4'" />
  </div>
</template>

<script>
import DotLoader from 'vue-spinner/src/DotLoader.vue'

export default {
	components: {
		DotLoader
	},
	props: {
		size: {
			type: String,
			default: '100px'
		},
		height: {
			type: String,
			default: '80vw'
		}
	}
}
</script>

<style lang="scss" scoped>
#loader {
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100%;
	
}
</style>