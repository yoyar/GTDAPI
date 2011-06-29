drop table Task if exists;
drop table gtd_taskid_sequence if exists;

create table gtd_taskid_sequence (seq int identity );
insert into  gtd_taskid_sequence (seq) values (1);

-- NB> the app relies on the delete cascade on parentid for 
-- recursive delete of related tasks.
create table Task (
	id BIGINT NOT NULL,
	parentid BIGINT default null,
	title VARCHAR(64) not null unique,
	dueDate TIMESTAMP,
	priority varchar(8),
	primary key (id),
	foreign key (parentid) references Task (id) on delete cascade
);


-- 