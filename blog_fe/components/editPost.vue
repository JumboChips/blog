<template>
  <div class="w-full max-w-5xl mx-auto p-6">
    <h1 class="text-3xl font-extrabold mb-6 text-gray-800">
      {{ mode === 'blog' ? '블로그 글 수정' : '프로젝트 글 수정' }}
    </h1>

    <!-- 제목 입력 -->
    <div class="mb-6">
      <label for="title" class="block font-medium mb-1 text-gray-700">제목</label>
      <input
        id="title"
        v-model="title"
        class="border px-4 py-2 w-full rounded focus:outline-none focus:ring-0 focus:border-blue-300 transition-all"
        placeholder="글 제목을 입력하세요"
      />
    </div>

    <!-- 카테고리 선택 -->
    <div class="mb-6">
      <label for="category" class="block font-medium mb-1 text-gray-700">카테고리</label>
      <select
        id="category"
        v-model="categoryId"
        class="border px-4 py-2 w-full rounded focus:outline-none focus:ring-0 focus:border-blue-300 transition-all"
      >
        <option value="1">Default</option>
      </select>
    </div>

    <!-- 태그 선택 -->
    <div class="mb-6">
      <label for="tags" class="block font-medium mb-1 text-gray-700">태그</label>
      <div class="flex flex-wrap gap-2">
        <button
          v-for="tag in availableTags"
          :key="tag.id"
          @click="toggleTag(tag.id)"
          :class="{
            'bg-blue-500 text-white': tagIds.includes(tag.id),
            'bg-gray-200 text-gray-700': !tagIds.includes(tag.id),
          }"
          class="text-sm py-2 px-4 rounded focus:outline-none transition-all"
        >
          {{ tag.name }}
        </button>
      </div>
    </div>

    <!-- 툴바 -->
    <div class="flex flex-wrap gap-3 mb-4 p-2 bg-gray-50 rounded border">
      <!-- 제목 스타일 -->
      <div class="relative">
        <button
          @click="toggleHeadingMenu"
          class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all flex items-center gap-1"
        >
          제목 <span class="text-xs">▼</span>
        </button>
        <div
          v-if="showHeadingMenu"
          class="absolute top-full left-0 mt-1 bg-white shadow-lg rounded z-10 w-40"
        >
          <button
            @click="setHeading(1)"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 text-2xl font-bold"
          >
            제목 1
          </button>
          <button
            @click="setHeading(2)"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 text-xl font-bold"
          >
            제목 2
          </button>
          <button
            @click="setHeading(3)"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 text-lg font-bold"
          >
            제목 3
          </button>
          <button
            @click="setHeading(0)"
            class="w-full text-left px-4 py-2 hover:bg-gray-100"
          >
            일반 텍스트
          </button>
        </div>
      </div>

      <!-- 글자 크기 -->
      <div class="relative flex items-center gap-2">
        <input
          type="number"
          v-model="fontSize"
          @change="setCustomFontSize"
          class="w-16 text-sm py-1 px-2 border rounded focus:outline-none focus:border-blue-300"
          min="8"
          max="72"
          step="1"
        />
        <span class="text-sm text-gray-500">px</span>
      </div>

      <!-- 리스트 -->
      <div class="relative">
        <button
          @click="toggleListMenu"
          class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all flex items-center gap-1"
        >
          리스트 <span class="text-xs">▼</span>
        </button>
        <div
          v-if="showListMenu"
          class="absolute top-full left-0 mt-1 bg-white shadow-lg rounded z-10 w-40"
        >
          <button
            @click="toggleBulletList"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 flex items-center"
          >
            <span class="mr-2">•</span> 불릿 리스트
          </button>
          <button
            @click="toggleOrderedList"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 flex items-center"
          >
            <span class="mr-2">1.</span> 순번 리스트
          </button>
          <button
            @click="toggleTaskList"
            class="w-full text-left px-4 py-2 hover:bg-gray-100 flex items-center"
          >
            <span class="mr-2">☐</span> 태스크 리스트
          </button>
        </div>
      </div>

      <!-- 기본 서식 -->
      <button
        @click="toggleBold"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all font-bold"
      >
        B
      </button>
      <button
        @click="toggleItalic"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all italic"
      >
        I
      </button>
      <button
        @click="toggleUnderline"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all underline"
      >
        U
      </button>
      <button
        @click="toggleHighlight"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all"
        style="background-color: #ffff99"
      >
        형광펜
      </button>

      <!-- 텍스트 정렬 -->
      <div class="flex items-center gap-1">
        <button
          @click="setTextAlign('left')"
          class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all"
          :class="{ 'bg-gray-300': editor?.isActive({ textAlign: 'left' }) }"
        >
          ⟵
        </button>
        <button
          @click="setTextAlign('center')"
          class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all"
          :class="{ 'bg-gray-300': editor?.isActive({ textAlign: 'center' }) }"
        >
          ⟷
        </button>
        <button
          @click="setTextAlign('right')"
          class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all"
          :class="{ 'bg-gray-300': editor?.isActive({ textAlign: 'right' }) }"
        >
          ⟶
        </button>
      </div>

      <!-- 코드 -->
      <button
        @click="toggleCodeBlock"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all font-mono"
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
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all"
      >
        이미지
      </button>
    </div>

    <!-- Tiptap Editor -->
    <div class="mb-6 border border-gray-300 rounded shadow-sm max-h-[400px] min-h-[400px] overflow-y-auto">
      <editor-content :editor="editor" class="w-full h-full text-base p-3" />
    </div>

    <!-- 수정 버튼 -->
    <button
      @click="submitPost"
      class="bg-blue-500 hover:bg-blue-600 text-white px-6 py-3 rounded font-semibold shadow transition-all focus:outline-none focus:ring-0"
    >
      수정하기
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
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
import TaskList from '@tiptap/extension-task-list'
import TaskItem from '@tiptap/extension-task-item'
import TextStyle from '@tiptap/extension-text-style'
import { Color } from '@tiptap/extension-color'
import { CodeBlockLowlight } from '@tiptap/extension-code-block-lowlight'
import { common, createLowlight } from 'lowlight'
import javascript from 'highlight.js/lib/languages/javascript'
import java from 'highlight.js/lib/languages/java'
import html from 'highlight.js/lib/languages/xml'
import css from 'highlight.js/lib/languages/css'

const props = defineProps<{
  mode: 'blog' | 'project'
  postId: number
}>()

// DTO 타입 정의
interface BlogResponseDto {
  blogId: number
  categoryId: number
  thumbnail: string
  title: string
  content: string
  tagIds: number[]
}

interface ProjectResponseDto {
  projectId: number
  categoryId: number
  thumbnail: string
  title: string
  content: string
  tagIds: number[]
}

// 폼 데이터
const title = ref('');
const thumbnail = ref('');
const categoryId = ref<number>(1);
const tagIds = ref<number[]>([]);
const availableTags = ref([{ id: 1, name: 'Default' }]);

// 메뉴 상태
const showHeadingMenu = ref(false);
const showListMenu = ref(false);

// 글자 크기
const fontSize = ref(16);

// 파일 입력 참조
const fileInput = ref<HTMLInputElement | null>(null);

// 이미지 URL 배열
const imageUrls = ref<string[]>([]);

// Tiptap Editor 초기화
const editor = new Editor({
  extensions: [
    StarterKit.configure({
      heading: {
        levels: [1, 2, 3],
      },
      paragraph: {
        HTMLAttributes: {
          class: 'paragraph-spacing',
        },
      },
    }),
    TextStyle.configure({
      HTMLAttributes: {
        class: 'custom-font-size',
      },
    }),
    Color,
    Underline,
    Highlight.configure({
      multicolor: true,
    }),
    TextAlign.configure({
      types: ['heading', 'paragraph'],
      alignments: ['left', 'center', 'right'],
      defaultAlignment: 'left',
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
  ],
  editorProps: {
    attributes: {
      class: 'prose prose-lg max-w-none focus:outline-none',
    },
    handleKeyDown: (view, event) => {
      if (event.key === 'Enter' && !event.shiftKey) {
        const { selection } = view.state
        const { empty, $head } = selection

        if (empty && $head.parent.type.name === 'listItem' && $head.parent.textContent === '') {
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
  showListMenu.value = false
}

const toggleListMenu = () => {
  showListMenu.value = !showListMenu.value
  showHeadingMenu.value = false
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

// 커스텀 글자 크기 설정 함수
const setCustomFontSize = () => {
  if (fontSize.value < 8) fontSize.value = 8
  if (fontSize.value > 72) fontSize.value = 72
  
  editor.chain()
    .focus()
    .setMark('textStyle', { 
      fontSize: `${fontSize.value}px`,
      class: 'custom-font-size'
    })
    .run()
}

// 텍스트 정렬 함수
const setTextAlign = (align: 'left' | 'center' | 'right') => {
  editor.commands.setTextAlign(align)
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

// 글 수정
const submitPost = async () => {
  if (!title.value.trim() || !editor.getText().trim()) {
    alert('제목과 내용을 입력하세요.')
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

  try {
    const config = useRuntimeConfig()
    const url = props.mode === 'blog'
      ? `${config.public.apiBaseUrl}/api/v1/blog/${props.postId}`
      : `${config.public.apiBaseUrl}/api/v1/project/${props.postId}`

    const authStore = useAuthStore()
    const router = useRouter()
    
    await $fetch(url, {
      method: 'PUT',
      body: requestDto,
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${authStore.token}`,
      },
    })

    alert('수정되었습니다.')
    navigateTo(props.mode === 'blog' ? `/blog/${props.postId}` : `/project/${props.postId}`)
  } catch (error) {
    console.error('글 수정 실패:', error)
    alert('글 수정 중 오류가 발생했습니다.')
  }
}

// 게시물 데이터 가져오기
const fetchPostData = async () => {
  if (!props.postId) return

  try {
    const config = useRuntimeConfig()
    const apiUrl = props.mode === 'blog'
      ? `${config.public.apiBaseUrl}/api/v1/blog/${props.postId}`
      : `${config.public.apiBaseUrl}/api/v1/project/${props.postId}`

    const authStore = useAuthStore()
    const response: BlogResponseDto | ProjectResponseDto = await $fetch(apiUrl, {
      method: 'GET',
      headers: { Authorization: `Bearer ${authStore.token}` },
    })

    title.value = response.title
    categoryId.value = response.categoryId
    tagIds.value = response.tagIds ?? []
    thumbnail.value = response.thumbnail
    editor.commands.setContent(response.content)
  } catch (error) {
    console.error('게시물을 불러오는 중 오류 발생:', error)
    alert('게시물을 불러오는 중 오류가 발생했습니다.')
  }
}

// 초기 로드 시 데이터 가져오기
onMounted(() => {
  if (props.postId) {
    fetchPostData()
  }
})

// postId가 변경될 때 데이터 다시 가져오기
watch(() => props.postId, (newId) => {
  if (newId) fetchPostData()
})
</script>

<style>
/* 에디터 기본 스타일 */
.ProseMirror {
  min-height: 300px;
  padding: 1rem;
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

/* 문단 간격 스타일 */
.ProseMirror p {
  margin: 1em 0 !important;
  line-height: 1.6 !important;
}

.ProseMirror p:first-child {
  margin-top: 0 !important;
}

.ProseMirror p:last-child {
  margin-bottom: 0 !important;
}

/* 문단 내 줄바꿈 스타일 */
.ProseMirror br {
  display: block !important;
  content: "" !important;
  margin: 0.5em 0 !important;
}

/* 빈 문단 스타일 */
.ProseMirror p:empty::before {
  content: "\00a0";
  display: inline-block;
}

.paragraph-spacing {
  margin: 1em 0 !important;
}

/* 글자 크기 스타일 */
.ProseMirror .custom-font-size {
  font-size: var(--custom-font-size) !important;
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
.tiptap-code-block {
  background-color: #f5f5f5;
  border-radius: 0.25rem;
  padding: 1rem;
  margin: 0.5rem 0;
  font-family: monospace;
  white-space: pre-wrap;
  overflow-x: auto;
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

/* 글자 크기 입력 스타일 */
input[type="number"] {
  -moz-appearance: textfield;
}

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
</style>