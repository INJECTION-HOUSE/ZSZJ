ALTER TABLE `zszj_db`.`task_tbl` 
ADD COLUMN `type` INT(1) NOT NULL COMMENT '1:��ע�ܻ� 2:���ܻ� 3: ���ܻ� 4: ��е�� 5: ��ע�ܻ�' AFTER `id`;