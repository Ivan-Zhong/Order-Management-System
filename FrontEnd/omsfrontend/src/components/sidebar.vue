<template>

    <div class="sidebar">

    </div>
</template>

<script>
export default ({
    name:"sidebar",
    data() {
        return {
            role : this.$store.state.identity//获得用户类型
        };
    },
})
</script>

<style scoped>
.sidebar{

}
</style>

