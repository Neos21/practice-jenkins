pipeline {
  agent any
  // 利用ツールを指定する
  tools {
    maven 'maven v3.3.0'
    jdk 'jdk8'
  }
  stages {
    // 通常のパイプラインの時はココで git checkout とかしておく
    stage('Maven インストール・テスト') {
      steps {
        // Windows サーバの場合は「bat」で、Linux サーバとかなら「sh」で
        // 「-Dmaven.test.failure.ignore=true」でテスト失敗を無視する
        bat 'mvn clean install -Dmaven.test.failure.ignore=true'
      }
      post {
        success {
          // JUnit 結果レポートを集計したりとか
          junit 'target/surefire-reports/**/*.xml'
        }
      }
    }
  }
  post {
    always {
      // ワークスペースを削除する
      deleteDir()
    }
  }
}
