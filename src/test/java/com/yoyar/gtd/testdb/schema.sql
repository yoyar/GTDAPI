drop table Task if exists;

-- create table gtd_taskid_sequence (seq int identity );
-- insert into  gtd_taskid_sequence (seq) values (1);

-- CREATE SEQUENCE gtd_taskid_sequence AS BIGINT START WITH 1 INCREMENT BY 1;

--create table Priority (
--	priorityid varchar(8) not null,
--	label varchar(8) not null unique,
--	sortorder int not null,
--	primary key (priorityid)
--);

--insert into Priority (priorityid, label, sortorder) values ('TOP', 'Top', 10);
--insert into Priority (priorityid, label, sortorder) values ('HIGH', 'High', 20);
--insert into Priority (priorityid, label, sortorder) values ('MEDIUM', 'Medium', 30);
--insert into Priority (priorityid, label, sortorder) values ('LOW', 'Low', 40);

-- NB> the app relies on the delete cascade on parentid for 
-- recursive delete of related tasks.
create table Task (
	taskid BIGINT identity NOT NULL,
	parent_taskid BIGINT default null,
	priorityid int default null,
	title VARCHAR(64) not null unique,
	duedate TIMESTAMP,
	completed TIMESTAMP,
	foreign key (parent_taskid) references Task (taskid) on delete cascade
);



--create view TaskView as
--select t.taskid, t.parent_taskid, t.priorityid, t.title, t.duedate, t.completed 
--from Task t 
--left join Priority p on t.priorityid = p.priorityid order by p.sortorder asc;

-- CREATE VIEW <viewname>[(<viewcolumn>,..) AS SELECT ... FROM ... [WHERE Expression]
-- [ORDER BY orderExpression [, ...]]
-- [LIMIT <limit> [OFFSET <offset>]];



-- 