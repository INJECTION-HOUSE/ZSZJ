ALTER TABLE `zszj_db`.`task_tbl` 
ADD COLUMN `type` INT(1) NOT NULL COMMENT '1:立注塑机 2:吹塑机 3: 吸塑机 4: 机械手 5: 卧注塑机' AFTER `id`;