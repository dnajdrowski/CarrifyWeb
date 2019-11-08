INSERT INTO
`role` (`name`)
VALUES
('UNAUTHORIZED_USER'),
('USER'),
('ADMIN');

INSERT INTO
 `users` (`username`, `phone`, `password`, `personal_number`, `email`, `latitude`, `longitude`, `role_id`)
VALUES
('user1', '656454343', '$2a$10$P/CZBZnx5wgeVNlEFcidLu2NY8xNloOWgMPVCmK1Otv9enikPkfVi', '98091008874', 'jankowal@gmail.com', null, null, 1),
('user2', '934323423', '$2a$10$P/CZBZnx5wgeVNlEFcidLu2NY8xNloOWgMPVCmK1Otv9enikPkfVi','97101034345', 'dnajdrowski@gmail.com', null, null, 2),
('user3', '734343312', '$2a$10$P/CZBZnx5wgeVNlEFcidLu2NY8xNloOWgMPVCmK1Otv9enikPkfVi', '99092049584','kserocki@gmail.com', null, null, 3);

INSERT INTO
`card` (`card_number`, `expire_year`, `expire_month`, `card_cvv`, `user_id`)
VALUES
('1234567890123456', '2021', '09', '808', 1),
('3840394829483942', '2020', '12', '405', 2),
('5940305948392941', '2022', '01', '203', 2);

INSERT INTO
`car` (`name`, `fuel_level`, `last_sync`, `registration_number`, `service_mode`, `mileage`, `last_service`, `car_state`)
VALUES
('CARRIFY-0001', 85, '2019-10-10 15:24:00', 'GDA25934', 0, 15603, '2019-10-05 12:54:23', 1),
('CARRIFY-0002', 12, '2019-10-11 15:59:00', 'GD405EL', 1, 25034, '2019-10-07 17:32:23', 1),
('CARRIFY-0003', 26, '2019-10-12 13:13:00', 'GDA23405', 0, 31232, '2019-10-06 18:12:00', 1),
('CARRIFY-0004', 100, '2019-10-12 20:00:10', 'GD042EN', 1, 4003, '2019-10-11 10:43:00', 1),
('CARRIFY-0005', 58, '2019-10-09 16:54:32', 'GA403EU', 0, 604, '2019-10-04 6:32:31', 1);

INSERT INTO
 `car_location_log` (`car_id`, `latitude`, `longitude`, `created_at`)
VALUES
(1, 54.303132, 18.589390, '2019-10-10 15:24:00'),
(2, 54.396464, 18.571315, '2019-10-11 15:59:00'),
(3, 54.383533, 18.608790, '2019-10-12 13:13:00'),
(4, 54.383636, 18.591707, '2019-10-12 20:00:10'),
(5, 54.425521, 18.593068, '2019-10-09 16:54:32');



