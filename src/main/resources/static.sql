use registration;
select * from users;
select * from roles;
select * from users_roles;
DELETE FROM users_roles; DELETE FROM users; DELETE FROM roles;
SELECT * FROM users WHERE 
    id = (SELECT user_id FROM users_roles WHERE 
              role_id = (SELECT id FROM roles WHERE role = 'MASTER'));