pipeline {
  agent any
  stages {
    stage('エラーが起こりそうな処理') {
      steps {
        script {
          try {
            // ココにエラーが起こりそうな処理を書く
            bat 'ruby -v'
          }
          catch(error) {
            echo 'なにやらエラーです'
          }
          finally {
            echo '成功時も失敗時も実行されます'
          }
        }
      }
    }
  }
}
