// 빌드 시 생성되는 임시 파일들이 저장되는 build 디렉토리를 삭제
tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}