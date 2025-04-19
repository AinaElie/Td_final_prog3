do
$$
    begin
        if not exists(select from pg_type where typname = 'duration_unit') then
            create type "duration_unit" as enum ('SECONDS','MINUTES', 'HOUR');
        end if;
    end
$$;

create table time_element (
    id_time_element serial primary key not null,
    sales_point varchar(30),
    dish_name varchar(30),
    duration double precision,
    duration_unit duration_unit,
    id_processing_time int,
    constraint fk_processing_time foreign key (id_processing_time) references processing_time(id_processing_time)
);