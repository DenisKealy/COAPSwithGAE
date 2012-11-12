/**
 * 
 */
package telecom.sudparis.eu.paas.core.server.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;


/**
 * @author sellami
 *
 */


@XmlRootElement(name = "staging")
@XmlAccessorType(XmlAccessType.NONE)
public class StagingXML {

	public Map<String, String> map = new HashMap<String, String>();
	

	public StagingXML(){
		
	}
    public StagingXML(Map<String, String> map) {
		this.map = new HashMap<String, String>();
		this.map.putAll(map);
	}

    @XmlElement(name = "ressource")
    public StagingMapEntry[] getMap() {
        List<StagingMapEntry> list = new ArrayList<StagingMapEntry>();
        for (Entry<String, String> entry : map.entrySet()) {
            StagingMapEntry mapEntry =new StagingMapEntry();
            mapEntry.key = entry.getKey();
            mapEntry.value = entry.getValue();
            list.add(mapEntry);
        }
        return list.toArray(new StagingMapEntry[list.size()]);
    }
    
    public void setMap(StagingMapEntry[] arr) {
        for(StagingMapEntry entry : arr) {
            this.map.put(entry.key, entry.value);
        }
    }

   // @XmlType(name="ressource",namespace="staging")
    private static class StagingMapEntry {
        @XmlAttribute
        public String key;
        @XmlValue
        public String value;
    }

}