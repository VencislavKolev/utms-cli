pipeline {
    agent any

   stages {
        stage("Build") {
            steps {
                sh "mvn -version"
                sh "mvn clean install"
            }
        }
<<<<<<< HEAD
		stage ('Build') {
            steps {
				sh 'mvn -Dmaven.test.failure.ignore=true install'
            }
		}
=======
    }

    post {
        always {
            cleanWs()
        }
>>>>>>> 8f27ad9e0c4f4d1ffccb52e233cd332726af32be
    }
}