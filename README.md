# 일정 관리 앱


## 1. API 명세서

<img width="800" alt="스크린샷 2024-10-31 오후 4 53 58" src="https://github.com/user-attachments/assets/86bf1099-de19-4ab4-a915-d92e5bc6c370">


<img width="800" alt="스크린샷 2024-10-31 오후 4 56 36" src="https://github.com/user-attachments/assets/ed1ef6cf-96eb-4a46-a9b0-f7ff69fc6c2f">



## 2. ERD

<img width="600" alt="스크린샷 2024-10-31 오후 4 58 00" src="https://github.com/user-attachments/assets/49e021f9-2393-4983-8fca-85c65aa9f5e7">



## 3. SQL

### 1) 일정 및 작성자 테이블 생성

```sql
CREATE TABLE `Schedules` (
	`id`	bigint	NOT NULL	COMMENT 'Auto Increament',
	`schedule`	varchar(200)	NULL	COMMENT '일정 내용',
	`pw`	varchar(40)	NOT NULL	COMMENT '권한 체크용 비밀번호',
	`enroll_date`	datetime	NOT NULL	COMMENT '최초 일정 작성 날짜 - 자동',
	`modify_date`	datetime	NOT NULL	COMMENT '가장 최근 일정 내용 수정 날짜 - 자동',
	`writer_id`	varchar(40)	NOT NULL	COMMENT 'Writer 외래키'
);

CREATE TABLE `Writers` (
	`id`	varchar(40)	NOT NULL	COMMENT '작성자 고유의 아이디 - 문자+숫자',
	`pw`	varchar(40)	NOT NULL	COMMENT '작성자 확인용 비밀번호',
	`name`	varchar(255)	NOT NULL	COMMENT '일정을 작성한 사람의 이름',
	`email`	varchar(40)	NULL	COMMENT '일정을 작성한 사람의 E-mail',
	`add_date`	datetime	NOT NULL	COMMENT '최초 작성자 정보 등록 날짜 - 자동',
	`modify_date`	datetime	NOT NULL	COMMENT '가장 최근작성자 정보 수정 날짜 - 자동'
);

ALTER TABLE `Schedules` ADD CONSTRAINT `PK_SCHEDULES` PRIMARY KEY (
	`id`
);

ALTER TABLE `Writers` ADD CONSTRAINT `PK_WRITERS` PRIMARY KEY (
	`id`
);
```

### 2) 작성자 생성 및 일정 생성
```sql
-- Insert Writers
INSERT INTO writer (id, pw, name, email, create_date)
VALUES ("taketheking", "abcd123", "kim", "sparta@teamsparta.co.kr", 2024-10-29, 2024-10-30);

-- Insert schedules
INSERT INTO schedule (id, schedule, pw, enroll_date, modify_date, writer_id)
VALUES (1,"과제하기","abcd123", 2024-10-29, 2024-10-30, "taketheking");
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
