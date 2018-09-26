package com.cm.dao;

import java.util.List;

import com.cm.entity.RadioSound;

public interface IRadioSoundDao {
	public List<RadioSound> get(RadioSound radioSound);
	public void add(RadioSound radioSound);
}
