#Borra la database si existe. En caso de no existir, no la borra.
drop database if exists `Acme-Madruga`;
#drop database if exists `Acme-Shout`;

create database `Acme-Madruga`;

#No necesario si los usuarios no existen aun
drop user 'acme-user';
drop user 'acme-manager';


create user 'acme-user'@'%'
		identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

#Clave que da para el Manager: e62c2bc3f155997006014fd3aaddeffc Clave cambiada
create user 'acme-manager'@'%'
		identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete
on `Acme-Madruga`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter,
create temporary tables, lock tables, create view, create routine,
alter routine, execute, trigger, show view
on `Acme-Madruga`.* to 'acme-manager'@'%';

