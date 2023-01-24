pipeline {
  // エージェント : 「any」で良い
  agent any
  // ビルドトリガ : ジョブ設定画面の「ビルドトリガ」で選択したものと同じトリガを念のため記述しておく
  triggers {
    bitbucketPush()  // 「Build when a change is pushed to BitBucket」相当
    pollSCM('')  // 「SCM をポーリング」相当
  }
  options {
    // ビルドの保存最大数を設定する
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  // 変数定義
  environment {
    // チェックアウトするブランチ名を指定する
    BRANCH = 'develop'
    // プロジェクト・リポジトリ URL
    GIT_URL = 'ssh://git@【プロジェクト・リポジトリ URL】.git'
    // リポジトリ・ブラウザ URL
    BROWSER_URL = 'http://【Bitbucket リポジトリ・ブラウザ URL】/browse'
  }
  stages {
    stage('Git チェックアウト') {
      steps {
        // Push を監視しチェックアウトする
        checkout poll: true,
                 scm: [
                   $class: 'GitSCM',
                   branches: [[name: "origin/${BRANCH}"]],
                   browser: [
                     $class: 'BitbucketWeb',
                     repoUrl: "${BROWSER_URL}"
                   ],
                   doGenerateSubmoduleConfigurations: false,
                   extensions: [],
                   submoduleCfg: [],
                   userRemoteConfigs: [[
                     credentialsId: '【認証情報】',
                     url: "${GIT_URL}"
                   ]]
                 ]
      }
    }
    stage('テスト実行') {
      steps {
        // ここでは npm パッケージのテストを実行するテイ
        nodejs(configId: '【.npmrc ファイル】', nodeJSInstallationName: '【利用する Node.js】') {
          // Jenkins サーバの OS に応じて「bat」か「sh」を使用する
          bat 'npm install'
          bat 'npm test'
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
