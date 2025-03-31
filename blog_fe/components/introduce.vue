<template>
  <div class="relative overflow-hidden">
    <!-- 배경 그라데이션 요소 -->
    <div class="absolute inset-0 -z-10 bg-gradient-to-b from-purple-50 to-white dark:from-gray-800 dark:to-gray-900 transition-colors duration-300"></div>
    <div class="absolute -top-24 -right-24 w-96 h-96 bg-purple-200 dark:bg-purple-900 rounded-full mix-blend-multiply filter blur-3xl opacity-20 transition-colors duration-300"></div>
    <div class="absolute -bottom-24 -left-24 w-96 h-96 bg-blue-200 dark:bg-blue-900 rounded-full mix-blend-multiply filter blur-3xl opacity-20 transition-colors duration-300"></div>
    
    <!-- 히어로 섹션 -->
    <section class="relative pt-20 pb-12 md:pt-32 md:pb-20">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex flex-col lg:flex-row items-center lg:items-start gap-12 lg:gap-16">
          <!-- 프로필 이미지 -->
          <div class="w-full lg:w-1/3 flex justify-center order-2 lg:order-1">
            <div class="relative group">
              <div class="absolute -inset-1 rounded-full bg-gradient-to-r from-purple-600 to-blue-600 opacity-75 blur-md transition-all duration-500 group-hover:opacity-100"></div>
              <div class="relative aspect-square rounded-full h-64 w-64 sm:h-80 sm:w-80 overflow-hidden border-4 border-white dark:border-gray-800 shadow-xl transition-colors duration-300">
                <img v-if="imageUrl" :src="imageUrl" alt="프로필 이미지" class="h-full w-full object-cover transition-transform duration-500 group-hover:scale-105" />
                <span v-else class="flex h-full w-full items-center justify-center text-gray-400 dark:text-gray-600 text-lg sm:text-xl transition-colors duration-300">이미지</span>
              </div>
            </div>
          </div>
          
          <!-- 소개 텍스트 -->
          <div class="w-full lg:w-2/3 order-1 lg:order-2">
            <div class="space-y-2 text-center lg:text-left">
              <div class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-gradient-to-r from-purple-500/10 to-blue-500/10 text-purple-700 dark:text-purple-400 transition-colors duration-300">
                <span class="animate-pulse mr-2 h-2 w-2 rounded-full bg-purple-600 dark:bg-purple-400"></span>
                백엔드 개발자
              </div>
              <h1 class="text-3xl sm:text-4xl lg:text-5xl font-extrabold tracking-tight text-gray-900 dark:text-white transition-colors duration-300">
                안녕하세요, 저는 <span class="text-transparent bg-clip-text bg-gradient-to-r from-purple-600 to-blue-600 inline-block transform hover:scale-105 transition-transform duration-300">JumboChips</span>입니다
              </h1>
              <p class="text-xl text-gray-600 dark:text-gray-300 mt-4 max-w-2xl mx-auto lg:mx-0 transition-colors duration-300">
                안정적이고 확장 가능한 서버 아키텍처를 설계하고 구현하는 백엔드 개발자입니다.
              </p>
            </div>
            
            <!-- 핵심 역량 -->
            <div class="mt-8 grid grid-cols-2 md:grid-cols-3 gap-4 text-center lg:text-left">
              <div class="p-4 rounded-lg bg-white dark:bg-gray-800 shadow-sm hover:shadow-md transition-all duration-300 transform hover:-translate-y-1">
                <div class="w-10 h-10 mx-auto lg:mx-0 flex items-center justify-center rounded-full bg-purple-100 dark:bg-purple-900 text-purple-600 dark:text-purple-400 mb-3 transition-colors duration-300">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 17.58A5 5 0 0 0 18 8h-1.26A8 8 0 1 0 4 16.25"></path><path d="m8 16 4-4 4 4"></path><path d="M16 16v6"></path></svg>
                </div>
                <h3 class="font-bold text-gray-900 dark:text-white transition-colors duration-300">백엔드 개발</h3>
                <p class="text-sm text-gray-600 dark:text-gray-400 mt-1 transition-colors duration-300">SpringBoot, Java</p>
              </div>
              <div class="p-4 rounded-lg bg-white dark:bg-gray-800 shadow-sm hover:shadow-md transition-all duration-300 transform hover:-translate-y-1">
                <div class="w-10 h-10 mx-auto lg:mx-0 flex items-center justify-center rounded-full bg-blue-100 dark:bg-blue-900 text-blue-600 dark:text-blue-400 mb-3 transition-colors duration-300">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 11v3a1 1 0 0 0 1 1h16a1 1 0 0 0 1-1v-3"></path><path d="M12 19H4a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1h-3.5"></path><path d="m12 19 5-5"></path><path d="m17 14-5 5"></path></svg>
                </div>
                <h3 class="font-bold text-gray-900 dark:text-white transition-colors duration-300">데이터베이스</h3>
                <p class="text-sm text-gray-600 dark:text-gray-400 mt-1 transition-colors duration-300">MySQL, JPA</p>
              </div>
              <div class="p-4 rounded-lg bg-white dark:bg-gray-800 shadow-sm hover:shadow-md transition-all duration-300 transform hover:-translate-y-1">
                <div class="w-10 h-10 mx-auto lg:mx-0 flex items-center justify-center rounded-full bg-purple-100 dark:bg-purple-900 text-purple-600 dark:text-purple-400 mb-3 transition-colors duration-300">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M2 20h20"></path><path d="M5 4h14a1 1 0 0 1 1 1v10a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V5a1 1 0 0 1 1-1Z"></path></svg>
                </div>
                <h3 class="font-bold text-gray-900 dark:text-white transition-colors duration-300">프론트엔드</h3>
                <p class="text-sm text-gray-600 dark:text-gray-400 mt-1 transition-colors duration-300">Nuxt.js, Vue</p>
              </div>
              <div class="p-4 rounded-lg bg-white dark:bg-gray-800 shadow-sm hover:shadow-md transition-all duration-300 transform hover:-translate-y-1">
                <div class="w-10 h-10 mx-auto lg:mx-0 flex items-center justify-center rounded-full bg-blue-100 dark:bg-blue-900 text-blue-600 dark:text-blue-400 mb-3 transition-colors duration-300">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path></svg>
                </div>
                <h3 class="font-bold text-gray-900 dark:text-white transition-colors duration-300">DevOps</h3>
                <p class="text-sm text-gray-600 dark:text-gray-400 mt-1 transition-colors duration-300">CI/CD, Docker</p>
              </div>
              <div class="p-4 rounded-lg bg-white dark:bg-gray-800 shadow-sm hover:shadow-md transition-all duration-300 transform hover:-translate-y-1">
                <div class="w-10 h-10 mx-auto lg:mx-0 flex items-center justify-center rounded-full bg-purple-100 dark:bg-purple-900 text-purple-600 dark:text-purple-400 mb-3 transition-colors duration-300">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"></path><path d="m2 12 5-3 2 6 5-5 2 4 6-4"></path><path d="M9 9h.01"></path></svg>
                </div>
                <h3 class="font-bold text-gray-900 dark:text-white transition-colors duration-300">AI 통합</h3>
                <p class="text-sm text-gray-600 dark:text-gray-400 mt-1 transition-colors duration-300">머신러닝, 데이터 분석</p>
              </div>
              <div class="p-4 rounded-lg bg-white dark:bg-gray-800 shadow-sm hover:shadow-md transition-all duration-300 transform hover:-translate-y-1">
                <div class="w-10 h-10 mx-auto lg:mx-0 flex items-center justify-center rounded-full bg-blue-100 dark:bg-blue-900 text-blue-600 dark:text-blue-400 mb-3 transition-colors duration-300">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12s4.477 10 10 10z"></path><path d="m15 9-6 6"></path><path d="m9 9 6 6"></path></svg>
                </div>
                <h3 class="font-bold text-gray-900 dark:text-white transition-colors duration-300">문제 해결</h3>
                <p class="text-sm text-gray-600 dark:text-gray-400 mt-1 transition-colors duration-300">최적화, 디버깅</p>
              </div>
            </div>
            
            <!-- 소셜 링크 -->
            <div class="mt-10 flex flex-wrap justify-center lg:justify-start gap-4">
              <a href="https://github.com/jumbochips" target="_blank" class="flex items-center gap-2 px-4 py-2 rounded-lg bg-gray-900 dark:bg-gray-700 text-white hover:bg-gray-800 dark:hover:bg-gray-600 transition-colors duration-300">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M15 22v-4a4.8 4.8 0 0 0-1-3.5c3 0 6-2 6-5.5.08-1.25-.27-2.48-1-3.5.28-1.15.28-2.35 0-3.5 0 0-1 0-3 1.5-2.64-.5-5.36-.5-8 0C6 2 5 2 5 2c-.3 1.15-.3 2.35 0 3.5A5.403 5.403 0 0 0 4 9c0 3.5 3 5.5 6 5.5-.39.49-.68 1.05-.85 1.65-.17.6-.22 1.23-.15 1.85v4"></path><path d="M9 18c-4.51 2-5-2-7-2"></path></svg>
                GitHub
              </a>
              <a href="https://linkedin.com/in/jumbochips" target="_blank" class="flex items-center gap-2 px-4 py-2 rounded-lg bg-blue-600 dark:bg-blue-700 text-white hover:bg-blue-700 dark:hover:bg-blue-800 transition-colors duration-300">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M16 8a6 6 0 0 1 6 6v7h-4v-7a2 2 0 0 0-2-2 2 2 0 0 0-2 2v7h-4v-7a6 6 0 0 1 6-6z"></path>
                  <rect width="4" height="12" x="2" y="9"></rect>
                  <circle cx="4" cy="4" r="2"></circle>
                </svg>
                LinkedIn
              </a>
              <a href="mailto:jumbochips1@gmail.com" class="flex items-center gap-2 px-4 py-2 rounded-lg bg-gradient-to-r from-purple-600 to-blue-600 text-white hover:opacity-90 transition-colors duration-300">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect width="20" height="16" x="2" y="4" rx="2"></rect><path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"></path></svg>
                이메일로 연락하기
              </a>
            </div>
          </div>
        </div>
      </div>
    </section>
    
    <!-- 미션 & 소개 섹션 -->
    <section class="py-16 bg-white dark:bg-gray-900 transition-colors duration-300">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="max-w-3xl mx-auto">
          <div class="text-center mb-12">
            <h2 class="text-3xl font-bold text-gray-900 dark:text-white transition-colors duration-300">나의 미션</h2>
            <div class="mt-2 h-1 w-20 bg-gradient-to-r from-purple-600 to-blue-600 mx-auto"></div>
          </div>
          
          <div class="prose prose-lg mx-auto dark:prose-invert transition-colors duration-300">
            <p class="text-gray-700 dark:text-gray-300 leading-relaxed text-center transition-colors duration-300">
              안정적이고 확장 가능한 서버 아키텍처를 설계하고 구현하는 것에 집중하며, 필요에 따라 프론트엔드도 다룰 수 있습니다. 
              효율적인 백엔드 시스템을 연구 및 개발하여 사용자에게 의미 있는 경험을 제공하는 것이 저의 목표입니다.
            </p>
            
            <div class="mt-12 grid grid-cols-1 md:grid-cols-2 gap-8">
              <div class="bg-gray-50 dark:bg-gray-800 p-6 rounded-xl shadow-sm transition-colors duration-300">
                <h3 class="text-xl font-bold text-purple-700 dark:text-purple-400 mb-4 transition-colors duration-300">주요 역할</h3>
                <ul class="space-y-3">
                  <li class="flex items-start">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-purple-600 dark:text-purple-400 mr-2 mt-0.5 transition-colors duration-300" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"></polyline></svg>
                    <span class="text-gray-700 dark:text-gray-300 transition-colors duration-300">SpringBoot를 활용한 안정적인 백엔드 시스템 구축</span>
                  </li>
                  <li class="flex items-start">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-purple-600 dark:text-purple-400 mr-2 mt-0.5 transition-colors duration-300" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"></polyline></svg>
                    <span class="text-gray-700 dark:text-gray-300 transition-colors duration-300">데이터베이스 설계 및 최적화</span>
                  </li>
                  <li class="flex items-start">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-purple-600 dark:text-purple-400 mr-2 mt-0.5 transition-colors duration-300" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"></polyline></svg>
                    <span class="text-gray-700 dark:text-gray-300 transition-colors duration-300">CI/CD 파이프라인 구축 및 자동화</span>
                  </li>
                  <li class="flex items-start">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-purple-600 dark:text-purple-400 mr-2 mt-0.5 transition-colors duration-300" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"></polyline></svg>
                    <span class="text-gray-700 dark:text-gray-300 transition-colors duration-300">Nuxt.js를 활용한 프론트엔드 개발</span>
                  </li>
                </ul>
              </div>
              
              <div class="bg-gray-50 dark:bg-gray-800 p-6 rounded-xl shadow-sm transition-colors duration-300">
                <h3 class="text-xl font-bold text-blue-700 dark:text-blue-400 mb-4 transition-colors duration-300">핵심 가치</h3>
                <div class="flex flex-wrap gap-2">
                  <span class="px-3 py-1 text-sm font-semibold text-purple-700 dark:text-purple-400 bg-purple-100 dark:bg-purple-900/30 rounded-full transition-colors duration-300">혁신적</span>
                  <span class="px-3 py-1 text-sm font-semibold text-blue-700 dark:text-blue-400 bg-blue-100 dark:bg-blue-900/30 rounded-full transition-colors duration-300">사용자 중심</span>
                  <span class="px-3 py-1 text-sm font-semibold text-purple-700 dark:text-purple-400 bg-purple-100 dark:bg-purple-900/30 rounded-full transition-colors duration-300">지속적 학습</span>
                  <span class="px-3 py-1 text-sm font-semibold text-blue-700 dark:text-blue-400 bg-blue-100 dark:bg-blue-900/30 rounded-full transition-colors duration-300">팀 플레이어</span>
                  <span class="px-3 py-1 text-sm font-semibold text-purple-700 dark:text-purple-400 bg-purple-100 dark:bg-purple-900/30 rounded-full transition-colors duration-300">문제 해결사</span>
                  <span class="px-3 py-1 text-sm font-semibold text-blue-700 dark:text-blue-400 bg-blue-100 dark:bg-blue-900/30 rounded-full transition-colors duration-300">디자인 지향</span>
                </div>
                <p class="mt-4 text-gray-700 dark:text-gray-300 transition-colors duration-300">
                  저는 기술적 전문성과 함께 협업 능력, 지속적인 학습 의지, 그리고 사용자 중심의 사고방식을 가지고 있습니다. 
                  복잡한 문제를 해결하고 혁신적인 솔루션을 개발하는 것에 열정을 가지고 있습니다.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    
    <!-- 기술 스택 섹션 -->
    <section class="py-16 bg-gray-50 dark:bg-gray-800 transition-colors duration-300">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-12">
          <h2 class="text-3xl font-bold text-gray-900 dark:text-white transition-colors duration-300">기술 스택</h2>
          <div class="mt-2 h-1 w-20 bg-gradient-to-r from-purple-600 to-blue-600 mx-auto"></div>
        </div>
        
        <div class="grid grid-cols-2 md:grid-cols-4 gap-6 max-w-4xl mx-auto">
          <div class="bg-white dark:bg-gray-900 p-6 rounded-xl shadow-sm hover:shadow-md transition-all duration-300 transform hover:-translate-y-1">
            <div class="w-16 h-16 flex items-center justify-center bg-purple-100 dark:bg-purple-900/30 rounded-full mb-4 mx-auto transition-colors duration-300">
              <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-purple-600 dark:text-purple-400 transition-colors duration-300"><path d="M20 17.58A5 5 0 0 0 18 8h-1.26A8 8 0 1 0 4 16.25"></path><path d="m8 16 4-4 4 4"></path><path d="M16 16v6"></path></svg>
            </div>
            <h3 class="font-bold text-center text-gray-900 dark:text-white transition-colors duration-300">Java</h3>
            <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-2 mt-4 transition-colors duration-300">
              <div class="bg-purple-600 dark:bg-purple-500 h-2 rounded-full transition-colors duration-300" style="width: 90%"></div>
            </div>
            <div class="text-right text-xs text-gray-500 dark:text-gray-400 mt-1 transition-colors duration-300">90%</div>
          </div>
          
          <div class="bg-white dark:bg-gray-900 p-6 rounded-xl shadow-sm hover:shadow-md transition-all duration-300 transform hover:-translate-y-1">
            <div class="w-16 h-16 flex items-center justify-center bg-blue-100 dark:bg-blue-900/30 rounded-full mb-4 mx-auto transition-colors duration-300">
              <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-blue-600 dark:text-blue-400 transition-colors duration-300"><path d="M12 3a6.364 6.364 0 0 0 9 9 9 9 0 1 1-9-9Z"></path><path d="M12 12 7.5 7.5"></path><path d="M16.5 16.5 12 12"></path></svg>
            </div>
            <h3 class="font-bold text-center text-gray-900 dark:text-white transition-colors duration-300">SpringBoot</h3>
            <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-2 mt-4 transition-colors duration-300">
              <div class="bg-blue-600 dark:bg-blue-500 h-2 rounded-full transition-colors duration-300" style="width: 85%"></div>
            </div>
            <div class="text-right text-xs text-gray-500 dark:text-gray-400 mt-1 transition-colors duration-300">85%</div>
          </div>
          
          <div class="bg-white dark:bg-gray-900 p-6 rounded-xl shadow-sm hover:shadow-md transition-all duration-300 transform hover:-translate-y-1">
            <div class="w-16 h-16 flex items-center justify-center bg-purple-100 dark:bg-purple-900/30 rounded-full mb-4 mx-auto transition-colors duration-300">
              <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-purple-600 dark:text-purple-400 transition-colors duration-300"><path d="M2 20h20"></path><path d="M5 4h14a1 1 0 0 1 1 1v10a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V5a1 1 0 0 1 1-1Z"></path></svg>
            </div>
            <h3 class="font-bold text-center text-gray-900 dark:text-white transition-colors duration-300">Nuxt.js</h3>
            <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-2 mt-4 transition-colors duration-300">
              <div class="bg-purple-600 dark:bg-purple-500 h-2 rounded-full transition-colors duration-300" style="width: 75%"></div>
            </div>
            <div class="text-right text-xs text-gray-500 dark:text-gray-400 mt-1 transition-colors duration-300">75%</div>
          </div>
          
          <div class="bg-white dark:bg-gray-900 p-6 rounded-xl shadow-sm hover:shadow-md transition-all duration-300 transform hover:-translate-y-1">
            <div class="w-16 h-16 flex items-center justify-center bg-blue-100 dark:bg-blue-900/30 rounded-full mb-4 mx-auto transition-colors duration-300">
              <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-blue-600 dark:text-blue-400 transition-colors duration-300"><path d="M3 11v3a1 1 0 0 0 1 1h16a1 1 0 0 0 1-1v-3"></path><path d="M12 19H4a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1h-3.5"></path><path d="m12 19 5-5"></path><path d="m17 14-5 5"></path></svg>
            </div>
            <h3 class="font-bold text-center text-gray-900 dark:text-white transition-colors duration-300">MySQL</h3>
            <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-2 mt-4 transition-colors duration-300">
              <div class="bg-blue-600 dark:bg-blue-500 h-2 rounded-full transition-colors duration-300" style="width: 80%"></div>
            </div>
            <div class="text-right text-xs text-gray-500 dark:text-gray-400 mt-1 transition-colors duration-300">80%</div>
          </div>
        </div>
      </div>
    </section>
    
    <!-- 채팅 위젯 -->
    <div class="fixed bottom-6 right-6 z-50">
      <div class="relative">
        <!-- 채팅 버튼 -->
        <button 
          class="flex items-center justify-center w-14 h-14 bg-gradient-to-r from-purple-600 to-blue-600 text-white rounded-full shadow-lg hover:opacity-90 transition-all duration-300 transform hover:scale-105"
          @click="toggleChat"
          type="button"
        >
          <svg v-if="!isChatOpen" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-message-circle"><path d="M21 11.5a8.38 8.38 0 0 1-.9 3.8 8.5 8.5 0 0 1-7.6 4.7 8.38 8.38 0 0 1-3.8-.9L3 21l1.9-5.7a8.38 8.38 0 0 1-.9-3.8 8.5 8.5 0 0 1 4.7-7.6 8.38 8.38 0 0 1 3.8-.9h.5a8.48 8.48 0 0 1 8 8v.5z"></path></svg>
          <svg v-else xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-x"><path d="M18 6 6 18"></path><path d="m6 6 12 12"></path></svg>
        </button>
        
        <!-- 채팅 창 -->
        <div v-if="isChatOpen" class="absolute bottom-16 right-0 w-80 sm:w-96 bg-white dark:bg-gray-900 rounded-xl shadow-2xl border border-gray-200 dark:border-gray-700 overflow-hidden transition-colors duration-300">
          <div class="p-4 bg-gradient-to-r from-purple-600 to-blue-600 text-white">
            <h3 class="font-semibold">실시간 채팅</h3>
            <p class="text-xs text-purple-100">질문이나 문의사항이 있으시면 채팅해주세요</p>
          </div>
          
          <div class="h-80 p-4 overflow-y-auto bg-white dark:bg-gray-900 transition-colors duration-300" ref="chatContainer">
            <div class="space-y-4">
              <!-- 시스템 메시지 -->
              <div class="flex justify-center">
                <span class="text-xs bg-gray-100 dark:bg-gray-800 text-gray-500 dark:text-gray-400 px-2 py-1 rounded-full transition-colors duration-300">오늘</span>
              </div>
              
              <!-- 봇 메시지 -->
              <div class="flex items-start">
                <div class="w-8 h-8 rounded-full bg-purple-100 dark:bg-purple-900/30 flex items-center justify-center text-purple-600 dark:text-purple-400 mr-2 transition-colors duration-300">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-bot"><path d="M12 8V4H8"></path><rect width="16" height="12" x="4" y="8" rx="2"></rect><path d="M2 14h2"></path><path d="M20 14h2"></path><path d="M15 13v2"></path><path d="M9 13v2"></path></svg>
                </div>
                <div class="bg-gray-100 dark:bg-gray-800 rounded-lg p-3 max-w-[80%] transition-colors duration-300">
                  <p class="text-sm text-gray-700 dark:text-gray-300 transition-colors duration-300">안녕하세요! 무엇을 도와드릴까요?</p>
                </div>
              </div>
              
              <!-- 사용자 메시지 예시 -->
              <div v-for="(message, index) in chatMessages" :key="index" class="flex items-start" :class="{'justify-end': message.sender === 'user'}">
                <template v-if="message.sender === 'bot'">
                  <div class="w-8 h-8 rounded-full bg-purple-100 dark:bg-purple-900/30 flex items-center justify-center text-purple-600 dark:text-purple-400 mr-2 transition-colors duration-300">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-bot"><path d="M12 8V4H8"></path><rect width="16" height="12" x="4" y="8" rx="2"></rect><path d="M2 14h2"></path><path d="M20 14h2"></path><path d="M15 13v2"></path><path d="M9 13v2"></path></svg>
                  </div>
                  <div class="bg-gray-100 dark:bg-gray-800 rounded-lg p-3 max-w-[80%] transition-colors duration-300">
                    <p class="text-sm text-gray-700 dark:text-gray-300 transition-colors duration-300">{{ message.text }}</p>
                  </div>
                </template>
                <template v-else>
                  <div class="bg-gradient-to-r from-purple-600 to-blue-600 text-white rounded-lg p-3 max-w-[80%]">
                    <p class="text-sm">{{ message.text }}</p>
                  </div>
                </template>
              </div>
            </div>
          </div>
          
          <div class="p-4 border-t border-gray-200 dark:border-gray-700 bg-white dark:bg-gray-900 transition-colors duration-300">
            <form @submit.prevent="sendMessage" class="flex gap-2">
              <input 
                v-model="newMessage" 
                type="text" 
                placeholder="메시지를 입력하세요..." 
                class="flex-1 px-3 py-2 border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 rounded-md focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-colors duration-300"
              />
              <button 
                type="submit" 
                class="px-3 py-2 bg-gradient-to-r from-purple-600 to-blue-600 text-white rounded-md hover:opacity-90"
              >
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-send"><path d="m22 2-7 20-4-9-9-4Z"></path><path d="M22 2 11 13"></path></svg>
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue';

// 프로필 이미지 URL
defineProps<{
  imageUrl?: string;
}>();

// 채팅 관련 상태
const isChatOpen = ref(false);
const newMessage = ref('');
const chatMessages = ref<Array<{sender: string, text: string}>>([]);
const chatContainer = ref<HTMLElement | null>(null);

// 채팅창 토글 함수
const toggleChat = () => {
  isChatOpen.value = !isChatOpen.value;
  if (isChatOpen.value) {
    nextTick(() => {
      scrollToBottom();
    });
  }
};

// 메시지 전송 함수
const sendMessage = () => {
  if (newMessage.value.trim() === '') return;
  
  // 사용자 메시지 추가
  chatMessages.value.push({
    sender: 'user',
    text: newMessage.value
  });
  
  const userMessage = newMessage.value;
  newMessage.value = '';
  
  // 스크롤 아래로 이동
  nextTick(() => {
    scrollToBottom();
  });
  
  // 봇 응답 시뮬레이션 (실제로는 API 호출 등으로 대체)
  setTimeout(() => {
    chatMessages.value.push({
      sender: 'bot',
      text: getBotResponse(userMessage)
    });
    
    // 스크롤 아래로 이동
    nextTick(() => {
      scrollToBottom();
    });
  }, 1000);
};

// 간단한 봇 응답 생성 함수 (실제로는 서버 API로 대체)
const getBotResponse = (message: string): string => {
  const lowerMessage = message.toLowerCase();
  
  if (lowerMessage.includes('안녕') || lowerMessage.includes('hello')) {
    return '안녕하세요! 무엇을 도와드릴까요?';
  } else if (lowerMessage.includes('프로젝트') || lowerMessage.includes('project')) {
    return '프로젝트에 관심이 있으신가요? 프로젝트 페이지에서 자세한 내용을 확인하실 수 있습니다.';
  } else if (lowerMessage.includes('연락') || lowerMessage.includes('contact')) {
    return '이메일(jumbochips1@gmail.com)로 연락주시면 빠르게 답변드리겠습니다.';
  } else {
    return '더 자세한 내용은 이메일로 문의해주시면 빠르게 답변드리겠습니다.';
  }
};

// 채팅창 스크롤을 아래로 이동시키는 함수
const scrollToBottom = () => {
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
  }
};
</script>

<style scoped>
/* 애니메이션 효과 */
@keyframes float {
  0% { transform: translateY(0px); }
  50% { transform: translateY(-10px); }
  100% { transform: translateY(0px); }
}

.animate-float {
  animation: float 6s ease-in-out infinite;
}
</style>