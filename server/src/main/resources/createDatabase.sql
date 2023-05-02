create table chat_room.user
(
    user_id    int auto_increment comment '用户id'
        primary key,
    `username` varchar(30)  not null comment '用户名',
    `password` varchar(30)  not null comment '密码',
    avatar_id  varchar(255) not null default '1' comment '头像'
) auto_increment = 1001;
create index username_index
    on chat_room.user (username);

create table chat_room.message
(
    message_id   int auto_increment comment '消息id' primary key,
    sender_id    int comment '发送方id',
    receiver_id  int comment '接收方id',
    group_id     int comment '群id',
    content      varchar(255) comment '消息内容',
    `file`       binary comment '文件数组',
    send_time    varchar(255) comment '发送事件',
    message_type varchar(3) comment '消息类型',
    state        int comment '消息状态, 是否已读'
);
create index sender_index
    on chat_room.message (sender_id);
create index receiver_index
    on chat_room.message (receiver_id);
create index group_index
    on chat_room.message (group_id);
create index time_index
    on chat_room.message (send_time);
create index type_index
    on chat_room.message (message_type);
create index state_index
    on chat_room.message (state);

create table chat_room.group
(
    group_id   int auto_increment comment '群组id' primary key,
    group_name varchar(30)  not null comment '群名',
    leader_id  int          not null comment '群主id',
    avatar_id  varchar(255) not null comment '头像'
) auto_increment = 1001;
create index group_name_index
    on chat_room.group (group_name);
create index leader_id_index
    on chat_room.group (leader_id);

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