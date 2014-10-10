package fuj1n.recmod.client.gui;

import fuj1n.recmod.RecMod;
import fuj1n.recmod.inventory.ContainerDummy;
import fuj1n.recmod.network.packet.PacketUpdatePlayerStatus;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiSimple extends GuiContainer {
	public String boundPlayer;

	public int guiState = 0;
	public String[] stateNames = { translate("recmod.gui.pane.title.main"), translate("recmod.gui.pane.title.general"), translate("recmod.gui.pane.title.interface"), translate("recmod.gui.pane.title.keyboard"), translate("recmod.gui.pane.title.integration"), translate("recmod.gui.pane.title.testing") };
	public int[] returnStates = { -1337, 0, 0, 0, 0, 0 };

	// Keyboard pane globals
	public int listenKeyType = -1;

	public ResourceLocation background = new ResourceLocation("recmod:textures/gui/simple.png");

	public GuiSimple(String boundPlayer) {
		super(new ContainerDummy());

		this.boundPlayer = boundPlayer;

		if (stateNames.length != returnStates.length) {
			throw new IllegalStateException("The length of GuiSimple.stateNames must match GuiSimple.returnStates");
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(translate("recmod.interface.name") + stateNames[guiState], 8, 11, 4210752);

		if (guiState == 4) {
			this.fontRendererObj.drawString(translate("recmod.gui.pane.nyitext1"), 8, 11 + fontRendererObj.FONT_HEIGHT * 2, 4210752);
			this.fontRendererObj.drawString(translate("recmod.gui.pane.nyitext2"), 8, 11 + fontRendererObj.FONT_HEIGHT * 3, 4210752);
			this.fontRendererObj.drawString(translate("recmod.gui.pane.nyitext3"), 8, 11 + fontRendererObj.FONT_HEIGHT * 4, 4210752);
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(background);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void initGui() {
		super.initGui();

		this.createGui();
	}

	public void createGui() {
		buttonList.clear();

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

		buttonList.add(new GuiButton(returnStates[guiState] == -1337 ? -1337 : 1337 + returnStates[guiState], k + this.xSize - 25, l + 5, 20, 20, returnStates[guiState] == -1337 ? translate("recmod.gui.close") : translate("recmod.gui.back")));

		int buttonDelim = 21;
		int buttonSize = this.xSize - 16;
		int smallButtonSize = buttonSize / 2;

		switch (guiState) {
		case 0:
			buttonList.add(new GuiButton(0, k + 8 + smallButtonSize * 0, l + 30 + buttonDelim * 0, smallButtonSize, 20, '\u00A7' + (RecMod.instance.isPlayerRecording(boundPlayer) ? translate("recmod.gui.enabledcode") : translate("recmod.gui.disabledcode")) + translate("recmod.gui.pane.main.recording")));
			buttonList.add(new GuiButton(1, k + 8 + smallButtonSize * 1, l + 30 + buttonDelim * 0, smallButtonSize, 20, '\u00A7' + (RecMod.instance.isPlayerStreaming(boundPlayer) ? translate("recmod.gui.enabledcode") : translate("recmod.gui.disabledcode")) + translate("recmod.gui.pane.main.streaming")));
			buttonList.add(new GuiButton(1338, k + 8, l + 30 + buttonDelim * 1, buttonSize, 20, stateNames[1]));
			buttonList.add(new GuiButton(1339, k + 8, l + 30 + buttonDelim * 2, buttonSize, 20, stateNames[2]));
			buttonList.add(new GuiButton(1340, k + 8, l + 30 + buttonDelim * 3, buttonSize, 20, stateNames[3]));
			buttonList.add(new GuiButton(1341, k + 8, l + 30 + buttonDelim * 4, buttonSize, 20, stateNames[4]));
			buttonList.add(new GuiButton(-1111, k + 8, l + 30 + buttonDelim * 5, buttonSize, 20, stateNames[5]));
			break;
		case 1:
			buttonList.add(new GuiButton(5, k + 8, l + 30 + buttonDelim * 0, buttonSize, 20, translate("recmod.gui.pane.general.kstate").replace("$s", '\u00A7' + (RecMod.instance.keepState ? translate("recmod.gui.enabledcode") + translate("recmod.gui.enabled") : translate("recmod.gui.disabledcode") + translate("recmod.gui.disabled")))));
			break;
		case 2:
			buttonList.add(new GuiButton(3, k + 8 + smallButtonSize * 0, l + 30 + buttonDelim * 0, smallButtonSize, 20, translate("recmod.gui.pane.interface.bobber").replace("$s", '\u00A7' + (RecMod.instance.showSelf ? translate("recmod.gui.enabledcode") + translate("recmod.gui.enabled") : translate("recmod.gui.disabledcode") + translate("recmod.gui.disabled")))));
			buttonList.add(new GuiButton(4, k + 8 + smallButtonSize * 1, l + 30 + buttonDelim * 0, smallButtonSize, 20, translate("recmod.gui.pane.interface.never").replace("$s", '\u00A7' + (!RecMod.instance.showSelfDef ? translate("recmod.gui.enabledcode") + translate("recmod.gui.enabled") : translate("recmod.gui.disabledcode") + translate("recmod.gui.disabled")))));
			break;
		case 3:
			buttonList.add(new GuiButton(2, k + 8, l + 30 + buttonDelim * 0, buttonSize, 20, translate("recmod.gui.pane.keyboard.enable").replace("$s", '\u00A7' + (RecMod.instance.enableKeys ? translate("recmod.gui.enabledcode") + translate("recmod.gui.enabled") : translate("recmod.gui.disabledcode") + translate("recmod.gui.disabled")))));

			if (RecMod.instance.enableKeys) {
				buttonList.add(new GuiButton(1594, k + 9, l + 30 + buttonDelim * 2, buttonSize, 20, (listenKeyType == 0 ? '\u00A7' + translate("recmod.gui.highlightcode") : "") + translate("recmod.gui.pane.keyboard.record").replace("$s", RecMod.instance.keyRec == -1337 ? translate("recmod.gui.disabled2") : Keyboard.getKeyName(RecMod.instance.keyRec))));
				buttonList.add(new GuiButton(1595, k + 9, l + 30 + buttonDelim * 3, buttonSize, 20, (listenKeyType == 1 ? '\u00A7' + translate("recmod.gui.highlightcode") : "") + translate("recmod.gui.pane.keyboard.stream").replace("$s", RecMod.instance.keyStr == -1337 ? translate("recmod.gui.disabled2") : Keyboard.getKeyName(RecMod.instance.keyStr))));
			}

			break;
		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		listenKeyType = -1;

		if (button.id == -1337) {
			this.mc.thePlayer.closeScreen();
		}

		if (button.id >= 1594) {
			listenKeyType = button.id - 1594;
		} else if (button.id >= 1337) {
			guiState = button.id - 1337;
		} else {
			switch (button.id) {
			case 0:
				RecMod.instance.updatePlayerInformation(boundPlayer, 0, !RecMod.instance.isPlayerRecording(boundPlayer));
				RecMod.packetPipeline.sendToServer(new PacketUpdatePlayerStatus(boundPlayer, 0, RecMod.instance.isPlayerRecording(boundPlayer)));

				break;
			case 1:
				RecMod.instance.updatePlayerInformation(boundPlayer, 1, !RecMod.instance.isPlayerStreaming(boundPlayer));
				RecMod.packetPipeline.sendToServer(new PacketUpdatePlayerStatus(boundPlayer, 1, RecMod.instance.isPlayerStreaming(boundPlayer)));

				break;
			case 2:
				RecMod.instance.enableKeys = !RecMod.instance.enableKeys;
				RecMod.instance.writeToFile();

				break;
			case 3:
				RecMod.instance.showSelf = !RecMod.instance.showSelf;

				break;
			case 4:
				RecMod.instance.showSelfDef = !RecMod.instance.showSelfDef;
				RecMod.instance.writeToFile();

				break;
			case 5:
				RecMod.instance.keepState = !RecMod.instance.keepState;
				RecMod.instance.writeToFile();

				break;
			}
		}

		createGui();
	}

	@Override
	protected void keyTyped(char c, int key) {
		if (listenKeyType == -1 && key == 1) {
			if (returnStates[guiState] != -1337) {
				guiState = returnStates[guiState];
				createGui();
			} else {
				this.mc.thePlayer.closeScreen();
			}
		} else {
			switch (listenKeyType) {
			case 0:
				RecMod.instance.keyRec = key == 1 ? -1337 : key;
				break;
			case 1:
				RecMod.instance.keyStr = key == 1 ? -1337 : key;
				break;
			}
			listenKeyType = -1;
			RecMod.instance.writeToFile();

			createGui();
		}
	}

	private String translate(String s) {
		return StatCollector.translateToLocal(s);
	}
}
