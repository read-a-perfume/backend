# Read a perfume Backend

## 💭 서비스 소개
Read A Perfume은 향수에 대한 리뷰를 남길 수 있는 플랫폼으로, 사용자의 리뷰를 기반으로 향수가 어떤지 한눈에 확인하고 더 나은 향수 선택을 할 수 있도록 하면 어떨까 라는 생각에서 시작되었습니다. 🍾

다양한 향수를 내 취향에 맞는 카테고리 별로 살펴보고, 다양한 사용자가 작성한 향수에 대한 리뷰로 합리적인 향수를 구매해보아요!

## ⚒️ 사용 툴
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

## 🎞️ ERD
![erd](https://github.com/read-a-perfume/backend/assets/56705221/32312877-d92d-4289-85c4-f6f6ace363be?size=300)


## 🏃🏻‍♀️ 실행 방법

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

## ✅ CI/CD

![image](https://github.com/read-a-perfume/backend/assets/72547111/e33f6a85-bfc0-43df-ae67-f4a591be2ff9)

## ⚠️ 로컬 환경 개발 가이드
### PathVariable 값 못 읽어오는 현상
- Spring 6.X 버전을 사용함으로 인해 인텔리제이에서 어플리케이션 구동 시 @PathVariable으로 값을 못읽어오는 현상이 발생할 수 있습니다.
- 이럴 경우에는 인텔리제이에서 `Preferences > Build, Execution, Deployment > Compiler > Java Complier` 에서 `addditional command line parameters`에 `-parameters`를 추가해주세요.

### flyway 주의 사항
- 반드시 대문자 V로 시작해야 합니다.
- 반드시 `__` 두개의 언더바로 구분해야 합니다.
- 버전이 중복되지 않도록 merge 시 주의해주세요. 
