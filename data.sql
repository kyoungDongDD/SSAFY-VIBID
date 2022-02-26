insert into member (credential, is_email_authentication, is_lock, is_withdrawal, login_count, login_fail_count, nickname, principal, profile_image_url, regist_date, vendor) values ('$2a$10$cJqRqNdqpSurQi3zGEd6EuUSnlxmEDRlM0u/3GdcHsgYaPnzvL4Ou', true, false, false, 0, 0, '관리자', 'admin@b207.io', NULL, NOW(),'vibid');
insert into member (credential, is_email_authentication, is_lock, is_withdrawal, login_count, login_fail_count, nickname, principal, profile_image_url, regist_date, vendor) values ('$2a$10$CjiCKJ2gxuMH/XnllMd6bebf70msYPV9GRREeZVXHsuCSNOIlU47i', true, false, false, 0, 0, '회원1', 'ssafy@b207.io', NULL, NOW(),'vibid');

insert into authority (authority, member_id) values ('ADMIN', 1);
insert into authority (authority, member_id) values ('USER', 2);