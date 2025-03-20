<template>
  <div>
    <!-- 상단 프로필 섹션 -->
    <section class="container mx-auto px-4 py-12 sm:px-8">
      <div class="flex flex-wrap items-center gap-6 sm:gap-12">
        <!-- 사진 -->
        <div class="flex w-full justify-center sm:w-1/3">
          <div
            class="flex aspect-square h-48 w-48 items-center justify-center rounded-full bg-gray-200 sm:h-64 sm:w-64">
            <span class="text-gray-400">
              <img :src="'/profilePhoto.jpeg'" alt="" class="rounded-full h-full w-full object-cover">
            </span>
          </div>
        </div>
        <!-- 연락처 정보 -->
        <div class="w-full text-gray-700 sm:w-2/3">
          <ul class="space-y-2">
            <li><strong>이메일:</strong> jumbochips1@gmail.com</li>
            <li><strong>주소:</strong> 서울특별시 노원구</li>
          </ul>
        </div>
      </div>
    </section>

    <!-- 자기소개 섹션 -->
    <section class="container mx-auto px-4 py-8 sm:px-8">
      <h2 class="text-xl font-bold text-gray-900 sm:text-2xl">자기소개 (Introduce)</h2>
      <p class="mt-4 text-gray-700">안녕하세요, 안정적이고 확장 가능한 서버 아키텍처를 설계하고 구현하는 것에 집중하며, 
        필요에 따라 프론트엔드도 다룰 수 있습니다. 효율적인 백엔드 시스템을 연구 및 개발하고 있습니다.</p>
    </section>

    <!-- 프로젝트 경험 섹션 -->
    <section class="container mx-auto px-4 py-8 sm:px-8">
      <h2 class="text-xl font-bold text-gray-900 sm:text-2xl">프로젝트 경험 (Project Experience)</h2>
      <ul class="mt-4 space-y-4 text-gray-700">
        <li>
          <h3 class="font-semibold">Jumbochips</h3>
          <p class="text-sm">jumbochips.com은 개발자로서의 이력과 프로젝트 경험을 기록하고, 기술 블로그를 운영하며, 개인적인 학습과 지식을 기록하기 위해 구축한 개인 블로그 및 포트폴리오 사이트입니다. 해당 플랫폼은 사용자 친화적인 마크다운 디터와 효율적인 배포 시스템을 갖춘 웹 어플리케이션을 목표로 개발하였습니다.
</p>
        </li>
        <li>
          <h3 class="font-semibold">Chillguy-Music</h3>
          <p class="text-sm">기존 음악 스트리밍 서비스가 제공하지 못하는 개인 맞춤형 음악 추천 경험을 제공하고자 AI를 활용한 음악 추천 플랫폼을 개발 중입니다. 사용자의 기분, 날씨, 활동 상태를 입력받아 실시간으로 최적화된 음악 플레이리스트를 추천하며, 사용자의 취향에 따라 플레이리스트를 손쉽게 수정할 수 있도록 지원합니다. 추천된 플레이리스트는 YouTube Music과 연동하여 즉시 저장 및 재생 가능하며, Spotify 등 타 서비스 연동도 고려하고 있습니다.</p>
        </li>
      </ul>
    </section>

    <!-- 기술 스킬 섹션 -->
    <section class="container mx-auto px-4 py-8 sm:px-8">
      <h2 class="text-xl font-bold text-gray-900 sm:text-2xl">기술 (Skill)</h2>
      <ul class="mt-4 grid grid-cols-2 gap-4 text-gray-700 sm:grid-cols-4">
        <li>Java</li>
        <li>SpringBoot</li>
        <li>Nuxt.js</li>
        <li>MySQL</li>
        <li>Spring Data JPA</li>
        <li>Git</li>
        <li>GitHub Actions</li>
        <li>Docker Compose</li>
      </ul>
    </section>

    <!-- 학력 섹션 -->
    <section class="container mx-auto px-4 py-8 sm:px-8">
      <h2 class="text-xl font-bold text-gray-900 sm:text-2xl">학력 (Education)</h2>
      <ul class="mt-4 space-y-4 text-gray-700">
        <li>
          <h3 class="font-semibold">인덕대학교</h3>
          <p class="text-sm">세무회계학과</p>
          <p class="text-sm">졸업년도: 2022</p>
        </li>
      </ul>
    </section>
  </div>

</template>
<script setup lang="ts">

</script>