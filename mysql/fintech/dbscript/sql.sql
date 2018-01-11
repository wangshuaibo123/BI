
/*********2018/1/11 11:18:30 统计分析菜单 *********/
delete from sys_menu where id=56;
INSERT INTO sys_menu VALUES (56,'BI_STAT','统计分析','',NULL,0,1,1,0,NULL,91);

delete from sys_resource where id=91;
INSERT INTO sys_resource VALUES (91,'统计分析','url','/bi/prepareExecute/biList',NULL,1,NULL,NULL,'1','1');