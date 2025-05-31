package com.QrApplication.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.GenrateInvoiceDto;
import com.QrApplication.Entity.OrderDetails;
import com.QrApplication.Enum.RequestStatus;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InvoicePdfService {
	
	@Value("${product.invoice.dir:/home/ubuntu/invoice/}")
	String invoiceDestinastion;
	
	@Value("${product.logo.dir:/home/ubuntu/logo/}")
	String logo;
	
	@Autowired
	private TwilioSmsInvoice twilioSmsInvoice;
	
	private static final Logger logger = LoggerFactory.getLogger(InvoicePdfService.class);

	public ResponseType print(GenrateInvoiceDto genrateInvoiceDto) {
		try {
			String orderId = genrateInvoiceDto.getOrder().getOrderId().toString();
			String first8Digits  = orderId.length() > 8 ? orderId.substring(0, 8) : orderId;
			String dest = invoiceDestinastion +"/invoice_"+first8Digits+".pdf";
//			String dest = "D://test/receipt.pdf";
//			String dest = "/home/ubuntu/myfile/receipt.pdf";
			File file = new File(dest);
			file.getParentFile().mkdirs();

			PdfWriter writer = new PdfWriter(new FileOutputStream(dest));
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf, PageSize.A7); // Small receipt size
			document.setMargins(10, 10, 10, 10);

//			// Custom Font (Optional)
//			PdfFont font = PdfFontFactory.createFont();
			InputStream fontStream = getClass().getClassLoader().getResourceAsStream("DejaVuSans.ttf");
			PdfFont font = PdfFontFactory.createFont(fontStream.readAllBytes(), PdfEncodings.IDENTITY_H);

//			PdfFont font = PdfFontFactory.createFont("src/main/resources/DejaVuSans.ttf", PdfEncodings.IDENTITY_H);

//			ImageData imageData = ImageDataFactory.create(logo);
//			Image logo = new Image(imageData).setWidth(15).setHeight(15); // Resize the logo
//			logo.setHorizontalAlignment(HorizontalAlignment.CENTER);

//			document.add(logo);

			Paragraph title = new Paragraph(genrateInvoiceDto.getVendor().getStoreName()).setFont(font).setFontSize(10).setBold()
					.setMarginBottom(0).setTextAlignment(TextAlignment.CENTER);
			document.add(title);

			Paragraph tokenTitle = new Paragraph("Address: Gujarat , Ahmedabad 04789").setFont(font).setBold()
					.setFontSize(6).setMarginTop(1).setTextAlignment(TextAlignment.CENTER);
			document.add(tokenTitle);
			
			Paragraph date = new Paragraph(genrateInvoiceDto.getOrder().getOrderAt().toString()).setFont(font).setBold()
					.setFontSize(6).setMarginTop(1).setTextAlignment(TextAlignment.CENTER);
			document.add(date);

			// Divider
			SolidLine line = new SolidLine(1f); // Thickness of the line
			DottedLine dot = new DottedLine(1f);
			LineSeparator separator = new LineSeparator(dot);
			separator.setMarginTop(2); // Space above the line
			separator.setMarginBottom(2); // Space below the line

			// Add the separator to the document
			document.add(separator);
 

			Table table = new Table(2).useAllAvailableWidth();

			table.addCell(getCell("Order Id", TextAlignment.LEFT, true));
			
			table.addCell(getCell(first8Digits , TextAlignment.RIGHT, false));
			table.addCell(getCell("Customer Name", TextAlignment.LEFT, true));
			table.addCell(getCell(genrateInvoiceDto.getOrder().getCustomerName(), TextAlignment.RIGHT, false));
			table.addCell(getCell("Mobile No", TextAlignment.LEFT, true));
			table.addCell(getCell(genrateInvoiceDto.getOrder().getCustomerMobileNo(), TextAlignment.RIGHT, false));
			table.addCell(getCell("Payment Type", TextAlignment.LEFT, true));
			table.addCell(getCell(genrateInvoiceDto.getOrder().getPayment_mode().toString(), TextAlignment.RIGHT, false));
			

			document.add(table);

			document.add(separator);

			// order details
			float[] columnWidths = { 5, 2, 3 };
			Table tableColumns = new Table(columnWidths).useAllAvailableWidth();
			tableColumns.addCell(getCell("Item", TextAlignment.LEFT, true));
			tableColumns.addCell(getCell("Qty", TextAlignment.CENTER, true));
			tableColumns.addCell(getCell("Amount", TextAlignment.RIGHT, true));
			document.add(tableColumns);
			document.add(separator);

			// ✅ Create Order Items Table
			Table orderItems = new Table(columnWidths).useAllAvailableWidth();
			for (OrderDetails d : genrateInvoiceDto.getOrder().getOrderDetails()) {
				orderItems.addCell(getCell(d.getItemName(), TextAlignment.LEFT, false));
				orderItems.addCell(getCell(d.getQuntity().toString(), TextAlignment.CENTER, false));
				orderItems.addCell(getCell(d.getAmount()+"₹", TextAlignment.RIGHT, false).setFont(font));
			}

			document.add(orderItems);
			document.add(separator);

			Table amount = new Table(2).useAllAvailableWidth();

			amount.addCell(getCell("Restaurant Charge", TextAlignment.LEFT, true));
			amount.addCell(getCell( genrateInvoiceDto.getOrder().getBillingDtos().getResturentCharge()+"₹", TextAlignment.RIGHT, true).setFont(font));

			amount.addCell(getCell("SGST", TextAlignment.LEFT, true));
			amount.addCell(getCell(genrateInvoiceDto.getOrder().getBillingDtos().getSgst()+"₹", TextAlignment.RIGHT, true).setFont(font));

			amount.addCell(getCell("GST", TextAlignment.LEFT, true));
			amount.addCell(getCell(genrateInvoiceDto.getOrder().getBillingDtos().getGst()+"₹", TextAlignment.RIGHT, true).setFont(font));

			amount.addCell(getCell("Total Amount", TextAlignment.LEFT, true));
			amount.addCell(getCell(genrateInvoiceDto.getOrder().getBillingDtos().getTotalAmount()+"₹", TextAlignment.RIGHT, true).setFont(font));

			document.add(amount);
			document.add(separator);

		
		
			BufferedImage bufferedImage = generateQRCodeImage(genrateInvoiceDto); // Pass valid string

			// Convert BufferedImage to byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);

			byte[] imageBytes = baos.toByteArray();

			// Convert byte array to ImageData
			ImageData qr = ImageDataFactory.create(imageBytes);
			Image qrImage = new Image(qr).setWidth(100).setHeight(100); // Resize the logo
			qrImage.setHorizontalAlignment(HorizontalAlignment.CENTER);

			document.add(qrImage);
			
			Paragraph footer = new Paragraph("Sacn & Pay").setFont(font).setFontSize(6).setBold().setMarginBottom(0)
					.setTextAlignment(TextAlignment.CENTER);
			document.add(footer);

			
			document.add(separator);
			Paragraph qrMsg = new Paragraph("Powered By: Vitts.in").setFont(font).setFontSize(6).setBold()
					.setMarginBottom(0).setTextAlignment(TextAlignment.CENTER);
			document.add(qrMsg);

			document.close();
			System.out.println("PDF Receipt Created: " + dest);
			
			twilioSmsInvoice.sendSms(genrateInvoiceDto.getOrder().getOrderId() , genrateInvoiceDto.getOrder().getCustomerMobileNo());
			
		} catch (Exception e) {
			   logger.error( e.getMessage() );
			e.printStackTrace();
		}
		return ResponseType.ResponseGenerator(RequestStatus.success, "Ok");
	}

	private static Cell getCell(String text, TextAlignment alignment, boolean isBold) {
		Cell cell = new Cell().add(new Paragraph(text).setFontSize(8)).setTextAlignment(alignment);
		if (isBold) {
			cell.setBold();
		}
		cell.setBorder(null);
		return cell;
	}

	public BufferedImage generateQRCodeImage(GenrateInvoiceDto genrateInvoiceDto) throws WriterException, UnsupportedEncodingException {

		String upiId = genrateInvoiceDto.getVendor().getUpa();
		String payeeName = genrateInvoiceDto.getVendor().getStoreName();
		String transactionNote = "Payment For Order";
		double amount = genrateInvoiceDto.getOrder().getTotelAmount();
		String currency = "INR";
		String callbackUrl = "https://qr.vitts.in/pg";
		String upiLink = String.format("upi://pay?pa=%s&pn=%s&tn=%s&am=%.2f&cu=%s&url=%s", upiId, payeeName,
				 transactionNote, amount, currency , URLEncoder.encode(callbackUrl, "UTF-8"));

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(upiLink, BarcodeFormat.QR_CODE, 100, 100);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}
}
