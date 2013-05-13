package telecom.sudparis.eu.paas.core.server.artifact.handling;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "apps")
public class AppList {

	@XmlTransient
	private ArrayList<AppObj> listApps;

	public AppList() {
		listApps = new ArrayList<AppObj>();
	}

	public AppObj searchForAppObj(String appID) {
		System.out.println("listApps " + listApps);
		System.out.println("appID" + appID);
		if (!isEmpty())
			for (AppObj a : listApps)
				if (a.getId().equals(appID))
					return a;
		return null;
	}

	public boolean isEmpty() {
		return (listApps == null || listApps.size() == 0) ? true : false;
	}

	public void addAppToList(AppObj app) {
		if (listApps != null)
			listApps.add(app);
	}

	public void deleteAppByID(String appID) {
		AppObj app = searchForAppObj(appID);
		if (app != null)
			listApps.remove(app);
	}

	public void deleteApp(AppObj app) {
		if (app != null)
			listApps.remove(app);
	}

	@XmlElement(name = "app")
	public ArrayList<AppObj> getListOfApps() {
		return listApps;
	}

	public void setListOfApps(ArrayList<AppObj> listOfApps) {
		this.listApps = listOfApps;
	}

}
