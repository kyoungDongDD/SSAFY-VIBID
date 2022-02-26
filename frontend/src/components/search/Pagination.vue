<template>
  <div id="pagination">
    <button
      href=""
      class="pagination-button go-prev">
      {{ '<<' }}
    </button>
    <button
      class="pagination-button go-first">
      {{ "<" }}
    </button>
    <button class="pagination-button">
      <strong>1</strong>
    </button>
    <button class="pagination-button">
      <strong>2</strong>
    </button>
    <button class="pagination-button">
      <strong>3</strong>
    </button>
    <button class="pagination-button">
      <strong>4</strong>
    </button>
    <button class="pagination-button">
      <strong>5</strong>
    </button>
    <button
      class="pagination-button go-next">
      {{ ">" }}
    </button>
    <button
      class="pagination-button go-last">
      {{ ">>" }}
    </button>
  </div>
</template>

<script>
export default {
  
}
</script>

<style lang="scss" scoped>
	#pagination {
		display: flex;
		justify-content: center;
		align-items: center;
		margin: 100px;
		@media #{$mobile} {
			margin: 100px;
		}
		.pagination-button {
			width: 28px;
			height: 28px;
			text-align: center;
			border: 1px solid #ddd;
			border-right: none;

			&:last-child {
				border-right: 1px solid #ddd;
			}
		}
	}
</style>