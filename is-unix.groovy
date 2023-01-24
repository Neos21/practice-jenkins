pipeline {
  agent any
  stages {
    stage('Unix 系 OS 判定') {
      steps {
        script {
          if(isUnix()) {
            sh 'ls'
          }
          else {
            bat 'dir'
          }
        }
      }
    }
  }
}
