<template>
  <div class="container mx-auto px-4 py-12 sm:px-8">
    <!-- 검색 및 필터 섹션 -->
    <div class="mb-8">
      <div class="flex flex-col md:flex-row gap-4">
        <div v-if="isLoggedIn" class="md:w-auto">
          <router-link :to="createPath" class="block w-full md:w-auto text-center rounded bg-purple-600 dark:bg-purple-700 px-4 py-2 font-bold text-white hover:bg-purple-700 dark:hover:bg-purple-800 transition-colors duration-300">
            Create Post
          </router-link>
        </div>
      </div>
    </div>

    <!-- 게시물 목록 -->
    <section class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
      <NuxtLink v-for="item in paginatedItems" :key="item.id" :to="`${basePath}/${item.id}`"
        class="relative block overflow-hidden rounded bg-white dark:bg-gray-900 shadow hover:shadow-md transition-all duration-300 transform hover:-translate-y-1">
        
        <!-- 썸네일 이미지 -->
        <img :src="item.image" alt="Post Image" class="h-48 w-full object-cover" />

        <!-- 드롭다운 버튼 (썸네일 우측 상단) -->
        <div class="absolute top-2 right-2 z-10">
          <button 
            @click.stop.prevent="toggleMenu(item.id)" 
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
          <div v-if="openMenuId === item.id"
            class="absolute right-0 mt-2 w-32 bg-white dark:bg-gray-700 border border-gray-200 dark:border-gray-600 rounded-lg shadow-lg pointer-events-auto transition-colors duration-300">
            <button 
              @click.stop.prevent="handleEditPost(item.id)" 
              class="block w-full px-4 py-2 text-left text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-600 transition-colors duration-300"
              type="button"
            >
              편집
            </button>
            <button 
              @click.stop.prevent="handleDeletePost(item.id)" 
              class="block w-full px-4 py-2 text-left text-red-600 dark:text-red-400 hover:bg-gray-100 dark:hover:bg-gray-600 transition-colors duration-300"
              type="button"
            >
              삭제
            </button>
          </div>
        </div>

        <!-- 카테고리 및 태그 -->
        <div class="p-4">
          <div class="mb-2 flex flex-wrap gap-2">
            <span v-if="item.category" class="px-3 py-1 text-xs font-semibold bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-300 rounded transition-colors duration-300">
              {{ item.category.name }}
            </span>
            <span v-for="tag in item.tags" :key="tag.id" class="px-3 py-1 text-xs font-semibold bg-purple-500 dark:bg-purple-600 text-white rounded transition-colors duration-300">
              #{{ tag.name }}
            </span>
          </div>

          <h2 class="mb-2 text-xl font-bold text-gray-900 dark:text-white transition-colors duration-300">{{ item.title }}</h2>
          <p class="line-clamp-3 text-gray-700 dark:text-gray-300 transition-colors duration-300">{{ extractTextFromHTML(item.description) }}</p>
        </div>
      </NuxtLink>
    </section>

    <!-- 검색 결과가 없을 때 -->
    <div v-if="filteredItems.length === 0 && searchQuery" class="text-center py-12">
      <p class="text-gray-600 dark:text-gray-400 text-lg transition-colors duration-300">
        "{{ searchQuery }}"에 대한 검색 결과가 없습니다.
      </p>
    </div>

    <!-- 페이지네이션 -->
    <Pagination 
      v-if="totalPages > 1" 
      :current-page="currentPage" 
      :total-pages="totalPages" 
      @page-change="handlePageChange" 
    />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue';
import { useAuthStore } from '~/stores/auth';
import { useRouter } from 'vue-router';
import Pagination from './pagination.vue';

// Pinia 스토어에서 로그인 여부 확인
const authStore = useAuthStore();
const isLoggedIn = authStore.isLoggedIn;

// props에서 받아온 게시물 리스트 및 mode 추가
const props = defineProps<{
  mode: 'blog' | 'project';
  items: Array<{ 
    id: number; 
    title: string; 
    description: string; 
    image: string; 
    category?: { id: number; name: string }; 
    tags?: Array<{ id: number; name: string }> 
  }>;
}>();

// basePath 및 createPath를 동적으로 설정
const basePath = `/${props.mode}`;
const createPath = `/${props.mode}/post`;

// 드롭다운 상태
const openMenuId = ref<number | null>(null);

// 검색 및 페이징 상태
const searchQuery = ref('');
const currentPage = ref(1);
const itemsPerPage = 6; // 한 페이지에 표시할 항목 수

// 검색어에 따른 필터링된 아이템
const filteredItems = computed(() => {
  if (!searchQuery.value) return props.items;
  
  const query = searchQuery.value.toLowerCase();
  return props.items.filter(item => 
    item.title.toLowerCase().includes(query) || 
    extractTextFromHTML(item.description).toLowerCase().includes(query)
  );
});

// 총 페이지 수 계산
const totalPages = computed(() => {
  return Math.ceil(filteredItems.value.length / itemsPerPage);
});

// 현재 페이지에 표시할 아이템
const paginatedItems = computed(() => {
  const startIndex = (currentPage.value - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  return filteredItems.value.slice(startIndex, endIndex);
});

// 검색어가 변경되면 페이지를 1로 리셋
watch(searchQuery, () => {
  currentPage.value = 1;
});

// 검색 처리 (디바운스 적용)
let searchTimeout: NodeJS.Timeout | null = null;
const handleSearch = () => {
  if (searchTimeout) clearTimeout(searchTimeout);
  searchTimeout = setTimeout(() => {
    // 실제 검색 로직은 computed에서 처리됨
    // 여기서는 디바운스만 적용
  }, 300);
};

// 페이지 변경 처리
const handlePageChange = (page: number) => {
  currentPage.value = page;
  // 페이지 상단으로 스크롤
  window.scrollTo({ top: 0, behavior: 'smooth' });
};

// 드롭다운 토글
const toggleMenu = (id: number) => {
  openMenuId.value = openMenuId.value === id ? null : id;
};

// 편집 기능
const router = useRouter();
const handleEditPost = (id: number) => {
  if (!isLoggedIn) {
    alert("권한이 없습니다.");
    return;
  }

  router.push(`/${props.mode}/${id}/edit`);
};

const emit = defineEmits(['postDeleted']);

// 삭제 기능
const handleDeletePost = async (id: number) => {
  if (!isLoggedIn) {
    alert("권한이 없습니다.");
    return;
  }

  if (!confirm("정말로 이 게시물을 삭제하시겠습니까?")) return;

  try {
    await $fetch(`/api/v1/${props.mode}/${id}`, {
      method: 'DELETE',
      baseURL: useRuntimeConfig().public.apiBaseUrl,
    });

    // 삭제 성공 후 이벤트 발생 (부모에서 반영)
    emit('postDeleted', id);
    
    alert("게시물이 삭제되었습니다.");
  } catch (error: any) {
    console.error("삭제 실패:", error);
    alert(`삭제 실패: ${error.message || "알 수 없는 오류가 발생했습니다."}`);
  }
};

// HTML 태그 제거
const extractTextFromHTML = (html: string) => {
  return html.replace(/<[^>]*>/g, '').replace(/&nbsp;/g, ' ').trim();
};
</script>