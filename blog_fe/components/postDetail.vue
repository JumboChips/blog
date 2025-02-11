<template>
  <div>
    <!-- 배경 썸네일 섹션 -->
    <div v-if="post.thumbnail" class="hero-section relative" :style="{ backgroundImage: `url(${post.thumbnail})` }">
      <!-- 배경 오버레이 -->
      <div class="overlay"></div>

      <!-- 제목 및 작성일 -->
      <div class="text-container">
        <h1 class="title">{{ post.title }}</h1>
        <div class="meta-info">
          <span class="category">📌 {{ post.categoryId }}</span>
          <span class="date">📅 {{ new Date().toLocaleDateString() }}</span>
        </div>
      </div>
    </div>

    <!-- 본문 콘텐츠 -->
    <article v-html="post.content" class="prose prose-lg prose-gray max-w-4xl mx-auto p-6 text-gray-800"></article>
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
/* Hero 배경 섹션 */
.hero-section {
  position: relative;
  width: 100%;
  height: 300px;
  /* 배경 높이 조절 */
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

/* 배경 위에 어두운 오버레이 */
.overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to bottom, rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.2));
}

/* 제목 및 작성일 */
.text-container {
  position: absolute;
  bottom: 40px;
  left: 50%;
  transform: translateX(-50%);
  text-align: center;
  color: white;
}

/* 제목 스타일 */
.title {
  font-size: 2.5rem;
  font-weight: bold;
  margin-bottom: 10px;
  text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.3);
}

/* 카테고리 및 날짜 */
.meta-info {
  display: flex;
  gap: 15px;
  justify-content: center;
  font-size: 0.9rem;
  opacity: 0.9;
}
</style>
