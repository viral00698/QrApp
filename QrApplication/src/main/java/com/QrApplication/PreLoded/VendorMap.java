package com.QrApplication.PreLoded;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.Interface.VendorMapSubject;
import com.QrApplication.Repository.VenderRepository;

@Service
public class VendorMap  implements VendorMapSubject{

	@Autowired
	private VenderRepository venderRepository;
	
	private HashMap<UUID, String> map = new HashMap<>();
	

	@Override
	public String getVenderStorenameByUUID(UUID id) {
		return this.map.getOrDefault(id, "DEF"); //DEF is default prefix;
	}

	@Override
	public int getVenderMapFromDB() {
		HashMap<UUID, String> tmp = new HashMap<>();
		
		this.venderRepository.findAll().forEach(obj->{
			if(obj!=null) {
				tmp.put(obj.getVendorId(), obj.getStoreName());
			}else {
				return;
			}
		});
		map = tmp;
		return 1;
	}

	
	
}
