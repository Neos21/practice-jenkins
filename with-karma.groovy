pipeline {
  // エージェント : 「any」で良い
  agent any
  // ビルドトリガ
  triggers {
    bitbucketPush()  // 「Build when a change is pushed to BitBucket」相当 (Bitbucket との連携時)
    pollSCM('')  // 「SCM をポーリング」相当
  }
  options {
    // ビルドの保存最大数を設定する
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  // 変数定義
  environment {
    // チェックアウトするブランチ名 : ココでは develop ブランチでテストするテイ
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
    stage('インストール') {
      steps {
        nodejs(configId: '【.npmrc ファイル】', nodeJSInstallationName: '【Node.js バージョン】') {
          bat 'npm install'
        }
      }
    }
    stage('テスト実行') {
      steps {
        nodejs(configId: '【.npmrc ファイル】', nodeJSInstallationName: '【Node.js バージョン】') {
          bat 'npm test -- --single-run'
        }
      }
      post {
        // UT が失敗しても必ず実行する
        always {
          // JUnit 結果集計を行う
          junit 'coverage/junit-report.xml'
          
          // Cobertura カバレッジレポートを出力する
          cobertura autoUpdateHealth: false,
                    autoUpdateStability: false,
                    coberturaReportFile: 'coverage/cobertura-coverage.xml',
                    conditionalCoverageTargets: '70, 0, 0',
                    failUnhealthy: false,
                    failUnstable: false,
                    lineCoverageTargets: '80, 0, 0',
                    maxNumberOfBuilds: 0,
                    methodCoverageTargets: '80, 0, 0',
                    onlyStable: false,
                    zoomCoverageChart: false
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
