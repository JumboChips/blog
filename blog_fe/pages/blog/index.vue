<template>
  <div class="bg-gray-50 dark:bg-gray-800 min-h-screen transition-colors duration-300">
    <div class="container mx-auto px-4 py-8">
      <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-8 text-center transition-colors duration-300">Blog</h1>
      
      <!-- 검색 및 필터 UI (서버 사이드 검색을 위한 UI) -->
      <div class="mb-8 max-w-2xl mx-auto">
        <form @submit.prevent="handleServerSearch" class="flex gap-2">
          <div class="relative flex-1">
            <input
              v-model="serverSearchQuery"
              type="text"
              placeholder="서버 검색 (제목, 내용)"
              class="w-full px-4 py-2 pl-10 border border-gray-300 dark:border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500 dark:bg-gray-800 dark:text-white transition-colors duration-300"
            />
            <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
              <svg class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
              </svg>
            </div>
          </div>
          <button type="submit" class="px-4 py-2 bg-purple-600 dark:bg-purple-700 text-white rounded-lg hover:bg-purple-700 dark:hover:bg-purple-800 transition-colors duration-300">
            검색
          </button>
        </form>
      </div>
      
      <PostList mode="blog" :items="mappedBlogPosts" @postDeleted="removePost" />
      
      <!-- 로딩 상태 표시 -->
      <div v-if="isLoading" class="flex justify-center items-center py-12">
        <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-purple-500"></div>
      </div>
      
      <!-- 데이터가 없을 때 표시 -->
      <div v-if="!isLoading && mappedBlogPosts.length === 0" class="text-center py-12">
        <p class="text-gray-600 dark:text-gray-400 text-lg transition-colors duration-300">
          {{ serverSearchQuery ? `"${serverSearchQuery}"에 대한 검색 결과가 없습니다.` : '아직 작성된 블로그 포스트가 없습니다.' }}
        </p>
      </div>
      
      <!-- 서버 사이드 페이징 -->
      <div v-if="totalPages > 1" class="mt-8">
        <Pagination 
          :current-page="currentPage" 
          :total-pages="totalPages" 
          @page-change="handlePageChange" 
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, watch } from 'vue';
import PostList from '@/components/postList.vue';
import Pagination from '@/components/pagination.vue';

useHead({
  title: 'Blog',
});

// BlogResponse 타입 정의
interface BlogResponseDto {
  blogId: number;
  title: string;
  content: string;
  thumbnail: string;
}

// 페이징 및 검색 상태
const currentPage = ref(1);
const itemsPerPage = 9;
const totalItems = ref(0);
const serverSearchQuery = ref('');

// 상태 변수
const blogPosts = ref<BlogResponseDto[]>([]);
const isLoading = ref(true);

const mappedBlogPosts = computed(() =>
  blogPosts.value.map(post => ({
    id: post.blogId,
    title: post.title,
    description: post.content,
    image: post.thumbnail
  }))
);

// 총 페이지 수 계산
const totalPages = computed(() => {
  return Math.ceil(totalItems.value / itemsPerPage);
});

// 서버에서 블로그 포스트 가져오기 (페이징 및 검색 지원)
const fetchBlogPosts = async () => {
  isLoading.value = true;
  try {
    // 백엔드가 구현되면 아래 주석을 해제하고 실제 API 호출로 대체
    /*
    const response = await $fetch('/api/v1/blog', {
      baseURL: useRuntimeConfig().public.apiBaseUrl,
      method: 'GET',
      params: {
        page: currentPage.value,
        size: itemsPerPage,
        search: serverSearchQuery.value || undefined
      }
    });
    
    blogPosts.value = response.content;
    totalItems.value = response.totalElements;
    */
    
    // 백엔드 구현 전 임시 코드
    const response = await $fetch<BlogResponseDto[]>('/api/v1/blog', {
      baseURL: useRuntimeConfig().public.apiBaseUrl,
      method: 'GET'
    });
    
    // 검색어가 있으면 클라이언트에서 필터링 (백엔드 구현 전)
    let filteredPosts = response;
    if (serverSearchQuery.value) {
      const query = serverSearchQuery.value.toLowerCase();
      filteredPosts = response.filter(post => 
        post.title.toLowerCase().includes(query) || 
        post.content.toLowerCase().includes(query)
      );
    }
    
    // 페이징 처리 (백엔드 구현 전)
    totalItems.value = filteredPosts.length;
    const startIndex = (currentPage.value - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    blogPosts.value = filteredPosts.slice(startIndex, endIndex);
    
    console.log('블로그 불러오기 성공:', response);
  } catch (error) {
    console.error('블로그 불러오기 실패:', error);
  } finally {
    isLoading.value = false;
  }
};

// 서버 검색 처리
const handleServerSearch = () => {
  currentPage.value = 1; // 검색 시 첫 페이지로 이동
  fetchBlogPosts();
};

// 페이지 변경 처리
const handlePageChange = (page: number) => {
  currentPage.value = page;
  fetchBlogPosts();
  // 페이지 상단으로 스크롤
  window.scrollTo({ top: 0, behavior: 'smooth' });
};

// 삭제된 게시물 목록에서 제거
const removePost = (id: number) => {
  blogPosts.value = blogPosts.value.filter(post => post.blogId !== id);
  // 게시물이 삭제되면 총 아이템 수도 감소
  totalItems.value--;
  
  // 현재 페이지에 표시할 항목이 없고, 이전 페이지가 있으면 이전 페이지로 이동
  if (blogPosts.value.length === 0 && currentPage.value > 1) {
    currentPage.value--;
    fetchBlogPosts();
  }
};

// URL 쿼리 파라미터 처리
const route = useRoute();
watch(() => route.query, (newQuery) => {
  const page = Number(newQuery.page) || 1;
  const search = String(newQuery.search || '');
  
  if (page !== currentPage.value) {
    currentPage.value = page;
  }
  
  if (search !== serverSearchQuery.value) {
    serverSearchQuery.value = search;
  }
  
  fetchBlogPosts();
}, { immediate: true });

// 페이지나 검색어가 변경되면 URL 업데이트
watch([currentPage, serverSearchQuery], ([page, search]) => {
  const router = useRouter();
  const query: Record<string, string> = {};
  
  if (page > 1) {
    query.page = String(page);
  }
  
  if (search) {
    query.search = search;
  }
  
  router.replace({ query });
});

onMounted(() => {
  // URL에 쿼리 파라미터가 없으면 초기 데이터 로드
  if (!route.query.page && !route.query.search) {
    fetchBlogPosts();
  }
});
</script>