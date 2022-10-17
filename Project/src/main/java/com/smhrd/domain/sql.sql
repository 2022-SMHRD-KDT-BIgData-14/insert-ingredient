-- member 테이블 생성
create table member(
id varchar(20) not null,
pw varchar(20) not null,
name varchar(20) not null,
primary key(id)
)

-- member 테이블 조회
select * from member

-- 아이디가 hi, 비밀번호가 hi인 튜플 삭제
delete from member where user_id='hi' and user_pw='hi'

-- 컬럼 명 변경 
alter table member
rename column id to user_id;
alter table member
rename column pw to user_pw;
alter table member
rename column name to user_name;

-- 컬럼 데이터 타입 변경(user_name)
alter table member
modify user_name varchar(45)

-----------------------------------------------------------------------------
-- food 테이블 생성
create table food(
food_seq int auto_increment,
food_name varchar(45) not null,
food_img varchar(45) not null,
introduce varchar(45),
process_article longtext not null,
process_img varchar(45) not null,
youtube varchar(45),

primary key(food_seq)
)

-- food 테이블 auto_increment 기본값 설정하기
alter table food auto_increment=1

-- food 테이블 ingredient 컬럼 추가
alter table food 
add ingredient_name varchar(45)

-- ingredient_name 컬럼 삭제
alter table food
drop ingredient_name

-- food 테이블 조회
select * from food where food_seq=500


-- food 테이블 test 값 넣기
insert into food(food_name, food_img, introduce, process_article, process_img, youtube, ingredient_name)
values('test', 'test', 'test', 'test', 'test', 'test', 'test')
insert into food(food_name, food_img, introduce, process_article, process_img, youtube, ingredient_name)
values('test1', 'test1', 'test1', 'test1', 'test1', 'test1', 'test1')
insert into food(food_name, food_img, introduce, process_article, process_img, youtube, ingredient_name)
values('test2', 'test2', 'test2', 'test2', 'test2', 'test2', 'test2')
insert into food(food_name, food_img, introduce, process_article, process_img, youtube, ingredient_name)
values('test3', 'test3', 'test3', 'test3', 'test3', 'test3', 'test3')
insert into food(food_name, food_img, introduce, process_article, process_img, youtube, ingredient_name)
values('test4', 'test4', 'test4', 'test4', 'test4', 'test4', 'test4')

drop table food

alter table food
modify introduce varchar(3000)

alter table food
modify youtube varchar(3000)

-----------------------------------------------------------------------------
-- wishlist 테이블 생성
create table wishlist(
user_id varchar(20) not null,
food_seq int not null,
foreign key(user_id) references member (user_id),
foreign key(food_seq) references food (food_seq)
)

-- wishlist 테이블 조회
select * from wishlist where user_id='dd'

-- food_seq가 1인 컬럼 삭제
delete from wishlist where user_id='dd'

show tables

-- 찜 여부 컬럼 추가
alter table wishlist
add column wish_or_not varchar(45)


-- 영문모를 테이블 삭제
drop table product

-----------------------------------------------------------------------------
-- relationship 테이블 생성
create table relationship(
ingredient_seq int not null,
food_seq int not null,

-- relationship 테이블에 test 데이터 넣기
insert into relationship
values (1, 1)
insert into relationship
values (1, 2)
insert into relationship
values (1, 7)

-- relationship 테이블 조회
select * from relationship

-----------------------------------------------------------------------------
-- ingredient 테이블 조회
select * from ingredient

desc ingredient

-- 식재료 이름 컬럼 데이터 타입 수정
alter table ingredient
modify column ingredient_name varchar(500)

-- 식재료 이름 unique 설정
alter table ingredient
add constraint name_unique UNIQUE (ingredient_name)

-----------------------------------------------------------------------------
-- 컬럼 정보 확인
show columns from food
show columns from ingredient
show columns from wishlist
show columns from relationship


-- 메롱~
select * from food

select * from ingredient where ingredient_name = 'carrot'

select * from ingredient where ingredient_name = 'potato'

select * from ingredient where ingredient_name = 'pork'

select ingredient_seq from ingredient where ingredient_name = 'Square fish cake'
select ingredient_name from ingredient where ingredient_seq = 1860

select * from ingredient where ingredient_seq = 1876

delete from wishlist
select * from wishlist

select * from food where youtube != ''