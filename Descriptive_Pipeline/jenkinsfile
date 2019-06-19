pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'echo "Hello World"'
                sh '''
                    echo "Multiline shell steps"
                    ls -lah
                '''
            }
        }
        stage('Unit Test') {
            steps {
                echo 'Unit Testing With JUnit'
            }
        }
        stage('Deploy') {
            steps {
                timeout(time: 20, unit: 'MINUTES') {
                    retry(3) {
                        println "Try to Deploy"
                    }
                }
            }
        }
        stage('UI Tests') {
            parallel {
                stage('Selenium Chrome') {
                    steps {
                        echo "Chrome Tests With WebDriver"
                        sleep 30
                    }
                }
                stage('Selenium Firefox') {
                    steps {
                        echo "Firefox Tests With WebDriver"
                        sleep 30
                    }
                }
            }
        }
    }
}
