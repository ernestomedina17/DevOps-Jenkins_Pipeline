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
                sh 'mkdir -p directoryA/directoryB/'
                sh "echo 'foo=var' > config.props"
                sh 'ls -l directoryA/directoryB/config.props'
            }
        }
    }
}
