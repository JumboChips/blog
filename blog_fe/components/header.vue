<template>
  <header class="bg-white shadow-md">
    <div class="container mx-auto flex items-center justify-between py-4 px-6">
      <!-- 로고 또는 제목 -->
      <h1 class="text-xl font-bold text-gray-900">
        <a href="/">
          JumboChips
        </a>
      </h1>

      <!-- 내비게이션 메뉴 -->
      <nav>
        <ul class="flex space-x-6 text-gray-600">
          <li>
            <NuxtLink :to="`/aboutme`" class="hover:text-gray-900 transition">
              About me
            </NuxtLink>
          </li>
          <li>
            <NuxtLink :to="`/project`" class="hover:text-gray-900 transition">
              Project
            </NuxtLink>
          </li>
          <li>
            <NuxtLink :to="`/blog`" class="hover:text-gray-900 transition">
              Blog
            </NuxtLink>
          </li>
        </ul>
      </nav>
    </div>
  </header>
</template>
<script>
</script>