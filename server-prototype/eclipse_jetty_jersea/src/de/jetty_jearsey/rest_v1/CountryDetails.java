package de.jetty_jearsey.rest_v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/state")
public class CountryDetails {

	@GET
	@Path("/{param}")
	@Produces("application/xml")
	public String getMsg(@PathParam("param") String state) {

		String stateDetails = null;

		if (state.equals("GE")) {

			stateDetails = "<State>" +
					"<name>GERMANY</name>" 		 +
					"<shortname>GE</shortname>"  +
					"<headq>Berlin</headq>" 	 +
					"<language>German</language>"+
					"</State>";

		} else if (state.equals("RU")) {

			stateDetails = "<State>" +
							"<name>RUSSIA</name>"    		+
							"<shortname>RU</shortname>"		+
							"<headq>Moscow</headq>" 		+
							"<language>Russian</language>"  +
							"</State>";

		} else if (state.equals("USA")) {

			stateDetails = "<State>" +
					"<name>USA</name>" 				+
					"<shortname>USA</shortname>"	+
					"<headq>Washington</headq>" 	+
					"<language>English</language>"+
					"</State>";

		} else {

			stateDetails = "Data not found";

		}

		return stateDetails;

	}

}
