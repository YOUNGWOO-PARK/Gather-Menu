# Gather-Menu
------------
## 프로젝트 요약
> 같은 와이파이 내에서 팀원들과 함께 음식을 시킬 때 메뉴를 취합하는 텍스트 기반 웹 어플리케이션.

## 코드 설명
-----------
SaveText는 서버에서 취합한 메뉴를 텍스트파일로 저장하는 기능을 담당.

server는 서버의 실행코드.

User는 웹으로 접속한 유저의 정보를 처리하는 클래스로  Memory 클래스 및 IPManagement클래스의 저장정보를 가지고 있음.

## 기능설명
------------
1. 서버를 실행하면 음식점 이름과 서버의 유지시간을 입력받음
2. 입력을 완료하면 서버가 생성됨과 동시에 서버의 IP주소와 PORT번호를 출력함
 <img width="394" alt="시작" src="https://user-images.githubusercontent.com/77572677/129447750-82f62d8b-6a5a-480c-b954-17c9d33e8f0f.PNG">

3. IP주소와 PORT번호를 웹페이지에 입력해 접속 함.
4. 사용자에게 메뉴번호를 받음으로써 서버는 각 IP별로 사용자의 메뉴를 관리함.
5. IP별로 관리하기 때문에 IP당 하나의 음식을 주문, 변경, 취소 가능
6. 서버는 자동저장을 제공 및 서버의 유지시간이 끝나면 저장을 완료하고 서버 종료.
<img width="564" alt="결과" src="https://user-images.githubusercontent.com/77572677/129447802-704c66db-9ca8-44f3-90f2-646f45fd6260.PNG">

## 사용법
------------
사용자는 웹페이지에 서버의 IP와 포트번호를 입력해 서버에 접속함

그 후 자신이 원하는 메뉴의 메뉴번호를 서버의 IP와 포트번호에 추가해 서버로 보냄

사용자는 자신의 메뉴를 주문, 변경, 취소 가능.

<img width="1143" alt="사용자 화면" src="https://user-images.githubusercontent.com/77572677/129447787-89e67634-9e81-4a99-ac93-33bed594d187.PNG">

