
/*********2018/1/11 11:18:30 统计分析菜单 *********/
delete from sys_menu where id=56;
INSERT INTO sys_menu VALUES (56,'BI_STAT','统计分析','',NULL,0,1,1,0,NULL,91);

delete from sys_resource where id=91;
INSERT INTO sys_resource VALUES (91,'统计分析','module','',NULL,1,NULL,NULL,'1','1');


/****2018/1/24 10:57:11 添加云剪累计视频数据汇总统计表************/
drop table if exists clip_video_summary_stat;
create table clip_video_summary_stat (
    id bigint not null auto_increment,
    type integer comment '1全部、2除了ims\iss、3新浪、4政务媒体',
    source_type integer comment '云剪系统中的video中source_type',
    count integer comment '数量',
    share_count integer comment '共享数量',
    summary_date timestamp comment '汇总日期',
    summary_date_millisecond bigint comment '汇总日期毫秒数',
    primary key (id)
) charset=utf8;

-- 视频数据汇总统计明细表（统计每天的）
drop table if exists clip_video_summary_stat_detail;
create table clip_video_summary_stat_detail (
    id bigint not null auto_increment,
    type integer comment '1全部、2除了ims\iss、3新浪、4政务媒体',
    source_type integer comment '云剪系统中的video中source_type',
    count integer comment '数量',
    share_count integer comment '共享数量',
    summary_date timestamp comment '汇总日期',
    summary_date_millisecond bigint comment '汇总日期毫秒数',
    primary key (id)
) charset=utf8;



drop table if exists clip_video_copyright_summary_stat;
create table clip_video_copyright_summary_stat (
    id bigint not null auto_increment,
    abroad_count integer comment '国外数量',
    abroad_length decimal(12,2) comment '国外视频长度，秒',
    home_count integer comment '国内数量',
    home_length decimal(12,2) comment '国内视频长度，秒',
    summary_date timestamp comment '汇总日期',
    summary_date_millisecond bigint comment '汇总日期毫秒数',
    primary key (id)
) charset=utf8;
