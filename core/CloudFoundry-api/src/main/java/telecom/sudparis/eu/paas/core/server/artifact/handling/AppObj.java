package telecom.sudparis.eu.paas.core.server.artifact.handling;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "app")
@XmlType(propOrder = { "id", "description", "source", "logicalName",
		"localName", "remoteName", "sizeInBytes", "method" })
public class AppObj {

	private String id;
	private String description;
	private String source;
	private String logicalName;
	private String localName;
	private String remoteName;
	private String method;
	private long sizeInBytes;

	public AppObj() {
	}

	public AppObj(String id, String description, String source,
			String logicalName, String localName, String remoteName,
			long sizeInBytes, String method) {
		this.id = id;
		this.description = description;
		this.source = source;
		this.logicalName = logicalName;
		this.localName = localName;
		this.remoteName = remoteName;
		this.sizeInBytes = sizeInBytes;
		this.method = method;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLogicalName() {
		return logicalName;
	}

	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getRemoteName() {
		return remoteName;
	}

	public void setRemoteName(String remoteName) {
		this.remoteName = remoteName;
	}

	public long getSizeInBytes() {
		return sizeInBytes;
	}

	public void setSizeInBytes(long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
