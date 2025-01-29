<template>
  <ProjectList :items="mappedProjectPosts" base-path="/project" create-path="/project/post"></ProjectList>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue';
import ProjectList from '@/components/postList.vue'

// ProjectResponse 타입 정의
interface ProjectResponseDto {
  projectId: number;
  categoryId: number;
  thumbnail: string;
  title: string;
  content: string;
  tagIds: number[];
}

// 상태 변수 선언
const projectPosts = ref<ProjectResponseDto[]>([]);
const isLoading = ref(true);
const error = ref<string | null>(null);

// computed로 postList.vue가 기대하는 데이터 형식으로 매핑
const mappedProjectPosts = computed(() =>
  projectPosts.value.map((post) => ({
    id: post.projectId,
    title: post.title,
    description: post.content,
    image: post.thumbnail
  }))
);

const fetchProjectPosts = async () => {
  try {
    // 반환 타입을 BlogResponseDto[]로 명시
    const response = await $fetch<ProjectResponseDto[]>('/api/v1/project', {
      baseURL: useRuntimeConfig().public.apiBaseUrl,
      method: 'GET'
    });
    projectPosts.value = response;
  } catch (err: any) {
    error.value = err.message || 'Failed to load project posts.';
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  fetchProjectPosts();
})
</script>