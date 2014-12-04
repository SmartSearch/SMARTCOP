package org.smart.service.impl;

import java.util.List;

import org.smart.dao.AlertDAO;
import org.smart.model.Alert;
import org.smart.service.AlertManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlertManagerImpl implements AlertManager {

	@Autowired
	private AlertDAO alertDao;

	@Transactional
	public void addAlert(Alert alert) {
		alertDao.addAlert(alert);
	}

	@Transactional
	public List<Alert> listAlert() {
		return alertDao.listAlert();
	}

	@Transactional
	public void removeAlert(Integer id) {
		alertDao.removeAlert(id);
	}

	@Transactional
	@Override
	public Alert findOneByNodeId(Integer alertId) {
		return alertDao.findOne(alertId);
	}
}