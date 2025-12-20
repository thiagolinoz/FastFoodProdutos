CREATE TRIGGER before_insert_tb_produtos
BEFORE INSERT ON tb_produtos
FOR EACH ROW
SET NEW.cd_produto = IF(NEW.cd_produto IS NULL OR NEW.cd_produto = '', UUID(), NEW.cd_produto);
