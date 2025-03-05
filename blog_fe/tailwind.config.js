/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./components/**/*.{js,vue,ts}",
    "./layouts/**/*.vue",
    "./pages/**/*.vue",
    "./plugins/**/*.{js,ts}",
    "./app.vue",
    "./error.vue",
  ],
  theme: {
    extend: {
      typography: (theme) => ({
        DEFAULT: {
          css: {
            h1: { fontWeight: '700', fontSize: '2.25rem' },
            h2: { fontWeight: '600', fontSize: '1.875rem' },
            h3: { fontWeight: '500', fontSize: '1.5rem' },
            strong: { fontWeight: '700' }, // 한글 폰트 굵기 보정
            'ul > li::before': { backgroundColor: theme('colors.gray.700') }, // 리스트 스타일 개선
          },
        },
      }),
    },
  },
  plugins: [
    require('@tailwindcss/typography'), // typography 플러그인 추가
  ],
}
