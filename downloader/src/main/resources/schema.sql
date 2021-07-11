create table daily_price
(
    id          int unsigned auto_increment
        primary key,
    security_id int unsigned                       not null,
    date        date                               not null,
    open_price  decimal unsigned                   not null,
    high_price  decimal unsigned                   not null,
    low_price   decimal unsigned                   not null,
    close_price decimal unsigned                   not null,
    volume      int      default 0                 not null,
    created_dt  datetime default CURRENT_TIMESTAMP not null,
    updated_dt  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);

create index idx_security_id_date
    on daily_price (security_id, date);

create table security
(
    id         int unsigned auto_increment
        primary key,
    ticker     varchar(20)                            not null,
    name       varchar(100) default 'N/A'             not null,
    industry   varchar(100) default 'N/A'             not null,
    created_dt datetime     default CURRENT_TIMESTAMP not null,
    updated_dt datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint security_ticker_uindex
        unique (ticker)
)
    comment 'company information' charset = utf8mb4;
