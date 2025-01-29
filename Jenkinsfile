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
                git branch: 'main', url: 'https://github.com/JumboChips/blog.git'
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

        stage('Deploy Backend') {
            steps {
                sh """
                    scp -o StrictHostKeyChecking=no ${BACKEND_DIR}/target/*.jar ${EC2_USER}@${EC2_IP}:/home/ec2-user/app.jar
                    ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_IP} "nohup java -jar /home/ec2-user/app.jar > app.log 2>&1 &"
                """
            }
        }

        stage('Deploy Frontend') {
            steps {
                sh """
                    scp -r -o StrictHostKeyChecking=no ${FRONTEND_DIR}/dist/* ${EC2_USER}@${EC2_IP}:/var/www/html/
                """
            }
        }
    }
}
