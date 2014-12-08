package org.coreasm.compiler.plugins.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.coreasm.compiler.classlibrary.ClassLibrary;
import org.coreasm.compiler.exception.CompilerException;
import org.coreasm.compiler.exception.EntryAlreadyExistsException;
import org.coreasm.compiler.exception.IncludeException;
import org.coreasm.compiler.mainprogram.EntryType;
import org.coreasm.compiler.mainprogram.MainFileEntry;
import org.coreasm.compiler.plugins.map.code.rcode.MapHandler;
import org.coreasm.engine.plugin.Plugin;
import org.coreasm.engine.plugins.map.MapBackgroundElement;
import org.coreasm.engine.plugins.map.MapToPairsFunctionElement;
import org.coreasm.engine.plugins.map.ToMapFunctionElement;
import org.coreasm.compiler.CodeType;
import org.coreasm.compiler.CoreASMCompiler;
import org.coreasm.compiler.interfaces.CompilerCodePlugin;
import org.coreasm.compiler.interfaces.CompilerPlugin;
import org.coreasm.compiler.interfaces.CompilerVocabularyExtender;

public class CompilerMapPlugin extends CompilerCodePlugin implements CompilerPlugin,
		CompilerVocabularyExtender {

	private Plugin interpreterPlugin;
	
	public CompilerMapPlugin(Plugin parent){
		this.interpreterPlugin = parent;
	}
	
	@Override
	public Plugin getInterpreterPlugin(){
		return interpreterPlugin;
	}

	@Override
	public List<MainFileEntry> loadClasses(ClassLibrary classLibrary)
			throws CompilerException {
		File enginePath = CoreASMCompiler.getEngine().getOptions().enginePath;
		List<MainFileEntry> result = new ArrayList<MainFileEntry>();
		
		if(enginePath == null){
			CoreASMCompiler.getEngine().getLogger().error(getClass(), "loading classes from a directory is currently not supported");
			throw new CompilerException("could not load classes");
		}
		else{
			try {
				//classLibrary.addPackageReplacement("org.coreasm.engine.plugins.collection.AbstractMapElement", "plugins.CollectionPlugin.AbstractMapElement");
				//classLibrary.addPackageReplacement("org.coreasm.compiler.plugins.collection.include.ModifiableCollection", "plugins.CollectionPlugin.ModifiableCollection");
				//classLibrary.addPackageReplacement("org.coreasm.engine.plugins.list.ListElement", "plugins.ListPlugin.ListElement");
				
				//package replacements for classes accessible from other plugins
				classLibrary.addPackageReplacement("org.coreasm.engine.plugins.map.MapBackgroundElement", "plugins.MapPlugin.MapBackgroundElement");
				classLibrary.addPackageReplacement("org.coreasm.compiler.plugins.map.include.MapElement", "plugins.MapPlugin.MapElement");
				
				result.add(new MainFileEntry(classLibrary.includeClass(enginePath, "org/coreasm/engine/plugins/map/MapBackgroundElement.java", this), EntryType.BACKGROUND, MapBackgroundElement.NAME));
				result.add(new MainFileEntry(classLibrary.includeClass(enginePath, "org/coreasm/engine/plugins/map/MapToPairsFunctionElement.java", this), EntryType.FUNCTION, MapToPairsFunctionElement.NAME));
				result.add(new MainFileEntry(classLibrary.includeClass(enginePath, "org/coreasm/engine/plugins/map/ToMapFunctionElement.java", this), EntryType.FUNCTION, ToMapFunctionElement.NAME));
				result.add(new MainFileEntry(classLibrary.includeClass(enginePath, "org/coreasm/compiler/plugins/map/include/MapElement.java", this), EntryType.INCLUDEONLY, ""));
			} catch (IncludeException e) {
				throw new CompilerException(e);
			} catch (EntryAlreadyExistsException e) {
				throw new CompilerException(e);
			}
		}

		return result;
	}

	@Override
	public String getName() {
		return "MapPlugin";
	}

	@Override
	public void registerCodeHandlers() throws CompilerException {
		register(new MapHandler(), CodeType.R, "Expression", "MapTerm", null);
	}
}