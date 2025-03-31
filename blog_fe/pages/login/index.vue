<template>
  <div class="bg-gray-50 dark:bg-gray-800 min-h-screen flex items-center justify-center transition-colors duration-300">
    <div class="bg-white dark:bg-gray-900 shadow-md rounded-lg p-8 w-full max-w-md transition-colors duration-300">
      <h1 class="text-2xl font-bold text-gray-900 dark:text-white mb-6 text-center transition-colors duration-300">로그인</h1>
      <form @submit.prevent="handleLogin">
        <div class="mb-4">
          <label for="name" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2 transition-colors duration-300">이름</label>
          <input type="name" id="name" v-model="name"
            class="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-purple-500 bg-white dark:bg-gray-800 text-gray-900 dark:text-white transition-colors duration-300"
            placeholder="홍길동" required />
        </div>
        <div class="mb-6">
          <label for="password" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2 transition-colors duration-300">비밀번호</label>
          <input type="password" id="password" v-model="password"
            class="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-purple-500 bg-white dark:bg-gray-800 text-gray-900 dark:text-white transition-colors duration-300"
            placeholder="비밀번호 입력" required />
        </div>
        <button type="submit"
          class="w-full py-2 px-4 bg-purple-600 dark:bg-purple-700 text-white font-medium rounded-md hover:bg-purple-500 dark:hover:bg-purple-600 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2 transition-colors duration-300">
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
  let response: any = null;

  try {
    console.log("🔄 로그인 요청 시작");

    const apiUrl = useRuntimeConfig().public.apiBaseUrl + '/login';
    console.log("📡 API 요청 URL:", apiUrl);

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
      credentials: 'include',
      onResponse: (context) => {
        response = context.response as Response;
      }
    });

    console.log("✅ API 요청 완료");

    // 응답이 존재하는지 확인
    if (!response) {
      console.error("응답 객체가 존재하지 않음!");
      return;
    }

    console.log("서버 응답 상태 코드:", response.status);
    console.log("서버 응답 헤더:", response.headers);
    console.log("서버 응답 데이터:", data.value);

    // Authorization 헤더 확인
    const authorizationHeader = response.headers.get('Authorization');
    if (!authorizationHeader) {
      console.error("Authorization 헤더가 존재하지 않음");
    } else {
      console.log("Authorization 헤더:", authorizationHeader);
    }

    if (authorizationHeader && authorizationHeader.startsWith('Bearer ')) {
      const token = authorizationHeader.split(' ')[1];
      console.log("로그인 성공! 토큰 수신:", token);
      authStore.login(token); // Pinia 상태 업데이트
    } else {
      console.error("로그인 성공했지만 토큰을 찾을 수 없습니다.");
    }

    // 이전 경로로 리다이렉트
    const redirectPath = localStorage.getItem('redirectPath') || '/';
    localStorage.removeItem('redirectPath');
    console.log("리다이렉트 경로:", redirectPath);
    router.push(redirectPath);
  } catch (error) {
    console.error("로그인 요청 중 오류 발생:", error);
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