package com.QrApplication.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.VendorDto;
import com.QrApplication.Entity.Address;
import com.QrApplication.Entity.Vendor;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Implementation.DocumentUpload;
import com.QrApplication.Mapper.VendorMapper;
import com.QrApplication.Repository.AddressRepos;
import com.QrApplication.Repository.VenderRepository;

@Service
public class CreateVendorService {

	@Value("${aadhaar.doc.dir:/home/ubuntu/AADHAR_DOC/}")
	private String addharDoc;

	@Value("${pan.doc.dir:/home/ubuntu/PAN_DOC/}")
	private String panDoc;

	@Value("${fssi.doc.dir:/home/ubuntu/FSSI_DOC/}")
	private String fssiDoc;

	@Value("${gst.doc.dir:/home/ubuntu/GST_DOC/}")
	private String gstDoc;

	@Value("${restro.doc.dir:/home/ubuntu/RESTRO_DOC/}")
	private String restroDoc;

	@Autowired
	private VendorMapper vendorMapper;

	@Autowired
	private VenderRepository venderRepository;

	@Autowired
	private AddressRepos addressRepos;

	@Autowired
	private DocumentUpload documentUpload;

	public ResponseType createVendor(VendorDto vendorDto) {

		try {

			String aadhaarResponse = documentUpload.uploadFile(addharDoc, vendorDto.getAadharDoc());
			vendorDto.setAadharDoc(aadhaarResponse);

			String panResponse = documentUpload.uploadFile(panDoc, vendorDto.getPanDoc());
			vendorDto.setPanDoc(panResponse);

			String gstResponse = documentUpload.uploadFile(gstDoc, vendorDto.getGstDoc());
			vendorDto.setGstDoc(gstResponse);

			String restroResponse = documentUpload.uploadFile(restroDoc, vendorDto.getPhoto());
			vendorDto.setPhoto(restroResponse);

			String fssiResponse = documentUpload.uploadFile(fssiDoc, vendorDto.getFssiDoc());
			vendorDto.setFssiDoc(fssiResponse);

			Vendor ver = vendorMapper.toEntity(vendorDto);
			ver.setCreateAt(new Date());

			Vendor res = venderRepository.save(ver);
			Address address = vendorDto.getAddress();
			address.setVendor(res);
			addressRepos.save(address);
			return ResponseType.ResponseGenerator(RequestStatus.success, "Vendor has been created successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Getting error while create vendor");
		}
	}

	public ResponseType getVendors() {
		try {
			List<Vendor> res = venderRepository.findAll();
			return ResponseType.ResponseGenerator(RequestStatus.success, "Vendors fetched successfully", res);
		} catch (Exception e) {
			e.printStackTrace(); // or use a logger
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Failed to fetch vendors: " + e.getMessage());
		}
	}

	public ResponseType changeVendorStatus(VendorDto vendorDto) {
		try {
			int updated = venderRepository.changeVendorStatus(vendorDto.getVendorId(), vendorDto.getStatus());

			if (updated > 0) {
				return ResponseType.ResponseGenerator(RequestStatus.success, "Vendor status updated successfully.",
						updated);
			} else {
				return ResponseType.ResponseGenerator(RequestStatus.failure, "Vendor not found or status not changed.");
			}
		} catch (Exception e) {
			e.printStackTrace(); // Or use a logger
			return ResponseType.ResponseGenerator(RequestStatus.failure,
					"An error occurred while updating vendor status: " + e.getMessage());
		}
	}

}
