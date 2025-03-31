<template>
  <div class="w-full max-w-5xl mx-auto p-6 bg-white dark:bg-gray-900 transition-colors duration-300">
    <h1 class="text-3xl font-extrabold mb-6 text-gray-800 dark:text-white transition-colors duration-300">
      {{ mode === 'blog' ? '블로그 글 작성' : '프로젝트 글 작성' }}
    </h1>

    <!-- 제목 입력 -->
    <div class="mb-6">
      <label for="title" class="block font-medium mb-1 text-gray-700 dark:text-gray-300 transition-colors duration-300">제목</label>
      <input
        id="title"
        v-model="title"
        class="border px-4 py-2 w-full rounded focus:outline-none focus:ring-0 focus:border-purple-300 bg-white dark:bg-gray-800 text-gray-900 dark:text-white border-gray-300 dark:border-gray-600 transition-colors duration-300"
        placeholder="글 제목을 입력하세요"
      />
    </div>

    <!-- 카테고리 선택 -->
    <div class="mb-6">
      <label for="category" class="block font-medium mb-1 text-gray-700 dark:text-gray-300 transition-colors duration-300">카테고리</label>
      <select
        id="category"
        v-model="categoryId"
        class="border px-4 py-2 w-full rounded focus:outline-none focus:ring-0 focus:border-purple-300 bg-white dark:bg-gray-800 text-gray-900 dark:text-white border-gray-300 dark:border-gray-600 transition-colors duration-300"
      >
        <option value="1">Default</option>
      </select>
    </div>

    <!-- 태그 선택 -->
    <div class="mb-6">
      <label for="tags" class="block font-medium mb-1 text-gray-700 dark:text-gray-300 transition-colors duration-300">태그</label>
      <div class="flex flex-wrap gap-2">
        <button
          v-for="tag in availableTags"
          :key="tag.id"
          @click="toggleTag(tag.id)"
          :class="{
            'bg-purple-500 text-white': tagIds.includes(tag.id),
            'bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-300': !tagIds.includes(tag.id),
          }"
          class="text-sm py-2 px-4 rounded focus:outline-none transition-all"
          type="button"
        >
          {{ tag.name }}
        </button>
      </div>
    </div>

    <!-- 툴바 -->
    <div class="flex flex-wrap gap-3 mb-4 p-2 bg-gray-50 dark:bg-gray-800 rounded border border-gray-200 dark:border-gray-700 transition-colors duration-300">
      <!-- 제목 스타일 -->
      <div class="relative">
        <button
          @click="toggleHeadingMenu"
          class="text-sm py-2 px-4 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded focus:outline-none focus:ring-0 transition-all flex items-center gap-1 text-gray-700 dark:text-gray-300"
          type="button"
        >
          제목 <span class="text-xs">▼</span>
        </button>
        <div
          v-if="showHeadingMenu"
          class="absolute top-full left-0 mt-1 bg-white dark:bg-gray-800 shadow-lg rounded z-10 w-40 border border-gray-200 dark:border-gray-700 transition-colors duration-300"
        >
          <button
            @click="setHeading(1)"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-700 text-2xl font-bold text-gray-900 dark:text-white transition-colors duration-300"
            type="button"
          >
            제목 1
          </button>
          <button
            @click="setHeading(2)"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-700 text-xl font-bold text-gray-900 dark:text-white transition-colors duration-300"
            type="button"
          >
            제목 2
          </button>
          <button
            @click="setHeading(3)"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-700 text-lg font-bold text-gray-900 dark:text-white transition-colors duration-300"
            type="button"
          >
            제목 3
          </button>
          <button
            @click="setHeading(0)"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-700 text-gray-900 dark:text-white transition-colors duration-300"
            type="button"
          >
            일반 텍스트
          </button>
        </div>
      </div>

      <!-- 글자 크기 -->
      <div class="relative">
        <button
          @click="toggleSizeMenu"
          class="text-sm py-2 px-4 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded focus:outline-none focus:ring-0 transition-all flex items-center gap-1 text-gray-700 dark:text-gray-300"
          type="button"
        >
          글자 크기 <span class="text-xs">▼</span>
        </button>
        <div
          v-if="showSizeMenu"
          class="absolute top-full left-0 mt-1 bg-white dark:bg-gray-800 shadow-lg rounded z-10 w-40 border border-gray-200 dark:border-gray-700 transition-colors duration-300"
        >
          <button
            @click="setFontSize('small')"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-700 text-sm text-gray-900 dark:text-white transition-colors duration-300"
            type="button"
          >
            작게
          </button>
          <button
            @click="setFontSize('normal')"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-700 text-gray-900 dark:text-white transition-colors duration-300"
            type="button"
          >
            보통
          </button>
          <button
            @click="setFontSize('large')"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-700 text-lg text-gray-900 dark:text-white transition-colors duration-300"
            type="button"
          >
            크게
          </button>
          <button
            @click="setFontSize('x-large')"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-700 text-xl text-gray-900 dark:text-white transition-colors duration-300"
            type="button"
          >
            매우 크게
          </button>
        </div>
      </div>

      <!-- 리스트 -->
      <div class="relative">
        <button
          @click="toggleListMenu"
          class="text-sm py-2 px-4 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded focus:outline-none focus:ring-0 transition-all flex items-center gap-1 text-gray-700 dark:text-gray-300"
          type="button"
        >
          리스트 <span class="text-xs">▼</span>
        </button>
        <div
          v-if="showListMenu"
          class="absolute top-full left-0 mt-1 bg-white dark:bg-gray-800 shadow-lg rounded z-10 w-40 border border-gray-200 dark:border-gray-700 transition-colors duration-300"
        >
          <button
            @click="toggleBulletList"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-700 flex items-center text-gray-900 dark:text-white transition-colors duration-300"
            type="button"
          >
            <span class="mr-2">•</span> 불릿 리스트
          </button>
          <button
            @click="toggleOrderedList"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-700 flex items-center text-gray-900 dark:text-white transition-colors duration-300"
            type="button"
          >
            <span class="mr-2">1.</span> 순번 리스트
          </button>
          <button
            @click="toggleTaskList"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-700 flex items-center text-gray-900 dark:text-white transition-colors duration-300"
            type="button"
          >
            <span class="mr-2">☐</span> 태스크 리스트
          </button>
        </div>
      </div>

      <!-- 기본 서식 -->
      <button
        @click="toggleBold"
        class="text-sm py-2 px-4 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded focus:outline-none focus:ring-0 transition-all font-bold text-gray-700 dark:text-gray-300"
        type="button"
      >
        B
      </button>
      <button
        @click="toggleItalic"
        class="text-sm py-2 px-4 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded focus:outline-none focus:ring-0 transition-all italic text-gray-700 dark:text-gray-300"
        type="button"
      >
        I
      </button>
      <button
        @click="toggleUnderline"
        class="text-sm py-2 px-4 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded focus:outline-none focus:ring-0 transition-all underline text-gray-700 dark:text-gray-300"
        type="button"
      >
        U
      </button>
      <button
        @click="toggleHighlight"
        class="text-sm py-2 px-4 rounded focus:outline-none focus:ring-0 transition-all text-gray-700 dark:text-gray-300"
        :style="{ backgroundColor: isDarkMode ? '#4d3800' : '#ffff99' }"
        type="button"
      >
        형광펜
      </button>

      <!-- 텍스트 정렬 -->
      <button 
        @click="setTextAlign('left')"
        class="text-sm py-2 px-4 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded focus:outline-none focus:ring-0 transition-all text-gray-700 dark:text-gray-300"
        type="button"
      >
        ⟵
      </button>
      <button 
        @click="setTextAlign('center')"
        class="text-sm py-2 px-4 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded focus:outline-none focus:ring-0 transition-all text-gray-700 dark:text-gray-300"
        type="button"
      >
        ⟷
      </button>
      <button 
        @click="setTextAlign('right')"
        class="text-sm py-2 px-4 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded focus:outline-none focus:ring-0 transition-all text-gray-700 dark:text-gray-300"
        type="button"
      >
        ⟶
      </button>

      <!-- 코드 -->
      <button
        @click="toggleCodeBlock"
        class="text-sm py-2 px-4 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded focus:outline-none focus:ring-0 transition-all font-mono text-gray-700 dark:text-gray-300"
        type="button"
      >
        &lt;/&gt;
      </button>

      <!-- 이미지 업로드 -->
      <input
        type="file"
        class="hidden"
        ref="fileInput"
        @change="handleFileUpload"
        accept="image/*"
      />
      <button
        @click="triggerFileUpload"
        class="text-sm py-2 px-4 bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 rounded focus:outline-none focus:ring-0 transition-all text-gray-700 dark:text-gray-300"
        type="button"
      >
        이미지
      </button>
    </div>

    <!-- Tiptap Editor -->
    <div class="mb-6 border border-gray-300 dark:border-gray-700 rounded shadow-sm max-h-[400px] min-h-[400px] overflow-y-auto bg-white dark:bg-gray-800 transition-colors duration-300">
      <editor-content :editor="editor" class="w-full h-full text-base p-3" />
    </div>

    <!-- 등록 버튼 -->
    <button
      @click="submitPost"
      class="bg-purple-600 dark:bg-purple-700 hover:bg-purple-700 dark:hover:bg-purple-800 text-white px-6 py-3 rounded font-semibold shadow transition-all focus:outline-none focus:ring-0"
      type="button"
    >
      글 등록
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useRuntimeConfig } from '#app'
import { useAuthStore } from '~/stores/auth'
import { Editor, EditorContent } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import Underline from '@tiptap/extension-underline'
import Highlight from '@tiptap/extension-highlight'
import TextAlign from '@tiptap/extension-text-align'
import Link from '@tiptap/extension-link'
import Image from '@tiptap/extension-image'
import ResizeImage from 'tiptap-extension-resize-image';
import TaskList from '@tiptap/extension-task-list'
import TaskItem from '@tiptap/extension-task-item'
import TextStyle from '@tiptap/extension-text-style'
import HardBreak from '@tiptap/extension-hard-break'
import { Color } from '@tiptap/extension-color'

const props = defineProps<{
  mode: 'blog' | 'project'
}>()

// 다크 모드 감지
const isDarkMode = computed(() => {
  if (typeof document !== 'undefined') {
    return document.documentElement.classList.contains('dark');
  }
  return false;
});

// 폼 데이터
const title = ref('');
const thumbnail = ref('');
const categoryId = ref<number>(1);
const tagIds = ref<number[]>([]);
const availableTags = ref([{ id: 1, name: 'Default' }]);

// 메뉴 상태
const showHeadingMenu = ref(false);
const showSizeMenu = ref(false);
const showListMenu = ref(false);

// 파일 입력 참조
const fileInput = ref<HTMLInputElement | null>(null);

// 이미지 URL 배열
const imageUrls = ref<string[]>([]);

// 기본 글자 크기
const fontSize = ref(16);

// Tiptap 에디터 설정
const editor = new Editor({
  extensions: [
    StarterKit.configure({
      heading: {
        levels: [1, 2, 3],
      },
      hardBreak: false,
    }),
    HardBreak.extend({
      addKeyboardShortcuts() {
        return {
          Enter: ({editor}) => editor.commands.setHardBreak(),
        }
      }
    }),
    ResizeImage,
    TextStyle,
    Color,
    Underline,
    Highlight.configure({
      multicolor: true,
    }),
    TextAlign.configure({
      types: ['heading', 'paragraph'],
    }),
    Link.configure({
      openOnClick: true,
    }),
    Image.configure({
      inline: true,
      allowBase64: false,
    }),
    TaskList,
    TaskItem.configure({
      nested: true,
    }),
    TextAlign.configure({
    types: ['heading', 'paragraph'],
    alignments: ['left', 'center', 'right'],
    defaultAlignment: 'left',
    }),
  ],
  editorProps: {
  attributes: {
    class: 'prose prose-lg max-w-none focus:outline-none dark:prose-invert',
  },
  handleKeyDown: (view, event) => {
    if (event.key === 'Enter' && !event.shiftKey) {
      const { selection } = view.state
      const { empty, $head } = selection

      if (empty && $head.parent.type.name === 'listItem' && $head.parent.textContent === '') {
        // editor 인스턴스 직접 참조
        editor.commands.liftListItem('listItem')
        return true
      }
    }
    return false
  },
},
})

// 메뉴 토글 함수들
const toggleHeadingMenu = () => {
  showHeadingMenu.value = !showHeadingMenu.value
  showSizeMenu.value = false
  showListMenu.value = false
}

const toggleSizeMenu = () => {
  showSizeMenu.value = !showSizeMenu.value
  showHeadingMenu.value = false
  showListMenu.value = false
}

const toggleListMenu = () => {
  showListMenu.value = !showListMenu.value
  showHeadingMenu.value = false
  showSizeMenu.value = false
}

// 커스텀 글자 크기 설정 함수
const setCustomFontSize = () => {
  if (fontSize.value < 8) fontSize.value = 8
  if (fontSize.value > 72) fontSize.value = 72
  
  editor.commands.setMark('textStyle', {
    fontSize: `${fontSize.value}px`
  })
}

// 텍스트 정렬 함수
const setTextAlign = (align: 'left' | 'center' | 'right') => {
  editor.commands.setTextAlign(align)
}

// 서식 관련 함수들
const setHeading = (level: 0 | 1 | 2 | 3) => {
  if (level === 0) {
    editor.commands.setParagraph()
  } else {
    editor.commands.setHeading({ level })
  }
  showHeadingMenu.value = false
}

const setFontSize = (size: 'small' | 'normal' | 'large' | 'x-large') => {
  const sizes = {
    small: '0.875em',
    normal: '1em',
    large: '1.25em',
    'x-large': '1.5em',
  }
  
  editor.commands.setMark('textStyle', { fontSize: sizes[size] })
  showSizeMenu.value = false
}

const toggleBold = () => editor.commands.toggleBold()
const toggleItalic = () => editor.commands.toggleItalic()
const toggleUnderline = () => editor.commands.toggleUnderline()
const toggleHighlight = () => editor.commands.toggleHighlight()
const toggleCodeBlock = () => editor.commands.toggleCodeBlock()

const toggleBulletList = () => {
  if (editor.isActive('bulletList')) {
    editor.commands.liftListItem('listItem')
  } else {
    editor.commands.toggleBulletList()
  }
  showListMenu.value = false
}

const toggleOrderedList = () => {
  if (editor.isActive('orderedList')) {
    editor.commands.liftListItem('listItem')
  } else {
    editor.commands.toggleOrderedList()
  }
  showListMenu.value = false
}

const toggleTaskList = () => {
  editor.commands.toggleTaskList()
  showListMenu.value = false
}

// 이미지 업로드 관련
const triggerFileUpload = () => {
  fileInput.value?.click()
}

const handleFileUpload = async (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  
  if (file) {
    try {
      const imageUrl = await uploadImage(file)
      imageUrls.value.push(imageUrl)
      editor.commands.setImage({ src: imageUrl })
    } catch (error) {
      console.error('이미지 업로드 실패:', error)
      alert('이미지 업로드 중 오류가 발생했습니다.')
    }
  }
}

// 이미지 업로드 함수
const uploadImage = async (file: File): Promise<string> => {
  const config = useRuntimeConfig()
  const imageUrl = props.mode === 'blog'
    ? `${config.public.apiBaseUrl}/api/v1/upload/file/blog`
    : `${config.public.apiBaseUrl}/api/v1/upload/file/project`

  try {
    const formData = new FormData()
    formData.append('file', file)

    const authStore = useAuthStore()
    const token = authStore.token

    const response = await $fetch<string[]>(imageUrl, {
      method: 'POST',
      body: formData,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })

    return `${config.public.uploadsBaseUrl}/${response[0]}`
  } catch (error) {
    console.error('이미지 업로드 실패:', error)
    throw new Error('이미지 업로드 실패')
  }
}

// 태그 토글
const toggleTag = (tagId: number) => {
  if (tagIds.value.includes(tagId)) {
    tagIds.value = tagIds.value.filter(id => id !== tagId)
  } else {
    tagIds.value.push(tagId)
  }
}

// 글 등록
const submitPost = async () => {
  if (!title.value.trim()) {
    alert('제목을 입력하세요.')
    return
  }

  if (!editor.getText().trim()) {
    alert('내용을 입력하세요.')
    return
  }

  const content = editor.getHTML()
  const firstImage = content.match(/<img[^>]+src="([^">]+)"/)?.[1] || null

  if (!thumbnail.value && firstImage) {
    thumbnail.value = firstImage
  }

  const requestDto = {
    categoryId: categoryId.value,
    tagIds: tagIds.value,
    thumbnail: thumbnail.value,
    title: title.value,
    content,
    imageUrls: imageUrls.value,
  }

  const config = useRuntimeConfig()
  const url = props.mode === 'blog'
    ? `${config.public.apiBaseUrl}/api/v1/blog`
    : `${config.public.apiBaseUrl}/api/v1/project`

  try {
    const authStore = useAuthStore();
    const router = useRouter();
    const token = authStore.token;
    
    const response = await $fetch<{ blogId?: number; projectId?: number; message: string }>(url, {
      method: 'POST',
      body: requestDto,
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    })

    if (props.mode === 'blog' && response.blogId) {
      alert('등록되었습니다.')
      navigateTo(`/blog/${response.blogId}`)
    } else if (props.mode === 'project' && response.projectId) {
      alert('등록되었습니다.')
      navigateTo(`/project/${response.projectId}`)
    } else {
      throw new Error('Response에서 올바른 ID를 찾을 수 없습니다.')
    }
  } catch (error) {
    console.error('글 생성 실패:', error)
    alert('글 생성 중 오류가 발생했습니다.')
  }
}

// 클릭 이벤트 리스너
const closeAllMenus = (event: MouseEvent) => {
  const target = event.target as HTMLElement
  if (!target.closest('button')) {
    showHeadingMenu.value = false
    showSizeMenu.value = false
    showListMenu.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', closeAllMenus)
})

onBeforeUnmount(() => {
  document.removeEventListener('click', closeAllMenus)
})
</script>

<style>
/* 에디터 기본 스타일 */
.ProseMirror {
  min-height: 300px;
  padding: 1rem;
}

/* 개행 표시 스타일 */
.ProseMirror br {
  display: block;
  content: "";
  margin-bottom: 12px;
}

/* 제목 스타일 */
.ProseMirror h1 {
  font-size: 2.25rem !important;
  font-weight: 700 !important;
  margin: 1rem 0 !important;
  line-height: 1.2 !important;
}

.ProseMirror h2 {
  font-size: 1.875rem !important;
  font-weight: 600 !important;
  margin: 0.875rem 0 !important;
  line-height: 1.3 !important;
}

.ProseMirror h3 {
  font-size: 1.5rem !important;
  font-weight: 500 !important;
  margin: 0.75rem 0 !important;
  line-height: 1.4 !important;
}

/* 리스트 스타일 */
.ProseMirror ul {
  list-style-type: disc !important;
  padding-left: 1.5rem !important;
  margin: 0.5rem 0 !important;
}

.ProseMirror ol {
  list-style-type: decimal !important;
  padding-left: 1.5rem !important;
  margin: 0.5rem 0 !important;
}

.ProseMirror li {
  margin: 0.25rem 0 !important;
}

/* 글자 크기 스타일 */
.ProseMirror [style*="font-size: 0.875em"] {
  font-size: 0.875em !important;
}

.ProseMirror [style*="font-size: 1em"] {
  font-size: 1em !important;
}

.ProseMirror [style*="font-size: 1.25em"] {
  font-size: 1.25em !important;
}

.ProseMirror [style*="font-size: 1.5em"] {
  font-size: 1.5em !important;
}

/* 태스크 리스트 스타일 */
.ProseMirror ul[data-type="taskList"] {
  list-style: none !important;
  padding: 0 !important;
}

.ProseMirror ul[data-type="taskList"] li {
  display: flex;
  align-items: flex-start;
  margin: 0.5rem 0 !important;
}

.ProseMirror ul[data-type="taskList"] li > label {
  margin-right: 0.5rem;
}

/* 코드 블록 스타일 */
.ProseMirror pre {
  background-color: #f5f5f5;
  border-radius: 0.25rem;
  padding: 1rem;
  margin: 0.5rem 0;
  font-family: monospace;
}

/* 다크 모드 코드 블록 스타일 */
.dark .ProseMirror pre {
  background-color: #1e293b;
  color: #e2e8f0;
}

/* 이미지 스타일 */
.ProseMirror img {
  max-width: 100%;
  height: auto;
  margin: 1rem 0;
  border-radius: 0.25rem;
}

/* 에디터 포커스 스타일 */
.ProseMirror:focus {
  outline: none;
}

/* 플레이스홀더 스타일 */
.ProseMirror p.is-editor-empty:first-child::before {
  content: attr(data-placeholder);
  float: left;
  color: #adb5bd;
  pointer-events: none;
  height: 0;
}

/* 텍스트 정렬 스타일 */
.ProseMirror [style*="text-align: left"] {
  text-align: left !important;
}

.ProseMirror [style*="text-align: center"] {
  text-align: center !important;
}

.ProseMirror [style*="text-align: right"] {
  text-align: right !important;
}

/* 글자 크기 입력 스타일 */
input[type="number"] {
  appearance: textfield;      /* 표준 속성 추가 */
  -moz-appearance: textfield; /* Firefox 대응 */
  -webkit-appearance: textfield; /* Safari, Chrome 대응 */
}

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* 다크 모드 스타일 */
.dark .ProseMirror {
  color: #e2e8f0;
}

.dark .ProseMirror h1,
.dark .ProseMirror h2,
.dark .ProseMirror h3 {
  color: #f1f5f9;
}

.dark .ProseMirror a {
  color: #93c5fd;
}

.dark .ProseMirror strong {
  color: #f1f5f9;
}

.dark .ProseMirror code {
  color: #e2e8f0;
  background-color: #1e293b;
}

.dark .ProseMirror blockquote {
  color: #cbd5e1;
  border-left-color: #475569;
}

.dark .ProseMirror hr {
  border-color: #475569;
}

.dark .ProseMirror ul,
.dark .ProseMirror ol {
  color: #e2e8f0;
}

.dark .ProseMirror li::marker {
  color: #94a3b8;
}
</style>