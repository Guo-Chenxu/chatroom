create table chat_room.user
(
    user_id    int auto_increment comment '用户id'
        primary key,
    `username` varchar(30)  not null comment '用户名',
    `password` varchar(255) not null comment '密码',
    avatar_id  varchar(255) not null default '1' comment '头像'
) auto_increment = 1001;
create index username_index
    on chat_room.user (username);

create table chat_room.message
(
    message_id   int auto_increment comment '消息id' primary key,
    sender_id    int comment '发送方id',
    receiver_id  int comment '接收方id',
    content      varchar(255) comment '消息内容',
    `file`       binary comment '文件数组',
    send_time    bigint comment '发送时间',
    message_type varchar(3) comment '消息类型',
    is_group_message int comment '是否是群聊消息',
    state        int comment '消息状态, 是否已读'
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
create index state_index
    on chat_room.message (state);

create table chat_room.group
(
    group_id   int auto_increment comment '群组id' primary key,
    group_name varchar(30)  not null comment '群名',
    leader_id  int          not null comment '群主id',
    avatar_id  varchar(255) not null comment '头像',
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