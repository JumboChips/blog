<template>
  <div>
    <div class="container mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <section class="flex flex-col lg:flex-row items-center lg:items-start gap-8 lg:gap-12">
        <div class="w-full lg:w-1/3 flex justify-center">
          <div
            class="aspect-square bg-gray-200 rounded-full h-64 w-64 sm:h-80 sm:w-80 lg:h-96 lg:w-96 flex items-center justify-center">
            <img v-if="imageUrl" :src="imageUrl" alt="프로필 이미지" class="rounded-full h-full w-full object-cover" />
            <span v-else class="text-gray-400 text-lg sm:text-xl">이미지</span>
          </div>
        </div>
        <div class="w-full lg:w-2/3">
          <h1 class="text-2xl sm:text-3xl lg:text-4xl font-bold text-gray-800 text-center lg:text-left">안녕하세요, 저는
            JumboChips입니다</h1>
          <h2 class="mt-6 lg:mt-8 text-xl sm:text-2xl font-bold text-gray-900 text-center lg:text-left">나의 미션</h2>
          <p class="mt-4 text-gray-700 text-sm sm:text-base text-center lg:text-left">기술과 디자인의 조화를 통해 사용자에게 의미 있는 경험을
            제공하는 것이 저의 목표입니다. 지속적인 학습과 혁신을 통해 더 나은 웹 환경을 만들어 나가고 있습니다.</p>

          <h3 class="mt-6 lg:mt-8 text-xl sm:text-2xl font-semibold text-gray-900 text-center lg:text-left">주요 역할</h3>
          <ul class="mt-4 space-y-2 text-gray-700 text-sm sm:text-base">
            <li class="flex items-start">
              <span class="mr-2">-</span>
              <span>모던 JavaScript와 React를 활용한 동적 웹 애플리케이션 개발</span>
            </li>
            <li class="flex items-start">
              <span class="mr-2">-</span>
              <span>반응형 디자인과 크로스 브라우저 호환성 구현</span>
            </li>
            <li class="flex items-start">
              <span class="mr-2">-</span>
              <span>성능 최적화 및 웹 접근성 향상</span>
            </li>
            <li class="flex items-start">
              <span class="mr-2">-</span>
              <span>UI/UX 디자인 원칙을 고려한 인터페이스 설계</span>
            </li>
          </ul>

          <div class="flex flex-wrap justify-center lg:justify-start gap-2 mt-6">
            <span class="px-3 py-1 text-xs sm:text-sm font-semibold text-gray-700 bg-gray-200 rounded">혁신적</span>
            <span class="px-3 py-1 text-xs sm:text-sm font-semibold text-gray-700 bg-gray-200 rounded">사용자 중심</span>
            <span class="px-3 py-1 text-xs sm:text-sm font-semibold text-gray-700 bg-gray-200 rounded">지속적 학습</span>
            <span class="px-3 py-1 text-xs sm:text-sm font-semibold text-gray-700 bg-gray-200 rounded">팀 플레이어</span>
            <span class="px-3 py-1 text-xs sm:text-sm font-semibold text-gray-700 bg-gray-200 rounded">문제 해결사</span>
            <span class="px-3 py-1 text-xs sm:text-sm font-semibold text-gray-700 bg-gray-200 rounded">디자인 지향</span>
          </div>
        </div>
      </section>

      <section class="mt-12 text-center">
        <button
          class="w-full sm:w-auto px-6 py-3 text-white bg-black rounded hover:bg-gray-800 transition duration-300">
          <NuxtLink to="/aboutme">About me</NuxtLink>
        </button>
        <button
          class="w-full sm:w-auto mt-4 sm:mt-0 sm:ml-4 px-6 py-3 text-black border border-black rounded hover:bg-gray-100 transition duration-300">
          <NuxtLink to="/project">프로젝트 보기</NuxtLink>
        </button>
        <button
          class="w-full sm:w-auto mt-4 sm:mt-0 sm:ml-4 px-6 py-3 text-black border border-black rounded hover:bg-gray-100 transition duration-300">
          <NuxtLink to="/blog">블로그 보기</NuxtLink>
        </button>
      </section>
    </div>
  </div>
</template>
<script setup lang="ts">
defineProps({
  imageUrl: {
    types: String,
    required: false,
    default: ''
  }
});
</script>