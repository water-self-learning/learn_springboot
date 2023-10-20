/*
Navicat Premium Data Transfer
Source Server : 192.168.31.250
Source Server Type : MySQL
Source Server Version : 80027
Source Host : 192.168.31.250:3306
Source Schema : authz
Target Server Type : MySQL
Target Server Version : 80027
File Encoding : 65001
Date: 25/10/2022 15:09:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authorities
-- ----------------------------
DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
                               `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
                                   NOT NULL,
                               `authority` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                               UNIQUE INDEX `ix_auth_username`(`username`, `authority`) USING BTREE,
                               CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of authorities
-- ----------------------------
-- ----------------------------
-- Table structure for cust_user
-- ----------------------------
DROP TABLE IF EXISTS `cust_user`;
CREATE TABLE `cust_user` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `enabled` tinyint NOT NULL DEFAULT 1,
                             PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cust_user
-- ----------------------------
INSERT INTO `cust_user` VALUES (1, 'zhangsan', 'zhangsan', '张三', 1);
INSERT INTO `cust_user` VALUES (2, 'lisi', 'lisi', '李四', 1);
INSERT INTO `cust_user` VALUES (3, 'wangwu', 'wangwu', '王五', 1);
-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
                             NOT NULL,
                         `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `enabled` tinyint(1) NOT NULL,
                         PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;
