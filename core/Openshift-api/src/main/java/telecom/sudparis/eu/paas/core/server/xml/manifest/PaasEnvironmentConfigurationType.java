//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.02.26 at 10:10:09 AM CET 
//


package telecom.sudparis.eu.paas.core.server.xml.manifest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paas_environment_configurationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paas_environment_configurationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paas_environment_variable" type="{}paas_environment_variableType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_environment_configurationType", propOrder = {
    "paasEnvironmentVariable"
})
public class PaasEnvironmentConfigurationType {

    @XmlElement(name = "paas_environment_variable", required = true)
    protected List<PaasEnvironmentVariableType> paasEnvironmentVariable;

    /**
     * Gets the value of the paasEnvironmentVariable property.
     * 
     * @return
     *     possible object is
     *     {@link PaasEnvironmentVariableType }
     *     
     */
    public List<PaasEnvironmentVariableType> getPaasEnvironmentVariable() {
        return paasEnvironmentVariable;
    }

    /**
     * Sets the value of the paasEnvironmentVariable property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasEnvironmentVariableType }
     *     
     */
    public void setPaasEnvironmentVariable(List<PaasEnvironmentVariableType> value) {
        this.paasEnvironmentVariable = value;
    }

}
