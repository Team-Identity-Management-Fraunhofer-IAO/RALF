package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadDTO {
	private int operator_id;
	private String file_upload_name;
	private int first_data_line;
	private String file_type;
	private int transformation_order_id;
	private int order;
	
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	public String getFile_upload_name() {
		return file_upload_name;
	}
	public void setFile_upload_name(String file_upload_name) {
		this.file_upload_name = file_upload_name;
	}
	public int getFirst_data_line() {
		return first_data_line;
	}
	public void setFirst_data_line(int first_data_line) {
		this.first_data_line = first_data_line;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public int getTransformation_order_id() {
		return transformation_order_id;
	}
	public void setTransformation_order_id(int transformation_order_id) {
		this.transformation_order_id = transformation_order_id;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
