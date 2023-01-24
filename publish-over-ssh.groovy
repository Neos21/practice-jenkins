sshPublisher(
  publishers: [
    sshPublisherDesc(
      configName: '対象サーバ',
      transfers: [
        sshTransfer(
          excludes: '',
          execCommand: '【実行コマンドを指定した場合はココに入る】',
          execTimeout: 120000,
          flatten: false,
          makeEmptyDirs: false,
          noDefaultExcludes: false,
          patternSeparator: '[, ]+',
          remoteDirectory: '【リモートディレクトリの指定】',
          remoteDirectorySDF: false,
          removePrefix: '【除去するディレクトリパスの指定】',
          sourceFiles: '【転送対象ファイルの指定】'
        )
      ],
      usePromotionTimestamp: false,
      useWorkspaceInPromotion: false,
      verbose: false
    )
  ]
)
