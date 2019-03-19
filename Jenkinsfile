pipeline {
    agent any
    stages {
        stage('build jar') {
            steps {
                withMaven() {
                    sh './mvnw clean install'
                }
            }
        }
        stage('build docker') {
            steps {
                withMaven() {
                    sh './mvnw fabric8:build'
                }
            }
        }
        stage('build kubernetes') {
            steps {
                withMaven() {
                    sh './mvnw fabric8:resource'
                }
                stash name: 'kube-stash', includes: 'target/classes/META-INF/fabric8/**'
            }
        }
        stage('deploy in test') {
            steps {
                unstash 'kube-stash'
                withMaven() {
                    sh '''
                    export KUBERNETES_NAMESPACE=mysql
                    ./mvnw fabric8:apply
                    '''
                }
            }
        }
    }
}