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

@XmlRootElement(name = "ressources")
@XmlAccessorType(XmlAccessType.NONE)
public class RessourcesXML {

	private Map<String, Integer> map = new HashMap<String, Integer>();

	public RessourcesXML() {

	}

	public RessourcesXML(Map<String, Integer> map) {
		this.map = new HashMap<String, Integer>();
		this.map.putAll(map);
	}

	@XmlElement(name = "ressource", namespace = "ressources")
	public RessourcesMapEntry[] getMap() {
		List<RessourcesMapEntry> list = new ArrayList<RessourcesMapEntry>();
		for (Entry<String, Integer> entry : map.entrySet()) {
			RessourcesMapEntry mapEntry = new RessourcesMapEntry();
			mapEntry.key = entry.getKey();
			mapEntry.value = entry.getValue();
			list.add(mapEntry);
		}
		return list.toArray(new RessourcesMapEntry[list.size()]);
	}

	public void setMap(RessourcesMapEntry[] arr) {
		for (RessourcesMapEntry entry : arr) {
			this.map.put(entry.key, entry.value);
		}
	}

	// @XmlType(name="ressource",namespace="ressources")
	public static class RessourcesMapEntry {
		@XmlAttribute
		public String key;
		@XmlValue
		public Integer value;
	}

}