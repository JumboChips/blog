<template>
  <div class="fixed bottom-6 right-6 z-50 flex flex-col items-end">
    <!-- Chat Button with Hover Tooltip -->
    <div class="relative group">
      <!-- Professional Tooltip - Only shows on hover -->
      <div 
        class="absolute bottom-full right-0 mb-2 w-64 p-3 bg-white dark:bg-gray-800 rounded-lg shadow-lg text-sm opacity-0 group-hover:opacity-100 transition-opacity duration-300 transform translate-y-2 group-hover:translate-y-0 pointer-events-none"
        style="filter: drop-shadow(0 4px 6px rgba(0, 0, 0, 0.1));"
      >
        <div class="flex items-start space-x-3">
          <div class="flex-shrink-0 mt-0.5">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-primary"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path></svg>
          </div>
          <div>
            <p class="font-medium text-gray-800 dark:text-white">JumboChips 고객센터</p>
            <p class="text-gray-600 dark:text-gray-300 text-xs mt-1">
              궁금한 점이 있으신가요? 실시간 채팅으로 문의해 보세요.
            </p>
          </div>
        </div>
        <!-- Modern triangle pointer -->
        <div class="absolute -bottom-2 right-4 w-4 h-4 bg-white dark:bg-gray-800 transform rotate-45"></div>
      </div>
      
      <!-- Modern Chat Button - 밝은 그라데이션 색상 적용 -->
      <button 
        @click="toggleChat" 
        class="flex items-center justify-center rounded-full shadow-lg transition-all duration-300 relative"
        :class="[
          isChatOpen 
            ? 'opacity-0 scale-0 w-0 h-0' 
            : 'w-14 h-14 hover:shadow-xl hover:-translate-y-1 opacity-100 scale-100 chat-button-gradient'
        ]"
      >
        <span v-if="unreadCount > 0" class="absolute -top-1 -right-1 flex h-5 w-5 items-center justify-center rounded-full bg-red-500 text-xs text-white">
          {{ unreadCount }}
        </span>
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-white"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path></svg>
      </button>
    </div>

    <!-- Modern Chat Window -->
    <transition
      enter-active-class="transition duration-300 ease-out"
      enter-from-class="transform scale-95 opacity-0"
      enter-to-class="transform scale-100 opacity-100"
      leave-active-class="transition duration-200 ease-in"
      leave-from-class="transform scale-100 opacity-100"
      leave-to-class="transform scale-95 opacity-0"
    >
      <div 
        v-if="isChatOpen" 
        class="mb-3 w-80 sm:w-96 h-[450px] rounded-lg shadow-2xl overflow-hidden flex flex-col bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700"
      >
        <!-- Username Input Screen -->
        <div v-if="!hasUsername" class="flex flex-col h-full">
          <div class="p-4 bg-white dark:bg-gray-800 border-b border-gray-200 dark:border-gray-700 flex justify-between items-center">
            <h3 class="font-medium text-gray-800 dark:text-white">채팅을 시작하기 전에</h3>
            <button @click="toggleChat" class="text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M18 6 6 18"/><path d="m6 6 12 12"/></svg>
            </button>
          </div>
          <div class="flex-1 flex flex-col items-center justify-center p-6">
            <div class="w-16 h-16 rounded-full bg-primary/10 flex items-center justify-center mb-4">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-primary"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>
            </div>
            <h3 class="text-lg font-medium text-gray-800 dark:text-white mb-4">닉네임을 입력해주세요</h3>
            <div class="w-full max-w-xs">
              <input 
                v-model="tempUsername" 
                @keyup.enter="setUsername"
                type="text" 
                placeholder="닉네임" 
                class="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-800 dark:text-white focus:outline-none focus:ring-2 focus:ring-primary"
                autofocus
              />
              <button 
                @click="setUsername" 
                class="w-full mt-3 py-2 rounded-lg hover:bg-primary-hover transition-colors chat-button-gradient text-white"
                :disabled="!tempUsername.trim()"
                :class="{'opacity-50 cursor-not-allowed': !tempUsername.trim()}"
              >
                시작하기
              </button>
            </div>
          </div>
        </div>

        <!-- Chat Interface -->
        <template v-else>
          <!-- Chat Header -->
          <div class="p-4 bg-white dark:bg-gray-800 border-b border-gray-200 dark:border-gray-700 flex justify-between items-center">
            <div class="flex items-center space-x-3">
              <div class="w-8 h-8 rounded-full chat-button-gradient flex items-center justify-center text-white">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path></svg>
              </div>
              <div>
                <h3 class="font-medium text-gray-800 dark:text-white">JumboChips 고객센터</h3>
                <div class="flex items-center text-xs text-green-500">
                  <span class="w-2 h-2 rounded-full bg-green-500 mr-1"></span>
                  온라인
                </div>
              </div>
            </div>
            <button @click="toggleChat" class="text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M18 6 6 18"/><path d="m6 6 12 12"/></svg>
            </button>
          </div>

          <!-- Chat Messages -->
          <div 
            ref="chatBox"
            class="flex-1 p-4 overflow-y-auto bg-gray-50 dark:bg-gray-900"
          >
            <!-- Welcome Message -->
            <div v-if="messages.length === 0" class="flex flex-col items-center justify-center h-full text-center p-6">
              <div class="w-16 h-16 rounded-full bg-primary/10 flex items-center justify-center mb-4">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-primary"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path></svg>
              </div>
              <h3 class="text-lg font-medium text-gray-800 dark:text-white mb-2">JumboChips에 오신 것을 환영합니다</h3>
              <p class="text-gray-600 dark:text-gray-400 text-sm">
                무엇을 도와드릴까요? 질문이나 문의사항을 남겨주세요.
              </p>
            </div>

            <!-- Message Bubbles -->
            <div 
              v-for="(msg, index) in messages" 
              :key="index" 
              class="mb-4"
            >
              <div 
                class="flex"
                :class="{'justify-end': msg.sender === username}"
              >
                <!-- Avatar for other users -->
                <div v-if="msg.sender !== username" class="w-8 h-8 rounded-full chat-button-gradient flex-shrink-0 flex items-center justify-center text-white mr-2">
                  {{ msg.sender.charAt(0).toUpperCase() }}
                </div>
                
                <!-- Message Bubble -->
                <div 
                  class="max-w-[75%] rounded-lg px-4 py-2 break-words shadow-sm"
                  :class="msg.sender === username 
                    ? 'chat-message-gradient text-white rounded-tr-none' 
                    : 'bg-white dark:bg-gray-800 text-gray-800 dark:text-gray-200 rounded-tl-none border border-gray-200 dark:border-gray-700'"
                >
                  <div v-if="msg.sender !== username" class="text-xs font-medium mb-1 text-gray-600 dark:text-gray-400">{{ msg.sender }}</div>
                  <div>{{ msg.message }}</div>
                  <div class="text-xs mt-1 text-right" :class="msg.sender === username ? 'text-white/70' : 'text-gray-500'">
                    {{ formatTime(msg.sentAt) }}
                  </div>
                </div>
                
                <!-- Avatar for current user -->
                <div v-if="msg.sender === username" class="w-8 h-8 rounded-full bg-gray-200 dark:bg-gray-700 flex-shrink-0 flex items-center justify-center text-gray-700 dark:text-gray-300 ml-2">
                  {{ username.charAt(0).toUpperCase() }}
                </div>
              </div>
            </div>
          </div>

          <!-- Chat Input -->
          <div class="p-3 bg-white dark:bg-gray-800 border-t border-gray-200 dark:border-gray-700">
            <div class="flex items-center rounded-full bg-gray-100 dark:bg-gray-700 px-3 py-1">
              <input 
                v-model="inputMessage" 
                @keyup.enter="sendMessage"
                type="text" 
                placeholder="메시지를 입력하세요..." 
                class="flex-1 bg-transparent border-none outline-none py-2 text-gray-800 dark:text-white placeholder-gray-500"
              />
              <button 
                @click="sendMessage" 
                class="ml-2 w-8 h-8 rounded-full chat-button-gradient flex items-center justify-center text-white"
                :disabled="!inputMessage.trim()"
                :class="{'opacity-50 cursor-not-allowed': !inputMessage.trim()}"
              >
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m22 2-7 20-4-9-9-4Z"/><path d="M22 2 11 13"/></svg>
              </button>
            </div>
          </div>
        </template>
      </div>
    </transition>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount, nextTick } from 'vue'

// isDarkMode prop 추가
const props = defineProps({
  isDarkMode: {
    type: Boolean,
    default: false
  }
})

interface ChatMessage {
  sender: string
  message: string
  sentAt: string
}

const roomId = 'test-room' // 실제 앱에서는 route param이나 사용자 기반으로 설정
const socket = ref<WebSocket | null>(null)
const messages = ref<ChatMessage[]>([])
const inputMessage = ref('')
const username = ref('')
const tempUsername = ref('') // 임시 사용자 이름 입력용
const isChatOpen = ref(false)
const chatBox = ref<HTMLElement | null>(null)
const isUnmounted = ref(false)
const unreadCount = ref(0) // 읽지 않은 메시지 수 (실제로는 서버에서 가져오거나 계산)
const isConnected = ref(false) // 웹소켓 연결 상태

// 사용자 이름이 설정되었는지 확인
const hasUsername = computed(() => !!username.value)

// 사용자 이름 설정
const setUsername = () => {
  if (tempUsername.value.trim()) {
    username.value = tempUsername.value.trim()
    localStorage.setItem('chat-username', username.value)
    tempUsername.value = ''
    
    // 사용자 이름이 설정되면 웹소켓 연결
    if (!isConnected.value) {
      connectWebSocket()
    }
  }
}

const toggleChat = () => {
  isChatOpen.value = !isChatOpen.value
  
  if (isChatOpen.value) {
    // 채팅창이 열릴 때
    
    // 저장된 사용자 이름이 있는지 확인
    const savedName = localStorage.getItem('chat-username')
    if (savedName) {
      username.value = savedName
      
      // 웹소켓 연결이 없으면 연결
      if (!isConnected.value) {
        connectWebSocket()
      }
    }
    
    // 채팅창이 열릴 때 스크롤을 아래로 이동
    nextTick(() => {
      scrollToBottom()
      // 채팅창을 열면 읽지 않은 메시지 수 초기화
      unreadCount.value = 0
    })
  } else {
    // 채팅창이 닫힐 때 웹소켓 연결 종료 (선택적)
    // socket.value?.close()
    // isConnected.value = false
  }
}

const sendMessage = () => {
  if (inputMessage.value.trim() && socket.value?.readyState === WebSocket.OPEN) {
    const msg: ChatMessage = {
      sender: username.value,
      message: inputMessage.value,
      sentAt: new Date().toISOString(),
    }
    socket.value.send(JSON.stringify(msg))
    inputMessage.value = ''
    
    // 메시지 전송 후 스크롤 아래로
    nextTick(() => {
      scrollToBottom()
    })
  }
}

const formatTime = (iso: string) => {
  const date = new Date(iso)
  const hours = date.getHours().toString().padStart(2, '0')
  const minutes = date.getMinutes().toString().padStart(2, '0')
  return `${hours}:${minutes}`
}

const scrollToBottom = () => {
  if (chatBox.value) {
    chatBox.value.scrollTop = chatBox.value.scrollHeight
  }
}

const connectWebSocket = () => {
  socket.value = new WebSocket(`wss://jumbochips.com/ws/chat?roomId=${roomId}`)

  socket.value.onopen = () => {
    console.log('채팅 연결됨')
    isConnected.value = true
  }

  socket.value.onmessage = (event) => {
    const received: ChatMessage = JSON.parse(event.data)
    messages.value.push(received)
    
    // 채팅창이 닫혀있을 때 메시지가 오면 읽지 않은 메시지 수 증가
    if (!isChatOpen.value && received.sender !== username.value) {
      unreadCount.value++
    }
    
    nextTick(() => {
      if (isChatOpen.value) {
        scrollToBottom()
      }
    })
  }

  socket.value.onerror = (err) => {
    console.error('채팅 오류:', err)
    isConnected.value = false
  }

  socket.value.onclose = () => {
    console.warn('채팅 연결 종료됨')
    isConnected.value = false
    
    // 연결이 끊어지면 재연결 시도 (채팅창이 열려있을 때만)
    if (!isUnmounted.value && isChatOpen.value) {
      setTimeout(connectWebSocket, 3000)
    }
  }
}

// 이전 메시지 로드 함수
const loadPreviousMessages = async () => {
  try {
    // API 호출이 가능한 경우 이전 메시지 로드
    // const { data } = await useFetch<ChatMessage[]>(`/api/v1/chat/messages?roomId=${roomId}`)
    // if (data.value) {
    //   messages.value.push(...data.value)
    //   nextTick(() => scrollToBottom())
    // }
  } catch (error) {
    console.error('이전 메시지 로드 실패:', error)
  }
}

onMounted(async() => {
  // 페이지 로드 시 이전 메시지만 로드
  await loadPreviousMessages()
  
  // 페이지 로드 시 웹소켓 연결은 하지 않음
  // 채팅창을 열 때만 연결
})

onBeforeUnmount(() => {
  // 컴포넌트가 제거될 때 웹소켓 연결 종료
  isUnmounted.value = true
  if (socket.value) {
    socket.value.close()
  }
})
</script>

<style scoped>
/* 채팅 버튼 그라데이션 스타일 */
.chat-button-gradient {
  background: linear-gradient(135deg, #9333ea, #4f46e5);
  box-shadow: 0 4px 14px rgba(79, 70, 229, 0.4);
  border: none;
}

.chat-button-gradient:hover {
  background: linear-gradient(135deg, #a855f7, #6366f1);
  box-shadow: 0 6px 20px rgba(79, 70, 229, 0.5);
}

/* 메시지 그라데이션 스타일 */
.chat-message-gradient {
  background: linear-gradient(135deg, #9333ea, #4f46e5);
}

/* 다크 모드에서의 그라데이션 조정 */
:deep(.dark) .chat-button-gradient {
  background: linear-gradient(135deg, #a855f7, #6366f1);
  box-shadow: 0 4px 14px rgba(99, 102, 241, 0.4);
}

:deep(.dark) .chat-button-gradient:hover {
  background: linear-gradient(135deg, #c084fc, #818cf8);
  box-shadow: 0 6px 20px rgba(99, 102, 241, 0.5);
}

:deep(.dark) .chat-message-gradient {
  background: linear-gradient(135deg, #a855f7, #6366f1);
}
</style>