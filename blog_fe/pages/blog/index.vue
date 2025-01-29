<template>
  <BlogList :items="mappedBlogPosts" base-path="/blog" create-path="/blog/post">
  </BlogList>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import BlogList from '@/components/postList.vue';

// BlogResponseDto 타입 정의
interface BlogResponseDto {
  blogId: number;
  categoryId: number;
  thumbnail: string;
  title: string;
  content: string;
  tagIds: number[];
}

// 상태 변수 선언
const blogPosts = ref<BlogResponseDto[]>([]);
const isLoading = ref(true);
const error = ref<string | null>(null);

// computed로 postList.vue가 기대하는 데이터 형식으로 매핑
const mappedBlogPosts = computed(() =>
  blogPosts.value.map((post) => ({
    id: post.blogId,
    title: post.title,
    description: post.content,
    image: post.thumbnail
  }))
);

// API 호출 함수
const fetchBlogPosts = async () => {
  try {
    // 반환 타입을 BlogResponseDto[]로 명시
    const response = await $fetch<BlogResponseDto[]>('/api/v1/blog', {
      baseURL: useRuntimeConfig().public.apiBaseUrl,
      method: 'GET'
    });
    blogPosts.value = response;
  } catch (err: any) {
    error.value = err.message || 'Failed to load blog posts.';
  } finally {
    isLoading.value = false;
  }
};

// 컴포넌트가 마운트될 때 API 호출
onMounted(() => {
  fetchBlogPosts();
});
</script>
