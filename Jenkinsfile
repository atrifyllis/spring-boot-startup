pipeline {
  agent { label 'docker-maven' }
  stages {
    stage('build') {
      steps {
        sh 'mvn clean install'
      }
    }
  }
}