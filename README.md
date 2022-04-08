# Hello-Spring
기본적인 자바스프링을 이용한 프로젝트
## 1. CRUD 만들기
User 도메인은 다음과 같습니다  
```java
public class Member {
    private long id;
    private String name;
    private Integer age;
}
```
### Qustions
위의 도메인을 사용하여 Controller, Service, Repository를 생성하고 CRUD 기능을 구현합니다

Repository는 Map을 사용하여 만듭니다
```text
- 유저 생성
- 유저 검색
- 유저 수정
- 유저 삭제
```
```java
@Repository
interface UserRepository{
    void createUser(member : Member);
    void updateUser(member : Member);
    Member getUser(id: long);
    void deleteUser(id: long);
}
```

---
## 2. 데이터베이스를 사용하여 CRUD 구현
1번에서 만든 내용에 UserRepository를 데이터베이스(H2)에 연결하여 사용합니다
```text
1. application.properties 또는 application.yml을 사용하여 데이터베이스 설정을 합니다
2. H2 데이터베이스를 In-Memory로 사용합니다
```
```java
/**
 * build.gradle에 아래 디펜던시를 추가합니다
 */
dependencies {
    ...
	// in-memory h2
	runtimeOnly 'com.h2database:h2'
    ...
}
```
아래 설정파일을 사용하여 h2 설정을 합니다
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:test
    driver-class-name: org.h2.Driver
    username: sa
  h2:
    console:
      enabled: true
```
---
## 3. 환경설정(profile) 파일을 다룹니다
application.properties 또는 application.yml을 사용하여 환경설정을 작성합니다.  
Spring boot framework는 아래 설정한 내용을 기반으로 기본설정을 적용합니다  
**properties**
```properties
# 서버 기본 포트 설정
server.port=8080
# 서버 루트 경로 지정 
server.servlet.context-path=/api/v1
```
**yaml**
```yaml
server:
  port: 8080
  servlet:
    context-path: /api/v1  
```
런타입 환경변수를 아무것도 설정하지 않으면 `profile`을 `default`로 확인하여 application.properties 또는 yml을 기본으로 사용합니다

### 환경별(local, dev, prod) 환경설정
`application-{환경별}`을 사용하면 각 환경에 맞게 설정할 수 있습니다

이때 `application`은 무조건 읽고 그 이후 `application-{환경별}`을 읽습니다. 동일한 pah의 설정이 있다면 이후에 읽어진 환경파일에 의해 overwrite가 됩니다
```text
# local이라는 환경파일을 쓰고 싶다면 아래 순서와 같이 읽어옵니다
1. application.properties 또는 yaml
2. application-local.properties 또는 yaml
```
### Questions
환경설정파일 `local`과 `dev`를 만들고 아래와 같이 각 환경마다 다른 `port`와 `context`를 가지게 작성하세요
```text
- local의 포트는 8081이며 context-path는 /api/v1/local 입니다 
- dev의 포트는 8082이며 context-path는 /api/v1/dev 입니다
```
### Answer
환경 변수 적용 하는 방법 2가지를 찾았습니다. 
```text
1. 현재 적용 된 방법
apoplication 파일을 복사 하여 3가지 형태(dev,prod,local)로 생성
ide에서 설정을 바꿔 직접 사용 파일명 지정
 community version
  - Edit configration -> modify option -> add vm option,[활성화] -> -Dspring.profiles.active=[환경별]  
 Ultimate verion 
  - Edit configration -> action proifle -> application[환경별]

?? ide에서 설정한 application 환경별 값이 운영 서버에 등록 할때도 같이 반영이 됩니까?
?? context-path 변경 하면 index page는 연결 되는데 그 하위 페이지는 다 오류 납니다. 이건 자동으로 되는건데 안된건가요? 아니면 다른 설정이 필요 할까요?



2. 찾았지만 확인이 안된 방법
build.gradle 내용추가
 ext.profile = (!project.hasProperty('pfofile') || ! profile) ? 'dev' : profile
 sourceSets {
          main { 
             resoucres {
                     srcDirs " src/main/resources", " src/main/resources-${profile}"
                     }
            }
          }   
}

Gradle 실행
 gradle clean bootRun
 gradle clean bootRun -profile=dev  
 gradle clean bootRun -pprofile=prod
 
ps. dev port 8083은 전에 톰캣 따로 설치 햇을때 포트를 8082로 정해 둔거 같습니다. 포트 변경 하는 방법을 잊어서 부득이하게 변경 햇씁니다.  
```

