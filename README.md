# 일정 관리 앱


## 1. API 명세서

<img width="1000" alt="스크린샷 2024-10-31 오후 5 45 59" src="https://github.com/user-attachments/assets/c7326b95-412a-4ed3-ad9f-97d3d96b0c1b">


<img width="1000" alt="스크린샷 2024-10-31 오후 5 46 10" src="https://github.com/user-attachments/assets/0f99011d-a494-4d9c-8839-70a496e8cb13">




## 2. ERD

![ERD](https://github.com/user-attachments/assets/d9dcc768-27a2-46f7-8b5c-7c3dc06ccddd)


## 3. SQL

### 1) 일정 및 작성자 테이블 생성

```sql

create table writers
(
    `id`	bigint	auto_increment	PRIMARY KEY	COMMENT 'Auto Increament',
    `pw`	varchar(40)	NOT NULL	COMMENT '작성자 확인용 비밀번호',
    `name`	varchar(40)	NOT NULL	COMMENT '일정을 작성한 사람의 이름',
    `email`	varchar(40)	NULL	COMMENT '일정을 작성한 사람의 E-mail',
    `add_date`	datetime(6)	NOT NULL	COMMENT '최초 작성자 정보 등록 날짜 - 자동',
    `modify_date`	datetime(6)	NOT NULL	COMMENT '가장 최근작성자 정보 수정 날짜 - 자동'
);

create table schedules
(
    id          bigint auto_increment comment 'Auto Increament'	primary key,
    schedule    varchar(200) null comment '일정 내용',
    pw          varchar(40)  not null comment '권한 체크용 비밀번호',
    enroll_date datetime(6)  not null comment '최초 일정 작성 날짜 - 자동',
    modify_date datetime(6)  not null comment '가장 최근 일정 내용 수정 날짜 - 자동',
    writer_id	bigint	NOT NULL	COMMENT 'writers 외래키',
    FOREIGN KEY (writer_id) REFERENCES writers(id)
);

```

### 2) 작성자 생성 및 일정 생성
```sql
-- Insert Writers
INSERT INTO writer (id, pw, name, email, create_date)
VALUES (1, "abcd123", "kim", "sparta@teamsparta.co.kr", 2024-10-29, 2024-10-30);

-- Insert schedules
INSERT INTO schedule (id, schedule, pw, enroll_date, modify_date, writer_id)
VALUES (1,"과제하기","abcd123", 2024-10-29, 2024-10-30, 2);
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

### 5) 선택 일정 수정 및 유저 수정
```sql
-- Update schedules
UPDATE schedules SET schedule = "상담하기", update_date = "2024-10-30" WHERE id = 1;


-- Update writers
UPDATE writers SET name = "sewon", update_date = "2024-10-30" WHERE id = 1;
```

### 6) 선택 일정 삭제
```sql
-- Delete schedules with id 1
DELETE FROM schedules WHERE id = 1;

```
