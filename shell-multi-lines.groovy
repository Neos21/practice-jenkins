pipeline {
  agent any
  stages {
    stage('複数行の Windows コマンド') {
      steps {
        // シングルクォート3つで囲む
        bat '''
          Setlocal EnableDelayedExpansion
          some_cmd.bat
          If Not !ERRORLEVEL! == 0 (
            Echo 失敗 !ERRORLEVEL!
          ) Else (
            Echo 成功: !ERRORLEVEL!
          )
        '''
      }
    }
    stage('複数行のシェルスクリプト') {
      steps {
        sh '''
          sh ./some_shell.sh
          echo HogeFuga
        '''
      }
    }
  }
}
