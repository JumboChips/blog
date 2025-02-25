<template>
  <div>
    <PostDetail v-if="project" :post="project" mode="project" />
    <p v-else>로딩 중...</p>
  </div>
</template>

<script setup lang="ts">
import PostDetail from '@/components/postDetail.vue';
import { useAsyncData, useRoute } from 'nuxt/app';

const route = useRoute();

// useAsyncData의 반환 타입 지정
const { data: project, error, pending } = useAsyncData<{ 
  projectId?: number; 
  categoryId: number; 
  thumbnail: string; 
  title: string; 
  content: string; 
} | null>(async () => {
  const projectId = route.params.projectId;
  return await $fetch(`/api/v1/project/${projectId}`, {
    baseURL: useRuntimeConfig().public.apiBaseUrl,
    method: 'GET'
  });
});
</script>
