pipeline {
    agent any

    stages {
        stage ('Fetch source code') {
            steps {
                git (
				branch: 'master',
				credentialsId: 'da61a20e-3f61-4e50-bb53-22aa05285eb4',
				url:'https://gitlab-talentboost.vmware.com/venci362/utms-cli.git'
					)
            }
        }
		stage ('Build') {
            steps {
				sh 'mvn -B -DskipTests clean package' 
            }
		}
    }
	 post {
        always {
            archiveArtifacts artifacts: 'target/*.jar'
			}
		}
}
