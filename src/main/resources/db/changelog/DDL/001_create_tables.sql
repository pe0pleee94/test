--liquibase formatted sql
--changeset digitallending:create-tables

create table if not exists individual_identity(
nik bigint not null,
name  varchar(160),
dob date,
email varchar(100),
category varchar(3),
primary key (nik)
);

create table if not exists corporate_identity(
npwp bigint not null,
phone_number  long,
pic_nik long,
category varchar(3),
primary key (npwp)
);

create table if not exists step_tracker(
application_id bigint,
linked_id  long,
type varchar(20),
step_completed int,
primary key (application_id)
);

create table if not exists facility_detail(
facility_id bigint,
application_id bigint,
outstanding long,
tenor int,
primary key (facility_id),
foreign key (application_id) references step_tracker(application_id)
);

create table if not exists company_detail(
company_id bigint,
application_id bigint,
order_id int,
primary key (company_id),
foreign key (application_id) references step_tracker(application_id)
);

create table if not exists partner_info(
partner_nik bigint,
type varchar(20),
linked_id bigint,
relation varchar(20),
primary key (partner_nik)
);