설치한 데이터베이스(Mysql)에 ERD를 따라 테이블을 생성

1. 필수 기능 가이드 개발에 필요한 테이블을 생성하는 query를 작성

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

2. 일정 생성을 하는 query를 작성
    -- Insert writers
    INSERT INTO writers (id, name, email, enroll_date, modify_date)
    VALUES (1, 'kim', 'sparta@teamsparta.co.kr', '2024-10-29 20:11:02', '2024-10-30 15:11:02');

    -- Insert schedules
    INSERT INTO schedules (id, schedule, pw, enroll_date, modify_date, writer_id)
    VALUES (1,'과제하기','abcd123', '2024-10-29 09:12:40', '2024-10-30 19:20:24', 1);

3. 전체 일정을 조회하는 query를 작성
    SELECT * FROM schedules;

4.  선택 일정을 조회하는 query를 작성
    - SELECT * FROM schedules WHERE id = 1;

5.  선택한 일정을 수정하는 query를 작성
    -- Update schedules
    UPDATE schedules SET schedule = '상담하기', modify_date = '2024-10-30 14:20:05' WHERE id = 1;


    -- Update writers
    UPDATE writers SET name = 'king', modify_date = '2024-10-30 11:04:49' WHERE id = 1;

6.  선택한 일정을 삭제하는 query를 작성
    DELETE FROM schedules WHERE id = 1;
