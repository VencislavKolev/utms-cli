pipeline {
    agent any

    stages {
        stage ('Clone source code') {
            steps {
                git 'https://gitlab-talentboost.vmware.com/venci362/utms-cli.git'
            }
        }

        stage ('Build') {
            steps {
                script {
                    sh 'mvn clean install'
                }
            }
        }

        stage ('Archive') {
            steps {
                archiveArtifacts 'target/**/*.jar'
            }
        }
    }
}