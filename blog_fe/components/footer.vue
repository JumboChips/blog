<template>
  <footer class="bg-white border-t">
    <div class="container mx-auto px-4 sm:px-8 py-6 flex flex-wrap justify-between items-center">
      <p class="text-gray-600 text-sm">© 2024 JumboChips. All Rights Reserved.</p>
      <ul class="flex space-x-4 text-gray-600">
        <!-- 로그인 여부에 따라 동적 표시 -->
        <li v-if="isLoggedIn">
          <button @click="handleLogout" class="hover:text-gray-900 transition">Logout</button>
        </li>
        <li v-else>
          <NuxtLink :to="`/login`" class="hover:text-gray-900 transition">Login</NuxtLink>
        </li>
      </ul>
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

