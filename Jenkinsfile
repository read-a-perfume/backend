pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = 'dockerhub'
        DOCKER_REPOSITORY_NAME = 'webdev0594'
        MANIFEST_REPOSITORY_PATH = 'github.com/read-a-perfume/gitops.git'
    }

    stages {
        stage('Initialize') {
            steps {
                script {
                    env.GIT_COMMIT_HASH = sh(returnStdout: true, script: 'git rev-parse HEAD | cut -c 1-10').trim()
                    env.DOCKER_IMAGE_NAME = "${DOCKER_REPOSITORY_NAME}/perfume-backend"
                }
            }
        }

        stage('Clone Repository') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './gradlew :perfume-core:clean :perfume-api:clean :perfume-api:build'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def customImage = docker.build("${DOCKER_IMAGE_NAME}:${env.GIT_COMMIT_HASH}", "--file Dockerfile.dev.api .")
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry("https://index.docker.io/v1/", DOCKER_HUB_CREDENTIALS) {
                        def customImage = docker.image("${env.DOCKER_IMAGE_NAME}:${env.GIT_COMMIT_HASH}")
                        customImage.push("${env.GIT_COMMIT_HASH}")
                        customImage.push("latest")
                    }
                }
            }
        }

        stage('Edit manifest image version') {
            steps {
                withCredentials([string(credentialsId: 'github', variable: 'TOKEN')]) {
                    sh("""
                        rm -rf manifest
                        git clone https://dygma0:$TOKEN@$MANIFEST_REPOSITORY_PATH manifest
                        cd manifest
                        sed -i 's/perfume-backend:.*/perfume-backend:${env.GIT_COMMIT_HASH}/g' perfume-backend.yaml
                        git config --global user.email "404err@naver.com"
                        git config --global user.name "dygma0"
                        git add .
                        git status
                        git commit -m "Update image version to ${env.GIT_COMMIT_HASH}"
                        git push origin HEAD:main
                    """)
                }
            }
        }
    }

    post {
        always {
            script {
                sh 'docker logout'
            }
        }
    }
}
