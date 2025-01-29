import { defineStore } from 'pinia';
import { ref, onMounted } from 'vue';

export const useAuthStore = defineStore('auth', () => {
  const isLoggedIn = ref(false);
  const token = ref<string | null>(null); // token 상태

  // 컴포넌트가 마운트될 때 localStorage 확인 (클라이언트 환경에서만 실행)
  onMounted(() => {
    const storedToken = localStorage.getItem('token');
    if (storedToken) {
      token.value = storedToken;
      isLoggedIn.value = true;
    }
  });

  const login = (newToken: string) => {
    localStorage.setItem('token', newToken);
    token.value = newToken;
    isLoggedIn.value = true;
  };

  const logout = () => {
    localStorage.removeItem('token');
    token.value = null; // token 상태 초기화
    isLoggedIn.value = false;
  };

  return { isLoggedIn, token, login, logout };
});
