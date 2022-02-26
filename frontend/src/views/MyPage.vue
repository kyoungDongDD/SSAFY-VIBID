<template>
  <div id="myPage">
    <user-data />
    <div class="inner">
      <list :direction="'x'" />
    </div>
    <Footer />
  </div>
</template>

<script>
import List from '@/components/common/List.vue'
import UserData from '@/components/Member/UserData.vue'
import Footer from '@/components/header-footer/Footer.vue'

export default {
  components: {
    List,
    UserData,
    Footer,

  },
  setup() {
    (function () { document.title = "Vibid | 마이페이지"})();
  }
}
</script>