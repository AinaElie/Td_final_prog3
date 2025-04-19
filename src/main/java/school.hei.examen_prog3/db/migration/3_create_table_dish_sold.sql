create table dish_sold (
    id_dish_sold serial primary key not null,
    dish_name varchar(20),
    quantity double precision,
    total_amount double precision,
    id_sales_element int,
    constraint fk_sales_element foreign key (id_sales_element) references sales_element(id_sales_element)
);