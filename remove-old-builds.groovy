pipeline {
  agent any
  // 以下の options ブロックを追加する
  options {
    // ビルドの保存最大数を 3 件に設定する
    buildDiscarder(logRotator(numToKeepStr: '3'))
  }
  // …(以下略)…
}
