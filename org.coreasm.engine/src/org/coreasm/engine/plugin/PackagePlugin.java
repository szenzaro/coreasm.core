/*	
 * PackagePlugin.java 	1.0 	$Revision: 243 $
 * 
 * Copyright (C) 2006 Roozbeh Farahbod
 *
 * Last modified by $Author: rfarahbod $ on $Date: 2011-03-29 02:05:21 +0200 (Di, 29 Mrz 2011) $.
 *
 * Licensed under the Academic Free License version 3.0
 *   http://www.opensource.org/licenses/afl-3.0.php
 *   http://www.coreasm.org/afl-3.0.php
 *
 */
 
package org.coreasm.engine.plugin;

import java.util.Set;

/** 
 * Interface to plugins that are act as a package containing other plugins.
 *   
 * @author  Roozbeh Farahbod
 * 
 */
public interface PackagePlugin {

	public Set<String> getEnclosedPluginNames();
	
}
