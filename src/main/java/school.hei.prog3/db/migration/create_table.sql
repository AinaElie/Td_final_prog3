do
$$
    begin
        if not exists(select from pg_type where typname = 'unit') then
            create type "unit" as enum ('G','L', 'U');
        end if;
    end
$$;

create table dish
(
    id_dish    varchar(10) primary key not null,
    name       varchar(20)             not null,
    unit_price double precision
);

create table ingredient
(
    id_ingredient varchar(10) primary key not null,
    name          varchar(20)             not null,
    unit          unit
);

create table unit_price
(
    id_unit_price   varchar(10) primary key not null,
    id_ingredient   varchar(10),
    constraint fk_ingredient foreign key (id_ingredient) references ingredient (id_ingredient),
    price           double precision,
    update_datetime timestamp
);

create table dish_ingredient
(
    id_dish           varchar(10) not null,
    constraint fk_dish foreign key (id_dish) references dish (id_dish),
    id_ingredient     varchar(10) not null,
    constraint fk_ingredient foreign key (id_ingredient) references ingredient (id_ingredient),
    required_quantity float,
    unit              unit
);

create table stock_movement
(
    id_stock      varchar(10) primary key not null,
    id_ingredient varchar(10)             not null,
    constraint fk_ingredient foreign key (id_ingredient) references ingredient (id_ingredient),
    quantity      double precision,
    unit          unit,
    movement      char(5) check ( movement in ('enter', 'exit') ),
    date_movement timestamp
);

do
$$
    begin
        if not exists(select from pg_type where typname = 'status') then
            create type "status" as enum ('CREATE', 'CONFIRMED', 'IN_PREPARATION', 'FINISHED', 'SERVE');
        end if;
    end
$$;

create table "order"
(
    id_order varchar(10) primary key not null,
    reference varchar(5) not null
);

create table dish_order
(
    id_dish_order varchar(10) primary key not null,
    id_dish       varchar(10)             not null,
    constraint fk_dish foreign key (id_dish) references dish (id_dish),
    quantity      int,
    id_order varchar(10) not null,
    constraint fk_order foreign key (id_order) references "order" (id_order)
);

create table status_dish_order
(
    id_dish_order varchar(10) not null,
    constraint fk_dish_order foreign key (id_dish_order) references dish_order (id_dish_order),
    status        status default 'CREATE',
    date_status   timestamp default current_timestamp
);

create table status_order
(
    id_order    varchar(10) not null,
    constraint fk_order foreign key (id_order) references "order" (id_order),
    status      status default 'CREATE',
    date_status timestamp default current_timestamp
);

create table dish_belong_order
(
    id_order      varchar(10) not null,
    constraint fk_order foreign key (id_order) references "order" (id_order),
    id_dish_order varchar(10) not null,
    constraint fk_dish_order foreign key (id_dish_order) references dish_order (id_dish_order)
);

alter table status_dish_order alter column status set default 'CREATE';
alter table status_dish_order alter column date_status set default current_timestamp;
alter table status_order alter column status set default 'CREATE';
alter table status_order alter column date_status set default current_timestamp;