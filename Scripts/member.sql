



CREATE TABLE MP_MEMBER (
	USERID VARCHAR2(40) NOT NULL,
	USERPASS VARCHAR2(100) NOT NULL,
	USERNAME VARCHAR2(40) NOT NULL,
	REGDATE DATE DEFAULT SYSDATE,
	PRIMARY KEY(USERID)
);



SELECT * FROM MP_MEMBER mm ;



