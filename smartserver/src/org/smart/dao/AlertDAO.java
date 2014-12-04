package org.smart.dao;
 
import java.util.List;

import org.smart.model.Alert;
 
public interface AlertDAO {
     
    public void addAlert(Alert alert);
    public List<Alert> listAlert();
    public Alert findOne(Integer id);
    public void removeAlert(Integer id);
}