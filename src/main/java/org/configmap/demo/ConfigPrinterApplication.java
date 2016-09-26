package org.configmap.demo;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.swagger.SwaggerArchive;

/**
 * @author kameshs
 */

public class ConfigPrinterApplication {

    public static void main(String[] args) throws Exception {
        Swarm swarm = new Swarm();
        SwaggerArchive archive = ShrinkWrap.create(SwaggerArchive.class, "env-printer-app.war");
        JAXRSArchive deployment = archive.as(JAXRSArchive.class)
                .addPackage(ConfigPrinterApplication.class.getPackage());
        archive.setResourcePackages("org.configmap.demo");
        deployment.addAllDependencies();
        swarm
                .fraction(LoggingFraction.createDebugLoggingFraction())
                .start()
                .deploy(deployment);
    }
}
