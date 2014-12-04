package org.smart.service;

import java.util.List;

import org.smart.model.Alert;


public interface AlertManager {     
    public void addAlert(Alert alert);
    public List<Alert> listAlert();
    public void removeAlert(Integer id);
	public Alert findOneByNodeId(Integer alertId);
}
