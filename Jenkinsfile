pipeline {
    agent any
    stages {
        stage('sayHello') {
            steps {
                sayHello
                sayHello 'Ernesto'
            }
        }
        stage('deplySingleFileToLinux') {
            sh 'ls -l Jenkins_Pipeline/bounceEAR/jenkins_input_form.txt'
        }
    }
}
