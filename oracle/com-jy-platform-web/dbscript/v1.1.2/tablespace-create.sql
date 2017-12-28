#捷越平台创建表空间、用户、授权
create tablespace jypt datafile 'D:\oracle\product\10.2.0\oradata\jieyue\jypt.dbf ' size 100m autoextend on next 10m;
create user jypt identified by password default tablespace jypt;
grant dba to jypt;

#导入导出
#exp jypt/password@jieyue file=C:/jypt20141112.dmp  owner=(jypt)
#imp jypt/password@orcl  file=C:/jypt20141112.dmp  full=y  ignore=y
