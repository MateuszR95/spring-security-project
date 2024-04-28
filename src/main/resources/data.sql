INSERT INTO users (user_name, email, first_name, last_name, password)
values
--    hasło: abcd
('anowak', 'an@wp.pl', 'Anna', 'Nowak', '{bcrypt}$2a$10$lp8FHLaRtkqTH92cyfFf/exQ6wI3Usvx7r/NRaooI7c8h7Xa6nwE6'),

--  hasło: qwer
('twolny', 'tw@wp.pl','Tomasz', 'Wolny', '{bcrypt}$2a$10$OuFOdd8q6aPWB6uoYzRNgO4ZuMymorm7aebvyeqOB9h0.kOmiFXI6'),

--  hasło: wwww
('iszymanski', 'is@wp.pl', 'Igor', 'Szymański', '{bcrypt}$2a$10$GOwhkdeh6mX.0UzP8Ey49e/aytbpTY87OFHUExhP5EGck0Xbed26K'),

--  hasło: aaaa
('kwlodar', 'kp@wp.pl', 'Konrad', 'Włodarczyk', '{bcrypt}$2a$10$QAz.G6pxZWg6mx9SlYY.BuCmW3f1f0oasHAeSPKQi5QOVtW.6L3HK'),

--  hasło: 1234
('akowalska', 'aw@wp.pl', 'Agnieszka', 'Kowalska', '{bcrypt}$2a$10$7Xv90yJSWCjZZId5SVRlo.JQnPTVWlwWpk1xT5A3VlQRTODX8NdNK');

INSERT INTO role (role)
values
    ('ADMIN'),
    ('USER');

INSERT INTO users_roles(user_id, role_id)
values
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 1),
    (5, 2);