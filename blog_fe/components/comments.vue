<template>
  <div class="comments-section max-w-4xl mx-auto p-6 bg-white dark:bg-gray-900 rounded-lg shadow-sm transition-colors duration-300">
    <h3 class="text-2xl font-bold mb-6 text-gray-800 dark:text-white transition-colors duration-300">댓글 {{ comments.length > 0 ? `(${comments.length})` : '' }}</h3>
    
    <!-- 댓글 목록 -->
    <div v-if="comments.length > 0" class="space-y-6 mb-8">
      <div v-for="comment in comments" :key="comment.id" class="comment-item border-b border-gray-200 dark:border-gray-700 pb-4 transition-colors duration-300">
        <div class="flex justify-between items-start">
          <div class="flex items-center">
            <div class="w-10 h-10 rounded-full bg-purple-100 dark:bg-purple-900 flex items-center justify-center text-purple-600 dark:text-purple-300 font-bold text-lg transition-colors duration-300">
              {{ comment.username.charAt(0).toUpperCase() }}
            </div>
            <div class="ml-3">
              <h4 class="font-semibold text-gray-800 dark:text-white transition-colors duration-300">{{ comment.username }}</h4>
              <p class="text-sm text-gray-500 dark:text-gray-400 transition-colors duration-300">{{ formatDate(comment.createdAt) }}</p>
            </div>
          </div>
          
          <!-- 수정/삭제 버튼 (댓글 작성자만 볼 수 있음) -->
          <div v-if="!comment.isEditing" class="flex space-x-2">
            <button 
              @click="startEditing(comment)" 
              class="text-sm text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-300 transition-colors duration-300"
              type="button"
            >
              수정
            </button>
            <button 
              @click="deleteComment(comment.id)" 
              class="text-sm text-red-500 hover:text-red-700 dark:text-red-400 dark:hover:text-red-300 transition-colors duration-300"
              type="button"
            >
              삭제
            </button>
          </div>
        </div>
        
        <!-- 댓글 내용 (일반 모드) -->
        <div v-if="!comment.isEditing" class="mt-2 text-gray-700 dark:text-gray-300 whitespace-pre-wrap transition-colors duration-300">
          {{ comment.content }}
        </div>
        
        <!-- 댓글 수정 폼 -->
        <div v-else class="mt-2">
          <textarea 
            v-model="comment.editContent" 
            class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-2 focus:ring-purple-500 transition-colors duration-300"
            rows="3"
            placeholder="댓글을 입력하세요"
          ></textarea>
          
          <div class="mt-2 flex space-x-2">
            <input 
              type="password" 
              v-model="comment.editPassword" 
              class="flex-grow p-2 border border-gray-300 dark:border-gray-600 rounded-md bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-2 focus:ring-purple-500 transition-colors duration-300"
              placeholder="비밀번호 확인"
            />
            <button 
              @click="updateComment(comment)" 
              class="px-4 py-2 bg-purple-600 hover:bg-purple-700 text-white rounded-md transition-colors duration-300"
              type="button"
            >
              수정
            </button>
            <button 
              @click="cancelEditing(comment)" 
              class="px-4 py-2 bg-gray-300 dark:bg-gray-700 hover:bg-gray-400 dark:hover:bg-gray-600 text-gray-700 dark:text-gray-300 rounded-md transition-colors duration-300"
              type="button"
            >
              취소
            </button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 댓글이 없을 때 -->
    <div v-else class="text-center py-8 text-gray-500 dark:text-gray-400 transition-colors duration-300">
      첫 번째 댓글을 남겨보세요!
    </div>
    
    <!-- 댓글 작성 폼 -->
    <div class="comment-form mt-8 border-t border-gray-200 dark:border-gray-700 pt-6 transition-colors duration-300">
      <h4 class="text-xl font-semibold mb-4 text-gray-800 dark:text-white transition-colors duration-300">댓글 작성</h4>
      
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1 transition-colors duration-300">이름</label>
          <input 
            type="text" 
            id="username" 
            v-model="newComment.username" 
            class="w-full p-2 border border-gray-300 dark:border-gray-600 rounded-md bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-2 focus:ring-purple-500 transition-colors duration-300"
            placeholder="이름을 입력하세요"
          />
        </div>
        <div>
          <label for="password" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1 transition-colors duration-300">비밀번호</label>
          <input 
            type="password" 
            id="password" 
            v-model="newComment.password" 
            class="w-full p-2 border border-gray-300 dark:border-gray-600 rounded-md bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-2 focus:ring-purple-500 transition-colors duration-300"
            placeholder="비밀번호를 입력하세요"
          />
        </div>
      </div>
      
      <div class="mb-4">
        <label for="content" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1 transition-colors duration-300">내용</label>
        <textarea 
          id="content" 
          v-model="newComment.content" 
          class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-2 focus:ring-purple-500 transition-colors duration-300"
          rows="4"
          placeholder="댓글을 입력하세요"
        ></textarea>
      </div>
      
      <!-- reCAPTCHA -->
      <div class="mb-4">
        <div ref="recaptchaContainer" class="g-recaptcha"></div>
        <div v-if="recaptchaError" class="text-red-500 text-sm mt-1">{{ recaptchaError }}</div>
      </div>
      
      <button 
        @click="submitComment" 
        class="px-6 py-3 bg-purple-600 hover:bg-purple-700 text-white font-semibold rounded-md transition-colors duration-300 disabled:opacity-50 disabled:cursor-not-allowed"
        :disabled="isSubmitting"
        type="button"
      >
        {{ isSubmitting ? '등록 중...' : '댓글 등록' }}
      </button>
    </div>
    
    <!-- 비밀번호 확인 모달 -->
    <div v-if="showPasswordModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg max-w-md w-full transition-colors duration-300">
        <h3 class="text-xl font-bold mb-4 text-gray-800 dark:text-white transition-colors duration-300">비밀번호 확인</h3>
        <p class="mb-4 text-gray-700 dark:text-gray-300 transition-colors duration-300">댓글을 삭제하려면 비밀번호를 입력하세요.</p>
        
        <input 
          type="password" 
          v-model="passwordInput" 
          class="w-full p-2 border border-gray-300 dark:border-gray-600 rounded-md bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-2 focus:ring-purple-500 mb-4 transition-colors duration-300"
          placeholder="비밀번호"
        />
        
        <div class="flex justify-end space-x-2">
          <button 
            @click="closePasswordModal" 
            class="px-4 py-2 bg-gray-300 dark:bg-gray-700 hover:bg-gray-400 dark:hover:bg-gray-600 text-gray-700 dark:text-gray-300 rounded-md transition-colors duration-300"
            type="button"
          >
            취소
          </button>
          <button 
            @click="confirmDeleteComment" 
            class="px-4 py-2 bg-red-600 hover:bg-red-700 text-white rounded-md transition-colors duration-300"
            type="button"
          >
            삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { useRuntimeConfig } from '#imports';

const props = defineProps<{
  postId: number | string;
  postType: 'blog' | 'project';
}>();

// 댓글 상태
const comments = ref<any[]>([]);
const isSubmitting = ref(false);
const newComment = ref({
  username: '',
  password: '',
  content: ''
});

// 비밀번호 모달
const showPasswordModal = ref(false);
const passwordInput = ref('');
const currentCommentId = ref<number | null>(null);

// reCAPTCHA 관련
const recaptchaContainer = ref<HTMLElement | null>(null);
const recaptchaError = ref('');
const recaptchaLoaded = ref(false);

// 날짜 포맷
const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  });
};

// reCAPTCHA 로드
const loadRecaptcha = () => {
  if (typeof window !== 'undefined' && !window.grecaptcha) {
    const script = document.createElement('script');
    script.src = `https://www.google.com/recaptcha/api.js?render=explicit`;
    script.async = true;
    script.defer = true;
    script.onload = renderRecaptcha;
    document.head.appendChild(script);
  } else if (window.grecaptcha) {
    renderRecaptcha();
  }
};

// reCAPTCHA 렌더링
const renderRecaptcha = () => {
  if (recaptchaContainer.value && window.grecaptcha && !recaptchaLoaded.value) {
    const config = useRuntimeConfig();
    window.grecaptcha.render(recaptchaContainer.value, {
      sitekey: config.public.recaptchaSiteKey,
      theme: document.documentElement.classList.contains('dark') ? 'dark' : 'light'
    });
    recaptchaLoaded.value = true;
  }
};

// 다크모드 변경 감지 및 reCAPTCHA 테마 업데이트
watch(() => document.documentElement.classList.contains('dark'), (isDark) => {
  if (recaptchaLoaded.value && window.grecaptcha) {
    // reCAPTCHA를 다시 렌더링하여 테마 업데이트
    recaptchaLoaded.value = false;
    window.grecaptcha.reset();
    renderRecaptcha();
  }
});

// 댓글 불러오기
const fetchComments = async () => {
  try {
    const config = useRuntimeConfig();
    const response = await $fetch<any[]>(`${config.public.apiBaseUrl}/api/v1/comments/${props.postType}/${props.postId}`);
    comments.value = response.map(comment => ({
      id: comment.commentId,
      username: comment.username,
      content: comment.content,
      createdAt: comment.createdAt,
      isEditing: false,
      editContent: comment.content,
      editPassword: ''
    }));
  } catch (err) {
    console.error('댓글을 불러오는 중 오류 발생:', err);
  }
};

// 댓글 등록
const submitComment = async () => {
  if (!newComment.value.username.trim()) {
    alert('이름을 입력해주세요.');
    return;
  }

  if (!newComment.value.password.trim()) {
    alert('비밀번호를 입력해주세요.');
    return;
  }

  if (!newComment.value.content.trim()) {
    alert('댓글 내용을 입력해주세요.');
    return;
  }

  // reCAPTCHA 토큰 확인
  const token = window.grecaptcha?.getResponse();
  if (!token) {
    recaptchaError.value = 'reCAPTCHA 인증을 완료해주세요.';
    return;
  }
  recaptchaError.value = '';

  isSubmitting.value = true;
  try {
    const config = useRuntimeConfig();
    await $fetch(`${config.public.apiBaseUrl}/api/v1/comments/${props.postType}/${props.postId}`, {
      method: 'POST',
      body: {
        username: newComment.value.username,
        password: newComment.value.password,
        content: newComment.value.content,
        recaptchaToken: token
      }
    });

    await fetchComments();
    newComment.value = { username: '', password: '', content: '' };
    window.grecaptcha?.reset(); // reCAPTCHA 리셋
  } catch (error) {
    console.error('댓글 등록 중 오류 발생:', error);
    alert('댓글 등록에 실패했습니다.');
  } finally {
    isSubmitting.value = false;
  }
};

// 댓글 수정
const startEditing = (comment: any) => {
  comment.isEditing = true;
  comment.editContent = comment.content;
  comment.editPassword = '';
};

const cancelEditing = (comment: any) => {
  comment.isEditing = false;
  comment.editPassword = '';
};

const updateComment = async (comment: any) => {
  if (!comment.editContent.trim() || !comment.editPassword.trim()) {
    alert('수정 내용과 비밀번호를 입력해주세요.');
    return;
  }

  try {
    const config = useRuntimeConfig();
    await $fetch(`${config.public.apiBaseUrl}/api/v1/comments/${comment.id}`, {
      method: 'PUT',
      body: {
        content: comment.editContent,
        password: comment.editPassword
      }
    });

    await fetchComments();
  } catch (err) {
    console.error('댓글 수정 중 오류:', err);
    alert('수정 실패: 비밀번호 불일치 또는 권한 없음');
  }
};

// 댓글 삭제
const deleteComment = (id: number) => {
  currentCommentId.value = id;
  passwordInput.value = '';
  showPasswordModal.value = true;
};

const closePasswordModal = () => {
  showPasswordModal.value = false;
  currentCommentId.value = null;
};

const confirmDeleteComment = async () => {
  if (!passwordInput.value.trim()) {
    alert('비밀번호를 입력해주세요.');
    return;
  }

  try {
    const config = useRuntimeConfig();
    await $fetch(`${config.public.apiBaseUrl}/api/v1/comments/${currentCommentId.value}`, {
      method: 'DELETE',
        headers: {
          'x-password': passwordInput.value
        }

    });

    await fetchComments();
    closePasswordModal();
  } catch (err) {
    console.error('댓글 삭제 실패:', err);
    alert('비밀번호가 일치하지 않거나 삭제 권한이 없습니다.');
  }
};

// 전역 타입 선언
declare global {
  interface Window {
    grecaptcha: {
      render: (container: HTMLElement, options: any) => number;
      reset: (widgetId?: number) => void;
      getResponse: (widgetId?: number) => string;
    };
  }
}

onMounted(() => {
  fetchComments();
  loadRecaptcha();
});
</script>

<style scoped>
/* 댓글 섹션 스타일 */
.comments-section {
  margin-top: 2rem;
  border-top: 1px solid #e5e7eb;
}

/* reCAPTCHA 컨테이너 스타일 */
.g-recaptcha {
  margin-bottom: 1rem;
  display: inline-block;
}

/* 다크 모드에서 reCAPTCHA 테마 조정 */
:deep(.dark .g-recaptcha) {
  filter: invert(0.85) hue-rotate(180deg);
}
</style>