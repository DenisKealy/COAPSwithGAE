//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2013.05.13 � 03:02:07 PM CEST 
//


package telecom.sudparis.eu.paas.core.server.xml.environment.list;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour environmentsType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="environmentsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="environment" type="{}environmentType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "environments")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "environments", propOrder = {
    "environment"
})
public class EnvironmentsType {

    @XmlElement(required = true)
    protected List<SimpleEnvironmentType> environment;


	/**
     * Gets the value of the environment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the environment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEnvironment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SimpleEnvironmentType }
     * 
     * 
     */
    public List<SimpleEnvironmentType> getEnvironment() {
        if (environment == null) {
            environment = new ArrayList<SimpleEnvironmentType>();
        }
        return this.environment;
    }
    
    public void setEnvironment(List<SimpleEnvironmentType> e) {
        this.environment=e;
    }


}
