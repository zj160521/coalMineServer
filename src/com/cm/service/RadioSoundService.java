package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.IRadioSoundDao;
import com.cm.entity.RadioSound;

@Service
public class RadioSoundService implements IRadioSoundDao {

	@Autowired
	private IRadioSoundDao theDao;

	@Override
	public List<RadioSound> get(RadioSound radioSound) {
		return theDao.get(radioSound);
	}

	@Override
	public void add(RadioSound radioSound) {
		theDao.add(radioSound);
	}
}
