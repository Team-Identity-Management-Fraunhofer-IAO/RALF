/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.data.objects.persistence.platforms;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cpe")
public class CPECore implements Serializable {

    int cpeID;
    String part;
    String vendor;
    String product;
    String version;
    String cpe_update;
    String edition;
    String cpe_language;
    String sw_edition;
    String target_sw;
    String target_hw;
    String other;
    String versionEND;
    String URI;
    boolean include;
    boolean standardized;

    public CPECore() {
        this.standardized = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cpeID", nullable = false)
    public int getCpeID() {
        return cpeID;
    }

    public void setCpeID(int cpeID) {
        this.cpeID = cpeID;
    }

    @Column(name = "part", nullable = true)
    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    @Column(name = "vendor", nullable = true)
    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Column(name = "product", nullable = true)
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Column(name = "version", nullable = true)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "cpe_update", nullable = true)
    public String getCpe_update() {
        return cpe_update;
    }

    public void setCpe_update(String cpe_update) {
        this.cpe_update = cpe_update;
    }

    @Column(name = "edition", nullable = true)
    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    @Column(name = "cpe_language", nullable = true)
    public String getCpe_language() {
        return cpe_language;
    }

    public void setCpe_language(String cpe_language) {
        this.cpe_language = cpe_language;
    }

    @Column(name = "sw_edition", nullable = true)
    public String getSw_edition() {
        return sw_edition;
    }

    public void setSw_edition(String sw_edition) {
        this.sw_edition = sw_edition;
    }

    @Column(name = "target_sw", nullable = true)
    public String getTarget_sw() {
        return target_sw;
    }

    public void setTarget_sw(String target_sw) {
        this.target_sw = target_sw;
    }

    @Column(name = "target_hw", nullable = true)
    public String getTarget_hw() {
        return target_hw;
    }

    public void setTarget_hw(String target_hw) {
        this.target_hw = target_hw;
    }

    @Column(name = "other", nullable = true)
    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Column(name = "versionEND", nullable = true)
    public String getVersionEND() {
        return versionEND;
    }

    public void setVersionEND(String versionEND) {
        this.versionEND = versionEND;
    }

    @Column(name = "include", nullable = true)
    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    @Column(name = "URI", nullable = false)
    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    @Column(name = "standardized", nullable = false)
    public boolean isStandardized() {
        return standardized;
    }

    public void setStandardized(boolean standardized) {
        this.standardized = standardized;
    }
    
    

    public void disassembleCPEURI(String uri) {
        if (uri != null) {
            String[] uriFields = uri.split(":");
            if (uriFields.length > 0) {
                for (int i = 0; i < uriFields.length; i++) {
                    if (i == 0 && uriFields[0].equals("cpe")) {
                        continue;
                    } else if (i == 0) {
                        break;
                    } else if (i == 1 && uriFields[1].equals("2.3")) {
                        continue;
                    } else if (i == 1) {
                        break;
                    } else if (i == 2) {
                        switch (uriFields[i]) {
                            case "a":
                                //Applicaton
                                this.part = "application";
                                break;
                            case "o":
                                //OS
                                this.part = "operating system";
                                break;
                            case "h":
                                //HW
                                this.part = "hardware";
                                break;
                            default:
                                break;
                        }
                    } else if (i == 3) {
                        this.vendor = uriFields[i];
                    } else if (i == 4) {
                        this.product = uriFields[i];
                    } else if (i == 5) {
                        this.version = uriFields[i];
                    } else if (i == 6) {
                        this.cpe_update = uriFields[i];
                    } else if (i == 7) {
                        this.edition = uriFields[i];
                    } else if (i == 8) {
                        this.cpe_language = uriFields[i];
                    } else if (i == 9) {
                        this.sw_edition = uriFields[i];
                    } else if (i == 10) {
                        this.target_sw = uriFields[i];
                    } else if (i == 11) {
                        this.target_hw = uriFields[i];
                    } else if (i == 12) {
                        this.other = uriFields[i];
                    }
                }
            }
        }
    }
    
    public boolean equals(Object o) {
    	if (o == this) {
    		return true;
    	}else if (o instanceof CPECore) {
    		if (((CPECore) o).getCpeID() == this.cpeID) {
    			return true;
    		}
    	}
    	return false;
    }

}
