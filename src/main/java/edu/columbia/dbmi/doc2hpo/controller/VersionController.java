package edu.columbia.dbmi.doc2hpo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.columbia.dbmi.doc2hpo.pojo.Version;


@Controller
public class VersionController {

	@RequestMapping("/version")
	@ResponseBody
	public Version getVersion() {
		Version version = new Version();
		// major version: new function backward incompatible;
		// minor version: new function backward compatible;
		// patch: bug corrected;
		version.setDoc2hpo("0.16.2");
		version.setJava("1.8.0_191");
		version.setTomcat("8.5.35");
		version.setMetamap("2016v2");
		version.setNcbo(null);

		return version;
    }

}
