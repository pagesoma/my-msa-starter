create user if not exists 'msa'@'%' identified by 'password';

-- msa
create database if not exists `msa_starter` default character set = utf8 default collate = utf8_general_ci;
grant all privileges on `msa_starter`.* to 'msa'@'%';
flush privileges;
