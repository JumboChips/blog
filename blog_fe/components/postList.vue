<template>
  <div class="container mx-auto px-4 py-12 sm:px-8">
    <div v-if="isLoggedIn" class="mb-6 flex justify-end">
      <router-link :to="createPath" class="rounded bg-blue-500 px-4 py-2 font-bold text-white hover:bg-blue-700">
        Create Post
      </router-link>
    </div>

    <section class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
      <NuxtLink v-for="item in items" :key="item.id" :to="`${basePath}/${item.id}`"
        class="block overflow-hidden rounded bg-white shadow">
        <img :src="item.image" alt="Post Image" class="h-48 w-full object-cover" />
        <div class="p-4">
          <h2 class="mb-2 text-xl font-bold text-gray-900">{{ item.title }}</h2>
          <p class="line-clamp-3 text-gray-700">{{ extractTextFromHTML(item.description) }}</p>
        </div>
      </NuxtLink>
    </section>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '~/stores/auth';

// Pinia 스토어에서 가져온 값
const authStore = useAuthStore();
const isLoggedIn = authStore.isLoggedIn;

const extractTextFromHTML = (html : String) => {
    // <p> 태그를 제거하고 줄바꿈 문자를 추가
    let withoutPTags = html.replace(/<p[^>]*>(.*?)<\/p>/g, '$1\n');

    // 나머지 태그들 제거
    let withoutTags = withoutPTags.replace(/<[^>]*>/g, '');

    // &nbsp; 제거
    let withoutNBSP = withoutTags.replace(/&nbsp;/g, ' ');

    return withoutNBSP;
}


// Props 타입 정의
defineProps<{
  items: Array<{ id: number; title: string; description: string; image: string }>;
  basePath: string;
  createPath: string;   // Create Post 버튼 경로
}>();
</script>
