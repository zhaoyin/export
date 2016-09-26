/**
 * 
 */
package com.crt.excel.exports.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author UOrder
 *
 */
class ExportProperties {
	private static final String PROPERTIES_RESOURCE_LOCATION = "export.properties";

	private static final Logger logger = LoggerFactory.getLogger(ExportProperties.class);

	private static final Properties localProperties = new Properties();

	static {
		try {
			ClassLoader cl = ExportProperties.class.getClassLoader();
			URL url = (cl != null ? cl.getResource(PROPERTIES_RESOURCE_LOCATION)
					: ClassLoader.getSystemResource(PROPERTIES_RESOURCE_LOCATION));
			if (url != null) {
				logger.info("Found 'export.properties' file in local classpath");
				InputStream is = url.openStream();
				try {
					localProperties.load(is);
				} finally {
					is.close();
				}
			}
		} catch (IOException ex) {
			if (logger.isInfoEnabled()) {
				logger.info("Could not load 'export.properties' file from local classpath: " + ex);
			}
		}
	}

	/**
	 * Programmatically set a local property, overriding an entry in the
	 * {@code spring.properties} file (if any).
	 * 
	 * @param key
	 *            the property key
	 * @param value
	 *            the associated property value, or {@code null} to reset it
	 */
	public static void setProperty(String key, String value) {
		if (value != null) {
			localProperties.setProperty(key, value);
		} else {
			localProperties.remove(key);
		}
	}

	/**
	 * Retrieve the property value for the given key, checking local Spring
	 * properties first and falling back to JVM-level system properties.
	 * 
	 * @param key
	 *            the property key
	 * @return the associated property value, or {@code null} if none found
	 */
	public static String getProperty(String key) {
		String value = localProperties.getProperty(key);
		if (value == null) {
			try {
				value = System.getProperty(key);
			} catch (Throwable ex) {
				if (logger.isDebugEnabled()) {
					logger.debug("Could not retrieve system property '" + key + "': " + ex);
				}
			}
		}
		return value;
	}

	/**
	 * Programmatically set a local flag to "true", overriding an entry in the
	 * {@code spring.properties} file (if any).
	 * 
	 * @param key
	 *            the property key
	 */
	public static void setFlag(String key) {
		localProperties.put(key, Boolean.TRUE.toString());
	}

	/**
	 * Retrieve the flag for the given property key.
	 * 
	 * @param key
	 *            the property key
	 * @return {@code true} if the property is set to "true", {@code} false
	 *         otherwise
	 */
	public static boolean getFlag(String key) {
		return Boolean.parseBoolean(getProperty(key));
	}
}
