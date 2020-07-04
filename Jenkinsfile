pipeline {
    agent any
    stages {
        stage('sayHello') {
            steps {
                script {
                    sayHello()
                    sayHello('Ernesto')
                }
            }
        }
        stage('deplySingleFileToLinux') {
            steps {
                sh 'ls -l Jenkins_Pipeline/bounceEAR/jenkins_input_form.txt'
            }
        }
    }
}
