<template>
  <div class="bg-gray-50 dark:bg-gray-800 min-h-screen transition-colors duration-300">
    <div class="container mx-auto px-4 py-8">
      <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-8 text-center transition-colors duration-300">Project</h1>
      <PostList mode="project" :items="mappedProjectPosts" @postDeleted="removePost" />
      
      <!-- 로딩 상태 표시 -->
      <div v-if="isLoading" class="flex justify-center items-center py-12">
        <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-purple-500"></div>
      </div>
      
      <!-- 데이터가 없을 때 표시 -->
      <div v-if="!isLoading && mappedProjectPosts.length === 0" class="text-center py-12">
        <p class="text-gray-600 dark:text-gray-400 text-lg transition-colors duration-300">아직 등록된 프로젝트가 없습니다.</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import PostList from '@/components/postList.vue';

// ProjectResponse 타입 정의
interface ProjectResponseDto {
  projectId: number;
  title: string;
  content: string;
  thumbnail: string;
}

// 상태 변수
const projectPosts = ref<ProjectResponseDto[]>([]);
const isLoading = ref(true);

const mappedProjectPosts = computed(() =>
  projectPosts.value.map(post => ({
    id: post.projectId,
    title: post.title,
    description: post.content,
    image: post.thumbnail
  }))
);

const fetchProjectPosts = async () => {
  isLoading.value = true;
  try {
    const response = await $fetch<ProjectResponseDto[]>('/api/v1/project', {
      baseURL: useRuntimeConfig().public.apiBaseUrl,
      method: 'GET'
    });
    projectPosts.value = response;
  } catch (error) {
    console.error('프로젝트 불러오기 실패:', error);
  } finally {
    isLoading.value = false;
  }
};

// 삭제된 게시물 목록에서 제거
const removePost = (id: number) => {
  projectPosts.value = projectPosts.value.filter(post => post.projectId !== id);
};

onMounted(fetchProjectPosts);
</script>