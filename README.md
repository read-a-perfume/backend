# Read a perfume Backend

## 개발 환경 준비

### 프로젝트 자바 설치&적용

```shell
jabba install 22.3.1-graalvm=tgz+https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-22.3.1/graalvm-ce-java19-darwin-aarch64-22.3.1.tar.gz
jabba use
```

### 개발 환경 컨테이너 실행

```shell
docker-compose up -d
```

### 서버 실행

```shell
./gradlew :perfume-core:clean :perfume-api:clean :perfume-api:bootRun
```

## 도구 링크

- [Jabba](https://github.com/shyiko/jabba)
- [Docker](https://www.docker.com/)
- [IntelliJ](https://www.jetbrains.com/idea/)
