<template>
  <video autoplay>
  </video>
</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
	},

	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>

<style lang="scss" scoped>
video {
	width: 100%;
}
</style>