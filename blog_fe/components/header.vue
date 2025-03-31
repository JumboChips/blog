<template>
  <header class="bg-white dark:bg-gray-900 shadow-md dark:shadow-gray-800 transition-colors duration-300">
    <div class="container mx-auto flex items-center justify-between py-4 px-6">
      <!-- 로고 또는 제목 -->
      <NuxtLink to="/" class="flex items-center group">
        <div class="w-10 h-10 rounded-lg bg-gradient-to-r from-purple-600 to-blue-600 flex items-center justify-center text-white font-bold mr-2 shadow-md transform group-hover:scale-105 transition-transform duration-300">
          JC
        </div>
        <h1 class="text-xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-purple-600 to-blue-600 transition-colors duration-300">
          JumboChips
        </h1>
      </NuxtLink>

      <!-- 내비게이션 메뉴 (모바일에서는 햄버거 메뉴) -->
      <div class="md:hidden">
        <button @click="toggleMobileMenu" class="text-gray-700 dark:text-gray-300 hover:text-purple-600 dark:hover:text-purple-400 transition-colors">
          <svg v-if="!isMobileMenuOpen" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="4" x2="20" y1="12" y2="12"/><line x1="4" x2="20" y1="6" y2="6"/><line x1="4" x2="20" y1="18" y2="18"/></svg>
          <svg v-else xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M18 6 6 18"/><path d="m6 6 12 12"/></svg>
        </button>
      </div>

      <!-- 데스크톱 메뉴 -->
      <nav class="hidden md:block">
        <ul class="flex items-center space-x-8 text-gray-700 dark:text-gray-300">
          <li>
            <NuxtLink to="/project" class="hover:text-purple-600 dark:hover:text-purple-400 transition-colors relative group">
              Project
              <span class="absolute -bottom-1 left-0 w-0 h-0.5 bg-purple-600 dark:bg-purple-400 transition-all duration-300 group-hover:w-full"></span>
            </NuxtLink>
          </li>
          <li>
            <NuxtLink to="/blog" class="hover:text-purple-600 dark:hover:text-purple-400 transition-colors relative group">
              Blog
              <span class="absolute -bottom-1 left-0 w-0 h-0.5 bg-purple-600 dark:bg-purple-400 transition-all duration-300 group-hover:w-full"></span>
            </NuxtLink>
          </li>
          <li>
            <button 
              @click="handleDarkModeToggle" 
              class="p-2 rounded-full hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
              type="button"
            >
              <svg v-if="isDarkMode" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-yellow-400"><circle cx="12" cy="12" r="4"/><path d="M12 2v2"/><path d="M12 20v2"/><path d="m4.93 4.93 1.41 1.41"/><path d="m17.66 17.66 1.41 1.41"/><path d="M2 12h2"/><path d="M20 12h2"/><path d="m6.34 17.66-1.41 1.41"/><path d="m19.07 4.93-1.41 1.41"/></svg>
              <svg v-else xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-gray-700"><path d="M12 3a6.364 6.364 0 0 0 9 9 9 9 0 1 1-9-9Z"/></svg>
            </button>
          </li>
        </ul>
      </nav>
    </div>

    <!-- 모바일 메뉴 -->
    <div v-if="isMobileMenuOpen" class="md:hidden bg-white dark:bg-gray-900 shadow-md transition-all duration-300">
      <nav class="container mx-auto px-6 py-4">
        <ul class="space-y-4 text-gray-700 dark:text-gray-300">
          <li>
            <NuxtLink to="/project" class="block py-2 hover:text-purple-600 dark:hover:text-purple-400 transition-colors" @click="isMobileMenuOpen = false">
              Project
            </NuxtLink>
          </li>
          <li>
            <NuxtLink to="/blog" class="block py-2 hover:text-purple-600 dark:hover:text-purple-400 transition-colors" @click="isMobileMenuOpen = false">
              Blog
            </NuxtLink>
          </li>
          <li class="flex items-center justify-between py-2">
            <span>다크 모드</span>
            <button 
              @click="handleDarkModeToggle" 
              class="p-2 rounded-full hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
              type="button"
            >
              <svg v-if="isDarkMode" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-yellow-400"><circle cx="12" cy="12" r="4"/><path d="M12 2v2"/><path d="M12 20v2"/><path d="m4.93 4.93 1.41 1.41"/><path d="m17.66 17.66 1.41 1.41"/><path d="M2 12h2"/><path d="M20 12h2"/><path d="m6.34 17.66-1.41 1.41"/><path d="m19.07 4.93-1.41 1.41"/></svg>
              <svg v-else xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-gray-700"><path d="M12 3a6.364 6.364 0 0 0 9 9 9 9 0 1 1-9-9Z"/></svg>
            </button>
          </li>
        </ul>
      </nav>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref } from 'vue';

// 모바일 메뉴 상태
const isMobileMenuOpen = ref(false);

// 모바일 메뉴 토글 함수
const toggleMobileMenu = () => {
  isMobileMenuOpen.value = !isMobileMenuOpen.value;
};

// 다크 모드 관련 props
const props = defineProps<{
  toggleDarkMode: (event: MouseEvent) => void;
  isDarkMode: boolean;
}>();

// 다크 모드 토글 핸들러 - 타입 안전하게 수정
const handleDarkModeToggle = (event: MouseEvent) => {
  props.toggleDarkMode(event);
};
</script>