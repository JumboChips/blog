<template>
  <div>
    <!-- 배경 썸네일 섹션 -->
    <div v-if="post.thumbnail" class="hero-section relative" :style="{ backgroundImage: `url(${post.thumbnail})` }">
      <!-- 배경 오버레이 -->
      <div class="overlay"></div>

      <!-- 드롭다운 메뉴 (우측 상단) -->
      <div v-if="authStore.isLoggedIn" class="absolute top-4 right-4">
        <div class="relative">
          <button 
            @click.stop.prevent="toggleMenu" 
            class="p-2 bg-white dark:bg-gray-700 rounded-full shadow-lg transition-colors duration-300"
            type="button"
          >
            <svg class="h-6 w-6 text-gray-700 dark:text-gray-300 transition-colors duration-300" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M13 5C13 4.44772 12.5523 4 12 4C11.4477 4 11 4.44772 11 5C11 5.55228 11.4477 6 12 6C12.5523 6 13 5.55228 13 5Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
              <path d="M13 12C13 11.4477 12.5523 11 12 11C11.4477 11 11 11.4477 11 12C11 12.5523 11.4477 13 12 13C12.5523 13 13 12.5523 13 12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
              <path d="M13 19C13 18.4477 12.5523 18 12 18C11.4477 18 11 18.4477 11 19C11 19.5523 11.4477 20 12 20C12.5523 20 13 19.5523 13 19Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
            </svg>
          </button>

          <!-- 드롭다운 메뉴 -->
          <div v-if="showMenu"
            class="absolute right-0 mt-2 w-32 bg-white dark:bg-gray-700 border border-gray-200 dark:border-gray-600 rounded-lg shadow-lg pointer-events-auto transition-colors duration-300">
            <button 
              @click="editPost" 
              class="block w-full px-4 py-2 text-left text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-600 transition-colors duration-300"
              type="button"
            >
              편집
            </button>
            <button 
              @click="deletePost" 
              class="block w-full px-4 py-2 text-left text-red-600 dark:text-red-400 hover:bg-gray-100 dark:hover:bg-gray-600 transition-colors duration-300"
              type="button"
            >
              삭제
            </button>
          </div>
        </div>
      </div>

      <!-- 제목 및 작성일 -->
      <div class="text-container">
        <h1 class="title">{{ post.title }}</h1>
        <div class="meta-info">
          <span class="date">📅 {{ formatDate(post.createdAt) }}</span>
          <span v-if="post.updatedAt !== post.createdAt" class="date">📝 수정일: {{ formatDate(post.updatedAt) }}</span>
        </div>
        <!-- 카테고리 및 태그 -->
        <div class="mt-2 flex justify-center items-center flex-wrap gap-2">
          <span v-if="post.category" class="px-3 py-1 text-sm font-semibold bg-gray-100 dark:bg-gray-700 text-gray-800 dark:text-gray-300 rounded transition-colors duration-300">
            {{ post.category.name }}
          </span>
          <span v-for="tag in post.tags" :key="tag.id" class="px-3 py-1 text-sm font-semibold bg-purple-500 dark:bg-purple-600 text-white rounded transition-colors duration-300">
            #{{ tag.name }}
          </span>
        </div>
      </div>
    </div>

    <!-- 본문 콘텐츠 -->
    <article v-html="post.content" class="prose-content prose prose-lg prose-gray dark:prose-invert max-w-4xl mx-auto p-6 text-gray-800 dark:text-gray-200 bg-white dark:bg-gray-900 transition-colors duration-300"></article>
    
    <!-- 댓글 섹션 -->
    <Comments 
      :postId="postId" 
      :postType="mode" 
    />
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useRoute } from 'vue-router';
import { useAuthStore } from '~/stores/auth';
import { useRuntimeConfig } from '#imports';
import Comments from './comments.vue';

const props = defineProps<{
  post: {
    blogId?: number;
    projectId?: number;
    thumbnail: string;
    title: string;
    content: string;
    category?: { id: number; name: string };
    tags?: Array<{ id: number; name: string }>;
    createdAt?: string;
    updatedAt?: string;
  };
  mode: 'blog' | 'project';
}>();

const formatDate = (dateString: string | undefined) => {
  if (!dateString) return "날짜 없음";
  return new Date(dateString).toLocaleDateString("ko-KR", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
};

const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();
const config = useRuntimeConfig();
const showMenu = ref(false);

// postId를 props에서 가져오거나, props에 없으면 route.params에서 가져오기
const postId = computed(() => {
  // props에서 ID 값을 먼저 확인
  if (props.post?.blogId) return props.post.blogId;
  if (props.post?.projectId) return props.post.projectId;
  
  // route.params에서 ID 값 확인 (배열일 경우 첫 번째 요소 사용)
  const routeId = props.mode === 'blog' 
    ? route.params.blogId 
    : route.params.projectId;
    
  // 배열인 경우 첫 번째 요소 반환, 그렇지 않으면 값 그대로 반환
  return Array.isArray(routeId) ? routeId[0] : routeId;
});

// 드롭다운 메뉴 토글
const toggleMenu = () => {
  showMenu.value = !showMenu.value;
};

// 편집 버튼 클릭 → 편집 페이지로 이동
const editPost = () => {
  if (!authStore.isLoggedIn) {
    alert("권한이 없습니다.");
    return;
  }

  if (!postId.value) {
    alert("게시글 ID가 존재하지 않습니다.");
    console.error("게시글 ID가 존재하지 않아 편집 페이지로 이동할 수 없습니다.");
    return;
  }

  router.push(`/${props.mode}/${postId.value}/edit`);
};

// 삭제 요청
const deletePost = async () => {
  if (!authStore.isLoggedIn) {
    alert("권한이 없습니다.");
    return;
  }

  if (!confirm("정말 삭제하시겠습니까?")) return;

  try {
    const postId = props.mode === 'blog' ? props.post.blogId : props.post.projectId;
    const apiUrl = `${config.public.apiBaseUrl}/api/v1/${props.mode}/${postId}`;

    await $fetch(apiUrl, {
      method: 'DELETE',
      headers: { Authorization: `Bearer ${authStore.token}` },
    });

    alert("삭제되었습니다.");
    router.push(`/${props.mode}`);
  } catch (error) {
    console.error("삭제 중 오류 발생:", error);
    alert("삭제 중 오류가 발생했습니다.");
  }
};
</script>

<style scoped>
/* Hero 배경 섹션 */
.hero-section {
  position: relative;
  width: 100%;
  height: 300px;
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
  width: 90%; /* 너비 제한 추가 */
  max-width: 800px; /* 최대 너비 설정 */
  padding: 0 15px; /* 좌우 패딩 추가 */
}

/* 제목 스타일 */
.title {
  font-size: 2.5rem;
  font-weight: bold;
  margin-bottom: 10px;
  text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.3);
  word-wrap: break-word; /* 긴 단어 줄바꿈 */
  overflow-wrap: break-word; /* 긴 단어 줄바꿈 (최신 브라우저) */
  hyphens: auto; /* 필요시 하이픈 추가 */
}

/* 카테고리 및 날짜 */
.meta-info {
  display: flex;
  gap: 15px;
  justify-content: center;
  font-size: 0.9rem;
  opacity: 0.9;
  flex-wrap: wrap; /* 모바일에서 줄바꿈 허용 */
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .title {
    font-size: 1.8rem; /* 모바일에서 폰트 크기 축소 */
  }
  
  .text-container {
    bottom: 30px; /* 모바일에서 위치 조정 */
  }
  
  .meta-info {
    font-size: 0.8rem; /* 모바일에서 메타 정보 폰트 크기 축소 */
  }
}

/* 더 작은 화면에 대한 추가 조정 */
@media (max-width: 480px) {
  .title {
    font-size: 1.5rem; /* 더 작은 화면에서 폰트 크기 추가 축소 */
  }
  
  .hero-section {
    height: 250px; /* 더 작은 화면에서 높이 축소 */
  }
}

/* 다크 모드에서 prose 스타일 조정 */
:deep(.dark .prose-content) {
  --tw-prose-body: theme('colors.gray.300');
  --tw-prose-headings: theme('colors.white');
  --tw-prose-lead: theme('colors.gray.300');
  --tw-prose-links: theme('colors.purple.400');
  --tw-prose-bold: theme('colors.white');
  --tw-prose-counters: theme('colors.gray.400');
  --tw-prose-bullets: theme('colors.gray.400');
  --tw-prose-hr: theme('colors.gray.700');
  --tw-prose-quotes: theme('colors.gray.300');
  --tw-prose-quote-borders: theme('colors.gray.700');
  --tw-prose-captions: theme('colors.gray.400');
  --tw-prose-code: theme('colors.white');
  --tw-prose-pre-code: theme('colors.gray.300');
  --tw-prose-pre-bg: theme('colors.gray.800');
  --tw-prose-th-borders: theme('colors.gray.700');
  --tw-prose-td-borders: theme('colors.gray.700');
}
</style>