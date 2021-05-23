create table record
(
		id int auto_increment
				primary key,
					cookie varchar(255) not null,
						building varchar(20) not null,
							date datetime null,
								mode varchar(10) default 'normal' null
							);


