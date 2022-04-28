package today.sleek.client.gui.click.components.tab.configuration;

import today.sleek.Sleek;
import today.sleek.base.config.Config;
import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.components.Component;
import today.sleek.client.gui.click.components.base.BaseContainer;
import today.sleek.client.gui.click.utils.Draw;
import today.sleek.client.utils.font.Fonts;
import java.awt.*;

/**
 * @author antja03
 */
public class ContainerConfigurations extends BaseContainer {

    private int scrollIndex;
    private Config selectedConfig;

    public ContainerConfigurations(Interface theInterface, double x, double y, double width, double height) {
        super(theInterface, x, y, width, height);
        this.scrollIndex = 0;
        refresh();
    }

    public void refresh() {
        components.clear();
        /*/Helium.instance.configurationManager.getConfigurationList().forEach(config -> {
            components.add(new ButtonConfiguration(theInterface, config, this.positionX, this.positionY + (10 * Helium.instance.configurationManager.getConfigurationList().indexOf(config)), this.maxWidth - 2.5, 10,
                    (int button) -> {
                        if (button == 0) {
                            if (selectedConfig != config) {
                                selectedConfig = config;
                            } else {
                                selectedConfig = null;
                            }
                        }
                    }, () -> selectedConfig == config));
        });/*/

        Sleek.getInstance().getConfigManager().getConfigs().forEach(config -> {
            components.add(new ButtonConfiguration(theInterface, config, this.positionX, this.positionY + (10 * Sleek.getInstance().getConfigManager().getConfigs().indexOf(config)), this.maxWidth - 2.5, 10,
                    (int button) -> {
                        if (button == 0) {
                            if (selectedConfig != config) {
                                selectedConfig = config;
                            } else {
                                selectedConfig = null;
                            }
                        }
                    }, () -> selectedConfig == config));
        });

    }

    public void drawComponent(double x, double y) {
        double barHeight = this.maxHeight;
        double div = barHeight / components.size();
        if (components.size() > 19) {
            barHeight -= (components.size() - 19) * div;
        }
        double barPosition = div * scrollIndex;

        Draw.drawRectangle(x, y, x + this.maxWidth, y + this.maxHeight + 51, new Color(32, 31, 32).getRGB());

        String cat = "Configs";
        Draw.drawRectangle(theInterface.getPositionX() + 25, theInterface.getPositionY() - 1, theInterface.getPositionX() + 173, theInterface.getPositionY() + 251, new Color(32, 31, 32).getRGB());
        Draw.drawCircle( Fonts.clickGuiVerdana.getStringWidth(cat) + 50 + theInterface.getPositionX(), theInterface.getPositionY() + 26, 13, new Color(149, 4, 245).getRGB());
        Fonts.clickGuiVerdana.drawStringWithShadow(cat, theInterface.getPositionX() + 35, theInterface.getPositionY() + 20, new Color(149, 4, 245).getRGB());

        if (components.size() < 10) {
            Fonts.clickGuiVerdana.drawString("" + components.size(), Fonts.clickGuiVerdana.getStringWidth(cat) + 50 + theInterface.getPositionX() - Fonts.clickGuiVerdana.getStringWidth(components.size() + "") + 4, theInterface.getPositionY() + 20, new Color(255, 255, 255).getRGB());
        } else {
            Fonts.clickGuiVerdana.drawString("" + components.size(), Fonts.clickGuiVerdana.getStringWidth(cat) + 50 + theInterface.getPositionX() - Fonts.clickGuiVerdana.getStringWidth(components.size() + "") + 9, theInterface.getPositionY() + 20, new Color(255, 255, 255).getRGB());
        }

        //Draw.drawRectangle(x + this.maxWidth - 2.5, y + barPosition, x + this.maxWidth - 1, y + barPosition + barHeight, theInterface.getColor(255, 255, 255));
        int index = 0;
        for (Component component : components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 19) {
                component.drawComponent(theInterface.getPositionX() + component.positionX, 50 + y + (10 * index));
                index += 1;
            }
        }
    }

    public boolean mouseButtonClicked(int button) {
        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 19) {
                if (component.mouseButtonClicked(button)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void mouseScrolled(final int scrollDirection) {
        if (theInterface.getCurrentFrameMouseX() > theInterface.getPositionX() + this.maxWidth)
            return;

        if (scrollDirection == 1) {
            if (scrollIndex < components.size() - 19) {
                scrollIndex += 1;
            }
        } else {
            if (scrollIndex > 0) {
                scrollIndex -= 1;
            }
        }

        for (Component component : components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 9) {
                component.mouseScrolled(scrollDirection);
            }
        }
    }

    public Config getSelectedConfig() {
        return selectedConfig;
    }
}
