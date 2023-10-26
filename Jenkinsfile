pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = 'dockerhub'
        DOCKER_IMAGE_NAME = 'webdev0594/perfume-backend'
    }

    stages {
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
                    def customImage = docker.build("${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}", "--file Dockerfile.dev.api .")
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry("https://index.docker.io/v1/", DOCKER_HUB_CREDENTIALS) {
                        def customImage = docker.image("${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}")
                        customImage.push("${env.BUILD_NUMBER}")
                        customImage.push("latest")
                    }
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
