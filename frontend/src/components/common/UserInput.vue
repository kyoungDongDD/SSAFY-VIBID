<template>
  <input
    :value="$store.state.Search.tags"
    type="text"
    @input="userInput" />
</template>

<script>
import { useStore } from 'vuex'

export default {
	setup() {
		const store = useStore()
    const userInput = (event) => store.dispatch('changeTags', event.target.value)
		return {
			userInput,
		}
	}
}
</script>