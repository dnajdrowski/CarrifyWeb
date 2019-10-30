CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(255) UNIQUE NOT NULL,
  `phone` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `pesel` char(11) UNIQUE NOT NULL,
  `email` varchar(255) UNIQUE NOT NULL,
  `latitude` double,
  `longitude` double,
  `role_id` int
);

CREATE TABLE `card` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `card_number` char(16) NOT NULL,
  `expire_year` char(4) NOT NULL,
  `expire_month` char(2) NOT NULL,
  `card_cvv` char(3) NOT NULL,
  `user_id` int NOT NULL
);

CREATE TABLE `car` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `fuel_level` int NOT NULL,
  `last_sync` datetime,
  `registration_nuber` varchar(255) UNIQUE NOT NULL,
  `service_mode` int NOT NULL,
  `mileage` int NOT NULL,
  `last_service` datetime,
  `car_state` int
);

CREATE TABLE `car_location_log` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `car_id` int NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `created_at` datetime NOT NULL
);

CREATE TABLE `rent` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `car_id` int NOT NULL,
  `distance` double NOT NULL DEFAULT 0,
  `created_at` datetime NOT NULL,
  `end_at` datetime,
  `amount` int
);

CREATE TABLE `rent_location_log` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `rent_id` int NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `created_at` datetime NOT NULL
);

CREATE TABLE `role` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL
);

CREATE TABLE `users_wallet` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `amount` int NOT NULL DEFAULT 0,
  `last_update` datetime NOT NULL
);

CREATE TABLE `transaction` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `rent_id` int NOT NULL,
  `user_id` int NOT NULL,
  `amount` int NOT NULL,
  `created_at` datetime NOT NULL
);

CREATE TABLE `coupons` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `value` varchar(255) NOT NULL,
  `name` varchar(255),
  `amount` int NOT NULL,
  `created_at` datetime NOT NULL
);

CREATE TABLE `user_coupon` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `coupon_id` int NOT NULL,
  `created_at` datetime NOT NULL
);

CREATE TABLE `reservation` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `car_id` int NOT NULL,
  `state` int NOT NULL,
  `can_extend` int NOT NULL,
  `created_at` datetime NOT NULL
);

CREATE TABLE `variables` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL
);

CREATE TABLE `report` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL
);

CREATE TABLE `region_zone` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `stroke_width` int NOT NULL,
  `stroke_color` varchar(255) NOT NULL,
  `stroke_alpha` int NOT NULL,
  `zone_color` varchar(255) NOT NULL,
  `zone_alpha` int NOT NULL,
  `created_at` datetime NOT NULL
);

CREATE TABLE `region_zone_coords` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `region_zone_id` int NOT NULL,
  `created_at` datetime NOT NULL
);

ALTER TABLE `users` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

ALTER TABLE `card` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

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


