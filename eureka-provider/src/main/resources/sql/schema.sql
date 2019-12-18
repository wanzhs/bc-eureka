DROP TABLE if EXISTS `user`;
create table `user` (
  id int(10),
  username VARCHAR(40),
  name VARCHAR(20),
  age INT(3),
  balance DECIMAL(10, 2),
  PRIMARY KEY (id)
);