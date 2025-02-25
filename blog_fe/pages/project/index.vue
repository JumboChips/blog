<template>
  <PostList mode="project" :items="mappedProjectPosts" @postDeleted="removePost" />
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
const mappedProjectPosts = computed(() =>
  projectPosts.value.map(post => ({
    id: post.projectId,
    title: post.title,
    description: post.content,
    image: post.thumbnail
  }))
);

const fetchProjectPosts = async () => {
  try {
    const response = await $fetch<ProjectResponseDto[]>('/api/v1/project', {
      baseURL: useRuntimeConfig().public.apiBaseUrl,
      method: 'GET'
    });
    projectPosts.value = response;
  } catch (error) {
    console.error('프로젝트 불러오기 실패:', error);
  }
};

// 삭제된 게시물 목록에서 제거
const removePost = (id: number) => {
  projectPosts.value = projectPosts.value.filter(post => post.projectId !== id);
};


onMounted(fetchProjectPosts);
</script>
