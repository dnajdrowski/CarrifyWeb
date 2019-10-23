CREATE TABLE `users` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(255),
  `phone` varchar(255),
  `password` varchar(255),
  `pesel` char(11),
  `card_number` char(16),
  `expire_year` char(4),
  `expire_month` char(2),
  `card_cvv` char(3),
  `email` varchar(255),
  `latitude` double,
  `longitude` double,
  `role_id` long
);

CREATE TABLE `car` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `fuel_level` int,
  `last_sync` datetime,
  `registration_nuber` varchar(255),
  `service_mode` int,
  `mileage` int,
  `last_service` datetime,
  `car_state` int
);

CREATE TABLE `car_location_log` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `car_id` long,
  `latitude` double,
  `longitude` double,
  `created_at` datetime
);

CREATE TABLE `rent` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `user_id` long,
  `car_id` long,
  `distance` double,
  `created_at` datetime,
  `end_at` datetime,
  `amount` int
);

CREATE TABLE `rent_location_log` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `rent_id` long,
  `latitude` double,
  `longitude` double,
  `created_at` datetime
);

CREATE TABLE `role` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255)
);

CREATE TABLE `users_wallet` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `user_id` long,
  `amount` int,
  `last_update` datetime
);

CREATE TABLE `transaction` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `rent_id` long,
  `user_id` long,
  `amount` int,
  `created_at` datetime
);

CREATE TABLE `coupons` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `value` varchar(255),
  `name` varchar(255),
  `amount` int,
  `created_at` datetime
);

CREATE TABLE `user_coupon` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `user_id` long,
  `coupon_id` long,
  `created_at` datetime
);

CREATE TABLE `reservation` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `user_id` long,
  `car_id` long,
  `state` int,
  `can_extend` int,
  `created_at` datetime
);

CREATE TABLE `variables` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `value` varchar(255)
);

CREATE TABLE `report` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `user_id` long,
  `title` varchar(255),
  `description` varchar(255),
  `created_at` datetime
);

CREATE TABLE `region_zone` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `stroke_width` int,
  `stroke_color` varchar(255),
  `stroke_alpha` int,
  `zone_color` varchar(255),
  `zone_alpha` int,
  `created_at` datetime
);

CREATE TABLE `region_zone_coords` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `latitude` double,
  `longitude` double,
  `region_zone_id` long,
  `created_at` datetime
);

ALTER TABLE `users` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

ALTER TABLE `car_location_log` ADD FOREIGN KEY (`car_id`) REFERENCES `car` (`id`);

ALTER TABLE `rent` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `rent` ADD FOREIGN KEY (`car_id`) REFERENCES `car` (`id`);

ALTER TABLE `rent_location_log` ADD FOREIGN KEY (`rent_id`) REFERENCES `rent` (`id`);

ALTER TABLE `users_wallet` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `transaction` ADD FOREIGN KEY (`rent_id`) REFERENCES `rent` (`id`);

ALTER TABLE `transaction` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `user_coupon` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `user_coupon` ADD FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`);

ALTER TABLE `reservation` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `reservation` ADD FOREIGN KEY (`car_id`) REFERENCES `car` (`id`);

ALTER TABLE `report` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `region_zone_coords` ADD FOREIGN KEY (`region_zone_id`) REFERENCES `region_zone` (`id`);
