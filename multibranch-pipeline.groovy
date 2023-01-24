pipeline {
  agent any
  stages {
    stage('npm install') {
      steps {
        nodejs(configId: '★', nodeJSInstallationName: 'my nodejs') {
          bat 'npm install'
        }
      }
    }
    stage('UT 実行') {
      steps {
        nodejs(configId: '★', nodeJSInstallationName: 'my nodejs') {
          bat 'npm test'
        }
      }
    }
  }
}
