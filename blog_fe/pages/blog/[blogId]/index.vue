<template>
  <div>
    <div v-if="pending">Loading blog...</div>
    <PostDetail v-else-if="blog" :post="blog" />
    <div v-else-if="error">Error loading blog.</div>
  </div>
</template>

<script setup lang="ts">
import PostDetail from '@/components/postDetail.vue';
import { useAsyncData, useRoute } from 'nuxt/app';

// 동적 라우트 파라미터 가져오기
const route = useRoute();

// useAsyncData에 비동기 함수 전달
const { data: blog, error, pending } = useAsyncData(async () => {
  const blogId = route.params.blogId;
  return await $fetch(`/api/v1/blog/${blogId}`, {
    baseURL: useRuntimeConfig().public.apiBaseUrl,
    method: 'GET'
  });
});
</script>
