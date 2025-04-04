<template>
  <div :class="{'dark': isDarkMode}" class="min-h-screen transition-colors duration-400">
    <div class="flex flex-col min-h-screen bg-white dark:bg-gray-900">
      <!-- Header 영역 -->
      <Header :toggleDarkMode="toggleDarkMode" :isDarkMode="isDarkMode" />

      <!-- 메인 콘텐츠 영역 -->
      <main class="flex-1 bg-gray-50 dark:bg-gray-800 transition-colors duration-400">
        <NuxtPage />
      </main>

      <!-- Footer 영역 -->
      <Footer />

      <!-- 실시간 채팅 컴포넌트 -->
      <FloatingChat :isDarkMode="isDarkMode" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch } from 'vue';
import Header from '~/components/header.vue';
import Footer from '~/components/footer.vue';

// 다크 모드 상태 관리
const isDarkMode = ref(false);

// 다크 모드 토글 함수 - 타입 명시
const toggleDarkMode = (event?: MouseEvent): void => {
  isDarkMode.value = !isDarkMode.value;
  // 로컬 스토리지에 사용자 선택 저장
  localStorage.setItem('darkMode', isDarkMode.value ? 'dark' : 'light');
  
  // HTML 요소에 dark 클래스 적용/제거
  updateDarkModeClass();
};

// HTML 요소에 dark 클래스 적용/제거하는 함수
const updateDarkModeClass = (): void => {
  if (isDarkMode.value) {
    document.documentElement.classList.add('dark');
    document.body.classList.add('dark');
  } else {
    document.documentElement.classList.remove('dark');
    document.body.classList.remove('dark');
  }
};

// 시스템 테마 변경 감지 핸들러
const handleSystemThemeChange = (e: MediaQueryListEvent): void => {
  // 사용자가 명시적으로 테마를 선택하지 않은 경우에만 시스템 테마 따름
  if (!localStorage.getItem('darkMode')) {
    isDarkMode.value = e.matches;
    updateDarkModeClass();
  }
};

// isDarkMode 값이 변경될 때마다 클래스 업데이트
watch(isDarkMode, () => {
  updateDarkModeClass();
});

// 컴포넌트 마운트 시 초기화
onMounted(() => {
  // 로컬 스토리지에서 사용자 선택 확인
  const savedTheme = localStorage.getItem('darkMode');
  
  if (savedTheme) {
    // 사용자가 이전에 테마를 선택한 경우
    isDarkMode.value = savedTheme === 'dark';
  } else {
    // 시스템 테마 감지
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    isDarkMode.value = prefersDark;
  }
  
  // 초기 다크 모드 클래스 적용
  updateDarkModeClass();
  
  // 시스템 테마 변경 감지 이벤트 리스너 등록
  const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
  mediaQuery.addEventListener('change', handleSystemThemeChange);
  
  // 컴포넌트 언마운트 시 이벤트 리스너 제거
  onUnmounted(() => {
    mediaQuery.removeEventListener('change', handleSystemThemeChange);
  });
});
</script>

<style>
/* 다크 모드 기본 스타일 */
.dark {
  color-scheme: dark;
}

/* 전역 스타일 */
:root {
  --primary: #7c3aed;
  --primary-hover: #6d28d9;
  --primary-light: #ddd6fe;
  --secondary: #3b82f6;
  --secondary-hover: #2563eb;
  --secondary-light: #dbeafe;
  --text-primary: #1f2937;
  --text-secondary: #4b5563;
  --text-tertiary: #9ca3af;
  --bg-primary: #ffffff;
  --bg-secondary: #f9fafb;
  --bg-tertiary: #f3f4f6;
  --border-color: #e5e7eb;
}

.dark {
  --primary: #8b5cf6;
  --primary-hover: #7c3aed;
  --primary-light: #4c1d95;
  --secondary: #60a5fa;
  --secondary-hover: #3b82f6;
  --secondary-light: #1e40af;
  --text-primary: #f9fafb;
  --text-secondary: #e5e7eb;
  --text-tertiary: #9ca3af;
  --bg-primary: #111827;
  --bg-secondary: #1f2937;
  --bg-tertiary: #374151;
  --border-color: #374151;
}

body {
  font-family: 'Inter', sans-serif;
  @apply antialiased;
}

/* 스크롤바 스타일 */
::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.dark ::-webkit-scrollbar-track {
  background: #1f2937;
}

::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 5px;
}

.dark ::-webkit-scrollbar-thumb {
  background: #4b5563;
}

::-webkit-scrollbar-thumb:hover {
  background: #555;
}

.dark ::-webkit-scrollbar-thumb:hover {
  background: #6b7280;
}
</style>