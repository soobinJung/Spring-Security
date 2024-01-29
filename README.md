# Spring Security 

## 목적
- Spring Security 3.2 Test

## 로직
해당 프로젝트에 대한 로직을 간단하게 설명합니다.

### SoobinAuthenticationFailureHandler
- 인증 과정에서 에러가 발생했을 때 실행

### SoobinFilter
- 인증된 jwt 를 가지고 있는지 확인
  
### SoobinSecurityConfig
- 전반적인 Security 설정을 관리

### SoobinTokenProvider
- JWT 의 전반적인 생성/확인/변환 등 관리
  
### SoobinUserDetailsService
- 인증 객체에 대한 정보를 조회 / 확인
  
## Test

### 로그인
![image](https://github.com/soobinJung/Spring-Security/assets/66097044/9349de81-9f24-4045-bad0-40b5ef7e574c)

### 회원가입
![image](https://github.com/soobinJung/Spring-Security/assets/66097044/f8c9c812-5c69-4e71-8874-a13a1175256b)

