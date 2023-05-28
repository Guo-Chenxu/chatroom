create table chat_room.user
(
    user_id    int auto_increment comment '用户id'
        primary key,
    `username` varchar(30)  not null comment '用户名',
    `password` varchar(255) not null comment '密码',
    avatar_id  varchar(255) null default '1' comment '头像',
    face_id    varchar(255) null comment '人脸信息'
) auto_increment = 1001;
create index username_index
    on chat_room.user (username);

create table chat_room.message
(
    message_id       int auto_increment comment '消息id' primary key,
    sender_id        int                  null comment '发送方id',
    receiver_id      int                  null comment '接收方id',
    content          varchar(255)         null comment '消息内容',
    `file`           longblob             null comment '文件数组',
    send_time        timestamp            null comment '发送时间',
    message_type     varchar(3)           null comment '消息类型',
    is_group_message tinyint(1) default 0 null comment '是否是群聊消息',
    is_read          tinyint(1) default 0 null comment '消息状态, 是否已读'
);
create index sender_index
    on chat_room.message (sender_id);
create index receiver_index
    on chat_room.message (receiver_id);
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
    group_id   int auto_increment comment '群组id' primary key,
    group_name varchar(30)  not null comment '群名',
    leader_id  int          not null comment '群主id',
    avatar_id  varchar(255) null comment '头像',
    `level`    int          not null comment '群聊等级, 不同等级有不同人数上限'
) auto_increment = 1001;
create index group_name_index
    on chat_room.group (group_name);
create index leader_id_index
    on chat_room.group (leader_id);
create index level_index
    on chat_room.group (`level`);

create table group_user_relation
(
    id       int auto_increment primary key,
    group_id int not null comment '群组id',
    user_id  int not null comment '用户id'
);
create index group_index
    on chat_room.group_user_relation (group_id);
create index user_index
    on chat_room.group_user_relation (user_id);

create table friends
(
    id        int auto_increment primary key,
    user_id   int not null comment '用户id',
    friend_id int not null comment '好友id'
);
create index user_index
    on chat_room.friends (user_id);
create index friend_index
    on chat_room.friends (friend_id);