#!/bin/bash

# 에러 발생 시 스크립트 중단
set -e

echo "🔄 애플리케이션 배포 시작..."

# 프로젝트 디렉토리 이동
cd ~/project/blog

# 최신 코드 가져오기
echo "📥 최신 코드 가져오는 중..."
git pull origin main

# 백엔드 빌드 & 재시작
echo "🚀 Spring Boot 애플리케이션 빌드 및 재시작"
./gradlew build
sudo systemctl restart my-app.service  # systemd로 관리하는 경우

# 프론트엔드 빌드 & 배포
echo "🌐 프론트엔드 빌드"
cd frontend
npm install
npm run build
pm2 restart nuxt-app  # PM2로 Nuxt 앱 관리하는 경우

echo "✅ 배포 완료!"
