<template>
  <div>
    <PostDetail v-if="blog" :post="blog" mode="blog" />
    <p v-else>로딩 중...</p>
  </div>
</template>

<script setup lang="ts">
import PostDetail from '@/components/postDetail.vue';
import { useAsyncData, useRoute } from 'nuxt/app';

const route = useRoute();

// useAsyncData의 반환 타입 지정
const { data: blog, error, pending } = useAsyncData<{ 
  blogId?: number; 
  categoryId: number; 
  thumbnail: string; 
  title: string; 
  content: string; 
} | null>(async () => {
  const blogId = route.params.blogId;
  return await $fetch(`/api/v1/blog/${blogId}`, {
    baseURL: useRuntimeConfig().public.apiBaseUrl,
    method: 'GET'
  });
});
</script>
