create database chat_room;

use chat_room;

create table chat_room.user
(
    id         int auto_increment primary key,
    `username` varchar(30)  not null comment '用户名' unique,
    `password` varchar(255) not null comment '密码',
    avatar_id  varchar(255) null comment '头像',
    face_id    longtext null comment '人脸信息'
);
create index username_index
    on chat_room.user (username);

create table chat_room.message
(
    id               int auto_increment primary key,
    sender_name      varchar(30)          null comment '发送方用户名',
    receiver_name    varchar(30)          null comment '接收方用户名',
    content          text                 null comment '消息内容',
    send_time        timestamp            null comment '发送时间',
    message_type     varchar(3)           null comment '消息类型',
    is_group_message tinyint(1) default 0 null comment '是否是群聊消息',
    is_read          tinyint(1) default 0 null comment '消息状态, 是否已读'
);
create index sender_index
    on chat_room.message (sender_name);
create index receiver_index
    on chat_room.message (receiver_name);
create index time_index
    on chat_room.message (send_time);
create index type_index
    on chat_room.message (message_type);
create index group_message_index
    on chat_room.message (is_group_message);
create index read_index
    on chat_room.message (is_read);

create table chat_room.group
(
    id          int auto_increment primary key,
    group_name  varchar(30)  not null comment '群名' unique,
    leader_name varchar(30)  not null comment '群主id',
    avatar_id   varchar(255) null comment '头像',
    `level`     int          not null comment '群聊等级, 不同等级有不同人数上限'
);
create index group_name_index
    on chat_room.group (group_name);
create index leader_name_index
    on chat_room.group (leader_name);
create index level_index
    on chat_room.group (`level`);

create table group_user_relation
(
    id         int auto_increment primary key,
    group_name varchar(255) not null comment '群组id',
    username   varchar(255) not null comment '用户id'
);
create index group_index
    on chat_room.group_user_relation (group_name);
create index user_index
    on chat_room.group_user_relation (username);

create table friends
(
    id          int auto_increment primary key,
    username    varchar(30) not null comment '用户id',
    friend_name varchar(30) not null comment '好友id'
);
create index user_index
    on chat_room.friends (username);
create index friend_index
    on chat_room.friends (friend_name);