<template>
  <div
    id="auctionPage"
    class="h-100">
    <auction />
  </div>
</template>

<script>
import Auction from "@/components/auction/Auction.vue"

export default {
  components: {
    Auction,
  },
  setup() {
    (function () { document.title = "Vibid | 경매"})()
  }
}
</script>
