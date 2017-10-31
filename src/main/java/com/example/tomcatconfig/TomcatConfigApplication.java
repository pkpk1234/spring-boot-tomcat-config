package com.example.tomcatconfig;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2017-10-31
 */
@SpringBootApplication
public class TomcatConfigApplication implements EmbeddedServletContainerCustomizer {

	public static void main(String[] args) {
		SpringApplication.run(TomcatConfigApplication.class, args);
	}

	@Value("${server.additional-ports}")
	String ports;

	@Override
	public void customize(
			ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {

		if (ports != null) {
			// 判断如果是Tomcat才进行如下配置
			if (configurableEmbeddedServletContainer instanceof TomcatEmbeddedServletContainerFactory) {
				// 转类型为TomcatEmbeddedServletContainerFactory
				TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) configurableEmbeddedServletContainer;

				String[] portsArray = ports.split(",");
				for (String portStr : portsArray) {
					int port = Integer.parseInt(portStr);
					// Tomcat中，一个Connecter监听一个端口
					// 指定协议为HTTP/1.1
					Connector httpConnector = new Connector("HTTP/1.1");
					httpConnector.setPort(port);
					tomcat.addAdditionalTomcatConnectors(httpConnector);
				}

			}
		}
	}
}
