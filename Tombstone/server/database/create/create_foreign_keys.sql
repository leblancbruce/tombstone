USE $(database_name);
GO

PRINT 'Creating the foreign key(s) on the image table'
GO

ALTER TABLE image ADD CONSTRAINT image_application_user_id_FK FOREIGN KEY (application_user_id) references application_user(id);
ALTER TABLE image ADD CONSTRAINT image_cemetery_id_FK FOREIGN KEY (cemetery_id) references cemetery(id);
ALTER TABLE image ADD CONSTRAINT image_plot_id_FK FOREIGN KEY (plot_id) references plot(id);
ALTER TABLE image ADD CONSTRAINT image_person_id_FK FOREIGN KEY (person_id) references person(id);
GO

PRINT 'Creating the foreign key(s) on the cemetery table'
GO

ALTER TABLE cemetery ADD CONSTRAINT cemetery_created_by_id_FK FOREIGN KEY (created_by_application_user_id) references application_user(id);
ALTER TABLE cemetery ADD CONSTRAINT cemetery_updated_by_id_FK FOREIGN KEY (updated_by_application_user_id) references application_user(id);
GO

PRINT 'Creating the foreign key(s) on the plot table'
GO

ALTER TABLE plot ADD CONSTRAINT plot_created_by_id_FK FOREIGN KEY (created_by_application_user_id) references application_user(id);
ALTER TABLE plot ADD CONSTRAINT plot_updated_by_id_FK FOREIGN KEY (updated_by_application_user_id) references application_user(id);
ALTER TABLE plot ADD CONSTRAINT plot_cemetery_id_FK FOREIGN KEY (cemetery_id) references cemetery(id);
GO

PRINT 'Creating the foreign key(s) on the person table'
GO

ALTER TABLE person ADD CONSTRAINT person_created_by_id_FK FOREIGN KEY (created_by_application_user_id) references application_user(id);
ALTER TABLE person ADD CONSTRAINT person_updated_by_id_FK FOREIGN KEY (updated_by_application_user_id) references application_user(id);
ALTER TABLE person ADD CONSTRAINT person_cemetery_id_FK FOREIGN KEY (cemetery_id) references cemetery(id);
ALTER TABLE person ADD CONSTRAINT person_plot_id_FK FOREIGN KEY (plot_id) references plot(id);
GO

PRINT 'Creating the foreign key(s) on the next_of_kin table'
GO

ALTER TABLE next_of_kin ADD CONSTRAINT next_of_kin_created_by_id_FK FOREIGN KEY (created_by_application_user_id) references application_user(id);
ALTER TABLE next_of_kin ADD CONSTRAINT next_of_kin_updated_by_id_FK FOREIGN KEY (updated_by_application_user_id) references application_user(id);
ALTER TABLE next_of_kin ADD CONSTRAINT next_of_kin_person_id_FK FOREIGN KEY (person_id) references person(id);
ALTER TABLE next_of_kin ADD CONSTRAINT next_of_kin_next_of_kin_person_id_FK FOREIGN KEY (next_of_kin_person_id) references person(id);
GO
