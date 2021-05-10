pipeline {
    agent any

    stages {
        stage('Fetch') {
            steps {
                  git (
				branch: 'master',
				credentialsId: 'da61a20e-3f61-4e50-bb53-22aa05285eb4',
				url:'https://gitlab-talentboost.vmware.com/venci362/utms-cli.git'
					)
            }
        }
        stage('Build'){
            steps{
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
        }
         stage ('Archive') {
            steps {
                archiveArtifacts 'target/**/*.jar', onlyIfSuccessful: true
            }
        }
    }
}
