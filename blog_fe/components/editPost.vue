<template>
  <div class="w-full max-w-5xl mx-auto p-6">
    <h1 class="text-3xl font-extrabold mb-6 text-gray-800">
      {{ mode === 'blog' ? '블로그 글 작성' : '프로젝트 글 작성' }}
    </h1>

    <!-- 제목 입력 -->
    <div class="mb-6">
      <label for="title" class="block font-medium mb-1 text-gray-700">제목</label>
      <input id="title" v-model="title"
        class="border px-4 py-2 w-full rounded focus:outline-none focus:ring-0 focus:border-blue-300 transition-all"
        placeholder="글 제목을 입력하세요" />
    </div>

    <!-- 카테고리 선택 -->
    <div class="mb-6">
      <label for="category" class="block font-medium mb-1 text-gray-700">카테고리</label>
      <select id="category" v-model="categoryId"
        class="border px-4 py-2 w-full rounded focus:outline-none focus:ring-0 focus:border-blue-300 transition-all">
        <option value="1">Test Category</option>
      </select>
    </div>

    <!-- 태그 선택 -->
    <div class="mb-6">
      <label for="tags" class="block font-medium mb-1 text-gray-700">태그</label>
      <div class="flex flex-wrap gap-2">
        <button v-for="tag in availableTags" :key="tag.id" @click="toggleTag(tag.id)" :class="{
          'bg-blue-500 text-white': tagIds.includes(tag.id),
          'bg-gray-200 text-gray-700': !tagIds.includes(tag.id),
        }" class="text-sm py-2 px-4 rounded focus:outline-none transition-all">
          {{ tag.name }}
        </button>
      </div>
    </div>

    <!-- 툴바 -->
    <div class="flex flex-wrap gap-3 mb-4">
      <!-- 굵게 -->
      <button @click="toggleBold"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all">
        굵게
      </button>

      <!-- 기울임 -->
      <button @click="toggleItalic"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all">
        기울임
      </button>

      <!-- 밑줄 -->
      <button @click="toggleUnderline"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all">
        밑줄
      </button>

      <!-- 하이라이트 -->
      <button @click="toggleHighlight"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all">
        하이라이트
      </button>

      <!-- 코드 -->
      <button @click="toogleCodeBlock"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all">
        코드
      </button>

      <!-- 태스크 리스트 -->
      <button @click="addTaskList"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all">
        태스크 리스트
      </button>

      <!-- 이미지 업로드 -->
      <input type="file" class="hidden" ref="fileInput" @change="handleFileUpload" accept="image/*" />
      <button @click="triggerFileUpload"
        class="text-sm py-2 px-4 bg-gray-100 hover:bg-gray-200 rounded focus:outline-none focus:ring-0 transition-all">
        이미지 업로드
      </button>
    </div>

    <!-- Tiptap Editor -->
    <div class="mb-6 border border-gray-300 rounded shadow-sm max-h-[400px] min-h-[400px] overflow-y-auto">
      <editor-content :editor="editor" class="w-full h-full text-base p-3" />
    </div>


    <!-- 등록 버튼 -->
    <button @click="submitPost"
      class="bg-blue-500 hover:bg-blue-600 text-white px-6 py-3 rounded font-semibold shadow transition-all focus:outline-none focus:ring-0">
      글 등록
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useRuntimeConfig } from '#app';
import { useAuthStore } from '~/stores/auth';
import { useEditor, EditorContent } from '@tiptap/vue-3';
import StarterKit from '@tiptap/starter-kit';
import ResizeImage from 'tiptap-extension-resize-image';
import Underline from '@tiptap/extension-underline';
import Highlight from '@tiptap/extension-highlight';
import TextAlign from '@tiptap/extension-text-align';
import Link from '@tiptap/extension-link';
import Image from '@tiptap/extension-image';
import TaskList from '@tiptap/extension-task-list';
import TaskItem from '@tiptap/extension-task-item';
import CodeBlock from '@tiptap/extension-code-block';
import { CodeBlockLowlight } from '@tiptap/extension-code-block-lowlight';
import { common, createLowlight } from 'lowlight'
import javascript from 'highlight.js/lib/languages/javascript';
import java from 'highlight.js/lib/languages/java';
import html from 'highlight.js/lib/languages/xml'; // HTML/XML
import css from 'highlight.js/lib/languages/css';
import Placeholder from '@tiptap/extension-placeholder';

const props = defineProps<{
  mode: 'blog' | 'project';
  postId: number;
}>();


// **DTO 타입 정의**
interface BlogResponseDto {
  blogId: number;
  categoryId: number;
  thumbnail: string;
  title: string;
  content: string;
  tagIds: number[];
}

interface ProjectResponseDto {
  projectId: number;
  categoryId: number;
  thumbnail: string;
  title: string;
  content: string;
  tagIds: number[];
}


// TipTap 에디터의 첫 번째 이미지 추출 함수
const extractFirstImage = (html: string): string | null => {
  const match = html.match(/<img[^>]+src="([^">]+)"/);
  return match ? match[1] : null;
};

// 폼 데이터
const title = ref('');
const thumbnail = ref('');
const categoryId = ref<number>(1); // Default to "Test Category"
const tagIds = ref<number[]>([]);
const availableTags = ref([
  { id: 1, name: 'None'},
]); // Default tag list

const authStore = useAuthStore();
const router = useRouter();

const lowlight = createLowlight();

lowlight.register('java', java);
lowlight.register('javascript', javascript);
lowlight.register('html', html);
lowlight.register('css', css);

// 언어 선택에 따른 코드 블록 추가 함수
const addCodeBlock = (language: string) => {
  editor.value?.chain().focus().insertContent({
    type: 'codeBlock',
    attrs: { language },
  }).run();
};

// Tiptap Editor 초기화
const editor = useEditor({
  extensions: [
    StarterKit.configure({
      codeBlock: false,
    }),              // 기본 텍스트 편집 기능
    Placeholder.configure({
      placeholder: '내용을 입력하세요...',
    }),
    CodeBlockLowlight.configure({
      lowlight,
    }),
    ResizeImage,            // 이미지 크기 조절 기능
    CodeBlock,
    Underline,               // 밑줄
    Highlight,               // 하이라이트
    TextAlign.configure({    // 텍스트 정렬
      types: ['heading', 'paragraph'],
    }),
    Link.configure({         // 링크
      openOnClick: true,
      autolink: true,
    }),
    Image.configure({        // 이미지
      inline: false,
      allowBase64: false,
    }),
    TaskList,                // 태스크 리스트
    TaskItem.configure({ nested: true }),
  ],
  content: '',
  autofocus: true,
  editorProps: {
    attributes: {
      class: "flex-1 w-full h-full p-3 focus:outline-none",
    },
  },
});

// 툴바 동작
const toggleBold = () => editor.value?.chain().focus().toggleBold().run();
const toggleItalic = () => editor.value?.chain().focus().toggleItalic().run();
const toggleUnderline = () => editor.value?.chain().focus().toggleUnderline().run();
const toggleHighlight = () => editor.value?.chain().focus().toggleHighlight().run();
const addTaskList = () => editor.value?.chain().focus().toggleTaskList().run();
const toogleCodeBlock = () => editor.value?.chain().focus().toggleCodeBlock().run();

// config
const config = useRuntimeConfig();

// 파일 업로드
const fileInput = ref<HTMLInputElement | null>(null);
const triggerFileUpload = () => {
  fileInput.value?.click();
};

const imageUrls = ref<string[]>([]); // 업로드된 이미지 URL을 저장할 배열

const handleFileUpload = async (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (file) {
    try {
      const imageUrl = await uploadImage(file);
      imageUrls.value.push(imageUrl); // 배열에 URL 추가
      editor.value?.chain().focus().setImage({ src: imageUrl }).run(); // 에디터에 이미지 삽입
    } catch (error) {
      alert('이미지 업로드 중 오류가 발생했습니다.');
    }
  }
};

// 이미지 업로드 함수
const uploadImage = async (file: File): Promise<string> => {
  const config = useRuntimeConfig();
  const imageUrl = props.mode === 'blog'
    ? `${config.public.apiBaseUrl}/api/v1/upload/file/blog`
    : `${config.public.apiBaseUrl}/api/v1/upload/file/project`;

  try {
    const formData = new FormData();
    formData.append('file', file);

    const token = authStore.token;

    const response = await $fetch<string[]>(imageUrl, {
      method: 'POST',
      body: formData,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return `${config.public.uploadsBaseUrl}/${response[0]}`;
  } catch (error) {
    console.error('이미지 업로드 실패:', error);
    throw new Error('이미지 업로드 실패');
  }
};

// 태그 선택 로직
const toggleTag = (tagId: number) => {
  if (tagIds.value.includes(tagId)) {
    tagIds.value = tagIds.value.filter((id) => id !== tagId); // 선택 해제
  } else {
    tagIds.value.push(tagId); // 선택
  }
};

// **등록/수정 요청**
const submitPost = async () => {
  if (!title.value.trim() || !editor.value?.getText().trim()) {
    alert('제목과 내용을 입력하세요.');
    return;
  }

  const content = editor.value.getHTML();

  const requestDto = {
    categoryId: categoryId.value,
    tagIds: tagIds.value,
    thumbnail: thumbnail.value,
    title: title.value,
    content,
  };

  console.log("보내는 데이터:", requestDto); // 🚀 디버깅용

  try {
    const apiUrl = props.mode === 'blog'
      ? `${config.public.apiBaseUrl}/api/v1/blog${props.postId ? '/' + props.postId : ''}`
      : `${config.public.apiBaseUrl}/api/v1/project${props.postId ? '/' + props.postId : ''}`;

    const method = props.postId ? 'PUT' : 'POST';

    const response: { blogId?: number; projectId?: number; message: string } = await $fetch(apiUrl, {
      method,
      body: requestDto,
      headers: { Authorization: `Bearer ${authStore.token}` },
    });

    alert(response.message);

    // 글 작성 후 또는 수정 후 이동할 경로 설정
    if (props.mode === 'blog' && response.blogId) {
      router.push(`/blog/${response.blogId}`);
    } else if (props.mode === 'project' && response.projectId) {
      router.push(`/project/${response.projectId}`);
    }
  } catch (error) {
    console.error('글 저장 중 오류 발생:', error);
    alert('글 저장 중 오류가 발생했습니다.');
  }
};

const fetchPostData = async () => {
  if (!props.postId) return;

  try {
    const apiUrl = props.mode === 'blog'
      ? `${config.public.apiBaseUrl}/api/v1/blog/${props.postId}`
      : `${config.public.apiBaseUrl}/api/v1/project/${props.postId}`;

    console.log("Fetching post data from:", apiUrl); // 디버깅용

    const response: BlogResponseDto | ProjectResponseDto = await $fetch(apiUrl, {
      method: 'GET',
      headers: { Authorization: `Bearer ${authStore.token}` },
    });

    console.log("Fetched post data:", response); // 디버깅용

    // 데이터 반영
    title.value = response.title;
    categoryId.value = response.categoryId;
    tagIds.value = response.tagIds ?? []; // 🚀 tagIds가 null이면 빈 배열로 처리
    thumbnail.value = response.thumbnail;

    // 🚀 editor가 완전히 로드된 후에 내용 삽입
    if (editor.value) {
      editor.value.commands.setContent(response.content);
    } else {
      watch(editor, (newEditor) => {
        if (newEditor) {
          newEditor.commands.setContent(response.content);
        }
      });
    }
  } catch (error) {
    console.error('게시물을 불러오는 중 오류 발생:', error);
    alert('게시물을 불러오는 중 오류가 발생했습니다.');
  }
};



// `props.postId` 값이 변경될 때 데이터를 불러오기
watch(() => props.postId, (newId) => {
  if (newId) fetchPostData();
});

// 초기 로드 시 데이터 가져오기
onMounted(() => {
  if (props.postId) {
    fetchPostData();
  }
});

</script>