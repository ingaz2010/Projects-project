DROP table if exists project;
drop table if exists material;
drop table if exists step;
drop table if exists category;
drop table if exists project_category;
create table project
(
   project_id INT not null,
   project_name VARCHAR (128) NOT null,
   estimated_hours DECIMAL
   (
      7,
      2
   ),
   actual_hours DECIMAL
   (
      7,
      2
   ),
   difficulty INT,
   notes TEXT,
   primary key (project_id)
);
create table material
(
   material_id INT not null,
   project_id INT not null,
   material_name VARCHAR (128) not NULL,
   num_required INT,
   cost DECIMAL
   (
      7,
      2
   ),
   primary key (material_id),
   foreign key (project_id) references project (project_id) on delete CASCADE
);
create table step
(
   step_id INT not null,
   project_id INT not null,
   step_text TEXT not null,
   step_order INT not null,
   primary key (step_id),
   foreign key (project_id) references project (project_id) on delete CASCADE
);
create table category
(
   category_id INT not null,
   category_name VARCHAR (128) not NULL,
   primary key (category_id)
);
create table project_category
(
   project_id INT not null,
   category_id INT not null,
   foreign key (project_id) references project (project_id) on delete cascade,
   foreign key (category_id) references category (category_id) on delete cascade,
   unique KEY
   (
      project_id,
      category_id
   )
);