CREATE SEQUENCE MP_BOARD_SEQ
START WITH 1
INCREMENT BY 1;


CREATE TABLE MP_BOARD(
    BNO NUMBER NOT NULL,
    TITLE VARCHAR2(100)     NOT NULL,
    CONTENT VARCHAR2(2000)  NOT NULL,
    WRITER VARCHAR2(100)    NOT NULL,
    REGDATE DATE            DEFAULT SYSDATE,
    PRIMARY KEY(BNO)
);

ALTER TABLE MP_BOARD ADD(HIT NUMBER DEFAULT 0);


INSERT INTO MP_BOARD(BNO, TITLE, CONTENT, WRITER)
     VALUES (MP_BOARD_SEQ.NEXTVAL, '제목입니다', '내용입니다', 'MELONPEACH');

SELECT * FROM MP_BOARD;
COMMIT;







		SELECT  BNO, 
		        TITLE, 
		        CONTENT,
		        WRITER, 
		        REGDATE
		 FROM ( 
		        SELECT BNO, 
		               TITLE, 
		               CONTENT, 
		               WRITER, 
		               REGDATE, 
		               ROW_NUMBER() OVER(ORDER BY BNO DESC) AS RNUM
		         FROM MP_BOARD 
		                       ) MP
		WHERE RNUM BETWEEN 1 AND 10
		ORDER BY BNO DESC;


		
		SELECT COUNT(BNO)
		  FROM MP_BOARD
		 WHERE BNO > 0;
	




