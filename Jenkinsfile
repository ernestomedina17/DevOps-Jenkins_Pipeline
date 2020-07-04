import com.company.department.publish.SSHServer

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
                sh "echo 'foo=var' > directoryA/directoryB/config.props"
                sh 'ls -l directoryA/directoryB/config.props'
                //script {   
                    //uploadSingleFileToLinux()
                //}
            }
        }
    }
}
