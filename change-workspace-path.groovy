pipeline {
  agent {
    label {
      label ""
      // ワークスペースディレクトリ配下のディレクトリ名を変える
      // ブランチ名に「feat/」が含まれていれば消す。それ以外の「/」は「-」にする
      // 「my-multi-job-【ブランチ名の省略形】-【ビルド番号】」にする
      customWorkspace "${JENKINS_HOME}/workspace/my-multi-job-${BRANCH_NAME.replaceAll("feat/", "").replaceAll("/", "-")}-${BUILD_NUMBER}"
    }
  }
  stages {
    steps {
      echo "${pwd()}"
    }
  }
}
