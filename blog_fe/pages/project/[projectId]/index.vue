<template>
  <div>
    <div v-if="pending">Loading project...</div>
    <PostDetail v-else-if="project" :post="project" />
    <div v-else-if="error">Error loading project.</div>
  </div>
</template>
<script setup lang="ts">
import PostDetail from '@/components/postDetail.vue';

import { useAsyncData, useRoute } from 'nuxt/app';

// 동적 라우트 파라미터 가져오기
const route = useRoute();

// useAsyncData에 비동기 함수 전달
const { data: project, error, pending } = useAsyncData(async () => {
  const projectId = route.params.projectId;
  return await $fetch(`/api/v1/project/${projectId}`, {
    baseURL: useRuntimeConfig().public.apiBaseUrl,
    method: 'GET'
  });
});
</script>