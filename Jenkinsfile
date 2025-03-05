pipeline {
    agent any

    tools {
        jdk 'JDK 17'
        gradle 'Gradle 7.4'
    }

    environment {
        // Define any environment variables here
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Package') {
            steps {
                sh './gradlew bootJar'
            }
        }

        stage('Deploy') {
            steps {
                // Add your deployment steps here
                echo 'Deploying application...'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'build/libs/*.jar', allowEmptyArchive: true
            junit 'build/test-results/test/*.xml'
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}