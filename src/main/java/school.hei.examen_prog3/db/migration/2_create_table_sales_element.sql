create table sales_element (
    id_sales_element serial primary key not null,
    sales_point varchar(50),
    id_best_sales int,
    constraint fk_best_sales foreign key (id_best_sales) references best_sales(id_best_sales)
);