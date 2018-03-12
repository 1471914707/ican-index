/*userInfo*/
INSERT INTO `user_info` VALUES (100000,'https://images.nowcoder.net/images/20180102/826546_1514884488956_6906171DA1232FFCAEE71276E01E2508',
'林嘉瑜',1,'EA48576F30BE1669971699C09AD05C94','123456',1,1,'1970-01-01 00:00:00','1970-01-01 00:00:00'),
(100001,'https://images.nowcoder.net/images/20180102/63_1514861814371_E573EC9DA05DFAC7340D94F1A6D246E3',
'华丽芳',2,'EA48576F30BE1669971699C09AD05C94','123456',2,1,'1970-01-01 00:00:00','1970-01-01 00:00:00'),
(100002,'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2603224971,2644804380&fm=58','广东技术师范学院',1,
'EA48576F30BE1669971699C09AD05C94','123456',3,1,'1970-01-01 00:00:00','1970-01-01 00:00:00'),
(100003,'https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1754456924,2226635552&fm=58','暨南大学',1,
'EA48576F30BE1669971699C09AD05C94','123456',3,1,'1970-01-01 00:00:00','1970-01-01 00:00:00'),
(100004,'https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2187454435,2491540446&fm=58','华南师范学院'
,1,'EA48576F30BE1669971699C09AD05C94','123456',3,1,'1970-01-01 00:00:00','1970-01-01 00:00:00'),
(100005,'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1446226878,1596766157&fm=58','华南理工大学',
1,'EA48576F30BE1669971699C09AD05C94','123456',3,1,'1970-01-01 00:00:00','1970-01-01 00:00:00'),
(100006,'https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=707505851,691492666&fm=58','华南农业大学',1,
'EA48576F30BE1669971699C09AD05C94','123456',3,1,'1970-01-01 00:00:00','1970-01-01 00:00:00'),
(100007,'https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=686718905,597120231&fm=58&bpow=652&bpoh=652',
'中山大学',1,'EA48576F30BE1669971699C09AD05C94','123456',3,1,'1970-01-01 00:00:00','1970-01-01 00:00:00');

/*admin*/
INSERT INTO `admin` VALUES (100000,'18813960106','1471914707@qq.com','1970-01-01 00:00:00','1970-01-01 00:00:00'),
(100001,'13609043152','13609043152@163.com','1970-01-01 00:00:00','1970-01-01 00:00:00');


/*国家*/
insert into city values(1,0,'中国','China','C',1,now(),now());
insert into city values(2,0,'澳大利亚','Australia','A',1,now(),now());
insert into city values(3,0,'加拿大','Canada','C',1,now(),now());
insert into city values(4,0,'法国','France','F',1,now(),now());
insert into city values(5,0,'德国','Germany','G',1,now(),now());
insert into city values(6,0,'日本','Japan','J',1,now(),now());
insert into city values(7,0,'马来西亚','Malaysia','M',1,now(),now());
insert into city values(8,0,'荷兰','Netherlands','N',1,now(),now());
insert into city values(9,0,'新西兰','New Zealand','N',1,now(),now());
insert into city values(10,0,'瑞典','Sweden','S',1,now(),now());
insert into city values(11,0,'瑞士','Switzerland','S',1,now(),now());
insert into city values(12,0,'英国','United Kingdom','U',1,now(),now());
insert into city values(13,0,'美国','United States','U',1,now(),now());
insert into city values(14,0,'美国','United States','U',1,now(),now());
/*省份*/
insert into city values(15,1,'北京','','B',2,now(),now());
insert into city values(16,1,'上海','','S',2,now(),now());
insert into city values(17,1,'广东','','G',2,now(),now());
insert into city values(18,1,'浙江','','Z',2,now(),now());
insert into city values(19,1,'江苏','','J',2,now(),now());
insert into city values(20,1,'湖南','','H',2,now(),now());
/*省份*/
insert into city values(21,15,'北京','','B',3,now(),now());
insert into city values(22,16,'上海','','S',3,now(),now());
insert into city values(23,17,'广州','','G',3,now(),now());
insert into city values(24,17,'深圳','','S',3,now(),now());
insert into city values(25,18,'杭州','','H',3,now(),now());
insert into city values(26,19,'南京','','N',3,now(),now());