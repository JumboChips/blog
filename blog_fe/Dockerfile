# Ubuntu 기반 Node.js 22 사용
FROM node:22

# 작업 디렉토리 설정
WORKDIR /app

# 패키지 파일 복사 후 의존성 설치
COPY package.json yarn.lock ./
RUN yarn install

# 전체 프로젝트 복사
COPY . .

# Nuxt 앱 빌드
RUN yarn build

# 포트 노출
EXPOSE 3000

# Nuxt 실행
CMD ["yarn", "start"]
