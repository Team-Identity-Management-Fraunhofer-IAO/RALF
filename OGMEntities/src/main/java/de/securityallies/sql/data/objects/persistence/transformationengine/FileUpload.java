package de.securityallies.sql.data.objects.persistence.transformationengine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="file_upload")
public class FileUpload {
	private int operator_id;
	private String file_upload_name;
	private int first_data_line;
	private String file_type;
	private byte[] content;
	
	@Id
	@Column(name="operator_id", nullable=false)
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	
	@Column(name="file_upload_name", nullable=false)
	public String getFile_upload_name() {
		return file_upload_name;
	}
	public void setFile_upload_name(String file_upload_name) {
		this.file_upload_name = file_upload_name;
	}
	
	@Column(name="first_data_line", nullable=false)
	public int getFirst_data_line() {
		return first_data_line;
	}
	public void setFirst_data_line(int first_data_line) {
		this.first_data_line = first_data_line;
	}
	
	@Column(name="file_type", nullable=false)
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	
	@Lob
	@Column(name="content", nullable=true)
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}

	
}
