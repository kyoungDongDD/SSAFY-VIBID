<template>
  <div>
    <sign-up />
    <Footer />
  </div>
</template>

<script>
import SignUp from "@/components/Member/SignUp.vue";
import Footer from "@/components/header-footer/Footer.vue";

export default {
  components: {
    SignUp,
    Footer,
  },
};
</script>

<style lang="scss" scoped></style>
