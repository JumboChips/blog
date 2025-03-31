<template>
  <footer class="bg-white dark:bg-gray-900 border-t border-gray-200 dark:border-gray-800 transition-colors duration-300">
    <div class="container mx-auto px-6 py-8">
      <!-- 푸터 상단 영역 -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mb-8">
        <!-- 브랜드 정보 -->
        <div>
          <div class="flex items-center mb-4">
            <div class="w-8 h-8 rounded-lg bg-gradient-to-r from-purple-600 to-blue-600 flex items-center justify-center text-white font-bold mr-2 shadow-sm">
              JC
            </div>
            <h3 class="text-lg font-bold bg-clip-text text-transparent bg-gradient-to-r from-purple-600 to-blue-600">
              JumboChips
            </h3>
          </div>
          <p class="text-gray-600 dark:text-gray-400 text-sm mb-4">
            안정적이고 확장 가능한 서버 아키텍처를 설계하고 구현하는 백엔드 개발자입니다.
          </p>
          <div class="flex space-x-4">
            <a href="https://github.com/jumbochips" target="_blank" class="text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-300 transition-colors">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M15 22v-4a4.8 4.8 0 0 0-1-3.5c3 0 6-2 6-5.5.08-1.25-.27-2.48-1-3.5.28-1.15.28-2.35 0-3.5 0 0-1 0-3 1.5-2.64-.5-5.36-.5-8 0C6 2 5 2 5 2c-.3 1.15-.3 2.35 0 3.5A5.403 5.403 0 0 0 4 9c0 3.5 3 5.5 6 5.5-.39.49-.68 1.05-.85 1.65-.17.6-.22 1.23-.15 1.85v4"></path><path d="M9 18c-4.51 2-5-2-7-2"></path></svg>
            </a>
            <a href="https://linkedin.com/in/jumbochips" target="_blank" class="text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-300 transition-colors">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 8a6 6 0 0 1 6 6v7h-4v-7a2 2 0 0 0-2-2 2 2 0 0 0-2 2v7h-4v-7a6 6 0 0 1 6-6z"></path><rect width="4" height="12" x="2" y="9"></rect><circle cx="4" cy="4" r="2"></circle></svg>
            </a>
            <a href="mailto:jumbochips1@gmail.com" class="text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-300 transition-colors">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect width="20" height="16" x="2" y="4" rx="2"></rect><path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"></path></svg>
            </a>
          </div>
        </div>
        
        <!-- 빠른 링크 -->
        <div>
          <h3 class="text-gray-900 dark:text-white font-semibold mb-4">빠른 링크</h3>
          <ul class="space-y-2 text-gray-600 dark:text-gray-400">
            <li>
              <NuxtLink to="/" class="hover:text-purple-600 dark:hover:text-purple-400 transition-colors">홈</NuxtLink>
            </li>
            <li>
              <NuxtLink to="/project" class="hover:text-purple-600 dark:hover:text-purple-400 transition-colors">프로젝트</NuxtLink>
            </li>
            <li>
              <NuxtLink to="/blog" class="hover:text-purple-600 dark:hover:text-purple-400 transition-colors">블로그</NuxtLink>
            </li>
          </ul>
        </div>
        
        <!-- 연락처 -->
        <div>
          <h3 class="text-gray-900 dark:text-white font-semibold mb-4">연락처</h3>
          <ul class="space-y-2 text-gray-600 dark:text-gray-400">
            <li class="flex items-center">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="mr-2"><rect width="20" height="16" x="2" y="4" rx="2"></rect><path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"></path></svg>
              <a href="mailto:jumbochips1@gmail.com" class="hover:text-purple-600 dark:hover:text-purple-400 transition-colors">jumbochips1@gmail.com</a>
            </li>
          </ul>
        </div>
      </div>
      
      <!-- 푸터 하단 영역 -->
      <div class="pt-8 border-t border-gray-200 dark:border-gray-800 flex flex-col md:flex-row justify-between items-center">
        <p class="text-gray-600 dark:text-gray-400 text-sm mb-4 md:mb-0">© 2024 JumboChips. All Rights Reserved.</p>
        
        <ul class="flex space-x-4 text-gray-600 dark:text-gray-400 text-sm">
          <!-- 로그인 여부에 따라 동적 표시 -->
          <li v-if="isLoggedIn">
            <button @click="handleLogout" class="hover:text-purple-600 dark:hover:text-purple-400 transition-colors">로그아웃</button>
          </li>
          <li v-else>
            <NuxtLink to="/login" class="hover:text-purple-600 dark:hover:text-purple-400 transition-colors">로그인</NuxtLink>
          </li>
        </ul>
      </div>
    </div>
  </footer>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '~/stores/auth';

const isLoggedIn = ref(false);
const router = useRouter();
const authStore = useAuthStore();

// 로그아웃 처리 함수
const handleLogout = () => {
  authStore.logout(); // pinia 상태 초기화 및 토큰 삭제
  router.push('/login'); // 로그인 페이지로 리다이렉트
};

// 컴포넌트가 마운트될 때 로그인 상태 확인 (클라이언트 환경에서만 실행됨)
onMounted(() => {
  const token = localStorage.getItem('token');
  isLoggedIn.value = !!token; // 토큰이 있으면 true, 없으면 false
});
</script>