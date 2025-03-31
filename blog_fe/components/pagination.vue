<template>
  <div class="flex items-center justify-center space-x-1 mt-8">
    <button
      @click="onPageChange(currentPage - 1)"
      :disabled="currentPage === 1"
      class="px-3 py-1 rounded-md bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-300 disabled:opacity-50 disabled:cursor-not-allowed transition-colors duration-300"
      aria-label="Previous page"
    >
      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
        <path fill-rule="evenodd" d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clip-rule="evenodd" />
      </svg>
    </button>
    
    <template v-for="page in displayedPages" :key="page">
      <button
        v-if="page !== '...'"
        @click="onPageChange(page as number)"
        :class="[
          'px-3 py-1 rounded-md transition-colors duration-300',
          currentPage === page 
            ? 'bg-purple-600 dark:bg-purple-700 text-white' 
            : 'bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-300 dark:hover:bg-gray-600'
        ]"
      >
        {{ page }}
      </button>
      <span v-else class="px-2 text-gray-500 dark:text-gray-400">...</span>
    </template>
    
    <button
      @click="onPageChange(currentPage + 1)"
      :disabled="currentPage === totalPages"
      class="px-3 py-1 rounded-md bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-300 disabled:opacity-50 disabled:cursor-not-allowed transition-colors duration-300"
      aria-label="Next page"
    >
      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
        <path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd" />
      </svg>
    </button>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';

const props = defineProps<{
  currentPage: number;
  totalPages: number;
}>();

const emit = defineEmits(['page-change']);

const onPageChange = (page: number) => {
  if (page < 1 || page > props.totalPages) return;
  emit('page-change', page);
};

// 표시할 페이지 번호 계산 (1, 2, 3, ..., 10 또는 1, ..., 4, 5, 6, ..., 10)
const displayedPages = computed(() => {
  const pages = [];
  const maxVisiblePages = 5; // 최대 표시할 페이지 수
  
  if (props.totalPages <= maxVisiblePages) {
    // 전체 페이지가 적으면 모두 표시
    for (let i = 1; i <= props.totalPages; i++) {
      pages.push(i);
    }
  } else {
    // 현재 페이지 주변과 처음/끝 페이지 표시
    const leftSide = Math.floor(maxVisiblePages / 2);
    const rightSide = maxVisiblePages - leftSide - 1;
    
    // 현재 페이지가 왼쪽에 가까울 때
    if (props.currentPage <= leftSide + 1) {
      for (let i = 1; i <= maxVisiblePages - 1; i++) {
        pages.push(i);
      }
      pages.push('...');
      pages.push(props.totalPages);
    } 
    // 현재 페이지가 오른쪽에 가까울 때
    else if (props.currentPage >= props.totalPages - rightSide) {
      pages.push(1);
      pages.push('...');
      for (let i = props.totalPages - maxVisiblePages + 2; i <= props.totalPages; i++) {
        pages.push(i);
      }
    } 
    // 현재 페이지가 중간에 있을 때
    else {
      pages.push(1);
      pages.push('...');
      for (let i = props.currentPage - 1; i <= props.currentPage + 1; i++) {
        pages.push(i);
      }
      pages.push('...');
      pages.push(props.totalPages);
    }
  }
  
  return pages;
});
</script>