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
-- Table structure for table `log_info`
--

DROP TABLE IF EXISTS `log_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log_info` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `USER_ID` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `MODULE_NAME` varchar(100) DEFAULT NULL COMMENT '模块名称',
  `CONTROLLER_NAME` varchar(100) DEFAULT NULL COMMENT 'CONTROLLER名称',
  `METHOD_NAME` varchar(100) DEFAULT NULL COMMENT '方法名称',
  `PARAM_INFO` varchar(1000) DEFAULT NULL COMMENT '参数信息',
  `CREATED` date DEFAULT NULL COMMENT '生成日期',
  `URI` varchar(200) DEFAULT NULL COMMENT '请求地址',
  `LOG_TYPE` varchar(1) DEFAULT NULL COMMENT '日志类型0：访问日志，1：系统日志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `logging_event`
--

DROP TABLE IF EXISTS `logging_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logging_event` (
  `timestmp` bigint(20) NOT NULL,
  `formatted_message` text NOT NULL,
  `logger_name` varchar(254) NOT NULL,
  `level_string` varchar(254) NOT NULL,
  `thread_name` varchar(254) DEFAULT NULL,
  `reference_flag` smallint(6) DEFAULT NULL,
  `arg0` varchar(254) DEFAULT NULL,
  `arg1` varchar(254) DEFAULT NULL,
  `arg2` varchar(254) DEFAULT NULL,
  `arg3` varchar(254) DEFAULT NULL,
  `caller_filename` varchar(254) NOT NULL,
  `caller_class` varchar(254) NOT NULL,
  `caller_method` varchar(254) NOT NULL,
  `caller_line` char(4) NOT NULL,
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `logging_event_exception`
--

DROP TABLE IF EXISTS `logging_event_exception`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logging_event_exception` (
  `event_id` bigint(20) NOT NULL,
  `i` smallint(6) NOT NULL,
  `trace_line` varchar(254) NOT NULL,
  PRIMARY KEY (`event_id`,`i`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `logging_event_property`
--

DROP TABLE IF EXISTS `logging_event_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logging_event_property` (
  `event_id` bigint(20) NOT NULL,
  `mapped_key` varchar(254) NOT NULL,
  `mapped_value` text,
  PRIMARY KEY (`event_id`,`mapped_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIG_TO_TRIG_FK` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIG_TO_TRIG_FK` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `PRIORITY` bigint(13) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `idx_qrtz_ft_trig_inst_name` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `idx_qrtz_ft_inst_job_req_rcvry` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `idx_qrtz_ft_j_g` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `idx_qrtz_ft_jg` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `idx_qrtz_ft_t_g` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `idx_qrtz_ft_tg` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `idx_qrtz_j_req_recovery` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `idx_qrtz_j_grp` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` int(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` int(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIG_TO_TRIG_FK` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(10) DEFAULT NULL,
  `INT_PROP_2` int(10) DEFAULT NULL,
  `LONG_PROP_1` bigint(13) DEFAULT NULL,
  `LONG_PROP_2` bigint(13) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIG_TO_TRIG_FK` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `IS_VOLATILE` varchar(1) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` bigint(13) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` int(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `idx_qrtz_t_j` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `idx_qrtz_t_jg` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `idx_qrtz_t_c` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `idx_qrtz_t_g` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `idx_qrtz_t_state` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `idx_qrtz_t_n_state` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `idx_qrtz_t_n_g_state` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `idx_qrtz_t_next_fire_time` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `idx_qrtz_t_nft_st` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `idx_qrtz_t_nft_misfire` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `idx_qrtz_t_nft_st_misfire` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `idx_qrtz_t_nft_st_misfire_grp` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `QRTZ_TRIGGER_TO_JOBS_FK` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quartz_task_group_def`
--

DROP TABLE IF EXISTS `quartz_task_group_def`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_task_group_def` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `GROUP_ID` varchar(50) DEFAULT NULL COMMENT '分组处理编号',
  `GROUP_NAME` varchar(40) DEFAULT NULL COMMENT '分组处理名称',
  `GROUP_STATE` varchar(40) DEFAULT NULL COMMENT '分组任务是否发布（1：发布，0：待发布）',
  `TASK_ID` varchar(20) NOT NULL COMMENT '任务编号',
  `TASK_NAME` varchar(100) DEFAULT NULL COMMENT '任务描述',
  `BEAN_ID` varchar(100) DEFAULT NULL COMMENT '任务类名（实体BEANID）',
  `DEAL_STEP` bigint(8) DEFAULT NULL COMMENT '执行步骤(顺序序号)',
  `PRE_STEP` varchar(100) DEFAULT NULL COMMENT '前提步骤（任务编号）',
  `PRE_STEP_STATE` varchar(2) DEFAULT NULL COMMENT '前置步骤返回状态（1：成功，0：是失败）',
  `AUTO_EXEC` varchar(2) DEFAULT NULL COMMENT '是否自动执行（1：自动，0：手动）',
  `DEAL_CHANCE` varchar(10) DEFAULT NULL COMMENT '执行时机（DAY:每天，YEAR：每年12月31日，NORUN:不执行）',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '数据有效性（1：有效、0：无效）',
  `CREATED_BY` varchar(20) DEFAULT NULL COMMENT '新增人',
  `CREATED` timestamp NULL DEFAULT NULL COMMENT '新增时间',
  `LAST_UPD_BY` varchar(20) DEFAULT NULL COMMENT '修改人',
  `LAST_UPD` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  `RUN_TIME` varchar(10) DEFAULT NULL COMMENT '执行时间',
  `IS_END` varchar(1) DEFAULT '0' COMMENT '是否执行',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务分组定义表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quartz_task_group_instance`
--

DROP TABLE IF EXISTS `quartz_task_group_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_task_group_instance` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `GROUP_ID` varchar(50) DEFAULT NULL COMMENT '分组处理编号',
  `GROUP_NAME` varchar(40) DEFAULT NULL COMMENT '分组处理名称',
  `BATCH_NO` varchar(40) DEFAULT NULL COMMENT '批次号码',
  `TASK_ID` varchar(20) NOT NULL COMMENT '任务编号',
  `TASK_NAME` varchar(100) DEFAULT NULL COMMENT '任务描述',
  `BEAN_ID` varchar(100) DEFAULT NULL COMMENT '任务类名（实体BEANID）',
  `DEAL_STEP` bigint(8) DEFAULT NULL COMMENT '执行步骤(顺序序号)',
  `PRE_STEP` varchar(100) DEFAULT NULL COMMENT '前提步骤（任务编号）',
  `PRE_STEP_STATE` varchar(2) DEFAULT NULL COMMENT '前置步骤返回状态（1：成功，0：是失败）',
  `AUTO_EXEC` varchar(2) DEFAULT NULL COMMENT '是否自动执行（1：自动，0：手动）',
  `DEAL_CHANCE` varchar(10) DEFAULT NULL COMMENT '执行时机（DAY:每天，YEAR：每年12月31日，NORUN:不执行）',
  `TASK_INS_STATE` varchar(2) DEFAULT NULL COMMENT '任务实例执行状态（1：成功：0：失败）',
  `BUG_CONTINUE` varchar(2) DEFAULT NULL COMMENT '失败后，断点执行（1：是，0：否）',
  `IS_END` varchar(2) DEFAULT NULL COMMENT '任务是否执行（0：执行，1：未执行）',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '数据有效性（1：有效、0：无效）',
  `CREATED` timestamp NULL DEFAULT NULL COMMENT '新增时间',
  `LAST_UPD` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  `PERSON_SET_START_TIME` varchar(50) DEFAULT NULL COMMENT '人工设置的手动执行时间（如：2014-10-10 10:10:00）',
  `TASK_START_TIME` varchar(20) DEFAULT NULL COMMENT '任务开始时间',
  `TASK_END_TIME` varchar(20) DEFAULT NULL COMMENT '任务结束时间',
  `FAIL_TIMES` decimal(8,0) DEFAULT '0' COMMENT '失败次数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务分组实例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quartz_task_hcomment`
--

DROP TABLE IF EXISTS `quartz_task_hcomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_task_hcomment` (
  `ID` bigint(18) NOT NULL COMMENT '主键ID',
  `BATCH_NO` varchar(50) DEFAULT NULL COMMENT '批次号码',
  `GROUP_ID` varchar(50) DEFAULT NULL COMMENT '分组编码',
  `THREAD_ID` varchar(50) DEFAULT NULL COMMENT '执行任务的主线程ID',
  `TASK_ID` varchar(20) NOT NULL COMMENT '任务编号',
  `BEAN_ID` varchar(100) DEFAULT NULL COMMENT '任务类名（实体BEANID）',
  `TASK_STATE` varchar(2) DEFAULT NULL COMMENT '任务执行返回结果（1：成功，0：是失败）',
  `TASK_START_TIME` timestamp NULL DEFAULT NULL COMMENT '任务执行开始时间',
  `TASK_END_TIME` timestamp NULL DEFAULT NULL COMMENT '任务执行结束时间',
  `TASK_INFO` varchar(4000) DEFAULT NULL COMMENT '任务执行中信息描述',
  `ERROR_INFO` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务执行轨迹表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quartz_task_his`
--

DROP TABLE IF EXISTS `quartz_task_his`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_task_his` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `BATCH_NO` varchar(50) DEFAULT NULL COMMENT '批次号码',
  `GROUP_ID` varchar(50) DEFAULT NULL COMMENT '分组编码',
  `THREAD_ID` varchar(50) DEFAULT NULL COMMENT '执行任务的主线程ID',
  `TASK_ID` varchar(20) NOT NULL COMMENT '任务编号',
  `BEAN_ID` varchar(100) DEFAULT NULL COMMENT '任务类名（实体BEANID）',
  `TASK_STATE` varchar(2) DEFAULT NULL COMMENT '任务执行返回结果（1：成功，0：是失败）',
  `TASK_START_TIME` timestamp NULL DEFAULT NULL COMMENT '任务执行开始时间',
  `TASK_END_TIME` timestamp NULL DEFAULT NULL COMMENT '任务执行结束时间',
  `TASK_INFO` varchar(4000) DEFAULT NULL COMMENT '任务执行中信息描述',
  `ERROR_INFO` varchar(4000) DEFAULT NULL COMMENT '任务执行失败，错误描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务执行轨迹表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_acl`
--

DROP TABLE IF EXISTS `sys_acl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_acl` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` bigint(18) DEFAULT NULL COMMENT '角色ID',
  `RESOURE_ID` bigint(18) DEFAULT NULL COMMENT '资源ID',
  `ACCESSIBILITY` int(1) DEFAULT NULL COMMENT '1可访问，0拒绝访问',
  `APP_ID` bigint(18) DEFAULT NULL COMMENT '应用ID，备用',
  `VALIDATE_STATE` varchar(2) DEFAULT '1' COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1294 DEFAULT CHARSET=utf8 COMMENT='操作权限控制表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_app_access_log`
--

DROP TABLE IF EXISTS `sys_app_access_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_app_access_log` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT,
  `REMOTE_IP` varchar(100) DEFAULT NULL,
  `LOCAL_IP` varchar(100) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `THREAD_NAME` varchar(100) DEFAULT NULL,
  `REQUEST_TYPE` varchar(10) DEFAULT NULL,
  `REQUEST_URL` varchar(3000) DEFAULT NULL,
  `RESPONSE_CODE` decimal(18,0) DEFAULT NULL,
  `BYTES_SENT` decimal(18,0) DEFAULT NULL,
  `USE_TIME` decimal(18,0) DEFAULT NULL,
  `LOG_DATE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_app_error_info`
--

DROP TABLE IF EXISTS `sys_app_error_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_app_error_info` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NODE_NAME` varchar(128) NOT NULL COMMENT '节点名称（IP）',
  `APP_FLAG` varchar(50) NOT NULL COMMENT '业务系统标识',
  `CREATE_TIME` varchar(25) NOT NULL COMMENT '日志生成时间',
  `CONCENT` varchar(4000) DEFAULT NULL COMMENT '内容',
  `FILE_NAME` varchar(200) DEFAULT NULL,
  `LOG_LEVEL` int(11) DEFAULT NULL,
  `LEVEL_SETUP_ID` decimal(18,0) DEFAULT NULL,
  `MATCHED_FLAG` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ERROR_INFO_INDEX` (`NODE_NAME`),
  KEY `ERROR_INFO_INDEX2` (`APP_FLAG`),
  KEY `ERROR_INFO_INDEX3` (`CREATE_TIME`),
  KEY `ERROR_INFO_INDEX4` (`LEVEL_SETUP_ID`),
  KEY `ERROR_INFO_INDEX5` (`MATCHED_FLAG`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='业务系统节点错误日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_app_ftp_info`
--

DROP TABLE IF EXISTS `sys_app_ftp_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_app_ftp_info` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `IP` varchar(256) NOT NULL COMMENT '节点IP',
  `PORT` varchar(12) NOT NULL COMMENT '节点FTP端口',
  `USERNAME` varchar(20) NOT NULL COMMENT 'FTP用户名',
  `PASSWORD` varchar(50) NOT NULL COMMENT 'FTP密码',
  `APP_FLAG` varchar(10) NOT NULL COMMENT '业务系统标识',
  `REMOTE_DIC` varchar(300) NOT NULL COMMENT '错误日志目录',
  `APPLOG_DIC` varchar(200) NOT NULL COMMENT '业务系统日志目录',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务系统节点FTP配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_app_level_setup`
--

DROP TABLE IF EXISTS `sys_app_level_setup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_app_level_setup` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `LOG_LEVEL` int(11) NOT NULL COMMENT '级别类型',
  `KEY_WORD` varchar(200) NOT NULL COMMENT '关键字',
  `EMAIL_FLAG` int(11) DEFAULT '0' COMMENT '邮件提醒标识',
  `RATE` int(11) DEFAULT NULL,
  `RATE_UNIT` varchar(2) DEFAULT NULL,
  `SMS_FLAG` int(11) DEFAULT '0' COMMENT '短信提醒标识(0：否，1：是)',
  `SHOW_DETAIL_FLAG` int(11) DEFAULT '0' COMMENT '展示详细信息标识(0：否，1：是)',
  `APP_FLAG` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `LEVEL_SETUP_IDX1` (`APP_FLAG`),
  KEY `LEVEL_SETUP_IDX2` (`LOG_LEVEL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='错误级别设定表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_app_log_priv`
--

DROP TABLE IF EXISTS `sys_app_log_priv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_app_log_priv` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `APP_ID` varchar(20) NOT NULL COMMENT '系统标示',
  `USER_ID` decimal(18,0) NOT NULL COMMENT '用户ID',
  `VALIDATE_STATE` varchar(2) NOT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志访问权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_app_manage_contact_way`
--

DROP TABLE IF EXISTS `sys_app_manage_contact_way`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_app_manage_contact_way` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `APP_FLAG` varchar(10) NOT NULL COMMENT '系统标识',
  `EMAIL` varchar(100) NOT NULL COMMENT 'email地址',
  `TEL` varchar(18) DEFAULT NULL COMMENT '手机号',
  `MANAGE_NAME` varchar(100) DEFAULT NULL COMMENT '管理者名称',
  `LOG_LEVEL` int(11) DEFAULT NULL,
  `KEY_WORD` varchar(200) DEFAULT NULL COMMENT '接收关键字',
  PRIMARY KEY (`ID`),
  KEY `CONTACT_WAY_IDX1` (`APP_FLAG`) USING BTREE,
  KEY `CONTACT_WAY_IDX2` (`LOG_LEVEL`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统管理者联系方式';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_area`
--

DROP TABLE IF EXISTS `sys_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_area` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `AREA_CODE` varchar(50) DEFAULT NULL COMMENT '区域编码',
  `AREA_NAME` varchar(100) DEFAULT NULL COMMENT '区域名称',
  `PARENT_ID` bigint(18) DEFAULT NULL COMMENT '父节点ID',
  `VALIDATE_STATE` varchar(1) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=532929 DEFAULT CHARSET=utf8 COMMENT='行政区域';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_asyn_job`
--

DROP TABLE IF EXISTS `sys_asyn_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_asyn_job` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `BEAN_ID` varchar(100) DEFAULT NULL COMMENT '业务实体BEANID',
  `BIZ_KEY_ID` varchar(100) DEFAULT NULL COMMENT '业务表主键ID',
  `JOB_STATE` varchar(2) DEFAULT NULL COMMENT '任务状态（1：待调用，0：已完成）',
  `START_TIME` timestamp NULL DEFAULT NULL COMMENT '任务调用开始时间',
  `END_TIME` timestamp NULL DEFAULT NULL COMMENT '任务调用结束时间',
  `ERROR_REMARK` varchar(4000) DEFAULT NULL COMMENT '任务异常描述',
  `VALID` int(2) DEFAULT NULL COMMENT '数据有效性(1：有效，0：无效)',
  `JOB_RUN` varchar(2) DEFAULT NULL COMMENT '任务是否正在执行中 1：执行中，0:执行完成 或 未执行',
  `RUN_CUN` varchar(200) DEFAULT NULL COMMENT '重复执行次数',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '数据新增时间',
  `REMARK` varchar(2) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异步接口任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_config` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CONFIG_NAME` varchar(100) DEFAULT NULL COMMENT '配置名称',
  `CONFIG_CODE` varchar(100) DEFAULT NULL COMMENT '配置CODE',
  `CONFIG_VALUE` varchar(4000) DEFAULT NULL,
  `CONFIG_TYPE` varchar(100) DEFAULT NULL COMMENT '类型，0系统级（不可删除），1项目级',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8 COMMENT='系统配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DICT_CODE` varchar(50) DEFAULT NULL COMMENT '数据字典CODE',
  `DICT_NAME` varchar(50) DEFAULT NULL COMMENT '数据字典名称',
  `DICT_TYPE` varchar(50) DEFAULT NULL COMMENT '数据字典类型，0系统级（不可删除），1项目级',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=utf8 COMMENT='数据字典';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_dict_detail`
--

DROP TABLE IF EXISTS `sys_dict_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict_detail` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DICT_ID` bigint(18) DEFAULT NULL COMMENT '主键字典主键',
  `DICT_DETAIL_NAME` varchar(100) DEFAULT NULL COMMENT '数据名称',
  `DICT_DETAIL_VALUE` varchar(500) DEFAULT NULL COMMENT '数据值',
  `ORDER_BY` varchar(50) DEFAULT NULL COMMENT '排序',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1465 DEFAULT CHARSET=utf8 COMMENT='数据字典明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_flume_config_zk`
--

DROP TABLE IF EXISTS `sys_flume_config_zk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_flume_config_zk` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `APP_FLAG` varchar(50) DEFAULT NULL COMMENT '系统标示',
  `APP_IP` varchar(50) DEFAULT NULL COMMENT 'IP',
  `APP_ROLE` varchar(50) DEFAULT NULL COMMENT '角色',
  `STATUS` varchar(50) DEFAULT NULL COMMENT '状态',
  `CONFIG` longtext COMMENT '配置信息',
  `CREATE_BY` varchar(20) DEFAULT NULL COMMENT '创建人ID',
  `CREATE_BY_NAME` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(20) DEFAULT NULL COMMENT '更新人ID',
  `UPDATE_BY_NAME` varchar(50) DEFAULT NULL COMMENT '更新人姓名',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Flume配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_industry`
--

DROP TABLE IF EXISTS `sys_industry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_industry` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT,
  `INDUSTRY_NAME` varchar(500) DEFAULT NULL COMMENT '行业名称/职位名称',
  `INDUSTRY_TYPE` varchar(20) DEFAULT NULL COMMENT 'INDUSTRY:行业;POSITION:职位',
  `PARENT_CODE` varchar(50) DEFAULT NULL COMMENT '职位所属的行(只对应职位数据有效)',
  `VALIDATE_STATE` varchar(1) DEFAULT NULL,
  `INDUSTRY_CODE` varchar(50) DEFAULT NULL COMMENT '行业、职位编码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=928 DEFAULT CHARSET=utf8 COMMENT='行业';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_leave_info`
--

DROP TABLE IF EXISTS `sys_leave_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_leave_info` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `LEAVE_USER_ID` varchar(20) DEFAULT NULL COMMENT '请假人ID',
  `USER_LEVEL` varchar(200) DEFAULT NULL COMMENT '员工职级',
  `USER_NAME` varchar(50) DEFAULT NULL COMMENT '员工姓名',
  `ORG_NAME` varchar(200) DEFAULT NULL COMMENT '业务所属机构',
  `REASON` varchar(500) DEFAULT NULL COMMENT '事由',
  `EMAIL` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `STATUS` varchar(4) DEFAULT NULL COMMENT '状态（1：正常   2：请假中）',
  `START_TIME` date DEFAULT NULL COMMENT '请假开始时间',
  `END_TIME` date DEFAULT NULL COMMENT '请假结束时间',
  `LEAVE_TYPE` varchar(2) DEFAULT NULL COMMENT '请假类型（1：代理请假，2：本人请假）',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '数据有效性（1：有效、0：无效）',
  `OWNER_ID` bigint(16) DEFAULT NULL COMMENT '业务归属人',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `CREATE_BY` bigint(16) DEFAULT NULL COMMENT '创建人',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  `MODIFY_BY` bigint(16) DEFAULT NULL COMMENT '修改人',
  `ORG_ID` bigint(18) DEFAULT NULL COMMENT '业务所属机构',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工请假表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `MENU_CODE` varchar(50) DEFAULT NULL COMMENT '菜单编码',
  `MENU_NAME` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `MENU_ICON` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `MENU_URL` varchar(100) DEFAULT NULL COMMENT '菜单URL',
  `PARENT_ID` varchar(50) DEFAULT NULL COMMENT '父菜单ID',
  `ORDER_BY` varchar(50) DEFAULT NULL COMMENT '排序',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `APP_ID` bigint(18) DEFAULT NULL COMMENT '系统ID，备用',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  `RESOURCE_ID` int(22) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=703 DEFAULT CHARSET=utf8 COMMENT='菜单管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_message`
--

DROP TABLE IF EXISTS `sys_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_message` (
  `MSG_ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识，主键',
  `TITLE` varchar(100) DEFAULT NULL COMMENT '消息标题',
  `BODY` varchar(1000) DEFAULT NULL COMMENT '消息体',
  `URL` varchar(300) DEFAULT NULL COMMENT '消息链接，可为空',
  `CREATE_DATE` date DEFAULT NULL COMMENT '消息创建日期',
  `START_DATE` date DEFAULT NULL COMMENT '消息生效日期',
  `END_DATE` date DEFAULT NULL COMMENT '消息失效日期',
  `STATUS` char(1) DEFAULT NULL COMMENT '信息状态 0：初始；1：生效；2：失效；3：删除；DEFAULT：0',
  `PUBLISHER` varchar(10) DEFAULT NULL COMMENT '消息发布者ID',
  `TYPE` char(1) DEFAULT NULL COMMENT '信息的类型 0：全局消息；1：专有消息',
  `CHARSET` varchar(10) DEFAULT NULL COMMENT '信息体的编码集',
  `URGENT_FLAG` char(1) DEFAULT NULL COMMENT '紧急标识 0:普通；1：紧急；2：特急 DEFAULT:0',
  `SYS_FLAG` varchar(6) DEFAULT NULL COMMENT '标识归属于哪个系统；可取值范围：7/1/2/4/5/6/3，取值规则：TYPE为0时该字段为111；001标识贷前；010标识贷后；100标识财富；以此类推101标识贷前和财富',
  PRIMARY KEY (`MSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内消息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_message_his`
--

DROP TABLE IF EXISTS `sys_message_his`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_message_his` (
  `MSG_ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识，主键',
  `TITLE` varchar(100) DEFAULT NULL COMMENT '消息标题',
  `BODY` varchar(1000) DEFAULT NULL COMMENT '消息体',
  `URL` varchar(300) DEFAULT NULL COMMENT '消息链接，可为空',
  `CREATE_DATE` date DEFAULT NULL COMMENT '消息创建日期',
  `START_DATE` date DEFAULT NULL COMMENT '消息生效日期',
  `END_DATE` date DEFAULT NULL COMMENT '消息失效日期',
  `STATUS` char(1) DEFAULT NULL COMMENT '信息状态 0：初始；1：生效；2：失效；3：删除；DEFAULT：0',
  `PUBLISHER` varchar(10) DEFAULT NULL COMMENT '消息发布者ID',
  `TYPE` char(1) DEFAULT NULL COMMENT '信息的类型 0：全局消息；1：专有消息',
  `CHARSET` varchar(10) DEFAULT NULL COMMENT '信息体的编码集',
  `URGENT_FLAG` char(1) DEFAULT NULL COMMENT '紧急标识 0:普通；1：紧急；2：特急 DEFAULT:0',
  `SYS_FLAG` varchar(6) DEFAULT NULL COMMENT '标识归属于哪个系统；可取值范围：7/1/2/4/5/6/3，取值规则：TYPE为0时该字段为111；001标识贷前；010标识贷后；100标识财富；以此类推101标识贷前和财富',
  PRIMARY KEY (`MSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_org`
--

DROP TABLE IF EXISTS `sys_org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_org` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '机构ID',
  `ORG_CODE` varchar(100) DEFAULT NULL COMMENT '机构编码',
  `ORG_NAME` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `ORG_TYPE` varchar(10) DEFAULT NULL COMMENT '机构类型：ORG组织，DEPT部门',
  `PARENT_ID` varchar(100) DEFAULT NULL COMMENT '父机构ID',
  `PARENT_IDS` varchar(1000) DEFAULT NULL COMMENT '父机构ID串，以/分割',
  `ORDER_BY` varchar(50) DEFAULT NULL COMMENT '排序',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `IS_VIRTUAL` varchar(1) DEFAULT NULL COMMENT '是否是虚拟组织，1是，0否，默认0',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  `APP_FLAG` varchar(10) DEFAULT NULL COMMENT '应用标志',
  `IS_LEEF` varchar(1) DEFAULT NULL COMMENT '是否叶子节点',
  `AREA_CODES` varchar(50) DEFAULT NULL COMMENT '区域编码',
  `AREA_ADRESS` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `ORG_LEVEL` int(2) DEFAULT NULL COMMENT '层级',
  `EFFECTIVE_DATE` timestamp NULL DEFAULT NULL COMMENT '生效期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1010000000903 DEFAULT CHARSET=utf8 COMMENT='机构部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_org_user`
--

DROP TABLE IF EXISTS `sys_org_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_org_user` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ORG_ID` bigint(18) DEFAULT NULL COMMENT '机构ID',
  `USER_ID` bigint(18) DEFAULT NULL COMMENT '用户ID',
  `POSITION_ID` bigint(18) DEFAULT NULL COMMENT '岗位ID',
  `IS_MAIN_ORG` varchar(1) DEFAULT NULL COMMENT '是否主部门，1是（主部门），0否（兼职部门），默认1',
  `VALIDATE_STATE` varchar(1) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  `POSITION_CODE` varchar(50) DEFAULT NULL COMMENT '岗位编码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20023 DEFAULT CHARSET=utf8 COMMENT='员工归属机构部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_position`
--

DROP TABLE IF EXISTS `sys_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_position` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `POSITION_NAME` varchar(18) DEFAULT NULL COMMENT '岗位名称',
  `POSITION_CODE` varchar(50) DEFAULT NULL COMMENT '岗位编码',
  `ORDER_BY` varchar(50) DEFAULT NULL COMMENT '排序',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  `CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `ORG_CODE` varchar(20) DEFAULT NULL COMMENT '所属机构编码',
  `IS_RESPONSIBLE` int(2) DEFAULT NULL COMMENT '是否负责,1是,0否',
  `POSITION_SEQUENCE` int(2) DEFAULT NULL COMMENT '岗位序列,1管理序列,2职能序列,3业务序列,4技术序列,5运营序列',
  `POST` varchar(50) DEFAULT NULL COMMENT '职位',
  `EFFECTIVE_DATE` timestamp NULL DEFAULT NULL COMMENT '生效时间',
  `PARENT_ID` varchar(19) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位定义表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_prv_auth_result`
--

DROP TABLE IF EXISTS `sys_prv_auth_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_prv_auth_result` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID_FROM` bigint(18) DEFAULT NULL COMMENT '授权用户ID',
  `USER_ID_TO` bigint(18) DEFAULT NULL COMMENT '被授权用户ID',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性',
  `INSERT_FROM` varchar(18) DEFAULT NULL COMMENT '数据来源是哪个表。S是用户共享表，O是组织机构表，R是角色表。多个来源以'',''分开',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限授权结果表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_prv_biz_user`
--

DROP TABLE IF EXISTS `sys_prv_biz_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_prv_biz_user` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` bigint(18) DEFAULT NULL COMMENT '用户ID',
  `ORG_ID` bigint(18) DEFAULT NULL COMMENT '机构ID',
  `BIZ_ID` bigint(18) DEFAULT NULL COMMENT '业务ID',
  `TABLE_NAME` varchar(50) DEFAULT NULL COMMENT '业务表名',
  `ACTION_STATE` varchar(2) DEFAULT NULL COMMENT '操作状态，插入I，删除D',
  `SYN_STATE` varchar(2) DEFAULT NULL COMMENT '同步状态，未同步0，已同步1',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效状态，1有效，0无效，默认1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限注册表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_prv_org_auth`
--

DROP TABLE IF EXISTS `sys_prv_org_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_prv_org_auth` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` bigint(18) DEFAULT NULL COMMENT '用户ID',
  `ORG_ID` bigint(18) DEFAULT NULL COMMENT '组织ID',
  `ACTION_STATE` varchar(2) DEFAULT NULL COMMENT '操作状态，插入I，删除D',
  `SYN_STATE` varchar(2) DEFAULT NULL COMMENT '同步状态，未同步0，已同步1',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效状态，1有效，0无效，默认1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织授权表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_prv_role`
--

DROP TABLE IF EXISTS `sys_prv_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_prv_role` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_CODE` varchar(50) DEFAULT NULL COMMENT '角色CODE',
  `ROLE_NAME` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限角色定义';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_prv_role_auth`
--

DROP TABLE IF EXISTS `sys_prv_role_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_prv_role_auth` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` bigint(18) DEFAULT NULL COMMENT '用户ID',
  `ROLE_ID` bigint(18) DEFAULT NULL COMMENT '角色ID',
  `ACTION_STATE` varchar(2) DEFAULT NULL COMMENT '操作状态，插入I，删除D',
  `SYN_STATE` varchar(2) DEFAULT NULL COMMENT '同步状态，未同步0，已同步1',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效状态，1有效，0无效，默认1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限角色授权表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_prv_role_resource`
--

DROP TABLE IF EXISTS `sys_prv_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_prv_role_resource` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` bigint(18) DEFAULT NULL COMMENT '角色ID',
  `RESOURCE_ID` bigint(18) DEFAULT NULL COMMENT '资源ID',
  `RESOURCE_TYPE` varchar(10) DEFAULT NULL COMMENT '资源类型，机构ORG，用户USER',
  `ACTION_STATE` varchar(2) DEFAULT NULL COMMENT '操作状态，插入I，删除D',
  `SYN_STATE` varchar(2) DEFAULT NULL COMMENT '同步状态，未同步0，已同步1',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效状态，1有效，0无效，默认1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据角色资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_prv_table_def`
--

DROP TABLE IF EXISTS `sys_prv_table_def`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_prv_table_def` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `TABLE_NAME` varchar(50) DEFAULT NULL COMMENT '表名称',
  `TABLE_DESC` varchar(500) DEFAULT NULL COMMENT '描述',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性，1有效，0无效，默认1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UNI_SYS_DATA_PRV_TABLE` (`TABLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限表定义';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_prv_user_share`
--

DROP TABLE IF EXISTS `sys_prv_user_share`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_prv_user_share` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `FROM_USER_ID` bigint(18) DEFAULT NULL COMMENT '共享用户ID',
  `TO_USER_ID` bigint(18) DEFAULT NULL COMMENT '被共享用户ID',
  `ACTION_STATE` varchar(2) DEFAULT NULL COMMENT '操作状态，插入I，删除D',
  `SYN_STATE` varchar(2) DEFAULT NULL COMMENT '同步状态，未同步0，已同步1',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效状态，1有效，0无效，默认1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据共享表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_resource`
--

DROP TABLE IF EXISTS `sys_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_resource` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `RESOURE_NAME` varchar(200) DEFAULT NULL COMMENT '名称',
  `RESOURE_TYPE` varchar(50) DEFAULT NULL COMMENT '类型，URL或BUTTON',
  `RESOURE_URL` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `PERMISSION` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `PARENT_ID` varchar(50) DEFAULT NULL COMMENT '父ID',
  `PARENT_IDS` varchar(200) DEFAULT NULL COMMENT '父ID串，以/分割',
  `APP_ID` bigint(18) DEFAULT NULL COMMENT '应用ID，备用字段',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=888 DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_NAME` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `ROLE_CODE` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `ROLE_TYPE` varchar(1) DEFAULT NULL COMMENT '角色类型：0系统角色（不能删除），1项目自定义角色',
  `APP_ID` bigint(18) DEFAULT NULL COMMENT '系统ID，备用',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_group`
--

DROP TABLE IF EXISTS `sys_role_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_group` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_GROUP_NAME` varchar(100) DEFAULT NULL,
  `ROLE_GROUP_CODE` varchar(50) DEFAULT NULL COMMENT '角色组编码',
  `ROLE_GROUP_TYPE` varchar(1) DEFAULT NULL COMMENT '角色组类型：备用，默认1',
  `APP_ID` bigint(18) DEFAULT NULL COMMENT '系统ID，备用',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色组';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_group_role`
--

DROP TABLE IF EXISTS `sys_role_group_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_group_role` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` bigint(18) DEFAULT NULL COMMENT '角色ID',
  `ROLE_GROUP_ID` bigint(18) DEFAULT NULL COMMENT '角色组ID',
  `APP_ID` bigint(18) DEFAULT NULL COMMENT '系统ID，备用',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(18) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色组角色中间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_user`
--

DROP TABLE IF EXISTS `sys_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_user` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` bigint(18) DEFAULT NULL COMMENT '角色ID',
  `TARGET_ID` bigint(18) DEFAULT NULL COMMENT '用户ID或者机构ID',
  `APP_ID` bigint(18) DEFAULT NULL COMMENT '系统ID，备用',
  `TARGET_TYPE` varchar(50) DEFAULT NULL COMMENT 'USER用户类型,ORG机构类型',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=744 DEFAULT CHARSET=utf8 COMMENT='角色用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_template`
--

DROP TABLE IF EXISTS `sys_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_template` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '机构ID',
  `APP_ID` varchar(50) DEFAULT NULL COMMENT '系统ID',
  `TEMPLATE_NO` varchar(50) DEFAULT NULL COMMENT '模板编码',
  `TEMPLATE_NAME` varchar(100) DEFAULT NULL COMMENT '模板名称',
  `TEMPLATE_TYPE` varchar(2) DEFAULT NULL COMMENT '模板类型 1  短信 2邮件 9 其他',
  `TEMPLATE_CONTENT` longblob COMMENT '模板内容',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_NAME` varchar(50) DEFAULT NULL COMMENT '姓名',
  `USER_NO` varchar(50) DEFAULT NULL COMMENT '编码',
  `LOGIN_NAME` varchar(50) DEFAULT NULL COMMENT '登录名',
  `PASSWORD` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `SALT` varchar(50) DEFAULT NULL COMMENT '盐值',
  `MOBILE` varchar(50) DEFAULT NULL COMMENT '手机',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '邮件',
  `USER_IMAGE` varchar(100) DEFAULT NULL COMMENT '用户头像',
  `SEX` varchar(50) DEFAULT NULL COMMENT '性别',
  `BIRTHDAY` varchar(50) DEFAULT NULL COMMENT '出生日期',
  `NATIONALITY` varchar(50) DEFAULT NULL COMMENT '民族',
  `EDUCATION` varchar(50) DEFAULT NULL COMMENT '学历',
  `JOB` varchar(100) DEFAULT NULL COMMENT '职务',
  `HOME_ADDRESS` varchar(100) DEFAULT NULL COMMENT '家庭住址',
  `HOME_ZIPCODE` varchar(50) DEFAULT NULL COMMENT '家庭邮编',
  `HOME_TEL` varchar(50) DEFAULT NULL COMMENT '家庭电话',
  `OFFICE_TEL` varchar(50) DEFAULT NULL COMMENT '办公电话',
  `OFFICE_ADDRESS` varchar(100) DEFAULT NULL COMMENT '办公地址',
  `ORDER_BY` varchar(50) DEFAULT NULL COMMENT '排序',
  `VALIDATE_STATE` varchar(2) DEFAULT NULL COMMENT '有效性状态，1有效，0无效，默认1',
  `IS_LOCKED` varchar(2) DEFAULT NULL COMMENT '是否锁定，1锁定，0未锁，默认0',
  `VERSION` bigint(16) DEFAULT NULL COMMENT '乐观锁',
  `PROBATION_PERIOD` int(2) DEFAULT NULL,
  `ENTRY_DATE` timestamp NULL DEFAULT NULL COMMENT '入职日期',
  `QUIT_DATE` timestamp NULL DEFAULT NULL COMMENT '离职日期',
  `WORK_DATE` timestamp NULL DEFAULT NULL COMMENT '参加工作日期',
  `POLITICAL_STATUS` varchar(2) DEFAULT '' COMMENT '政治面貌,1党员,2团员,3群众,4民主党派,5预备党员',
  `USER_RELATION` varchar(20) DEFAULT NULL COMMENT '员工关系,1正式,2试用,3实习,4劳务派遣',
  `CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '创建日期',
  `CARD_NO` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `ANNUAL_LEAVE` int(2) DEFAULT NULL COMMENT '标准年假',
  `JXZJ` varchar(10) DEFAULT NULL,
  `NJQSRQ` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_SYS_USER_LOGIN_NAME` (`LOGIN_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=88888889 DEFAULT CHARSET=utf8 COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_msg_relation`
--

DROP TABLE IF EXISTS `sys_user_msg_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_msg_relation` (
  `REL_ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `REL_STATUS` char(1) DEFAULT NULL COMMENT '信息状态：0：有效1：用户删除；DEFAULT0',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '归属者ID',
  `READ_FLAG` char(1) DEFAULT NULL COMMENT '用户读取状态：0:未读；1：已读 DEFAULT:0',
  `MSG_ID` int(20) DEFAULT NULL COMMENT '消息ID',
  PRIMARY KEY (`REL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_msg_relation_his`
--

DROP TABLE IF EXISTS `sys_user_msg_relation_his`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_msg_relation_his` (
  `REL_ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `REL_STATUS` char(1) DEFAULT NULL COMMENT '信息状态：0：有效1：用户删除；DEFAULT0',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '归属者ID',
  `READ_FLAG` char(1) DEFAULT NULL COMMENT '用户读取状态：0:未读；1：已读 DEFAULT:0',
  `MSG_ID` int(20) DEFAULT NULL COMMENT '消息ID',
  PRIMARY KEY (`REL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_version`
--

DROP TABLE IF EXISTS `sys_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_version` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `VERSION_NUM` varchar(50) DEFAULT NULL COMMENT '版本号',
  `VERSION_NAME` varchar(200) DEFAULT NULL COMMENT '版本名称',
  `VERSION_CONTENT` varchar(4000) DEFAULT NULL COMMENT '版本内容',
  `VERSION_TIME` varchar(50) DEFAULT NULL COMMENT '上线时间',
  `SYSTEM_STATE` varchar(50) DEFAULT NULL COMMENT '系统标志位',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统版本号表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vmauth_register_info`
--

DROP TABLE IF EXISTS `vmauth_register_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vmauth_register_info` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `VMTREE_CODE` varchar(50) DEFAULT NULL COMMENT '虚拟树代码',
  `VMTREE_NAME` varchar(100) DEFAULT NULL COMMENT '虚拟树名称',
  `MAP_TAB_NAME` varchar(100) DEFAULT NULL COMMENT '映射表名',
  `DATA_TAB_NAME` varchar(100) DEFAULT NULL COMMENT '数据权限表名',
  `MAP_INIT_SQL` varchar(4000) DEFAULT NULL COMMENT '映射表创建SQL',
  `DATA_INIT_SQL` varchar(4000) DEFAULT NULL COMMENT '数据权限表创建SQL',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_BY` bigint(18) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vmtree_info`
--

DROP TABLE IF EXISTS `vmtree_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vmtree_info` (
  `ORG_ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '序列或HR的ORGID',
  `ORG_NAME` varchar(200) DEFAULT NULL COMMENT '机构名称',
  `ORG_TYPE` varchar(50) DEFAULT NULL COMMENT '虚拟树代码',
  `PARENT_ID` bigint(18) DEFAULT NULL COMMENT '父节点ORG_ID',
  `SOURCE_TYPE` varchar(10) DEFAULT NULL COMMENT '数据来源（XN/HR）',
  `END_FLAG` varchar(2) DEFAULT NULL COMMENT '是否叶子节点（1：是，0：否）',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_BY` bigint(18) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`ORG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='虚拟树表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vmuser_vmorg_map`
--

DROP TABLE IF EXISTS `vmuser_vmorg_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vmuser_vmorg_map` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `USER_ID` bigint(18) DEFAULT NULL COMMENT '用户ID',
  `ORG_ID` bigint(18) DEFAULT NULL COMMENT '虚拟树的ORG_ID',
  `ORG_TYPE` varchar(50) DEFAULT NULL COMMENT '虚拟树代码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工虚拟组织关系表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-26 17:45:13
