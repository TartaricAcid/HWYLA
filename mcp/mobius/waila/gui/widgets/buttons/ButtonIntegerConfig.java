package mcp.mobius.waila.gui.widgets.buttons;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import org.lwjgl.util.Point;

import mcp.mobius.waila.mod_Waila;
import mcp.mobius.waila.gui.events.MouseEvent;
import mcp.mobius.waila.gui.interfaces.IWidget;
import mcp.mobius.waila.gui.widgets.WidgetBase;

public class ButtonIntegerConfig extends ButtonInteger {

	private String configKey;
	private boolean instant;

	public ButtonIntegerConfig(IWidget parent, String configKey, String... texts){
		this(parent, configKey, true, 0, texts);
	}
	
	public ButtonIntegerConfig(IWidget parent, String configKey, boolean instant, int state_, String... texts){
		super(parent, texts);
		this.configKey = configKey;
		this.instant   = instant;
		
		mod_Waila.instance.config.load();
		Property prop = mod_Waila.instance.config.get(Configuration.CATEGORY_GENERAL, this.configKey, state_);
		this.state = prop.getInt();	
		
		for (int i = 0; i < this.nStates; i++)
			this.getWidget(String.format("Label_%d", i)).hide();
		
		this.getWidget(String.format("Label_%d", state)).show();			
	}
	
	@Override
	public void onMouseClick(MouseEvent event){
		super.onMouseClick(event);
		
		if (this.instant){
			mod_Waila.instance.config.getCategory(Configuration.CATEGORY_GENERAL).put(this.configKey, new Property(this.configKey,String.valueOf(this.state),Property.Type.INTEGER));
			mod_Waila.instance.config.save();
		}
	}

}