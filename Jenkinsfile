pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = credentials('dockerhub')
        DOCKERFILE_PATH = 'Dockerfile.dev.api'
        DOCKER_IMAGE_NAME = 'webdev5094/perfume-backend'
        DOCKER_IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './gradlew :perfume-core:clean :perfume-api:clean :perfume-api:build'
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS, usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                        sh "docker login -u $DOCKER_HUB_USERNAME -p $DOCKER_HUB_PASSWORD"
                    }

                    sh "docker build -f $DOCKERFILE_PATH -t $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG ."

                    sh "docker push $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG"
                }
            }
        }
    }

    post {
        success {
            script {
                sh 'docker logout'
            }
        }
    }
}
