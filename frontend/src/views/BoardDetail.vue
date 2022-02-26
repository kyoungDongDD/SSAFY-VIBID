<template>
  <div>
    <board-detail />
    <Footer />
  </div>
</template>

<script>
import BoardDetail from "@/components/Board/BoardDetail.vue";
import Footer from '@/components/header-footer/Footer.vue'

export default {
  components: {
    BoardDetail,
    Footer,

  },
};
</script>

<style lang="scss" scoped></style>
