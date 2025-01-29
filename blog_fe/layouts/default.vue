<template>
  <div class="flex flex-col min-h-screen">
    <!-- Header 영역 -->
    <Header />

    <!-- 메인 콘텐츠 영역 -->
    <main class="flex-1 bg-gray-50">
      <NuxtPage />
    </main>

    <!-- Footer 영역 -->
    <Footer />
  </div>
</template>

<script setup lang="ts">
import Header from '/components/header';
import Footer from '/components/footer';
</script>