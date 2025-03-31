<template>
  <div class="bg-gray-50 dark:bg-gray-800 min-h-screen transition-colors duration-300">
    <div v-if="project" class="container mx-auto px-4 py-8">
      <PostDetail :post="project" mode="project" />
    </div>
    <div v-else-if="pending" class="flex justify-center items-center py-20">
      <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-purple-500"></div>
    </div>
    <div v-else class="container mx-auto px-4 py-20 text-center">
      <p class="text-xl text-gray-700 dark:text-gray-300 transition-colors duration-300">프로젝트를 찾을 수 없습니다.</p>
      <NuxtLink to="/project" class="mt-4 inline-block px-6 py-2 bg-purple-600 dark:bg-purple-700 text-white rounded-lg hover:bg-purple-700 dark:hover:bg-purple-800 transition-colors duration-300">
        프로젝트 목록으로 돌아가기
      </NuxtLink>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAsyncData, useRoute } from 'nuxt/app';
import PostDetail from '~/components/postDetail.vue';

const route = useRoute();

// useAsyncData의 반환 타입 지정
const { data: project, error, pending } = useAsyncData<{ 
  projectId?: number; 
  thumbnail: string; 
  title: string; 
  content: string;
  createdAt?: string;
  updatedAt?: string;
  category?: { id: number; name: string };
  tags?: Array<{ id: number; name: string }>;
} | null>(async () => {
  const projectId = route.params.projectId;
  try {
    return await $fetch(`/api/v1/project/${projectId}`, {
      baseURL: useRuntimeConfig().public.apiBaseUrl,
      method: 'GET'
    });
  } catch (error) {
    console.error('프로젝트 상세 정보 불러오기 실패:', error);
    return null;
  }
});
</script>