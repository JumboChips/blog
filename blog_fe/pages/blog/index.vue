<template>
  <PostList mode="blog" :items="mappedBlogPosts" @postDeleted="removePost" />
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import PostList from '@/components/postList.vue';

// BlogResponse 타입 정의
interface BlogResponseDto {
  blogId: number;
  title: string;
  content: string;
  thumbnail: string;
}

// 상태 변수
const blogPosts = ref<BlogResponseDto[]>([]);
const mappedBlogPosts = computed(() =>
  blogPosts.value.map(post => ({
    id: post.blogId,
    title: post.title,
    description: post.content,
    image: post.thumbnail
  }))
);

const fetchBlogPosts = async () => {
  try {
    const response = await $fetch<BlogResponseDto[]>('/api/v1/blog', {
      baseURL: useRuntimeConfig().public.apiBaseUrl,
      method: 'GET'
    });
    blogPosts.value = response;
    console.log('블로그 불러오기 성공:', response);
  } catch (error) {
    console.error('블로그 불러오기 실패:', error);
  }
};

// 삭제된 게시물 목록에서 제거
const removePost = (id: number) => {
  blogPosts.value = blogPosts.value.filter(post => post.blogId !== id);
};


onMounted(fetchBlogPosts);
</script>
