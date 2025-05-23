// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2024-11-01',
  app: {
    head: {
      title: 'JumboChips',
      titleTemplate: 'JumboChips - %s',
      meta: [
        { name: 'description', content: 'JumboChips - 웹 개발 포트폴리오와 기술 블로그.' },
      ],
      script: [
        {
          src: `https://www.google.com/recaptcha/api.js`,
          async: true,
          defer: true
        },
      ]
    },
  },
  devtools: { enabled: true },
  css: ['~/assets/main.css'],
  postcss: {
    plugins: {
      tailwindcss: {},
      autoprefixer: {}
    }
  },
  nitro: {
    routeRules: {
      // toast-ui editor 가 SSR 을 지원하지 않아 reload시 에러가 나는것을 방지
      "/blog/post": { ssr: false },
      "/project/post": { ssr: false }
    },
  },
  runtimeConfig: {
    public: {
      apiBaseUrl: process.env.API_BASE_URL || 'https://jumbochips.com', // 클라이언트와 서버에서 모두 접근 가능
      uploadsBaseUrl: process.env.UPLOADS_BASE_URL,
      recaptchaSiteKey: process.env.RECAPTCHA_SITE_KEY || '',
    }
  },
  plugins: ['~/plugins/pinia']

})
