## 0. Getting Started (시작하기)
```bash
$ ./gradlew clean build 
$ docker compose up --build -d
```

<br/>

서비스 링크 (준비 중...) 
----> https://daengle.com  <----

<br/>
<br/>

## 🧑‍💻 1. Project Overview (프로젝트 개요)
- 프로젝트 이름: Daengle
- 프로젝트 설명: 강아지 미용 중계 플랫폼

<br/>
<br/>

## 👥 2. Team Members (팀원 및 팀 소개)
| 진명인 | 백효석 | 심지혜 | 
|:------:|:------:|:------:|
| <img src="https://avatars.githubusercontent.com/myeonginjin" alt="진명인" width="150"> | <img src="https://avatars.githubusercontent.com/alexization" alt="백효석" width="150"> | <img src="https://avatars.githubusercontent.com/sapientia1007" alt="심지혜" width="150">
| BE | BE | BE | 
| [GitHub](https://github.com/myeonginjin) | [GitHub](https://github.com/alexization) | [GitHub](https://github.com/sapientia1007) 
<br/>
<br/>

## 🔨 3. Tasks & Responsibilities (작업 및 역할 분담)

| 팀원  | 사진 | 역할 |
|-----------------|-----------------|-----------------|
| 진명인    |  <img src="https://avatars.githubusercontent.com/myeonginjin" alt="진명인" width="100"> | <ul><li>인프라 구축</li><li>페이먼츠 시스템</li><li>금칙어 필터링 시스템</li></ul>     |
| 백효석   |  <img src="https://avatars.githubusercontent.com/alexization" alt="백효석" width="100">| <ul><li>인증/인가 시스템</li><li>미용 견적 입찰 시스템</li> |
| 심지혜   |  <img src="https://avatars.githubusercontent.com/sapientia1007" alt="심지혜" width="100">    |<ul><li>실시간 채팅 시스템</li><li>실시간 화상 시스템</li></ul>  |

<br/> 
<br/>

## 💻 4. Technology Stack (기술 스택)

**실시간 화상 시스템**
<br/>
<img width="500" alt="architecture" src="https://github.com/user-attachments/assets/b1e175bd-b958-41b6-8124-1ea2b987fb5d">
<br />
- WebRTC를 활용한 실시간 양방향 통신
- Signaling Server를 활용해 클라이언트간의 연결 정보 전달
- STUN Server를 통해 클라이언트간의 IP 정보 전달
- TURN Server를 통해 클라이언트간의 서버를 중계
  
**실시간 채팅 시스템**

<br />
<img width="500" alt="architecture" src="https://github.com/user-attachments/assets/98571420-3519-4936-a397-a9e49942222b">
<br />

- WebSocket(STOMP) + SockJS를 활용한 실시간 양방향 통신
- WebSocket 위에서 STOMP를 활용해, 다양한 시스템 간 상호 운용성을 보장하며 비동기 처리로 여러 구독자에게 동시에 메시지 전송 가능
- SockJS를 활용해 연결이 끊어졌을 때 자동으로 재연결 시도하며 안정적인 통신 보장
