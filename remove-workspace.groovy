pipeline {
  agent any
  stages {
    stage('処理あれこれ') {
      steps {
        echo '処理とか'
      }
    }
  }
  post {
    always {
      // 最後に必ずワークスペースを削除する
      deleteDir()
    }
  }
}
