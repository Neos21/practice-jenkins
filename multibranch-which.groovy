pipeline {
  agent any
  stages {
    stage('master ブランチのみ処理する') {
      when {
        branch 'master'
      }
      steps {
        echo 'master ブランチのみ実行します'
      }
    }
    stage('feat/ 始まりのブランチのみ処理する') {
      when {
        branch 'feat/*'
      }
      steps {
        echo 'feat/ 始まりのブランチのみ実行します'
      }
    }
  }
}
