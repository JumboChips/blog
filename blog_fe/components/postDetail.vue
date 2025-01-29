<template>
  <div class="max-w-4xl mx-auto p-6 bg-gray-50">
    <!-- 헤더 영역 -->
    <header class="mb-12">
      <h1 class="text-4xl font-extrabold text-gray-900 leading-tight mb-4">
        {{ post.title }}
      </h1>
      <div class="flex items-center gap-4 text-sm text-gray-500">
        <span class="px-3 py-1 bg-gray-200 text-gray-600 rounded-full">
          카테고리: {{ post.categoryId }}
        </span>
        <span>{{ new Date().toLocaleDateString() }}</span>
      </div>
    </header>

    <!-- 썸네일 -->
    <div v-if="post.thumbnail" class="mb-8">
      <img
        :src="post.thumbnail"
        alt="Thumbnail"
        class="w-full h-auto rounded-lg shadow-md"
      />
    </div>

    <!-- 본문 콘텐츠 -->
    <article
      v-html="post.content"
      class="prose prose-lg prose-gray max-w-none mx-auto leading-8 text-gray-800"
    ></article>
  </div>
</template>

<script setup lang="ts">
defineProps({
  post: {
    type: Object,
    required: true,
  },
});
</script>

<style scoped>
/* Tistory/Velog 스타일을 강조하기 위한 추가적인 커스텀 */
</style>
