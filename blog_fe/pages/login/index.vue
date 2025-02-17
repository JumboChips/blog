<template>
  <div class="bg-gray-50 min-h-screen flex items-center justify-center">
    <div class="bg-white shadow-md rounded-lg p-8 w-full max-w-md">
      <h1 class="text-2xl font-bold text-gray-900 mb-6 text-center">로그인</h1>
      <form @submit.prevent="handleLogin">
        <div class="mb-4">
          <label for="name" class="block text-sm font-medium text-gray-700 mb-2">이름</label>
          <input type="name" id="name" v-model="name"
            class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="홍길동" required />
        </div>
        <div class="mb-6">
          <label for="password" class="block text-sm font-medium text-gray-700 mb-2">비밀번호</label>
          <input type="password" id="password" v-model="password"
            class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="비밀번호 입력" required />
        </div>
        <button type="submit"
          class="w-full py-2 px-4 bg-blue-600 text-white font-medium rounded-md hover:bg-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2">
          로그인
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '~/stores/auth';

// 상태 변수
const name = ref<string>('');
const password = ref<string>('');
const router = useRouter();
const authStore = useAuthStore();

// 로그인 처리 함수
const handleLogin = async (): Promise<void> => {
  try {
    const { data, error } = await useFetch('/login', {
      baseURL: useRuntimeConfig().public.apiBaseUrl,
      method: 'POST',
      body: new URLSearchParams({
        username: name.value,
        password: password.value
      }),
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      credentials: 'include'
    });

    if (error.value) {
      console.error('로그인 실패:', error.value);
      return;
    }

    let token: string | null = null;

    // JSON 응답에서 토큰 추출
    if (data.value && data.value.token) {
      token = data.value.token;
    }

    if (token) {
      console.log('로그인 성공: 토큰 수신 =', token);
      authStore.login(token); // Pinia 상태 업데이트
    } else {
      console.error('로그인 성공했지만 토큰을 찾을 수 없습니다.');
    }

    // 이전 경로로 리다이렉트
    const redirectPath = localStorage.getItem('redirectPath') || '/';
    localStorage.removeItem('redirectPath');
    router.push(redirectPath);
  } catch (error) {
    console.error('로그인 처리 중 오류 발생:', error);
  }
};



// 컴포넌트가 마운트될 때 실행 (localStorage 접근)
onMounted(() => {
  const redirectPath = localStorage.getItem('redirectPath');
  if (redirectPath) {
    console.log('이전 경로가 저장되어 있습니다:', redirectPath);
  }
});
</script>
