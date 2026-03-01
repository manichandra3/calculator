pipeline {
    agent any

    tools {
        maven 'Maven'   // Name must match the Maven installation configured in Jenkins
        jdk   'JDK17'   // Name must match the JDK installation configured in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                echo '=== Checking out source code ==='
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                echo '=== Compiling the project ==='
                sh 'mvn compile'
            }
        }

        stage('Test') {
            steps {
                echo '=== Running unit tests ==='
                sh 'mvn test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Build') {
            steps {
                echo '=== Packaging the application ==='
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Execute') {
            steps {
                echo '=== Running the Calculator application ==='
                sh 'java -jar target/calculator-1.0-SNAPSHOT.jar'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check the logs for details.'
        }
        always {
            cleanWs()
        }
    }
}
