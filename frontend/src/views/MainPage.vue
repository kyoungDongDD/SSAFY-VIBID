<template>
  <div>
    <Main />
    <Footer />
  </div>
</template>

<script>
import Main from "@/components/main/Main.vue";
import Footer from '@/components/header-footer/Footer.vue'

export default {
  components: {
    Main,
    Footer,
  },
  setup() {
    (function () { document.title = "Vibid"})();
  }
};
</script>