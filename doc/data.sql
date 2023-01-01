
create table bidlist
(
    bid_list_id   int          not null
        primary key,
    account       varchar(30)  null,
    ask           double       null,
    ask_quantity  double       null,
    benchmark     varchar(125) null,
    bid           double       null,
    bid_list_date datetime(6)  null,
    bid_quantity  double       not null,
    book          varchar(125) null,
    commentary    varchar(125) null,
    creation_date datetime(6)  null,
    creation_name varchar(125) null,
    deal_name     varchar(125) null,
    deal_type     varchar(125) null,
    revision_date datetime(6)  null,
    revision_name varchar(125) null,
    security      varchar(125) null,
    side          varchar(125) null,
    source_listid varchar(125) null,
    status        varchar(10)  null,
    trader        varchar(125) null,
    type          varchar(30)  null
);

create table curvepoint
(
    id            int         not null
        primary key,
    as_of_date    datetime(6) null,
    creation_date datetime(6) null,
    curve_id      int         not null,
    term          double      not null,
    value         double      not null
);

create table hibernate_sequence
(
    next_val bigint null
);

create table rating
(
    id            int          not null
        primary key,
    fitch_rating  varchar(125) null,
    moodys_rating varchar(125) null,
    order_number  int          not null,
    sandprating   varchar(125) null
);

create table rulename
(
    id          int          not null
        primary key,
    description varchar(125) null,
    json        varchar(125) null,
    name        varchar(255) null,
    sql_part    varchar(125) null,
    sql_str     varchar(125) null,
    template    varchar(512) null
);

create table trade
(
    trade_id       int          not null
        primary key,
    account        varchar(30)  null,
    benchmark      varchar(125) null,
    book           varchar(125) null,
    buy_price      double       null,
    buy_quantity   double       not null,
    creation_date  datetime(6)  null,
    creation_name  varchar(125) null,
    deal_name      varchar(125) null,
    deal_type      varchar(125) null,
    revision_date  datetime(6)  null,
    revision_name  varchar(125) null,
    security       varchar(125) null,
    sell_price     double       null,
    sell_quantity  double       null,
    side           varchar(125) null,
    source_list_id varchar(125) null,
    status         varchar(10)  null,
    trade_date     datetime(6)  null,
    trader         varchar(125) null,
    type           varchar(30)  null
);

create table users
(
    id       int          not null
        primary key,
    fullname varchar(255) not null,
    password varchar(255) not null,
    role     varchar(255) not null,
    username varchar(255) not null
);