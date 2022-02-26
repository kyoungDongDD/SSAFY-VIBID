<template>
  <div>
    <board-modify />
    <Footer />
  </div>
</template>

<script>
import BoardModify from "@/components/Board/BoardModify.vue";
import Footer from '@/components/header-footer/Footer.vue'

export default {
  components: {
    BoardModify,
    Footer,

  },
};
</script>

<style lang="scss" scoped></style>
