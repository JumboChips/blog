<template>
  <editPost mode="project" :postId="postId" />
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import editPost from '~/components/editPost.vue';

// URL에서 ID 가져오기
const route = useRoute();
const postId = Number(route.query.id);
</script>
