// Declarative Pipeline 記法。コメントはスラッシュ2つで単一行
/* これで複数行コメントも可能 */
pipeline {
  // ジョブを実行するエージェントを指定 : 特になければ any で
  agent any
  stages {
    stage('Git チェックアウト') {
      steps {
        // Credentials ID は「Pipeline Syntax」より作成すると自動的に生成してくれる
        // master ブランチ以外を指定する際は「branch:」オプションを付けて書く
        git credentialsId: '★', url: 'ssh://git@example.com/example.git'
      }
    }
    stage('npm install') {
      steps {
        // Node.js を使用する際は別途環境定義しておき、それを指定する
        // これも「Pipeline Syntax」から生成すると良い
        nodejs(configId: '★', nodeJSInstallationName: 'my nodejs') {
          // Jenkins を Windows Server に立てている場合、Git Bash を入れてあっても「sh」が上手く動作しなかった
          // 仕方がないので「bat」で動かしている
          bat 'npm install'
          // 強引に GitBash を動かすには以下のようにすれば良いが標準出力が取得できない
          bat(returnStdout: true, script: '"C:/Program Files/Git/bin/bash.exe" -xec "ls"')
          // Linux サーバ上に立てている場合は「sh」で良い
          // sh 'npm install'
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
