<template>
  <div>
    <!-- 상단 프로필 섹션 -->
    <section class="container mx-auto px-4 py-12 sm:px-8">
      <div class="flex flex-wrap items-center gap-6 sm:gap-12">
        <!-- 사진 -->
        <div class="flex w-full justify-center sm:w-1/3">
          <div
            class="flex aspect-square h-48 w-48 items-center justify-center rounded-full bg-gray-200 sm:h-64 sm:w-64">
            <span class="text-gray-400">사진</span>
          </div>
        </div>
        <!-- 연락처 정보 -->
        <div class="w-full text-gray-700 sm:w-2/3">
          <ul class="space-y-2">
            <li><strong>연락처:</strong> 010-1234-5678</li>
            <li><strong>이메일:</strong> example@example.com</li>
            <li><strong>주소:</strong> 서울특별시 강남구</li>
          </ul>
        </div>
      </div>
    </section>

    <!-- 자기소개 섹션 -->
    <section class="container mx-auto px-4 py-8 sm:px-8">
      <h2 class="text-xl font-bold text-gray-900 sm:text-2xl">자기소개 (Introduce)</h2>
      <p class="mt-4 text-gray-700">안녕하세요, 저는 웹 개발자로서 사용자 경험과 기술적 완성도를 동시에 추구하고 있습니다. 항상 새로운 기술을 탐구하며, 팀원들과 협력하여 더 나은
        결과를 만들기 위해 노력하고 있습니다.</p>
    </section>

    <!-- 프로젝트 경험 섹션 -->
    <section class="container mx-auto px-4 py-8 sm:px-8">
      <h2 class="text-xl font-bold text-gray-900 sm:text-2xl">프로젝트 경험 (Project Experience)</h2>
      <ul class="mt-4 space-y-4 text-gray-700">
        <li>
          <h3 class="font-semibold">프로젝트 이름 1</h3>
          <p class="text-sm">프로젝트 설명과 역할</p>
        </li>
        <li>
          <h3 class="font-semibold">프로젝트 이름 2</h3>
          <p class="text-sm">프로젝트 설명과 역할</p>
        </li>
      </ul>
    </section>

    <!-- 기술 스킬 섹션 -->
    <section class="container mx-auto px-4 py-8 sm:px-8">
      <h2 class="text-xl font-bold text-gray-900 sm:text-2xl">기술 (Skill)</h2>
      <ul class="mt-4 grid grid-cols-2 gap-4 text-gray-700 sm:grid-cols-4">
        <li>JavaScript</li>
        <li>React</li>
        <li>Vue.js</li>
        <li>Spring Boot</li>
        <li>Node.js</li>
        <li>MySQL</li>
        <li>MongoDB</li>
        <li>CSS / Tailwind</li>
      </ul>
    </section>

    <!-- 학력 섹션 -->
    <section class="container mx-auto px-4 py-8 sm:px-8">
      <h2 class="text-xl font-bold text-gray-900 sm:text-2xl">학력 (Education)</h2>
      <ul class="mt-4 space-y-4 text-gray-700">
        <li>
          <h3 class="font-semibold">대학교 이름</h3>
          <p class="text-sm">전공 / 학위 (예: 컴퓨터 공학 / 학사)</p>
          <p class="text-sm">졸업년도: 2020</p>
        </li>
      </ul>
    </section>
  </div>

</template>
<script setup lang="ts">

</script>