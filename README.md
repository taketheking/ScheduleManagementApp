# 일정 관리 앱 서버 구축하기

## 프로젝트 소개
사용자가 입력한 일정을 날짜 별로 저장하고 조회할 수 있는 기능을 하는 앱의 서버를 구축하는 프로젝트이다.

해당 프로젝트는 사용자 입력을 받는 앱이 있다는 가정하에 일정 생성, 일정 전체 조회, 일정 단건 조회, 일정 수정, 일정 삭제 등의 기능을 하는 서버이다.


## 개발 환경

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

IDE : Intellij IDEA 2024.2.3

언어 : Java

프레임 워크 : 스프링 부트 3.3.5

JDK : corretto-17.0.13

RDBMS : MYSQL_Server 9.1.0 

버전관리도구 : GitHub


## 목표
프로젝트 개발 프로세스 전반의 흐름을 이해하고, 스프링 MVC를 적용하여 프로젝트를 개발하자.

### 개발 프로세스

1. 요구사항 분석(파악)

2. 시스템 명세(화면 구성 및 API 스펙 협의)

3. 시스템 설계(프로토타입 및 API 명세서 작성)

4. 프로젝트 구현

5. 테스트

6. 배포

7. 유지보수


## 1. 요구사항 분석
### 1) 일정 생성(작성)하기
  - 일정 생성시 {할일, 작성자명, 비밀번호, 작성일, 수정일} 데이터가 일정 테이블에 저장
  - 작성일/수정일은 날짜와 시간을 모두 포함
  - 각 일정은 고유 식별자(ID)를 자동으로 생성하여 관리
  - 최초 생성시 작성일 = 수정일

### 2) 전체 일정 조회
 - 조건
     - 수정일 (형식 : YYYY-MM-DD)
     - 작성자명
 - 각 조건의 값이 있을 수도 있고 없을 수도 있음
 - 수정일 기준으로 내림차순 정렬하여 조회

### 3) 선택 일정 조회
 - 일정의 고유 식별자(ID)를 사용하여 선택한 일정의 정보를 조회

### 4) 선택 일정 수정
 - 선택한 일정의 할일, 작성자명 만 수정 가능
 - 일정 수정 요청시 비밀번호도 같이 전달
 - 작성일은 변경 불가 / 수정일은 수정 완료 시, 수정 완료된 시점으로 변경
 - 
 ### 5) 선택 일정 삭제
 - 선택 일정 삭제 가능
 - 일정 삭제 요청시 비밀번호 전달

### 6) 작성자 정보를 테이블로 분리
 - 작성자는 {이름, 이메일, 등록일, 수정일} 데이터 존재
 - 정보 데이터 추가 가능
 - 고유 식별자 존재(ID) - 자동 생성
 - 작성자 고유 식별자을 일정 테이블의 외래키로 설정

### 7) 페이지네이션
 - 전체 일정 조회 기능에서 페이지 번호와 크기를 기준을 추가하여 모두 조회
 - 조회한 일정 목록에는 작성자 이름 포함
 - 범위를 넘어선 페이지 요청시에는 빈 배열 반환
 - Paging 객체 활용 가능

### 8) 예외처리
 - 수정 및 삭제 요청 시 비밀번호 불일치 시 예외 발생
 - 선택한 일정 정보를 조회할 수 없을 시 예외 발생
   - 잘못된 정보로 조회할 때
   - 이미 삭제된 정보를 조회할 때
 - 사용자 정의 예외 클래스 생성 가능
 - @ExceptionHandler 활용 가능
 - 적절한 HTTP 상태 코드와 에러 메세지 전달

### 9) null 체크 및 특정 패턴에 대한 검증 수행
 - 유효성 검사
     - 잘못된 요청 및 입력을 미리 방지
     - 데이터 무결성 보장 및 예측 가능성을 높임
 - @Valid 사용가능
 - 데이터 조건
     - 할일 : 최대 200자 제한, 필수값 처리
     - 비밀번호 : 필수값 처리
     - 이메일 : 이메일 형식 확인


## 2. API 명세서

<table>
    <tr>
      <th scope="col">기능</td>
      <th scope="col">Method</td>
      <th scope="col">URL</th>
      <th scope="col">Request</td>
      <th scope="col">Response</td>
      <th scope="col">상태코드</td>
    </tr>
    <tr>
      <td>일정 생성</td>
      <td>POST</td>
      <td>/api/schdules</td>
      <td>요청 body</td>
      <td>등록된 데이터 정보</td>
      <td>200: 정상 등록, 400: 잘못된 값 입력</td>
    </tr>
    <tr>
      <td>전체 일정 조회</td>
      <td>GET</td>
      <td>/api/schdules</td>
      <td>요청 params</td>
      <td>조건에 맞는 데이터 정보들</td>
      <td>200: 정상 등록, 400: 잘못된 값 입력</td>
    </tr>
    <tr>
      <td>단건 일정 조회</td>
      <td>GET</td>
      <td>/api/schdules/{id}</td>
      <td>요청 param</td>
      <td>요청한 데이터 정보</td>
      <td>200: 정상 등록, 404: 해당 데이터 존재하지 않음</td>
    </tr>
    <tr>
      <td>단건 일정 수정</td>
      <td>PATCH</td>
      <td>/api/schdules/{id}</td>
      <td>요청 param, 요청 body</td>
      <td>수정된 데이터 정보</td>
      <td>200: 정상 등록, 400: 잘못된 값 입력, 404: 해당 데이터 존재하지 않음</td>
    </tr>
    <tr>
      <td>단건 일정 삭제</td>
      <td>Delete</td>
      <td>/api/schdules/{id}</td>
      <td>요청 param, 요청 body</td>
      <td>없음</td>
      <td>200: 정상 등록, 400: 잘못된 값 입력, 404: 해당 데이터 존재하지 않음</td>
    </tr>
  </table>



<details>
<summary>일정 생성</summary>
  
 - 설명 : 일정을 하나 생성합니다.
  
 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>localhost:8080/schdules</td>
    </tr>
  </table>

 - 예제
   - **요청** : JSON
    ```
      {
      "name" : "박스파르타",
      "email" : "sparta@teamsparta.com",
      "pw" : "1234",
      "schedule" : "숙제하기"
      }
    ```
   - **응답**
     
   HTTP/1.1 201 Created
   ```
     {
      "id": 37,
      "name": "박스파르타",
      "schedule": "숙제하기",
      "modifyDate": "2024-11-06T17:01:00"
      }
    ```

 - 본문
   - **요청**
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>name</td>
      <td>String</td>
      <td>O</td>
      <td>작성자명</td>
    </tr>
     <tr>
      <td>email</td>
      <td>String</td>
      <td>O</td>
      <td>작성자 email</td>
    </tr>
     <tr>
      <td>pw</td>
      <td>String</td>
      <td>O</td>
      <td>비밀번호</td>
    </tr>
    <tr>
      <td>schedule</td>
      <td>String</td>
      <td>O</td>
      <td>일정 내용</td>
    </tr>
  </table>
  
   - **응답**
  <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 테이블의 고유 식별자 아이디</td>
    </tr>
    <tr>
      <td>name</td>
      <td>String</td>
      <td>O</td>
      <td>작성자명</td>
    </tr>
    <tr>
      <td>schedule</td>
      <td>String</td>
      <td>O</td>
      <td>일정 내용</td>
    </tr>
    <tr>
      <td>modifyDate</td>
      <td>DateTime</td>
      <td>O</td>
      <td>최종 수정일</td>
    </tr>
  </table>
</details>

<details>
<summary>전체 일정 조회</summary>
  
 - 설명 : 조건에 맞는 일정을 모두 조회합니다.
  
 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>GET</td>
      <td>localhost:8080/schdules</td>
    </tr>
  </table>

 - 예제
   - **요청** : QueryParams
    ```
    {
      ?name=&date=2024-11-06&page=1&size=10
    }
    ```
   - **응답**
     
   HTTP/1.1 200 OK
   ```
     [
    {
        "id": 36,
        "name": "박스파르타",
        "schedule": "숙제하기",
        "modifyDate": "2024-11-06T15:25:11"
    },
    {
        "id": 35,
        "name": "김아무개",
        "schedule": "상담하기",
        "modifyDate": "2024-11-06T15:24:44"
    },
    {
        "id": 34,
        "name": "홍길동",
        "schedule": "공부하기",
        "modifyDate": "2024-11-06T15:21:04"
    }
]
    ```

 - 본문
   - **요청** : Query Params
   <table>
    <tr>
      <th scope="col">키 이름</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">기본값</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>name</td>
      <td>String</td>
      <td>X</td>
      <td></td>
      <td>작성자명</td>
    </tr>
     <tr>
      <td>date</td>
      <td>String</td>
      <td>X</td>
       <td></td>
      <td>수정일</td>
    </tr>
     <tr>
      <td>page</td>
      <td>int</td>
      <td>X</td>
       <td>1</td>
      <td>현재 페이지</td>
    </tr>
    <tr>
      <td>size</td>
      <td>int</td>
      <td>X</td>
      <td>10</td>
      <td>한 페이지 내의 일정 개수</td>
    </tr>
  </table>
  
   - **응답** : List
  <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 테이블의 고유 식별자 아이디</td>
    </tr>
    <tr>
      <td>name</td>
      <td>String</td>
      <td>O</td>
      <td>작성자명</td>
    </tr>
    <tr>
      <td>schedule</td>
      <td>String</td>
      <td>O</td>
      <td>일정 내용</td>
    </tr>
    <tr>
      <td>modifyDate</td>
      <td>DateTime</td>
      <td>O</td>
      <td>최종 수정일</td>
    </tr>
  </table>
</details>

<details>
<summary>선택 일정 조회</summary>
   
 - 설명 : 일정을 하나 조회합니다.
  
 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>localhost:8080/schdules/{id}</td>
    </tr>
  </table>

 - 예제
   - **요청** : PathVariable
    ```
      {
        34
      }
    ```
   - **응답**
     
   HTTP/1.1 200 OK
   ```
     {
    "id": 34,
    "name": "홍길동",
    "schedule": "공부하기",
    "modifyDate": "2024-11-06T15:21:04"
    }
    ```

 - 본문
   - **요청** : PathVariable
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 고유 식별자 ID</td>
    </tr>
  </table>
  
   - **응답**
  <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 테이블의 고유 식별자 아이디</td>
    </tr>
    <tr>
      <td>name</td>
      <td>String</td>
      <td>O</td>
      <td>작성자명</td>
    </tr>
    <tr>
      <td>schedule</td>
      <td>String</td>
      <td>O</td>
      <td>일정 내용</td>
    </tr>
    <tr>
      <td>modifyDate</td>
      <td>DateTime</td>
      <td>O</td>
      <td>최종 수정일</td>
    </tr>
  </table>
</details>

<details>
<summary>선택 일정 수정</summary>

 - 설명 : 일정을 하나 수정합니다.

 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>PATCH</td>
      <td>localhost:8080/schdules/{id}</td>
    </tr>
  </table>

 - 예제
   - **요청** : PathVariable + JSON
    ```
    // PathVariable
    {
      34
    }

    // requestBody 내의 JSON
    {
      "name" : "스파르타",
      "schedule" : "상담말고 산책하기",
      "pw" : "1234"
    }
    ```
   - **응답**
     
   HTTP/1.1 200 OK
   ```
     {
      "id": 34,
      "name": "스파르타",
      "schedule": "상담말고 산책하기",
      "modifyDate": "2024-11-06T17:38:32"
    }
    ```

 - 본문
   - **요청** : PathVariable + JSON
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
      <th scope="col">전달 방법</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 고유 식별자 ID</td>
      <td>PathVariable</td>
    </tr>
    <tr>
      <td>name</td>
      <td>String</td>
      <td>O</td>
      <td>수정할 작성자명</td>
      <td>JSON</td>
    </tr>
    <tr>
      <td>schedule</td>
      <td>String</td>
      <td>O</td>
      <td>수정할 일정 내용</td>
      <td>JSON</td>
    </tr>
    <tr>
      <td>pw</td>
      <td>String</td>
      <td>O</td>
      <td>비밀번호</td>
      <td>JSON</td>
    </tr>
  </table>
  
   - **응답**
  <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 테이블의 고유 식별자 아이디</td>
    </tr>
    <tr>
      <td>name</td>
      <td>String</td>
      <td>O</td>
      <td>작성자명</td>
    </tr>
    <tr>
      <td>schedule</td>
      <td>String</td>
      <td>O</td>
      <td>일정 내용</td>
    </tr>
    <tr>
      <td>modifyDate</td>
      <td>DateTime</td>
      <td>O</td>
      <td>최종 수정일</td>
    </tr>
  </table>
</details>

<details>
<summary>선택 일정 삭제</summary>

 - 설명 : 일정을 하나 삭제합니다.

 - 기본 정보
   
  |메소드|URL|
  |:---|:---:|
  |DELETE|localhost:8080/schdules/{id}|

 - 예제
   - **요청** : PathVariable + JSON
    ```
    // PathVariable
    {
      34
    }

    ```
   - **응답**
     
   HTTP/1.1 200 OK
   ```
     {}
   ```
- 본문
   - **요청** : PathVariable + JSON
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
      <th scope="col">전달 방법</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 고유 식별자 ID</td>
      <td>PathVariable</td>
    </tr>
    <tr>
      <td>pw</td>
      <td>String</td>
      <td>O</td>
      <td>비밀번호</td>
      <td>JSON</td>
    </tr>
  </table>
  
   - **응답**
  없음
</details>

<details>
<summary>요청 실패</summary>

  - 설명 : 요청에 실패 했을 때
  - 요청 : 어떤 요청이든 발생 가능함
    
  - 예제 응답
    ```
    {
      "dateTime": "2024-11-06T17:49:38.0225623",
      "httpStatus": "NOT_FOUND",
      "statusCode": 404,
      "errors": {
          "error": "[id = 34] 에 해당하는 정보가 존재하지 않습니다."
      }
    }
    ```
    ```
    {
      "dateTime": "2024-11-06T17:59:23.9190301",
      "httpStatus": "BAD_REQUEST",
      "statusCode": 400,
      "errors": {
          "errors": "잘못된 비밀번호입니다."
      }
    }
    ```
    ```
    {
      "dateTime": "2024-11-06T17:18:32.5251222",
      "httpStatus": "BAD_REQUEST",
      "statusCode": 400,
      "errors": {
          "schedule": "비어 있을 수 없습니다"
      }
    }
    ```


  - 본문 응답

  |이름|타입|필수|설명|
|:---|---:|:---:|-----|
|dateTime|datetime|O|오류가 발생한 시각|
|httpStatus|String|O|http 상태|
|statusCode|int|O|http 상태 코드|
|errors|List<Map<String, String>>|O|오류 내용들|

</details>


## 3. ERD

![ERD](https://github.com/user-attachments/assets/c2ee37a3-4d36-4a26-b789-3f1295810205)


## 4. SQL

### 1) 일정 및 작성자 테이블 생성

```sql

CREATE TABLE writers
(
    id          bigint	auto_increment	PRIMARY KEY	COMMENT 'Auto Increament',
    name	    varchar(40)	NOT NULL	COMMENT '일정을 작성한 사람의 이름',
    email	    varchar(40)	NOT NULL  UNIQUE	COMMENT '일정을 작성한 사람의 E-mail',
    enroll_date	datetime(6)	NOT NULL	COMMENT '최초 작성자 정보 등록 날짜 - 자동',
    modify_date	datetime(6)	NOT NULL	COMMENT '가장 최근작성자 정보 수정 날짜 - 자동'
);

CREATE TABLE schedules
(
    id          bigint auto_increment    primary key    COMMENT 'Auto Increament',
    pw          varchar(40)  NOT NULL COMMENT '권한 체크용 비밀번호',
    schedule    varchar(200) NOT NULL COMMENT '일정 내용',
    enroll_date datetime(6)  NOT NULL COMMENT '최초 일정 작성 날짜 - 자동',
    modify_date datetime(6)  NOT NULL COMMENT '가장 최근 일정 내용 수정 날짜 - 자동',
    writer_id	bigint	NOT NULL	COMMENT 'writers 외래키',
    FOREIGN KEY (writer_id) REFERENCES writers(id)
);

```

### 2) 작성자 생성 및 일정 생성
```sql
-- Insert writers
INSERT INTO writers (id, name, email, enroll_date, modify_date)
VALUES (1, 'kim', 'sparta@teamsparta.co.kr', '2024-10-29 20:11:02', '2024-10-30 15:11:02');

-- Insert schedules
INSERT INTO schedules (id, schedule, pw, enroll_date, modify_date, writer_id)
VALUES (1,'과제하기','abcd123', '2024-10-29 09:12:40', '2024-10-30 19:20:24', 1);
```


### 3) 전체 일정 조회 
```sql
-- Select schedules
SELECT * FROM schedules;
)
```
### 4) 선택 일정 조회
```sql
-- Select schedules with id 1
SELECT * FROM schedules WHERE id = 1;
)
```

### 5) 선택 일정 수정 및 작성자 수정
```sql
-- Update schedules
UPDATE schedules SET schedule = '상담하기', modify_date = '2024-10-30 14:20:05' WHERE id = 1;


-- Update writers
UPDATE writers SET name = 'king', modify_date = '2024-10-30 11:04:49' WHERE id = 1;
```

### 6) 선택 일정 삭제
```sql
-- Delete schedules with id 1
DELETE FROM schedules WHERE id = 1;
```

## 5. 예시 API 결과 이미지

 - 일정 생성

![post](https://github.com/user-attachments/assets/f9c6afc5-f82f-4034-8662-8f1a25a69788)


- 일정 전체 조회
  
![get1](https://github.com/user-attachments/assets/c8f3b43b-27ab-44a1-9828-7d68e01ff326)
![get2](https://github.com/user-attachments/assets/fda8b393-af29-4c9e-930b-34ed03089564)
![get3](https://github.com/user-attachments/assets/fbe740f0-dc5e-4f24-b3b1-0dc88f8cbd7d)



- 일정 단건 조회

![get4](https://github.com/user-attachments/assets/98d16f43-e5d3-4be9-ac8d-2247c742dcad)


- 일정 단건 수정

![put](https://github.com/user-attachments/assets/4e002b47-8cfc-4d54-b1a9-d64ffad0ae9a)


- 일정 단건 삭제

![delete](https://github.com/user-attachments/assets/4ec334a8-812d-45a3-b75e-aebaa78fa7dc)


- 에러

![error1](https://github.com/user-attachments/assets/fd787eb8-d84e-49d1-91e7-124348ce60d2)
![error2](https://github.com/user-attachments/assets/11a1bf94-c02c-4b57-a385-bcf755a1f11d)
![error3](https://github.com/user-attachments/assets/c85aaf3f-a604-4c88-b75b-b03f46dde4ed)
![error4](https://github.com/user-attachments/assets/00baca7b-3c84-400d-8878-1f9223d59935)
![error5](https://github.com/user-attachments/assets/e5ff89f8-2753-4066-9e2b-89a1fff3fdeb)

  

## 6. 코드 설명 및 트러블 슈팅

[taketheking 블로그](https://taketheking.tistory.com/8)
