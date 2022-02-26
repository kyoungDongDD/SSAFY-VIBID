<template>
  <div>
    <board-write />
    <Footer />
  </div>
</template>

<script>
import BoardWrite from "@/components/Board/BoardWrite.vue";
import Footer from '@/components/header-footer/Footer.vue'

export default {
  components: {
    BoardWrite,
    Footer,
  },
};
</script>

<style lang="scss" scoped></style>
