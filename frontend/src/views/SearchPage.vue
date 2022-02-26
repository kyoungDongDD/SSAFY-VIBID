<template>
  <div id="searchPage">
    <div class="banner"></div>
    <h1 class="heading">검색 결과</h1>
    <div class="inner">
      <h2 class="heading">검색창</h2>
      <search-bar />
      <h2 class="heading">정렬 조건</h2>
      <Filter />
      <list :num-per-row="3" />
      <h2 class="heading">Pagination</h2>
      <pagination />
    </div>
    <Footer />
  </div>
</template>

<script>
import SearchBar from '@/components/search/SearchBar.vue'
import Pagination from '@/components/search/Pagination.vue'
import List from '@/components/common/List.vue'
import Filter from '@/components/search/Filter.vue'
import Footer from '@/components/header-footer/Footer.vue'

export default {
	components: {
		SearchBar,
		List,
		Pagination,
		Filter,
    Footer,
	},
	setup() {
    (function () { document.title = "Vibid | 검색"})();
  }
}
</script>

<style lang="scss" scoped>
#searchPage {
	@media #{$mobile} {
		margin-top: 51px;			
	}
	.banner {
		width: 100vw;
		height: 300px;
		background-color: orange;
	}
}
</style>