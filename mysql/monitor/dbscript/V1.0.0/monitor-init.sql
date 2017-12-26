-- MySQL dump 10.13  Distrib 5.5.53, for Win64 (AMD64)
--
-- Host: 172.18.100.102    Database: monitor
-- ------------------------------------------------------
-- Server version	5.6.36-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `log_info`
--

LOCK TABLES `log_info` WRITE;
/*!40000 ALTER TABLE `log_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `logging_event`
--

LOCK TABLES `logging_event` WRITE;
/*!40000 ALTER TABLE `logging_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `logging_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `logging_event_exception`
--

LOCK TABLES `logging_event_exception` WRITE;
/*!40000 ALTER TABLE `logging_event_exception` DISABLE KEYS */;
/*!40000 ALTER TABLE `logging_event_exception` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `logging_event_property`
--

LOCK TABLES `logging_event_property` WRITE;
/*!40000 ALTER TABLE `logging_event_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `logging_event_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
INSERT INTO `qrtz_locks` VALUES ('startQuertz','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `quartz_task_group_def`
--

LOCK TABLES `quartz_task_group_def` WRITE;
/*!40000 ALTER TABLE `quartz_task_group_def` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_task_group_def` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `quartz_task_group_instance`
--

LOCK TABLES `quartz_task_group_instance` WRITE;
/*!40000 ALTER TABLE `quartz_task_group_instance` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_task_group_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `quartz_task_hcomment`
--

LOCK TABLES `quartz_task_hcomment` WRITE;
/*!40000 ALTER TABLE `quartz_task_hcomment` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_task_hcomment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `quartz_task_his`
--

LOCK TABLES `quartz_task_his` WRITE;
/*!40000 ALTER TABLE `quartz_task_his` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_task_his` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_acl`
--

LOCK TABLES `sys_acl` WRITE;
/*!40000 ALTER TABLE `sys_acl` DISABLE KEYS */;
INSERT INTO `sys_acl` VALUES (100,2,1,1,1,'1',1),(101,2,2,1,1,'1',1),(102,2,5,1,1,'1',1),(103,2,7,1,1,'1',1),(104,2,10,1,1,'1',1),(105,2,12,1,1,'1',1),(106,2,47,1,1,'1',1),(107,2,48,1,1,'1',1),(108,2,42,1,1,'1',1),(109,2,43,1,1,'1',1),(110,2,44,1,1,'1',1),(111,2,45,1,1,'1',1),(112,3,1,1,1,'1',1),(113,3,2,1,1,'1',1),(114,3,3,1,1,'1',1),(115,3,4,1,1,'1',1),(116,3,5,1,1,'1',1),(117,3,6,1,1,'1',1),(118,3,7,1,1,'1',1),(119,3,8,1,1,'1',1),(120,3,53,1,1,'1',1),(121,3,54,1,1,'1',1),(122,3,55,1,1,'1',1),(123,3,56,1,1,'1',1),(124,3,9,1,1,'1',1),(125,3,10,1,1,'1',1),(126,3,11,1,1,'1',1),(127,3,12,1,1,'1',1),(128,3,47,1,1,'1',1),(129,3,48,1,1,'1',1),(130,3,13,1,1,'1',1),(131,3,14,1,1,'1',1),(132,3,15,1,1,'1',1),(133,3,16,1,1,'1',1),(134,3,17,1,1,'1',1),(135,3,18,1,1,'1',1),(136,3,19,1,1,'1',1),(137,3,20,1,1,'1',1),(138,3,21,1,1,'1',1),(139,3,22,1,1,'1',1),(140,3,23,1,1,'1',1),(141,3,24,1,1,'1',1),(142,3,25,1,1,'1',1),(143,3,26,1,1,'1',1),(144,3,27,1,1,'1',1),(145,3,28,1,1,'1',1),(146,3,29,1,1,'1',1),(147,3,30,1,1,'1',1),(148,3,31,1,1,'1',1),(149,3,32,1,1,'1',1),(150,3,33,1,1,'1',1),(151,3,34,1,1,'1',1),(152,3,35,1,1,'1',1),(153,3,36,1,1,'1',1),(154,3,37,1,1,'1',1),(155,3,38,1,1,'1',1),(156,3,39,1,1,'1',1),(157,3,40,1,1,'1',1),(158,3,41,1,1,'1',1),(159,3,46,1,1,'1',1),(160,3,42,1,1,'1',1),(161,3,43,1,1,'1',1),(162,3,44,1,1,'1',1),(163,3,45,1,1,'1',1),(164,3,49,1,1,'1',1),(165,3,50,1,1,'1',1),(166,3,52,1,1,'1',1),(167,3,57,1,1,'1',1),(225,5,1,1,1,'1',1),(226,5,2,1,1,'1',1),(227,5,15,1,1,'1',1),(228,5,16,1,1,'1',1),(229,5,17,1,1,'1',1),(230,5,18,1,1,'1',1),(231,5,58,1,1,'1',1),(232,5,35,1,1,'1',1),(233,5,42,1,1,'1',1),(234,5,43,1,1,'1',1),(235,5,44,1,1,'1',1),(236,5,45,1,1,'1',1),(237,4,1,1,1,'1',1),(238,4,2,1,1,'1',1),(239,4,15,1,1,'1',1),(240,4,16,1,1,'1',1),(241,4,17,1,1,'1',1),(242,4,18,1,1,'1',1),(243,4,58,1,1,'1',1),(244,4,35,1,1,'1',1),(245,4,42,1,1,'1',1),(246,4,43,1,1,'1',1),(247,4,44,1,1,'1',1),(248,4,45,1,1,'1',1),(430,7,1,1,1,'1',1),(431,7,42,1,1,'1',1),(432,7,43,1,1,'1',1),(433,7,44,1,1,'1',1),(434,8,1,1,1,'1',1),(435,8,42,1,1,'1',1),(436,8,43,1,1,'1',1),(437,8,44,1,1,'1',1),(678,6,1,1,1,'1',1),(679,6,2,1,1,'1',1),(680,6,3,1,1,'1',1),(681,6,4,1,1,'1',1),(682,6,5,1,1,'1',1),(683,6,6,1,1,'1',1),(684,6,7,1,1,'1',1),(685,6,8,1,1,'1',1),(686,6,53,1,1,'1',1),(687,6,54,1,1,'1',1),(688,6,55,1,1,'1',1),(689,6,56,1,1,'1',1),(690,6,81,1,1,'1',1),(691,6,10,1,1,'1',1),(692,6,11,1,1,'1',1),(693,6,12,1,1,'1',1),(694,6,47,1,1,'1',1),(695,6,48,1,1,'1',1),(696,6,13,1,1,'1',1),(697,6,14,1,1,'1',1),(698,6,15,1,1,'1',1),(699,6,16,1,1,'1',1),(700,6,17,1,1,'1',1),(701,6,18,1,1,'1',1),(702,6,60,1,1,'1',1),(703,6,80,1,1,'1',1),(704,6,87,1,1,'1',1),(705,6,88,1,1,'1',1),(706,6,89,1,1,'1',1),(707,6,19,1,1,'1',1),(708,6,20,1,1,'1',1),(709,6,21,1,1,'1',1),(710,6,22,1,1,'1',1),(711,6,23,1,1,'1',1),(712,6,24,1,1,'1',1),(713,6,25,1,1,'1',1),(714,6,26,1,1,'1',1),(715,6,27,1,1,'1',1),(716,6,28,1,1,'1',1),(717,6,29,1,1,'1',1),(718,6,30,1,1,'1',1),(719,6,31,1,1,'1',1),(720,6,32,1,1,'1',1),(721,6,33,1,1,'1',1),(722,6,34,1,1,'1',1),(723,6,36,1,1,'1',1),(724,6,37,1,1,'1',1),(725,6,38,1,1,'1',1),(726,6,39,1,1,'1',1),(727,6,40,1,1,'1',1),(728,6,41,1,1,'1',1),(729,6,46,1,1,'1',1),(730,6,61,1,1,'1',1),(731,6,62,1,1,'1',1),(732,6,63,1,1,'1',1),(733,6,64,1,1,'1',1),(734,6,65,1,1,'1',1),(735,6,66,1,1,'1',1),(736,6,67,1,1,'1',1),(737,6,68,1,1,'1',1),(738,6,73,1,1,'1',1),(739,6,74,1,1,'1',1),(740,6,75,1,1,'1',1),(741,6,90,1,1,'1',1),(742,6,69,1,1,'1',1),(743,6,76,1,1,'1',1),(744,6,77,1,1,'1',1),(745,6,78,1,1,'1',1),(746,6,79,1,1,'1',1),(747,6,72,1,1,'1',1),(748,6,82,1,1,'1',1),(749,6,83,1,1,'1',1),(750,6,84,1,1,'1',1),(751,6,85,1,1,'1',1),(752,6,86,1,1,'1',1),(753,6,42,1,1,'1',1),(754,6,43,1,1,'1',1),(755,6,45,1,1,'1',1),(763,9,1,1,1,'1',1),(764,9,42,1,1,'1',1),(765,9,43,1,1,'1',1),(766,9,44,1,1,'1',1),(1169,1,1,1,1,'1',1),(1170,1,2,1,1,'1',1),(1171,1,3,1,1,'1',1),(1172,1,4,1,1,'1',1),(1173,1,5,1,1,'1',1),(1174,1,6,1,1,'1',1),(1175,1,7,1,1,'1',1),(1176,1,8,1,1,'1',1),(1177,1,53,1,1,'1',1),(1178,1,54,1,1,'1',1),(1179,1,55,1,1,'1',1),(1180,1,56,1,1,'1',1),(1181,1,81,1,1,'1',1),(1182,1,10,1,1,'1',1),(1183,1,11,1,1,'1',1),(1184,1,12,1,1,'1',1),(1185,1,47,1,1,'1',1),(1186,1,48,1,1,'1',1),(1187,1,13,1,1,'1',1),(1188,1,14,1,1,'1',1),(1189,1,19,1,1,'1',1),(1190,1,20,1,1,'1',1),(1191,1,21,1,1,'1',1),(1192,1,22,1,1,'1',1),(1193,1,23,1,1,'1',1),(1194,1,24,1,1,'1',1),(1195,1,25,1,1,'1',1),(1196,1,26,1,1,'1',1),(1197,1,27,1,1,'1',1),(1198,1,28,1,1,'1',1),(1199,1,29,1,1,'1',1),(1200,1,30,1,1,'1',1),(1201,1,31,1,1,'1',1),(1202,1,32,1,1,'1',1),(1203,1,33,1,1,'1',1),(1204,1,34,1,1,'1',1),(1205,1,36,1,1,'1',1),(1206,1,37,1,1,'1',1),(1207,1,38,1,1,'1',1),(1208,1,46,1,1,'1',1),(1209,1,82,1,1,'1',1),(1210,1,83,1,1,'1',1),(1211,1,84,1,1,'1',1),(1212,1,85,1,1,'1',1),(1213,1,86,1,1,'1',1),(1214,1,42,1,1,'1',1),(1215,1,44,1,1,'1',1),(1216,1,95,1,1,'1',1),(1217,1,96,1,1,'1',1),(1218,1,97,1,1,'1',1),(1219,1,98,1,1,'1',1),(1220,1,99,1,1,'1',1),(1221,1,100,1,1,'1',1),(1222,1,101,1,1,'1',1),(1223,1,103,1,1,'1',1),(1284,41,1,1,1,'1',1),(1285,41,95,1,1,'1',1),(1286,41,99,1,1,'1',1),(1287,41,100,1,1,'1',1),(1288,41,103,1,1,'1',1),(1289,21,1,1,1,'1',1),(1290,21,95,1,1,'1',1),(1291,21,99,1,1,'1',1),(1292,21,100,1,1,'1',1),(1293,21,103,1,1,'1',1);
/*!40000 ALTER TABLE `sys_acl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_app_access_log`
--

LOCK TABLES `sys_app_access_log` WRITE;
/*!40000 ALTER TABLE `sys_app_access_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_app_access_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_app_error_info`
--

LOCK TABLES `sys_app_error_info` WRITE;
/*!40000 ALTER TABLE `sys_app_error_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_app_error_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_app_ftp_info`
--

LOCK TABLES `sys_app_ftp_info` WRITE;
/*!40000 ALTER TABLE `sys_app_ftp_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_app_ftp_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_app_level_setup`
--

LOCK TABLES `sys_app_level_setup` WRITE;
/*!40000 ALTER TABLE `sys_app_level_setup` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_app_level_setup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_app_log_priv`
--

LOCK TABLES `sys_app_log_priv` WRITE;
/*!40000 ALTER TABLE `sys_app_log_priv` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_app_log_priv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_app_manage_contact_way`
--

LOCK TABLES `sys_app_manage_contact_way` WRITE;
/*!40000 ALTER TABLE `sys_app_manage_contact_way` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_app_manage_contact_way` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_area`
--

LOCK TABLES `sys_area` WRITE;
/*!40000 ALTER TABLE `sys_area` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_asyn_job`
--

LOCK TABLES `sys_asyn_job` WRITE;
/*!40000 ALTER TABLE `sys_asyn_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_asyn_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'发送错误日志邮件的主机IP','send_error_info_ip','172.19.100.92','1','0',NULL),(101,'ESB发送邮件URL','ESB_EMAIL_ADDR','http://172.19.100.233:9040/esbbiztoken/mailSend/v1','1','1',NULL),(102,'ESB发送短信URL','ESB_SMS_ADDR','http://172.19.100.233:9040/esbbiztoken/smsServerSend/hl95/v1','1','1',NULL),(103,'是否开启接受消息总开关','accept_msg','NO','1','1',NULL),(201,'消息推送服务的根路径','MSG_PUSH_WEBROOT','http://log.jieyue.com/monitor/','1','1',NULL);
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` VALUES (1,'IDTYPE','证件类型','0','1',NULL),(2,'SEX','性别','0','1',NULL),(3,'EDUCATION','学历','0','1',NULL),(4,'YESNO','是否','0','1',NULL),(5,'MARITALSTATUS','婚姻状况','0','1',NULL),(6,'COMPANYTYPE','企业类型','0','1',NULL),(7,'UNITNATURE','工作单位性质','0','1',NULL),(8,'WORKYEAR','工作年限','0','1',NULL),(9,'COMPANYSCALE','单位规模','0','1',NULL),(10,'HOUSETYPE','住宅类型','0','1',NULL),(11,'ESTATETYPE','房产类型','0','1',NULL),(12,'ADDRTYPE','地址类型','0','1',NULL),(13,'CHILDSTATUS','子女现状','0','1',NULL),(14,'JOBNATURE','工作性质','0','1',NULL),(15,'PAYWAGE','工资发放方式','0','1',NULL),(16,'PRIORITY','优先级','0','1',NULL),(17,'URGENCY','紧急程度','0','1',NULL),(18,'CUSTOMERSATISFACTION','客户满意度','0','1',NULL),(19,'CUSTOMERHOPE','客户购买意愿','0','1',NULL),(20,'HEALTHSTATUS','健康状况','0','1',NULL),(21,'CENSUSTYPE','户籍种类','0','1',NULL),(22,'HOUSERIGHT','产权性质','0','1',NULL),(23,'INDUSTRYTYPE','所属行业','0','1',NULL),(24,'WARRANTTYPE','担保方式','0','1',NULL),(25,'INCOMESOURCE','收入来源','0','1',NULL),(26,'INCOMLEVEL','收入水平','0','1',NULL),(27,'TELTYPE','电话类型','0','1',NULL),(28,'POROOM','经营场所','0','1',NULL),(29,'OCCUPATION','职业','0','1',NULL),(30,'DUTY','职务','0','1',NULL),(31,'TITLE','职称','0','1',NULL),(32,'RELATIONTYPE','联系人与客户关系','0','1',NULL),(33,'LINKMANTYPE','联系人类型','0','1',NULL),(34,'CONTACTTYPE','联系方式','0','1',NULL),(35,'LANGUAGE','语言','0','1',NULL),(36,'PAYFREQUENCY','还款频率','0','1',NULL),(38,'ACCOUNTTYPE','银行账户类型','0','1',NULL),(39,'BLACKLISTTYPE','黑名单类型','0','1',NULL),(40,'COMPREGMONEY','企业注册资本金（万元）','0','1',NULL),(41,'COMPANYAGE','公司成立时长','0','1',NULL),(42,'RESIDEAPPROVE','居住证明','0','1',NULL),(43,'JOBTYPE','工作方式','0','1',NULL),(44,'WORKAPPROVE','工作证明','0','1',NULL),(45,'LISTENRESULT','接听情况','0','1',NULL),(46,'COMPLASTRATAL','最近1年企业纳税额','0','1',NULL),(47,'COMPRENTAL','月办公场地租金','0','1',NULL),(48,'HOUSENUMBER','本地自有房产','0','1',NULL),(49,'FYNETTYPE','法院网查询类型','0','1',NULL),(50,'COMMONLIVE','共同居住者','0','1',NULL),(51,'COMPANYINBANK','申请人流水在银行的交易情况','0','1',NULL),(52,'SOCIALAPPROVE','社保证明','0','1',NULL),(53,'RIGHTFILEAPPROVE','股权证明文件','0','1',NULL),(54,'OWNCARVALUE','自有车辆价值','0','1',NULL),(55,'LOANACCOUNTSTATE','贷款账户状态','0','1',NULL),(56,'ASSETTPE','资产类别','0','1',NULL),(57,'ASSETAPPROVE','资产证明','0','1',NULL),(58,'IDENTITYAPPROVE','身份证明','0','1',NULL),(59,'MARITALHERJOB','配偶工作情况','0','1',NULL),(60,'CUSTOMERLEVEL','客户级别','0','1',NULL),(61,'CUSTCHANNEL','客户渠道','0','1',NULL),(62,'CUSTCATEGORY','客户类别','0','1',NULL),(63,'MONEYTYPE','币种','0','1',NULL),(64,'PAYMENTTYPE','付款方式','0','1',NULL),(65,'LOANPURPOSE','借款用途','0','1',NULL),(66,'POLISTATUS','政治面貌','0','1',NULL),(68,'NATION','民族','0','1',NULL),(69,'PERIODTYPE','期限类型','0','1',NULL),(70,'SYSTEMFLAG','系统标识','1','1',NULL),(71,'COSTCODE','费用类型','0','1',NULL),(72,'PT_ROLETYPE','角色类型','0','1',NULL),(73,'PT_ORGTYPE','机构类型','0','1',NULL),(74,'PT_USERJOB','职务','0','1',NULL),(75,'PT_MSGSTATUS','消息状态','0','1',NULL),(76,'PT_MSGFLAG','消息紧急标识','0','1',NULL),(77,'PT_MSGTYPE','消息类型','0','1',NULL),(78,'PT_USERTYPE','用户类型','0','1',NULL),(79,'LOANPERIODS','贷款期数','0','1',NULL),(80,'LOANFUNDS','放款机构','0','1',NULL),(81,'CUSTOMERSOURCE','客户来源','1','1',NULL),(102,'BANKCODE','银行名称','0','1',NULL),(103,'LOG_LEVEL','日志级别','0','1',NULL),(104,'LOG_RATE_UNIT','时间单位','0','1',NULL),(203,'SYS_DAI_MA_CODE','代码code','1','1',NULL),(303,'SYS_APP_LIMIT','系统限流控制','1','1',NULL),(304,'sss','sss','1','1',NULL);
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_dict_detail`
--

LOCK TABLES `sys_dict_detail` WRITE;
/*!40000 ALTER TABLE `sys_dict_detail` DISABLE KEYS */;
INSERT INTO `sys_dict_detail` VALUES (1,1,'护照','2','1','1',NULL),(2,1,'户口簿','5','2','1',NULL),(3,1,'港澳台居民往来大陆通行证','4','3','1',NULL),(4,1,'军官证','3','4','1',NULL),(5,1,'身份证','1','5','1',NULL),(6,2,'男','1','1','1',NULL),(7,2,'女','0','2','1',NULL),(8,2,'其他','9','3','1',NULL),(9,3,'本科','2','1','1',NULL),(10,3,'硕士及以上','1','2','1',NULL),(11,3,'专科','3','3','1',NULL),(12,3,'初中及以下','5','4','1',NULL),(13,3,'高中','4','5','1',NULL),(14,4,'否','0','1','1',NULL),(15,4,'是','1','2','1',NULL),(16,5,'已婚','2','1','1',NULL),(17,5,'离异','3','2','1',NULL),(18,5,'丧偶','4','3','1',NULL),(19,5,'未婚','1','4','1',NULL),(20,6,'个体工商户','5','1','1',NULL),(21,6,'股份有限公司','4','2','1',NULL),(22,6,'有限责任公司','3','3','1',NULL),(23,6,'合伙','2','4','1',NULL),(24,6,'独资','1','5','1',NULL),(25,6,'其他','6','6','1',NULL),(26,7,'三资企业','4','1','1',NULL),(27,7,'其他','5','2','1',NULL),(28,7,'机关及事业单位','1','3','1',NULL),(29,7,'国营','2','4','1',NULL),(30,7,'民营','3','5','1',NULL),(31,8,'1年以下','0','1','1',NULL),(32,8,'1（含）-2年','1','2','1',NULL),(33,8,'2（含）-5年','2','3','1',NULL),(34,8,'5（含）以上','3','4','1',NULL),(35,9,'100人以下','1','1','1',NULL),(36,9,'3000人以上','5','2','1',NULL),(37,9,'1000-3000人','4','3','1',NULL),(38,9,'500-1000人','3','4','1',NULL),(39,9,'100-500人','2','5','1',NULL),(40,10,'无按揭购房','1','1','1',NULL),(41,10,'亲属住房','5','2','1',NULL),(42,10,'租房','4','3','1',NULL),(43,10,'公积金按揭购房','3','4','1',NULL),(44,10,'商业按揭购房','2','5','1',NULL),(45,10,'单位宿舍','7','6','1',NULL),(46,10,'其他','8','7','1',NULL),(47,10,'自建房','6','8','1',NULL),(48,11,'全款房','1','1','1',NULL),(49,11,'公积金按揭房','3','2','1',NULL),(50,11,'商业按揭房','2','3','1',NULL),(51,12,'家庭地址','1','1','1',NULL),(52,12,'户籍地址','3','2','1',NULL),(53,12,'联系地址','4','3','1',NULL),(54,12,'单位地址','2','4','1',NULL),(55,12,'发证机关地址','5','5','1',NULL),(56,13,'婴幼儿','1','1','1',NULL),(57,13,'小学','2','2','1',NULL),(58,13,'工作','5','3','1',NULL),(59,13,'大学','4','4','1',NULL),(60,13,'中学','3','5','1',NULL),(61,13,'其他','9','6','1',NULL),(62,14,'试用/临时','3','1','1',NULL),(63,14,'正式职员','2','2','1',NULL),(64,14,'其他','4','3','1',NULL),(65,14,'企业主/股东','1','4','1',NULL),(66,15,'转账打卡','1','1','1',NULL),(67,15,'转账打卡+现金','3','2','1',NULL),(68,15,'现金','2','3','1',NULL),(69,16,'中','2','1','1',NULL),(70,16,'高','1','2','1',NULL),(71,16,'低','3','3','1',NULL),(72,17,'普通','3','1','1',NULL),(73,17,'非常紧急','1','2','1',NULL),(74,17,'紧急','2','3','1',NULL),(75,18,'非常满意','1','1','1',NULL),(76,18,'满意','2','2','1',NULL),(77,18,'一般','3','3','1',NULL),(78,18,'不满意','4','4','1',NULL),(79,19,'非常有意向','1','1','1',NULL),(80,19,'比较有意向','2','2','1',NULL),(81,19,'一般需要继续了解','3','3','1',NULL),(82,19,'可能性较小或尚未明确情况','4','4','1',NULL),(83,19,'可能性极小或不可能','5','5','1',NULL),(84,20,'良好','2','1','1',NULL),(85,20,'好','1','2','1',NULL),(86,20,'一般','3','3','1',NULL),(87,21,'本埠农村','2','1','1',NULL),(88,21,'本埠城镇','1','2','1',NULL),(89,21,'外埠农村','4','3','1',NULL),(90,21,'外埠城镇','3','4','1',NULL),(91,22,'大产权','8','1','1',NULL),(92,22,'宅基地','1','2','1',NULL),(93,22,'土地证','7','3','1',NULL),(94,22,'经济适用房','6','4','1',NULL),(95,22,'公租房','5','5','1',NULL),(96,22,'期房','4','6','1',NULL),(97,22,'小产权','3','7','1',NULL),(98,22,'自建房','2','8','1',NULL),(99,23,'环境和公共设施管理业','5','1','1',NULL),(100,23,'电力、热力、燃气及水的生产和供应业','4','2','1',NULL),(101,23,'制造业','3','3','1',NULL),(102,23,'批发和零售业','9','4','1',NULL),(103,23,'农、林、牧、渔业','1','5','1',NULL),(104,23,'综合（含投资类、主业不明显）','10','6','1',NULL),(105,23,'其它','11','7','1',NULL),(106,23,'建筑业','6','8','1',NULL),(107,23,'交通运输、仓储业和邮政业','7','9','1',NULL),(108,23,'信息传输、计算机服务和软件业','8','10','1',NULL),(109,23,'采矿业','2','11','1',NULL),(110,24,'信用','1','1','1',NULL),(111,24,'抵押','2','2','1',NULL),(112,24,'票据','3','3','1',NULL),(113,24,'个人/机构担保','4','4','1',NULL),(114,24,'其他','9','5','1',NULL),(115,25,'每月工资','1','1','1',NULL),(116,25,'其他收入','3','2','1',NULL),(117,25,'父母支付生活费','4','3','1',NULL),(118,25,'房屋出租','2','4','1',NULL),(119,26,'（月薪）1万以下','4','1','1',NULL),(120,26,'（月薪）3万以上','1','2','1',NULL),(121,26,'（月薪）2万-3万','2','3','1',NULL),(122,26,'（月薪）1万-2万','3','4','1',NULL),(123,27,'户籍联系电话','5','1','1',NULL),(124,27,'传真','4','2','1',NULL),(125,27,'手机','3','3','1',NULL),(126,27,'家庭电话','1','4','1',NULL),(127,27,'单位电话','2','5','1',NULL),(128,28,'其他','3','1','1',NULL),(129,28,'自有房产','2','2','1',NULL),(130,28,'租用','1','3','1',NULL),(131,29,'自营业主','1','1','1',NULL),(132,29,'自由职业者','5','2','1',NULL),(133,29,'离/退休人员','4','3','1',NULL),(134,29,'公司职员','2','4','1',NULL),(135,29,'其他','6','5','1',NULL),(136,30,'初级管理人员','4','1','1',NULL),(137,30,'中层管理人员','3','2','1',NULL),(138,30,'次高层管理人员','2','3','1',NULL),(139,30,'高层管理人员','1','4','1',NULL),(140,30,'普通员工','5','5','1',NULL),(141,31,'高级职称','1','1','1',NULL),(142,31,'中级职称','2','2','1',NULL),(143,31,'初级职称','3','3','1',NULL),(144,32,'同事','1','1','1',NULL),(145,32,'配偶','2','2','1',NULL),(146,32,'父亲','3','3','1',NULL),(147,32,'母亲','4','4','1',NULL),(148,32,'同学','7','5','1',NULL),(149,32,'朋友','6','6','1',NULL),(150,32,'其他亲属','5','7','1',NULL),(151,32,'其他','8','8','1',NULL),(152,32,'子女','9','9','1',NULL),(153,33,'紧急联系人','2','1','1',NULL),(154,33,'联系人','1','2','1',NULL),(155,33,'家庭联系人','3','3','1',NULL),(156,33,'工作联系人','4','4','1',NULL),(157,34,'其他','9','1','1',NULL),(158,34,'传真','4','2','1',NULL),(159,34,'QQ','3','3','1',NULL),(160,34,'邮件','2','4','1',NULL),(161,34,'电话','1','5','1',NULL),(162,35,'汉语','1','1','1',NULL),(163,35,'英语','2','2','1',NULL),(164,35,'日语','3','3','1',NULL),(165,35,'韩语','4','4','1',NULL),(166,35,'法语','5','5','1',NULL),(167,36,'一次性','1','1','1',NULL),(168,36,'双周','2','2','1',NULL),(169,36,'月度','3','3','1',NULL),(170,36,'季度','4','4','1',NULL),(171,36,'半年','5','5','1',NULL),(172,36,'年度','6','6','1',NULL),(185,38,'银行卡','1','1','1',NULL),(186,38,'存折','2','2','1',NULL),(187,39,'客户','1','1','1',NULL),(188,39,'企业名称','2','2','1',NULL),(189,39,'证件号码','3','3','1',NULL),(190,39,'电话','4','4','1',NULL),(191,39,'地址','5','5','1',NULL),(192,39,'电子邮件','6','6','1',NULL),(193,39,'员工','7','7','1',NULL),(194,39,'权证','8','8','1',NULL),(195,40,'20万（含）-100万','2','1','1',NULL),(196,40,'100万（含）-300万','1','2','1',NULL),(197,40,'20万以下','3','3','1',NULL),(198,40,'300万（含）以上','0','4','1',NULL),(199,41,'3年','2','1','1',NULL),(200,41,'2年','1','2','1',NULL),(201,41,'1年','0','3','1',NULL),(202,41,'4（含）-6年','3','4','1',NULL),(203,41,'大于6年（含）','4','5','1',NULL),(204,42,'居住证明','7','1','1',NULL),(205,42,'租房合同','6','2','1',NULL),(206,42,'信用卡邮寄函','5','3','1',NULL),(207,42,'固话单','4','4','1',NULL),(208,42,'水电煤气','3','5','1',NULL),(209,42,'使用权证','2','6','1',NULL),(210,42,'房产证','1','7','1',NULL),(211,42,'其他','8','8','1',NULL),(212,43,'雇佣','1','1','1',NULL),(213,43,'自营','2','2','1',NULL),(214,44,'工作证','1','1','1',NULL),(215,44,'工牌','2','2','1',NULL),(216,44,'劳动合同','3','3','1',NULL),(217,44,'工作证明','4','4','1',NULL),(218,44,'其他','5','5','1',NULL),(219,45,'空号','4','1','1',NULL),(220,45,'接通','5','2','1',NULL),(221,45,'关机','6','3','1',NULL),(222,45,'线路故障','7','4','1',NULL),(223,45,'无人接','1','5','1',NULL),(224,45,'拒接','2','6','1',NULL),(225,45,'停机','3','7','1',NULL),(226,46,'20（含）-30万','2','1','1',NULL),(227,46,'30（含）-50万','1','2','1',NULL),(228,46,'其他','3','3','1',NULL),(229,46,'大于等于50万','0','4','1',NULL),(230,47,'小于2000元','0','1','1',NULL),(231,47,'4000（含）-8000','2','2','1',NULL),(232,47,'8000（含）以上','3','3','1',NULL),(233,47,'2000（含）-4000','1','4','1',NULL),(234,48,'2套','1','1','1',NULL),(235,48,'1套','0','2','1',NULL),(236,48,'3套及以上','2','3','1',NULL),(237,49,'终止','3','1','1',NULL),(238,49,'中止执行','2','2','1',NULL),(239,49,'执行中案件','1','3','1',NULL),(240,49,'无信息','0','4','1',NULL),(241,49,'已结案','4','5','1',NULL),(242,49,'其它','5','6','1',NULL),(243,50,'朋友','4','1','1',NULL),(244,50,'其他','16','2','1',NULL),(245,50,'独居','8','3','1',NULL),(246,50,'配偶及子女','2','4','1',NULL),(247,50,'父母','1','5','1',NULL),(248,51,'均有','3','1','1',NULL),(249,51,'对公','2','2','1',NULL),(250,51,'个人','1','3','1',NULL),(251,52,'完税证明','3','1','1',NULL),(252,52,'公积金缴纳证明','2','2','1',NULL),(253,52,'社保缴纳证明','1','3','1',NULL),(254,52,'其他','4','4','1',NULL),(255,53,'股权证明','2','1','1',NULL),(256,53,'验资报告','1','2','1',NULL),(257,54,'30万以上','2','1','1',NULL),(258,54,'15万（含）以下','0','2','1',NULL),(259,54,'15-30万（含）','1','3','1',NULL),(260,55,'正常','0','1','1',NULL),(261,55,'抵债','3','2','1',NULL),(262,55,'结清','4','3','1',NULL),(263,55,'结束','5','4','1',NULL),(264,55,'逾期','1','5','1',NULL),(265,55,'代还','2','6','1',NULL),(266,55,'其他','7','7','1',NULL),(267,55,'未知','6','8','1',NULL),(268,56,'其它','3','1','1',NULL),(269,56,'房产','0','2','1',NULL),(270,56,'保单','2','3','1',NULL),(271,56,'车辆','1','4','1',NULL),(272,57,'其他','4','1','1',NULL),(273,57,'有价证券','3','2','1',NULL),(274,57,'房屋抵押合同','5','3','1',NULL),(275,57,'房产','1','4','1',NULL),(276,57,'车产','2','5','1',NULL),(277,58,'其他','6','1','1',NULL),(278,58,'结婚证','5','2','1',NULL),(279,58,'离婚证','7','3','1',NULL),(280,58,'身份证','1','4','1',NULL),(281,58,'军官证','2','5','1',NULL),(282,58,'户口本','3','6','1',NULL),(283,58,'护照','4','7','1',NULL),(284,59,'在职','1','1','1',NULL),(285,59,'自营','4','2','1',NULL),(286,59,'务农','3','3','1',NULL),(287,59,'退休','2','4','1',NULL),(288,59,'无业','5','5','1',NULL),(289,60,'四星级','2','1','1',NULL),(290,60,'三星级','3','2','1',NULL),(291,60,'五星级','1','3','1',NULL),(292,61,'58同城','1','1','1',NULL),(293,61,'本司','0','2','1',NULL),(294,62,'个人','1','1','1',NULL),(295,62,'单位','2','2','1',NULL),(296,63,'人民币','CNY','1','1',NULL),(297,63,'美元','USD','2','1',NULL),(298,63,'欧元','EUR','3','1',NULL),(299,63,'日元','JPY','4','1',NULL),(300,63,'英镑','GBP','5','1',NULL),(301,63,'港币','HKD','6','1',NULL),(302,63,'澳元','AUD','7','1',NULL),(303,63,'新台币','TWD','8','1',NULL),(304,64,'委托划扣','1','1','1',NULL),(305,64,'POS刷卡','2','2','1',NULL),(306,64,'转账','3','3','1',NULL),(307,65,'资金周转','1','1','1',NULL),(308,65,'购原材料','5','2','1',NULL),(309,65,'装修家居','6','3','1',NULL),(310,65,'其他','7','4','1',NULL),(311,65,'教育支出','3','5','1',NULL),(312,65,'购生活品','2','6','1',NULL),(313,65,'旅游','4','7','1',NULL),(314,66,'党员','1','1','1',NULL),(315,66,'团员','2','2','1',NULL),(316,66,'群众','3','3','1',NULL),(317,66,'民主党派','4','4','1',NULL),(323,68,'汉族','1','1','1',NULL),(324,68,'回族','2','2','1',NULL),(325,68,'满族','3','3','1',NULL),(326,68,'蒙古族','4','4','1',NULL),(327,68,'苗族','5','5','1',NULL),(328,68,'壮族','6','6','1',NULL),(329,68,'维吾尔族','7','7','1',NULL),(330,68,'朝鲜族','8','8','1',NULL),(331,68,'藏族','9','9','1',NULL),(332,68,'其他少数民族','10','10','1',NULL),(333,69,'日','1','1','1',NULL),(334,69,'月','2','2','1',NULL),(335,69,'年','3','3','1',NULL),(336,70,'贷款系统','S001','1','1',NULL),(337,70,'理财系统','S002','2','1',NULL),(338,70,'主数据系统','S003','3','1',NULL),(339,70,'合作机构','S004','4','1',NULL),(340,70,'核心系统','S005','5','0',NULL),(341,70,'核心日终','S006','6','1',NULL),(342,70,'第三方系统','S099','99','1',NULL),(343,71,'服务费','F001','1','1',NULL),(344,71,'停车费','F002','2','1',NULL),(345,71,'GPS安装费','F003','3','1',NULL),(346,71,'GPS拆卸费','F004','4','1',NULL),(347,71,'抵押登记费','F005','5','1',NULL),(348,71,'公证费','F006','6','1',NULL),(349,71,'其他费用','F999','7','1',NULL),(350,72,'工作流角色','2','3','1',NULL),(351,72,'系统角色','0','1','1',NULL),(352,72,'普通角色','1','2','1',NULL),(353,73,'组织','org','1','1',NULL),(354,73,'部门','dept','2','1',NULL),(355,74,'部门经理','manager','1','1',NULL),(356,74,'员工','staff','2','1',NULL),(357,75,'初始','0','1','1',NULL),(358,75,'生效','1','2','1',NULL),(359,75,'失效','2','3','1',NULL),(360,75,'删除','3','4','1',NULL),(361,76,'普通','0','1','1',NULL),(362,76,'紧急','1','2','1',NULL),(363,76,'特急','2','3','1',NULL),(364,77,'全局消息','0','1','1',NULL),(365,77,'专有消息','1','2','1',NULL),(366,78,'用户','user','1','1',NULL),(367,78,'机构','org','2','1',NULL),(368,79,'1期','1','0','1',NULL),(369,79,'2期','2','1','1',NULL),(370,79,'3期','3','2','1',NULL),(371,79,'4期','4','3','1',NULL),(372,79,'5期','5','4','1',NULL),(373,79,'6期','6','5','1',NULL),(374,79,'12期','12','6','1',NULL),(375,79,'18期','18','7','1',NULL),(376,79,'24期','24','8','1',NULL),(377,79,'36期','36','9','1',NULL),(378,79,'48期','48','10','1',NULL),(379,79,'30天','30','11','1',NULL),(380,80,'E贷','ED','0','1',NULL),(381,80,'有利网','YLW','1','1',NULL),(382,80,'向上','XS','2','1',NULL),(383,80,'正大','ZD','3','1',NULL),(384,80,'铜板街','TBJ','4','1',NULL),(385,80,'新天','XT','5','1',NULL),(386,80,'财客','CK','6','1',NULL),(387,80,'挖财','WC','7','1',NULL),(388,80,'安家世行','AJSX','8','1',NULL),(389,80,'捷越','JY','9','1',NULL),(391,70,'核心接口','S007','7','1',NULL),(392,70,'日志监控系统','S008','8','1',NULL),(401,81,'市场活动','1','1','1',NULL),(402,81,'coldcall','2','2','1',NULL),(403,81,'理财经理自由资源','3','3','1',NULL),(404,81,'宣传媒体','4','4','1',NULL),(405,81,'老客户续投','5','5','1',NULL),(406,81,'员工推荐','6','6','1',NULL),(407,81,'老客户推荐','7','7','1',NULL),(408,81,'其他','8','8','1',NULL),(435,102,'工商银行','102','1','1',NULL),(436,102,'农业银行','103','2','1',NULL),(437,102,'中国银行','104','3','1',NULL),(438,102,'建设银行','105','4','1',NULL),(439,102,'招商银行','308','5','1',NULL),(440,102,'交通银行','301','6','1',NULL),(441,102,'邮政储蓄银行','407','7','1',NULL),(442,102,'广发银行','306','8','1',NULL),(443,102,'兴业银行','309','9','1',NULL),(444,102,'广州银行','4135810','10','1',NULL),(445,102,'光大银行','303','11','1',NULL),(446,102,'民生银行','305','12','1',NULL),(447,102,'浦发银行','310','13','1',NULL),(448,102,'中信银行','302','14','1',NULL),(461,103,'一般','1','1','1',NULL),(462,103,'资讯','2','2','1',NULL),(463,103,'一般提醒','3','3','1',NULL),(464,103,'提醒','4','4','1',NULL),(465,103,'一般警告','5','5','1',NULL),(466,103,'警告','6','6','1',NULL),(467,103,'一般严重','7','7','1',NULL),(468,103,'严重','8','8','1',NULL),(469,103,'灾难','9','9','1',NULL),(470,104,'分钟','1','1','1',NULL),(471,104,'小时','2','2','1',NULL),(561,70,'押品系统','S009','9','1',NULL),(562,70,'销管系统','S011','11','1',NULL),(661,70,'快速录单系统','S010','10','1',NULL),(662,70,'报表门户平台','S012','12','1',NULL),(663,70,'贷款展业APP','SAPP001','100','1',NULL),(664,70,'理财APP','SAPP002','101','1',NULL),(665,70,'还款易APP','SAPP003','103','1',NULL),(666,203,'DTO','001','1','1',NULL),(667,203,'service','002','2','1',NULL),(668,203,'Controller','003','3','1',NULL),(669,203,'rest','004','4','1',NULL),(670,203,'Mapper','005','5','1',NULL),(671,203,'sql','006','6','1',NULL),(672,203,'JSP-JS','007','7','1',NULL),(673,70,'核心收付','S013','13','1',NULL),(761,303,'SYS_APP','1000','1','1',NULL),(861,70,'所有系统','ALL','0','1',NULL),(961,70,'录音录像系统','S014','104','1',NULL),(962,70,'档案管理系统','S015','105','1',NULL),(963,70,'电子合同','S081','81','0',NULL),(1061,70,'核心定时任务','S080','106','1',NULL),(1161,70,'核心存管PUB交易回调','S082','107','1',NULL),(1162,70,'核心存管SFTP','S083','108','1',NULL),(1163,70,'行销管理系统','S081','109','1',NULL),(1261,70,'部门精细化考评系统','S016','110','1',NULL),(1361,70,'房贷管理系统','S017','111','1',NULL),(1362,70,'向前APP','SAPP004','112','1',NULL),(1363,70,'水滴APP','SDAPP01','114','1',NULL),(1461,70,'房贷展业APP','SAPP005','113','1',NULL),(1462,70,'行销理财APP','SAPP006','115','1',NULL),(1463,70,'SAPP006','SAPP006','116','0',NULL),(1464,70,'行销理财APP','SAPP006','1','1',NULL);
/*!40000 ALTER TABLE `sys_dict_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_flume_config_zk`
--

LOCK TABLES `sys_flume_config_zk` WRITE;
/*!40000 ALTER TABLE `sys_flume_config_zk` DISABLE KEYS */;
INSERT INTO `sys_flume_config_zk` VALUES (1,'S001','172.19.100.33','agent','saved','app_${app_ip_underline}.sources = r1\r\napp_${app_ip_underline}.sinks = k1 k2\r\napp_${app_ip_underline}.channels = c1\r\n\r\napp_${app_ip_underline}.sources.r1.type = spooldir\r\napp_${app_ip_underline}.sources.r1.spoolDir = /home/jyapp/flume_logs\r\napp_${app_ip_underline}.sources.r1.deletePolicy = immediate\r\napp_${app_ip_underline}.sources.r1.ignorePattern = ^(.)*\\\\.tmp$\r\napp_${app_ip_underline}.sources.r1.deserializer = MULTILINE\r\napp_${app_ip_underline}.sources.r1.deserializer.maxLineLength = 60000\r\napp_${app_ip_underline}.sources.r1.interceptors = i1 i2 i3\r\napp_${app_ip_underline}.sources.r1.interceptors.i1.type = timestamp\r\napp_${app_ip_underline}.sources.r1.interceptors.i2.type = static\r\napp_${app_ip_underline}.sources.r1.interceptors.i2.key = flume.client.ip\r\napp_${app_ip_underline}.sources.r1.interceptors.i2.value = ${app_ip}\r\napp_${app_ip_underline}.sources.r1.interceptors.i3.type = static\r\napp_${app_ip_underline}.sources.r1.interceptors.i3.key = flume.client.system.flag\r\napp_${app_ip_underline}.sources.r1.interceptors.i3.value = ${app_flag}\r\n\r\napp_${app_ip_underline}.channels.c1.type = memory\r\napp_${app_ip_underline}.channels.c1.capacity = 1000\r\napp_${app_ip_underline}.channels.c1.transactionCapacity = 1000\r\napp_${app_ip_underline}.channels.c1.keep-alive = 3\r\napp_${app_ip_underline}.channels.c1.byteCapacityBufferPercentage = 30\r\napp_${app_ip_underline}.channels.c1.byteCapacity = 104857600\r\n\r\napp_${app_ip_underline}.sinks.k1.type = avro\r\napp_${app_ip_underline}.sinks.k1.hostname = 172.16.101.15\r\napp_${app_ip_underline}.sinks.k1.port = 44466\r\n\r\napp_${app_ip_underline}.sinks.k2.type = avro\r\napp_${app_ip_underline}.sinks.k2.hostname = 172.16.101.25\r\napp_${app_ip_underline}.sinks.k2.port = 44466\r\n\r\napp_${app_ip_underline}.sources.r1.channels = c1\r\napp_${app_ip_underline}.sinks.k1.channel = c1\r\napp_${app_ip_underline}.sinks.k2.channel = c1\r\n\r\napp_${app_ip_underline}.sinkgroups = g1\r\napp_${app_ip_underline}.sinkgroups.g1.sinks = k1 k2\r\napp_${app_ip_underline}.sinkgroups.g1.processor.type = load_balance\r\napp_${app_ip_underline}.sinkgroups.g1.processor.backoff = true\r\napp_${app_ip_underline}.sinkgroups.g1.processor.selector = round_robin','admin','管理员','2016-09-27 15:48:56.000000','admin','管理员','2017-10-26 17:03:19.679000');
/*!40000 ALTER TABLE `sys_flume_config_zk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_industry`
--

LOCK TABLES `sys_industry` WRITE;
/*!40000 ALTER TABLE `sys_industry` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_industry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_leave_info`
--

LOCK TABLES `sys_leave_info` WRITE;
/*!40000 ALTER TABLE `sys_leave_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_leave_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'101','系统管理','',NULL,'0','99','1',0,NULL,2),(2,'101-01','菜单管理','333',NULL,'1','1','1',1,NULL,3),(3,'101-02','机构管理','',NULL,'1','3','1',0,NULL,5),(4,'101-02-01','组织管理',NULL,NULL,'3','1','1',NULL,1,6),(5,'101-02-02','用户管理','',NULL,'3','2','1',0,NULL,7),(6,'101-02-03','岗位管理','',NULL,'3','3','1',0,NULL,8),(8,'101-06','操作权限','',NULL,'1','4','1',0,NULL,10),(9,'101-06-04','角色组管理','1',NULL,'8','4','1',1,NULL,11),(10,'101-06-01','角色管理',NULL,NULL,'8','1','1',1,1414740546193,12),(11,'101-06-02','资源管理',NULL,NULL,'8','1','1',1,1414980491644,13),(12,'101-06-03','角色资源授权管理',NULL,NULL,'8','1','1',1,1414980593081,14),(13,'101-08','流程管理','',NULL,'1','5','1',0,NULL,15),(14,'101-08-01','设计流程',NULL,NULL,'13','1','1',NULL,1,16),(15,'101-08-02','部署流程',NULL,NULL,'13','1','1',NULL,1,17),(16,'101-08-03','监控流程',NULL,NULL,'13','1','1',NULL,1,18),(17,'101-09','定时任务','',NULL,'1','7','1',0,NULL,19),(18,'101-09-01','任务查询','',NULL,'17','1','1',0,NULL,20),(19,'101-09-02','任务分组',NULL,NULL,'17','1','0',NULL,1,21),(20,'101-09-03','任务分组实例',NULL,NULL,'17','1','0',223,1,22),(21,'101-09-04','实例日志','',NULL,'17','2','1',223,NULL,23),(22,'101-10','业务权限管理','',NULL,'1','6','1',0,NULL,24),(23,'101-10-01','业务权限注册',NULL,NULL,'22','1','1',NULL,1,25),(24,'101-10-02','虚拟树管理',NULL,NULL,'22','1','1',NULL,1,26),(25,'101-10-03','映射管理',NULL,NULL,'22','1','1',NULL,1,27),(26,'101-10-04','业务用户配置',NULL,NULL,'22','1','1',NULL,1,28),(27,'101-10-05','数据权限查询',NULL,NULL,'22','1','1',NULL,1,29),(28,'101-03','参数配置','',NULL,'1','2','1',0,NULL,30),(29,'101-04','数据字典','',NULL,'1','9','1',0,NULL,31),(30,'101-05','日志审计','',NULL,'1','99','1',0,NULL,32),(31,'101-11','模板管理','',NULL,'1','10','1',1,NULL,33),(32,'101-12','消息管理','12',NULL,'1','98','1',12,NULL,34),(34,'101-14','日志统计','13',NULL,'1','99','1',14,NULL,36),(35,'101-15','安全策略','',NULL,'1','99','1',0,NULL,37),(37,'101-17','规则管理','',NULL,'1','8','0',0,NULL,39),(38,'101-17-01','业务模型管理',NULL,NULL,'37','1','0',NULL,1,40),(39,'101-17-02','规则配置',NULL,NULL,'37','1','0',NULL,1,41),(44,'101-18','系统版本管理','',NULL,'1','11','1',0,NULL,46),(51,'YHJS','用户解锁','1',NULL,'3','5','1',NULL,1479112556865,81),(52,'YWJK','业务流程监控','1',NULL,'13','4','1',NULL,1479112596357,88),(53,'MDDBJK','门店待办监控','1',NULL,'13','5','1',NULL,1479112624603,87),(54,'JJRGL','节假日管理','1',NULL,'13','6','1',NULL,1479112645657,60),(55,'JKLCQX','配置监控流程数据权限','1',NULL,'13','7','1',NULL,1479112679449,80),(60,'101-21','错误日志管理','101-21',NULL,'0','1','1',NULL,1506412822651,95),(61,'101-21-01','FTP管理','101-21-01',NULL,'60','1','0',NULL,1506412858980,102),(62,'101-21-02','错误日志','101-21-02',NULL,'60','2','1',NULL,1506412891043,103),(63,'101-21-03','日志级别','101-21-03',NULL,'60','3','1',NULL,1506412954283,96),(64,'101-21-04','日志提醒设置','101-21-04',NULL,'60','4','1',NULL,1506412981998,98),(65,'101-21-05','日志访问权限','101-21-05',NULL,'60','5','1',NULL,1506413030685,97),(66,'101-21-06','日志监控集中配置','101-21-06',NULL,'60','6','1',NULL,1506413066439,101),(67,'101-21-07','错误率统计','101-21-07',NULL,'60','7','1',NULL,1506413107396,99),(68,'101-21-08','错误率占比','101-21-08',NULL,'60','8','1',NULL,1506413129772,100),(69,'smrz','实名认证','smrz',NULL,'0','1','0',NULL,1507709687793,104),(70,'pzgl','配置管理','pzgl',NULL,'69','1','0',NULL,1507709718595,105),(71,'rzgl','认证管理','rzgl',NULL,'69','2','0',NULL,1507709748074,106);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_message`
--

LOCK TABLES `sys_message` WRITE;
/*!40000 ALTER TABLE `sys_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_message_his`
--

LOCK TABLES `sys_message_his` WRITE;
/*!40000 ALTER TABLE `sys_message_his` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_message_his` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_org`
--

LOCK TABLES `sys_org` WRITE;
/*!40000 ALTER TABLE `sys_org` DISABLE KEYS */;
INSERT INTO `sys_org` VALUES (6,'jylh001','捷越联合','org',' 0','/6/','1','1','1',1416485544100,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(101,'101','捷越总部','org','6','/6/','2','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(201,'201','捷越财富','org','6','/6/','18','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(301,'301','捷越资信','org','6','/6/','89','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20101,'20101','北区财富中心','org','201','/6/201/','19','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20102,'20102','西区财富中心','org','201','/6/201/','72','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(2010101,'2010101','北区管理部','org','20101','/6/201/20101/','20','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(2010102,'2010102','长春一分','org','20101','/6/201/20101/','30','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(2010201,'2010201','西区管理部','org','20102','/6/201/20102/','76','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(2010202,'2010202','南京分公司','org','20102','/6/201/20102/','81','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(2010203,'2010203','长春二分','org','20101','/6/201/20101/','34','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(2010304,'2010304','北京分公司','org','20101','/6/201/20101/','40','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(2010405,'2010405','河南分公司','org','20101','/6/201/20101/','43','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(2010506,'2010506','哈尔滨分公司','org','20101','/6/201/20101/','46','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(2010607,'2010607','青岛分公司','org','20101','/6/201/20101/','49','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(2010708,'2010708','大庆分公司','org','20101','/6/201/20101/','69','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010001,'3010001','资信管理部','org','301','/6/301/','90','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010002,'3010002','苏北分部','org','301','/6/301/','100','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010003,'3010003','安徽分部','org','301','/6/301/','110','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010004,'3010004','沪浙分部','org','301','/6/301/','122','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010005,'3010005','苏南分部','org','301','/6/301/','133','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010006,'3010006','湖南分部','org','301','/6/301/','140','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010007,'3010007','河南分部','org','301','/6/301/','146','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010008,'3010008','山东分部','org','301','/6/301/','157','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010009,'3010009','辽宁分部','org','301','/6/301/','169','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010010,'3010010','云贵分部','org','301','/6/301/','175','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010011,'3010011','南粤分部','org','301','/6/301/','189','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010012,'3010012','吉林分部','org','301','/6/301/','199','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010013,'3010013','川渝分部','org','301','/6/301/','204','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010014,'3010014','广东分部','org','301','/6/301/','215','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010015,'3010015','广西分部','org','301','/6/301/','227','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010016,'3010016','江西分部','org','301','/6/301/','236','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010017,'3010017','湖北分部','org','301','/6/301/','246','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(3010018,'3010018','福建分部','org','301','/6/301/','256','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(201010201,'201010201','长春营业一部','org','2010102','/6/201/20101/2010102/','31','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201010202,'201010202','长春营业四部','org','2010102','/6/201/20101/2010102/','32','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201010203,'201010203','长春营业六部','org','2010102','/6/201/20101/2010102/','33','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020001,'201020001','西安营业部','org','20102','/6/201/20102/','73','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020002,'201020002','重庆营业部','org','20102','/6/201/20102/','74','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020003,'201020003','敦煌营业部','org','20102','/6/201/20102/','75','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020004,'201020004','银川营业部','org','20102','/6/201/20102/','264','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020200,'201020200','城市管理部','dept','2010202','/6/201/20102/2010202/','265','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(201020201,'201020201','南京营业一部','org','2010202','/6/201/20102/2010202/','86','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020202,'201020202','南京营业二部','org','2010202','/6/201/20102/2010202/','87','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020203,'201020203','溧阳营业部','org','2010202','/6/201/20102/2010202/','88','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020301,'201020301','长春营业二部','org','2010203','/6/201/20101/2010203/','35','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020302,'201020302','长春营业三部','org','2010203','/6/201/20101/2010203/','36','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020303,'201020303','长春营业五部','org','2010203','/6/201/20101/2010203/','37','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020304,'201020304','长春营业七部','org','2010203','/6/201/20101/2010203/','38','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201020305,'201020305','长春体验店','org','2010203','/6/201/20101/2010203/','39','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201030401,'201030401','北京营业部','org','2010304','/6/201/20101/2010304/','41','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201030402,'201030402','房山门店','org','2010304','/6/201/20101/2010304/','42','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201040501,'201040501','郑州营业部','org','2010405','/6/201/20101/2010405/','44','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201040502,'201040502','洛阳营业部','org','2010405','/6/201/20101/2010405/','45','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201050601,'201050601','哈尔滨营业一部（常青）','org','2010506','/6/201/20101/2010506/','47','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201050602,'201050602','哈尔滨营业二部（红军街）','org','2010506','/6/201/20101/2010506/','48','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201060701,'201060701','青岛营业一部','org','2010607','/6/201/20101/2010607/','50','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201060702,'201060702','青岛营业二部','org','2010607','/6/201/20101/2010607/','51','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070001,'201070001','吉林营业部','org','20101','/6/201/20101/','52','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070002,'201070002','白山营业部','org','20101','/6/201/20101/','53','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070003,'201070003','大连营业部','org','20101','/6/201/20101/','54','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070004,'201070004','沈阳营业部','org','20101','/6/201/20101/','55','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070005,'201070005','延吉营业部','org','20101','/6/201/20101/','56','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070006,'201070006','牡丹江营业部','org','20101','/6/201/20101/','57','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070007,'201070007','齐齐哈尔营业部','org','20101','/6/201/20101/','58','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070008,'201070008','烟台营业部','org','20101','/6/201/20101/','59','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070009,'201070009','威海营业部','org','20101','/6/201/20101/','60','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070010,'201070010','文登营业部','org','20101','/6/201/20101/','61','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070011,'201070011','济南营业部','org','20101','/6/201/20101/','62','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070012,'201070012','临沂营业部','org','20101','/6/201/20101/','63','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070013,'201070013','绥芬河营业部','org','20101','/6/201/20101/','64','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070014,'201070014','潍坊营业部','org','20101','/6/201/20101/','65','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070015,'201070015','寿光营业部','org','20101','/6/201/20101/','66','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070016,'201070016','厦门营业部','org','20101','/6/201/20101/','67','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070017,'201070017','佳木斯营业部','org','20101','/6/201/20101/','68','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070801,'201070801','大庆营业一部','org','2010708','/6/201/20101/2010708/','70','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(201070802,'201070802','大庆营业二部','org','2010708','/6/201/20101/2010708/','71','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301000201,'301000201','分部管理部','org','3010002','/6/301/3010002/','101','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301000202,'301000202','南京营业一部','org','3010002','/6/301/3010002/','102','1','0',0,NULL,'1','320100','江苏省/南京市/太平区',NULL,NULL,NULL),(301000203,'301000203','南京营业二部','org','3010002','/6/301/3010002/','103','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301000204,'301000204','南通营业一部','org','3010002','/6/301/3010002/','104','1','0',0,NULL,'1','320600','江苏省/南通市/崇川区',NULL,NULL,NULL),(301000205,'301000205','徐州营业一部','org','3010002','/6/301/3010002/','105','1','0',0,NULL,'1','320300','江苏省/徐州市/泉山区',NULL,NULL,NULL),(301000206,'301000206','扬州营业一部','org','3010002','/6/301/3010002/','106','1','0',0,NULL,'1','321000','江苏省/扬州市/邗江区',NULL,NULL,NULL),(301000207,'301000207','泰州营业一部','org','3010002','/6/301/3010002/','107','1','0',0,NULL,'1','321200','江苏省/泰州市/海陵区',NULL,NULL,NULL),(301000208,'301000208','连云港营业一部','org','3010002','/6/301/3010002/','108','1','0',0,NULL,'1','320700','江苏省/连云港市/新浦区',NULL,NULL,NULL),(301000209,'301000209','镇江营业一部','org','3010002','/6/301/3010002/','109','1','0',0,NULL,'1','321100','江苏省/镇江市/润州区',NULL,NULL,NULL),(301000301,'301000301','分部管理部','org','3010003','/6/301/3010003/','111','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301000302,'301000302','车贷部','org','3010003','/6/301/3010003/','112','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301000303,'301000303','合肥营业一部','org','3010003','/6/301/3010003/','113','1','0',0,NULL,'1','340100','安徽省/合肥市/蜀山区',NULL,NULL,NULL),(301000304,'301000304','合肥营业二部','org','3010003','/6/301/3010003/','114','1','0',0,NULL,'1','340100','安徽省/合肥市/庐阳区',NULL,NULL,NULL),(301000305,'301000305','安庆营业一部','org','3010003','/6/301/3010003/','115','1','0',0,NULL,'1','340800','安徽省/安庆市/迎江区',NULL,NULL,NULL),(301000306,'301000306','宣城营业一部','org','3010003','/6/301/3010003/','116','1','0',0,NULL,'1','341800','安徽省/宣城市/宣州区',NULL,NULL,NULL),(301000307,'301000307','滁州营业一部','org','3010003','/6/301/3010003/','117','1','0',0,NULL,'1','341100','安徽省/滁州市/南谯区',NULL,NULL,NULL),(301000308,'301000308','芜湖营业一部','org','3010003','/6/301/3010003/','118','1','0',0,NULL,'1','340200','安徽省/芜湖市/镜湖区',NULL,NULL,NULL),(301000309,'301000309','蚌埠营业一部','org','3010003','/6/301/3010003/','119','1','0',0,NULL,'1','340300','安徽省/蚌埠市/禹会区',NULL,NULL,NULL),(301000310,'301000310','阜阳营业一部','org','3010003','/6/301/3010003/','120','1','0',0,NULL,'1','341200','安徽省/阜阳市/颍泉区',NULL,NULL,NULL),(301000311,'301000311','马鞍山营业一部','org','3010003','/6/301/3010003/','121','1','0',0,NULL,'1','340500','安徽省/马鞍山市/花山区',NULL,NULL,NULL),(301000401,'301000401','分部管理部','org','3010004','/6/301/3010004/','123','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301000402,'301000402','上海营业一部','org','3010004','/6/301/3010004/','124','1','0',0,NULL,'1','310100','上海市/浦东新区',NULL,NULL,NULL),(301000403,'301000403','台州营业一部','org','3010004','/6/301/3010004/','125','1','0',0,NULL,'1','331000','浙江省/台州市/椒江区',NULL,NULL,NULL),(301000404,'301000404','嘉兴营业一部','org','3010004','/6/301/3010004/','126','1','0',0,NULL,'1','330400','浙江省/嘉兴市/南湖区',NULL,NULL,NULL),(301000405,'301000405','宁波营业一部','org','3010004','/6/301/3010004/','127','1','0',0,NULL,'1','330200','浙江省/宁波市/海曙区',NULL,NULL,NULL),(301000406,'301000406','杭州营业一部','org','3010004','/6/301/3010004/','128','1','0',0,NULL,'1','330100','浙江省/杭州市/上城区',NULL,NULL,NULL),(301000407,'301000407','湖州营业一部','org','3010004','/6/301/3010004/','129','1','0',0,NULL,'1','330500','浙江省/湖州市/吴兴区',NULL,NULL,NULL),(301000408,'301000408','绍兴营业一部','org','3010004','/6/301/3010004/','130','1','0',0,NULL,'1','330600','浙江省/绍兴市/越城区',NULL,NULL,NULL),(301000409,'301000409','舟山营业一部','org','3010004','/6/301/3010004/','131','1','0',0,NULL,'1','330900','浙江省/舟山市/普陀区',NULL,NULL,NULL),(301000410,'301000410','金华营业一部','org','3010004','/6/301/3010004/','132','1','0',0,NULL,'1','330700','浙江省/金华市/婺城区',NULL,NULL,NULL),(301000501,'301000501','分部管理部','org','3010005','/6/301/3010005/','134','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301000502,'301000502','常州营业一部','org','3010005','/6/301/3010005/','135','1','0',0,NULL,'1','320400','江苏省/常州市/钟楼区',NULL,NULL,NULL),(301000503,'301000503','张家港营业部','org','3010005','/6/301/3010005/','136','1','0',0,NULL,'1','320500','江苏省/苏州市/张家港市',NULL,NULL,NULL),(301000504,'301000504','无锡营业一部','org','3010005','/6/301/3010005/','137','1','0',0,NULL,'1','320200','江苏省/无锡市/崇安区',NULL,NULL,NULL),(301000505,'301000505','昆山营业一部','org','3010005','/6/301/3010005/','138','1','0',0,NULL,'1','320500','江苏省/昆山市/昆山开发区',NULL,NULL,NULL),(301000506,'301000506','苏州营业一部','org','3010005','/6/301/3010005/','139','1','0',0,NULL,'1','320500','江苏省/苏州市/高新区',NULL,NULL,NULL),(301000601,'301000601','分部管理部','org','3010006','/6/301/3010006/','141','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301000602,'301000602','岳阳营业一部','org','3010006','/6/301/3010006/','142','1','0',0,NULL,'1','430600','湖南省/岳阳市/岳阳楼区',NULL,NULL,NULL),(301000603,'301000603','常德营业一部','org','3010006','/6/301/3010006/','143','1','0',0,NULL,'1','430700','湖南省/常德市/武陵区',NULL,NULL,NULL),(301000604,'301000604','株洲营业一部','org','3010006','/6/301/3010006/','144','1','0',0,NULL,'1','430200','湖南省/株洲市/天元区',NULL,NULL,NULL),(301000605,'301000605','长沙营业一部','org','3010006','/6/301/3010006/','145','1','0',0,NULL,'1','430100','湖南省/长沙市/芙蓉区',NULL,NULL,NULL),(301000701,'301000701','分部管理部','org','3010007','/6/301/3010007/','147','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301000702,'301000702','南阳营业一部','org','3010007','/6/301/3010007/','148','1','0',0,NULL,'1','411300','河南省/南阳市/卧龙区',NULL,NULL,NULL),(301000703,'301000703','平顶山营业一部','org','3010007','/6/301/3010007/','149','1','0',0,NULL,'1','410400','河南省/平顶山市/新华区',NULL,NULL,NULL),(301000704,'301000704','开封营业一部','org','3010007','/6/301/3010007/','150','1','0',0,NULL,'1','410200','河南省/开封市/鼓楼区',NULL,NULL,NULL),(301000705,'301000705','新乡营业一部','org','3010007','/6/301/3010007/','151','1','0',0,NULL,'1','410700','河南省/新乡市/红旗区',NULL,NULL,NULL),(301000706,'301000706','洛阳营业一部','org','3010007','/6/301/3010007/','152','1','0',0,NULL,'1','410300','河南省/洛阳市/洛龙区',NULL,NULL,NULL),(301000707,'301000707','洛阳营业二部','org','3010007','/6/301/3010007/','153','1','0',0,NULL,'1','410300','河南省/洛阳市/西工区',NULL,NULL,NULL),(301000708,'301000708','焦作营业一部','org','3010007','/6/301/3010007/','154','1','0',0,NULL,'1','410800','河南省/焦作市/解放区',NULL,NULL,NULL),(301000709,'301000709','许昌营业一部','org','3010007','/6/301/3010007/','155','1','0',0,NULL,'1','411000','河南省/许昌市/城东区',NULL,NULL,NULL),(301000710,'301000710','郑州营业一部','org','3010007','/6/301/3010007/','156','1','0',0,NULL,'1','410100','河南省/郑州市/金水区',NULL,NULL,NULL),(301000801,'301000801','分部管理部','org','3010008','/6/301/3010008/','158','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301000802,'301000802','东营营业一部','org','3010008','/6/301/3010008/','159','1','0',0,NULL,'1','370500','山东省/东营市/东营区',NULL,NULL,NULL),(301000803,'301000803','临沂营业一部','org','3010008','/6/301/3010008/','160','1','0',0,NULL,'1','371300','山东省/临沂市/兰山区',NULL,NULL,NULL),(301000804,'301000804','威海营业一部','org','3010008','/6/301/3010008/','161','1','0',0,NULL,'1','371000','山东省/威海市/环翠区',NULL,NULL,NULL),(301000805,'301000805','济南营业一部','org','3010008','/6/301/3010008/','162','1','0',0,NULL,'1','370100','山东省/济南市/历下区',NULL,NULL,NULL),(301000806,'301000806','济宁营业一部','org','3010008','/6/301/3010008/','163','1','0',0,NULL,'1','370800','山东省/济宁市/任城区',NULL,NULL,NULL),(301000807,'301000807','淄博营业一部','org','3010008','/6/301/3010008/','164','1','0',0,NULL,'1','370300','山东省/淄博市/张店区',NULL,NULL,NULL),(301000808,'301000808','潍坊营业一部','org','3010008','/6/301/3010008/','165','1','0',0,NULL,'1','370700','山东省/潍坊市/奎文区',NULL,NULL,NULL),(301000809,'301000809','烟台营业一部','org','3010008','/6/301/3010008/','166','1','0',0,NULL,'1','370600','山东省/烟台市/芝罘区',NULL,NULL,NULL),(301000810,'301000810','青岛营业一部','org','3010008','/6/301/3010008/','167','1','0',0,NULL,'1','370200','山东省/青岛市/市南区',NULL,NULL,NULL),(301000811,'301000811','青岛营业二部','org','3010008','/6/301/3010008/','168','1','0',0,NULL,'1','370200','山东省/青岛市/李沧区',NULL,NULL,NULL),(301000901,'301000901','分部管理部','org','3010009','/6/301/3010009/','170','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301000902,'301000902','大连营业一部','org','3010009','/6/301/3010009/','171','1','0',0,NULL,'1','210200','辽宁省/大连市/开发区',NULL,NULL,NULL),(301000903,'301000903','沈阳营业一部','org','3010009','/6/301/3010009/','172','1','0',0,NULL,'1','210100','辽宁省/沈阳市/沈河区',NULL,NULL,NULL),(301000904,'301000904','营口营业一部','org','3010009','/6/301/3010009/','173','1','0',0,NULL,'1','210800','辽宁省/营口市/鲅鱼圈区',NULL,NULL,NULL),(301000905,'301000905','鞍山营业一部','org','3010009','/6/301/3010009/','174','1','0',0,NULL,'1','210300','辽宁省/鞍山市/铁东区',NULL,NULL,NULL),(301001001,'301001001','分部管理部','org','3010010','/6/301/3010010/','176','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001002,'301001002','车贷分中心','org','3010010','/6/301/3010010/','177','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001003,'301001003','六盘水营业一部','org','3010010','/6/301/3010010/','178','1','0',0,NULL,'1','520200','贵州省/六盘水市/钟山区',NULL,NULL,NULL),(301001004,'301001004','大理营业一部','org','3010010','/6/301/3010010/','179','1','0',0,NULL,'1','532900','云南省/大理白族自治州',NULL,NULL,NULL),(301001005,'301001005','攀枝花营业一部','org','3010010','/6/301/3010010/','180','1','0',0,NULL,'1','510400','四川省/攀枝花市/东区',NULL,NULL,NULL),(301001006,'301001006','昆明营业一部','org','3010010','/6/301/3010010/','181','1','0',0,NULL,'1','530100','云南省/昆明市/五华区',NULL,NULL,NULL),(301001007,'301001007','昆明营业二部','org','3010010','/6/301/3010010/','182','1','0',0,NULL,'1','530100','云南省/昆明市/西山区',NULL,NULL,NULL),(301001008,'301001008','曲靖营业一部','org','3010010','/6/301/3010010/','183','1','0',0,NULL,'1','530300','云南省/曲靖市/麒麟区',NULL,NULL,NULL),(301001009,'301001009','毕节营业一部','org','3010010','/6/301/3010010/','184','1','0',0,NULL,'1','520500','贵州省/毕节市',NULL,NULL,NULL),(301001010,'301001010','玉溪营业一部','org','3010010','/6/301/3010010/','185','1','0',0,NULL,'1','530400','云南省/玉溪市/红塔区',NULL,NULL,NULL),(301001011,'301001011','红河营业一部','org','3010010','/6/301/3010010/','186','1','0',0,NULL,'1','532500','云南省/红河哈尼族彝族自治州/蒙自市',NULL,NULL,NULL),(301001012,'301001012','贵阳营业一部','org','3010010','/6/301/3010010/','187','1','0',0,NULL,'1','520100','贵州省/贵阳市/南明区',NULL,NULL,NULL),(301001013,'301001013','遵义营业一部','org','3010010','/6/301/3010010/','188','1','0',0,NULL,'1','520300','贵州省/遵义市/汇川区',NULL,NULL,NULL),(301001101,'301001101','分部管理部','org','3010011','/6/301/3010011/','190','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001102,'301001102','车贷分中心','org','3010011','/6/301/3010011/','191','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001103,'301001103','江门营业一部','org','3010011','/6/301/3010011/','192','1','0',0,NULL,'1','440700','广东省/江门市/新会区',NULL,NULL,NULL),(301001104,'301001104','海口营业一部','org','3010011','/6/301/3010011/','193','1','0',0,NULL,'1','460100','海南省/海口市/龙华区',NULL,NULL,NULL),(301001105,'301001105','海口营业二部','org','3010011','/6/301/3010011/','194','1','0',0,NULL,'1','460100','海南省/海口市/龙华区',NULL,NULL,NULL),(301001106,'301001106','深圳营业一部','org','3010011','/6/301/3010011/','195','1','0',0,NULL,'1','440300','广东省/深圳市',NULL,NULL,NULL),(301001107,'301001107','湛江营业一部','org','3010011','/6/301/3010011/','196','1','0',0,NULL,'1','440800','广东省/湛江市/霞山区',NULL,NULL,NULL),(301001108,'301001108','湛江营业二部','org','3010011','/6/301/3010011/','197','1','0',0,NULL,'1','440800','广东省/湛江市',NULL,NULL,NULL),(301001109,'301001109','茂名营业一部','org','3010011','/6/301/3010011/','198','1','0',0,NULL,'1','440900','广东省/茂名市/茂南区',NULL,NULL,NULL),(301001201,'301001201','分部管理部','org','3010012','/6/301/3010012/','200','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001202,'301001202','吉林营业一部','org','3010012','/6/301/3010012/','201','1','0',0,NULL,'1','220200','吉林省/吉林市/丰满区',NULL,NULL,NULL),(301001203,'301001203','四平营业一部','org','3010012','/6/301/3010012/','202','1','0',0,NULL,'1','220300','吉林省/四平市/铁西区',NULL,NULL,NULL),(301001204,'301001204','长春营业一部','org','3010012','/6/301/3010012/','203','1','0',0,NULL,'1','220100','吉林省/长春市/朝阳区',NULL,NULL,NULL),(301001301,'301001301','分部管理部','org','3010013','/6/301/3010013/','205','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001302,'301001302','乐山营业一部','org','3010013','/6/301/3010013/','206','1','0',0,NULL,'1','511100','四川省/乐山市/市中区',NULL,NULL,NULL),(301001303,'301001303','南充营业一部','org','3010013','/6/301/3010013/','207','1','0',0,NULL,'1','511300','四川省/南充市/高坪区',NULL,NULL,NULL),(301001304,'301001304','宜宾营业一部','org','3010013','/6/301/3010013/','208','1','0',0,NULL,'1','511500','四川省/宜宾市/翠屏区',NULL,NULL,NULL),(301001305,'301001305','德阳营业一部','org','3010013','/6/301/3010013/','209','1','0',0,NULL,'1','510600','四川省/德阳市/旌阳区',NULL,NULL,NULL),(301001306,'301001306','成都营业一部','org','3010013','/6/301/3010013/','210','1','0',0,NULL,'1','510100','四川省/成都市/锦江区',NULL,NULL,NULL),(301001307,'301001307','成都营业二部','org','3010013','/6/301/3010013/','211','1','0',0,NULL,'1','510100','四川省/成都市/武侯区',NULL,NULL,NULL),(301001308,'301001308','绵阳营业一部','org','3010013','/6/301/3010013/','212','1','0',0,NULL,'1','510700','四川省/绵阳市/涪城区',NULL,NULL,NULL),(301001309,'301001309','重庆营业一部','org','3010013','/6/301/3010013/','213','1','0',0,NULL,'1','500100','重庆市/重庆市/渝中区',NULL,NULL,NULL),(301001310,'301001310','重庆营业二部','org','3010013','/6/301/3010013/','214','1','0',0,NULL,'1','500100','重庆市/重庆市/渝中区',NULL,NULL,NULL),(301001401,'301001401','分部管理部','org','3010014','/6/301/3010014/','216','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001402,'301001402','东莞营业一部','org','3010014','/6/301/3010014/','217','1','0',0,NULL,'1','441900','广东省/东莞市/南城区',NULL,NULL,NULL),(301001403,'301001403','东莞营业二部','org','3010014','/6/301/3010014/','218','1','0',0,NULL,'1','441900','广东省/东莞市',NULL,NULL,NULL),(301001404,'301001404','广州营业一部','org','3010014','/6/301/3010014/','219','1','0',0,NULL,'1','440100','广东省/广州市/天河区',NULL,NULL,NULL),(301001405,'301001405','广州营业二部','org','3010014','/6/301/3010014/','220','1','0',0,NULL,'1','440100','广东省/广州市/越秀区',NULL,NULL,NULL),(301001406,'301001406','中山营业一部','org','3010014','/6/301/3010014/','221','1','0',0,NULL,'1','442000','广东省/中山市/石岐区',NULL,NULL,NULL),(301001407,'301001407','佛山营业一部','org','3010014','/6/301/3010014/','222','1','0',0,NULL,'1','440600','广东省/佛山市/禅城区',NULL,NULL,NULL),(301001408,'301001408','惠州营业一部','org','3010014','/6/301/3010014/','223','1','0',0,NULL,'1','441300','广东省/惠州市/惠城区',NULL,NULL,NULL),(301001409,'301001409','河源营业一部','org','3010014','/6/301/3010014/','224','1','0',0,NULL,'1','441600','广东省/河源市/源城区',NULL,NULL,NULL),(301001410,'301001410','清远营业一部','org','3010014','/6/301/3010014/','225','1','0',0,NULL,'1','441800','广东省/清远市/新城区',NULL,NULL,NULL),(301001411,'301001411','韶关营业一部','org','3010014','/6/301/3010014/','226','1','0',0,NULL,'1','440200','广东省/韶关市/浈江区',NULL,NULL,NULL),(301001501,'301001501','分部管理部','org','3010015','/6/301/3010015/','228','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001502,'301001502','南宁营业一部','org','3010015','/6/301/3010015/','229','1','0',0,NULL,'1','450100','广西壮族自治区/南宁市/青秀区',NULL,NULL,NULL),(301001503,'301001503','南宁营业二部','org','3010015','/6/301/3010015/','230','1','0',0,NULL,'1','450100','广西壮族自治区/南宁市/青秀区',NULL,NULL,NULL),(301001504,'301001504','柳州营业一部','org','3010015','/6/301/3010015/','231','1','0',0,NULL,'1','450200','广西壮族自治区/柳州市/鱼峰区',NULL,NULL,NULL),(301001505,'301001505','桂林营业一部','org','3010015','/6/301/3010015/','232','1','0',0,NULL,'1','450300','广西省/桂林市/秀峰区',NULL,NULL,NULL),(301001506,'301001506','梧州营业一部','org','3010015','/6/301/3010015/','233','1','0',0,NULL,'1','450400','广西省/梧州市/长洲区',NULL,NULL,NULL),(301001507,'301001507','玉林营业一部','org','3010015','/6/301/3010015/','234','1','0',0,NULL,'1','450900','广西省/玉林市/玉州区',NULL,NULL,NULL),(301001508,'301001508','钦州营业一部','org','3010015','/6/301/3010015/','235','1','0',0,NULL,'1','450700','广西壮族自治区/钦州市/钦南区',NULL,NULL,NULL),(301001601,'301001601','分部管理部','org','3010016','/6/301/3010016/','237','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001602,'301001602','车贷分中心','org','3010016','/6/301/3010016/','238','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001603,'301001603','上饶营业一部','org','3010016','/6/301/3010016/','239','1','0',0,NULL,'1','361100','江西省/上饶市',NULL,NULL,NULL),(301001604,'301001604','九江营业一部','org','3010016','/6/301/3010016/','240','1','0',0,NULL,'1','360400','江西省/九江市/浔阳区',NULL,NULL,NULL),(301001605,'301001605','南昌营业一部','org','3010016','/6/301/3010016/','241','1','0',0,NULL,'1','360100','江西省/南昌市/西湖区',NULL,NULL,NULL),(301001606,'301001606','宜春营业一部','org','3010016','/6/301/3010016/','242','1','0',0,NULL,'1','360900','江西省/宜春市/袁州区',NULL,NULL,NULL),(301001607,'301001607','赣州营业一部','org','3010016','/6/301/3010016/','243','1','0',0,NULL,'1','360700','江西省/赣州市/章贡区',NULL,NULL,NULL),(301001608,'301001608','黄冈营业一部','org','3010016','/6/301/3010016/','244','1','0',0,NULL,'1','421100','湖北省/黄冈市/黄州区',NULL,NULL,NULL),(301001609,'301001609','黄石营业一部','org','3010016','/6/301/3010016/','245','1','0',0,NULL,'1','420200','湖北省/黄石市/下陆区',NULL,NULL,NULL),(301001701,'301001701','分部管理部','org','3010017','/6/301/3010017/','247','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001702,'301001702','十堰营业一部','org','3010017','/6/301/3010017/','248','1','0',0,NULL,'1','420300','湖北省/十堰市/茅箭区',NULL,NULL,NULL),(301001703,'301001703','孝感营业一部','org','3010017','/6/301/3010017/','249','1','0',0,NULL,'1','420900','湖北省/孝感市/孝南区',NULL,NULL,NULL),(301001704,'301001704','宜昌营业一部','org','3010017','/6/301/3010017/','250','1','0',0,NULL,'1','420500','湖北省/宜昌市/西陵区',NULL,NULL,NULL),(301001705,'301001705','武汉营业一部','org','3010017','/6/301/3010017/','251','1','0',0,NULL,'1','420100','湖北省/武汉市/武昌区',NULL,NULL,NULL),(301001706,'301001706','武汉营业二部','org','3010017','/6/301/3010017/','252','1','0',0,NULL,'1','420100','湖北省/武汉市/江汉区',NULL,NULL,NULL),(301001707,'301001707','荆州营业一部','org','3010017','/6/301/3010017/','253','1','0',0,NULL,'1','421000','湖北省/荆州市/沙市区',NULL,NULL,NULL),(301001708,'301001708','荆门营业一部','org','3010017','/6/301/3010017/','254','1','0',0,NULL,'1','420800','湖北省/荆门市/东宝区',NULL,NULL,NULL),(301001709,'301001709','襄阳营业一部','org','3010017','/6/301/3010017/','255','1','0',0,NULL,'1','420600','湖北省/襄阳市/樊城区',NULL,NULL,NULL),(301001801,'301001801','分部管理部','org','3010018','/6/301/3010018/','257','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(301001802,'301001802','三明营业一部','org','3010018','/6/301/3010018/','258','1','0',0,NULL,'1','350400','福建省/三明市/梅列区',NULL,NULL,NULL),(301001803,'301001803','厦门营业一部','org','3010018','/6/301/3010018/','259','1','0',0,NULL,'1','350200','福建省/厦门市/思明区',NULL,NULL,NULL),(301001804,'301001804','泉州营业一部','org','3010018','/6/301/3010018/','260','1','0',0,NULL,'1','350500','福建省/泉州市/丰泽区',NULL,NULL,NULL),(301001805,'301001805','漳州营业一部','org','3010018','/6/301/3010018/','261','1','0',0,NULL,'1','350600','福建省/漳州市/龙文区',NULL,NULL,NULL),(301001806,'301001806','福州营业一部','org','3010018','/6/301/3010018/','262','1','0',0,NULL,'1','350100','福建省/福州市/台江区',NULL,NULL,NULL),(301001807,'301001807','龙岩营业一部','org','3010018','/6/301/3010018/','263','1','0',0,NULL,'1','350800','福建省/龙岩市/新罗区',NULL,NULL,NULL),(10100000001,'10100000001','总裁办','dept','101','/6/101/','3','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000002,'10100000002','行政部','dept','101','/6/101/','4','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000003,'10100000003','财务部','dept','101','/6/101/','5','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000004,'10100000004','人力资源部','dept','101','/6/101/','6','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000005,'10100000005','培训中心','dept','101','/6/101/','7','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000006,'10100000006','市场部','dept','101','/6/101/','8','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000007,'10100000007','内控管理部','dept','101','/6/101/','9','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000008,'10100000008','战略规划部','dept','101','/6/101/','10','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000009,'10100000009','授信评审中心','dept','101','/6/101/','11','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000010,'10100000010','大数据中心','dept','101','/6/101/','12','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000011,'10100000011','产品及运营部','dept','101','/6/101/','13','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000012,'10100000012','客户服务中心','dept','101','/6/101/','14','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000013,'10100000013','结算中心','dept','101','/6/101/','15','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000014,'10100000014','信息技术部','dept','101','/6/101/','16','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(10100000015,'10100000015','创新事业部','dept','101','/6/101/','17','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20101010001,'20101010001','业务事业部','dept','2010101','/6/201/20101/2010101/','21','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20101010002,'20101010002','人力资源部','dept','2010101','/6/201/20101/2010101/','22','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20101010003,'20101010003','行政部','dept','2010101','/6/201/20101/2010101/','23','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20101010004,'20101010004','财务部','dept','2010101','/6/201/20101/2010101/','24','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20101010005,'20101010005','培训部','dept','2010101','/6/201/20101/2010101/','25','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20101010006,'20101010006','营销部','dept','2010101','/6/201/20101/2010101/','26','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20101010007,'20101010007','财务部','dept','2010101','/6/201/20101/2010101/','27','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20101010008,'20101010008','培训部','dept','2010101','/6/201/20101/2010101/','28','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20101010009,'20101010009','营销部','dept','2010101','/6/201/20101/2010101/','29','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20102010001,'20102010001','人力资源部部','dept','2010201','/6/201/20102/2010201/','77','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20102010002,'20102010002','行政管理部','dept','2010201','/6/201/20102/2010201/','78','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20102010003,'20102010003','财务部','dept','2010201','/6/201/20102/2010201/','79','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20102010004,'20102010004','业务管理部','dept','2010201','/6/201/20102/2010201/','80','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20102020001,'20102020001','人事','dept','201020200','/6/201/20102/2010202/201020200/','82','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20102020002,'20102020002','行政','dept','201020200','/6/201/20102/2010202/201020200/','83','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20102020003,'20102020003','培训','dept','201020200','/6/201/20102/2010202/201020200/','84','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20102020004,'20102020004','渠道','dept','201020200','/6/201/20102/2010202/201020200/','85','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(20102020005,'20102020005','营销','dept','201020200','/6/201/20102/2010202/201020200/','266','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(30100010001,'30100010001','总裁办','dept','3010001','/6/301/3010001/','91','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(30100010002,'30100010002','业务管理部','dept','3010001','/6/301/3010001/','92','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(30100010003,'30100010003','人力资源部','dept','3010001','/6/301/3010001/','93','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(30100010004,'30100010004','合规管理部','dept','3010001','/6/301/3010001/','94','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(30100010005,'30100010005','培训部','dept','3010001','/6/301/3010001/','95','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(30100010006,'30100010006','行政部','dept','3010001','/6/301/3010001/','96','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(30100010007,'30100010007','财务部','dept','3010001','/6/301/3010001/','97','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(30100010008,'30100010008','资产管理中心','dept','3010001','/6/301/3010001/','98','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(30100010009,'30100010009','车贷管理部','dept','3010001','/6/301/3010001/','99','1','0',0,NULL,'0',NULL,NULL,NULL,NULL,NULL),(1010000000901,'1010000000901','信审一室','dept','10100000009','/6/101/10100000009/','267','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL),(1010000000902,'1010000000902','信审二室','dept','10100000009','/6/101/10100000009/','268','1','0',0,NULL,'1',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_org` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_org_user`
--

LOCK TABLES `sys_org_user` WRITE;
/*!40000 ALTER TABLE `sys_org_user` DISABLE KEYS */;
INSERT INTO `sys_org_user` VALUES (1,6,1,1,'1','1',1,NULL),(21,10100000014,10009750,55,'1','1',1,NULL),(25,10100000014,10007652,55,'1','1',1,NULL),(27,10100000014,10005075,55,'1','1',1,NULL),(28,10100000014,10006495,55,'1','1',1,NULL),(121,10100000014,11051544,1001,'1','1',1503569273606,NULL),(122,10100000014,10038008,1001,'1','1',1504117871820,NULL),(123,10100000014,11051608,1001,'1','1',1504494470157,NULL),(124,10100000014,11051970,1001,'1','1',1504494425029,NULL),(20002,10100000014,10004977,55,'1','1',1431140000000,NULL);
/*!40000 ALTER TABLE `sys_org_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_position`
--

LOCK TABLES `sys_position` WRITE;
/*!40000 ALTER TABLE `sys_position` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_prv_auth_result`
--

LOCK TABLES `sys_prv_auth_result` WRITE;
/*!40000 ALTER TABLE `sys_prv_auth_result` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_prv_auth_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_prv_biz_user`
--

LOCK TABLES `sys_prv_biz_user` WRITE;
/*!40000 ALTER TABLE `sys_prv_biz_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_prv_biz_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_prv_org_auth`
--

LOCK TABLES `sys_prv_org_auth` WRITE;
/*!40000 ALTER TABLE `sys_prv_org_auth` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_prv_org_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_prv_role`
--

LOCK TABLES `sys_prv_role` WRITE;
/*!40000 ALTER TABLE `sys_prv_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_prv_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_prv_role_auth`
--

LOCK TABLES `sys_prv_role_auth` WRITE;
/*!40000 ALTER TABLE `sys_prv_role_auth` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_prv_role_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_prv_role_resource`
--

LOCK TABLES `sys_prv_role_resource` WRITE;
/*!40000 ALTER TABLE `sys_prv_role_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_prv_role_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_prv_table_def`
--

LOCK TABLES `sys_prv_table_def` WRITE;
/*!40000 ALTER TABLE `sys_prv_table_def` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_prv_table_def` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_prv_user_share`
--

LOCK TABLES `sys_prv_user_share` WRITE;
/*!40000 ALTER TABLE `sys_prv_user_share` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_prv_user_share` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_resource`
--

LOCK TABLES `sys_resource` WRITE;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` VALUES (1,'资源根节点','root','',NULL,'0',NULL,NULL,'1',1),(2,'系统管理','module','',NULL,'1',NULL,NULL,'1',1),(3,'菜单管理','url','/sysMenu/prepareExecute/toTreePage',NULL,'2',NULL,NULL,'1',1),(4,'菜单','button',NULL,'ggg','3',NULL,NULL,'1',1),(5,'机构管理','module',NULL,NULL,'2',NULL,NULL,'1',1),(6,'组织管理','url','/sysOrg/prepareExecute/toTreePage',NULL,'5',NULL,NULL,'1',1),(7,'用户管理','url','/sysUser/prepareExecute/toManagePage',NULL,'5',NULL,NULL,'1',1),(8,'岗位管理','url','/sysPosition/prepareExecute/toQueryPage',NULL,'5',NULL,NULL,'1',1),(10,'操作权限','module',NULL,NULL,'2',NULL,NULL,'1',1),(11,'角色组管理','url','/sysRoleGroup/prepareExecute/toQueryPage','1','10',NULL,NULL,'1',2),(12,'角色管理','url','/sysRole/prepareExecute/toQueryMain',NULL,'10',NULL,NULL,'1',2),(13,'资源管理','url','/sysResource/prepareExecute/toTreePage',NULL,'10',NULL,NULL,'1',1),(14,'角色资源授权管理','url','/sysResource/prepareExecute/toRoleResource',NULL,'10',NULL,NULL,'1',1),(19,'定时任务','module',NULL,NULL,'2',NULL,NULL,'1',1),(20,'任务查询','url','/quartz/toQuartzList',NULL,'19',NULL,NULL,'1',1),(21,'任务分组','url','/quartzTaskGroupDef/prepareExecute/toQueryGroupPage',NULL,'19',NULL,NULL,'1',2),(22,'任务分组实例','url','/quartzTaskGroupInstance/prepareExecute/toQueryGroupPage',NULL,'19',NULL,NULL,'1',1),(23,'实例日志','url','/quartzTaskHis/prepareExecute/toQueryPage',NULL,'19',NULL,NULL,'1',1),(24,'业务权限管理','module',NULL,NULL,'2',NULL,NULL,'1',1),(25,'业务权限注册','url','/vmauthRegisterInfo/prepareExecute/toQueryPage',NULL,'24',NULL,NULL,'1',1),(26,'虚拟树管理','url','/vmtreeInfo/prepareExecute/toQueryPage',NULL,'24',NULL,NULL,'1',1),(27,'映射管理','url','/vmruleMapping/prepareExecute/toQueryPage?orgtype=LOAN',NULL,'24',NULL,NULL,'1',2),(28,'业务用户配置','url','/vmuserVmorgMap/prepareExecute/toManagePage?orgType=LOAN',NULL,'24',NULL,NULL,'1',2),(29,'数据权限查询','url','/vmdataPriv/prepareExecute/toQueryPage',NULL,'24',NULL,NULL,'1',1),(30,'参数配置','url','/sysConfig/prepareExecute/toQueryPage',NULL,'2',NULL,NULL,'1',1),(31,'数据字典','url','/sysDict/prepareExecute/toQueryMain',NULL,'2',NULL,NULL,'1',1),(32,'日志审计','module','/sysBizLog/prepareExecute/toQueryPage',NULL,'2',NULL,NULL,'1',1),(33,'模板管理','url','/sysTemplate/prepareExecute/toQueryPage',NULL,'2',NULL,NULL,'1',1),(34,'消息管理','url','/sysMessage/prepareExecute/toQueryPage','messagemanager','2',NULL,NULL,'1',2),(36,'日志统计','url','/sysLog/prepareExecute/toQueryLogPage',NULL,'2',NULL,NULL,'1',2),(37,'安全策略','module',NULL,NULL,'2',NULL,NULL,'1',1),(38,'用户解锁','url','/sysStragy/prepareExecute/toUnlock',NULL,'37',NULL,NULL,'1',1),(42,'开发工具','module',NULL,NULL,'1',NULL,NULL,'1',1),(44,'代码生成','url','/generate/parpareQuery',NULL,'42',NULL,NULL,'1',1),(46,'系统版本管理','url','/sysVersion/prepareExecute/toQueryPage',NULL,'2',NULL,NULL,'1',1),(47,'显示','button',NULL,'platform/sysauth/sysRole/querySysRole:add','12',NULL,NULL,'1',2),(48,'不显示','button',NULL,'platform/sysauth/sysRole/querySysRole:next','12',NULL,NULL,'1',2),(53,'新增','button','','platform/sysorg/sysPosition/querySysPosition:add','8','/8',NULL,'1',1),(54,'修改','button','','platform/sysorg/sysPosition/querySysPosition:modify','8','/8',NULL,'1',1),(55,'删除','button','','platform/sysorg/sysPosition/querySysPosition:delete','8','/8',NULL,'1',1),(56,'查看','button','','','8','/8',NULL,'1',1),(81,'用户解锁','url','/sysStragy/prepareExecute/toUnlock','','5','/5',NULL,'1',1),(82,'平台数据权限','module','','','2','/2',NULL,'1',1),(83,'用户授权','url','/sysPrvManager/prepareExecute/toManager','','82','/2/82',NULL,'1',1),(84,'数据角色','url','/sysPrvRole/prepareExecute/toManager','','82','/2/82',NULL,'1',1),(85,'权限汇总','url','/sysPrvAuthResult/prepareExecute/toQueryPage','','82','/2/82',NULL,'1',1),(86,'授权表管理','url','/sysPrvTableDef/prepareExecute/toQueryPage','','82','/2/82',NULL,'1',1),(95,'错误日志管理','module','','','1','/1',NULL,'1',1),(96,'日志级别','url','/sysAppLevelSetup/prepareExecute/toQueryPage',NULL,'95','/2/95',NULL,'1',1),(97,'日志访问权限','url','/sysAppLogPriv/prepareExecute/toQueryPage',NULL,'95','/2/95',NULL,'1',1),(98,'日志提醒设置','url','/sysAppManageContactWay/prepareExecute/toQueryPage',NULL,'95','/2/95',NULL,'1',2),(99,'错误率统计','url','/sysAppErrorCount/prepareExecute/toCountPage','logerrorcount','95','/2/95',NULL,'1',1),(100,'错误率占比','url','/sysAppErrorCount/prepareExecute/toPercentPage','logerrorpercent','95','/2/95',NULL,'1',1),(101,'日志监控集中配置','url','/sysFlumeConfigZk/prepareExecute/toQueryPage',NULL,'95','/2/95',NULL,'1',1),(103,'错误日志','url','/sysAppErrorInfo/prepareExecute/toQueryPage','logerror','95','/2/95',NULL,'1',1);
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'管理员','admin','0',NULL,'1',NULL),(21,'运维','yw','1',NULL,'1',NULL),(41,'值班监控','zbjk','1',NULL,'1',NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_role_group`
--

LOCK TABLES `sys_role_group` WRITE;
/*!40000 ALTER TABLE `sys_role_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_role_group_role`
--

LOCK TABLES `sys_role_group_role` WRITE;
/*!40000 ALTER TABLE `sys_role_group_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_group_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_role_user`
--

LOCK TABLES `sys_role_user` WRITE;
/*!40000 ALTER TABLE `sys_role_user` DISABLE KEYS */;
INSERT INTO `sys_role_user` VALUES (1,1,1,NULL,'user','1',NULL),(541,21,10005075,NULL,'user','1',NULL),(641,41,11051544,NULL,'user','1',NULL),(642,41,11051608,NULL,'user','1',NULL),(643,41,11051970,NULL,'user','1',NULL),(644,41,10004977,NULL,'user','1',NULL),(741,41,10038008,NULL,'user','1',NULL),(742,41,10006495,NULL,'user','1',NULL),(743,41,88888888,NULL,'user','1',NULL);
/*!40000 ALTER TABLE `sys_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_template`
--

LOCK TABLES `sys_template` WRITE;
/*!40000 ALTER TABLE `sys_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'管理员','admin','admin','NmIyNGIzNTVmOWM4ZjNjZjExNDNhMWNmZTMyZjhlOTU=','','13355254654','1402944568@qq.com','1','1','1958-12-01','4','高中','staff','uxyi','100000','0102','0101','uxyio ','1','1','0',1414141722071,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10004977,'马明晔','10004977','10004977','MWYxOWMwYTBlYzRiMTkwMjU4MDRkYTY2ZTViNjM2NzY=',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',1431140000000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10005075,'张冬','10005075','10005075','MWYxOWMwYTBlYzRiMTkwMjU4MDRkYTY2ZTViNjM2NzY=',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',1431140000000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10006495,'陈钢','10006495','10006495','MWYxOWMwYTBlYzRiMTkwMjU4MDRkYTY2ZTViNjM2NzY=','','13355254654','1402944568@qq.com','1','1','1958-12-01','4','高中','staff','uxyi','100000','0102','0101','uxyio ','1','1','0',1414141722071,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10007652,'齐晓明','10007652','10007652','MWYxOWMwYTBlYzRiMTkwMjU4MDRkYTY2ZTViNjM2NzY=',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',1431140000000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10009750,'樊奕','10009750','10009750','MWYxOWMwYTBlYzRiMTkwMjU4MDRkYTY2ZTViNjM2NzY=',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','0',1431140000000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10038008,'刘海鑫','10038008','10038008','MWYxOWMwYTBlYzRiMTkwMjU4MDRkYTY2ZTViNjM2NzY=',NULL,NULL,NULL,NULL,'1',NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,1504117871820,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11051544,'李杨','11051544','11051544','MWYxOWMwYTBlYzRiMTkwMjU4MDRkYTY2ZTViNjM2NzY=',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,1503569273608,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11051608,'李硕','11051608','11051608','MWYxOWMwYTBlYzRiMTkwMjU4MDRkYTY2ZTViNjM2NzY=',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,1504494470159,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11051970,'吕庆柱','11051970','11051970','MWYxOWMwYTBlYzRiMTkwMjU4MDRkYTY2ZTViNjM2NzY=',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,1504494425031,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_user_msg_relation`
--

LOCK TABLES `sys_user_msg_relation` WRITE;
/*!40000 ALTER TABLE `sys_user_msg_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_msg_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_user_msg_relation_his`
--

LOCK TABLES `sys_user_msg_relation_his` WRITE;
/*!40000 ALTER TABLE `sys_user_msg_relation_his` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_msg_relation_his` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sys_version`
--

LOCK TABLES `sys_version` WRITE;
/*!40000 ALTER TABLE `sys_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `vmauth_register_info`
--

LOCK TABLES `vmauth_register_info` WRITE;
/*!40000 ALTER TABLE `vmauth_register_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `vmauth_register_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `vmtree_info`
--

LOCK TABLES `vmtree_info` WRITE;
/*!40000 ALTER TABLE `vmtree_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `vmtree_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `vmuser_vmorg_map`
--

LOCK TABLES `vmuser_vmorg_map` WRITE;
/*!40000 ALTER TABLE `vmuser_vmorg_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `vmuser_vmorg_map` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-26 17:22:30
