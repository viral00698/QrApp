package com.QrApplication.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.TableOrder;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Repository.TableOrderRepository;

@Service
public class TableOrderService {

	@Autowired
	private TableOrderRepository tableOrderRepository;
	
	public ResponseType addTable(TableOrder tableOrder) {
		
		if(tableOrder.getTableName()!=null && tableOrder.getVendorId()!=null) {
			TableOrder t = this.tableOrderRepository.save(tableOrder);
			System.err.println(t);
			if(t!=null) {
				return ResponseType.ResponseGenerator(RequestStatus.success, "Exciting Update: New Table Added!");
			}
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	public ResponseType getTableByVendorId(UUID vendorId) {
		
		if(vendorId!=null) {
			List<TableOrder> t = this.tableOrderRepository.findByVendorId(vendorId);
			if(t!=null) {
				return ResponseType.ResponseGenerator(RequestStatus.success, t);
			}
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}

	public ResponseType deleteTableByVendorId(UUID tableId) {
		if(tableId!=null) {
			int t = this.tableOrderRepository.deleteTableByVendorId(tableId);
			if(t>0) {
				return ResponseType.ResponseGenerator(RequestStatus.success, "Exciting Update: Table Deleted!");
			}
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	public ResponseType updateTableStatus(TableOrder tableOrder) {
		if(tableOrder!=null && tableOrder.getTableId()!=null && tableOrder.getTableStatus()!=null && tableOrder.getVendorId()!=null) {
			int t = this.tableOrderRepository.updateTableStatus(tableOrder.getTableStatus() , tableOrder.getTableId(), tableOrder.getVendorId());
			if(t>0) {
				return ResponseType.ResponseGenerator(RequestStatus.success, "Table Status Updated Successfully!");
			}
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	public TableOrder findByTableId(UUID tableId) {
		return tableOrderRepository.findByTableId(tableId);
	}
	
	

}
