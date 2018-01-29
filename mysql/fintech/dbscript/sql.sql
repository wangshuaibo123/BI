
/*********2018/1/11 11:18:30 ͳ�Ʒ����˵� *********/
delete from sys_menu where id=56;
INSERT INTO sys_menu VALUES (56,'BI_STAT','ͳ�Ʒ���','',NULL,0,1,1,0,NULL,91);

delete from sys_resource where id=91;
INSERT INTO sys_resource VALUES (91,'ͳ�Ʒ���','module','',NULL,1,NULL,NULL,'1','1');


/****2018/1/24 10:57:11 ����Ƽ��ۼ���Ƶ���ݻ���ͳ�Ʊ�************/
drop table if exists clip_video_summary_stat;
create table clip_video_summary_stat (
    id bigint not null auto_increment,
    type integer comment '1ȫ����2����ims\iss��3���ˡ�4����ý��',
    source_type integer comment '�Ƽ�ϵͳ�е�video��source_type',
    count integer comment '����',
    share_count integer comment '��������',
    summary_date timestamp comment '��������',
    summary_date_millisecond bigint comment '�������ں�����',
    primary key (id)
) charset=utf8;

-- ��Ƶ���ݻ���ͳ����ϸ��ͳ��ÿ��ģ�
drop table if exists clip_video_summary_stat_detail;
create table clip_video_summary_stat_detail (
    id bigint not null auto_increment,
    type integer comment '1ȫ����2����ims\iss��3���ˡ�4����ý��',
    source_type integer comment '�Ƽ�ϵͳ�е�video��source_type',
    count integer comment '����',
    share_count integer comment '��������',
    summary_date timestamp comment '��������',
    summary_date_millisecond bigint comment '�������ں�����',
    primary key (id)
) charset=utf8;



drop table if exists clip_video_copyright_summary_stat;
create table clip_video_copyright_summary_stat (
    id bigint not null auto_increment,
    abroad_count integer comment '��������',
    abroad_length decimal(12,2) comment '������Ƶ���ȣ���',
    home_count integer comment '��������',
    home_length decimal(12,2) comment '������Ƶ���ȣ���',
    summary_date timestamp comment '��������',
    summary_date_millisecond bigint comment '�������ں�����',
    primary key (id)
) charset=utf8;
