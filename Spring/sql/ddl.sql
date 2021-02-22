create table member
(
    email varchar(255),
    password varchar(255),
    name varchar(255),
    nickname varchar(255),
    location varchar(255),
    primary key(email)
);

create table board
(
id int,
price bigint,
title varchar(255),
text varchar(255),
categoryId int,
nickname varchar(255),
location varchar(255),
registerDate varchar(255),
deadlineDate varchar(255),
dibsCnt int,
viewCnt int,
chatCnt int
);