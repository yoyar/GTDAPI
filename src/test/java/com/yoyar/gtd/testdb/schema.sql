drop table Task if exists;

-- create table gtd_sequence (id int not null primary key, text varchar(100));
create table gtd_taskid_sequence (seq int identity );
insert into  gtd_taskid_sequence (seq) values (1);

create table Task (
	id BIGINT primary key NOT NULL,
	parentid BIGINT,
	title VARCHAR(64) not null unique,
	dueDate TIMESTAMP,
	priority varchar(8)
);


--CREATE [MEMORY | CACHED | [GLOBAL] TEMPORARY | TEMP [2] | TEXT[2]] TABLE <name>
--    ( <columnDefinition> [, ...] [, <constraintDefinition>...] )
--    [ON COMMIT {DELETE | PRESERVE} ROWS];
