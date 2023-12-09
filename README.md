# Read a perfume Backend

## 사용 툴
- Java 17
- Spring Boot
- Spring Data JPA
- QueryDSL
- Spring Security
- Spring Rest Docs
- Flyway
- JUnit5
- Mockito
- Renovate


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

## 로컬 환경 개발 가이드
### PathVariable 값 못 읽어오는 현상
- Spring 6.X 버전을 사용함으로 인해 인텔리제이에서 어플리케이션 구동 시 @PathVariable으로 값을 못읽어오는 현상이 발생할 수 있습니다.
- 이럴 경우에는 인텔리제이에서 `Preferences > Build, Execution, Deployment > Compiler > Java Complier` 에서 `addditional command line parameters`에 `-parameters`를 추가해주세요.

### flyway 주의 사항
- 반드시 대문자 V로 시작해야 합니다.
- 반드시 `__` 두개의 언더바로 구분해야 합니다.
- 버전이 중복되지 않도록 merge 시 주의해주세요. 
