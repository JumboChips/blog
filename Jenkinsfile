pipeline {
    agent any

    environment {
        BACKEND_DIR = "blog_be"
        FRONTEND_DIR = "blog_fe"
        EC2_USER = "ec2-user"
        EC2_IP = "your-ec2-ip"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    def branch = env.GIT_BRANCH ?: 'unknown'
                    echo "현재 브랜치: ${branch}"
                    git branch: '*/dev', url: 'https://github.com/JumboChips/blog.git'
                }
            }
        }

        stage('Build Backend') {
            steps {
                dir(BACKEND_DIR) {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir(FRONTEND_DIR) {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Test') {
            steps {
                echo "Running tests..."
                dir(BACKEND_DIR) {
                    sh 'mvn test'
                }
                dir(FRONTEND_DIR) {
                    sh 'npm test'
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'main'  // 🚀 main 브랜치에서만 배포 실행
            }
            steps {
                sh """
                    scp -o StrictHostKeyChecking=no ${BACKEND_DIR}/target/*.jar ${EC2_USER}@${EC2_IP}:/home/ec2-user/app.jar
                    ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_IP} "nohup java -jar /home/ec2-user/app.jar > app.log 2>&1 &"
                """
                sh """
                    scp -r -o StrictHostKeyChecking=no ${FRONTEND_DIR}/dist/* ${EC2_USER}@${EC2_IP}:/var/www/html/
                """
            }
        }
    }
}
